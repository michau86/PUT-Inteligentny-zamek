#!/usr/bin/python
import time
import RPi.GPIO as GPIO
import cv2
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(12, GPIO.OUT)
GPIO.setup(16, GPIO.OUT)

try:
    p = GPIO.PWM(12, 50)  # pin=12 czestotliwosc=50Hz prawo=3 lewo=11
    GPIO.output(16,1)
    p.start(3)
    while True:
        raw_input("Nacisnij Enter aby otworzyc")
        p.ChangeDutyCycle(11)
        GPIO.output(16,0)
        print "Otwarty"
        time.sleep(10)
        p.ChangeDutyCycle(3)
        GPIO.output(16,1)
        print "Zamkniety"
except Error(Exception):
    pass
p.stop()
GPIO.cleanup()
