package com.WindowsStartUp;

import com.WindowsStartUp.properties.ApplicationFileProperties;
import com.WindowsStartUp.rest.HttpRest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = configureJettyServer(context);

        try {
            jettyServer.start();
            setJettyVersion("WakeOnLanHTTPRest");
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static void setJettyVersion(String jettyVersion) {
        org.eclipse.jetty.http.HttpGenerator.setJettyVersion(jettyVersion);
    }

    private static Server configureJettyServer(ServletContextHandler context) {
        ApplicationFileProperties applicationFileProperties = new ApplicationFileProperties("./server.properties");
        Server jettyServer = new Server(applicationFileProperties.getIntProperty("http.port", 8080));
        jettyServer.setHandler(context);
        addedError404Handler(context);
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", HttpRest.class.getCanonicalName());
        return jettyServer;
    }

    private static void addedError404Handler(ServletContextHandler context) {
        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        errorHandler.addErrorPage(404, "/command/status");
        context.setErrorHandler(errorHandler);
    }
}
