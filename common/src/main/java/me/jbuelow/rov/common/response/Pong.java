/**
 *
 */
package me.jbuelow.rov.common.response;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class Pong extends Response {

  private static final long serialVersionUID = -2290713647984123482L;
  public String message;

  public Pong(String message) {
    this.message = message;
  }

}
