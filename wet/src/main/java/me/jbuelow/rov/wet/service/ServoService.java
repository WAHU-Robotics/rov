package me.jbuelow.rov.wet.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import me.jbuelow.rov.common.capabilities.Tool;

public interface ServoService {
  void setServoValue(Tool tool, int power) throws IOException;
  
  int getServoValue(Tool tool);
  
  UUID getServoByName(String name);

  void setValues(Map<Tool, Integer> thrustVectors);
}
