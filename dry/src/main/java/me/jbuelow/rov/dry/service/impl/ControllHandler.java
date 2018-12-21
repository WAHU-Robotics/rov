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
import me.jbuelow.rov.common.OpenVideo;
import me.jbuelow.rov.common.Ping;
import me.jbuelow.rov.common.Pong;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.RovConstants;
import me.jbuelow.rov.common.VehicleCapabilities;
import me.jbuelow.rov.common.VideoStreamAddress;
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

  public ControllHandler(InetAddress vehicleAddress) throws IOException, ClassNotFoundException {
    vehicleSocket = new Socket(vehicleAddress, RovConstants.ROV_PORT);
    out = new ObjectOutputStream(vehicleSocket.getOutputStream());
    in = new ObjectInputStream(vehicleSocket.getInputStream());

    //Get Capabilities
    capabilities = (VehicleCapabilities) sendCommand(new GetCapabilities());
    video = (VideoStreamAddress) sendCommand(
        new OpenVideo(vehicleAddress)); //Test my own crappy command

    gui = new Gui();

    Loop loop = new Loop();
    loop.run();
  }

  private class Loop extends Thread {

    private boolean running = true;

    @Override
    public void run() {
      while (running) {
        Pong pong = null;
        try {
          pong = (Pong) sendCommand(new Ping());
        } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
        }

        gui.setLabel(pong.message);

        try {
          sleep(1000);
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
