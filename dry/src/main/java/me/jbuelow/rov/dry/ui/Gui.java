package me.jbuelow.rov.dry.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Gui extends JFrame {

  private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
  private JPanel panel1;
  private JLabel cpuTempValue;
  private JLabel joyAAxisXValue;
  private JLabel joyAAxisYValue;
  private JLabel joyAAxisZValue;
  private JLabel joyAAxisTValue;
  private JLabel joyAHatXValue;
  private JLabel joyAHatYValue;
  private JLabel joyBAxisXValue;
  private JLabel joyBAxisYValue;
  private JLabel joyBAxisZValue;
  private JLabel joyBAxisTValue;
  private JLabel joyBHatXValue;
  private JLabel joyBHatYValue;
  private JLabel waterTempValue;
  private JPanel cameraPane;
  private JPanel mainPanel;

  public Gui(String streamURL) {
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

    new NativeDiscovery().discover();
    cameraPane.removeAll();
    cameraPane.setLayout(new BorderLayout());
    mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    cameraPane.add(mediaPlayerComponent, BorderLayout.CENTER);

    mediaPlayerComponent.getMediaPlayer()
        .playMedia(streamURL);
  }

  public void setCpuTempValue(Object text) {
    this.cpuTempValue.setText(String.valueOf(text));
  }

  public void setWaterTempValue(Object text) {
    this.waterTempValue.setText(String.valueOf(text));
  }

  public void setJoyA(Object x, Object y, Object z, Object t, Object hx, Object hy) {
    this.joyAAxisXValue.setText(String.valueOf(x));
    this.joyAAxisYValue.setText(String.valueOf(y));
    this.joyAAxisZValue.setText(String.valueOf(z));
    this.joyAAxisTValue.setText(String.valueOf(t));
    this.joyAHatXValue.setText(String.valueOf(hx));
    this.joyAHatYValue.setText(String.valueOf(hy));
  }

  public void setJoyB(Object x, Object y, Object z, Object t, Object hx, Object hy) {
    this.joyBAxisXValue.setText(String.valueOf(x));
    this.joyBAxisYValue.setText(String.valueOf(y));
    this.joyBAxisZValue.setText(String.valueOf(z));
    this.joyBAxisTValue.setText(String.valueOf(t));
    this.joyBHatXValue.setText(String.valueOf(hx));
    this.joyBHatYValue.setText(String.valueOf(hy));
  }
}
