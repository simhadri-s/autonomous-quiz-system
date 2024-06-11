#include <IRremote.h>

const int RECV_PIN = 3; // Define the IR receiver pin
const long rm1=0x40BF30CF, rm2=0x79991C01, rm3=0xC1AAA15E, rm4=0xFB00E619, rm5=0xFB00C639;// rm6=, rm7=0xC1AA7986, rm8=0x80BFD12E, rm9=0x80BF23DC, rm10=0x80BFE11E;

IRrecv irrecv(RECV_PIN);
decode_results results;


void setup() {
  Serial.begin(9600);
  irrecv.enableIRIn(); // Start the IR receiver

}

void loop() {
  if (irrecv.decode(&results)) {

    // Print the received data to the Serial Monitor
    // Serial.print("IR Code: 0x");
    // Serial.println(results.value, HEX);

    // Serial.println(results.value);
    
    switch(results.value){
      case rm1:
        Serial.println('1');
        delay(100);
        break;
      
      case rm2:
        Serial.println('2');
        delay(100);
        break;
      
      case rm3:
        Serial.println('3');
        delay(100);
        break;

      case rm4:
        Serial.println('4');
        delay(100);
        break;

      case rm5:
        Serial.println('5');
        delay(100);
        break;
      
      // case rm6:
      //   Serial.println('6');
      //   delay(100);
      //   break;
      
      // case rm7:
      //   Serial.println('7');
      //   delay(100);
      //   break;

      // case rm8:
      //   Serial.println('8');
      //   delay(100);
      //   break;

      // case rm9:
      //   Serial.println('9');
      //   delay(100);
      //   break;

      // case rm10:
      //   Serial.println('10');
      //   delay(100);
      //   break;
    }
    irrecv.resume(); // Receive the next value
  }
}
