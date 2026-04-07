package com.cestasoft.cvintelligence.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void createDriver() {
        TestConfig config = TestConfig.getInstance();
        if (!"chrome".equalsIgnoreCase(config.getBrowser())) {
            throw new IllegalArgumentException("Only chrome is currently configured.");
        }

        try {
            Files.createDirectories(config.getDownloadDirectory());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create download directory.", exception);
        }

        WebDriverManager.chromedriver().setup();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", config.getDownloadDirectory().toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        prefs.put("safebrowsing.enabled", true);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--window-size=1440,1200");
        options.addArguments("--remote-allow-origins=*");
        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(config.getDefaultTimeout());
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
