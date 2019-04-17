package me.jbuelow.rov.wet.exception;

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

public class InvalidFactorException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 5619309471373955898L;

  public InvalidFactorException() {
    super();
  }

  public InvalidFactorException(String message) {
    super(message);
  }

  public InvalidFactorException(Throwable cause) {
    super(cause);
  }

  public InvalidFactorException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidFactorException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
