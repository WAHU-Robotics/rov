package me.jbuelow.rov.wet.service.connection;

import io.netty.handler.codec.serialization.ClassResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CommandClassResolver implements ClassResolver, ApplicationContextAware {

  private ApplicationContext applicationContext;

  private Map<String, Class<?>> cache;

  @Override
  public Class<?> resolve(String s) throws ClassNotFoundException {
    if (Objects.isNull(cache)) {
      fillCache();
    }

    if (!cache.containsKey(s)) {
      throw new ClassNotFoundException();
    }

    return cache.get(s);
  }

  private void fillCache() {
    cache = new HashMap<>();
    for (Map.Entry<String, CommandHandler> entry : applicationContext
        .getBeansOfType(CommandHandler.class).entrySet()) {
      Class<?> commandClass = entry.getValue().getCommandType();
      cache.put(commandClass.getCanonicalName(), commandClass);
    }
  }


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
