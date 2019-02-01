package me.jbuelow.rov.dry.controller;

import java.util.ArrayList;
import java.util.List;
import me.jbuelow.rov.common.MotorPower;
import me.jbuelow.rov.common.SetMotors;

public class ControlLogic {

  public static SetMotors genMotorValues(PolledValues controllerValues) {
    List<Integer> powerLevels = new ArrayList<>();

    PolledValues cvs = computeDeadzones(controllerValues, 10);

    //Rewire controller scheme here
    int rovX = cvs.x; //Strafe left/right
    int rovY = cvs.y; //Forwards/Backwards
    int rovZ = cvs.t; //Up/Down
    int rovYaw = cvs.z; //Rotate left/right

    //Logic
    powerLevels.set(0, rovX);
    powerLevels.set(1, rovX);
    powerLevels.set(2, rovX);
    powerLevels.set(3, rovX);
    powerLevels.set(4, rovZ);
    powerLevels.set(5, rovZ);

    SetMotors command = new SetMotors();
    for (int i = 0; i < 6; i++) {
      MotorPower m = new MotorPower();
      m.setPower(powerLevels.get(i));
      m.setId(i);
      command.getPowerLevels().add(m);
    }

    return command;
  }

  public static PolledValues computeDeadzones(PolledValues controllerValues, int deadzone) {
    List<Integer> vals = new ArrayList<>();

    for (Integer axis : controllerValues.getArray()) {
      if (axis > -deadzone && axis < deadzone) {
        axis = 0;
      }
      vals.add(axis);
    }

    return new PolledValues(vals.get(0), vals.get(1), vals.get(2), controllerValues.t,
        controllerValues.hat);
  }
}
