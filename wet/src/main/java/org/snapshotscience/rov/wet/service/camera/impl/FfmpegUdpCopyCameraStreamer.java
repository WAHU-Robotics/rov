package org.snapshotscience.rov.wet.service.camera.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.wet.service.camera.CameraStreamer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FfmpegUdpCopyCameraStreamer implements CameraStreamer {

    private boolean running = false;

    @Setter private int width = 0;
    @Setter private int height = 0;
    @Setter private double framerate = 0;
    @Setter private String device = "";

    @Setter private String format = "video4linux2";
    @Setter private String wantedCodec = "h264";
    @Setter private String outputContainer = "mpegts";
    @Setter private String outputURL = "";

    private Process ffmpeg;

    @Override
    public void start() {
        String[] command = new String[] {"ffmpeg", "-f", "", "-input_format", "", "-video_size", "", "-framerate", "", "-i", "", "-vcodec", "copy", "-an", "-f", "", ""};
        command[2] = format;
        command[4] = wantedCodec;
        command[6] = width+"x"+height;
        command[8] = String.valueOf(framerate);
        command[10] = device;
        command[15] = outputContainer;
        command[16] = outputURL;

        log.info("Starting ffmpeg camera stream for device '{}'...", device);
        log.debug("Using '{}' as ffmpeg command", String.join(" ", command));

        try {
            ffmpeg = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            log.error("Failed to start ffmpeg!", e);
            throw new RuntimeException(e);
        }

        running = true;
    }

    @Override
    public void stop() {
        ffmpeg.destroy();
        try {
            boolean killed = ffmpeg.waitFor(30, TimeUnit.SECONDS);
            if (!killed) ffmpeg.destroyForcibly();
        } catch (InterruptedException e) {
            log.warn("Interrupted while trying to end stream", e);
            ffmpeg.destroyForcibly();
        } finally {
            running = false;
        }
    }

    @Override
    public boolean isRunning() {
        if (ffmpeg != null) running = ffmpeg.isAlive();
        return running;
    }
}
