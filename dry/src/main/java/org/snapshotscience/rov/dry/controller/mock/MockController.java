package org.snapshotscience.rov.dry.controller.mock;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;

public class MockController implements Controller {

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
    return "Fake Controller";
  }

  @Override
  public String toString() {
    return getName();
  }
}
