package com.cestasoft.cvintelligence.pages;

import com.cestasoft.cvintelligence.config.DriverFactory;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
    }
}
