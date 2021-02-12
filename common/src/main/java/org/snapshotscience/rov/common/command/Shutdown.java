package org.snapshotscience.rov.common.command;

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

import lombok.Getter;

public class Shutdown extends Command {

  private static final long serialVersionUID = 4207802681924667139L;

  @Getter
  private final Option option;

  public Shutdown() {
    this.option = Option.REBOOT;
  }

  public Shutdown(Option option) {
    this.option = option;
  }

  public enum Option {
    NOTHING("Do Nothing"), //Does nothing
    SOFT_REBOOT(
        "Software Reboot"), //Exits wet side software. Its considered a reboot because the OS on wet side should be set up to restart software on exit
    REBOOT("Reboot"), //Reboots wet side OS
    POWEROFF("Shutdown"); //Shuts down wet side OS
    public String readable;

    Option(String readable) {
      this.readable = readable;
    }

    @Override
    public String toString() {
      return readable;
    }
  }
}
