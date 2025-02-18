#!/usr/bin/python

import subprocess
import re
import sys
import time
import datetime
import gspread

# Google Account
email       = 'smouring.dev@gmail.com'
password    = '()-()-NeverNeverWind-()-()'
spreadsheet = 'Temperature / Humidity Log'

# Login
try:
  gc = gspread.login(email, password)
except:
  print "Unable to log in.  Check your email address/password"
  sys.exit()

# Open worksheet
try:
  worksheet = gc.open(spreadsheet).sheet1
  # worksheet = gc.open_by_key('0BmgG6nO_6dprdS1MN3d3MkdPa142WFRrdnRRUWl1UFE')
except:
  print "Unable to open the spreadsheet.  Check your filename: %s" % spreadsheet
  sys.exit()

# Append data
while(True):
  output = subprocess.check_output(["./Adafruit_DHT", "22", "17"]);
  print output

  matches = re.search("Temp =\s+([0-9.]+)", output)
  if (not matches):
    time.sleep(3)
    continue

  tempC = float(matches.group(1))

  tempF = tempC * 9.0;
  tempF = tempF / 5.0;
  tempF = tempF + 32.0;

  matches = re.search("Hum =\s+([0-9.]+)", output)
  if (not matches):
    time.sleep(3)
    continue

  humidity = float(matches.group(1))

  print "Temperature: %.1f C" % tempC
  print "Temperature: %.1f F" % tempF
  print "Humidity:    %.1f %%" % humidity

  try:
    values = [datetime.datetime.now(), tempC, tempF, humidity]
    worksheet.append_row(values)
  except:
    print "Unable to append data.  Check your connection?"
    sys.exit()

  # Pause for next reading
  time.sleep(120)
