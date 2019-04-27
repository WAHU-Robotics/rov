package me.jbuelow.rov.dry.ui.utility.calculator.cannon;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.internal.matchers.InstanceOf;

public class CannonVolumeMathTest {

  @Test
  public void testCalculate() {
    double length = 46;
    double r1 = 5.3;
    double r2 = 2.8;
    double r3 = 7.7;

    Object result = CannonVolumeMath.calculate(r1, r2, r3, length);

    assertThat(result, instanceOf(Double.class));
    Double resultD = (Double) result;
    assertThat(resultD, equalTo(5042.067713452405));
  }
}