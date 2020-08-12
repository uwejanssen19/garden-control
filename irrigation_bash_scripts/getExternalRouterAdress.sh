#!/bin/bash
echo $(/bin/ping -c 1 -4 xdbu92gos1irkzbd.myfritz.net | awk '{print $3 }' | head -1| tr -d '()' 2>&1)
