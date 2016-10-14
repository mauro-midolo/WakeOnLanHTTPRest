package com.WindowsStartUp.rest;

import com.WindowsStartUp.Manager;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class HttpRestTest {
    private static final String PASSWORD = "PASSWORD";
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private Manager manager;
    private HttpRest httpRest;

    @Before
    public void setUp() throws Exception {
        httpRest = new HttpRest(manager);
    }

    @Test
    public void shouldReturnSuccessResponse() throws Exception {
        Response response = httpRest.checkServer();

        assertEquals(200, response.getStatus());
        assertEquals("{\"Status\":\"OK\"}", response.getEntity());
    }

    @Test
    public void shouldProcessRequest() throws Exception {
        context.checking(new Expectations() {{
            oneOf(manager).sendWakeOnLanPackage(PASSWORD);
        }});
        Response response = httpRest.startComputer(PASSWORD);

        assertEquals(200, response.getStatus());
        assertEquals("{\"Status\":\"OK\"}", response.getEntity());
    }
}