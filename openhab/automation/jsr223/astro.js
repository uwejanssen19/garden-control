// triggers:
//   - id: "2"
//     configuration:
//       channelUID: astro:moon:local:rise#event
//     type: core.ChannelEventTrigger
//   - id: "3"
//     configuration:
//       channelUID: astro:sun:local:astroDawn#event
//     type: core.ChannelEventTrigger
//   - id: "4"
//     configuration:
//       channelUID: astro:sun:local:nauticDawn#event
//     type: core.ChannelEventTrigger
//   - id: "6"
//     configuration:
//       channelUID: astro:sun:local:eveningNight#event
//     type: core.ChannelEventTrigger
//   - id: "5"
//     configuration:
//       channelUID: astro:sun:local:rise#event
//     type: core.ChannelEventTrigger
//   - id: "7"
//     configuration:
//       channelUID: astro:sun:local:set#event
//     type: core.ChannelEventTrigger
// conditions: []
// actions:
//   - inputs: {}
//     id: "1"
//     configuration:
//       type: application/javascript
//       script: >2
    // type: script.ScriptAction
    scriptExtension.importPreset("default");
    var log = Java.type("org.slf4j.LoggerFactory").getLogger("org.openhab.model.script.Rules.astro_js");
    var myEvent = event;
    log.info("event  = {}" ,myEvent);
    log.info("converted event  = {}" ,makeAstroEventHumanReadable(myEvent));

//        var mqttActions = actions.get("mqtt","mqtt:broker:myUnsecureBroker");
//    mqttActions.publishMQTT("astroevent","convertedEvent",true);




function makeAstroEventHumanReadable (pEventString) {

astroMapFile = "astroevents.map";
// Format of event : 
//   astro:sun:local:morningNight#event triggered START
//   [0]  :[1]:[2] :[3]
log.info("makeAstroEventHumanReadable::pEventString = " + pEventString + " type  = " + typeof pEventString);
// find last chunk separated by ':'
var astroEventName = pEventString.toString();
log.info("makeAstroEventHumanReadable::astroEventName   = " + astroEventName);
log.info("makeAstroEventHumanReadable::astroEventName  type = " + typeof astroEventName);

astroParts = astroEventName.split(":");

log.info("makeAstroEventHumanReadable::astroParts type  = " + typeof astroParts );
log.info("makeAstroEventHumanReadable::astroParts = " + astroParts );
log.info("makeAstroEventHumanReadable::astroParts length = " + astroParts.length);
log.info("makeAstroEventHumanReadable::astroParts last elem = " + astroParts[astroParts.length-1]);
lastPart = astroParts[astroParts.length-1];
log.info("makeAstroEventHumanReadable::lastPart  = "+ lastPart);
// now we got "<eventName>#event triggered START|END"
// split again and keep first part reusing array
astroParts2 = lastPart.split("#");
log.info("makeAstroEventHumanReadable::astroParts2 = {}", astroParts2);
eventName = astroParts[1]+astroParts2[0];
log.info("makeAstroEventHumanReadable::eventName = {}", eventName);

//userFriendlyName = transform("MAP",astroMapFile,eventName);
//var userFriendlyName = eventName;
//logInfo("","makeAstroEventHumanReadable::userFriendlyName = {}", userFriendlyName);
//  return "userFriendlyName";
return eventName;
}
