package org.snapshotscience.rov.dry.config;

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

import org.snapshotscience.rov.dry.controller.JoystickAxis;
import org.snapshotscience.rov.dry.controller.JoystickButton;

/**
 * Configure options like buttonmaps here
 */
public abstract class Config {
  // Joystick deadzone
  public static final int JOY_DEADZONE = 50;

  // Joystick axis mapping
  public static final JoystickAxis SURGE_AXIS = JoystickAxis.PRIMARY_Y;
  public static final JoystickAxis SWAY_AXIS = JoystickAxis.PRIMARY_X;
  public static final JoystickAxis HEAVE_AXIS = JoystickAxis.SECONDARY_Y;
  public static final JoystickAxis YAW_AXIS = JoystickAxis.PRIMARY_Z;
  public static final JoystickAxis PITCH_AXIS = null;
  public static final JoystickAxis ROLL_AXIS = JoystickAxis.SECONDARY_X;

  public static final JoystickAxis CAMERA_X_AXIS = JoystickAxis.PRIMARY_T;

  // Axis inversions
  public static final boolean SURGE_INVERT = true;
  public static final boolean SWAY_INVERT = false;
  public static final boolean HEAVE_INVERT = false;
  public static final boolean YAW_INVERT = false;
  public static final boolean PITCH_INVERT = false;
  public static final boolean ROLL_INVERT = true;

  // Axis sensitivities
  public static final double SURGE_SENS = 1.0;
  public static final double SWAY_SENS = 1.0;
  public static final double HEAVE_SENS = 1.0;
  public static final double YAW_SENS = 1.0;
  public static final double PITCH_SENS = 1.0;
  public static final double ROLL_SENS = 1.0;

  // Tool button mapping
  public static final JoystickButton GRIPPER_BUTTON = JoystickButton.PRIMARY_0;
  public static final JoystickButton MAGNET_BUTTON = JoystickButton.PRIMARY_1;
  public static final JoystickButton LIGHT_BUTTON = JoystickButton.PRIMARY_4;
  public static final JoystickButton CUP_BUTTON = JoystickButton.PRIMARY_5;
  public static final JoystickButton SNAP_BUTTON = JoystickButton.PRIMARY_6;

  // Tool servo inversions
  public static final boolean GRIPPER_INVERT = false;
  public static final boolean MAGNET_INVERT = false;
  public static final boolean LIGHT_INVERT = false;
  public static final boolean CUP_INVERT = false;
  public static final boolean CAMERA_X_INVERT = false;
}
