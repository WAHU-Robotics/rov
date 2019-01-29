package me.jbuelow.rov.dry.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import me.jbuelow.rov.dry.controller.PolledValues;
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
  private JLabel joyBAxisXValue;
  private JLabel joyBAxisYValue;
  private JLabel joyBAxisZValue;
  private JLabel joyBAxisTValue;
  private JLabel joyBHatXValue;
  private JLabel waterTempValue;
  private JPanel cameraPane;
  private JPanel mainPanel;
  private JLabel fpsValue;

  public Gui(String streamURL) {
    try {
      add(panel1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        int i = JOptionPane
            .showConfirmDialog(null, "Close application?", "", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
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

    while (!mediaPlayerComponent.getMediaPlayer().isPlaying()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ignored) {
      }
    }
    mediaPlayerComponent.getMediaPlayer().mute(true);
  }

  public void setCpuTempValue(Object text) {
    this.cpuTempValue.setText(String.valueOf(text));
  }

  public void setWaterTempValue(Object text) {
    this.waterTempValue.setText(String.valueOf(text));
  }

  public void setJoyA(PolledValues values) {
    this.joyAAxisXValue.setText(String.valueOf(values.x));
    this.joyAAxisYValue.setText(String.valueOf(values.y));
    this.joyAAxisZValue.setText(String.valueOf(values.z));
    this.joyAAxisTValue.setText(String.valueOf(values.t));
    this.joyAHatXValue.setText(String.valueOf(values.hatS));
  }

  public void setJoyB(PolledValues values) {
    this.joyBAxisXValue.setText(String.valueOf(values.x));
    this.joyBAxisYValue.setText(String.valueOf(values.y));
    this.joyBAxisZValue.setText(String.valueOf(values.z));
    this.joyBAxisTValue.setText(String.valueOf(values.t));
    this.joyBHatXValue.setText(String.valueOf(values.hatS));
  }

  public void setFps(Object fps) {
    this.fpsValue.setText(String.valueOf(fps));
  }
}
