package org.snapshotscience.rov.common.capabilities;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

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
