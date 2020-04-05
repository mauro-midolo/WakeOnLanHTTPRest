#!/bin/bash

set -e

mvn clean initialize release:prepare release:perform --batch-mode --settings .travis/settings.xml -f WakeOnLanHTTPRest -DskipTests
