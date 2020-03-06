package me.jbuelow.rov.common.response;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import lombok.Getter;

public class Depth extends Response {

  private static final long serialVersionUID = -2850275543679170735L;

  @Getter
  private final Quantity<Length> depth;

  public Depth(Quantity<Length> depth) {
    this.depth = depth;
  }

}
