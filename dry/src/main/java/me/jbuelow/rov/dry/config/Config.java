package me.jbuelow.rov.dry.config;

import me.jbuelow.rov.dry.controller.JoystickAxis;
import me.jbuelow.rov.dry.controller.JoystickButton;

import static me.jbuelow.rov.dry.controller.JoystickAxis.*;
import static me.jbuelow.rov.dry.controller.JoystickButton.PRIMARY_0;

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
  public static final JoystickAxis ROLL_AXIS = null;

  // Axis inversions
  public static final boolean SURGE_INVERT = true;
  public static final boolean SWAY_INVERT = true;
  public static final boolean HEAVE_INVERT = false;
  public static final boolean YAW_INVERT = false;
  public static final boolean PITCH_INVERT = false;
  public static final boolean ROLL_INVERT = false;

  // Tool button mapping
  public static final JoystickButton GRIPPER_BUTTON = PRIMARY_0;
}
