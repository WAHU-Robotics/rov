package me.jbuelow.rov.dry.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lombok.Getter;
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
  List<Controller> selectedControllers = new ArrayList<>(2);

  List<Controller> selectableControllers = new ArrayList<Controller>();

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

  public void promptForController(int position) {
    boolean unset = true;
    Object def = null;
    if (selectableControllers.size() <= 0) {
      selectedControllers.add(new FalseController());
      /*JOptionPane.showMessageDialog(null,
              "No compatible joysticks connected.\nProgram will exit.", "Error",
              JOptionPane.ERROR_MESSAGE);
      System.exit(1);*/
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
        couldNotPollErrorMessage(c);
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
      promptForController(controller);
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
  }

}
