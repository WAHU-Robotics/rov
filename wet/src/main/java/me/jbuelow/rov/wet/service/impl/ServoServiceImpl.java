package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.capabilities.Tool;
import me.jbuelow.rov.wet.service.ServoService;
import me.jbuelow.rov.wet.vehicle.ServoConfig;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import me.jbuelow.rov.wet.vehicle.hardware.pwm.PwmChannel;
import me.jbuelow.rov.wet.vehicle.hardware.pwm.PwmDevice;
import me.jbuelow.rov.wet.vehicle.hardware.pwm.Servo;
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
