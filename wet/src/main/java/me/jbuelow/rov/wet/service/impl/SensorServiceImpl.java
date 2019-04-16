package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.service.SensorService;
import me.jbuelow.rov.wet.vehicle.hardware.temp.TempDevice;
import org.springframework.stereotype.Service;

/**
 * Handles servos as a service.
 */
@Service
@Slf4j
public class SensorServiceImpl implements SensorService {

  private final TempDevice tempDevice;

  private SensorServiceImpl(TempDevice tempDevice) {
    this.tempDevice = tempDevice;
  }

  @Override
  public float getTemp() {
    try {
      return tempDevice.getTemp();
    } catch (IOException e) {
      return -420f;
    }
  }
}
