from time import sleep

import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)

GPIO.setup(17, GPIO.OUT)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(21, GPIO.OUT)
GPIO.setup(22, GPIO.OUT)

for x in range(0, 3):

  GPIO.output(17, True)
  GPIO.output(18, True)
  GPIO.output(21, True)
  GPIO.output(22, True)
  sleep(1)
  GPIO.output(17, False)
  GPIO.output(18, False)
  GPIO.output(21, False)
  GPIO.output(22, False)
  sleep(1)

GPIO.cleanup()
