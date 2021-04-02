package org.snapshotscience.rov.wet.exception;

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

public class InvalidThrustAxisException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 7659378390839865121L;

  public InvalidThrustAxisException() {
    super();
  }

  public InvalidThrustAxisException(String message) {
    super(message);
  }

  public InvalidThrustAxisException(Throwable cause) {
    super(cause);
  }

  public InvalidThrustAxisException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidThrustAxisException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
