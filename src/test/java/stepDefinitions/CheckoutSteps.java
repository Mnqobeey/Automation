package stepDefinitions;

import accelerators.actions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageobjects.SauceObjects;
import utility.Utils;

import static accelerators.actions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckoutSteps {

    // Navigates to login page and verifies logo
    @Given("the user navigates to the SauceDemo login page")
    public void userOnHomePage() throws Exception {
        waitForElementToBeVisible(SauceObjects.Logo, 10);
    }

    // Logs in with credentials from config
    @When("the user logs in with valid credentials")
    public void userEntersValidCredentials() throws Exception {
        waitForElementToBeVisible(SauceObjects.UsernameField, 10);
        typeInTextBox(SauceObjects.UsernameField, Utils.ConfigReader.getProperty("email"));
        typeInTextBox(SauceObjects.PasswordField, Utils.ConfigReader.getProperty("password"));

        click(SauceObjects.LoginButton);
    }

    // Adds two products to cart
    @When("adds {string} and {string}")
    public void userAddProducts(String product1, String product2) throws Exception {;
        click(SauceObjects.getAddToCartButton(product1));
        click(SauceObjects.getAddToCartButton(product2));
    }

    // Views cart and verifies items are present
    @And("views the cart and verifies the items")
    public void userViewsCartAndVerifiesItems() throws Exception {
        click(SauceObjects.CartIcon);

        assertTrue("Backpack should be in cart",
                isElementVisible(SauceObjects.BackpackInCart, "Sauce Labs Backpack"));
        assertTrue("T-Shirt should be in cart",
                isElementVisible(SauceObjects.TshirtInCart, "Sauce Labs Bolt T-Shirt"));
    }

    // Proceeds to checkout page
    @And("proceeds to checkout")
    public void userGoToCheckout() throws Exception {
        click(SauceObjects.CheckoutButton);
    }

    // Fills checkout form with user information
    @And("fills in checkout information")
    public void userFillCheckoutInformation() throws Exception {
        typeInTextBox(SauceObjects.FirstNameField, Utils.ConfigReader.getProperty("Name"));
        typeInTextBox(SauceObjects.LastNameField, Utils.ConfigReader.getProperty("LastName"));
        typeInTextBox(SauceObjects.PostalCodeField, Utils.ConfigReader.getProperty("PostalCode"));

        click(SauceObjects.ContinueButton);
    }

    // Completes the purchase by clicking Finish
    @And("completes the purchase")
    public void userCompletePurchase() throws Exception {
        click(SauceObjects.FinishButton);
    }

    // Verifies the order confirmation message
    @Then("the user should see the {string} confirmation message")
    public void userConfirmsMessage(String expectedMessage) throws Exception {
        String actualMessage = actions.getElementText(SauceObjects.CompleteHeader, "Thank You Message");
        assertEquals(expectedMessage, actualMessage);
    }
}