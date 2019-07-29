#!/bin/bash
source ~/scripts/irrLib.sh
ISRAINING=$(isRaining )
#WILLRAIN=$(willRain | awk 'END {print $NF}')
# only do it if good weather now or expected
log "depressure ENTRY -------" 
if [ $ISRAINING -eq 0 ]
#if [ $ISRAINING -eq 0  -a  $WILLRAIN -eq 0 ]
then
  log "depressure doing its job -------" 
  ~/scripts/waterOnOff.sh 1
  sleep 3
  ~/scripts/waterOnOff.sh 0
fi
log "depressure EXIT  -------" 
