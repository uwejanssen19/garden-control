#!/bin/bash
source /home/openhabian/scripts/irrLib.sh
log "waterOnOff ENTRY, set to $1 -------"
waterOnOff $1
log "waterOnOff EXIT  ------------------"
