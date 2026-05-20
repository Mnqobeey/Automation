package com.mnqobeey.saucedemo.core;

import com.mnqobeey.saucedemo.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Locale;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            driver = createDriver();
            DRIVER.set(driver);
        }
        return driver;
    }

    public static WebDriver currentDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver createDriver() {
        String browser = TestConfig.get("browser").toLowerCase(Locale.ROOT);
        if (browser.isBlank() || browser.equals("chrome")) {
            return createChromeDriver();
        }
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1440,1000");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        if (TestConfig.getBoolean("headless", false)) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.getInt("timeout.seconds", 15)));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getInt("page.load.timeout.seconds", 30)));
        return driver;
    }
}
