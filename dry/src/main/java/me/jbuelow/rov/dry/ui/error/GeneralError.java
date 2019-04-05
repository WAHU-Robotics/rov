package me.jbuelow.rov.dry.ui.error;

import me.jbuelow.rov.dry.Dry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
