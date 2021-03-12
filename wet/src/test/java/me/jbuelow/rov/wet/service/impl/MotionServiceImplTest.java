package me.jbuelow.rov.wet.service.impl;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.Map;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.wet.service.MotionService;
import me.jbuelow.rov.wet.service.MotorService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("noHardware")
public class MotionServiceImplTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();                         

  @Autowired
  MotorService motorService;

  @Autowired
  MotionService motionService;

  @Test
  public void testSetMotionFullThrottleForward() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.SURGE, Integer.valueOf(1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(-1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(-1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")),
        is(1000));
  }

  @Test
  public void testSetMotionHalfThrottleForward() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.SURGE, Integer.valueOf(500));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(500));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(-500));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(-500));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")),
        is(500));
  }

  @Test
  public void testSetMotionFullThrottleBackward() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.SURGE, Integer.valueOf(-1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(-1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")),
        is(1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")),
        is(-1000));
  }

  @Test
  public void testSetMotionFullThrottleRight() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.SWAY, Integer.valueOf(1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")),
        is(1000));
  }

  @Test
  public void testSetMotionFullThrottleLeft() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.SWAY, Integer.valueOf(-1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is( -1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")),
        is(-1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(-1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")),
        is(-1000));
  }

  @Test
  public void testSetMotionFullThrottleUp() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.HEAVE, Integer.valueOf(1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(-1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")), is(0));
  }

  @Test
  public void testSetMotionFullThrottleDown() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.HEAVE, Integer.valueOf(-1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(-1000));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")), is(0));
  }

  @Test
  public void testSetMotionFullYawClockwise() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.YAW, Integer.valueOf(1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(707));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(707));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(-707));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")), is(707));
  }

  @Test
  public void testSetMotionFullYawCounterClockwise() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.YAW, Integer.valueOf(-1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(-707));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")), is(-707));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(707));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")), is(-707));
  }

  @Test
  public void testSetMotionFullYawClockwiseAndForward() {
    Map<ThrustAxis, Integer> motion = new HashMap<>();
    
    motion.put(ThrustAxis.YAW, Integer.valueOf(1000));
    motion.put(ThrustAxis.SURGE, Integer.valueOf(1000));
    
    motionService.setMotion(motion);
    
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Bow Port Thruster")), is(1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Bow Starboard Thruster")),
        is(-171));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Port Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Mid Starboard Thruster")), is(0));
    collector.checkThat(motorService.getMotorPower(motorService.getMotorByName("Stern Port Thruster")), is(-1000));
    collector.checkThat(
        motorService.getMotorPower(motorService.getMotorByName("Stern Starboard Thruster")),
        is(1000));
  }
}
