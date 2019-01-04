/**
 *
 */
package me.jbuelow.rov.common;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class GetSystemStats extends Command {

  public boolean getEnvironment;
  public boolean getProperties;

  public GetSystemStats(boolean getEnvironment, boolean getProperties) {
    this.getEnvironment = getEnvironment;
    this.getProperties = getProperties;
  }

}
