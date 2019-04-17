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

import org.junit.Before;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class OpenVideoHandlerTest {

  //private OpenVideoHandler handler;

  @Before
  public void setup() {
    //handler = new OpenVideoHandler();
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
