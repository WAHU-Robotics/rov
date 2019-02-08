package me.jbuelow.rov.dry.ui.setup;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.java.games.input.Controller;

public class JoystickSelecter extends JDialog {

  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JComboBox<Controller> comboBox1;
  private JComboBox<Controller> comboBox2;

  public JoystickSelecter(List<Controller> controllers, Object[] def) {
    setContentPane(contentPane);
    setModal(true);
    setResizable(false);
    getRootPane().setDefaultButton(buttonOK);

    for (Controller controller : controllers) {
      comboBox1.addItem(controller);
      comboBox2.addItem(controller);
    }

    comboBox1.setSelectedItem(def[0]);
    comboBox2.setSelectedItem(def[1]);

    buttonOK.addActionListener(e -> onOK());

    buttonCancel.addActionListener(e -> onCancel());

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    // call onCancel() on ESCAPE
    contentPane
        .registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    pack();
    setVisible(true);
  }

  private void onOK() {
    // add your code here
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    System.exit(0);
  }

  public Controller[] getSelection() {
    return new Controller[]{(Controller) comboBox1.getSelectedItem(),
        (Controller) comboBox2.getSelectedItem()};
  }
}
