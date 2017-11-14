#!/bin/bash
###########################################################
#	Developement to John00Santos (DevOps Enginner)        #
#	version 1.0		                          #
###########################################################

setPackage(){
	
	if [ $? != 0 ];
	then
		echo "Error Set package version"
		exit 1	
	fi

}

sed -i "s/SNAPSHOT/$BUILD_NUMBER/g" devops/configs/web.config
setPackage
sed -i "s/SNAPSHOT/$BUILD_NUMBER/g" pom.xml
setPackage
sed -i "s/CHANGEREVISION/$BUILD_NUMBER/g" devops/version/application.properties
setPackage
sed -i "s/CHANGEREVISION/$BUILD_NUMBER/g" src/main/resources/application.yml
setPackage
