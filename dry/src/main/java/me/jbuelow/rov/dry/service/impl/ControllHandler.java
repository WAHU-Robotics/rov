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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.Command;
import me.jbuelow.rov.common.command.GetCapabilities;
import me.jbuelow.rov.common.command.OpenVideo;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.RovConstants;
import me.jbuelow.rov.common.response.SystemStats;
import me.jbuelow.rov.common.response.VehicleCapabilities;
import me.jbuelow.rov.common.response.VideoStreamAddress;
import me.jbuelow.rov.dry.controller.Control;
import me.jbuelow.rov.dry.controller.ControlLogic;
import me.jbuelow.rov.dry.controller.PolledValues;
import me.jbuelow.rov.dry.external.Mplayer;
import me.jbuelow.rov.dry.ui.Gui;
import me.jbuelow.rov.dry.ui.error.GeneralError;
import net.java.games.input.Component;
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
  private Mplayer player;

  public ControllHandler(InetAddress vehicleAddress) throws IOException, ClassNotFoundException {
    vehicleSocket = new Socket(vehicleAddress, RovConstants.ROV_PORT);
    out = new ObjectOutputStream(vehicleSocket.getOutputStream());
    in = new ObjectInputStream(vehicleSocket.getInputStream());


    //Get Capabilities
    capabilities = (VehicleCapabilities) sendCommand(new GetCapabilities());
    video = (VideoStreamAddress) sendCommand(
        new OpenVideo(vehicleAddress)); //Test my own crappy command

    control = new Control();
    //log.debug("/\\ Chances are thats an error there"); //It still works on my machine so i dont care
    Controller[] ca = control.getFoundControllers();
    log.info("There are " + ca.length + " controllers detected");

    for (int i = 0; i < ca.length; i++) {
      log.info("Controller " + i + ": '" + ca[i].getName() + "'");
    }

    log.debug("Prompting user to select controllers...");
    control.promptForControllers();

    Component[] components = control.getPrimaryController().getComponents();
    for (Component component : components) {
      log.debug(component.getName());
    }

    gui = new Gui("");
    log.info("Opening Mplayer instance...");
    player = new Mplayer(video.url);

    Loop loop = new Loop();
    try {
      loop.run();
    } catch (Exception e) {
      //Catch-all for loop exceptions
      e.printStackTrace();
      GeneralError.display();
      System.exit(69420); //kms
    }
  }

  private class Loop extends Thread {

    private boolean running = true;

    private double gt() {
      return System.currentTimeMillis();
    }

    /**
     * Round to certain number of decimals
     */
    private float round(float number, int scale) {
      int pow = 10;
      for (int i = 1; i < scale; i++) {
        pow *= 10;
      }
      float tmp = number * pow;
      return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / pow;
    }

    private float calculateAverage(List<Float> marks) {
      float sum = 0;
      if (!marks.isEmpty()) {
        for (float mark : marks) {
          sum += mark;
        }
        return sum / marks.size();
      }
      return (float) ((int) (sum * 10f)) / 10f;
    }

    @Override
    public void run() {
      try {
        double time = gt();
        double lastTime = gt();
        float fps = 0;
        List<Float> fpsLog = new ArrayList<>();
        PolledValues[] prevController = new PolledValues[2];
        boolean magnetState = false;
        boolean lightState = false;
        boolean cupState = false;
        boolean prevMagnetState = true;
        boolean prevLightState = true;
        boolean prevCupState = true;
        boolean firstLoop = true;
        while (running) {
          lastTime = time;
          time = gt();
          fps = (1000f / (float) (time - lastTime));
          fpsLog.add(fps);
          if (fpsLog.size() > 30) {
            fpsLog.remove(0);
          }
          PolledValues joyA = control.getPolledValues(0);
          PolledValues joyB = control.getPolledValues(1);
          gui.setJoyA(joyA);
          gui.setJoyB(joyB);

          SystemStats stat = null;

          try {
            //stat = (SystemStats) sendCommand(new GetSystemStats(true, true));
            sendCommand(ControlLogic.genMotorValues(joyA));
          } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
          }

          //gui.setCpuTempValue(String.valueOf(stat.getCpuTemp()));
          gui.setFps(round(calculateAverage(fpsLog), 1));
          if (magnetState != prevMagnetState) {
            gui.setMagnetState(magnetState);
            prevMagnetState = magnetState;
          }
          if (lightState != prevLightState) {
            gui.setLightState(lightState);
            prevLightState = lightState;
          }
          if (cupState != prevCupState) {
            gui.setCupState(cupState);
            prevCupState = cupState;
          }

          try {
            if (!firstLoop) {
              if (!joyA.buttons[0] && prevController[0].buttons[0]) {
                gui.takeScreenshot();
              }
            }
          } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
          }

          prevController[0] = joyA;
          prevController[1] = joyB;
          if (firstLoop) {
            firstLoop = false;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        GeneralError.display();
        System.exit(41673786);
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
