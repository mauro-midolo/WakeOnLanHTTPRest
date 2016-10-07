package com.WindowsStartUp;

import com.WindowsStartUp.properties.ApplicationFileProperties;
import com.WindowsStartUp.properties.ApplicationProperties;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkManager {
    private static NetworkManager instance;

    private final ApplicationProperties applicationProperties;

    private NetworkManager() {
        applicationProperties = ApplicationFileProperties.getInstance();
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void sendWakeOnLanPackage(String password) {
        if (passwordIsNotValid(password)) {
            return;
        }
        List<String> broadCastIps = getBroadCastIps();
        if (broadCastIps == null || broadCastIps.isEmpty()) {
            throw new RuntimeException("Impossible to find broadcast ip");
        }
        byte[] bytes = getBytesForMacAddress(getMacAddressFromFileProperty());
        broadCastIps.forEach(broadcastIp -> sendingPacketFor(broadcastIp, bytes));
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

    private byte[] getBytesForMacAddress(String macAddress) throws IllegalArgumentException {
        byte[] macBytes = getMacBytes(macAddress);
        byte[] bytes = new byte[6 + 16 * macBytes.length];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }
        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }
        return bytes;
    }

    private void sendingPacketFor(String ip, byte[] bytes) {
        DatagramSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
            socket = new DatagramSocket();
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private List<String> getBroadCastIps() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                    .filter(networkInterface -> {
                        try {
                            return !networkInterface.isLoopback() && networkInterface.isUp();
                        } catch (SocketException e) {
                            return false;
                        }
                    }).flatMap(networkInterface -> networkInterface.getInterfaceAddresses().stream())
                    .filter(interfaceAddress -> interfaceAddress.getBroadcast() != null)
                    .map(interfaceAddress -> interfaceAddress.getBroadcast().getHostAddress()).collect(Collectors.toList());

        } catch (SocketException e) {
            return null;
        }
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        return bytes;
    }
}