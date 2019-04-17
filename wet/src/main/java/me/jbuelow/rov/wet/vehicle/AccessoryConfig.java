/**
 *
 */
package me.jbuelow.rov.wet.vehicle;

import java.util.UUID;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface AccessoryConfig {

  UUID getId();

  String getName();

  void validateConfigurtion();
}
