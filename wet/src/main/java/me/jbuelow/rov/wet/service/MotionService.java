package me.jbuelow.rov.wet.service;

import java.util.Map;
import me.jbuelow.rov.common.capabilities.ThrustAxis;

public interface MotionService {
  
  void setMotion(Map<ThrustAxis, Integer> thrustVectors);
}
