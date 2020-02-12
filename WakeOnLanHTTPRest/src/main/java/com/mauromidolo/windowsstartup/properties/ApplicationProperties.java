package com.mauromidolo.windowsstartup.properties;

public interface ApplicationProperties {
    String getProperty(String name);
    String getProperty(String name, String defaultValue);
    Integer getIntProperty(String name, Integer defaultValue);
}
