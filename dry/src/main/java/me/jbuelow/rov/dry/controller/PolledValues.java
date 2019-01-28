package me.jbuelow.rov.dry.controller;

import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;

public class PolledValues {

  public int x;
  public int y;
  public int z;
  public int t;
  public float hat;
  public String hatS;

  private String[] directions = {"NW", "N", "NE", "E", "SE", "S", "SW", "W"};
  private int joyPrecision = 10000;

  public PolledValues(Controller controller) {
    Controller c = controller;
    x = (int) (c.getComponent(Axis.X).getPollData() * joyPrecision);
    y = (int) (c.getComponent(Axis.Y).getPollData() * joyPrecision);
    z = (int) (c.getComponent(Axis.RZ).getPollData() * joyPrecision);
    t = (int) (c.getComponent(Axis.SLIDER).getPollData() * joyPrecision);
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
