package me.jbuelow.rov.dry.controller;

import me.jbuelow.rov.common.MotorPower;
import me.jbuelow.rov.common.SetMotors;

public class ControlLogic {

  public static SetMotors genMotorValues(PolledValues controllerValues) {

    SetMotors command = new SetMotors();
    for (int i = 0; i < 6; i++) {
      MotorPower m = new MotorPower();
      m.setPower(0);
      m.setId(i);
      command.getPowerLevels().add(m);
    }

    return command;
  }
}
