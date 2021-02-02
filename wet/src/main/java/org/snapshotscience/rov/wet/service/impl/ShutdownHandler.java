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

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.common.command.Shutdown;
import org.snapshotscience.rov.common.response.Goodbye;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.wet.service.CommandHandler;
import org.snapshotscience.rov.wet.service.MotorService;
import org.snapshotscience.rov.wet.service.ShutdownThreads;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShutdownHandler implements CommandHandler<Shutdown> {

  protected ShutdownThreads shutdownThreads;
  private final MotorService motorService;

  public ShutdownHandler(MotorService motorService) {
    this.motorService = motorService;
    setShutdownThreads();
  }

  protected void setShutdownThreads() {
    this.shutdownThreads = new ShutdownThreadsImpl();
  }

  /**
   * Handles the Shutdown command
   *
   * @param command Shutdown instance
   * @return Goodbye instance
   */
  @Override
  public Response execute(Shutdown command) {
    boolean failsafed = false;
    for (int i = 0; i < 5; i++) {
      try {
        motorService.failsafe();
      } catch (IOException e) {
        continue;
      }
      failsafed = true;
      break;
    }

    Thread shutdownThread;

    switch (command.getOption()) {
      case SOFT_REBOOT:
        shutdownThread = shutdownThreads.getSoftRebootThread();
        break;

      case REBOOT:
      default:
        shutdownThread = shutdownThreads.getRebootThread();
        break;

      case POWEROFF:
        shutdownThread = shutdownThreads.getShutdownThread();
        break;
    }

    shutdownThread.start();
    return new Goodbye(failsafed, shutdownThread);
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of Shutdown
   */
  @Override
  public Class<Shutdown> getCommandType() {
    return Shutdown.class;
  }
}
