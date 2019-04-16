package me.jbuelow.rov.common.object;

class MotionVector extends ROVObject {

  private static final long serialVersionUID = 4712953101939461543L;
  // 6-DoF axes
  int surge;
  int sway;
  int heave;
  int pitch;
  int yaw;
  int roll;

}
