/**
 *
 */
package me.jbuelow.rov.moist.service.impl;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.moist.service.CommandHandler;
import me.jbuelow.rov.moist.service.CommandProcessorService;
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

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandProcessorService#handleCommand(Command)
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

    return response;
  }

  private <T extends Command> CommandHandler<T> getHandler(T command) {
    @SuppressWarnings("unchecked")
    CommandHandler<T> handler = handlerMap.get(command.getClass());

    if (handler == null) {
      throw new IllegalArgumentException(
          "No valid handler configured for command type: " + command.getClass().getName());
    }

    return handler;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @SuppressWarnings({"rawtypes"})
  @PostConstruct
  public void initializeCommandHandlers() {
    for (Map.Entry<String, CommandHandler> entry : applicationContext
        .getBeansOfType(CommandHandler.class).entrySet()) {
      CommandHandler handler = entry.getValue();

      handlerMap.put(handler.getCommandType(), handler);
    }
  }
}
