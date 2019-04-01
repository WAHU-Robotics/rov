import RPi.GPIO as GPIO
import time
import subprocess
import os
import os, fnmatch

#this is what we call "ghetto code"

print("Starting ROV updater...")
GPIO.setmode(GPIO.BCM)
GPIO.setup(26, GPIO.IN, pull_up_down=GPIO.PUD_UP)
input_state = GPIO.input(26)

def find(pattern, path):
  result = []
  for root, dirs, files in os.walk(path):
    for name in files:
      if fnmatch.fnmatch(name, pattern):
        result.append(os.path.join(root, name))
  return result

if input_state:
  print("Update pins are not shorted. Update not requested exiting...")
  print("If update is nessasary, short the update pin to ground.")
  exit()

print("Update requested. Updating...")
if os.path.ispath("/dev/sda1"):
  print("Mounting drive...")
  subprocess.run(["sudo", "mount", "/dev/sda1", "/mnt"])
  print("Looking for JARs...")
  r = find('wet*.jar', '/mnt/')
  r.sort(reverse = True)
  if r.length == 0:
    print("No JAR files found. Exiting...")
    exit()

  print("Copying latest version...")
  subprocess.run(["cp", r[0], "/home/pi/wet.jar"])
  print("Unmounting...")
  subprocess.run(["sudo", "umount", "/dev/sda1"])
  print("Update complete! Exiting...")
  exit()
else:
  print("No USB drive found. Exiting...")