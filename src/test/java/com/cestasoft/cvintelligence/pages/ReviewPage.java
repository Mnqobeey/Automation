package com.cestasoft.cvintelligence.pages;

import com.cestasoft.cvintelligence.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReviewPage extends BasePage {
    private static final By STATUS_TEXT = By.id("statusText");
    private static final By DOCUMENT_VIEWER = By.id("documentViewer");
    private static final By PREVIEW_TAB = By.cssSelector("[data-tab='preview']");
    private static final By REVIEW_COMPLETE_BUTTON = By.id("reviewCompleteBtn");

    public void waitForAnalysisComplete() {
        WebDriverWait wait = new WebDriverWait(driver, com.cestasoft.cvintelligence.config.TestConfig.getInstance().getDefaultTimeout());
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(STATUS_TEXT, "processed"),
                ExpectedConditions.textToBePresentInElementLocated(STATUS_TEXT, "generated"),
                ExpectedConditions.visibilityOfElementLocated(REVIEW_COMPLETE_BUTTON)
        ));
    }

    public boolean isWorkspaceVisible() {
        WebElement viewer = WaitUtils.visible(driver, DOCUMENT_VIEWER);
        return viewer.isDisplayed() && !viewer.getText().contains("Upload a CV to get started");
    }

    public void openPreview() {
        WaitUtils.clickable(driver, PREVIEW_TAB).click();
    }

    public boolean isReviewCompleteAvailable() {
        return WaitUtils.visible(driver, REVIEW_COMPLETE_BUTTON).isDisplayed();
    }

    public void completeReview() {
        WaitUtils.clickable(driver, REVIEW_COMPLETE_BUTTON).click();
    }
}
