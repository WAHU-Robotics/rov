package me.jbuelow.rov.dry.ui.setup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ConnectionIdler extends JFrame {

  private JPanel contentPane;
  private JButton quitButton;

  public ConnectionIdler() {
    setContentPane(contentPane);
    setResizable(false);
    setTitle("Awaiting Connection...");

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
    int i = JOptionPane
        .showConfirmDialog(null, "Close application?", "", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (i == 0) {
      System.exit(0); //commit self deletus
    }
  }

  public void close() {
    this.dispose(); //delet this
  }
}
