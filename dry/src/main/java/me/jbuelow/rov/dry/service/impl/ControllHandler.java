/**
 *
 */
package me.jbuelow.rov.dry.service.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.GetCapabilities;
import me.jbuelow.rov.common.GetSystemStats;
import me.jbuelow.rov.common.OpenVideo;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.RovConstants;
import me.jbuelow.rov.common.SystemStats;
import me.jbuelow.rov.common.VehicleCapabilities;
import me.jbuelow.rov.common.VideoStreamAddress;
import me.jbuelow.rov.dry.controller.Control;
import me.jbuelow.rov.dry.ui.Gui;
import net.java.games.input.Controller;
import org.apache.commons.io.IOUtils;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Slf4j
public class ControllHandler implements Closeable {

  private VehicleCapabilities capabilities;
  private Socket vehicleSocket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private VideoStreamAddress video;
  private Gui gui;
  private Control control;

  public ControllHandler(InetAddress vehicleAddress) throws IOException, ClassNotFoundException {
    vehicleSocket = new Socket(vehicleAddress, RovConstants.ROV_PORT);
    out = new ObjectOutputStream(vehicleSocket.getOutputStream());
    in = new ObjectInputStream(vehicleSocket.getInputStream());

    //Get Capabilities
    capabilities = (VehicleCapabilities) sendCommand(new GetCapabilities());
    video = (VideoStreamAddress) sendCommand(
        new OpenVideo(vehicleAddress)); //Test my own crappy command

    control = new Control();
    log.debug("/\\ Chances are thats an error there"); //It still works on my machine so i dont care
    Controller[] ca = control.getFoundControllers();
    log.info("There are " + ca.length + " controllers detected");

    for (int i = 0; i < ca.length; i++) {
      log.info("Controller " + i + ": '" + ca[i].getName() + "'");
    }

    log.debug("Prompting user to select controllers...");
    control.promptForController(0);
    control.promptForController(1);

    gui = new Gui(video.url);

    Loop loop = new Loop();
    loop.run();
  }

  private class Loop extends Thread {

    private boolean running = true;

    @Override
    public void run() {
      while (running) {
        SystemStats stat = null;
        try {
          stat = (SystemStats) sendCommand(new GetSystemStats(false, false));
        } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
        }

        gui.setCpuTempValue(String.valueOf(stat.getCpuTemp()));
        gui.setJoyA(control.getPolledValues(0));
        gui.setJoyB(control.getPolledValues(1));

        try {
          sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public Response sendCommand(Command command) throws IOException, ClassNotFoundException {
    log.debug("Sending command: " + command.getClass().getSimpleName());
    out.writeObject(command);
    out.flush();

    Response response = (Response) in.readObject();
    log.debug("Got command response: " + response.toString());

    return response;

  }

  public VehicleCapabilities getVehicleCapabilities() {
    return capabilities;
  }

  public UUID getId() {
    return capabilities.getId();
  }

  @Override
  public void close() {
    IOUtils.closeQuietly(in);
    IOUtils.closeQuietly(out);
    IOUtils.closeQuietly(vehicleSocket);
  }
}
