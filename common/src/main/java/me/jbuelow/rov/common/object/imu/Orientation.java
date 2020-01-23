package me.jbuelow.rov.common.object.imu;

import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Angle;

public abstract class Orientation {

  public abstract Quantity<Angle> getPitch();
  public abstract Quantity<Angle> getRoll();
  public abstract Quantity<Angle> getYaw();

  public Quantity<Angle> getHeading() {
    return this.getYaw();
  }

}
