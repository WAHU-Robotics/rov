package me.jbuelow.rov.dry.service.impl;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui extends JFrame {

  private JPanel panel1;
  private JLabel label1;

  public Gui() {
    add(panel1);
    setSize(200, 100);
    setVisible(true);
  }

  public void setLabel(String message) {
    this.label1.setText(message);
  }
}
