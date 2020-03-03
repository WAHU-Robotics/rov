package me.jbuelow.rov.dry.ui.video;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;

public class VideoPane extends JPanel {

  private BufferedImage image;
  private final ExecutorService worker = Executors.newSingleThreadExecutor();

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    if (image == null) {
      //LOS is drawn here
      //TODO make LOS screen
    } else {
      g2.drawImage(image, 0, 0, null);
    }
  }

  public void update(BufferedImage newImg) {
    worker.execute(new Runnable() {
      @Override
      public void run() {
        image = newImg;
        repaint();
      }
    });
  }

  public VideoFrameReceiver getFrameReceiver() {
    return new VideoFrameReceiver() {
      @Override
      public void newFrame(BufferedImage frame) {
        update(frame);
      }
    };
  }

}
