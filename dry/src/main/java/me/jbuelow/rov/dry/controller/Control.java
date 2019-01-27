package me.jbuelow.rov.dry.controller;

import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import lombok.Getter;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Control {

  @Getter
  Controller[] foundControllers;

  @Getter
  Controller primaryController;

  @Getter
  Controller secondaryController;

  List<Controller> selectedControllers = Arrays.asList(null, null);

  public Control() {
    refreshList();
  }

  private void refreshList() {
    foundControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
  }

  public Controller[] getSelectedControllers() {
    return (Controller[]) this.selectedControllers.toArray();
  }

  private void updateShortcuts() {
    this.primaryController = selectedControllers.get(0);
    this.secondaryController = selectedControllers.get(1);
  }

  public void promptForController(int position) {
    boolean unset = true;
    int def = position + 3;
    if (def > foundControllers.length) {
      def = foundControllers.length;
    }

    while (unset) {
      Controller c = (Controller) JOptionPane
          .showInputDialog(null, "Please select a controller to use for #" + position + 1 + ":",
              "Controllers",
              JOptionPane.PLAIN_MESSAGE, null, foundControllers, foundControllers[def - 1]);

      if (c == null) {
        refreshList();
        continue;
      }

      if (c.poll()) {
        selectedControllers.set(position, c);
        updateShortcuts();
        unset = false;
      } else {
        JOptionPane.showMessageDialog(null,
            "Could not poll '" + c.getName() + "'.\nPlease select a different controller.", "Error",
            JOptionPane.ERROR_MESSAGE);
        refreshList();
      }
    }
  }

}
