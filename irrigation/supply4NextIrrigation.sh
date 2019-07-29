#!/bin/bash
source ~pi/scripts/irrLib.sh
FILL_UNITS=10
MAX_FILL_TIME=240
NO_RAIN=0
# Description
# purpose is to fill up the barrel a bit such as to ensure the next irrigation will succeed
# so we will fill tap water up in steps of  $FILL_UNITS seconds until MAX_FILL_TIME elapsed
# (counter = $MAX_FILL_TIME/$FILL_UNITS)
# OR: high water mark is reached - whichever occurs first
# EXCEPT: it is going to rain within the next 24 hours more than specified in irrLib.sh::willRain 
# In this case we leave the script without turning on tap water
# 
log "supply ENTRY ------------------"
willRainResult=$(willRain |  awk 'END {print $NF}')
[[ $willRainResult -eq 1 ]] && logp "it will rain within next 24 hrs"  || logp "no rain expected"
MAX_COUNT=$(bc <<< "$MAX_FILL_TIME/$FILL_UNITS")
EMPTY="1"
    # do not fill up if willRain is true
    if [  $willRainResult -eq $NO_RAIN  ] # if no rain -> check if full else do nothing
    then
      counter=0
      while [ $counter -lt $MAX_COUNT ]
      do
        log "water ON  ------------------"
	      level=$(readLevel)
        logp "level = $level"
#	      if [ "$EMPTY" -eq "1" ] 
	      if [ "$level" -eq "$EMPTY" ] 
	      then
          log "start filling barrel with tap water"
          waterOnOff 1
          log "Start waiting $FILL_UNITS secs"
          sleep $FILL_UNITS # wait for water level to rise
          log "End   waiting $FILL_UNITS secs"
          ((counter++))
          logp "counter = $counter"
        else 
          log 'Water level high -> do not fill'
        break
	      fi
      done
      waterOnOff 0
      log 'FINISHED supplying for next irrigation'
    fi
log "supply EXIT  ------------------"
