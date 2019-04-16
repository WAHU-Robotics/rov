package me.jbuelow.rov.wet.service;

import java.io.IOException;
import java.util.UUID;

public interface MotorService {
  void setMotorPower(UUID id, int power) throws IOException;
  
  int getMotorPower(UUID id);
  
  void armMotor(UUID id) throws IOException;
  
  boolean isArmed(UUID id);

  void failsafe() throws IOException;
  
  UUID getMotorByName(String name);
}
