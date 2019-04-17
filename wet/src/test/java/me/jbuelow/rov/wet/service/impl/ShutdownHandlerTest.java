package me.jbuelow.rov.wet.service.impl;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.common.command.Shutdown;
import me.jbuelow.rov.common.command.Shutdown.Option;
import me.jbuelow.rov.common.response.Goodbye;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.MotionService;
import me.jbuelow.rov.wet.service.MotorService;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("noHardware")
@Slf4j
public class ShutdownHandlerTest {
  @Rule
  public ErrorCollector collector = new ErrorCollector();

  private ShutdownHandler handler;

  @Autowired
  private MotorService motorService;

  @Autowired
  private MotionService motionService;

  @Before
  public void setup() {
    handler = new ShutdownHandler(motorService) {
      @Override
      protected void setShutdownThreads() {
        this.shutdownThreads = new ShutdownThreadsImpl() {
          @SuppressWarnings("deprecation")
          @Override
          protected boolean isJUnitTest() {
            return true; //lol
          }
        };
      }
    };

    //Randomly set motors so we can verify that they stop
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    Random rnd = new Random();
    motion.put(ThrustAxis.SURGE, rnd.nextInt(2000) - 1000);
    motion.put(ThrustAxis.HEAVE, rnd.nextInt(2000) - 1000);
    motion.put(ThrustAxis.SWAY, rnd.nextInt(2000) - 1000);
    motion.put(ThrustAxis.PITCH, rnd.nextInt(2000) - 1000);
    motion.put(ThrustAxis.ROLL, rnd.nextInt(2000) - 1000);
    motion.put(ThrustAxis.YAW, rnd.nextInt(2000) - 1000);
    motionService.setMotion(motion);
  }
  
  /**
   * Test method for {@link ShutdownHandler#execute(Shutdown)}.
   */
  @Test
  public void testExecuteDefault() {
    Response response = handler.execute(new Shutdown());
    forAllExecutionTests(response);
    Goodbye goodbye = (Goodbye) response;

    //Verify that method defaulted to RebootThread
    assertThat(goodbye.getThread(), instanceOf(ShutdownThreadsImpl.RebootThread.class));
    log.info("RebootThread was resolved.");
  }

  /**
   * Test method for {@link ShutdownHandler#execute(Shutdown)}.
   */
  @Test
  public void testExecuteSoftReboot() {
    Response response = handler.execute(new Shutdown(Option.SOFT_REBOOT));
    forAllExecutionTests(response);
    Goodbye goodbye = (Goodbye) response;

    //Verify that method defaulted to RebootThread
    assertThat(goodbye.getThread(), instanceOf(ShutdownThreadsImpl.SoftRebootThread.class));
    log.info("SoftRebootThread was resolved.");
  }

  /**
   * Test method for {@link ShutdownHandler#execute(Shutdown)}.
   */
  @Test
  public void testExecuteReboot() {
    Response response = handler.execute(new Shutdown(Option.REBOOT));
    forAllExecutionTests(response);
    Goodbye goodbye = (Goodbye) response;

    //Verify that method defaulted to RebootThread
    assertThat(goodbye.getThread(), instanceOf(ShutdownThreadsImpl.RebootThread.class));
    log.info("RebootThread was resolved.");
  }

  /**
   * Test method for {@link ShutdownHandler#execute(Shutdown)}.
   */
  @Test
  public void testExecutePoweroff() {
    Response response = handler.execute(new Shutdown(Option.POWEROFF));
    forAllExecutionTests(response);
    Goodbye goodbye = (Goodbye) response;

    //Verify that method defaulted to RebootThread
    assertThat(goodbye.getThread(), instanceOf(ShutdownThreadsImpl.ShutdownThread.class));
    log.info("ShutdownThread was resolved.");
  }

  private void forAllExecutionTests(Response response) {
    //Verify type of response
    assertThat(response, instanceOf(Goodbye.class));
    log.info("Response is of Goodbye type.");

    Goodbye goodbye = (Goodbye) response;

    if (goodbye.isSafe()) {
      log.info("Response claims motors to be in safe state. Checking motor states...");
      //Verify that all motors are failsafed
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")), is(0));
      log.info("All motors have been checked and assertion failures have been added to error collector.");
    } else {
      log.error("Response does not claim to be in a safe state.");
      log.error("For safety reasons, this test will be failed.");
      collector.addError(new AssumptionViolatedException("Response did not report a safe motor state.")); //Wish Junit had warnings
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), not(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), not(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), not(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), not(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), not(0));
      collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")), not(0));
      log.info("All motors have been checked and assertion failures have been added to error collector.");
    }
  }

  /**
   * Test method for {@link PingHandler#getCommandType()}.
   */
  @Test
  public void testGetCommandType() {
    assertThat(handler.getCommandType(), is(equalTo(Shutdown.class)));
  }

}
