#!/bin/bash
###########################################################
#	Developement to John00Santos (DevOps Enginner)        #
#	version 1.0					                          #
###########################################################
PATH=/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin

checkValueFalied(){

	if [ $groupId == "" ] || [ $packaging == "" ];
	then
		echo "Error Fields groupId packaging is Compulsory"
		exit 1
	fi
}

checkSucess(){
	
	if [ $? != 0 ];
	then
		echo "Error upload package"
		exit 1	
	fi

}

groupId=$1
checkSucess

packaging=$2
checkSucess

artifactId="`ls target/*.war|awk -F '.war' '{print $1}'|awk -F '/' '{print $2}'|awk -F 'web' '{print $1"web"}'`"
checkSucess

versionPackage="`ls target/*.war|awk -F 'target/dasa-higia-api-vo-' '{print $2}'|awk -F '.war' '{print $1}'`"
checkSucess

packageName="`ls target/*.war`"
checkSucess

checkValueFalied

# Upload artifact for nexus
mvn deploy:deploy-file -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$versionPackage -DgeneratePom=true -Dpackaging=$packaging -DrepositoryId=nexus -Durl=http://nx.dasa.com.br:8081/repository/dasa-higia-api-vo/ -Dfile=$packageName -s devops/configs/settings.xml

checkSucess