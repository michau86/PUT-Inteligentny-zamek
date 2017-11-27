#include "Servo.h"

Key_Control::Key_Control(float delay): open_delay(delay)
{
	wiringPiSetup();
	pinMode(SERVO_PIN, PWM_OUTPUT);
}

Key_Control::~Key_Control() {}

bool Key_Control::Key_Open() 
{
	pwmWrite(SERVO_PIN, SERVO_OPEN);
	digitalWrite(ELECTRO_PIN, ELECTRO_OPEN);
	delay(open_delay);
	pwmWrite(SERVO_PIN, SERVO_CLOSE);
	digitalWrite(ELECTRO_PIN, ELECTRO_CLOSE);
}