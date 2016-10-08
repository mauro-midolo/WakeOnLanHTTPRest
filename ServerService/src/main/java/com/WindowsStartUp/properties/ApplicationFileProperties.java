package com.WindowsStartUp.properties;

import java.io.FileInputStream;
import java.util.Properties;

public class ApplicationFileProperties implements ApplicationProperties {

    private static ApplicationProperties instance;
    private final Properties properties;

    private ApplicationFileProperties() {
        properties = new Properties();
        FileInputStream file;
        String path = "./server.properties";
        try {
            file = new FileInputStream(path);
            properties.load(file);
            file.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException("Impossible to read server.properties file");
        }
    }

    public static ApplicationProperties getInstance() {
        if (instance == null) {
            instance = new ApplicationFileProperties();
        }
        return instance;
    }

    @Override
    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    @Override
    public Integer getIntProperty(String name, Integer defaultValue) {
        try {
            return Integer.valueOf(getProperty(name, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Property " + name + "should be a integer!");
        }
    }
}
