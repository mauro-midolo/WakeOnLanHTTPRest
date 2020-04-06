package com.mauromidolo.windowsstartup;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NetworkRepositoryImplTest {

    @Test
    public void shouldExist() {
        NetworkRepositoryImpl networkRepository = new NetworkRepositoryImpl();
        Assert.assertNotNull(networkRepository);
    }

    @Test
    public void shouldReturnIps() {
        NetworkRepositoryImpl networkRepository = new NetworkRepositoryImpl();
        List<String> broadCastIps = networkRepository.getBroadCastIps();
        Assert.assertFalse(broadCastIps.isEmpty());
    }

    @Test
    public void shouldExecuteWakeOnLan() {
        NetworkRepositoryImpl networkRepository = new NetworkRepositoryImpl();
        networkRepository.executeWOL("00-00-00-00-00-00","192.168.0.255");
    }

    @Test
    public void shouldExceptIfMacAddressIsWronge() {
        NetworkRepositoryImpl networkRepository = new NetworkRepositoryImpl();
        try {
            networkRepository.executeWOL("-00","192.168.0.255");
            Assert.fail();
        }catch (Exception ignored){}
    }
}
