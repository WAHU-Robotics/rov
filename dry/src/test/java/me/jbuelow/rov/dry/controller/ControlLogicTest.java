package me.jbuelow.rov.dry.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class ControlLogicTest {

  @Test
  public void testComputeDeadzonesZero() {
    PolledValues inVals = new PolledValues(6, -4, 8, 3, 0f);
    Object polledValues = ControlLogic.computeDeadzones(inVals, 10);

    assertThat(polledValues, instanceOf(PolledValues.class));
    PolledValues polledValues1 = (PolledValues) polledValues;
    assertThat(polledValues1.x, equalTo(0));
    assertThat(polledValues1.y, equalTo(0));
    assertThat(polledValues1.z, equalTo(0));
    assertThat(polledValues1.t, equalTo(3));
  }

  @Test
  public void testComputeDeadzonesOut() {
    PolledValues inVals = new PolledValues(100, 200, -200, -100, 0f);
    Object polledValues = ControlLogic.computeDeadzones(inVals, 10);

    assertThat(polledValues, instanceOf(PolledValues.class));
    PolledValues polledValues1 = (PolledValues) polledValues;
    assertThat(polledValues1.x, equalTo(100));
    assertThat(polledValues1.y, equalTo(200));
    assertThat(polledValues1.z, equalTo(-200));
    assertThat(polledValues1.t, equalTo(-100));
  }

}
