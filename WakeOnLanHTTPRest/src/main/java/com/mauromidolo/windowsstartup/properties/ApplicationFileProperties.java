package com.mauromidolo.windowsstartup.properties;

import java.io.FileInputStream;
import java.util.Properties;

public class ApplicationFileProperties implements ApplicationProperties {

    private final Properties properties;

    public ApplicationFileProperties(String path) {
        properties = new Properties();
        try(FileInputStream file = new FileInputStream(path)) {
            properties.load(file);
        } catch (java.io.IOException e) {
            System.err.println("Impossible to read server.properties file");
        }
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
            return defaultValue;
        }
    }
}
