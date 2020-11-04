
// RECEIVER
#include <RCSwitch.h> //Download the library below
RCSwitch rfsense = RCSwitch();

void setup() {
  Serial.begin(9600);
  mySwitch.enableReceive(0);  // Receiver on interrupt 0 => that is pin #2
  Serial.println("14CORE | RF Sniffer Test Code for Receiver");
  Serial.println("RF Sniffer Initializing......");
  delay(2000);
  Serial.println("Starting.....................")
  delay(2000);
}

void loop() {
  if (rfsense.available()) {
    int value = rfsense.getReceivedValue();
    if (value == 0) {
      Serial.print("Unknown encoding");
    } else {
     Serial.print("Received 0x");
      Serial.print(rfsense.getReceivedValue(),HEX );
      Serial.print(" / ");
      Serial.print(rfsense.getReceivedBitlength() );
      Serial.print("Bit Protocol: ");
      Serial.println(rfsense.getReceivedProtocol() );
    }
    rfsense.resetAvailable();
  }
}