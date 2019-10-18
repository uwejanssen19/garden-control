#!/bin/bash
source /home/pi/scripts/irrLib.sh
log "$0 ENTRY"
number=$(wget -qO - http://ipecho.net/plain | xargs echo)
log "$0 EXIT, result = $number"
echo $number
