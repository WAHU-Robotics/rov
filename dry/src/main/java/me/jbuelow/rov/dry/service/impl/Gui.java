package me.jbuelow.rov.dry.service.impl;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui extends JFrame {

  private JPanel panel1;
  private JLabel CPUTempValue;
  private JLabel joyAAxisXValue;
  private JLabel JoyAAxisYValue;
  private JLabel JoyAAxisZValue;
  private JLabel JoyAAxisTValue;
  private JLabel JoyAHatXValue;
  private JLabel JoyAHatYValue;
  private JLabel JoyBAxisXValue;
  private JLabel JoyBAxisYValue;
  private JLabel JoyBAxisZValue;
  private JLabel JoyBAxisTValue;
  private JLabel JoyBHatXValue;
  private JLabel JoyBHatYValue;
  private JLabel WaterTempValue;

  public Gui() {
    add(panel1);
    setSize(1920, 1080);
    setVisible(true);
  }

  public void setLabel(String message) {
    this.CPUTempValue.setText(message);
  }
}
