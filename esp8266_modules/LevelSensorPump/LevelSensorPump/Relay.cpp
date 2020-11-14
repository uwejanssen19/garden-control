// 
// 
// 

#include "Relay.h"

void Relay::init()
{


}
const char* Relay::toString() {
    return relayState == OFF ? "OFF" : "ON";
}
boolean Relay::toggleBoolean() {
    boolean newState = (relayState == OFF) ? ON : OFF;
    control(newState);
    return newState;
}
boolean Relay::setState(boolean state) {
    relayState = state;
    return state;
}
void Relay::control(boolean value) {
    if (value) {
        digitalWrite(RELAIS_PIN, LOW);
    }
    else {
        digitalWrite(RELAIS_PIN, HIGH);
    }
    setState(value);
}



//Relay Relay;

