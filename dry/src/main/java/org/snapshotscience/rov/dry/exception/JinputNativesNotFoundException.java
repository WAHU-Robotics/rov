package org.snapshotscience.rov.dry.exception;

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

import org.snapshotscience.rov.common.exception.ROVException;

/**
 * Exception class for software being unable to find native libraries for jinput
 *
 * @see ROVException
 */
public class JinputNativesNotFoundException extends ROVException {
  public JinputNativesNotFoundException() {
    super("Could not find jinput natives in any subdirectory or in the $PATH var.");
  }
}
