#!/bin/bash

set -e

git checkout master
mvn initialize release:clean release:prepare release:perform -B --settings .travis/settings.xml -f WakeOnLanHTTPRest
git push