package org.snapshotscience.rov.wet.service.camera;

import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.wet.service.camera.impl.FfmpegUdpCopyCameraStreamer;
import org.snapshotscience.rov.wet.vehicle.VehicleConfiguration;
import org.snapshotscience.rov.wet.vehicle.VideoConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Buelow
 *
 * Handles reading the configuration, setting up, and starting of video streamers
 */
@Slf4j
@Service
@Profile("enableCamera")
public class StreamingService {

    private List<VideoConfig> videoConfigs;

    private List<CameraStreamer> cameraStreamers;

    public StreamingService(VehicleConfiguration vehicleConfiguration) {
        videoConfigs = vehicleConfiguration.getVideoConfiguration();
    }

    @PostConstruct
    public void configureAndStartOnStartup() {
        configureStreams();
        startAllStreams();
    }

    public void configureStreams() {
        cameraStreamers = new ArrayList<>();
        for (VideoConfig conf : videoConfigs) {
            CameraStreamer streamer;
            try {
                streamer = conf.getStreamClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("Error starting camera streams!", e);
                continue;
            }
            streamer.setDevice(conf.getDevice());
            streamer.setWidth(conf.getWidth());
            streamer.setHeight(conf.getHeight());
            streamer.setFramerate(conf.getFramerate());

            if (conf.getStreamClass().equals(FfmpegUdpCopyCameraStreamer.class)) {
                ((FfmpegUdpCopyCameraStreamer) streamer).setOutputURL(conf.getOutputURL());
            }

            cameraStreamers.add(streamer);
        }
    }

    public void startAllStreams() {
        if (cameraStreamers == null) throw new IllegalStateException("Streams must be configured first!");
        for (CameraStreamer camStream : cameraStreamers) {
            if (!camStream.isRunning()) {
                try {
                    camStream.start();
                } catch (RuntimeException e) {
                    log.error("Failed to start cam stream!", e);
                }
            }
        }
    }

}
