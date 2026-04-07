package com.cestasoft.cvintelligence.steps;

import com.cestasoft.cvintelligence.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class CommonSteps {
    private final HomePage homePage = new HomePage();

    @Given("the CV Intelligence application is open")
    public void theApplicationIsOpen() {
        homePage.open();
    }

    @Then("the upload form is visible")
    public void theUploadFormIsVisible() {
        Assert.assertTrue(homePage.isUploadFormVisible());
    }

    @And("the status text contains {string}")
    public void theStatusTextContains(String expected) {
        Assert.assertTrue(homePage.getStatusText().contains(expected));
    }
}
