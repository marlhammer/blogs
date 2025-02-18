#!/usr/bin/python

import sys
import time
import Adafruit_BMP.BMP085 as BMP085

# Parameters are:
#   busnum (i2c bus number, defaults to 1)
#   mode (BMP085_ULTRALOWPOWER, BMP085_STANDARD, BMP085_HIGHRES, or BMP085_ULTRAHIGHRES, defaults to BMP085_STANDARD). See the data sheet.
#
#sensor = BMP085.BMP085()
sensor = BMP085.BMP085(mode=BMP085.BMP085_ULTRAHIGHRES)

try:
  sys.stdout.write('Establishing a baseline.')
  sys.stdout.flush()
  baseline_points=[];
  for x in range(0, 10):
    time.sleep(.1)
    baseline_points.append(sensor.read_pressure())
    if x % 10 == 0:
      sys.stdout.write('.')
      sys.stdout.flush()

  tolerance=8

  while 1:
    time.sleep(.1)
    reading = sensor.read_pressure()

    baseline_points.pop(0);
    baseline_points.append(reading);

    baseline = 0;
    for x in baseline_points:
      baseline += x
    baseline /= len(baseline_points)
    print('Baseline: %s' % baseline)

    if abs(reading - baseline) > tolerance:
      print reading
except KeyboardInterrupt:
  print "\nStopped."

