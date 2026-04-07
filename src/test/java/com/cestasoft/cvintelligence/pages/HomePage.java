package com.cestasoft.cvintelligence.pages;

import com.cestasoft.cvintelligence.config.TestConfig;
import com.cestasoft.cvintelligence.utils.WaitUtils;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    private static final By UPLOAD_FORM = By.id("uploadForm");
    private static final By STATUS_TEXT = By.id("statusText");

    public void open() {
        driver.get(TestConfig.getInstance().getBaseUrl());
        WaitUtils.visible(driver, UPLOAD_FORM);
    }

    public boolean isUploadFormVisible() {
        return driver.findElement(UPLOAD_FORM).isDisplayed();
    }

    public String getStatusText() {
        return WaitUtils.visible(driver, STATUS_TEXT).getText();
    }
}
