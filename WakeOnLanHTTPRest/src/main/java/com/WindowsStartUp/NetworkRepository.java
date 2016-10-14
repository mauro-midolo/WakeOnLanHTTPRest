package com.WindowsStartUp;

import java.util.List;

public interface NetworkRepository {

    void executeWOL(String macAddress, String broadCastIp);
    List<String> getBroadCastIps();
}
