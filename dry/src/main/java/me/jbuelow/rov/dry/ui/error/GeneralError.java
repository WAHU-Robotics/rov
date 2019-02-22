package me.jbuelow.rov.dry.ui.error;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import me.jbuelow.rov.dry.Dry;

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

    buttonOK.addActionListener(e -> onOK());

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onOK();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
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

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    contentPane = new JPanel();
    contentPane.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1,
            -1));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null,
        0, false));
    final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
    panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0,
        false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
        false));
    buttonOK = new JButton();
    buttonOK.setText("OK");
    panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 5), -1, -1));
    contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
        false));
    messageBox = new JLabel();
    messageBox.setAutoscrolls(false);
    messageBox.setHorizontalAlignment(2);
    messageBox.setHorizontalTextPosition(11);
    messageBox.setIcon(new ImageIcon(getClass().getResource("/error.png")));
    messageBox.setText(
        "<html>OOPSIE WOOPSIE!! Uwu We made a fucky wucky!! A wittle fucko boingo!<br>The code monkeys at our headquarters are working VEWY HAWD to fix this!</html>");
    panel3.add(messageBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return contentPane;
  }
}
