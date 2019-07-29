# get current weather
function getWeather() {
  wget 'http://api.openweathermap.org/data/2.5/weather?id=2886446&units=metric&lang=de&appid=19617de07bcc959ff8dc5e0caffce590' -O /tmp/weather.json
}

function isRaining() {
getWeather
# pretty format single line json to multi line output: python -mjson.tool <file>
ret=$(python -mjson.tool /tmp/weather.json | grep Regen)
if [ $? == "0" ]
then 
  echo currently raining >> /tmp/waterOnOff.log
  ret=1
else 
  echo 'Not raining' >> /tmp/waterOnOff.log
  ret=0
fi
echo $ret
}
