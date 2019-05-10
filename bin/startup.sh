#!/bin/bash

jarName=$1
profile=$2

nohup java -jar ../${jarName} --spring.profiles.active=${profile} >/dev/null 2>&1 &