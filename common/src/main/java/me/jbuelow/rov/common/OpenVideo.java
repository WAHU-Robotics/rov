/**
 *
 */
package me.jbuelow.rov.common;

import java.net.InetAddress;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class OpenVideo implements Command {

  public InetAddress address;

  public OpenVideo(InetAddress address) {
    this.address = address;
  }
}
