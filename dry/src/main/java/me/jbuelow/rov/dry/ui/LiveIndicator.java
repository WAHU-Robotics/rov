package me.jbuelow.rov.dry.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class LiveIndicator extends JPanel {

  LiveIndicator() {
    // set a preferred size for the custom panel.
    setPreferredSize(new Dimension(128,128));
    setLayout(new BorderLayout());
  }

  public abstract void draw(Graphics g);

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }
}
