#!/bin/bash
# Uwe Janssen, April 23rd, 2019 
LOW="water low"
HIGH='water ### HIGH ###'
STATE=""
IRRIGATION_PUMP_PORT=17
TAP_WATER_VALVE_PORT=18
LEVEL_SENSOR_PORT=23
LOG_FILE=/tmp/waterOnOff.log
CURRENT_WEATHER_RESPONSE=/tmp/weather.json
MAX_READ_COUNT=2
FORECAST=/tmp/forecast.json

function irrOnOff () {
  # Irrigation Punp Control
  gpio -g mode $IRRIGATION_PUMP_PORT out
  gpio -g write $IRRIGATION_PUMP_PORT $1
  [[ $1 == "1" ]] && STATE="IRRIGATION ON" || STATE="IRRIGATION OFF" 
}

function waterOnOff () {
  # Water valve Control
  # using GPIO $TAP_WATER_VALVE_PORT 
  [[ $1 == "1" ]] && STATE="WATER ON" || STATE="WATER OFF" 
  gpio -g mode $TAP_WATER_VALVE_PORT out
  gpio -g write $TAP_WATER_VALVE_PORT $1
}

function readLevel {
  # Uwe Janssen, May 7th, 2019 
  # water level read script
  # using GPIO $LEVEL_SENSOR_PORT 
  #
  # When high water mark is reached the GPIO pin $LEVEL_SENSOR_PORT values will be 0
  # Thus '1' means low water and '0' means high water
  # set mode of port to input
  log "readLevel ENTRY"
  gpio -g mode $LEVEL_SENSOR_PORT up

  #read until value is stable
  newVal=0
  oldVal=1
  count=0

  log "turn pump on in order to have power supply for level sensor relais" 
  irrOnOff 1
  while [ $oldVal -ne $newVal ] || [ $count -lt $MAX_READ_COUNT  ]
  do
    oldVal=$(gpio -g read $LEVEL_SENSOR_PORT)
    sleep 2
    newVal=$(gpio -g read $LEVEL_SENSOR_PORT)
    ((count++))
  done
  irrOnOff 0
  log "turn pump OFF"
  log "readLevel EXIT "
  echo $newVal
}

function init () {
  # gpio $LEVEL_SENSOR_PORT is input and pulled up
    gpio -g mode $LEVEL_SENSOR_PORT in
  # gpio $IRRIGATION_PUMP_PORT and $TAP_WATER_VALVE_PORT output
    gpio -g mode $IRRIGATION_PUMP_PORT out
    gpio -g mode $TAP_WATER_VALVE_PORT out
  # irrigation pump OFF
    gpio -g write $IRRIGATION_PUMP_PORT 0
  # water valve OFF
    gpio -g write $TAP_WATER_VALVE_PORT 0
}
function getWeather() {
# get current weather
  wget 'http://api.openweathermap.org/data/2.5/weather?id=2886446&units=metric&lang=de&appid=19617de07bcc959ff8dc5e0caffce590' -O $CURRENT_WEATHER_RESPONSE
}

function isRaining() {
  getWeather
  # pretty format single line json to multi line output: python -mjson.tool <file>
  ret=$(python -mjson.tool $CURRENT_WEATHER_RESPONSE | grep -i regen)
  if [ $? == "0" ]
  then 
    log 'currently raining' 
    ret=1
  else 
    log 'Not raining' 
    ret=0
  fi
  echo $ret
}

function getForeCast() {
  # retrieve 3*8 hours' forecast , i.e. 24h 
  wget 'http://api.openweathermap.org/data/2.5/forecast?id=2886446&units=metric&lang=de&cnt=8&appid=19617de07bcc959ff8dc5e0caffce590' -O $FORECAST
}
function willRain() {
  getForeCast
  #RAIN_SECTION_SAMPLE='"rain":{"3h":1.312},'
  PREC=0;
  # pretty format single line json to multi line output: python -mjson.tool <file>
  # find the 'rain:' lines and calculate the sum
  for line in $(python -mjson.tool $FORECAST | grep '3h":' | cut -d ':' -f 2 | tr -d ' ')
  do PREC=$(python -c "print $PREC+$line")
  done
  logp "precipitation forecast = $PREC"
  # use bc since bash cannot do floats
  ret=$(bc <<< "$PREC > 3.0")
  # return only the result
  #echo $ret
  echo "0"
}

function getText() {
  localDate=$(getDate)
  localText="$localDate --- $1"
  echo $localText
}

function log() {
  getText "$1" >> $LOG_FILE
}

function logp() {
  getText "$1" | tee -a $LOG_FILE
}

function getDate() {
  date +" %d.%m.%Y %H:%M:%S"
}