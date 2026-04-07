package com.cestasoft.cvintelligence.steps;

import com.cestasoft.cvintelligence.pages.ReviewPage;
import com.cestasoft.cvintelligence.pages.UploadPage;
import com.cestasoft.cvintelligence.utils.FileUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class UploadSteps {
    private final UploadPage uploadPage = new UploadPage();
    private final ReviewPage reviewPage = new ReviewPage();

    @When("I upload the test file {string}")
    public void iUploadTheTestFile(String fileName) {
        uploadPage.switchToFileMode();
        uploadPage.uploadFile(FileUtils.resolveTestData(fileName));
        uploadPage.submit();
    }

    @When("I switch to paste mode")
    public void iSwitchToPasteMode() {
        uploadPage.switchToPasteMode();
    }

    @And("I paste the test text {string}")
    public void iPasteTheTestText(String fileName) {
        uploadPage.pasteText(FileUtils.readTestData(fileName));
    }

    @And("I submit the CV for analysis")
    public void iSubmitTheCvForAnalysis() {
        uploadPage.submit();
    }

    @When("I paste the test text {string} in paste mode and submit it")
    public void iPasteTheTestTextInPasteModeAndSubmitIt(String fileName) {
        uploadPage.switchToPasteMode();
        uploadPage.pasteText(FileUtils.readTestData(fileName));
        uploadPage.submit();
    }

    @Then("the analysis completes successfully")
    public void theAnalysisCompletesSuccessfully() {
        reviewPage.waitForAnalysisComplete();
    }

    @And("the review workspace is visible")
    public void theReviewWorkspaceIsVisible() {
        Assert.assertTrue(reviewPage.isWorkspaceVisible());
    }
}
