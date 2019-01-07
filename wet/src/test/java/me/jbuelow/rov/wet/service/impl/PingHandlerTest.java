/**
 * 
 */
package me.jbuelow.rov.wet.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import me.jbuelow.rov.common.Ping;
import me.jbuelow.rov.common.Pong;
import me.jbuelow.rov.common.Response;
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
   * Test method for {@link me.jbuelow.rov.wet.service.impl.PingHandler#execute(me.jbuelow.rov.common.Ping)}.
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
