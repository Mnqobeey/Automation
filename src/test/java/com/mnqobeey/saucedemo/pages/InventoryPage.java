package com.mnqobeey.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

public class InventoryPage extends BasePage {
    private static final By PAGE_TITLE = By.cssSelector("[data-test='title']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
        visible(PAGE_TITLE);
    }

    public String title() {
        return text(PAGE_TITLE);
    }

    public InventoryPage addProduct(String productName) {
        click(By.cssSelector("[data-test='add-to-cart-" + productSlug(productName) + "']"));
        return this;
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }

    private String productSlug(String productName) {
        return productName
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+|-+$)", "");
    }
}
