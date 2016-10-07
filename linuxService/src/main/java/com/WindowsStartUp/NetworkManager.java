package com.WindowsStartUp;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class NetworkManager {
    private static NetworkManager instance;

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void sendWakeOnLanPackage() {
        List<String> broadCastIps = getBroadCastIps();
        if (broadCastIps == null || broadCastIps.isEmpty()) {
            throw new RuntimeException("Impossible to find broadcast ip");
        }

        byte[] bytes = getBytesForMacAddress("30-85-A9-9C-67-2C");
        broadCastIps.forEach(broadcastIp -> SendingPacketFor(broadcastIp, bytes));
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

    private void SendingPacketFor(String ip, byte[] bytes) {
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