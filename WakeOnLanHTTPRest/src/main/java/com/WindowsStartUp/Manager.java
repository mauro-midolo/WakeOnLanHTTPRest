package com.WindowsStartUp;


public interface Manager {
    void sendWakeOnLanPackage(String password);
    void sendWakeOnLanPackage(String password, String macAddress);

}
