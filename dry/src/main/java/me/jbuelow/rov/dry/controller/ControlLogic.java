package me.jbuelow.rov.dry.controller;

import java.util.ArrayList;
import java.util.List;
import me.jbuelow.rov.common.MotorPower;
import me.jbuelow.rov.common.SetMotors;

public class ControlLogic {

  public static SetMotors genMotorValues(PolledValues controllerValues) {
    List<Integer> powerLevels = new ArrayList<>();

    powerLevels.add(controllerValues.x / 10);
    powerLevels.add(controllerValues.y / 10);
    powerLevels.add(controllerValues.z / 10);
    powerLevels.add(controllerValues.t / 10);
    powerLevels.add(controllerValues.x / 10);
    powerLevels.add(controllerValues.y / 10);

    SetMotors command = new SetMotors();
    for (int i = 0; i < 6; i++) {
      MotorPower m = new MotorPower();
      m.setPower(powerLevels.get(i));
      m.setId(i);
      command.getPowerLevels().add(m);
    }

    return command;
  }
}
