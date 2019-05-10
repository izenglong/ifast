#!/bin/bash

jarName=$1
echo " ---  "
ps -ef | grep ${jarName} | grep -v grep | grep -v deploy | awk '{print $2}' | xargs kill -9