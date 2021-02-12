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

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class GetSystemStats extends Command {

  private static final long serialVersionUID = 8163151692922115214L;

  public boolean getEnvironment;
  public boolean getProperties;

  public GetSystemStats() {
    this(false, false);
  }

  public GetSystemStats(boolean getEnvironment, boolean getProperties) {
    this.getEnvironment = getEnvironment;
    this.getProperties = getProperties;
  }
}
