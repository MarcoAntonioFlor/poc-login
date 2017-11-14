#!/bin/bash
###########################################################
#	Developement to John00Santos (DevOps Enginner)        #
#	version 1.0					                          #
###########################################################
PATH=/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin

# Compile package
mvn compile -U -s $WORKSPACE/devops/configs/settings.xml

if [ $? == '0' ];
then
    echo "Was execute mvn compile with successfully"
else
    echo "Failed execute mvn compile"
    exit 1
fi