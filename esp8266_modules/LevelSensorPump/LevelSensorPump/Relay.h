// Relay.h

#ifndef _RELAY_h
#define _RELAY_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#define OFF (boolean)false
#define ON (boolean)true
#define RELAIS_PIN D3 

class Relay
{
 private:
	boolean relayState = OFF;

 protected:


 public:
	void init();
	const char* toString();
	boolean toggleBoolean();
	boolean setState(boolean state);
	void control(boolean state);
};

//extern Relay relay;

#endif

