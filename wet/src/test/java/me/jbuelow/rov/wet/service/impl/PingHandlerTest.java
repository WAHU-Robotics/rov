package me.jbuelow.rov.wet.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import me.jbuelow.rov.common.command.Ping;
import me.jbuelow.rov.common.response.Pong;
import me.jbuelow.rov.common.response.Response;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class PingHandlerTest {
  private PingHandler handler;
  
  @Before
  public void setup() {
    handler = new PingHandler();
  }
  
  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.PingHandler#execute(Ping)}.
   */
  @Test
  public void testExecute() {
    Response response = handler.execute(new Ping());
    assertThat(response, instanceOf(Pong.class));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.PingHandler#getCommandType()}.
   */
  @Test
  public void testGetCommandType() {
    assertThat(handler.getCommandType(), is(equalTo(Ping.class)));
  }

}
