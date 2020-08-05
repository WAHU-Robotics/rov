package me.jbuelow.rov.dry.ui;

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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import me.jbuelow.rov.dry.service.VehicleControlService;

public class CloseApplicationConfirmation extends JDialog {

  private JPanel contentPane;
  private JButton buttonYes;
  private JButton buttonNo;

  private final VehicleControlService vehicleControlService;

  public CloseApplicationConfirmation() {
    this(null, false);
  }

  public CloseApplicationConfirmation( boolean isModal) {
    this(null, isModal);
  }

  public CloseApplicationConfirmation(VehicleControlService vehicleControlService) {
    this(vehicleControlService, false);
  }

  public CloseApplicationConfirmation(VehicleControlService vehicleControlService, boolean isModal) {
    this.vehicleControlService = vehicleControlService;
    setContentPane(contentPane);
    setModal(isModal);
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
    setAlwaysOnTop(true);
    toFront();
    requestFocus();
  }

  public static void requestExit(VehicleControlService vehicleControlService) {
    CloseApplicationConfirmation dialog = new CloseApplicationConfirmation(vehicleControlService);
  }

  public static void requestExit() {
    CloseApplicationConfirmation dialog = new CloseApplicationConfirmation(true);
  }

  private void onOK() {
    if (vehicleControlService != null) {
      SelectWetShutdownMethod selectWetShutdownMethod = new SelectWetShutdownMethod(
          vehicleControlService);
      selectWetShutdownMethod.display();
    }
    System.exit(0);
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }
}
