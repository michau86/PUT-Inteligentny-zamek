import RPi.GPIO as GPIO

class Servo:

    servo_opened = 11
    servo_closed = 3

    electro_opened = 1
    electro_closed = 0

    def __init__(self, pin_servo=12, pin_electro=16):
        global electro_closed
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(pin_servo, GPIO.OUT)
        GPIO.setup(pin_electro, GPIO.OUT)
        self.pin_servo = pin_servo
        self.pin_electro = pin_electro

        self.p = GPIO.PWM(self.pin_servo, 50)  # pin=12 czestotliwosc=50Hz prawo=3 lewo=11
        GPIO.output(self.pin_electro, False)
        self.p.start(self.servo_closed)

    def Open(self):
        global electro_opened
        self.p.ChangeDutyCycle(self.servo_opened)
        GPIO.output(self.pin_electro, True)

    def Close(self):
        global electro_closed
        self.p.ChangeDutyCycle(self.servo_closed)
        GPIO.output(self.pin_electro, False)

    def Destroy(self):
        self.p.stop()
        GPIO.cleanup()
