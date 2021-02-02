package org.snapshotscience.rov.wet.service.impl;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.common.capabilities.ThrustAxis;
import org.snapshotscience.rov.common.command.Command;
import org.snapshotscience.rov.common.command.SetMotion;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.wet.service.CommandHandler;
import org.snapshotscience.rov.wet.service.CommandProcessorService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Slf4j
@Service
public class CommandProcessorServiceImpl implements CommandProcessorService,
    ApplicationContextAware {

  private ApplicationContext applicationContext;
  @SuppressWarnings("rawtypes")
  private Map<Class, CommandHandler> handlerMap = new HashMap<>();
  private Timer timer;
  private SetMotion frickCommand;


  /**
   * Sends a Command to the proper handler class to be processed
   *
   * @param command Command to handle
   * @return Response from command handler
   */
  @Override
  public Response handleCommand(Command command) {
    log.info("Received Command " + command.getClass().getSimpleName());
    Response response = getHandler(command).execute(command);
    try {
      response.setRequest(command);
    } catch (NullPointerException e) {
      log.warn("No response from " + command.getClass().getSimpleName() + " command");
    }

    try {
      timer.cancel();
      timer.schedule(new FrickTask(), 2000);
    } catch (IllegalStateException e) {
    }

    return response;
  }

  private class FrickTask extends TimerTask {

    @Override
    public void run() {
      handleCommand(frickCommand);
    }
  }

  /**
   * Fetches the corresponding command handler instance for a Command
   *
   * @param command Command type to fetch for
   * @param <T> Command type to fetch for
   * @return Command handler class
   */
  private <T extends Command> CommandHandler<T> getHandler(T command) {
    @SuppressWarnings("unchecked")
    CommandHandler<T> handler = handlerMap.get(command.getClass());

    if (handler == null) {
      throw new IllegalArgumentException(
          "No valid handler configured for command type: " + command.getClass().getName());
    }

    return handler;
  }

  /**
   * Gets an instance of ApplicationContext from Spring Boot
   *
   * @param applicationContext instance of ApplicationContext
   * @throws BeansException Something went wrong
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    this.timer = new Timer();

    frickCommand = new SetMotion();
    Map<ThrustAxis, Integer> vectors = frickCommand.getThrustVectors();

    vectors.put(ThrustAxis.SURGE, 0);
    vectors.put(ThrustAxis.SWAY, 0);
    vectors.put(ThrustAxis.HEAVE, 0);
    vectors.put(ThrustAxis.YAW, 0);
    vectors.put(ThrustAxis.ROLL, 0);
    vectors.put(ThrustAxis.PITCH, 0);
  }

  /**
   * initializes command handlers for commands
   */
  @SuppressWarnings({"rawtypes"})
  @PostConstruct
  public void initializeCommandHandlers() {
    for (Map.Entry<String, CommandHandler> entry : applicationContext
        .getBeansOfType(CommandHandler.class).entrySet()) {
      CommandHandler handler = entry.getValue();

      handlerMap.put(handler.getCommandType(), handler);
      log.debug("Registered new command handler '" + handler.getClass().getSimpleName() + "', handles command '" + handler.getCommandType().getSimpleName() + "'");
    }
  }
}
