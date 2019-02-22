package me.jbuelow.rov.dry.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import me.jbuelow.rov.dry.controller.PolledValues;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class Gui extends JFrame {

  //private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
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

  MediaPlayer player;

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

    //This code is resposible for starting a new vlcj instance
    //Unfortunatly, vlc's low latency capabilities can be described as a pile of dog poo in a garbage can fire with a nuclear weapon going off right next to it
    //As such, this code is not used

    /*
    new NativeDiscovery().discover();
    cameraPane.removeAll();
    cameraPane.setLayout(new BorderLayout());
    mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    cameraPane.add(mediaPlayerComponent, BorderLayout.CENTER);
    player = mediaPlayerComponent.getMediaPlayer();

    player.playMedia(streamURL);

    while (!player.isPlaying()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ignored) {
      }
    }
    player.mute(true);
    */
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

  public void takeScreenshot() {
    BufferedImage img = player.getSnapshot();
    new SnapshotViewer(img);
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    panel1 = new JPanel();
    panel1.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1,
            -1));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(13, 2, new Insets(10, 10, 10, 10), -1,
            -1));
    panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        new Dimension(250, -1), null, null, 1, false));
    panel2
        .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sensors"));
    cpuTempValue = new JLabel();
    Font cpuTempValueFont = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, cpuTempValue.getFont());
    if (cpuTempValueFont != null) {
      cpuTempValue.setFont(cpuTempValueFont);
    }
    cpuTempValue.setText("Label");
    panel2.add(cpuTempValue, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label1 = new JLabel();
    Font label1Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label1.getFont());
    if (label1Font != null) {
      label1.setFont(label1Font);
    }
    label1.setText("CPU Temp:");
    panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label2 = new JLabel();
    Font label2Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label2.getFont());
    if (label2Font != null) {
      label2.setFont(label2Font);
    }
    label2.setText("Water Temp:");
    panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label3 = new JLabel();
    Font label3Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label3.getFont());
    if (label3Font != null) {
      label3.setFont(label3Font);
    }
    label3.setText("JoyA AxisX:");
    panel2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label4 = new JLabel();
    Font label4Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label4.getFont());
    if (label4Font != null) {
      label4.setFont(label4Font);
    }
    label4.setText("JoyA AxisY:");
    panel2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyAAxisXValue = new JLabel();
    Font joyAAxisXValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyAAxisXValue.getFont());
    if (joyAAxisXValueFont != null) {
      joyAAxisXValue.setFont(joyAAxisXValueFont);
    }
    joyAAxisXValue.setText("Label");
    panel2.add(joyAAxisXValue, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    waterTempValue = new JLabel();
    Font waterTempValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, waterTempValue.getFont());
    if (waterTempValueFont != null) {
      waterTempValue.setFont(waterTempValueFont);
    }
    waterTempValue.setText("Label");
    panel2.add(waterTempValue, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyAAxisYValue = new JLabel();
    Font joyAAxisYValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyAAxisYValue.getFont());
    if (joyAAxisYValueFont != null) {
      joyAAxisYValue.setFont(joyAAxisYValueFont);
    }
    joyAAxisYValue.setText("Label");
    panel2.add(joyAAxisYValue, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyAAxisZValue = new JLabel();
    Font joyAAxisZValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyAAxisZValue.getFont());
    if (joyAAxisZValueFont != null) {
      joyAAxisZValue.setFont(joyAAxisZValueFont);
    }
    joyAAxisZValue.setText("Label");
    panel2.add(joyAAxisZValue, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label5 = new JLabel();
    Font label5Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label5.getFont());
    if (label5Font != null) {
      label5.setFont(label5Font);
    }
    label5.setText("JoyA AxisZ:");
    panel2.add(label5, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyAAxisTValue = new JLabel();
    Font joyAAxisTValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyAAxisTValue.getFont());
    if (joyAAxisTValueFont != null) {
      joyAAxisTValue.setFont(joyAAxisTValueFont);
    }
    joyAAxisTValue.setText("Label");
    panel2.add(joyAAxisTValue, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label6 = new JLabel();
    Font label6Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label6.getFont());
    if (label6Font != null) {
      label6.setFont(label6Font);
    }
    label6.setText("JoyA AxisT:");
    panel2.add(label6, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyAHatXValue = new JLabel();
    Font joyAHatXValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyAHatXValue.getFont());
    if (joyAHatXValueFont != null) {
      joyAHatXValue.setFont(joyAHatXValueFont);
    }
    joyAHatXValue.setText("Label");
    panel2.add(joyAHatXValue, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label7 = new JLabel();
    Font label7Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label7.getFont());
    if (label7Font != null) {
      label7.setFont(label7Font);
    }
    label7.setText("JoyA Hat:");
    panel2.add(label7, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label8 = new JLabel();
    Font label8Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label8.getFont());
    if (label8Font != null) {
      label8.setFont(label8Font);
    }
    label8.setText("JoyB AxisX:");
    panel2.add(label8, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label9 = new JLabel();
    Font label9Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label9.getFont());
    if (label9Font != null) {
      label9.setFont(label9Font);
    }
    label9.setText("JoyB AxisY:");
    panel2.add(label9, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyBAxisXValue = new JLabel();
    Font joyBAxisXValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyBAxisXValue.getFont());
    if (joyBAxisXValueFont != null) {
      joyBAxisXValue.setFont(joyBAxisXValueFont);
    }
    joyBAxisXValue.setText("Label");
    panel2.add(joyBAxisXValue, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyBAxisYValue = new JLabel();
    Font joyBAxisYValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyBAxisYValue.getFont());
    if (joyBAxisYValueFont != null) {
      joyBAxisYValue.setFont(joyBAxisYValueFont);
    }
    joyBAxisYValue.setText("Label");
    panel2.add(joyBAxisYValue, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyBAxisZValue = new JLabel();
    Font joyBAxisZValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyBAxisZValue.getFont());
    if (joyBAxisZValueFont != null) {
      joyBAxisZValue.setFont(joyBAxisZValueFont);
    }
    joyBAxisZValue.setText("Label");
    panel2.add(joyBAxisZValue, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label10 = new JLabel();
    Font label10Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label10.getFont());
    if (label10Font != null) {
      label10.setFont(label10Font);
    }
    label10.setText("JoyB AxisZ:");
    panel2.add(label10, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyBAxisTValue = new JLabel();
    Font joyBAxisTValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyBAxisTValue.getFont());
    if (joyBAxisTValueFont != null) {
      joyBAxisTValue.setFont(joyBAxisTValueFont);
    }
    joyBAxisTValue.setText("Label");
    panel2.add(joyBAxisTValue, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label11 = new JLabel();
    Font label11Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label11.getFont());
    if (label11Font != null) {
      label11.setFont(label11Font);
    }
    label11.setText("JoyB AxisT:");
    panel2.add(label11, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    joyBHatXValue = new JLabel();
    Font joyBHatXValueFont = this
        .$$$getFont$$$("Fira Code", Font.BOLD, 20, joyBHatXValue.getFont());
    if (joyBHatXValueFont != null) {
      joyBHatXValue.setFont(joyBHatXValueFont);
    }
    joyBHatXValue.setText("Label");
    panel2.add(joyBHatXValue, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label12 = new JLabel();
    Font label12Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label12.getFont());
    if (label12Font != null) {
      label12.setFont(label12Font);
    }
    label12.setText("JoyB Hat:");
    panel2.add(label12, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    final JLabel label13 = new JLabel();
    Font label13Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, label13.getFont());
    if (label13Font != null) {
      label13.setFont(label13Font);
    }
    label13.setText("FPS:");
    panel2.add(label13, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    fpsValue = new JLabel();
    Font fpsValueFont = this.$$$getFont$$$("Fira Code", Font.BOLD, 20, fpsValue.getFont());
    if (fpsValueFont != null) {
      fpsValue.setFont(fpsValueFont);
    }
    fpsValue.setText("Label");
    panel2.add(fpsValue, new com.intellij.uiDesigner.core.GridConstraints(12, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
        0, false));
    mainPanel = new JPanel();
    mainPanel.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(mainPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
        false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    mainPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    panel3.setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Other Controls"));
    cameraPane = new JPanel();
    cameraPane.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
    mainPanel.add(cameraPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
        false));
    cameraPane
        .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Camera"));
    final JLabel label14 = new JLabel();
    label14.setEnabled(true);
    label14.setHorizontalAlignment(0);
    label14.setHorizontalTextPosition(0);
    label14.setText("");
    cameraPane.add(label14, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
    cameraPane.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0,
        false));
    final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
    cameraPane.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
  }

  /**
   * @noinspection ALL
   */
  private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
    if (currentFont == null) {
      return null;
    }
    String resultName;
    if (fontName == null) {
      resultName = currentFont.getName();
    } else {
      Font testFont = new Font(fontName, Font.PLAIN, 10);
      if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
        resultName = fontName;
      } else {
        resultName = currentFont.getName();
      }
    }
    return new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
        size >= 0 ? size : currentFont.getSize());
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return panel1;
  }
}
