package com.mnqobeey.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private static final By PAGE_TITLE = By.cssSelector("[data-test='title']");
    private static final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
        visible(PAGE_TITLE);
    }

    public String title() {
        return text(PAGE_TITLE);
    }

    public boolean containsProduct(String productName) {
        return isVisible(By.xpath("//div[@data-test='inventory-item-name' and normalize-space()=" + xpathLiteral(productName) + "]"));
    }

    public CheckoutPage proceedToCheckout() {
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }
}
