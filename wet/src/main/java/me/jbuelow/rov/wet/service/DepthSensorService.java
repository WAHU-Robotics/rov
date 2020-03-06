package me.jbuelow.rov.wet.service;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public interface DepthSensorService {

  Quantity<Length> getDepth();

}
