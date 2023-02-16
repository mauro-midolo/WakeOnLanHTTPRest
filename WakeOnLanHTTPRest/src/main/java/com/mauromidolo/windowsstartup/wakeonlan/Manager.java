package com.mauromidolo.windowsstartup.wakeonlan;


public interface Manager {
    void sendWakeOnLanPackage(String password);
    void sendWakeOnLanPackage(String password, String macAddress);
}
