package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.service.SensorService;
import me.jbuelow.rov.wet.vehicle.hardware.temp.TempDevice;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Handles servos as a service.
 */
@Service
@Slf4j
public class SensorServiceImpl implements SensorService {
  private TempDevice tempDevice;

  public SensorServiceImpl(TempDevice tempDevice) {
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
