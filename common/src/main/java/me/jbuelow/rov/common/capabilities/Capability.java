/**
 * 
 */
package me.jbuelow.rov.common.capabilities;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Brian Wachsmuth
 *
 */
public interface Capability extends Serializable {
  public int getId();
  public String getName();
}
