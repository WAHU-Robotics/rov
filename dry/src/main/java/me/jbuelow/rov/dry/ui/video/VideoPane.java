package me.jbuelow.rov.dry.ui.video;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.border.StrokeBorder;
import lombok.Getter;

public class VideoPane extends JPanel {

  @Getter
  private BufferedImage image;
  private final ExecutorService worker = Executors.newSingleThreadExecutor();

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    if (image == null) {
      Font font = new Font("Dialog", Font.BOLD, 100);
      g2.setFont(font);
      g2.setColor(Color.RED);
      g2.drawString("Camera Error", 50,100);
    } else {
      g2.drawImage(image, 0, 0, null);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(Color.green);
      g2.setStroke(new BasicStroke(3));
      g2.drawArc((image.getWidth()/2)-200, (image.getHeight()/2)-200, 400, 400, 60, -120);
      g2.drawArc((image.getWidth()/2)-200, (image.getHeight()/2)-200, 400, 400, 120, 120);
      g2.drawLine(image.getWidth()/2+200, image.getHeight()/2, image.getWidth()/2+210, image.getHeight()/2);
      g2.drawLine(image.getWidth()/2-200, image.getHeight()/2, image.getWidth()/2-210, image.getHeight()/2);
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 60), getCircleY(image.getHeight()/2, 200, 60),
          getCircleX(image.getWidth()/2, 210, 60), getCircleY(image.getHeight()/2, 210, 60));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -60), getCircleY(image.getHeight()/2, 200, -60),
          getCircleX(image.getWidth()/2, 210, -60), getCircleY(image.getHeight()/2, 210, -60));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 120), getCircleY(image.getHeight()/2, 200, 120),
          getCircleX(image.getWidth()/2, 210, 120), getCircleY(image.getHeight()/2, 210, 120));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -120), getCircleY(image.getHeight()/2, 200, -120),
          getCircleX(image.getWidth()/2, 210, -120), getCircleY(image.getHeight()/2, 210, -120));
      g2.setStroke(new BasicStroke(2));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 30), getCircleY(image.getHeight()/2, 200, 30),
          getCircleX(image.getWidth()/2, 208, 30), getCircleY(image.getHeight()/2, 208, 30));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -30), getCircleY(image.getHeight()/2, 200, -30),
          getCircleX(image.getWidth()/2, 208, -30), getCircleY(image.getHeight()/2, 208, -30));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 150), getCircleY(image.getHeight()/2, 200, 150),
          getCircleX(image.getWidth()/2, 208, 150), getCircleY(image.getHeight()/2, 208, 150));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -150), getCircleY(image.getHeight()/2, 200, -150),
          getCircleX(image.getWidth()/2, 208, -150), getCircleY(image.getHeight()/2, 208, -150));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 15), getCircleY(image.getHeight()/2, 200, 15),
          getCircleX(image.getWidth()/2, 205, 15), getCircleY(image.getHeight()/2, 205, 15));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -15), getCircleY(image.getHeight()/2, 200, -15),
          getCircleX(image.getWidth()/2, 205, -15), getCircleY(image.getHeight()/2, 205, -15));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 45), getCircleY(image.getHeight()/2, 200, 45),
          getCircleX(image.getWidth()/2, 205, 45), getCircleY(image.getHeight()/2, 205, 45));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -45), getCircleY(image.getHeight()/2, 200, -45),
          getCircleX(image.getWidth()/2, 205, -45), getCircleY(image.getHeight()/2, 205, -45));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 165), getCircleY(image.getHeight()/2, 200, 165),
          getCircleX(image.getWidth()/2, 205, 165), getCircleY(image.getHeight()/2, 205, 165));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -165), getCircleY(image.getHeight()/2, 200, -165),
          getCircleX(image.getWidth()/2, 205, -165), getCircleY(image.getHeight()/2, 205, -165));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, 135), getCircleY(image.getHeight()/2, 200, 135),
          getCircleX(image.getWidth()/2, 205, 135), getCircleY(image.getHeight()/2, 205, 135));
      g2.drawLine(getCircleX(image.getWidth()/2, 200, -135), getCircleY(image.getHeight()/2, 200, -135),
          getCircleX(image.getWidth()/2, 205, -135), getCircleY(image.getHeight()/2, 205, -135));
      g2.drawLine((image.getWidth()/2)-50, image.getHeight()/2, (image.getWidth()/2)-25, image.getHeight()/2);
      g2.drawLine((image.getWidth()/2)+50, image.getHeight()/2, (image.getWidth()/2)+25, image.getHeight()/2);
      g2.drawLine((image.getWidth()/2)-10, (image.getHeight()/2)+10, image.getWidth()/2, image.getHeight()/2);
      g2.drawLine((image.getWidth()/2)+10, (image.getHeight()/2)+10, image.getWidth()/2, image.getHeight()/2);

    }

  }

  private int getCircleX(int x, int r, int a) {
    double da = a * Math.PI / 180;
    return (int) (Math.cos(da) * r + x);
  }

  private int getCircleY(int y, int r, int a) {
    double da = a * Math.PI / 180;
    return (int) (Math.sin(da) * r + y);
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

}
