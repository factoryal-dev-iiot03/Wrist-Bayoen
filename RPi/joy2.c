#include <wiringPi.h>
#include <wiringPiSPI.h>
#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>


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


int main(int argc, char* argv[])
{
    int adcX1=0;
    int adcY1=0;
    int adcX2=0;
    int adcY2=0;
    int Ltb = 0;
    int Rtb = 0;
    int SX1 = 0;
    int CX1 = 0;
    int SY1 = 0;
    int CY1 = 0;
    int SX2 = 0;
    int CX2 = 0;
    int SY2 = 0;
    int CY2 = 0;
    int sock;

//**************************************************************************

    struct sockaddr_in serv_addr;


    if(argc != 3) {
        printf("Usage: %s <IP> <port>\n", argv[0]);
        exit(1);
    }

    sock = socket(PF_INET, SOCK_STREAM, 0);
    printf("socket...");
    if(sock == -1)
        printf("socket() error");
    printf("OK\n");

    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = inet_addr(argv[1]);
    serv_addr.sin_port = htons(atoi(argv[2]));

    printf("connect...");
    while(connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) == -1)
        printf("connect() error!");
    printf("OK\n");

//**************************************************************************

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
		printf("f\n");
		Ltb = 0;
	}

	if(!Lb && Ltb ==0)
	{
		printf("F\n");
		Ltb = 1;
	}



	if( Rb && Rtb == 1)
	{
		printf("h\n");
		Rtb = 0;
	}

	if(!Rb && Rtb == 0)
	{
		printf("H\n");
		Rtb = 1;
	}


	if(adcX1 > 3000 && SX1 == 0)
    {
        printf("D\n");
        SX1 = 1;
    }
    
    if(adcX1 < 3000 && SX1 == 1)
    {
        printf("d\n");
        SX1 = 0;
    }


	if(adcX1 < 1000 && CX1 == 0)	
    {
        printf("A\n");
        CX1 =1;
    }

    if( adcX1 > 1000 && CX1 == 1)
    {
        printf("a\n");
        CX1 = 0;
    }


	if(adcY1 > 3000 && SY1 == 0)
    {
        printf("W\n");
        SY1 = 1;
    }

    if( adcY1 < 3000 && SY1 == 1)
    {
        printf("w\n");
        SY1 = 0;
    }



	if(adcY1 < 1000 && CY1 == 0)
    {
        printf("S\n");
        CY1 = 1;
    }

    if(adcY1 > 1000 && CY1 == 1)
    {
        printf("s\n");
        CY1 = 0;
    }

	if(adcX2 > 3000 && SX2 == 0)
    {
        printf("X\n");
        SX2 = 1;
    }

    if(adcX2 < 3000 && SX2 == 1)
    {
        printf("x\n");
        SX2 = 0;
    }


	if(adcX2 < 1000 && CX2 == 0)
    {
        printf("J\n");
        CX2 = 1;
    }

    if(adcX2 > 1000 && CX2 == 1)
    {
        printf("j\n");
        CX2 = 0;
    }


	if(adcY2 > 3000 && SY2 == 0)
    {
        printf("I\n");
        SY2 = 1;
    }

    if(adcY2 < 3000 && SY2 == 1)
    {
        printf("i\n");
        SY2 = 0;
    }


	if(adcY2 < 1000 && CY2 == 0)
    {
        printf("K\n");
        CY2 = 1;
    }

    if(adcY2 > 1000 && CY2 == 1)
    {
        printf("k\n");
        CY2 = 0;
    }
    
    delay(200);
    }
    return 0;
}