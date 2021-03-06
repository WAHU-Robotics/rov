package org.snapshotscience.rov.dry.ui;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.measure.unit.SI;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.common.capabilities.Tool;
import org.snapshotscience.rov.common.command.GetSystemStats;
import org.snapshotscience.rov.common.command.GetWaterTemp;
import org.snapshotscience.rov.common.command.OpenVideo;
import org.snapshotscience.rov.common.command.SetMotion;
import org.snapshotscience.rov.common.command.SetServo;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.common.response.SystemStats;
import org.snapshotscience.rov.common.response.VideoStreamAddress;
import org.snapshotscience.rov.common.response.WaterTemp;
import org.snapshotscience.rov.dry.config.Config;
import org.snapshotscience.rov.dry.controller.Control;
import org.snapshotscience.rov.dry.controller.ControlLogic;
import org.snapshotscience.rov.dry.controller.ControlMapper;
import org.snapshotscience.rov.dry.controller.PolledValues;
import org.snapshotscience.rov.dry.discovery.VehicleDiscoveryEvent;
import org.snapshotscience.rov.dry.exception.JinputNativesNotFoundException;
import org.snapshotscience.rov.dry.service.VehicleControlService;
import org.snapshotscience.rov.dry.ui.error.GeneralError;
import org.snapshotscience.rov.dry.ui.video.VideoFrameReceiver;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UiBootstrap {

  private Control control;
  private VehicleControlService vehicleControlService;
  private VideoStreamAddress video;
  private Gui gui;
  private UUID vehicleId;

  public UiBootstrap(Control control, VehicleControlService vehicleControlService) {
    this.control = control;
    this.vehicleControlService = vehicleControlService;
    
    Controller[] ca = control.getFoundControllers();
    log.info("There are " + ca.length + " controllers detected");

    for (int i = 0; i < ca.length; i++) {
      log.info("Controller " + i + ": '" + ca[i].getName() + "'");
    }

    log.debug("Prompting user to select controllers...");
    try {
      control.promptForControllers();
    } catch (JinputNativesNotFoundException e) {
      throw new RuntimeException("Error initializing controls", e);
    }

    Component[] components = control.getPrimaryController().getComponents();
    for (Component component : components) {
      log.debug(component.getName());
    }
  }

  @EventListener
  @Order()
  public void startMainUI(VehicleDiscoveryEvent event) {
    vehicleId = event.getVehicleID();
    video = (VideoStreamAddress) vehicleControlService
        .sendCommand(vehicleId, new OpenVideo(event.getVehicleAddress()));

    gui = new Gui("", vehicleControlService);
    log.info("Opening Mplayer instance...");
    //player = new Mplayer(video.url);

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

  @org.springframework.stereotype.Component
  private class FrameReceiver extends VideoFrameReceiver {
    @Override
    public void newFrame(BufferedImage frame) {
      gui.updateVideoFrame(frame);
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
        int i = 0;
        double time = gt();
        double lastTime;
        float fps;
        List<Float> fpsLog = new ArrayList<>();
        PolledValues[] prevController = new PolledValues[2];
        boolean magnetState = false;
        boolean lightState = false;
        boolean cupState = false;
        boolean gripperState = false;
        boolean prevMagnetState = true;
        boolean prevLightState = true;
        boolean prevCupState = true;
        boolean prevGripperState = true;
        boolean firstLoop = true;
        int cameraPosition = 0;
        SetMotion previousMotion = null;
        SetServo previousServo = null;

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

          //Run some commands that only need to update every once in a while
          if (i >= 30) {
            DecimalFormat df = new DecimalFormat("#.## °C");

            SystemStats stat = (SystemStats) vehicleControlService.sendCommand(vehicleId, new GetSystemStats());
            gui.setCpuTempValue(stat.getCpuTemp().doubleValue(SI.CELSIUS));
            int tempC = (int) stat.getCpuTemp().doubleValue(SI.CELSIUS);
            if (tempC > 80) {
              gui.setCpuTempBadness(2);
            } else if (tempC > 70) {
              gui.setCpuTempBadness(1);
            } else {
              gui.setCpuTempBadness(0);
            }

            WaterTemp temp = (WaterTemp) vehicleControlService.sendCommand(vehicleId, new GetWaterTemp());
            gui.setWaterTempValue(temp.getTemperature().doubleValue(SI.CELSIUS));

            i = 0;
          }

          SetMotion motion = ControlLogic.genMotorValues(joyA, joyB);
          gui.setMotionVector(motion);

          //Don't flood the ROV with unnecessary movement commands
          if (!motion.equals(previousMotion)) {
            Response response = vehicleControlService.sendCommand(vehicleId, motion);
            if (response.isSuccess()) {
              previousMotion = motion;
            } else {
              log.error("Error with setMotion command.", response.getException());
            }
          }

          gui.setFps(round(calculateAverage(fpsLog), 1));

          ControlMapper m = new ControlMapper(joyA, joyB);
          ControlMapper pm = new ControlMapper(prevController[0], prevController[1]);

          try {
            if (!firstLoop) {
              if (ControlMapper.wasButtonPress(m, pm, Config.GRIPPER_BUTTON)) {
                gripperState = !gripperState;
              }
              if (ControlMapper.wasButtonPress(m, pm, Config.MAGNET_BUTTON)) {
                magnetState = !magnetState;
              }
              if (ControlMapper.wasButtonPress(m, pm, Config.LIGHT_BUTTON)) {
                lightState = !lightState;
              }
              if (ControlMapper.wasButtonPress(m, pm, Config.CUP_BUTTON)) {
                cupState = !cupState;
              }
              if (ControlMapper.wasButtonPress(m, pm, Config.SNAP_BUTTON)) {
                SnapshotViewer viewer = new SnapshotViewer(gui.getVideoFrame());
                viewer.setVisible(true);
              }

              if (m.getAxis(Config.CAMERA_X_AXIS) > (pm.getAxis(Config.CAMERA_X_AXIS) + Config.JOY_DEADZONE) ||
                  m.getAxis(Config.CAMERA_X_AXIS) < (pm.getAxis(Config.CAMERA_X_AXIS) - Config.JOY_DEADZONE)) {
                cameraPosition = m.getAxis(Config.CAMERA_X_AXIS);
              }
            }
          } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
          }

          SetServo servoCommand = new SetServo();
          Map<Tool, Integer> protoMap = new HashMap<>();
          if (gripperState != prevGripperState) {
            gui.setGripperState(gripperState);
            protoMap.put(Tool.GRIPPER, (gripperState ^ Config.GRIPPER_INVERT) ? 1000 : -1000);
            prevGripperState = gripperState;
          }
          if (magnetState != prevMagnetState) {
            gui.setMagnetState(magnetState);
            protoMap.put(Tool.MAGNET, (magnetState ^ Config.MAGNET_INVERT) ? 1000 : -1000);
            prevMagnetState = magnetState;
          }
          if (lightState != prevLightState) {
            gui.setLightState(lightState);
            protoMap.put(Tool.LIGHT, (lightState ^ Config.LIGHT_INVERT) ? 1000 : -1000);
            prevLightState = lightState;
          }
          if (cupState != prevCupState) {
            gui.setCupState(cupState);
            protoMap.put(Tool.CUP, (cupState ^ Config.CUP_INVERT) ? 1000 : -1000);
            prevCupState = cupState;
          }
          protoMap.put(Tool.CAMERA_X,  (Config.CAMERA_X_INVERT ? -1 : 1) * cameraPosition);

          servoCommand.setServoValues(protoMap);
          if (!servoCommand.equals(previousServo)) {
            Response response = vehicleControlService.sendCommand(vehicleId, servoCommand);
            if (response.isSuccess()) {
              previousServo = servoCommand;
            }
          }

          prevController[0] = joyA;
          prevController[1] = joyB;
          if (firstLoop) {
            firstLoop = false;
          }

          i++;
        }
      } catch (Exception e) {
        e.printStackTrace();
        GeneralError.display();
        System.exit(41673786);
      }
    }
  }
}
