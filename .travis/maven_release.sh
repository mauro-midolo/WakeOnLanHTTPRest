#!/bin/sh

set -e

mvn clean release:prepare release:perform -Dresume=false --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
git push
