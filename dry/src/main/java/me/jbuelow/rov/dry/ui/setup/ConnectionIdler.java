package me.jbuelow.rov.dry.ui.setup;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import me.jbuelow.rov.dry.ui.CloseApplicationConfirmation;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

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
