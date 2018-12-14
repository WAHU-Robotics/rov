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
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.RovConstants;
import me.jbuelow.rov.common.VehicleCapabilities;
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

  public ControllHandler(InetAddress vehicleAddress) throws IOException, ClassNotFoundException {
    vehicleSocket = new Socket(vehicleAddress, RovConstants.ROV_PORT);
    out = new ObjectOutputStream(vehicleSocket.getOutputStream());
    in = new ObjectInputStream(vehicleSocket.getInputStream());

    //Get Capabilities
    capabilities = (VehicleCapabilities) sendCommand(new GetCapabilities());
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
  public void close() throws IOException {
    IOUtils.closeQuietly(in);
    IOUtils.closeQuietly(out);
    IOUtils.closeQuietly(vehicleSocket);
  }
}
