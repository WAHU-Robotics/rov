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

import java.util.Random;
import org.snapshotscience.rov.common.command.Ping;
import org.snapshotscience.rov.common.response.Pong;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class PingHandler implements CommandHandler<Ping> {

  /**
   * Handles the Ping command
   *
   * @param command Ping instance
   * @return Pong instance
   */
  @Override
  public Response execute(Ping command) {
    Random r = new Random();
    Pong response = new Pong(String.valueOf(r.nextInt(255)));
    return response;
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of Ping
   */
  @Override
  public Class<Ping> getCommandType() {
    return Ping.class;
  }

}
