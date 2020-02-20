package me.jbuelow.rov.dry.ui.video;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
      g2.drawString("Camera Error", g2.getClipBounds().width/2-g2.getFontMetrics().stringWidth("Camera Error")/2,g2.getClipBounds().height/2+g2.getFontMetrics().getHeight()/4);
    } else {
      g2.drawImage(image, 0, 0, null);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
      drawStatics(g2);
      drawCompass(g2);
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

  private void drawStatics(Graphics2D g2) {
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

  private void drawCompass(Graphics2D g2) {
    float heading = 128.6345f; //TODO get sensor value
    g2.setColor(Color.green);
    int center = image.getWidth()/2;
    g2.setStroke(new BasicStroke(2));
    g2.setColor(Color.red);
    g2.drawLine(center, 5, center, 30);
    g2.setColor(Color.green);
    g2.fillPolygon(new int[]{center, center+25, center-25}, new int[]{30, 40, 40}, 3);
    g2.setFont(new Font("Dialog", Font.PLAIN, 20));
    String headingText = (int)heading+"°";
    int headingTextWidth = g2.getFontMetrics().stringWidth(headingText);
    int degreeWidth = g2.getFontMetrics().charWidth('°');
    g2.drawString(headingText, center-(g2.getFontMetrics().stringWidth(headingText)/2), 60);
    g2.setFont(new Font("Dialog", Font.PLAIN, 14));
    g2.drawString("HDG", center-g2.getFontMetrics().stringWidth("HDG")/2, 75);
    g2.setFont(new Font("Dialog", Font.PLAIN, 10));
    String decimalText = String.valueOf((int)((heading-(int)heading)*10));
    g2.drawString(decimalText, (center+(headingTextWidth/2)-(degreeWidth/2))-g2.getFontMetrics().stringWidth(decimalText)/2+1, 60);
    g2.setStroke(new BasicStroke(2));
    g2.drawRect(center-24, 40, 49, 22);
    g2.setStroke(new BasicStroke(3));
    g2.drawLine(center-270, 5, center+270, 5);
    g2.drawLine(center-270, 5, center-270, 25);
    g2.drawLine(center+270, 5, center+270, 25);

    g2.setFont(new Font("Dialog", Font.PLAIN, 15));

    for (int i = 1; i <= 360; i++) {
      int pixel = (int)(i+90-heading)*3-270;

      if (pixel >= 810) {
        pixel = pixel - 1080;
      }

      if (Math.abs(pixel) > 270) {
        //Out of boundary. don't draw.
        continue;
      }

      int length = 5;
      int width = 1;
      if (i % 90 == 0) {
        String direction;
        switch (i) {
          case 90:
            direction = "E";
            break;
          case 180:
            direction = "S";
            break;
          case 270:
            direction = "W";
            break;
          default:
            direction = "N";
        }
        g2.drawString(direction, center+pixel+3, 25);
        length = 18;
        width = 3;
      } else if (i % 45 == 0) {
        length = 13;
        width = 2;
      } else if (i % 15 == 0) {
        length = 8;
        width = 2;
      }

      g2.setStroke(new BasicStroke(width));
      g2.drawLine(center+pixel, 5, center+pixel, 5+length);
    }
  }

  public static void main(String[] args) {
    VideoPane p = new VideoPane();
    JFrame frame = new JFrame();
    frame.add(p);
    frame.setSize(1296, 759);
    frame.setVisible(true);
    p.update(new BufferedImage(1280, 720, BufferedImage.TYPE_3BYTE_BGR));
  }

}
