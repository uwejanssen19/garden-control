#!/bin/bash
source ~pi/scripts/irrLib.sh
log "irrOnOff ENTRY $1 ---------------" 
#set -x 
if [ $1 -eq 1 ]
then
  irrOnOff 1
else
  irrOnOff 0 
fi
log "irrOnOff EXIT  ------------------" 
