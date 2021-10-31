triggers:
  - id: "2"
    configuration:
      channelUID: astro:moon:local:rise#event
    type: core.ChannelEventTrigger
  - id: "3"
    configuration:
      channelUID: astro:sun:local:astroDawn#event
    type: core.ChannelEventTrigger
  - id: "4"
    configuration:
      channelUID: astro:sun:local:nauticDawn#event
    type: core.ChannelEventTrigger
  - id: "6"
    configuration:
      channelUID: astro:sun:local:eveningNight#event
    type: core.ChannelEventTrigger
  - id: "5"
    configuration:
      channelUID: astro:sun:local:rise#event
    type: core.ChannelEventTrigger
  - id: "7"
    configuration:
      channelUID: astro:sun:local:set#event
    type: core.ChannelEventTrigger
conditions: []
actions:
  - inputs: {}
    id: "1"
    configuration:
      type: application/javascript
      script: >2
               scriptExtension.importPreset("default");
                var log = Java.type("org.slf4j.LoggerFactory").getLogger("org.openhab.model.script.Rules.a2");
                var myEvent = event;
                log.info("event  = {}" ,myEvent);
    type: script.ScriptAction
