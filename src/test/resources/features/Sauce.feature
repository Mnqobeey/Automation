

Feature: Complete purchase on Saucedemo.com

  As a registered user
  I want to purchase items from the store
  So that I can complete an order successfully

  Scenario:
    Given the user navigates to the SauceDemo login page
    When the user logs in with valid credentials
    And adds "Sauce Labs Backpack" and "Sauce Labs Bolt T-Shirt"
    And views the cart and verifies the items
    And proceeds to checkout
    And fills in checkout information
    And completes the purchase
    Then the user should see the "Thank you for your order!" confirmation message