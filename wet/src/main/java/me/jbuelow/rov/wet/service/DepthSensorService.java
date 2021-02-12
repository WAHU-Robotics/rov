package me.jbuelow.rov.wet.service;

import javax.measure.Measurable;
import javax.measure.quantity.Length;

public interface DepthSensorService {

  Measurable<Length> getDepth();

}
