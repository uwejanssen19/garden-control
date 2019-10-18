#!/bin/bash
RANGE=30
FLOOR=2
source /home/pi/scripts/irrLib.sh
log "$0 ENTRY"
# May combine above two techniques to retrieve random number between two limits.
number=0   #initialize
while [ "$number" -le $FLOOR ]
do
  number=$RANDOM
  let "number %= $RANGE"  # Scales $number down within $RANGE.
done
log "$0 EXIT, result = $number"
echo "Random number between $FLOOR and $RANGE ---  $number"
