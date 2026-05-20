package com.mnqobeey.saucedemo.hooks;

import com.mnqobeey.saucedemo.core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    @Before
    public void startBrowser() {
        DriverFactory.getDriver();
    }

    @After
    public void stopBrowser(Scenario scenario) {
        WebDriver driver = DriverFactory.currentDriver();
        if (scenario.isFailed() && driver instanceof TakesScreenshot screenshotDriver) {
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }
        DriverFactory.quitDriver();
    }
}
