class Key {
private:
    const int pin;
    const char letter;
    HardwareSerial* os;
    bool lastStatus = true;

    void press_key() {
        os->write(letter);
        os->write('\n');
    }

    void release_key() {
        os->write(letter | 0x20);
        os->write('\n');
    }

public:
    Key(int input_pin, char output_letter, HardwareSerial* ostream = &Serial) 
    : pin(input_pin), letter(output_letter), os(ostream) {
        pinMode(pin, INPUT_PULLUP);
    }

    void update() {
        bool s = digitalRead(pin);
        
        // falling edge
        if(lastStatus && !s) {
            press_key();
        }
        // rising edge
        else if(!lastStatus && s) {
            release_key();
        }

        lastStatus = s;
        
    }
};

Key* k = new Key[10] {
    Key(A4, 'W'),
    Key(A3, 'A'),
    Key(A2, 'S'),
    Key(A1, 'D'),
    Key(A0, 'F'),
    Key(6, 'I'),
    Key(7, 'J'),
    Key(8, 'K'),
    Key(9, 'L'),
    Key(10, 'H')
};

void setup() {
    Serial.begin(115200);

}

void loop() {
    for(int i = 0; i < 10; i++) {
        k[i].update();
    }
    delay(10);
}
