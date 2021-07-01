#!/bin/bash

JAR_ABSOLUTE_PATH=/opt/wol/WakeOnLanHTTPRest.jar

echo "Stopping Service..."
sudo service wol stop

echo "Cleaning space..."
sudo rm -f "$JAR_ABSOLUTE_PATH"
echo "Downloading latest verion..."
runuser -l wol -c "curl -Ls -o \"$JAR_ABSOLUTE_PATH\" \"https://oss.sonatype.org/service/local/artifact/maven/redirect?r=snapshots&g=com.github.tousinho&a=Tinei-Matewai&v=LATEST&e=jar&c=jar-with-dependencies\""

echo "Restaring Service..."
sudo service wol restart
