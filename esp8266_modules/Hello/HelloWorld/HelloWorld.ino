/*
Try to get that nice thing running inside visual studio
*/
#include <Arduino.h>
#include <U8g2lib.h>
//#include <U8x8lib.h>
#include <VL53L1X.h>


#ifdef U8X8_HAVE_HW_SPI
#include <SPI.h>
#endif
#ifdef U8X8_HAVE_HW_I2C 
#include <Wire.h>
#endif

U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2(U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ U8X8_PIN_NONE);   // All Boards without Reset of the Display

String result = String("");
VL53L1X sensor;
int distance = 0;

void setup(void) {
  Serial.println("Hello World 1.1 !");
  u8g2.begin();
  Serial.begin(115200);
  Wire.begin();
  Wire.setClock(400000); // use 400 kHz I2C

  sensor.setTimeout(500);
  if (!sensor.init())
  {
      Serial.println("Failed to detect and initialize sensor!");
      while (1);
  }

  // Use long distance mode and allow up to 50000 us (50 ms) for a measurement.
  // You can change these settings to adjust the performance of the sensor, but
  // the minimum timing budget is 20 ms for short distance mode and 33 ms for
  // medium and long distance modes. See the VL53L1X datasheet for more
  // information on range and timing limits.
  sensor.setDistanceMode(VL53L1X::Long);
  sensor.setMeasurementTimingBudget(50000);

  // Start continuous readings at a rate of one measurement every 50 ms (the
  // inter-measurement period). This period should be at least as long as the
  // timing budget.
  sensor.startContinuous(1000);
  
}

void loop(void) {
  result = String("Dist = ");
  distance = sensor.read();
  u8g2.clearBuffer();					// clear the internal memory
  u8g2.setFont(u8g2_font_ncenB08_tr);	// choose a suitable font
  u8g2.drawStr(0, 11, "Hello v1.1");	// write something to the internal memory
  u8g2.drawStr(0, 22, "from VS mic!");	// write something to the internal memory
  
  
  result += distance;
  Serial.println(result);
  u8g2.drawStr(0, 33, result.c_str());	// write something to the internal memory

  u8g2.sendBuffer();					// transfer internal memory to the display

  delay(1000);  
}
