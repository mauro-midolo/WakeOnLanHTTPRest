#!/bin/sh

set -e

mvn release:prepare -B -DdryRun=true -f WakeOnLanHTTPRest
mvn clean release:prepare --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
mvn release:perform --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
git push
