package org.snapshotscience.rov.dry.ui.setup;

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
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import org.snapshotscience.rov.dry.ui.CloseApplicationConfirmation;
import org.snapshotscience.rov.dry.ui.error.ErrorIcon;
import org.snapshotscience.rov.dry.ui.error.GeneralError;
import net.java.games.input.Controller;

/**
 * Provides a nice clean graphical method of selecting the primary and secondary joysticks
 */
public class JoystickSelecter extends JDialog {
  private static final long serialVersionUID = 3880297608880881195L;

  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JComboBox<Controller> comboBox1;
  private JComboBox<Controller> comboBox2;

  public JoystickSelecter(List<Controller> controllers, Object[] def) {
    setContentPane(contentPane);
    setModal(true);
    setResizable(false);
    setTitle("Joystick Selection");
    getRootPane().setDefaultButton(buttonOK);

    for (Controller controller : controllers) {
      comboBox1.addItem(controller);
      comboBox2.addItem(controller);
    }

    comboBox1.setSelectedItem(def[0]);
    comboBox2.setSelectedItem(def[1]);

    UIManager.put("Button.select",new Color(49, 49, 97));
    UIManager.put("ComboBox.selectionBackground", new Color(84, 84, 84));
    buttonOK.updateUI();
    buttonCancel.updateUI();
    comboBox1.updateUI();
    comboBox2.updateUI();

    buttonOK.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));
    buttonCancel.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));
    comboBox1.setBorder(BorderFactory.createBevelBorder(0));
    comboBox2.setBorder(BorderFactory.createBevelBorder(0));

    buttonOK.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JoystickSelecter.this.onOK();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JoystickSelecter.this.onCancel();
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
    contentPane
        .registerKeyboardAction(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                    JoystickSelecter.this.onCancel();
                                  }
                                }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    toFront();
    requestFocus();
  }

  private void onOK() {
    for (Controller c : getSelection()) {
      if (!c.poll()) {
        GeneralError.display(
            "<html><b>Could connect to controller.</b><br>Controller: " + c.getName()
                + "<br>Please try again.</html>",
            ErrorIcon.JOY_ERROR);
        return;
      }
    }
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    CloseApplicationConfirmation.requestExit();
  }

  public Controller[] getSelection() {
    return new Controller[]{(Controller) comboBox1.getSelectedItem(),
        (Controller) comboBox2.getSelectedItem()};
  }

  public static void main(String[] args) {
    List<Controller> list = new ArrayList<>();
    Object[] defs = {"Controller 1", "Controller 2"};
    JoystickSelecter sel = new JoystickSelecter(list, defs);
  }

}
