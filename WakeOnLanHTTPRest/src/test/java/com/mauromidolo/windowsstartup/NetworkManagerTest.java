package com.mauromidolo.windowsstartup;

import com.mauromidolo.windowsstartup.properties.ApplicationProperties;
import com.mauromidolo.windowsstartup.wakeonlan.Manager;
import com.mauromidolo.windowsstartup.wakeonlan.NetworkManager;
import com.mauromidolo.windowsstartup.wakeonlan.NetworkRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class NetworkManagerTest {

    private static final String PASSWORD = "MyPassword";
    private static final String WRONG_PASSWORD = "WrongPassword";
    private static final String MAC_ADDRESS = "00-00-00-00-00";
    private static final String BROADCAST_IP = "192.168.0.255";
    private Manager manager;

    @Mock
    private NetworkRepository networkRepository;
    @Mock
    private ApplicationProperties applicationProperties;

    @Before
    public void setUp() {
        manager = new NetworkManager(networkRepository, applicationProperties);
    }

    @Test
    public void shouldExist() {
        NetworkManager.getInstance();
    }

    @Test
    public void shouldManagesCorrectStartRequest() {
        Mockito.when(applicationProperties.getProperty("mac.address")).thenReturn(MAC_ADDRESS);
        Mockito.when(applicationProperties.getProperty("security.password")).thenReturn(PASSWORD);
        Mockito.when(networkRepository.getBroadCastIps()).thenReturn(Collections.singletonList(BROADCAST_IP));
        manager.sendWakeOnLanPackage(PASSWORD);
        Assert.assertNotNull(manager);
    }

    @Test
    public void shouldNotRaiseExceptionIfMacAddressIsMissing() {
        Mockito.when(applicationProperties.getProperty("mac.address")).thenReturn(null);
        Mockito.when(applicationProperties.getProperty("security.password")).thenReturn(PASSWORD);
        manager.sendWakeOnLanPackage(PASSWORD);
        Assert.assertNotNull(manager);
    }

    @Test
    public void shouldReturnsIfPasswordIsWrong() {
        Mockito.when(applicationProperties.getProperty("mac.address")).thenReturn(MAC_ADDRESS);
        Mockito.when(applicationProperties.getProperty("security.password")).thenReturn(PASSWORD);
        manager.sendWakeOnLanPackage(WRONG_PASSWORD);
        Assert.assertNotNull(manager);
    }

    @Test
    public void shouldManagesCorrectStartRequestWithMacAddress() {
        Mockito.when(applicationProperties.getProperty("security.password")).thenReturn(PASSWORD);
        Mockito.when(networkRepository.getBroadCastIps()).thenReturn(Collections.singletonList(BROADCAST_IP));
        manager.sendWakeOnLanPackage(PASSWORD, MAC_ADDRESS);
        Mockito.verify(networkRepository).getBroadCastIps();
        Assert.assertNotNull(manager);
    }

    @Test
    public void shouldReturnsIfPasswordIsWrongWithMacAddress() {
        Mockito.when(applicationProperties.getProperty("security.password")).thenReturn(PASSWORD);
        manager.sendWakeOnLanPackage(WRONG_PASSWORD, MAC_ADDRESS);
        Assert.assertNotNull(manager);
    }
}