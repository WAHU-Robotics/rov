/**
 *
 */
package me.jbuelow.rov.common;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public abstract class Response implements Serializable {

  @Getter
  private Instant timestamp;

  @Getter
  @Setter
  private Command request;

  public Response(Command request) {
    this.timestamp = Instant.now();
    this.request = request;
  }

  public Response() {
    this.timestamp = Instant.now();
    this.request = null;
  }

}