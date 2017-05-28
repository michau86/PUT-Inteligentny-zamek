import RPi.GPIO as GPIO

class Servo:

    servo_opened = 11
    servo_closed = 3

    electro_opened = 0
    electro_closed = 1

    def __init__(self, pin_servo=12, pin_electro=16):
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(pin_servo, GPIO.OUT)
        GPIO.setup(pin_electro, GPIO.OUT)
        self.pin_servo = pin_servo
        self.pin_electro = pin_electro

        self.p = GPIO.PWM(self.pin_servo, 50)  # pin=12 czestotliwosc=50Hz prawo=3 lewo=11
        GPIO.output(pin_electro, self.electro_closed)
        self.p.start(self.servo_closed)

    def Open(self):
        self.p.ChangeDutyCycle(self.servo_opened)
        GPIO.output(self.pin_electro, self.electro_opened)

    def Close(self):
        self.p.ChangeDutyCycle(self.servo_closed)
        GPIO.output(self.pin_electro, self.electro_closed)

    def Destroy(self):
        self.p.stop()
        GPIO.cleanup()
