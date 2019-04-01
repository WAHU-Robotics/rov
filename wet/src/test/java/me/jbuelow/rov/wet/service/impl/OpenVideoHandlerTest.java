package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.OpenVideo;
import org.junit.Before;

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
   * Test method for {@link OpenVideoHandler#execute(OpenVideo)}.
   *//*
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
  }*/

  /**
   * Test method for {@link PingHandler#getCommandType()}.
   *//*
  @Test
  public void testGetCommandType() {
    assertThat(handler.getCommandType(), is(equalTo(OpenVideo.class)));
  }*/

}
