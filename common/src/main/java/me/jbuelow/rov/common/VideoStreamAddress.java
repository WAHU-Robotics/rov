/**
 *
 */
package me.jbuelow.rov.common;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class VideoStreamAddress implements Response {

  public String url;

  public VideoStreamAddress(String url) {
    this.url = url;
  }
}
