package me.jbuelow.rov.dry.service.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        int i = JOptionPane.showConfirmDialog(null, "Close ROV Topside control application?");
        if (i == 0) {
          System.exit(0); //commit self deletus
        }
      }
    });
    setVisible(true);
  }

  public void setLabel(String message) {
    this.CPUTempValue.setText(message);
  }
}
