package me.jbuelow.rov.common.response;

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

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import me.jbuelow.rov.common.command.Command;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public abstract class Response implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 550897509739131117L;

  @Getter
  private Instant timestamp;

  @Getter
  @Setter
  private Command request;

  @Getter
  private boolean success;

  @Getter
  private Exception exception;

  public Response() {
    this(null);
  }

  public Response(Command request) {
    this(request, true, null);
  }

  public Response(boolean success) {
    this(success, null);
  }

  public Response(boolean success, Exception exception) {
    this(null, success, exception);
  }

  public Response(Command request, boolean success, Exception exception) {
    this.timestamp = Instant.now();
    this.request = request;
    this.success = success;
    this.exception = exception;
  }

}