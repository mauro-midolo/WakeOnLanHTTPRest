package com.mauromidolo.windowsstartup.properties;

import org.junit.Test;

import java.util.Objects;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ApplicationFilePropertiesTest {

    @Test
    public void shouldCreateInstance() {
        ApplicationFileProperties applicationFileProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        assertNotNull(applicationFileProperties);
    }

    @Test
    public void shouldReturnsAValueWithoutDefault() {
        ApplicationProperties applicationProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        String result = applicationProperties.getProperty("mac.address");
        assertNotNull(result);
    }

    @Test
    public void shouldReturnsAValueWithDefault() {
        ApplicationProperties applicationProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        String result = applicationProperties.getProperty("mac.address", "DefaultValue");
        assertNotEquals("DefaultValue", result);
    }

    @Test
    public void shouldReturnsDefaultValueIfVariableNotSet() {
        ApplicationProperties applicationProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        String result = applicationProperties.getProperty("notExists", "DefaultValue");
        assertEquals("DefaultValue", result);
    }

    @Test
    public void shouldReturnsIntValue() {
        ApplicationProperties applicationProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        int result = applicationProperties.getIntProperty("http.port", 91);
        assertEquals(8080, result);
    }

    @Test
    public void shouldReturnsDefaultValueIfNotSet() {
        ApplicationProperties applicationProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        int result = applicationProperties.getIntProperty("notSet", 91);
        assertEquals(91, result);
    }

    @Test
    public void shouldUseDefaultIfVariableNotANumber() {
        ApplicationProperties applicationProperties = new ApplicationFileProperties(getPathOf("test1.properties"));
        Integer result = applicationProperties.getIntProperty("mac.address", 91);
        assertEquals(91, result.intValue());
    }

    private String getPathOf(String name) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(name)).getPath();
    }
}