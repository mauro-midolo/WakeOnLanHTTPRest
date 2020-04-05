#!/bin/bash

set -e

mvn initialize release:clean release:prepare release:perform -B --settings .travis/settings.xml -f WakeOnLanHTTPRest
