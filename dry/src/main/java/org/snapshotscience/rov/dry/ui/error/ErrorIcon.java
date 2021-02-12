package org.snapshotscience.rov.dry.ui.error;

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
 * Enumerator for icons used in GeneralError
 *
 * @see GeneralError
 */
public enum ErrorIcon {
  GENERAL("error.png"),
  JOY_OK("joystick-icon.png"),
  JOY_ERROR("joystick-connection-error.png"),
  WARNING("warning.png");


  private String filename;

  ErrorIcon(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
