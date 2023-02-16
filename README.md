# WindowsStartUp 
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=mauro-midolo_WakeOnLanHTTPRest&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=mauro-midolo_WakeOnLanHTTPRest) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=mauro-midolo_WakeOnLanHTTPRest&metric=security_rating)](https://sonarcloud.io/dashboard?id=mauro-midolo_WakeOnLanHTTPRest) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mauro-midolo_WakeOnLanHTTPRest&metric=coverage)](https://sonarcloud.io/dashboard?id=mauro-midolo_WakeOnLanHTTPRest) [![Test & Release](https://github.com/mauro-midolo/WakeOnLanHTTPRest/actions/workflows/build.yml/badge.svg)](https://github.com/mauro-midolo/WakeOnLanHTTPRest/actions/workflows/build.yml)

This project is available for startup any computer in the local network from a server such as a raspberry pi

## Scenario
![scenario](https://cloud.githubusercontent.com/assets/10880247/19399841/bdd538ca-9253-11e6-8fc0-9f51d5abdb18.PNG)

## Installation
*   Download [last version](https://github.com/mauro-midolo/WakeOnLanHTTPRest/releases/download/v1.1.0/WakeOnLanHTTPRest-1.1.0.zip)

*   edit _server.properties_ with your configuration 
    *   change _mac.address_ with the computer's mac address that you want to start up
    *   change _security.password_ with stronge password
    

*   add execution mode for startService.sh (chmod +x startService.sh)

*   execute startService.sh with start (./startService.sh start)

*   check if server is start up
    *   with browser go [http://localhost:8080/command/status](http://localhost:8080/command/status)

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

## Support the project with a donation
This project is open source and free, but if you want to support us and help us continue to maintain and improve it, you can make a donation through PayPal. 
Any contribution, no matter how small, is greatly appreciated and will help us keep the project active and healthy. Thank you for your support!

[![Donate](https://img.shields.io/static/v1?label=PayPal&message=Buy%20Me%20a%20Coffee&color=green&logo=PayPal)](https://paypal.me/mauromi?country.x=IT&locale.x=it_IT)

