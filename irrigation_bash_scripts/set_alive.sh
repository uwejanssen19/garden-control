#!/bin/bash
source /home/pi/scripts/irrLib.sh
log "$0 ENTRY ---------------" 
#set -x 
/bin/date +"%d %H %M " > /tmp/openhab_alive
log "$0 EXIT  ------------------" 
