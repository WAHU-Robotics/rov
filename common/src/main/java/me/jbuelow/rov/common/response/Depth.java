package me.jbuelow.rov.common.response;

import javax.measure.Measurable;
import javax.measure.quantity.Length;
import lombok.Getter;

public class Depth extends Response {

  private static final long serialVersionUID = -2850275543679170735L;

  @Getter
  private final Measurable<Length> depth;

  public Depth(Measurable<Length> depth) {
    this.depth = depth;
  }

}
