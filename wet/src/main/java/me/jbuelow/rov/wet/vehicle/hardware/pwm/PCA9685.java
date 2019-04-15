/*
 *  Copyright (C) 2015 Marcus Hirt
 *                     www.hirt.se
 *
 * This software is free:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESSED OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright (C) Marcus Hirt, 2015
 */
package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an Adafruit 16 channel I2C PWM driver board.
 * 
 * @author Marcus Hirt
 */
@Component
@Slf4j
@Profile("!noHardware")
@SuppressWarnings("unused") // Not using all commands - yet.
public class PCA9685 implements PwmDevice {
	private final int address;
	private final I2CDevice i2cDevice;
	private final int bus;

	private static final int MODE1 = 0x00;
	private static final int MODE2 = 0x01;
	private static final int SUBADR1 = 0x02;
	private static final int SUBADR2 = 0x03;
	private static final int SUBADR13 = 0x04;
	private static final int PRESCALE = 0xFE;
	private static final int LED0_ON_L = 0x06;
	private static final int LED0_ON_H = 0x07;
	private static final int LED0_OFF_L = 0x08;
	private static final int LED0_OFF_H = 0x09;
	private static final int ALL_LED_ON_L = 0xFA;
	private static final int ALL_LED_ON_H = 0xFB;
	private static final int ALL_LED_OFF_L = 0xFC;
	private static final int ALL_LED_OFF_H = 0xFD;

	// Bits
	private static final int RESTART = 0x80;
	private static final int SLEEP = 0x10;
	private static final int ALLCALL = 0x01;
	private static final int INVRT = 0x10;
	private static final int OUTDRV = 0x04;
	
    private static final double PULSE_LENGTH = 960_000d;

	private Map<Integer, PwmChannel> channels = new HashMap<>(16);
	private Double frequency = null;

	/**
	 * Constructs a PWM device using the default settings. (I2CBUS.BUS_1, 0x40)
	 * 
	 * @throws IOException                   if there was communication problem
	 * @throws UnsupportedBusNumberException
	 */
	public PCA9685() throws IOException, UnsupportedBusNumberException {
		// 0x40 is the default address used by the AdaFruit PWM board.
		this(I2CBus.BUS_1, 0x40);
	}

	/**
	 * Creates a software interface to an Adafruit 16 channel I2C PWM driver board
	 * (PCA9685).
	 * 
	 * @param bus     the I2C bus to use.
	 * @param address the address to use.
	 * 
	 * @see I2CBus
	 * 
	 * @throws IOException                   if there was communication problem
	 * @throws UnsupportedBusNumberException
	 */
	public PCA9685(int bus, int address) throws IOException, UnsupportedBusNumberException {
		this.bus = bus;
		this.address = address;
		i2cDevice = I2CFactory.getInstance(bus).getDevice(address);
		initialize();
	}

	/**
	 * Sets all PWM channels to the provided settings.
	 * 
	 * @param on  when to turn on the signal [0, 4095]
	 * @param off when to turn off the signal [0, 4095]
	 * 
	 * @throws IOException if there was a problem communicating with the device.
	 */
	@Override
	public void setAllPWM(int on, int off) throws IOException {
		write(ALL_LED_ON_L, (byte) (on & 0xFF));
		write(ALL_LED_ON_H, (byte) (on >> 8));
		write(ALL_LED_OFF_L, (byte) (off & 0xFF));
		write(ALL_LED_OFF_H, (byte) (off >> 8));
	}

	/**
	 * Sets the PWM frequency to use. This is common across all channels. For
	 * controlling RC servos, 50Hz is a good starting point.
	 * 
	 * @param frequency the PWM frequency to use, in Hz.
	 * @throws IOException if a problem occured accessing the device.
	 */
	@Override
	public void setPWMFreqency(double frequency) throws IOException {
		this.frequency = Double.valueOf(frequency);
		double prescaleval = 25000000.0;
		prescaleval /= 4096.0;
		prescaleval /= frequency;
		prescaleval -= 1.0;
		double prescale = Math.floor(prescaleval + 0.5);
		int oldmode = i2cDevice.read(MODE1);
		int newmode = (oldmode & 0x7F) | 0x10;
		write(MODE1, (byte) newmode);
		write(PRESCALE, (byte) (Math.floor(prescale)));
		write(MODE1, (byte) oldmode);
		sleep(50);
		write(MODE1, (byte) (oldmode | 0x80));
	}

	/**
	 * Returns one of the PWM channels on the device. Allowed range is [0, 15].
	 * 
	 * @param channel the channel to retrieve.
	 * 
	 * @return the specified PWM channel.
	 */
	@Override
	public PwmChannel getChannel(int channel) {
		// Lazy Load the channel objects and cache to prevent excessive object creation.
		if (!channels.containsKey(channel)) {
			channels.put(channel, new PCA9685Channel(channel));
		}

		return channels.get(channel);
	}

	/**
	 * Returns the address used when communicating with this PWM device.
	 * 
	 * @return the address used when communicating with this PWM device.
	 */
	@Override
	public int getAddress() {
		return address;
	}

	/**
	 * Returns the bus used when communicating with this PWM device.
	 * 
	 * @return the bus used when communicating with this PWM device.
	 */
	@Override
	public int getBus() {
		return bus;
	}

	/**
	 * Use to control a PWM channel on the PWM device.
	 * 
	 * @see PCA9685#getChannel(int)
	 */
	public class PCA9685Channel implements PwmChannel {
		private final int channel;

		private PCA9685Channel(int channel) {
			if (channel < 0 || channel > 15) {
				throw new IllegalArgumentException("There is no channel " + channel + " on the board.");
			}
			this.channel = channel;
		}

		/**
		 * Configures the PWM pulse for the PWMChannel.
		 * 
		 * @param on  when to go from low to high [0, 4095]. 0 means at the very start
		 *            of the pulse, 4095 at the very end.
		 * @param off when to go from high to low [0, 4095]. 0 means at the very start
		 *            of the pulse, 4095 at the very end.
		 * 
		 * @throws IOException
		 */
		@Override
		public void setPWM(int on, int off) throws IOException {
			i2cDevice.write(LED0_ON_L + 4 * channel, (byte) (on & 0xFF));
			i2cDevice.write(LED0_ON_H + 4 * channel, (byte) (on >> 8));
			i2cDevice.write(LED0_OFF_L + 4 * channel, (byte) (off & 0xFF));
			i2cDevice.write(LED0_OFF_H + 4 * channel, (byte) (off >> 8));
		}

		@Override
		public void setServoPulse(float pulseMS) throws IOException {
			int pulse = getServoValueFromPulse(pulseMS);
			setPWM(0, pulse);
		}

		@Override
		public int getChannelNumber() {
			return channel;
		}
	}

	private int getServoValueFromPulse(float targetPulse) {
	    double pulseLength = PULSE_LENGTH; // 1s = 1,000,000 us per pulse. "us" is to be read "micro (mu) sec".
	    pulseLength /= frequency;  // 40..1000 Hz
	    pulseLength /= 4_096; // 12 bits of resolution. 4096 = 2^12
	    int pulse = (int) Math.round((targetPulse * 1_000) / pulseLength); // in millisec
	    if (log.isDebugEnabled()) {
	      log.debug(
	          String.format("%.04f \u00b5s per bit, pulse: %d", pulseLength, pulse)); // bit? cycle?
	    }
	    
	    return pulse;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Don't care
		}
	}

	private void write(int address, byte b) throws IOException {
		i2cDevice.write(address, b);
	}

	private int read(int address) throws IOException {
		return i2cDevice.read(address);
	}

	private void initialize() throws IOException {
		setAllPWM(0, 0);
		write(MODE2, (byte) OUTDRV);
		write(MODE1, (byte) ALLCALL);
		sleep(50);
		int mode1 = read(MODE1);
		mode1 = mode1 & ~SLEEP;
		write(MODE1, (byte) mode1);
		sleep(50);
	}
}
