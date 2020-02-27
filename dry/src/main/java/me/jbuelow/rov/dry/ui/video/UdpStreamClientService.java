package me.jbuelow.rov.dry.ui.video;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.codec.StreamFrameListener;
import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import me.jbuelow.rov.dry.ui.UiBootstrap;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UdpStreamClientService {

  private final UiBootstrap bootstrap;
  private final StreamFrameListener frameListener;
  private UdpStreamHandler streamHandler;

  public UdpStreamClientService(UiBootstrap bootstrap) {
    this.bootstrap = bootstrap;
    this.frameListener = (StreamFrameListener) bootstrap.getFrameListener();
  }

  @EventListener
  @Order(1)
  public void initiateConnection(VehicleDiscoveryEvent event) {
    log.debug("Starting udp stream handler...");
    streamHandler = new UdpStreamHandler(frameListener);
    new Thread(() -> streamHandler.start("udp://239.0.0.1:1234?multicast", "h264")).start();
  }

}
