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
