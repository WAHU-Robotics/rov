package me.jbuelow.rov.dry.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import me.jbuelow.rov.dry.controller.PolledValues;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;

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
  private JLabel cameraLabel;
  private JLabel light_indicator;
  private JLabel cup_indicator;

  MediaPlayer player;
  ApplicationContext ctx;

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
      filename = filePrefix + "light_on.png";
    }
    try {
      File file = new ClassPathResource(filename).getFile();
      Image img = getScaledImage(ImageIO.read(file), label.getHeight(), label.getHeight());
      label.setIcon(new ImageIcon(img));
    } catch (IOException | IllegalArgumentException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    //for testing the main gui without starting everything
    Gui gui = new Gui("lol");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ctx = applicationContext;
  }
}
