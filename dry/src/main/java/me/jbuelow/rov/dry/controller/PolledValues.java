package me.jbuelow.rov.dry.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;

/**
 * Object for transfer and mapping of input values
 */
public class PolledValues {

  public int x = 0;
  public int y = 0;
  public int z = 0;
  public int t = 0;
  public float hat = 0;
  public String hatS = "REEE";

  public boolean[] buttons;

  private final String[] directions = {"\uD83E\uDC84", "\uD83E\uDC81", "\uD83E\uDC85",
      "\uD83E\uDC82", "\uD83E\uDC86", "\uD83E\uDC83", "\uD83E\uDC87", "\uD83E\uDC80", "⚫"};

  public PolledValues(Controller controller) {

    int joyPrecision = 1000;
    if (Objects.equals(controller.getName(), "False Controller")) {
      x = new Random().nextInt(joyPrecision);
      return;
    }
    try {
      x = (int) (controller.getComponent(Axis.X).getPollData() * joyPrecision);
    } catch (NullPointerException ignored) {
    }
    try {
      y = (int) (controller.getComponent(Axis.Y).getPollData() * joyPrecision);
    } catch (NullPointerException ignored) {
    }
    try {
      z = (int) (controller.getComponent(Axis.RZ).getPollData() * joyPrecision);
    } catch (NullPointerException ignored) {
    }
    try {
      t = (int) (controller.getComponent(Axis.SLIDER).getPollData() * joyPrecision);
    } catch (NullPointerException ignored) {
    }
    try {
      hat = controller.getComponent(Axis.POV).getPollData();
    } catch (NullPointerException ignored) {
    }
    resolveHatPosition();

    Component[] components = controller.getComponents();
    List<Component> buttonList = new ArrayList<>();
    for (Component comp : components) {
      if (comp.getIdentifier().getClass() == Identifier.Button.class) {
        buttonList.add(comp);
      }
    }

    buttons = new boolean[buttonList.size()];
    for (Component comp : buttonList) {
      buttons[buttonList.indexOf(comp)] = (comp.getPollData() == 1f);
    }
  }

  public PolledValues(int x, int y, int z, int t, float hat) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.t = t;
    this.hat = hat;
    resolveHatPosition();
  }

  private void resolveHatPosition() {
    float v = hat * 8;
    if (v != 0f) {
      hatS = directions[(int) v - 1];
    } else {
      hatS = directions[8];
    }
  }

  public int[] getArray() {
    return new int[]{x, y, z};
  }
}
