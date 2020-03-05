package me.jbuelow.rov.wet.service.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.Command;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NettyCommandProcessorService extends SimpleChannelInboundHandler<Command> implements ApplicationContextAware {

  @SuppressWarnings("rawtypes")
  private Map<Class, CommandHandler> handlerMap = new HashMap<>();
  private ApplicationContext applicationContext;

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command)
      throws Exception {
    log.info("Received Command " + command.getClass().getSimpleName());
    Response r = getHandler(command).execute(command);
    channelHandlerContext.writeAndFlush(r);
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
