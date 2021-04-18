package org.snapshotscience.rov.wet.service.camera;

/**
 * @author Jacob Buelow
 *
 * Interface used to generify different ways of streaming a camera or other video device
 */
public interface CameraStreamer {

    void start();
    void stop();

    boolean isRunning();

    void setWidth(int width);
    void setHeight(int height);
    void setFramerate(double framerate);
    void setDevice(String device);



}
