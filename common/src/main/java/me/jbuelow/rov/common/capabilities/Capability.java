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

  UUID getId();

  String getName();
}
