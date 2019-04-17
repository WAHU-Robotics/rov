/**
 *
 */
package me.jbuelow.rov.common.command;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class GetSystemStats extends Command {

  private static final long serialVersionUID = 8163151692922115214L;

  public boolean getEnvironment;
  public boolean getProperties;

  public GetSystemStats() {
    this(false, false);
  }

  public GetSystemStats(boolean getEnvironment, boolean getProperties) {
    this.getEnvironment = getEnvironment;
    this.getProperties = getProperties;
  }
}
