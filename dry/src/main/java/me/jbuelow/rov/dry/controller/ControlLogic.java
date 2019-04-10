package me.jbuelow.rov.dry.controller;

import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.common.command.SetMotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles mapping of joystick axes and computing deadzones
 */
public class ControlLogic {

  public static SetMotion genMotorValues(PolledValues controllerValues) {
    //int[] powerLevels = new int[6];

    PolledValues cvs = computeDeadzones(controllerValues, 10); //TODO trace deadzone back to be more configurable

    //Rewire controller scheme here
//    int rovX = cvs.x; //Strafe left/right
//    int rovY = cvs.y; //Forwards/Backwards
//    int rovZ = cvs.t; //Up/Down
//    int rovYaw = cvs.z; //Rotate left/right

//    //Logic
//    double[] dVec = new double[]{0, 0};
//    dVec[0] = ((double) rovX * -1 / 1000);
//    dVec[1] = ((double) rovY * -1 / 1000);
//    double mag = getMag(dVec);
//    dVec = getNorm(mag, dVec);
//    if (mag > 1) {
//      mag = 1;
//    }
//    powerLevels[0] = (int) ((dotProduct(dVec, motorFrontLeft) * mag) * 1000)+100;
//    powerLevels[1] = (int) ((dotProduct(dVec, motorFrontRight) * mag) * 1000);
//    powerLevels[2] = (int) ((dotProduct(dVec, motorBackLeft) * mag) * 1000);
//    powerLevels[3] = (int) ((dotProduct(dVec, motorBackRight) * mag) * 1000);
//
//    powerLevels[4] = rovZ;
//    powerLevels[5] = rovZ;
//
//    SetMotors command = new SetMotors();
//    for (int i = 0; i < 6; i++) {
//      MotorPower m = new MotorPower();
//      m.setPower(powerLevels[i]);
//      m.setId(i);
//      command.getPowerLevels().add(m);
//    }

    SetMotion command = new SetMotion();
    Map<ThrustAxis, Integer> vectors = command.getThrustVectors();
    
    vectors.put(ThrustAxis.SURGE, cvs.y * -1);  //invert the controls
    vectors.put(ThrustAxis.SWAY, cvs.x * -1);  //invert the controls
    vectors.put(ThrustAxis.HEAVE, cvs.t);
    vectors.put(ThrustAxis.YAW, cvs.z);
    
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
