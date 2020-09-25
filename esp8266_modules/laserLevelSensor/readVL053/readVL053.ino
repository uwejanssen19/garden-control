#include "Adafruit_VL53L0X.h"
#include "Wire.h"
 
Adafruit_VL53L0X lox = Adafruit_VL53L0X();
int retCode = 0; 
void setup()
{
Serial.begin(115200);
 
// wait until serial port opens for native USB devices
while (! Serial)
{
delay(1);
}
 
Serial.println(F("Adafruit VL53L0X test"));

retCode = lox.begin();
if (retCode == false)
{
Serial.println(F("Failed to boot VL53L0X"));
while(1);
}
// power
Serial.println(F("VL53L0X API Simple Ranging example\n\n"));
}
 
void loop()
{
VL53L0X_RangingMeasurementData_t measure;
 
Serial.print(F("Reading a measurement... "));
lox.rangingTest(&measure, false); // pass in 'true' to get debug data printout!
 
if (measure.RangeStatus != 4)
{ // phase failures have incorrect data
Serial.print(F("Distance (mm): ")); Serial.println(measure.RangeMilliMeter);
}
else
{
Serial.println(F(" out of range "));
}
 
delay(1000);
}

 
