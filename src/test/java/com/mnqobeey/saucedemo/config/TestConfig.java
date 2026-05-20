package com.mnqobeey.saucedemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class TestConfig {
    private static final String CONFIG_RESOURCE = "config/test.properties";
    private static final Properties PROPERTIES = loadProperties();

    private TestConfig() {
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (hasText(systemValue)) {
            return systemValue.trim();
        }

        String envValue = System.getenv(toEnvKey(key));
        if (hasText(envValue)) {
            return envValue.trim();
        }

        return PROPERTIES.getProperty(key, "").trim();
    }

    public static String required(String key) {
        String value = get(key);
        if (!hasText(value)) {
            throw new IllegalStateException("Missing required configuration value: " + key);
        }
        return value;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return hasText(value) ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (!hasText(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException("Could not find " + CONFIG_RESOURCE);
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load " + CONFIG_RESOURCE, exception);
        }
    }

    private static String toEnvKey(String key) {
        return key.toUpperCase(Locale.ROOT).replace('.', '_').replace('-', '_');
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
