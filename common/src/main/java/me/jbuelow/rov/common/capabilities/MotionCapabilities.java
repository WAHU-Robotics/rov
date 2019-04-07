package me.jbuelow.rov.common.capabilities;

import java.util.Set;

public class MotionCapabilities extends AbstractCapability {
  /**
   * 
   */
  private static final long serialVersionUID = 375426720660294260L;
  
  private final boolean surge;
  private final boolean sway;
  private final boolean heave;
  private final boolean pitch;
  private final boolean roll;
  private final boolean yaw;
  
  public MotionCapabilities(Set<ThrustAxis> thrustAxes) {
    setName("motion");
    surge = thrustAxes.contains(ThrustAxis.SURGE);
    sway = thrustAxes.contains(ThrustAxis.SWAY);
    heave = thrustAxes.contains(ThrustAxis.HEAVE);
    pitch = thrustAxes.contains(ThrustAxis.PITCH);
    roll = thrustAxes.contains(ThrustAxis.ROLL);
    yaw = thrustAxes.contains(ThrustAxis.YAW);
  }

  public boolean canSurge() {
    return surge;
  }

  public boolean canSway() {
    return sway;
  }

  public boolean canHeave() {
    return heave;
  }

  public boolean canPitch() {
    return pitch;
  }

  public boolean canRoll() {
    return roll;
  }

  public boolean canYaw() {
    return yaw;
  }
}
