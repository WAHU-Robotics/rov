package me.jbuelow.rov.dry.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import me.jbuelow.rov.common.command.Shutdown;
import me.jbuelow.rov.common.command.Shutdown.Option;
import me.jbuelow.rov.common.response.Goodbye;
import me.jbuelow.rov.dry.service.VehicleControlService;
import me.jbuelow.rov.dry.ui.error.ErrorIcon;
import me.jbuelow.rov.dry.ui.error.GeneralError;
import org.springframework.stereotype.Service;

@Service
public class SelectWetShutdownMethod extends JDialog {

  private final VehicleControlService vehicleControlService;
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JComboBox<Option> comboBox1;

  public SelectWetShutdownMethod(VehicleControlService vehicleControlService) {
    this.vehicleControlService = vehicleControlService;
  }

  public void display() {
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    for (Option option : Option.values()) {
      comboBox1.addItem(option);
    }
    comboBox1.setSelectedItem(Option.REBOOT);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
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

  private void onOK() {
    // add your code here
    if (comboBox1.getSelectedItem() == Option.NOTHING) {
      dispose();
      return;
    }
    Goodbye response = (Goodbye) vehicleControlService
        .sendCommand(vehicleControlService.getAttatchedVehicles().get(0).getId(),
            new Shutdown((Option) comboBox1.getSelectedItem()));
    if (!response.isSafe()) {
      GeneralError.display(
          "<html>WARNING!<br>Wet side may not have been able to failsafe motors.<br>Motors may be powered up. Use Caution.</html>",
          ErrorIcon.WARNING);
    }
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }
}
