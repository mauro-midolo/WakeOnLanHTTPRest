package com.mauromidolo.windowsstartup.rest;

import com.mauromidolo.windowsstartup.Manager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class HttpRestTest {
    private static final String PASSWORD = "PASSWORD";

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
        Response response = httpRest.startComputer(PASSWORD);

        assertEquals(200, response.getStatus());
        assertEquals("{\"Status\":\"OK\"}", response.getEntity());

        Mockito.verify(manager).sendWakeOnLanPackage(PASSWORD);
    }
}