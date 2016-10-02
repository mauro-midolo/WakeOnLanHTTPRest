package com.WindowsStartUp;

import org.eclipse.jetty.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
