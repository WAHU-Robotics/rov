package me.jbuelow.rov.dry.ui.video;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoService {

  private final VideoFrameReceiver frameReceiver;
  private VideoTransport transport;
  private String videoUrl = "udp://239.0.0.1:1234";

  public VideoService(VideoFrameReceiver frameReceiver) {
    this.frameReceiver = frameReceiver;
  }

  @EventListener
  @Order(1)
  public void initiateConnection(VehicleDiscoveryEvent event) {
    start();
  }

  private void start() {
    log.info("Starting camera stream...");
    transport = new VideoTransport(videoUrl, frameReceiver);
    transport.start();
  }

}
