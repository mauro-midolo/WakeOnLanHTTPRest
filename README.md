# WindowsStartUp [![Build Status](https://travis-ci.org/mauro-midolo/WakeOnLanHTTPRest.svg?branch=master)](https://travis-ci.org/mauro-midolo/WakeOnLanHTTPRest) [![codecov](https://codecov.io/gh/mauro-midolo/WakeOnLanHTTPRest/branch/master/graph/badge.svg)](https://codecov.io/gh/mauro-midolo/WakeOnLanHTTPRest) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b174c0945cf84c788e839327c5e4bd10)](https://app.codacy.com/manual/mmxx1991/WakeOnLanHTTPRest?utm_source=github.com&utm_medium=referral&utm_content=mauro-midolo/WakeOnLanHTTPRest&utm_campaign=Badge_Grade_Dashboard)

This project is available for startup any computer in the local network from a server such as a raspberry pi

## Scenario
![scenario](https://cloud.githubusercontent.com/assets/10880247/19399841/bdd538ca-9253-11e6-8fc0-9f51d5abdb18.PNG)

## Installation
* Download [last version](https://github.com/mauro-midolo/WakeOnLanHTTPRest/releases/download/v1.1.0/WakeOnLanHTTPRest-1.1.0.zip)

* edit _server.properties_ with your configuration 
    * change _mac.address_ with the computer's mac address that you want to start up
    * change _security.password_ with stronge password
    
* add execution mode for startService.sh (chmod +x startService.sh)

* execute startService.sh with start (./startService.sh start)

* check if server is start up
    * with browser go http://localhost:8080/command/status

## HTTP REST API
### Check Status
Use it to checking if application is started
Available on: GET /command/status
Returns a json value as follow:
_{"Status":"OK"}_

### Startup a network pc, reading mac address from configuration file
Use it to starting the computer in network lan set into config file, using Wake On Lan (WOL) protocol
Available on: GET /command/start/{passoword}

For security reasons, it returns always a json value as follow:
_{"Status":"OK"}_

### Startup a network pc, specifying mac address 
Use it to starting any computer in network lan, using Wake On Lan (WOL) protocol
Available on: GET /command/start/{passoword}/{mac-address}

For security reasons, it returns always a json value as follow:
_{"Status":"OK"}_
