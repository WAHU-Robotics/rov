/**
 *
 */
package me.jbuelow.rov.common.command;

import java.net.InetAddress;
import me.jbuelow.rov.common.command.Command;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class OpenVideo extends Command {
  /**
   * 
   */
  private static final long serialVersionUID = 1273040397011101223L;

  public InetAddress address;

  public OpenVideo(InetAddress address) {
    this.address = address;
  }
}
