#include <stdio.h>
#include <wiringPi.h>
#include <wiringPiSPI.h>

#define CS 3
#define CHANNEL 0
#define SPEED 1000000
 
int readADC(unsigned char adcChannel)
{
	unsigned char buf[3];
	int adcValue = 0;
 
	buf[0] = 0x06 | ((adcChannel & 0x07) >> 2);
	buf[1] = ((adcChannel & 0x07) << 6);
	buf[2] = 0x00;
 
	digitalWrite(CS, LOW);
 
	wiringPiSPIDataRW(CHANNEL, buf, 3);
 
	buf[1] = 0x0F & buf[1];
	adcValue = (buf[1] << 8) | buf[2];
 
	digitalWrite(CS, HIGH);
 
	return adcValue;
}

int main()
{
	int adcX1 = 0;
	int adcY1 = 0;
	int adcX2 = 0;
	int adcY2 = 0;
 
	if(wiringPiSetup() == -1)
		return -1;
 
	if(wiringPiSPISetup(CHANNEL, SPEED) == -1)
		return -1;

	int ledPin[10] = { 1, 4, 5, 6, 10, 11, 26, 27, 28, 29 };
 
	pinMode(CS, OUTPUT);
	for(int i = 0; i < 10; i++) {
		pinMode(ledPin[i], OUTPUT);
		digitalWrite(ledPin[i], HIGH);
	}
 
	while(1)
	{
		adcX1 = readADC(0);
		adcY1 = readADC(1);
		adcX2 = readADC(2);
		adcY2 = readADC(3);
		printf("x1 = %4d\t y1 = %4d\t x2 = %4d\t y2 = %4d\n", adcX1, adcY1, adcX2, adcY2);
		delay(100);
	}
 
	return 0;
}
