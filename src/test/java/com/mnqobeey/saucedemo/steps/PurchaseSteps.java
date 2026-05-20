package com.mnqobeey.saucedemo.steps;

import com.mnqobeey.saucedemo.config.TestConfig;
import com.mnqobeey.saucedemo.core.DriverFactory;
import com.mnqobeey.saucedemo.pages.CartPage;
import com.mnqobeey.saucedemo.pages.CheckoutPage;
import com.mnqobeey.saucedemo.pages.InventoryPage;
import com.mnqobeey.saucedemo.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PurchaseSteps {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @Given("the user navigates to the SauceDemo login page")
    public void openLoginPage() {
        loginPage = new LoginPage(DriverFactory.getDriver()).open();
        assertEquals(TestConfig.required("expected.logo"), loginPage.logoText());
    }

    @When("the user logs in with valid credentials")
    public void loginWithValidCredentials() {
        inventoryPage = loginPage.login(
                TestConfig.required("saucedemo.username"),
                TestConfig.required("saucedemo.password")
        );
        assertEquals(TestConfig.required("expected.products.title"), inventoryPage.title());
    }

    @And("adds {string} and {string}")
    public void addProducts(String firstProduct, String secondProduct) {
        inventoryPage
                .addProduct(firstProduct)
                .addProduct(secondProduct);
    }

    @And("views the cart and verifies the items")
    public void viewCartAndVerifyItems() {
        cartPage = inventoryPage.openCart();
        assertEquals(TestConfig.required("expected.cart.title"), cartPage.title());
        assertTrue("Expected first product in cart", cartPage.containsProduct("Sauce Labs Backpack"));
        assertTrue("Expected second product in cart", cartPage.containsProduct("Sauce Labs Bolt T-Shirt"));
    }

    @And("proceeds to checkout")
    public void proceedToCheckout() {
        checkoutPage = cartPage.proceedToCheckout();
        assertEquals(TestConfig.required("expected.checkout.title"), checkoutPage.title());
    }

    @And("fills in checkout information")
    public void fillCheckoutInformation() {
        checkoutPage.enterInformation(
                TestConfig.required("checkout.first.name"),
                TestConfig.required("checkout.last.name"),
                TestConfig.required("checkout.postal.code")
        );
    }

    @And("completes the purchase")
    public void completePurchase() {
        checkoutPage.finishOrder();
    }

    @Then("the user should see the {string} confirmation message")
    public void verifyConfirmationMessage(String expectedMessage) {
        assertEquals(expectedMessage, checkoutPage.completeMessage());
    }
}
