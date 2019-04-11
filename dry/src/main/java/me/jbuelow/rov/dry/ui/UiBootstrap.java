package me.jbuelow.rov.dry.ui;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.capabilities.Tool;
import me.jbuelow.rov.common.command.OpenVideo;
import me.jbuelow.rov.common.command.SetMotion;
import me.jbuelow.rov.common.command.SetServo;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SystemStats;
import me.jbuelow.rov.common.response.VideoStreamAddress;
import me.jbuelow.rov.dry.config.Config;
import me.jbuelow.rov.dry.controller.Control;
import me.jbuelow.rov.dry.controller.ControlLogic;
import me.jbuelow.rov.dry.controller.ControlMapper;
import me.jbuelow.rov.dry.controller.PolledValues;
import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import me.jbuelow.rov.dry.exception.JinputNativesNotFoundException;
import me.jbuelow.rov.dry.service.VehicleControlService;
import me.jbuelow.rov.dry.ui.error.GeneralError;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.*;

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
  @Order(Ordered.LOWEST_PRECEDENCE)
  public void startMainUI(VehicleDiscoveryEvent event) {
    vehicleId = event.getVehicleID();
    video = (VideoStreamAddress) vehicleControlService.sendCommand(vehicleId, new OpenVideo(event.getVehicleAddress()));

    gui = new Gui("");
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
        boolean gripperState = false;
        boolean prevMagnetState = true;
        boolean prevLightState = true;
        boolean prevCupState = true;
        boolean prevGripperState = false;
        boolean firstLoop = true;
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

          SystemStats stat = null;

          SetMotion motion = ControlLogic.genMotorValues(joyA, joyB);
          
          //Don't flood the ROV with unnecessary movement commands
          if (!motion.equals(previousMotion)) {
            Response response = vehicleControlService.sendCommand(vehicleId, motion);
            if (response.isSuccess()) {
              previousMotion = motion;
            } else {
              log.error("Error with setMotion command.", response.getException());
            }
          }

          //gui.setCpuTempValue(String.valueOf(stat.getCpuTemp()));
          gui.setFps(round(calculateAverage(fpsLog), 1));

          ControlMapper m = new ControlMapper(joyA, joyB);
          ControlMapper pm = new ControlMapper(prevController[0], prevController[1]);

          try {
            if (!firstLoop) {
              if (ControlMapper.wasButtonPress(m, pm, Config.GRIPPER_BUTTON)) {
                gripperState = !gripperState;
              }
            }
          } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
          }

          SetServo servoCommand = new SetServo();
          Map<Tool, Integer> protoMap = new HashMap<>();
          if (gripperState != prevGripperState) {
            gui.setGripperState(gripperState);
            protoMap.put(Tool.GRIPPER, gripperState ? -1000 : 1000);
            prevGripperState = gripperState;
          }
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

          servoCommand.setServoValues(protoMap);
          if (!servoCommand.equals(previousServo)) {
            Response response = vehicleControlService.sendCommand(vehicleId, servoCommand);
            previousServo = servoCommand;
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
}
