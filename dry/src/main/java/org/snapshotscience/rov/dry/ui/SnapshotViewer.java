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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.dry.ui.error.GeneralError;

@Slf4j
public class SnapshotViewer extends JFrame {

  private JLabel image;
  private JButton saveButton;

  private boolean flipImage = false;

  private final BufferedImage bufImage;

  private boolean savedImage = false;

  public SnapshotViewer(BufferedImage img) {
    this.bufImage = img;
    getContentPane().setLayout(new BorderLayout());

    if (flipImage) {
      AffineTransform ty = AffineTransform.getScaleInstance(1, -1);
      ty.translate(0, -img.getHeight(null));
      AffineTransformOp op = new AffineTransformOp(ty, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
      img = op.filter(img, null);
    }

    image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (!savedImage) {
          Object[] options = {"Save Image","Discard Image","Cancel"};
          int opt = JOptionPane.showOptionDialog(getContentPane(),
              "Image has not been saved. Do you want to save or discard this image?",
              "Confirm exit",
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.WARNING_MESSAGE,
              null,
              options,
              options[2]);
          switch (opt) {
            case 0:
              saveFile();
              if (savedImage) {
                super.windowClosing(e);
              }
              break;
            case 1:
              super.windowClosing(e);
            default:
              break;
          }
        }
      }
    });

    saveButton = new JButton("Save Image...");
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        saveFile();
      }
    });

    getContentPane().add(saveButton, BorderLayout.PAGE_END);
    getContentPane().add(image, BorderLayout.CENTER);
    pack();
  }

  private void saveFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Saving image");
    FileFilter filter = new FileFilter() {
      @Override
      public boolean accept(File f) {
        if (f.isDirectory()) {
          return true;
        }
        return f.getName().endsWith(".jpg");
      }

      @Override
      public String getDescription() {
        return "JPEG Images (*.jpg)";
      }
    };
    fileChooser.addChoosableFileFilter(filter);
    fileChooser.setFileFilter(filter);
    int userSelection = fileChooser.showSaveDialog(getContentPane());

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      if (!fileToSave.getName().endsWith(".jpg")) {
        fileToSave = new File(fileToSave.getAbsolutePath()+".jpg");
      }
      log.info("Saving screenshot image to {}", fileToSave.getAbsolutePath());
      try {
        ImageIO.write(bufImage, "jpg", fileToSave);
        savedImage = true;
      } catch (IOException ex) {
        log.error("Writing image file to {} failed.", fileToSave.getAbsolutePath(), ex);
        GeneralError.display("Could not write image to file.");
      }
    }
  }


}
