package org.snapshotscience.rov.dry.ui.error;

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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import org.snapshotscience.rov.dry.Dry;

/**
 * Class for making graphical error messages easier
 */
public class GeneralError extends JDialog {

  private JPanel contentPane;
  private JButton buttonOK;
  private JLabel messageBox;

  public GeneralError() {
    setContentPane(contentPane);
    setModal(true);
    setResizable(false);
    setTitle("Error");
    getRootPane().setDefaultButton(buttonOK);

    UIManager.put("Button.select",new Color(49, 49, 97));
    buttonOK.updateUI();
    buttonOK.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));

    buttonOK.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GeneralError.this.onOK();
      }
    });

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onOK();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                           GeneralError.this.onOK();
                                         }
                                       }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  private void onOK() {
    // add your code here
    dispose();
  }

  public void setMessage(String message) {
    messageBox.setText(message);
  }

  public void setMessageIcon(ErrorIcon icon) {
    try {
      messageBox.setIcon(new ImageIcon(Dry.class.getClassLoader().getResource(icon.getFilename())));
    } catch (NullPointerException ignored) {
    }
  }

  public static void main(String[] args) {
    GeneralError dialog = new GeneralError();
    dialog.pack();
    dialog.setVisible(true);
    System.exit(0);
  }

  public static void display(String message, ErrorIcon icon) {
    GeneralError dialog = new GeneralError();
    dialog.setMessage(message);
    dialog.setMessageIcon(icon);
    dialog.pack();
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }

  public static void display(ErrorIcon icon) {
    GeneralError dialog = new GeneralError();
    dialog.setMessageIcon(icon);
    dialog.pack();
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }

  public static void display() {
    GeneralError dialog = new GeneralError();
    dialog.pack();
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }

  public static void display(String message) {
    GeneralError dialog = new GeneralError();
    dialog.setMessage(message);
    dialog.pack();
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }

}
