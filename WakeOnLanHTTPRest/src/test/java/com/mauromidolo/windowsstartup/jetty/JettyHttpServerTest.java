package com.mauromidolo.windowsstartup.jetty;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.*;

public class JettyHttpServerTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldExists() {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(7000);
        Assert.assertNotNull(jettyHttpServer);
    }

    @Test
    public void shouldStart() throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(7001);
        jettyHttpServer.start();
        Assert.assertNotNull(jettyHttpServer);
    }

    @Test
    public void shouldStartAndJoin() throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(7002);
        jettyHttpServer.start();
        final Duration timeout = Duration.ofSeconds(2);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(() -> {
            executor.submit(() -> {
                jettyHttpServer.join();
                return "ok";
            }).cancel(false);
        }, timeout.toMillis(), TimeUnit.MILLISECONDS);
        Assert.assertNotNull(jettyHttpServer);
    }

    @Test
    public void shouldStartAndStop() throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(7003);
        jettyHttpServer.start();
        jettyHttpServer.destroy();
        Assert.assertNotNull(jettyHttpServer);
    }
}