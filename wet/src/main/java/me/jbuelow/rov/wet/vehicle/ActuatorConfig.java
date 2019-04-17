/**
 *
 */
package me.jbuelow.rov.wet.vehicle;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface ActuatorConfig extends AccessoryConfig {

  int getPwmPort();
}
