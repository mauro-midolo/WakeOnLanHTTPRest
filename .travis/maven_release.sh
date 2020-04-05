#!/bin/bash

set -e

mvn clean initialize release:prepare release:perform -B --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
