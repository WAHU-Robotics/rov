package me.jbuelow.rov.dry.ui.video;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VideoPane extends JPanel {

  @Getter
  private BufferedImage image;
  private final ExecutorService worker = Executors.newSingleThreadExecutor();

  @Getter
  private double lastFrameTime = 0;

  public BufferedImage getScreencap() {
    BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
    this.paintComponent(img.createGraphics());
    return img;
  }

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
      g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
          RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      BufferedImage horizon = drawArtificialHorizon();
      g2.drawImage(horizon, image.getWidth()/2-horizon.getWidth()/2, image.getHeight()/2-horizon.getHeight()/2, null);
      BufferedImage compass = drawCompass();
      g2.drawImage(compass, 0, 0, null);
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
        long startTime = System.nanoTime();
        repaint();
        long endTime = System.nanoTime();
        lastFrameTime = (double)(endTime-startTime)/1000000;
        log.trace("Compositor took {} ms to overlay frame.", lastFrameTime);
      }
    });
  }

  private BufferedImage drawArtificialHorizon() {
    BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2 = out.createGraphics();
    BufferedImage bi = new BufferedImage(720, 720, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = bi.createGraphics();
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    //Set render hints for anitaliasing and interpolation
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    //Set color
    g.setColor(Color.GREEN);

    //Get inclination
    float inclination = 16f;

    //Draw lines
    int vCtr = bi.getHeight()/2;
    int hCtr = bi.getWidth()/2;
    for (int i = -180; i <= 180; i++) {
      int pixel = (int)(i-45-inclination)*8+360;

      /*if (pixel >= 810) {
        pixel = pixel - 1080;
      }*/

      if (Math.abs(pixel) > 360) {
        //Out of boundary. don't draw.
        continue;
      }

      int lineWidth = 0;

      if (i % 10 == 0) {
        lineWidth = 250;
        g.setStroke(new BasicStroke(2));
        g.drawString(String.valueOf(i), hCtr+125, vCtr-pixel-2);
      } else if (i % 5 == 0) {
        lineWidth = 75;
        g.setStroke(new BasicStroke(1));
      } else {
        lineWidth = 10;
        g.setStroke(new BasicStroke(1));
      }

      g.drawLine(hCtr-lineWidth/2, vCtr-pixel, hCtr+lineWidth/2, vCtr-pixel);
    }

    g2.setColor(Color.GREEN);

    //Draw inclination readout
    g2.setFont(new Font("Dialog", Font.PLAIN, 20));
    String headingText = (int)inclination+"°";
    int headingTextWidth = g2.getFontMetrics().stringWidth(headingText);
    int degreeWidth = g2.getFontMetrics().charWidth('°');
    g2.drawString(headingText, image.getWidth()/2-(g2.getFontMetrics().stringWidth(headingText)/2)+277, image.getHeight()/2+9);
    g2.setFont(new Font("Dialog", Font.PLAIN, 14));
    g2.drawString("INCL", image.getWidth()/2+277-g2.getFontMetrics().stringWidth("INCL")/2, image.getHeight()/2+24);
    g2.setFont(new Font("Dialog", Font.PLAIN, 10));
    String decimalText = String.valueOf((int)((inclination-(int)inclination)*10));
    g2.drawString(decimalText, (image.getWidth()/2+277+(headingTextWidth/2)-(degreeWidth/2))-g2.getFontMetrics().stringWidth(decimalText)/2, image.getHeight()/2+9);
    g2.setStroke(new BasicStroke(2));
    g2.drawRect(image.getWidth()/2+252, image.getHeight()/2-11, 49, 22);

    float rotationDeg = 63f;

    //Draw roll readout
    g2.setFont(new Font("Dialog", Font.PLAIN, 20));
    String rollHeadingText = (int)rotationDeg+"°";
    int rollHeadingTextWidth = g2.getFontMetrics().stringWidth(rollHeadingText);
    int rollDegreeWidth = g2.getFontMetrics().charWidth('°');
    g2.drawString(rollHeadingText, image.getWidth()/2-(g2.getFontMetrics().stringWidth(rollHeadingText)/2)-277, image.getHeight()/2+9);
    g2.setFont(new Font("Dialog", Font.PLAIN, 14));
    g2.drawString("ROLL", image.getWidth()/2-277-g2.getFontMetrics().stringWidth("ROLL")/2, image.getHeight()/2+24);
    g2.setFont(new Font("Dialog", Font.PLAIN, 10));
    String rollDecimalText = String.valueOf((int)((rotationDeg-(int)rotationDeg)*10));
    g2.drawString(rollDecimalText, (image.getWidth()/2-277+(rollHeadingTextWidth/2)-(rollDegreeWidth/2))-g2.getFontMetrics().stringWidth(rollDecimalText)/2, image.getHeight()/2+9);
    g2.setStroke(new BasicStroke(2));
    g2.drawRect(image.getWidth()/2-301, image.getHeight()/2-11, 49, 22);

    //Rotate image to rotationDeg
    final double rads = Math.toRadians(rotationDeg);
    final double sin = Math.abs(Math.sin(rads));
    final double cos = Math.abs(Math.cos(rads));
    final int w = (int) Math.floor(bi.getWidth() * cos + bi.getHeight() * sin);
    final int h = (int) Math.floor(bi.getHeight() * cos + bi.getWidth() * sin);
    final BufferedImage rotatedImage = new BufferedImage(w, h, bi.getType());
    final AffineTransform at = new AffineTransform();
    at.translate(w / 2, h / 2);
    at.rotate(rads,0, 0);
    at.translate(-bi.getWidth() / 2, -bi.getHeight() / 2);
    final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    rotateOp.filter(bi,rotatedImage);

    BufferedImage cropped = rotatedImage.getSubimage(rotatedImage.getWidth()/2-250, rotatedImage.getHeight()/2-250, 500, 500);

    //Draw Artificial horizon on top of window canvas
    g2.drawImage(cropped, out.getWidth()/2-cropped.getWidth()/2,out.getHeight()/2-cropped.getHeight()/2,null);
    g2.setColor(Color.RED);
    g2.drawLine(out.getWidth()/2-50, out.getHeight()/2, out.getWidth()/2+50, out.getHeight()/2);
    g2.drawLine(out.getWidth()/2, out.getHeight()/2-5, out.getWidth()/2, out.getHeight()/2+5);

    return out;
  }

  private BufferedImage drawCompass() {
    BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2 = out.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

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

    return out;
  }

  public static void main(String[] args) throws IOException {
    VideoPane p = new VideoPane();
    JFrame frame = new JFrame();
    frame.add(p);
    frame.setSize(1296, 759);
    frame.setVisible(true);
    BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_3BYTE_BGR);

    p.update(image);
  }

}
