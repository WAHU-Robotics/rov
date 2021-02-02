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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import org.snapshotscience.rov.dry.ui.CloseApplicationConfirmation;

/**
 * Displays a brief animation while awaiting a connection from wet side
 */
public class ConnectionIdler extends JFrame {

  private JPanel contentPane;
  private JButton quitButton;

  public ConnectionIdler() {
    setContentPane(contentPane);
    setResizable(false);
    setUndecorated(true);
    setTitle("Awaiting Connection...");

    UIManager.put("Button.select",new Color(49, 49, 97));
    quitButton.updateUI();
    quitButton.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        ConnectionIdler.this.onQuit();
      }
    });

    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ConnectionIdler.this.onQuit();
      }
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    toFront();
    requestFocus();
  }

  private void onQuit() {
    CloseApplicationConfirmation.requestExit();
  }

  public void close() {
    this.dispose(); //delet this
  }

  public static void main(String[] args) {
    new ConnectionIdler();
  }
}
