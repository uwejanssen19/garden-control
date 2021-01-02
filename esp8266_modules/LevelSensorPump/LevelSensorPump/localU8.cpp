#include "localU8.h"

// call CTOR for our purposes
LocalU8Class::LocalU8Class() : U8G2_SSD1306_128X64_NONAME_F_SW_I2C (U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ U8X8_PIN_NONE)
{
	this->result = "";
}

