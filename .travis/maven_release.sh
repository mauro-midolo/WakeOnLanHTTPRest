#!/bin/bash

set -e

mvn clean release:prepare --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
mvn release:perform --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
git push
