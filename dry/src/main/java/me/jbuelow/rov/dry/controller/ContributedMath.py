# Old zack had a contribution to make. This is what he sent me. dont blame me.

import math
import pygame


def dotProduct(vecOne=[], vecTwo=[]):
  return vecOne[0] * vecTwo[0] + vecOne[1] * vecTwo[1]


def getMag(vec=[]):
  return math.sqrt(vec[0] * vec[0] + vec[1] * vec[1])


def getNorm(mag, vec=[]):
  resault = [0, 0]
  if mag:
    resault[0] = vec[0] / mag
    resault[1] = vec[1] / mag
  return resault


# pygame shit, ingore it
pygame.init()
pygame.joystick.init()
joystick = pygame.joystick.Joystick(0)
joystick.init()
clock = pygame.time.Clock()

# Seting motors states for full thottle forward
motorFountRight = [-0.707, 0.707]
motorFrountLeft = [0.707, 0.707]
motorBackRight = [-0.707, -0.707]
motorBackLeft = [0.707, -0.707]

while True:
  # more pygame shit
  for event in pygame.event.get():  # User did something
    if event.type == pygame.QUIT:  # If user clicked close
      done = True  # Flag that we are done so we exit this loop

  # Get axis and store them into a vec
  dVec = [0, 0]
  dVec[0] = round(joystick.get_axis(0) * -1, 3)
  dVec[1] = round(joystick.get_axis(1) * -1, 3)

  # find the mag, used in scaling
  mag = getMag(dVec)

  dVec = getNorm(float(mag), dVec)

  if mag > 1:
    mag = 1

  print("Input axis  : [" + str(round(dVec[0], 3)) + ", " + str(
      round(dVec[1], 3)) + "]");
  print("Frount Right: %.3f" % (dotProduct(dVec, motorFountRight) * mag))
  print("Frount Left : %.3f" % (dotProduct(dVec, motorFrountLeft) * mag))
  print("Back Right  : %.3f" % (dotProduct(dVec, motorBackRight) * mag))
  print("Back Left   : %.3f" % (dotProduct(dVec, motorBackLeft) * mag))
  clock.tick(1)
