package com.mauromidolo.windowsstartup;

import com.mauromidolo.windowsstartup.jetty.JettyHttpServer;
import com.mauromidolo.windowsstartup.jetty.WebServer;
import com.mauromidolo.windowsstartup.properties.ApplicationFileProperties;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationFileProperties applicationFileProperties = new ApplicationFileProperties("./server.properties");
        int httpPort = applicationFileProperties.getIntProperty("http.port", 8080);
        WebServer httpServer = new JettyHttpServer(httpPort);
        try {
            httpServer.start();
            httpServer.join();
        } finally {
            httpServer.destroy();
        }
    }
}
