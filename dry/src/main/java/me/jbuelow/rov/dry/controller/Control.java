package me.jbuelow.rov.dry.controller;

import java.util.ArrayList;
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
  List<Controller> selectableControllers = new ArrayList<>();

  public enum Controllers {
    PRIMARY(0),
    SECONDARY(1);

    private int id;

    Controllers(int id) {
      this.id = id;
    }
  }

  public Control() {
    refreshList();
  }

  private void refreshList() {
    foundControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

    for (Controller c:foundControllers) {
      if (c.getType() == Controller.Type.STICK) {
        selectableControllers.add(c);
      }
    }
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
    Object def = null;
    if (selectableControllers.size() <= 0) {
      JOptionPane.showMessageDialog(null,
              "No compatible joysticks connected.\nProgram will exit.", "Error",
              JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    } else if (position > (selectableControllers.size() - 1)) {
      def = selectableControllers.get(selectableControllers.size() - 1);
    } else {
      def = selectableControllers.get(position);
    }

    while (unset) {
      Controller c = (Controller) JOptionPane
          .showInputDialog(null, "Please select a controller to use for #" + position + 1 + ":",
              "Controllers",
              JOptionPane.PLAIN_MESSAGE, null, selectableControllers.toArray(), def);

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

  public PolledValues getPolledValues(int controller) {
    Controller c = selectedControllers.get(controller);

    if (!c.poll()) {
      JOptionPane.showMessageDialog(null,
          "Could not poll '" + c.getName() + "'.\nPlease select a different controller.", "Error",
          JOptionPane.ERROR_MESSAGE);
      refreshList();
      promptForController(controller);
    }

    return new PolledValues(c);
  }

}
