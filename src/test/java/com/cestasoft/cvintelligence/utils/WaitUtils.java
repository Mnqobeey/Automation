package com.cestasoft.cvintelligence.utils;

import com.cestasoft.cvintelligence.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class WaitUtils {
    private WaitUtils() {
    }

    public static WebElement visible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TestConfig.getInstance().getDefaultTimeout())
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TestConfig.getInstance().getDefaultTimeout())
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean textPresent(WebDriver driver, By locator, String text) {
        return new WebDriverWait(driver, TestConfig.getInstance().getDefaultTimeout())
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}
