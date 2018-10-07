package com.WindowsStartUp.properties;

import org.junit.Test;

import java.util.Objects;

public class ApplicationFilePropertiesTest {


    @Test
    public void shouldCreateInstance() {
        new ApplicationFileProperties(getPathOf("test1.properties"));
    }

    private String getPathOf(String name) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(name)).getPath();
    }
}