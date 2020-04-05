#!/bin/bash

set -e

git remote set-url origin git@github.com:mauro-midolo/WakeOnLanHTTPRest.git
git checkout master
git pull
cd WakeOnLanHTTPRest
mvn initialize
cat release.properties
echo "HEREEE"
mvn release:clean release:prepare -B
git push
git pull
mvn release:perform
