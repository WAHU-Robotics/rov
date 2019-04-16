/**
 * 
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.Shutdown;
import me.jbuelow.rov.common.response.Goodbye;
import me.jbuelow.rov.common.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class ShutdownHandlerTest {
  private ShutdownHandler handler;
  
  @Before
  public void setup() {
    handler = new ShutdownHandler();
  }
  
  /**
   * Test method for {@link ShutdownHandler#execute(Shutdown)}.
   */
  @Test
  public void testExecute() {
    Response response = handler.execute(new Shutdown());
    assertThat(response, instanceOf(Goodbye.class));
  }

  /**
   * Test method for {@link PingHandler#getCommandType()}.
   */
  @Test
  public void testGetCommandType() {
    assertThat(handler.getCommandType(), is(equalTo(Shutdown.class)));
  }

}
