#!/bin/bash

SERVICE_NAME=WakeOnLanHTTPRest-1.1.0
JAR_FILE=WakeOnLanHTTPRest-1.1.0-SNAPSHOT-jar-with-dependencies.jar
JAR_PARAMETER=-Xmx128M

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PATH_TO_JAR="$SCRIPT_DIR/$JAR_FILE"
CURRENT_PID=$(ps aux |grep $JAR_FILE | grep -v grep | awk '{print $2}')

if [ ! -f $PATH_TO_JAR ]; then
    echo "$PATH_TO_JAR not exists!"
    exit 1
fi

cd "$SCRIPT_DIR"
case $1 in
    start)
        if [ -z "$CURRENT_PID" ]; then
            nohup java ${JAR_PARAMETER} -jar ${PATH_TO_JAR} 2>> /dev/null >> /dev/null &
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ ! -z "$CURRENT_PID" ]; then
            kill "$CURRENT_PID";
            echo "$SERVICE_NAME stopped ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        $0 stop
        $0 start
    ;;
    status)
        if [ -z "${CURRENT_PID}" ]; then
            echo  "$SERVICE_NAME is not running ..."
        else
            echo  "$SERVICE_NAME is running with pid $CURRENT_PID"
        fi
    ;;
    *)
        echo "Usage: ./"$(basename "$0")" {start|stop|restart|status}"
esac