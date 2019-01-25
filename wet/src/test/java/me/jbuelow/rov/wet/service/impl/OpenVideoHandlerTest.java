package me.jbuelow.rov.wet.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import me.jbuelow.rov.common.OpenVideo;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.VideoStreamAddress;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class OpenVideoHandlerTest {

  private OpenVideoHandler handler;

  @Before
  public void setup() {
    handler = new OpenVideoHandler();
  }

  /**
   * Test method for {@link OpenVideoHandler#execute(me.jbuelow.rov.common.OpenVideo)}.
   */
  @Test
  public void testExecute() {
    InetAddress addr = null;
    try {
      addr = InetAddress.getByName("127.0.0.1");
    } catch (UnknownHostException e) {
      fail();
    }
    Response response = handler.execute(new OpenVideo(addr));
    assertThat(response, instanceOf(VideoStreamAddress.class));
    VideoStreamAddress vidaddr = (VideoStreamAddress) response;
  }

  /**
   * Test method for {@link PingHandler#getCommandType()}.
   */
  @Test
  public void testGetCommandType() {
    assertThat(handler.getCommandType(), is(equalTo(OpenVideo.class)));
  }

}
