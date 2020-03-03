package me.jbuelow.rov.dry.ui.video;

import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import me.jbuelow.rov.dry.ui.UiBootstrap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

  private final VideoFrameReceiver frameReceiver;
  private VideoTransport transport;

  @Value("${rov.camera.url}")
  private String videoUrl;

  public VideoService(UiBootstrap uiBootstrap) {
    this.frameReceiver = uiBootstrap.getFrameReceiver();
  }

  @EventListener
  @Order(1)
  public void initiateConnection(VehicleDiscoveryEvent event) {
    transport = new VideoTransport(videoUrl, frameReceiver);
    transport.start();
  }

}
