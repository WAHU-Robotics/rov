package me.jbuelow.rov.dry.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import lombok.Getter;
import me.jbuelow.rov.dry.ui.setup.JoystickSelecter;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;

public class Control {

  @Getter
  Controller[] foundControllers;

  @Getter
  Controller primaryController;

  @Getter
  Controller secondaryController;

  @Getter
  List<Controller> selectedControllers = new ArrayList<>(Collections.nCopies(2, null));

  List<Controller> selectableControllers = new ArrayList<>(20);

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

  private void updateShortcuts() {
    this.primaryController = selectedControllers.get(0);
    this.secondaryController = selectedControllers.get(1);
  }

  public void promptForControllers() {
    boolean unset = true;
    Object[] def = new Object[2];
    if (selectableControllers.size() <= 0) {
      if (Objects.equals(System.getenv("ROV_DEVMODE"), "true")) {
        selectableControllers.add(new FalseController());
      } else {
        JOptionPane.showMessageDialog(null,
            "No compatible joysticks connected.\nProgram will exit.", "Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    for (int position = 0; position < 2; position++) {
      if (position > (selectableControllers.size() - 1)) {
        def[position] = selectableControllers.get(selectableControllers.size() - 1);
      } else {
        def[position] = selectableControllers.get(position);
      }
    }

    while (unset) {
      JoystickSelecter sel = new JoystickSelecter(selectableControllers, def);
      Controller[] controllers = sel.getSelection();

      int i = 0;
      for (Controller c : controllers) {
        if (c == null) {
          refreshList();
          continue;
        }

        if (c.poll()) {
          selectedControllers.set(i, c);
          updateShortcuts();
          unset = false;
        } else {
          couldNotPollErrorMessage(c);
        }
        i++;
      }
    }
  }

  private void couldNotPollErrorMessage(Controller c) {
    JOptionPane.showMessageDialog(null,
        "Could not poll '" + c.getName() + "'.\nPlease select a different controller.", "Error",
        JOptionPane.ERROR_MESSAGE);
    refreshList();
  }

  public PolledValues getPolledValues(int controller) {
    Controller c = selectedControllers.get(controller);

    if (!c.poll()) {
      couldNotPollErrorMessage(c);
      promptForControllers();
    }

    return new PolledValues(c);
  }

  private class FalseController implements Controller {

    @Override
    public Controller[] getControllers() {
      return new Controller[0];
    }

    @Override
    public Type getType() {
      return null;
    }

    @Override
    public Component[] getComponents() {
      return new Component[0];
    }

    @Override
    public Component getComponent(Identifier identifier) {
      return null;
    }

    @Override
    public Rumbler[] getRumblers() {
      return new Rumbler[0];
    }

    @Override
    public boolean poll() {
      return true;
    }

    @Override
    public void setEventQueueSize(int i) {

    }

    @Override
    public EventQueue getEventQueue() {
      return null;
    }

    @Override
    public PortType getPortType() {
      return null;
    }

    @Override
    public int getPortNumber() {
      return 0;
    }

    @Override
    public String getName() {
      return "False Controller";
    }

    @Override
    public String toString() {
      return getName();
    }
  }

}
