package com.WindowsStartUp;

import com.WindowsStartUp.properties.ApplicationFileProperties;
import com.WindowsStartUp.properties.ApplicationProperties;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class NetworkManager implements Manager {
    private static NetworkManager instance;

    private NetworkRepository networkRepository;
    private final ApplicationProperties applicationProperties;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager(new NetworkRepositoryImpl(), new ApplicationFileProperties());
        }
        return instance;
    }

    NetworkManager(NetworkRepository networkRepository, ApplicationProperties applicationProperties) {
        this.networkRepository = networkRepository;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void sendWakeOnLanPackage(String password) {
        String macAddressFromFileProperty = getMacAddressFromFileProperty();
        sendWakeOnLanPackage(password, macAddressFromFileProperty);
    }

    @Override
    public void sendWakeOnLanPackage(String password, String macAddress) {
        if (passwordIsNotValid(password)) {
            return;
        }
        if (macAddress == null || macAddress.isEmpty()) {
            throw new RuntimeException("mac address is required");
        }
        List<String> broadCastIps = networkRepository.getBroadCastIps();
        broadCastIps.forEach(broadCastIp -> networkRepository.executeWOL(macAddress, broadCastIp));
    }


    private boolean passwordIsNotValid(String password) {
        String securityPasswordFromFileProperty = getSecurityPasswordFromFileProperty();
        return !passwordStoredInFileIsNotSetOrEmpty(securityPasswordFromFileProperty)
                && inputPasswordIsNullOrNotEqualsToStoredPassword(password, securityPasswordFromFileProperty);
    }

    private boolean inputPasswordIsNullOrNotEqualsToStoredPassword(String password, String securityPasswordFromFileProperty) {
        return password == null || password.isEmpty() || !password.equals(securityPasswordFromFileProperty);
    }

    private boolean passwordStoredInFileIsNotSetOrEmpty(String securityPasswordFromFileProperty) {
        return securityPasswordFromFileProperty == null || securityPasswordFromFileProperty.isEmpty();
    }

    private String getMacAddressFromFileProperty() {
        return applicationProperties.getProperty("mac.address");
    }

    private String getSecurityPasswordFromFileProperty() {
        return applicationProperties.getProperty("security.password");
    }


}