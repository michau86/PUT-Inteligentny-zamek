#include <wiringPi.h>

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#define SERVO_PIN 1
#define ELECTRO_PIN 4

#define SERVO_OPEN 100
#define SERVO_CLOSE 30
#define ELECTRO_OPEN 1
#define ELECTRO_CLOSE 0

class Key_Control {
public:
	Key_Control(float delay);
	~Key_Control();
	bool Key_Open();
private:
	float open_delay;
};