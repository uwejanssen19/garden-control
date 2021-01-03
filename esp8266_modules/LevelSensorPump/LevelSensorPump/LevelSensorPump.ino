/*
Try to get that nice thing running inside visual studio
*/

#include "localU8.h"
#include <Adafruit_BME280.h>
#include "Relay.h"
#include <PubSubClient.h>
#include <WiFiUdp.h>
#include <WiFiServerSecureBearSSL.h>
#include <WiFiServerSecureAxTLS.h>
#include <WiFiServerSecure.h>
#include <WiFiServer.h>
#include <WiFiClientSecureBearSSL.h>
#include <WiFiClientSecureAxTLS.h>
#include <WiFiClientSecure.h>
#include <WiFiClient.h>
#include <ESP8266WiFiType.h>
#include <ESP8266WiFiSTA.h>
#include <ESP8266WiFiScan.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266WiFiGratuitous.h>
#include <ESP8266WiFiGeneric.h>
#include <ESP8266WiFiAP.h>
#include <ESP8266WiFi.h>
#include <CertStoreBearSSL.h>
#include <BearSSLHelpers.h>
#include <Arduino.h>
#include <U8g2lib.h>
#include <VL53L1X.h>
#include "cred.h"
#include <ArduinoOTA.h>


#ifdef U8X8_HAVE_HW_SPI
#include <SPI.h>
#endif
#ifdef U8X8_HAVE_HW_I2C 
#include <Wire.h>
#endif

#define TEMP_RESULT "Temp: "

//U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2(U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ U8X8_PIN_NONE);   // All Boards without Reset of the Display
LocalU8Class u8g2;

VL53L1X sensor;
Relay relay;
unsigned int sensorDistance = 0;
WiFiClient wclient;
PubSubClient mqttClient(wclient);
Adafruit_BME280 bme;
const char* ssid = SSID;
const char* wlanPwd = WLAN_KEY;
const char* mqttServer = "garden-control";
const char* topic = "UweLevelSensor/dist";
const unsigned int mqttPort = 1883;
float outputValue = 0;
unsigned int analogValue = 0;
unsigned int readCount = 0;
void setupOTA();
//float temperature = -50.0;
String tempResult;


void setup(void) {
    setupOTA();
    inithw();
    Serial.println("Read Range Sensor and display at OLED display");
    if (!sensor.init())
    {
        Serial.println("Failed to detect and initialize sensor VL53L1X!");
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
    sensor.startContinuous(1000); // every sec

    // connect to WLAN
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, wlanPwd);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.print("Wifi Connected to ");
    Serial.println(ssid);
    Serial.println("IP Address: ");
    Serial.println(WiFi.localIP());

    // now connect to MQTT server 

    mqttClient.setServer(mqttServer, mqttPort);
    while (!mqttClient.connected()) {
        if (mqttClient.connect(mqttServer)) {
            Serial.println("MQTT connected");
        }
        else {
            Serial.print("mqtt connect failed:");
            Serial.println(mqttClient.state());
            Serial.print(" retry in 5 seconds");
            delay(5000);
        }
    }
}

void loop(void) {
    ArduinoOTA.handle();
    u8g2.clearBuffer();					// clear the internal OLED display memory
    u8g2.setFont(u8g2_font_ncenB08_tr);	// choose a suitable font
    sensorDistance = sensor.read();
    /*    if (sensorDistance < 60) {
            relaisControl(OFF);
        }
        else {
            relaisControl(ON);

        }*/

    analogValue = analogRead(A0);
    Serial.print("sensorDistance = " );
    Serial.println(sensorDistance);
    outputValue = map(analogValue, 0, 1023, 0, 255);
   
    mqttClient.publish(topic, (String("") + sensorDistance).c_str());
    // display measured sensor distance on oled display 
    u8g2.displayValue("Distance: ", sensorDistance, "mm", 11);
    u8g2.displayValue("Count: ", readCount++, "", 22);
    IPAddress localIP = WiFi.localIP();
    String ip = localIP.toString();
    u8g2.displayValue("IP: ", ip.c_str(), "",33);
    u8g2.displayValue("Voltage: ", outputValue, "V", 55);
    if (readCount > 100) {
        Serial.println("Rebooting");
        ESP.restart();
    }
    relay.toggleBoolean();
   //u8g2.drawStr(0, 44, (String("Relay state = ") + relay.toString()).c_str());
 //   temperature = bme.readTemperature();
 //   u8g2.displayValue(TEMP_RESULT, temperature,"", 44);
    u8g2.sendBuffer();	// transfer internal memory to the display
    delay(5000);

}
void setupOTA() {
    ArduinoOTA.onStart([]() {
        String type;
        if (ArduinoOTA.getCommand() == U_FLASH) {
            type = "sketch";
        }
        else { // U_FS
            type = "filesystem";
        }

        // NOTE: if updating FS this would be the place to unmount FS using FS.end()
        Serial.println("Start updating " + type);
        });
    ArduinoOTA.onEnd([]() {
        Serial.println("\nEnd");
        });
    ArduinoOTA.onProgress([](unsigned int progress, unsigned int total) {
        Serial.printf("Progress: %u%%\r", (progress / (total / 100)));
        });
    ArduinoOTA.onError([](ota_error_t error) {
        Serial.printf("Error[%u]: ", error);
        if (error == OTA_AUTH_ERROR) {
            Serial.println("Auth Failed");
        }
        else if (error == OTA_BEGIN_ERROR) {
            Serial.println("Begin Failed");
        }
        else if (error == OTA_CONNECT_ERROR) {
            Serial.println("Connect Failed");
        }
        else if (error == OTA_RECEIVE_ERROR) {
            Serial.println("Receive Failed");
        }
        else if (error == OTA_END_ERROR) {
            Serial.println("End Failed");
        }
        });
    ArduinoOTA.begin();
    Serial.println("Ready");
}


void inithw(void) {
    u8g2.begin(); // init display
    Serial.begin(115200); // Debug port init 
    Wire.begin(); // Start I2C
    Wire.setClock(400000); // use 400 kHz I2C

    sensor.setTimeout(500); // range sensort init
    pinMode(RELAIS_PIN, OUTPUT);
    relay.control(OFF);
    //int status = bme.begin(0x76); // temperature sensor init
    //if (!status) {
        //Serial.println("Could not find a valid BME280 sensor, check wiring, address, sensor ID!");
//        Serial.print("SensorID was: 0x"); Serial.println(bme.sensorID(), 16);
//        Serial.print("        ID of 0xFF probably means a bad address, a BMP 180 or BMP 085\n");
    //}
}

const char* makeMsg(float par)
{
    tempResult = String(TEMP_RESULT) + par;
    return tempResult.c_str();
}
