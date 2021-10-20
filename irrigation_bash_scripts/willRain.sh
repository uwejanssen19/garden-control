#!/bin/bash
# gets forecast for rain for next 24 hours
source /home/openhabian/scripts/irrLib.sh
# BEGIN  main 
willRain 2>/dev/null | grep -v -i precipitation
# END    main 
