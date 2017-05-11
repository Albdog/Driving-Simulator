#define POTENTIOMETER_PIN 0

char inputs   [20];
char oldInputs[20];
int gnd1 = 13;
int echo = 12;
int trig = 11;
int vcc1 = 10;
int cnt = 1;
float f;
int to = 25000; //max time

void setup() {
    Serial.begin(9600);
    pinMode(vcc1,OUTPUT);  //to connect to Vcc
    pinMode(echo, INPUT);      
    pinMode(trig, OUTPUT);   
    pinMode(gnd1, OUTPUT);
    digitalWrite(gnd1,LOW); //to ground sonar gnd pin
    digitalWrite(vcc1,HIGH); //to Vcc=+5V
    digitalWrite(trig,0); //init trig = 0
}

void getInputs(){   
    sprintf(inputs, "SS:%03X",
        analogRead(POTENTIOMETER_PIN)
    );
}

int ch;
void loop() {
    getInputs();
    if( strcmp(inputs, oldInputs) != 0){
        strcpy(oldInputs, inputs);
        Serial.println(inputs);
    }
    
    if(Serial.available()){
        int ind=0;
        char buff[5];
        while(Serial.available()){
            unsigned char c = Serial.read();
            buff[ind] = c;
            if(ind++ > 6) break;
        }
        buff[2]=0;       
    }

    digitalWrite(trig,0);  
    delay(10);
    digitalWrite(trig,HIGH);
    delayMicroseconds(10);
    digitalWrite(trig,LOW);
    cnt = pulseIn(echo,1,to); //measures t in us
    f= (cnt/2000.0)*346; //distance in mm

    if (cnt > 0) {
      dispsonar();
    }  
}

void dispsonar() {
    Serial.print("dis:");
    Serial.println(f);
}

