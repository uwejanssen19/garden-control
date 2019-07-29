#!/bin/bash
source ~pi/scripts/irrLib.sh
# begin test
echo "BEGIN TEST ########################################"
log "BEGIN TEST ########################################"
getText "my text"
log "simple log 1 ENTRY ------------------"
log "simple log 1 EXIT  ------------------"
var1="VAR 1"
log "log with variable = $var1 "
logp "log to file and output ENTRY"
logp "log to file and output EXIT "
#source ./irrOnOff.sh 1
#source ./irrOnOff.sh 0
readLevel
echo "END   TEST ########################################"
log "END   TEST ########################################"
