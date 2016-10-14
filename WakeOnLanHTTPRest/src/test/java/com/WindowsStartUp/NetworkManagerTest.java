package com.WindowsStartUp;

import com.WindowsStartUp.properties.ApplicationProperties;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;

public class NetworkManagerTest {

    private static final String PASSWORD = "MyPassword";
    private static final String WRONG_PASSWORD = "WrongPassword";
    private static final String MAC_ADDRESS = "00-00-00-00-00";
    private static final String BROADCAST_IP = "192.168.0.255";
    private Manager manager;
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private NetworkRepository networkRepository;
    @Mock
    private ApplicationProperties applicationProperties;

    @Before
    public void setUp() throws Exception {
        manager = new NetworkManager(networkRepository, applicationProperties);
    }

    @Test
    public void shouldManagesCorrectStartRequest() throws Exception {
        context.checking(new Expectations() {{
            allowing(applicationProperties).getProperty("mac.address");
            will(returnValue(MAC_ADDRESS));
            oneOf(applicationProperties).getProperty("security.password");
            will(returnValue(PASSWORD));
            oneOf(networkRepository).getBroadCastIps();
            will(returnValue(Collections.singletonList(BROADCAST_IP)));
            oneOf(networkRepository).executeWOL(MAC_ADDRESS, BROADCAST_IP);
        }});

        manager.sendWakeOnLanPackage(PASSWORD);
    }

    @Test
    public void shouldReturnsIfPasswordIsWronge() throws Exception {
        context.checking(new Expectations() {{
            allowing(applicationProperties).getProperty("mac.address");
            will(returnValue(MAC_ADDRESS));
            oneOf(applicationProperties).getProperty("security.password");
            will(returnValue(PASSWORD));
            never(networkRepository).getBroadCastIps();
            never(networkRepository).executeWOL(MAC_ADDRESS, BROADCAST_IP);
        }});

        manager.sendWakeOnLanPackage(WRONG_PASSWORD);
    }

    @Test
    public void shouldManagesCorrectStartRequestWithMacAddress() throws Exception {
        context.checking(new Expectations() {{
            oneOf(applicationProperties).getProperty("security.password");
            will(returnValue(PASSWORD));
            oneOf(networkRepository).getBroadCastIps();
            will(returnValue(Collections.singletonList(BROADCAST_IP)));
            oneOf(networkRepository).executeWOL(MAC_ADDRESS, BROADCAST_IP);
        }});

        manager.sendWakeOnLanPackage(PASSWORD, MAC_ADDRESS);
    }

    @Test
    public void shouldReturnsIfPasswordIsWrongeWithMacAddress() throws Exception {
        context.checking(new Expectations() {{
            oneOf(applicationProperties).getProperty("security.password");
            will(returnValue(PASSWORD));
            never(networkRepository).getBroadCastIps();
            never(networkRepository).executeWOL(MAC_ADDRESS, BROADCAST_IP);
        }});

        manager.sendWakeOnLanPackage(WRONG_PASSWORD, MAC_ADDRESS);
    }
}