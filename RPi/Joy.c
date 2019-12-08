#include <wiringPi.h>
#include <wiringPiSPI.h>
#include <stdio.h>

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

    digitalWrite(CS,LOW);

    wiringPiSPIDataRW(CHANNEL, buf, 3);

    buf[1] = 0x0f & buf[1];
    adcValue = (buf[1] << 8) | buf[2];

    digitalWrite(CS, HIGH);

    return adcValue;
}


int main()
{
    int adcX1=0;
    int adcY1=0;
    int adcX2=0;
    int adcY2=0;
    int Ltb = 0;
    int Rtb = 0;

    if(wiringPiSetup() == -1)
        return -1;

    if(wiringPiSPISetup(CHANNEL, SPEED) == -1)
        return -1;

    pinMode(21,INPUT);	
    pinMode(22,INPUT);
    pinMode(CS,OUTPUT);
    while(1)
    {
        adcX1 = readADC(0);
        adcY1 = readADC(1);
        adcX2 = readADC(2);
        adcY2 = readADC(3);

	int Lb = digitalRead(21);
	int Rb = digitalRead(22);

	if(Lb && Ltb == 1)
	{
		printf('f');
		Ltb = 0;
	}

	if(!Lb && Ltb ==0)
	{
		printf('F');
		Ltb = 1;
	}



	if( Rb && Rtb == 1)
	{
		printf('h');
		Rtb = 0;
	}

	if(!Rb && Rtb == 0)
	{
		printf('H');
		Rtb = 1;
	}


	if(adcX1 > 3000)	{printf('D');}
	if(adcX1 < 1000)	{printf('A');}

	if(adcY1 > 3000)	{printf('W');}
	if(adcY1 < 1000)	{printf('S');}

	if(adcX2 > 3000)	{printf('X');}
	if(adcX2 < 1000)	{printf('J');}

	if(adcY2 > 3000)	{printf('I');}
	if(adcY2 < 1000)	{printf('K');}

        delay(200);

    }
    return 0;

}