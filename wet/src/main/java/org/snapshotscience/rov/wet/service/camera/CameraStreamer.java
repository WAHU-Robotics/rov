package org.snapshotscience.rov.wet.service.camera;

public interface CameraStreamer {

    void start();
    void stop();

    boolean isRunning();

    void setWidth(int width);
    void setHeight(int height);
    void setFramerate(double framerate);
    void setDevice(String device);



}
