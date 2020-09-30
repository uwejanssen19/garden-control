#line 1 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino"
/*
Try to get that dammn thing running inside visual studio
*/
#include <Arduino.h>
#include <U8g2lib.h>

#ifdef U8X8_HAVE_HW_SPI
#include <SPI.h>
#endif
#ifdef U8X8_HAVE_HW_I2C 
#include <Wire.h>
#endif

U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2(U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ U8X8_PIN_NONE);   // All Boards without Reset of the Display

#line 16 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino"
void setup(void);
#line 22 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino"
void loop(void);
#line 16 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino"
void setup(void) {


  u8g2.begin();
}

void loop(void) {
  u8g2.clearBuffer();					// clear the internal memory
  u8g2.setFont(u8g2_font_ncenB08_tr);	// choose a suitable font
  u8g2.drawStr(0,10,"Hello World!");	// write something to the internal memory
  u8g2.sendBuffer();					// transfer internal memory to the display
  delay(1000);  
}

/*
void setup() {
  Serial.begin(115200);
}
void loop() {
    Serial.println("Hello World 1.0 !");
    delay(1000);
}
*/
