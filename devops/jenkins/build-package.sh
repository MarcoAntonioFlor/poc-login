#!/bin/bash
###########################################################
#	Developement to John00Santos (DevOps Enginner)        #
#	version 1.0					                          #
###########################################################
PATH=/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin

mvn clean
	
	if [ $? != 0 ];
	then
		echo "Error exec mvn clean"
		exit 1	
	fi

mvn package -DskipTests -U -s $WORKSPACE/devops/configs/settings.xml

	if [ $? != 0 ];
	then
		echo "Error exec mvn install"
		exit 1
	fi