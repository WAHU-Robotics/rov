package me.jbuelow.rov.dry.ui;

import java.awt.Graphics;

public class LiveJoystickXYIndicator extends LiveIndicator {

  public void draw(Graphics g) {
    g.drawLine(0,0,g.getClipBounds().width, g.getClipBounds().height);
  }
}
