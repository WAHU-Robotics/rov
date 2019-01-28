package me.jbuelow.rov.dry.controller;

import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;

public class PolledValues {

  public float x;
  public float y;
  public float z;
  public float t;
  public float hat;
  public String hatS;

  private String[] directions = {"NW", "N", "NE", "E", "SE", "S", "SW", "W"};

  public PolledValues(Controller controller) {
    Controller c = controller;
    x = c.getComponent(Axis.X).getPollData();
    y = c.getComponent(Axis.Y).getPollData();
    z = c.getComponent(Axis.RZ).getPollData();
    t = c.getComponent(Axis.SLIDER).getPollData();
    hat = c.getComponent(Axis.POV).getPollData();
    resolveHatPosition();
  }

  private void resolveHatPosition() {
    float v = hat * 8;
    if (v != 0f) {
      hatS = directions[(int) v - 1];
    } else {
      hatS = "YEET";
    }
  }
}
