#!/bin/bash
source ~pi/scripts/irrLib.sh
log "waterOnOff ENTRY, set to $1 -------"
waterOnOff $1
log "waterOnOff EXIT  ------------------"
