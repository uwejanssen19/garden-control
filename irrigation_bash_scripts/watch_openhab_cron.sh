#!/bin/bash
source /home/pi/scripts/irrLib.sh
#log " ENTRY $1 ---------------" 
set -x 
FILE_TO_CHECK=/tmp/openhab_alive
FOLDER_TO_CHECK=$(dirname $FILE_TO_CHECK)
CURR_DAY=$(date +"%d")
CURR_HOUR=$(date +"%H")
[ -f $FILE_TO_CHECK ]
RESULT=$?
if (( $RESULT == 0 )) 
then
    # file exists, so check if it is new enough
    # if the file's age is not older than 30 minutes
    # Daemon reoccurs every 10 minutes and after a reboot it will take at most 20 minutes 
    # the cron jobs start atll. Thus, after 30 minutes there should be a fresh $FILE_TO_CHECK
    # -> o.k.
    find FOLDER_TO_CHECK -mmin +9 2>/dev/null | grep $(basename $FILE_TO_CHECK)
    # not found a too old file ? -> o.k.
    if (( $? != 0 ))
    then
      # o.k.
      log "$FILE_TO_CHECK ONTENTS = $(cat $FILE_TO_CHECK) -> OK"
    else # too old 
      log "file $FILE_TO_CHECK too old -> REBOOT"
      # turn off everything without openhab since it is no longer reliable
      irrOnOff 0
      waterOnOff 0 
      # stop services
      # try turning off power
      #mosquitto_pub -t "cmnd/PowerSupplyGardenControl" -value "OFF"
      echo "sudo service grafana-server stop 2>&1"
      echo "sudo sudo service mosquitto stop 2>&1"
      echo "sudo sudo service influxdb stop 2>&1"
      echo "sudo sudo service openhab stop 2>&1"
      echo "sudo shutdown --no-wall -r +1"
    endif
endif
#log "irrOnOff EXIT  ------------------" 
