package me.jbuelow.rov.dry.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class ControlLogicTest {

  @Test
  public void testComputeDeadzonesZero() {
    PolledValues inVals = new PolledValues(6, -4, 8, 3, 0f);
    PolledValues polledValues = ControlLogic.computeDeadzones(inVals, 10);

    assertThat(polledValues, instanceOf(PolledValues.class));
    assertThat(polledValues.x, equalTo(0));
    assertThat(polledValues.y, equalTo(0));
    assertThat(polledValues.z, equalTo(0));
    assertThat(polledValues.t, equalTo(3));
  }

  @Test
  public void testComputeDeadzonesOut() {
    PolledValues inVals = new PolledValues(100, 200, -200, -100, 0f);
    PolledValues polledValues = ControlLogic.computeDeadzones(inVals, 10);

    assertThat(polledValues, instanceOf(PolledValues.class));
    assertThat(polledValues.x, equalTo(100));
    assertThat(polledValues.y, equalTo(200));
    assertThat(polledValues.z, equalTo(-200));
    assertThat(polledValues.t, equalTo(-100));
  }

}
