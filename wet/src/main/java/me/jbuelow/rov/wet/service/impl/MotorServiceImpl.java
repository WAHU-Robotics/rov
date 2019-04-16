package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.jbuelow.rov.wet.service.MotorService;
import me.jbuelow.rov.wet.vehicle.MotorConfig;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import me.jbuelow.rov.wet.vehicle.hardware.pwm.Motor;
import me.jbuelow.rov.wet.vehicle.hardware.pwm.PwmChannel;
import me.jbuelow.rov.wet.vehicle.hardware.pwm.PwmDevice;
import org.springframework.stereotype.Service;

/**
 * Handles motors as a service.
 */
@Service
public class MotorServiceImpl implements MotorService {
  private static final double PWM_FREQUENCY = 60;

  private final Map<UUID, Motor> motors = new HashMap<>();
  private final Map<String, UUID> motorNames = new HashMap<>();
  
  public MotorServiceImpl(PwmDevice pwmDevice, VehicleConfiguration vehicleConfigurtion) throws IOException {
    pwmDevice.setPWMFreqency(PWM_FREQUENCY);

    for (MotorConfig motorConfig : vehicleConfigurtion.getMotorConfiguration()) {
      PwmChannel pwmChannel = pwmDevice.getChannel(motorConfig.getPwmPort());
      Motor motor = new Motor(pwmChannel);

      motors.put(motorConfig.getId(), motor);
      motorNames.put(motorConfig.getName(), motorConfig.getId());
      motor.arm();
    }
  }

  @Override
  public void setMotorPower(UUID id, int power) throws IOException {
    getMotor(id).setPower(power);
  }

  @Override
  public int getMotorPower(UUID id) {
    return getMotor(id).getPower();
  }

  @Override
  public void armMotor(UUID id) throws IOException {
    getMotor(id).arm();
  }

  @Override
  public boolean isArmed(UUID id) {
    return getMotor(id).isArmed();
  }

  @Override
  public void failsafe() throws IOException {
    for (Motor m:motors.values()) {
      m.setPower(0);
    }
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
