// Transmitter code
#include <RCSwitch.h> //Download this library below
RCSwitch rfsense = RCSwitch();

void setup() {
Serial.begin(9600);
Serial.println("14CORE | RF Sniffer Test Code for Transmitter");
Serial.println("RF Transmitter Initializing......");
delay(2000);
Serial.println("Starting.....................")
delay(2000);
display_Running_Sketch();
  // Data transmission 
pinMode(2, OUTPUT);  
rfsense.enableTransmit(2);
delay(1000);
}
  void loop() {
  static int pos =0;
  unsigned long value;
  unsigned long channel = 0x5f5500;
  int sound[] = {0x00, 0x01, 0x04, 0x05,
                 0x10, 0x11, 0x14, 0x15,
                 0x40, 0x41, 0x44, 0x45,
                 0x50, 0x51, 0x54, 0x55};

  int pmax = sizeof(sound) / sizeof(int);
  Serial.print("pmax=");
  Serial.println(pmax);

  value = channel + sound[pos];
  Serial.print("value=");
  Serial.println(value,16);
  rfsense.send(value, 24);
  rfsense.send(value, 24);
  rfsense.send(value, 24);
  pos++;
  if (pos == pmax) pos = 0;
  delay(30000);
}