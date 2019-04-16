package me.jbuelow.rov.dry.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.RovConstants;
import me.jbuelow.rov.dry.ui.setup.ConnectionIdler;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
@Slf4j
class DiscoveryService implements DisposableBean {

  private final ExecutorService executorService;
  private DiscoveryListener listener;
  private final ApplicationEventPublisher eventPublisher;

  private DiscoveryService(ExecutorService executorService,
      ApplicationEventPublisher eventPublisher) {
    this.executorService = executorService;
    this.eventPublisher = eventPublisher;
  }

  @EventListener
  public void performStart(ApplicationReadyEvent event) {
    startService();
  }

  @Override
  public void destroy() {
    stopService();
  }

  private void startService() {
    log.debug("Starting Discovery Service");

    synchronized (this) {
      listener = new DiscoveryListener();
      executorService.execute(listener);
    }
  }

  private void stopService() {
    log.debug("Stopping Discovery Service");
    listener.stop();
  }

  private class DiscoveryListener implements Runnable {

    private static final long SLEEP_TIME = 1000;
    private static final int SOCKET_READ_TIMEOUT = 100;
    private volatile boolean running = true;



    @Override
    public void run() {
      DatagramSocket socketIn = null;

      System.setProperty("java.awt.headless","false");
      ConnectionIdler connectionIdler = new ConnectionIdler();

      try {
        socketIn = new DatagramSocket(RovConstants.DISCOVERY_BCAST_PORT);
        socketIn.setSoTimeout(SOCKET_READ_TIMEOUT);

        while (running) {
          DatagramPacket data = new DatagramPacket(new byte[RovConstants.DISCOVERY_PACKET_SIZE],
              RovConstants.DISCOVERY_PACKET_SIZE);

          try {
            socketIn.receive(data);

            log.debug(
                "Received beacon packet: " + new String(data.getData(), RovConstants.CHARSET));

            if (Arrays.equals(data.getData(), RovConstants.DISCOVERY_BYTES)) {
              //We found a controller!
              connectionIdler.close();
              eventPublisher.publishEvent(new VehicleDiscoveryEvent(this, data.getAddress()));
              log.debug("Setting vehicle address to " + data.getAddress().toString());
            }
          } catch (SocketTimeoutException e) {
            //Do nothing and let's move on...
          }

          try {
            Thread.sleep(SLEEP_TIME);
          } catch (InterruptedException e) {
            // DO Nothing and eat the exception
          }
        }
      } catch (IOException e) {
        log.error("Failed to read broadcast message", e);
      } finally {
        if (socketIn != null) {
          IOUtils.closeQuietly(socketIn);
        }
      }

      log.debug("Discovery Service Stopped.");
    }

    void stop() {
      running = false;
    }
  }
}
