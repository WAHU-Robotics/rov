package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.unit.SI;
import me.jbuelow.rov.wet.service.DepthSensorService;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.DepthDevice;
import org.springframework.stereotype.Service;

@Service
public class DepthSensorServiceImpl implements DepthSensorService {

  private final DepthDevice depthDevice;

  public DepthSensorServiceImpl(
      DepthDevice depthDevice) {
    this.depthDevice = depthDevice;
  }

  @Override
  public Measurable<Length> getDepth() {
    try {
      return depthDevice.getDepth();
    } catch (IOException e) {
      return Measure.valueOf(0, SI.METER);
    }
  }

}
