#!/bin/bash
###########################################################
#	Developement to John00Santos (DevOps Enginner)        #
#	version 1.0					                          #
###########################################################

# print usage
function usage() {
  cat << EOUSAGE
usage: $0 options

$0 is a script to help deploy in azure webapp.

OPTIONS:
  -h                Show this message
  -u <appId>     	  Azure AppID [required]
  -p <password>  	  Password to AppID [required]
  -t <tenant>       TenantID [required]
  -a <webapp name>	WebAPP name to deploy [required]
  -r <rsg name>		  Resource Group name [required]
  -e <env>          Enviroment to deploy
EOUSAGE
}

# ##
# # Parse command line arguments and set global vars
parse_cmd_args() {

  # parse cmd line options
  while getopts ":hu:p:t:a:r:e:" optname; do
    case "$optname" in
      "h")
        usage
        exit 0
        ;;
      "u")
        APPID_NAME=$OPTARG
        echo "Setting AppID of application $APPLICATION_NAME"
        ;;
      "p")
        APPID_PASSWORD=$OPTARG
        echo "Setting password to $APPID_NAME"
        ;;
      "t")
		    TENANTID=$OPTARG	
	    echo "Setting TenantID to $APPID_NAME"
        ;;
      "a")
		    APPLICATION_NAME=$OPTARG	
	    echo "Setting Application to deploy"
        ;;
      "r")
        RSG=$OPTARG
        echo "Setting RSG to deploy"
        ;;                
      "e")
        ENVIRONMENT=$OPTARG
        echo "Setting deploy in $ENVIRONMENT"
        ;;
      "?")
        echo "Unknown option $OPTARG"
        usage
        exit 1
        ;;
      ":")
        echo "No argument value for option $OPTARG"
        usage
        exit 1
        ;;
      *)
        echo "Unknown error while processing options"
        usage
        exit 1
        ;;
    esac
  done

  # check required params
  if [ "$APPID_NAME" == "" ];
  then
    usage
    exit 1
  fi

  if [ "$APPID_PASSWORD" == "" ];
  then
    usage
    exit 1
  fi

  if [ "$TENANTID" == "" ];
  then
    usage
    exit 1
  fi

}

checkSucessful(){
	
	if [ $? != 0 ];
	then
		echo "Error deploy package"
		exit 1	
	fi

}

credentialsDeploy(){

  source devops/version/application.properties

  if [ "$ENVIRONMENT" == "qa" ];
  then

    HOST='waws-prod-cq1-011.ftp.azurewebsites.windows.net'
    USER="HigiaWebDEV\dasa-ci-deploy"
    PASSWD='Dasa12345678'
    FILE="dasa-higia-web-0.0.1-$REVISION.war"
  fi

  if [ "$ENVIRONMENT" == "hm" ];
  then

    HOST='waws-prod-cq1-009.ftp.azurewebsites.windows.net'
    USER="higiaweb\dasa-ci-deploy"
    PASSWD='Dasa12345678'
    FILE="dasa-higia-web-0.0.1-$REVISION.war"
    
  fi

  if [ "$ENVIRONMENT" == "prod" ];
  then

    HOST='waws-prod-cq1-009.ftp.azurewebsites.windows.net'
    USER="higia-prod-frontend\dasa-ci-deploy"
    PASSWD='Dasa12345678'
    FILE="dasa-higia-web-0.0.1-$REVISION.war"
    
  fi

}

deployFTP(){

amb=$1
rollback=$2

credentialsDeploy

for i in `echo "$FILE"`
do

	cd $WORKSPACE/target

      if [ ! -f dasa-higia-web-0.0.1-$REVISION.war ];
      then
        echo "This artifact does not exist"
        echo "Check the WORKSPACE in target folder"
        exit 1
      fi

ftp -p -i -n $HOST <<END_SCRIPT
	quote USER "$USER"
	quote PASS $PASSWD
	bin
	cd site/wwwroot/webapps
  mdel dasa-*
	del ROOT.war
  put $i
  rename $i ROOT.war
	ls
	quit
END_SCRIPT
	checkSucessful
done

}

AzureAuth(){
  az login -u $APPID_NAME -p $APPID_PASSWORD --service-principal --tenant $TENANTID
  checkSucessful
}

DeployApp(){

  AzureAuth
  az webapp stop --name $APPLICATION_NAME --resource-group $RSG
  checkSucessful
  sleep 2
  deployFTP
  sleep 2
  AzureAuth
  az webapp start --name $APPLICATION_NAME --resource-group $RSG 
  checkSucessful
}

parse_cmd_args "$@"
DeployApp
