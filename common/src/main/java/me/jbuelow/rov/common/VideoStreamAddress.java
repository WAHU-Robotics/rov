/**
 *
 */
package me.jbuelow.rov.common;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class VideoStreamAddress extends Response {

  public String url;

  public VideoStreamAddress(String url) {
    this.url = url;
  }
}
