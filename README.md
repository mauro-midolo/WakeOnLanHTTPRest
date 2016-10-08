# WindowsStartUp
This project is available for startup any computer in the local network from a server such as a raspberry pi

# HTTP REST API
## Check Status
Use it to checking if application is started
Available on: GET /command/status
Returns a json value as follow:
_{"Status":"OK"}_

## Startup a network pc
Use it to starting any computer in network lan, using Wake On Lan (WOL) protocol
Available on: GET /command/start/{passoword}
For security reasons, it returns always a json value as follow:
_{"Status":"OK"}_