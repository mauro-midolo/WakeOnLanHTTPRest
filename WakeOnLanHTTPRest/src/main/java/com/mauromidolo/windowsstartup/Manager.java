package com.mauromidolo.windowsstartup;


public interface Manager {
    void sendWakeOnLanPackage(String password);
    void sendWakeOnLanPackage(String password, String macAddress);
}
