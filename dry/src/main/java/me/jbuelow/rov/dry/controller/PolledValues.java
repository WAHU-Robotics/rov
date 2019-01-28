package me.jbuelow.rov.dry.controller;

import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;

public class PolledValues {

  public float x;
  public float y;
  public float z;
  public float t;
  public float hx;
  public float hy;

  public PolledValues(Controller controller) {
    Controller c = controller;
    x = c.getComponent(Axis.X).getPollData();
    y = c.getComponent(Axis.Y).getPollData();
    //z = c.getComponent(Axis.Z).getPollData();
    t = c.getComponent(Axis.SLIDER).getPollData();
    hx = c.getComponent(Axis.POV).getPollData();
  }
}
