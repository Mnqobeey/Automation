package com.cestasoft.cvintelligence.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public final class TestConfig {
    private static final String PROPERTIES_PATH = "/config/test.properties";
    private static final TestConfig INSTANCE = new TestConfig();

    private final Properties properties = new Properties();

    private TestConfig() {
        try (InputStream inputStream = getClass().getResourceAsStream(PROPERTIES_PATH)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load test properties.", exception);
        }
    }

    public static TestConfig getInstance() {
        return INSTANCE;
    }

    public String getBaseUrl() {
        return read("BASE_URL", "base.url");
    }

    public String getBrowser() {
        return read("BROWSER", "browser");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(read("HEADLESS", "headless"));
    }

    public Duration getDefaultTimeout() {
        return Duration.ofSeconds(Long.parseLong(read("DEFAULT_TIMEOUT_SECONDS", "default.timeout.seconds")));
    }

    public Path getDownloadDirectory() {
        return Paths.get(read("DOWNLOAD_DIR", "download.dir")).toAbsolutePath();
    }

    private String read(String envKey, String propertyKey) {
        String value = System.getProperty(envKey);
        if (value == null || value.isBlank()) {
            value = System.getenv(envKey);
        }
        if (value == null || value.isBlank()) {
            value = properties.getProperty(propertyKey);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration for " + propertyKey);
        }
        return value.trim();
    }
}
