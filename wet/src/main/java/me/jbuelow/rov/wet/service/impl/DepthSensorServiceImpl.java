package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import me.jbuelow.rov.wet.service.DepthSensorService;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.DepthDevice;
import org.springframework.stereotype.Service;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

@Service
public class DepthSensorServiceImpl implements DepthSensorService {

  private final DepthDevice depthDevice;

  public DepthSensorServiceImpl(
      DepthDevice depthDevice) {
    this.depthDevice = depthDevice;
  }

  @Override
  public Quantity<Length> getDepth() {
    try {
      return depthDevice.getDepth();
    } catch (IOException e) {
      return Quantities.getQuantity(0, Units.METRE);
    }
  }

}
