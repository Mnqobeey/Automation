package pageobjects;

import org.openqa.selenium.By;

public class SauceObjects {

    // Login Page Locators
    public static final By Logo = By.cssSelector("div.login_logo");
    public static final By UsernameField = By.cssSelector("[data-test='username']");
    public static final By PasswordField = By.cssSelector("[data-test='password']");
    public static final By LoginButton = By.id("login-button");

    // Product Page Locators
    public static final By ProductsTitle = By.cssSelector("[data-test='title']");
    public static final By CartIcon = By.cssSelector(".shopping_cart_link");

    // Product Add to Cart buttons
    public static final By AddBackpack = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    public static final By AddTshirt = By.cssSelector("[data-test='add-to-cart-sauce-labs-bolt-t-shirt']");

    // Cart Page Locators
    public static final By cartTitle = By.cssSelector("[data-test='title']");

    // Cart Items Verification
    public static final By BackpackInCart = By.xpath("//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']");
    public static final By TshirtInCart = By.xpath("//div[@class='inventory_item_name' and text()='Sauce Labs Bolt T-Shirt']");

    // Checkout button
    public static final By CheckoutButton = By.cssSelector("[data-test='checkout']");

    // Checkout Information Page Locators
    public static final By CheckoutTitle =
            By.cssSelector("span[data-test='title']");
    public static final By FirstNameField = By.cssSelector("[data-test='firstName']");
    public static final By LastNameField = By.cssSelector("[data-test='lastName']");
    public static final By PostalCodeField = By.cssSelector("[data-test='postalCode']");
    public static final By ContinueButton = By.cssSelector("[data-test='continue']");
    public static final By CancelButton = By.cssSelector("[data-test='cancel']");

    // Checkout Overview Page Locator
    public static final By FinishButton = By.cssSelector("button[data-test='finish']");

    // Order Complete Page Locator
    public static final By CompleteHeader = By.cssSelector("[data-test='complete-header']");

    // Helper Method
    public static By getAddToCartButton(String productName) {
        return switch (productName) {
            case "Sauce Labs Backpack" -> AddBackpack;
            case "Sauce Labs Bolt T-Shirt" -> AddTshirt;
            default -> throw new IllegalArgumentException(
                    "No Add-to-Cart button found for product: " + productName
            );
        };
    }
}