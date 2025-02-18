import RPi.GPIO as GPIO

import time

import datetime

hourToWake = 8

try:
  GPIO.setmode(GPIO.BCM)
  GPIO.setup(18, GPIO.OUT)

  while(True):

    currentHour = datetime.datetime.now().hour

    if (currentHour >= hourToWake and currentHour < hourToWake+2):
      GPIO.output(18, 1)
    else:
      GPIO.output(18, 0)

    time.sleep(5)

  GPIO.output(18, 0)
  GPIO.cleanup()
except:
  print "Error!"
  GPIO.cleanup()
