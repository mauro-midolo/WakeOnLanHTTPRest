package com.mauromidolo.windowsstartup.wakeonlan;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkRepositoryImpl implements NetworkRepository {

    @Override
    public void executeWOL(String macAddress, String broadCastIp) {
        byte[] macBytes = getBytesForMacAddress(macAddress);
        DatagramSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName(broadCastIp);
            DatagramPacket packet = new DatagramPacket(macBytes, macBytes.length, address, 9);
            socket = new DatagramSocket();
            socket.send(packet);
        } catch (IOException ignored) {
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

    }

    @Override
    public List<String> getBroadCastIps() {
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
            return List.of();
        }
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

    private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("([:\\-])");
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
