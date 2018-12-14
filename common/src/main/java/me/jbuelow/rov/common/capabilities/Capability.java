/**
 *
 */
package me.jbuelow.rov.common.capabilities;

import java.io.Serializable;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface Capability extends Serializable {

  public int getId();

  public String getName();
}
