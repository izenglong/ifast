#!/bin/bash

jarName=$1
if [ -z "${jarName}" ]
then
    jarName=ifast-1.0.0.jar
fi
ps -ef | grep ${jarName} | grep -v grep | grep -v deploy | awk '{print $2}' | xargs kill -9