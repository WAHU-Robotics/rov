package me.jbuelow.rov.dry.config;

import static me.jbuelow.rov.dry.controller.JoystickAxis.PRIMARY_X;
import static me.jbuelow.rov.dry.controller.JoystickAxis.PRIMARY_Y;
import static me.jbuelow.rov.dry.controller.JoystickAxis.PRIMARY_Z;
import static me.jbuelow.rov.dry.controller.JoystickAxis.SECONDARY_T;
import static me.jbuelow.rov.dry.controller.JoystickAxis.SECONDARY_X;
import static me.jbuelow.rov.dry.controller.JoystickAxis.SECONDARY_Y;
import static me.jbuelow.rov.dry.controller.JoystickButton.PRIMARY_0;
import static me.jbuelow.rov.dry.controller.JoystickButton.PRIMARY_1;
import static me.jbuelow.rov.dry.controller.JoystickButton.PRIMARY_4;
import static me.jbuelow.rov.dry.controller.JoystickButton.PRIMARY_5;

import me.jbuelow.rov.dry.controller.JoystickAxis;
import me.jbuelow.rov.dry.controller.JoystickButton;

/**
 * Configure options like buttonmaps here
 */
public abstract class Config {
  // Joystick deadzone
  public static final int JOY_DEADZONE = 20;

  // Joystick axis mapping
  public static final JoystickAxis SURGE_AXIS = PRIMARY_Y;
  public static final JoystickAxis SWAY_AXIS = PRIMARY_X;
  public static final JoystickAxis HEAVE_AXIS = SECONDARY_Y;
  public static final JoystickAxis YAW_AXIS = PRIMARY_Z;
  public static final JoystickAxis PITCH_AXIS = null;
  public static final JoystickAxis ROLL_AXIS = SECONDARY_X;

  public static final JoystickAxis CAMERA_X_AXIS = SECONDARY_T;

  // Axis inversions
  public static final boolean SURGE_INVERT = true;
  public static final boolean SWAY_INVERT = true;
  public static final boolean HEAVE_INVERT = false;
  public static final boolean YAW_INVERT = false;
  public static final boolean PITCH_INVERT = false;
  public static final boolean ROLL_INVERT = false;

  // Tool button mapping
  public static final JoystickButton GRIPPER_BUTTON = PRIMARY_0;
  public static final JoystickButton MAGNET_BUTTON = PRIMARY_1;
  public static final JoystickButton LIGHT_BUTTON = PRIMARY_4;
  public static final JoystickButton CUP_BUTTON = PRIMARY_5;

  // Tool servo inversions
  public static final boolean GRIPPER_INVERT = false;
  public static final boolean MAGNET_INVERT = false;
  public static final boolean LIGHT_INVERT = false;
  public static final boolean CUP_INVERT = false;
  public static final boolean CAMERA_X_INVERT = false;
}
