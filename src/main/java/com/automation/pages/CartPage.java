package com.automation.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class CartPage extends BasePage {
    private static final By CART_ITEMS = By.className("cart-item");
    private static final By ITEM_QUANTITY = By.className("item-quantity");
    private static final By REMOVE_ITEM_BTN = By.className("remove-btn");
    private static final By CONTINUE_SHOPPING_BTN = By.id("continueShopping");
    private static final By CHECKOUT_BTN = By.id("checkoutBtn");
    private static final By CART_TOTAL = By.id("cartTotal");
    private static final By EMPTY_CART_MESSAGE = By.className("empty-cart-message");

    @Step("Verify cart page is loaded")
    public boolean isCartPageLoaded() {
        log.info("Verifying cart page is loaded");
        return isElementDisplayed(CHECKOUT_BTN) || isElementDisplayed(EMPTY_CART_MESSAGE);
    }

    @Step("Get number of items in cart")
    public int getCartItemsCount() {
        log.info("Getting number of items in cart");
        try {
            return waitForElementsPresence(CART_ITEMS).size();
        } catch (Exception e) {
            log.warn("Error getting cart items count: {}", e.getMessage());
            return 0;
        }
    }

    @Step("Get cart total")
    public String getCartTotal() {
        log.info("Getting cart total");
        return getText(CART_TOTAL);
    }

    @Step("Update item quantity at index {index} to {quantity}")
    public void updateItemQuantity(int index, int quantity) {
        log.info("Updating item {} quantity to {}", index, quantity);
        try {
            var items = waitForElementsPresence(ITEM_QUANTITY);
            if (index < items.size()) {
                items.get(index).clear();
                items.get(index).sendKeys(String.valueOf(quantity));
            }
        } catch (Exception e) {
            log.error("Error updating quantity: {}", e.getMessage());
        }
    }

    @Step("Remove item from cart at index {index}")
    public void removeItemFromCart(int index) {
        log.info("Removing item at index {} from cart", index);
        try {
            var items = waitForElementsPresence(REMOVE_ITEM_BTN);
            if (index < items.size()) {
                items.get(index).click();
            }
        } catch (Exception e) {
            log.error("Error removing item: {}", e.getMessage());
        }
    }

    @Step("Proceed to checkout")
    public void proceedToCheckout() {
        log.info("Proceeding to checkout");
        click(CHECKOUT_BTN);
    }

    @Step("Continue shopping")
    public void continueShopping() {
        log.info("Continuing shopping");
        click(CONTINUE_SHOPPING_BTN);
    }

    @Step("Verify cart is empty")
    public boolean isCartEmpty() {
        return isElementDisplayed(EMPTY_CART_MESSAGE);
    }
}
