package me.jbuelow.rov.dry.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class CloseApplicationConfirmation extends JDialog {

  private JPanel contentPane;
  private JButton buttonYes;
  private JButton buttonNo;

  public CloseApplicationConfirmation() {
    setContentPane(contentPane);
    setModal(false);
    getRootPane().setDefaultButton(buttonYes);

    UIManager.put("Button.select",new Color(49, 49, 97));
    buttonYes.updateUI();
    buttonYes.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));
    buttonNo.updateUI();
    buttonNo.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));

    buttonYes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    buttonNo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
                                         public void actionPerformed(ActionEvent e) {
                                           onCancel();
                                         }
                                       }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    toFront();
    requestFocus();
  }

  public static void requestExit() {
    CloseApplicationConfirmation dialog = new CloseApplicationConfirmation();
  }

  private void onOK() {
    //TODO OPEN THE SelectWetShutdownMethod GUI

    System.exit(0);
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }

  public static void main(String[] args) {
    CloseApplicationConfirmation dialog = new CloseApplicationConfirmation();
  }
}
