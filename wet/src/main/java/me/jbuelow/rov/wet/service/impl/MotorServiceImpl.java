package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.wet.service.MotorService;
import me.jbuelow.rov.wet.vehicle.MotorConfig;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import me.jbuelow.rov.wet.vehicle.hardware.Motor;
import me.jbuelow.rov.wet.vehicle.hardware.PwmInterface;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles motors as a service.
 */
@Service
public class MotorServiceImpl implements MotorService {
  private static final int PWM_FREQUENCY = 60;

  private Map<UUID, Motor> motors = new HashMap<>();
  private Map<String, UUID> motorNames = new HashMap<>();
  
  public MotorServiceImpl(PwmInterface pwmInterface, VehicleConfiguration vehicleConfigurtion) {
    pwmInterface.setPWMFreq(PWM_FREQUENCY);
    
    for (MotorConfig motorConfig : vehicleConfigurtion.getMotorConfiguration()) {
      Motor motor = new Motor(pwmInterface, motorConfig.getPwmPort());
      
      motors.put(motorConfig.getId(), motor);
      motorNames.put(motorConfig.getName(), motorConfig.getId());
    }
  }
  
  @Override
  public void setMotorPower(UUID id, int power) {
    getMotor(id).setPower(power);
  }

  @Override
  public int getMotorPower(UUID id) {
    return getMotor(id).getPower();
  }

  @Override
  public void armMotor(UUID id) {
    getMotor(id).arm();
  }

  @Override
  public boolean isArmed(UUID id) {
    return getMotor(id).isArmed();
  }

  private Motor getMotor(UUID id) {
    if (motors.containsKey(id)) {
      return motors.get(id);
    }
    
    throw new RuntimeException("Invalid motor Id: " + id);
  }

  @Override
  public UUID getMotorByName(String name) {
    return motorNames.get(name);
  }
}
