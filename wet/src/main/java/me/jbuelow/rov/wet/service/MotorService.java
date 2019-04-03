package me.jbuelow.rov.wet.service;

import java.util.UUID;

public interface MotorService {
  void setMotorPower(UUID id, int power);
  
  int getMotorPower(UUID id);
  
  void armMotor(UUID id);
  
  boolean isArmed(UUID id);
}
