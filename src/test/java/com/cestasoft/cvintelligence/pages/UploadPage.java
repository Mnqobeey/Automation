package com.cestasoft.cvintelligence.pages;

import com.cestasoft.cvintelligence.utils.WaitUtils;
import org.openqa.selenium.By;

import java.nio.file.Path;

public class UploadPage extends BasePage {
    private static final By FILE_TAB = By.cssSelector("[data-input-mode='file']");
    private static final By PASTE_TAB = By.cssSelector("[data-input-mode='paste']");
    private static final By FILE_INPUT = By.id("cvFile");
    private static final By PASTE_TEXTAREA = By.id("cvPasteText");
    private static final By SUBMIT_BUTTON = By.id("uploadBtn");

    public void switchToFileMode() {
        WaitUtils.clickable(driver, FILE_TAB).click();
    }

    public void switchToPasteMode() {
        WaitUtils.clickable(driver, PASTE_TAB).click();
    }

    public void uploadFile(Path file) {
        WaitUtils.visible(driver, FILE_INPUT).sendKeys(file.toString());
    }

    public void pasteText(String text) {
        WaitUtils.visible(driver, PASTE_TEXTAREA).clear();
        driver.findElement(PASTE_TEXTAREA).sendKeys(text);
    }

    public void submit() {
        WaitUtils.clickable(driver, SUBMIT_BUTTON).click();
    }
}
