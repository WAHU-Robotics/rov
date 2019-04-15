package me.jbuelow.rov.wet.vehicle.hardware.temp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TSYS01Test {

  @Test
  public void calculateTemperature() {
    List<Integer> k = new ArrayList<>();

    k.add(40781);
    k.add(32791);
    k.add(36016);
    k.add(24926);
    k.add(28446);

    float result = TSYS01.calculateTemperature(9378708, k);
    assertThat(result, is(10.577273f));
  }
}