package me.jbuelow.rov.dry.controller;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.common.command.SetMotion;
import me.jbuelow.rov.dry.config.Config;

/**
 * Handles mapping of joystick axes and computing deadzones
 */
public class ControlLogic {

  public static SetMotion genMotorValues(PolledValues rawPrimaryJoy, PolledValues rawSecondaryJoy) {
    //int[] powerLevels = new int[6];

    PolledValues primaryJoy = computeDeadzones(rawPrimaryJoy, Config.JOY_DEADZONE);
    PolledValues secondaryJoy = computeDeadzones(rawSecondaryJoy, Config.JOY_DEADZONE);

    /*
      // really old and bad code. dont use it.
      // i will hunt you down if this ends up uncommented

      //Rewire controller scheme here
      int rovX = cvs.x; //Strafe left/right
      int rovY = cvs.y; //Forwards/Backwards
      int rovZ = cvs.t; //Up/Down
      int rovYaw = cvs.z; //Rotate left/right
      //Logic
      double[] dVec = new double[]{0, 0};
      dVec[0] = ((double) rovX * -1 / 1000);
      dVec[1] = ((double) rovY * -1 / 1000);
      double mag = getMag(dVec);
      dVec = getNorm(mag, dVec);
      if (mag > 1) {
        mag = 1;
      }
      powerLevels[0] = (int) ((dotProduct(dVec, motorFrontLeft) * mag) * 1000)+100;
      powerLevels[1] = (int) ((dotProduct(dVec, motorFrontRight) * mag) * 1000);
      powerLevels[2] = (int) ((dotProduct(dVec, motorBackLeft) * mag) * 1000);
      powerLevels[3] = (int) ((dotProduct(dVec, motorBackRight) * mag) * 1000);

      powerLevels[4] = rovZ;
      powerLevels[5] = rovZ;

      SetMotors command = new SetMotors();
      for (int i = 0; i < 6; i++) {
        MotorPower m = new MotorPower();
        m.setPower(powerLevels[i]);
        m.setId(i);
        command.getPowerLevels().add(m);
      }
    */

    SetMotion command = new SetMotion();
    Map<ThrustAxis, Integer> vectors = command.getThrustVectors();

    ControlMapper m = new ControlMapper(primaryJoy, secondaryJoy);

    vectors.put(ThrustAxis.SURGE, m.getAxis(Config.SURGE_AXIS) * (Config.SURGE_INVERT ? -1 : 1));
    vectors.put(ThrustAxis.SWAY, m.getAxis(Config.SWAY_AXIS) * (Config.SWAY_INVERT ? -1 : 1));
    vectors.put(ThrustAxis.HEAVE, m.getAxis(Config.HEAVE_AXIS) * (Config.HEAVE_INVERT ? -1 : 1));
    vectors.put(ThrustAxis.YAW, m.getAxis(Config.YAW_AXIS) * (Config.YAW_INVERT ? -1 : 1));
    
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

  public static PolledValues computeNonLinearity(PolledValues controllerValues) {
    List<Integer> vals = new ArrayList<>();

    for (Integer axis : controllerValues.getArray()) {
      int val = ((axis * axis) / 1000);
      vals.add(axis < 0 ? val * -1 : val);
    }

    return new PolledValues(vals.get(0), vals.get(1), vals.get(2), controllerValues.t,
        controllerValues.hat);
  }
}
