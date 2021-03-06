package org.snapshotscience.rov.dry.ui.utility.calculator.cannon;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class CannonVolumeCalculator extends JFrame {

  private JPanel contentPane;
  private JTextField smallEndRadiusSpinner;
  private JButton calculateButton;
  private JTextField largeEndRadiusSpinner;
  private JTextField cannonLengthSpinner;
  private JTextField boreRadiusSpinner;

  public CannonVolumeCalculator() {
    setContentPane(contentPane);
    setResizable(false);
    setTitle("Cannon Volume Calculator");
    getRootPane().setDefaultButton(calculateButton);

    calculateButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        calculate();
      }
    });
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        cancel();
      }
    });
    contentPane.registerKeyboardAction(new ActionListener() {
                                         public void actionPerformed(ActionEvent e) {
                                           cancel();
                                         }
                                       }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    toFront();
    requestFocus();
  }

  private void calculate() {
    try {
      Double result = CannonVolumeMath.calculate(
          Double.valueOf(smallEndRadiusSpinner.getText()),
          Double.valueOf(boreRadiusSpinner.getText()),
          Double.valueOf(largeEndRadiusSpinner.getText()),
          Double.valueOf(cannonLengthSpinner.getText()));
      CannonVolumeResult resultGui = new CannonVolumeResult();
      DecimalFormat df = new DecimalFormat("#.####");
      resultGui.setValue(result.toString());
      resultGui.display();
      dispose();
    } catch (NumberFormatException e) {
      error();
    }
  }

  private void cancel() {
    dispose();
  }

  private void error() {
    CannonVolumeResult resultGui = new CannonVolumeResult();
    resultGui.setValue("ERROR");
    resultGui.display();
  }

  public static void main(String[] args) {
    CannonVolumeCalculator yeet = new CannonVolumeCalculator();
  }
}
