#!/bin/bash
# Uwe Janssen, April 22nd, 2019 
# water level read script
# using GPIO 23 
source ~pi/scripts/irrLib.sh
log "$0 ENTRY"
ret=$(readLevel)
[[ $ret -eq 1 ]] && STATE=$LOW || STATE=$HIGH
log "$0 $STATE "
log "$0 EXIT "
echo "$ret"

