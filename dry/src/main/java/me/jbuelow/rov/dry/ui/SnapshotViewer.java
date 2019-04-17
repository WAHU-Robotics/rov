package me.jbuelow.rov.dry.ui;

import java.awt.FlowLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SnapshotViewer extends JFrame {

  private JLabel image;

  public SnapshotViewer(BufferedImage img) {
    getContentPane().setLayout(new FlowLayout());

    AffineTransform ty = AffineTransform.getScaleInstance(1, -1);
    ty.translate(0, -img.getHeight(null));
    AffineTransformOp op = new AffineTransformOp(ty, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    img = op.filter(img, null);
    image = new JLabel(new ImageIcon(img));

    getContentPane().add(image);
    pack();
    setVisible(true);
  }
}
