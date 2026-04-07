package com.cestasoft.cvintelligence.steps;

import com.cestasoft.cvintelligence.pages.ReviewPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ReviewSteps {
    private final ReviewPage reviewPage = new ReviewPage();

    @When("I open the recruiter preview")
    public void iOpenTheRecruiterPreview() {
        reviewPage.openPreview();
    }

    @Then("the review complete action is available")
    public void theReviewCompleteActionIsAvailable() {
        Assert.assertTrue(reviewPage.isReviewCompleteAvailable());
    }

    @When("I mark the review as complete")
    public void iMarkTheReviewAsComplete() {
        reviewPage.completeReview();
    }
}
