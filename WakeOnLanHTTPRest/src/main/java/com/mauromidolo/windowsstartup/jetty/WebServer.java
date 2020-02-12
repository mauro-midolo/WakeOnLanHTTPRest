package com.mauromidolo.windowsstartup.jetty;

public interface WebServer {
    void start() throws Exception;
    void join() throws InterruptedException;
    void destroy();
}
