/**
 * 
 */
package me.jbuelow.rov.wet.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.GetCapabilities;
import me.jbuelow.rov.common.GetSystemStats;
import me.jbuelow.rov.common.Ping;
import me.jbuelow.rov.common.Pong;
import me.jbuelow.rov.common.SetMotors;
import me.jbuelow.rov.common.SetMotorsResponse;
import me.jbuelow.rov.common.SystemStats;
import me.jbuelow.rov.common.VehicleCapabilities;
import me.jbuelow.rov.wet.service.CommandProcessorService;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import me.jbuelow.rov.wet.vehicle.hardware.PCA9685;
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
  private PCA9685 pcaDriver;

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
      verify(pingHandler, times(1)).getCommandType();
      verify(capabilitiesHandler, times(1)).getCommandType();
      verify(setMotorsHandler, times(1)).getCommandType();
      firstTest = false;
    }

    verifyNoMoreInteractions(pingHandler, capabilitiesHandler, setMotorsHandler);
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.CommandProcessorServiceImpl#handleCommand(me.jbuelow.rov.common.Command)}.
   */
  @Test
  public void testHandleCommandPing() {
    assertThat(commandProcessor.handleCommand(new Ping()), instanceOf(Pong.class));
    verify(pingHandler, times(1)).execute(any(Ping.class));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.CommandProcessorServiceImpl#handleCommand(me.jbuelow.rov.common.Command)}.
   */
  @Test
  public void testHandleCommandGetCapabilities() {
    assertThat(commandProcessor.handleCommand(new GetCapabilities()), instanceOf(VehicleCapabilities.class));
    verify(capabilitiesHandler, times(1)).execute(any(GetCapabilities.class));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.CommandProcessorServiceImpl#handleCommand(me.jbuelow.rov.common.Command)}.
   */
  @Test
  public void testHandleCommandSetMotors() {
    assertThat(commandProcessor.handleCommand(new SetMotors()),
        instanceOf(SetMotorsResponse.class));
    verify(setMotorsHandler, times(1)).execute(any(SetMotors.class));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.CommandProcessorServiceImpl#handleCommand(me.jbuelow.rov.common.Command)}.
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
   * Test method for {@link me.jbuelow.rov.wet.service.impl.CommandProcessorServiceImpl#handleCommand(me.jbuelow.rov.common.Command)}.
   */
  @Test
  public void testHandleCommandGetSystemStats() {
    GetSystemStats command = new GetSystemStats(true, true);
    assertThat(commandProcessor.handleCommand(command), instanceOf(SystemStats.class));
    verify(getSystemStatsHandler, times(1)).execute(any(GetSystemStats.class));
  }
}

