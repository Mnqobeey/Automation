@smoke @checkout
Feature: SauceDemo checkout

  As a registered SauceDemo user
  I want to buy products from the store
  So that I can confirm the checkout journey still works

  Scenario: Complete checkout with two products
    Given the user navigates to the SauceDemo login page
    When the user logs in with valid credentials
    And adds "Sauce Labs Backpack" and "Sauce Labs Bolt T-Shirt"
    And views the cart and verifies the items
    And proceeds to checkout
    And fills in checkout information
    And completes the purchase
    Then the user should see the "Thank you for your order!" confirmation message
