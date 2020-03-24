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
#define SPEED 500000

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


	int pinmap[10] = { 1, 4, 5, 6, 10, 11, 26, 27, 28, 29 };

	for(int i = 0; i < 10; i++)
	{
		pinMode(pinmap[i], OUTPUT);
		digitalWrite(pinmap[i], LOW);
	}



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

//왼손 버튼
	if(Lb && Ltb == 1)
	{
		printf("f\n");
		write(sock, "f\n", 2);
		digitalWrite(1,LOW);
		Ltb = 0;
	}

	if(!Lb && Ltb ==0)
	{
		printf("F\n");
		write(sock, "F\n", 2);
		digitalWrite(1,HIGH);
		Ltb = 1;
	}

//오른손 버튼
	if(!Rb && Rtb == 0)
	{
		printf("H\n");
		write(sock, "H\n", 2);
		digitalWrite(11,HIGH);
		Rtb = 1;
	}

	if( Rb && Rtb == 1)
	{
		printf("h\n");
		write(sock, "h\n", 2);
		digitalWrite(11,LOW);
		Rtb = 0;
	}

//왼손 오른쪽
	if(adcX1 > 3500 && SX1 == 0)
	{
		printf("D\n");
		write(sock, "D\n", 2);
		digitalWrite(10,HIGH);
		SX1 = 1;
	}
	
	if(adcX1 < 3500 && SX1 == 1)
	{
		printf("d\n");
		write(sock, "d\n", 2);
		digitalWrite(10,LOW);
		SX1 = 0;
	}

//왼손 왼쪽
	if(adcX1 < 500 && CX1 == 0)	
	{
		printf("A\n");
		write(sock, "A\n", 2);
		digitalWrite(5,HIGH);
		CX1 =1;
	}

	if( adcX1 > 500 && CX1 == 1)
	{
		printf("a\n");
		write(sock, "a\n", 2);
		digitalWrite(5,LOW);
		CX1 = 0;
	}

//왼손 위쪽
	if(adcY1 > 3500 && SY1 == 0)
	{
		printf("W\n");
		write(sock, "W\n", 2);
		digitalWrite(4,HIGH);
		SY1 = 1;
	}

	if( adcY1 < 3500 && SY1 == 1)
	{
		printf("w\n");
		write(sock, "w\n", 2);
		digitalWrite(4,LOW);
		SY1 = 0;
	}


//왼손 아래쪽
	if(adcY1 < 500 && CY1 == 0)
	{
		printf("S\n");
		write(sock, "S\n", 2);
		digitalWrite(6,HIGH);
		CY1 = 1;
	}

	if(adcY1 > 500 && CY1 == 1)
	{
		printf("s\n");
		write(sock, "s\n", 2);
		digitalWrite(6,LOW);
		CY1 = 0;
	}

//오른손 오른쪽
	if(adcX2 > 3500 && SX2 == 0)
	{
		printf("L\n");
		write(sock, "L\n", 2);
		digitalWrite(29,HIGH);
		SX2 = 1;
	}

	if(adcX2 < 3500 && SX2 == 1)
	{
		printf("l\n");
		write(sock, "l\n", 2);
		digitalWrite(29,LOW);
		SX2 = 0;
	}

//오른손 왼쪽
	if(adcX2 < 500 && CX2 == 0)
	{
		printf("J\n");
		write(sock, "J\n", 2);
		digitalWrite(27,HIGH);
		CX2 = 1;
	}



	if(adcX2 > 500 && CX2 == 1)
	{
		printf("j\n");
		write(sock, "j\n", 2);
		digitalWrite(27,LOW);
		CX2 = 0;
	}

//오른손 위쪽

	if(adcY2 > 3500 && SY2 == 0)
	{
		printf("I\n");
		write(sock, "I\n", 2);
		digitalWrite(26,HIGH);
		SY2 = 1;
	}

	if(adcY2 < 3500 && SY2 == 1)
	{
		printf("i\n");
		write(sock, "i\n", 2);
		digitalWrite(26,LOW);
		SY2 = 0;
	}

//오른손 아래쪽

	if(adcY2 < 500 && CY2 == 0)
	{
		printf("K\n");
		write(sock, "K\n", 2);
		digitalWrite(28,HIGH);
		CY2 = 1;
	}

	if(adcY2 > 500 && CY2 == 1)
	{
		printf("k\n");
		write(sock, "k\n", 2);
		digitalWrite(28,LOW);
		CY2 = 0;
	}
	
	delay(10);
	}
	return 0;
}