from time import sleep

import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)

GPIO.setup(17, GPIO.OUT)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(21, GPIO.OUT)
GPIO.setup(22, GPIO.OUT)

for n in range(0, 16):

  x = n

  if (x >= 8):
    GPIO.output(17, True)
    x = x - 8
  else:
    GPIO.output(17, False)

  if (x >= 4):
    GPIO.output(18, True)
    x = x - 4
  else:
    GPIO.output(18, False)

  if (x >= 2):
    GPIO.output(21, True)
    x = x - 2
  else:
    GPIO.output(21, False)

  if (x == 1):
    GPIO.output(22, True)
    x = x - 1
  else:
    GPIO.output(22, False)

  sleep(1)

  GPIO.output(17, False)
  GPIO.output(18, False)
  GPIO.output(21, False)
  GPIO.output(22, False)

GPIO.cleanup()
