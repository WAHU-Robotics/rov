/**
 *
 */
package me.jbuelow.rov.common.capabilities;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface Capability extends Serializable {

  public UUID getId();

  public String getName();
}
