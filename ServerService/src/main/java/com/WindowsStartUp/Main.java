package com.WindowsStartUp;

import com.WindowsStartUp.properties.ApplicationFileProperties;
import com.WindowsStartUp.rest.HttpRest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(ApplicationFileProperties.getInstance().getIntProperty("http.port", 8080));
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", HttpRest.class.getCanonicalName());

        try {
            jettyServer.start();
            org.eclipse.jetty.http.HttpGenerator.setJettyVersion("WindowsStartUp");
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
