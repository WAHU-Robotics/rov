package org.snapshotscience.rov.wet.service.impl;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.common.capabilities.Tool;
import org.snapshotscience.rov.wet.service.ServoService;
import org.snapshotscience.rov.wet.vehicle.ServoConfig;
import org.snapshotscience.rov.wet.vehicle.VehicleConfiguration;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.pwm.PwmChannel;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.pwm.PwmDevice;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.pwm.Servo;
import org.springframework.stereotype.Service;

/**
 * Handles servos as a service.
 */
@Service
@Slf4j
public class ServoServiceImpl implements ServoService {
  //private static final double PWM_FREQUENCY = 60;

  private Map<Tool, Servo> servos = new HashMap<>();
  private Map<String, UUID> servoNames = new HashMap<>();
  private List<ServoConfig> servoConfiguration;

  public ServoServiceImpl(PwmDevice pwmDevice, VehicleConfiguration vehicleConfigurtion)
      throws IOException {
    //pwmDevice.setPWMFreqency(PWM_FREQUENCY); //TODO figure out if neccessary
    
    for (ServoConfig servoConfig : vehicleConfigurtion.getServoConfiguration()) {
      PwmChannel pwmChannel = pwmDevice.getChannel(servoConfig.getPwmPort());
      Servo servo = new Servo(pwmChannel);
      
      servos.put(servoConfig.getTool(), servo);
      servoNames.put(servoConfig.getName(), servoConfig.getId());
    }
  }

  @Override//TODO cleanup maybe?
  public void setValues(Map<Tool, Integer> thrustVectors) {
    log.debug("setMotion: {}", thrustVectors.toString());

    //Set Servos
    for (Entry<Tool, Integer> entry : thrustVectors.entrySet()) {
      try {
        setServoValue(entry.getKey(), entry.getValue());
      } catch (IOException e) {
        throw new RuntimeException("Error setting motor power levels.", e);
      }
    }
  }
  
  @Override
  public void setServoValue(Tool tool, int value) throws IOException {
    getServo(tool).setValue(value);
  }

  @Override
  public int getServoValue(Tool tool) {
    return getServo(tool).getValue();
  }

  private Servo getServo(Tool tool) {
    if (servos.containsKey(tool)) {
      return servos.get(tool);
    }
    
    throw new RuntimeException("Invalid servo Id: " + tool);
  }

  @Override
  public UUID getServoByName(String name) {
    return servoNames.get(name);
  }
}
