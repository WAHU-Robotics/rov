package org.snapshotscience.rov.wet.service.impl;

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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.snapshotscience.rov.common.command.Command;
import org.snapshotscience.rov.common.command.GetCapabilities;
import org.snapshotscience.rov.common.command.GetSystemStats;
import org.snapshotscience.rov.common.command.Ping;
import org.snapshotscience.rov.common.command.SetMotors;
import org.snapshotscience.rov.common.response.Pong;
import org.snapshotscience.rov.common.response.SetMotorsResponse;
import org.snapshotscience.rov.common.response.SystemStats;
import org.snapshotscience.rov.common.response.VehicleCapabilities;
import org.snapshotscience.rov.wet.service.CommandProcessorService;
import org.snapshotscience.rov.wet.service.MotorService;
import org.snapshotscience.rov.wet.vehicle.VehicleConfiguration;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.pwm.PwmDevice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import({CommandProcessorServiceImpl.class, PingHandler.class, GetCapabilitiesHandler.class, SetMotorsHandler.class})
public class CommandProcessorServiceImplTest {

  private static boolean firstTest = true;

  @MockBean
  private PwmDevice pcaDriver;

  @MockBean
  private MotorService motorService;

  @SpyBean
  private PingHandler pingHandler;

  @SpyBean
  private GetCapabilitiesHandler capabilitiesHandler;

  @SpyBean
  private SetMotorsHandler setMotorsHandler;

  @SpyBean
  private GetSystemStatsHandler getSystemStatsHandler;

  @MockBean
  private VehicleConfiguration vehicleConfiguration;

  @Autowired
  private CommandProcessorService commandProcessor;

  @Before
  public void setup() {
    when(vehicleConfiguration.getAllConfiguration()).thenReturn(new ArrayList<>());
  }

  @After
  public void finish() {
    if (firstTest) {
      verify(pingHandler, times(2)).getCommandType();
      verify(capabilitiesHandler, times(2)).getCommandType();
      verify(setMotorsHandler, times(2)).getCommandType();
      firstTest = false;
    }

    verifyNoMoreInteractions(pingHandler, capabilitiesHandler, setMotorsHandler);
  }

  /**
   * Test method for {@link CommandProcessorServiceImpl#handleCommand(Command)}.
   */
  @Test
  public void testHandleCommandPing() {
    assertThat(commandProcessor.handleCommand(new Ping()), instanceOf(Pong.class));
    verify(pingHandler, times(1)).execute(any(Ping.class));
  }

  /**
   * Test method for {@link CommandProcessorServiceImpl#handleCommand(Command)}.
   */
  @Test
  public void testHandleCommandGetCapabilities() {
    assertThat(commandProcessor.handleCommand(new GetCapabilities()), instanceOf(VehicleCapabilities.class));
    verify(capabilitiesHandler, times(1)).execute(any(GetCapabilities.class));
  }

  /**
   * Test method for {@link CommandProcessorServiceImpl#handleCommand(Command)}.
   */
  @Test
  public void testHandleCommandSetMotors() {
    assertThat(commandProcessor.handleCommand(new SetMotors()),
        instanceOf(SetMotorsResponse.class));
    verify(setMotorsHandler, times(1)).execute(any(SetMotors.class));
  }

  /**
   * Test method for {@link CommandProcessorServiceImpl#handleCommand(Command)}.
   */
  @Test(expected=IllegalArgumentException.class)
  public void testHandleCommandInvalid() {
    Command command = new Command() {

      /**
       *
       */
      private static final long serialVersionUID = 1L;};
    commandProcessor.handleCommand(command);
  }

  /**
   * Test method for {@link CommandProcessorServiceImpl#handleCommand(Command)}.
   */
  @Test
  public void testHandleCommandGetSystemStats() {
    GetSystemStats command = new GetSystemStats(true, true);
    assertThat(commandProcessor.handleCommand(command), instanceOf(SystemStats.class));
    verify(getSystemStatsHandler, times(1)).execute(any(GetSystemStats.class));
  }
}

