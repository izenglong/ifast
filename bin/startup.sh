#!/bin/bash

jarName=$1
if [ -z "${jarName}" ]
then
    jarName=ifast-1.0.0.jar
fi
profile=$2
if [ -z "${profile}" ]
then
    profile=dev
fi

nohup java -jar ../${jarName} --spring.profiles.active=${profile} >/dev/null 2>&1 &