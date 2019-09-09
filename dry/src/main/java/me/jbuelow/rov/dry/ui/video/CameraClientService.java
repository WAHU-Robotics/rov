package me.jbuelow.rov.dry.ui.video;

import java.awt.Dimension;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.RovConstants;
import me.jbuelow.rov.common.codec.StreamFrameListener;
import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import me.jbuelow.rov.dry.ui.UiBootstrap;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CameraClientService {

  private final Dimension resolution = new Dimension(1280, 720);

  private final UiBootstrap bootstrap;
  private final StreamFrameListener frameListener;
  private CameraClientAgent agent;

  public CameraClientService(UiBootstrap bootstrap) {
    this.bootstrap = bootstrap;
    this.frameListener = (StreamFrameListener) bootstrap.getFrameListener();
    this.agent = new CameraClientAgent(frameListener, resolution);
  }

  @EventListener
  @Order(1)
  public void initiateConnection(VehicleDiscoveryEvent event) {
    //agent.start(new InetSocketAddress(event.getVehicleAddress(), 20000));
    agent.start(new InetSocketAddress("127.0.0.1", 20000));
  }

}
