package org.snapshotscience.rov.dry.ui.video;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoPane extends JPanel {

  @Getter
  private BufferedImage image;
  private final ExecutorService worker = Executors.newSingleThreadExecutor();

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    if (image == null) {
      //LOS is drawn here
      //TODO make LOS screen
    } else {
      //scale image
      double widthRatio = (double) getSize().width / image.getWidth();
      double heightRatio = (double) getSize().height / image.getHeight();
      Image scaled;
      if (widthRatio > heightRatio) {
        int width = (int) (image.getWidth() * ((double) getSize().height / image.getHeight()));
        scaled = image.getScaledInstance(width, getSize().height, BufferedImage.TYPE_INT_ARGB);
      } else {
        int height = (int) (image.getHeight() * ((double) getSize().width / image.getWidth()));
        scaled = image.getScaledInstance(getSize().width, height, BufferedImage.TYPE_INT_ARGB);
      }

      g2.drawImage(scaled, 0, 0, null);
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
