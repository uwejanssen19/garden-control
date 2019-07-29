#!/bin/bash
source ~pi/scripts/irrLib.sh
log "irrOnOff ENTRY ------------------" 
#set -x 
log "Parameter for $0 : $1" 
if [ $1 -eq 1 ]
then
  ISRAINING=$(isRaining )
  WILLRAIN=$(willRain | awk 'END {print $NF}')
# only irrigate if wanted AND not raining
  if [ $ISRAINING -eq 0 ]  && [ $WILLRAIN -ne 1 ] 
  then
    irrOnOff 1
  fi
else
  irrOnOff 0 
fi
# tell reason for decision in log file 
if [ $1 -eq 1 ] 
then 
  if [ $ISRAINING -ne 0 ]
  then 
        log "It is raining, do NOT irrigate" 
  fi
  if [ $WILLRAIN -ne 0 ]  
  then 
    log "It is going to rain, do NOT irrigate" 
  fi
fi

#set +x
log "irrOnOff EXIT  ------------------" 
