// triggers:
//   - id: "18"
//     configuration:
//       channelUID: astro:moon:local:set#event
//     type: core.ChannelEventTrigger
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
//   - id: "10"
//     configuration:
//       channelUID: astro:sun:local:civilDawn#event
//     type: core.ChannelEventTrigger
//   - id: "11"
//     configuration:
//       channelUID: astro:sun:local:night#event
//     type: core.ChannelEventTrigger
//   - id: "12"
//     configuration:
//       channelUID: astro:sun:local:daylight#event
//     type: core.ChannelEventTrigger
//   - id: "6"
//     configuration:
//       channelUID: astro:sun:local:eveningNight#event
//     type: core.ChannelEventTrigger
//   - id: "8"
//     configuration:
//       channelUID: astro:sun:local:morningNight#event
//     type: core.ChannelEventTrigger
//   - id: "5"
//     configuration:
//       channelUID: astro:sun:local:rise#event
//     type: core.ChannelEventTrigger
//   - id: "7"
//     configuration:
//       channelUID: astro:sun:local:set#event
//     type: core.ChannelEventTrigger
//   - id: "9"
//     configuration:
//       channelUID: astro:sun:local:noon#event
//     type: core.ChannelEventTrigger
//   - id: "13"
//     configuration:
//       channelUID: astro:sun:local:astroDusk#event
//     type: core.ChannelEventTrigger
//   - id: "14"
//     configuration:
//       channelUID: astro:sun:local:civilDusk#event
//     type: core.ChannelEventTrigger
//   - id: "15"
//     configuration:
//       channelUID: astro:sun:local:nauticDusk#event
//     type: core.ChannelEventTrigger
// conditions: []
// actions:
//   - inputs: {}
//     id: "1"
//     configuration:
//       type: application/javascript
//       script: >2
            var log = Java.type("org.slf4j.LoggerFactory").getLogger("org.openhab.model.script.Rules.astro_js");
            var convertedEvent = makeAstroEventHumanReadable(event);
        //        log.info("event  = {}" ,convertedEvent);
            log.info("converted event  = {}" ,convertedEvent);
            // send converted event via mqtt
            var mqttActions = actions.get("mqtt","mqtt:broker:myUnsecureBroker");
            mqttActions.publishMQTT("astroevent", convertedEvent, true);

            function makeAstroEventHumanReadable (pEventString) {   
            // Format of event :    
            //   astro:sun:local:morningNight#event triggered START   
            //   [0]  :[1]:[2] :[3]   
            // we want to return the "morningNight" part   
            //log.info("makeAstroEventHumanReadable::pEventString = " + pEventString + " type  = " + typeof pEventString);   
            var astroEventName = pEventString.toString();   
            //log.info("makeAstroEventHumanReadable::astrooEventName   = " + astroEventName);   
            astroParts = astroEventName.split(":");   
            // find last chunk separated by ':'   
            lastPart = astroParts[astroParts.length-1];   
            //log.info("makeAstroEventHumanReadable::lastPart  = "+ lastPart);   
            // now we got "<eventName>#event triggered START|END"   
            // split again to get the actual event   
            astroParts2 = lastPart.split("#");   
            //log.info("makeAstroEventHumanReadable::astroParts2 = {}", astroParts2);  
            // map file contains:  
            // sunrise | sunset for the sun BUT  
            // rise | set for the moon   
            // (not very tidy but i am too lazy since I am not even sure wether to keep moon rise and set anyway)

              if ((astroParts[1] === "sun") && (astroParts2[0] === "rise" || astroParts2[0] === "set")) {
                eventName = astroParts[1]+astroParts2[0];
              } else {
                eventName = astroParts2[0];
              }
                log.info("makeAstroEventHumanReadable::eventName = {}", eventName);   
              astroMapFile = "astroevents.map";   
              var Transformation = Java.type("org.openhab.core.transform.actions.Transformation");   
              userFriendlyName = Transformation.transform("MAP",astroMapFile , eventName);   
              //log.info("makeAstroEventHumanReadable::userFriendlyName = {}", userFriendlyName);   
              return userFriendlyName; 
            }
    // type: script.ScriptAction
