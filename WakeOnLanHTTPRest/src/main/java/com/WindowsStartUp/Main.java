package com.WindowsStartUp;

import com.WindowsStartUp.jetty.JettyHttpServer;
import com.WindowsStartUp.jetty.WebServer;
import com.WindowsStartUp.properties.ApplicationFileProperties;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationFileProperties applicationFileProperties = new ApplicationFileProperties("./server.properties");
        int httpPort = applicationFileProperties.getIntProperty("http.port", 8080);
        WebServer httpServer = new JettyHttpServer("WakeOnLanHTTPRest", httpPort);
        try {
            httpServer.start();
            httpServer.join();
        } finally {
            httpServer.destroy();
        }
    }
}
