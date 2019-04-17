/**
 *
 */
package me.jbuelow.rov.common.response;

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