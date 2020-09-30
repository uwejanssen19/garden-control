# 1 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino"
/*

Try to get that dammn thing running inside visual studio

*/
# 4 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino"
# 5 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino" 2
# 6 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino" 2


# 9 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino" 2


# 12 "p:\\dokumente\\uwe\\software_projekte\\rasperry_pi\\My_Raspberry_Pis\\garden-control\\esp8266_modules\\Hello\\HelloWorld.ino" 2


U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2((&u8g2_cb_r0), /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ 255); // All Boards without Reset of the Display

void setup(void) {


  u8g2.begin();
}

void loop(void) {
  u8g2.clearBuffer(); // clear the internal memory
  u8g2.setFont(u8g2_font_ncenB08_tr); // choose a suitable font
  u8g2.drawStr(0,10,"Hello World!"); // write something to the internal memory
  u8g2.sendBuffer(); // transfer internal memory to the display
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
