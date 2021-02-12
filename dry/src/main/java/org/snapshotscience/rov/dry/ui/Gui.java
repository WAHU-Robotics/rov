package org.snapshotscience.rov.dry.ui;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.common.capabilities.ThrustAxis;
import org.snapshotscience.rov.common.command.SetMotion;
import org.snapshotscience.rov.dry.controller.PolledValues;
import org.snapshotscience.rov.dry.service.VehicleControlService;
import org.snapshotscience.rov.dry.ui.utility.calculator.cannon.CannonVolumeCalculator;
import org.snapshotscience.rov.dry.ui.video.VideoPane;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * Main graphical user interface for rov software
 */
@Slf4j
public class Gui extends JFrame implements ApplicationContextAware {

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
  private JLabel magnet_indicator;
  private JLabel light_indicator;
  private JLabel cup_indicator;
  private JPanel sensorsPanel;
  private JLabel gripper_indicator;
  private JSlider sliderJoyAX;
  private JSlider sliderJoyAT;
  private JSlider sliderJoyAY;
  private JSlider sliderJoyAZ;
  private JSlider sliderJoyBX;
  private JSlider sliderJoyBT;
  private JSlider sliderJoyBY;
  private JSlider sliderJoyBZ;
  private JLabel Motion;
  private JSlider sliderSurge;
  private JSlider sliderSway;
  private JSlider sliderHeave;
  private JSlider sliderYaw;
  private JSlider sliderRoll;
  private JSlider sliderPitch;
  private JButton calcButton;
  @Getter
  private VideoPane videoPane;

  MediaPlayer player;
  ApplicationContext ctx;
  private final VehicleControlService vehicleControlService;

  public Gui(String streamURL, VehicleControlService vehicleControlService) {
    this.vehicleControlService = vehicleControlService;
    try {
      add(panel1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        CloseApplicationConfirmation.requestExit(vehicleControlService);
      }
    });

    //LiveJoystickXYIndicator xyIndc = new LiveJoystickXYIndicator();
    //sensorsPanel.add(xyIndc);

    calcButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new CannonVolumeCalculator();
      }
    });

    setVisible(true);
    setTitle("ROV View");

    setMagnetState(false);
    setLightState(false);
    setCupState(false);

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
    this.videoPane = new VideoPane();
    cameraPane.add(videoPane, BorderLayout.CENTER);
  }

  private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();

    return resizedImg;
  }

  public void setCpuTempValue(Object text) {
    this.cpuTempValue.setText(String.valueOf(text));
  }

  public void setCpuTempBadness(int badness) {
    switch (badness) {
      case 2:
        this.cpuTempValue.setForeground(new Color(255,0,0));
        break;

      case 1:
        this.cpuTempValue.setForeground(new Color(255, 255,0));
        break;

      default:
        this.cpuTempValue.setForeground(new Color(189, 189,189));
        break;
    }
  }

  public void setWaterTempValue(Object text) {
    this.waterTempValue.setText(String.valueOf(text));
  }

  public void setJoyA(PolledValues values) {
    this.joyAHatXValue.setText(String.valueOf(values.hatS));

    this.sliderJoyAX.setValue(values.x);
    this.sliderJoyAY.setValue(values.y);
    this.sliderJoyAZ.setValue(values.z);
    this.sliderJoyAT.setValue(values.t);
  }

  public void setJoyB(PolledValues values) {
    this.joyBHatXValue.setText(String.valueOf(values.hatS));

    this.sliderJoyBX.setValue(values.x);
    this.sliderJoyBY.setValue(values.y);
    this.sliderJoyBZ.setValue(values.z);
    this.sliderJoyBT.setValue(values.t);
  }

  public void setMotionVector(SetMotion motionVector) {
    try {
      Map<ThrustAxis, Integer> vectors = motionVector.getThrustVectors();
      this.sliderSurge.setValue(vectors.get(ThrustAxis.SURGE));
      this.sliderSway.setValue(vectors.get(ThrustAxis.SWAY));
      this.sliderHeave.setValue(vectors.get(ThrustAxis.HEAVE));
      this.sliderYaw.setValue(vectors.get(ThrustAxis.YAW));
      this.sliderRoll.setValue(vectors.get(ThrustAxis.ROLL));
      this.sliderPitch.setValue(vectors.get(ThrustAxis.PITCH));
    } catch (NullPointerException e) {
      // don't even bother flooding console with this error
    } catch (Exception e) {
      // Not a critical function. eat any errors
      log.debug("Ate exception:", e);
    }
  }

  public void setFps(Object fps) {
    this.fpsValue.setText(String.valueOf(fps));
  }

  public void takeScreenshot() {
    BufferedImage img = player.getSnapshot();
    new SnapshotViewer(img);
  }

  public void setGripperState(Boolean state) {
    setIndicatorState(state, "gripper", gripper_indicator);
  }

  public void setMagnetState(Boolean state) {
    setIndicatorState(state, "magnet", magnet_indicator);
  }

  public void setLightState(Boolean state) {
    setIndicatorState(state, "light", light_indicator);
  }

  public void setCupState(Boolean state) {
    setIndicatorState(state, "cup", cup_indicator);
  }

  private void setIndicatorState(Boolean state, String filePrefix, JLabel label) {
    String filename = filePrefix + "_off.png";
    if (state) {
      filename = filePrefix + "_on.png";
    }
    try {
      File file = new ClassPathResource(filename).getFile();
      Image img = getScaledImage(ImageIO.read(file), label.getHeight(), label.getHeight());
      label.setIcon(new ImageIcon(img));
    } catch (IOException | IllegalArgumentException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ctx = applicationContext;
  }

  public BufferedImage getVideoFrame() {
    return this.videoPane.getImage();
  }

  public void updateVideoFrame(BufferedImage image) {
    this.videoPane.update(image);
  }
}
