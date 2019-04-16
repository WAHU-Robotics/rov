package me.jbuelow.rov.common.capabilities;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public enum MotorOrientation {
  X_AXIS,     //Left/Right - x axis
  Y_AXIS,     //Forwards/Backwards - y axis
  Z_AXIS,     //Up/Down - z axis
  MULTI_AXIS  //Motor is angled across multiple axis or variable angled.
}
