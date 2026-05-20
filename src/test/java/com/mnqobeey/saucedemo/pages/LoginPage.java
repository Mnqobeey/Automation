package com.mnqobeey.saucedemo.pages;

import com.mnqobeey.saucedemo.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By LOGO = By.cssSelector(".login_logo");
    private static final By USERNAME = By.cssSelector("[data-test='username']");
    private static final By PASSWORD = By.cssSelector("[data-test='password']");
    private static final By LOGIN_BUTTON = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(TestConfig.required("base.url"));
        visible(LOGO);
        return this;
    }

    public String logoText() {
        return text(LOGO);
    }

    public InventoryPage login(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
        return new InventoryPage(driver);
    }
}
