package com.mnqobeey.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private static final By PAGE_TITLE = By.cssSelector("[data-test='title']");
    private static final By FIRST_NAME = By.cssSelector("[data-test='firstName']");
    private static final By LAST_NAME = By.cssSelector("[data-test='lastName']");
    private static final By POSTAL_CODE = By.cssSelector("[data-test='postalCode']");
    private static final By CONTINUE_BUTTON = By.cssSelector("[data-test='continue']");
    private static final By FINISH_BUTTON = By.cssSelector("[data-test='finish']");
    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        visible(PAGE_TITLE);
    }

    public String title() {
        return text(PAGE_TITLE);
    }

    public CheckoutPage enterInformation(String firstName, String lastName, String postalCode) {
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(POSTAL_CODE, postalCode);
        click(CONTINUE_BUTTON);
        return this;
    }

    public CheckoutPage finishOrder() {
        click(FINISH_BUTTON);
        return this;
    }

    public String completeMessage() {
        return text(COMPLETE_HEADER);
    }
}
