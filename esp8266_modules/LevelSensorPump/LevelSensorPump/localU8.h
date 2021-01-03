// localU8.h

#ifndef _LOCALU8_h
#define _LOCALU8_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif
#include <U8g2lib.h>

#include <iostream>
using namespace std;

class LocalU8Class : public U8G2_SSD1306_128X64_NONAME_F_SW_I2C
{
 private:
	String result;
 protected:

 public:

	LocalU8Class(); 
	template <typename T> void displayValue(char* prefix, T par, char* suffix, int lineNo)
	{
		this->result = String(prefix) + par + String(suffix);
		drawStr(0, lineNo, result.c_str());
	}

};

//extern LocalU8Class LocalU8;

#endif

