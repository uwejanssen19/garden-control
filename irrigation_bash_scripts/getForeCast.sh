#!/bin/bash
# gets forecast of next 24 hours
source /home/openhabian/scripts/irrLib.sh
# BEGIN  main 
echo "isRaining:"
isRaining 2>/dev/null
willRain 2>/dev/null
# END    main 
