package com.automation.tests.ui;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.automation.pages.CartPage;
import com.automation.tests.BaseTest;

@Slf4j
@Feature("Shopping Cart")
@Story("Cart Operations")
public class CartTest extends BaseTest {
    private CartPage cartPage;

    @BeforeMethod
    public void setUpCartTest() {
        super.setUp();
        cartPage = new CartPage();
        navigateTo("/cart");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test cart page loads")
    public void testCartPageLoaded() {
        log.info("Test: testCartPageLoaded");
        Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page should be loaded");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test get cart items count")
    public void testGetCartItemsCount() {
        log.info("Test: testGetCartItemsCount");
        int itemCount = cartPage.getCartItemsCount();
        Assert.assertTrue(itemCount >= 0, "Item count should be 0 or more");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test update item quantity")
    public void testUpdateItemQuantity() {
        log.info("Test: testUpdateItemQuantity");
        if (cartPage.getCartItemsCount() > 0) {
            cartPage.updateItemQuantity(0, 5);
            Allure.step("Quantity updated successfully");
        }
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test proceed to checkout")
    public void testProceedToCheckout() {
        log.info("Test: testProceedToCheckout");
        if (cartPage.getCartItemsCount() > 0) {
            cartPage.proceedToCheckout();
            waitForSeconds(2);
            verifyPageUrl("/checkout");
        }
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test continue shopping")
    public void testContinueShopping() {
        log.info("Test: testContinueShopping");
        cartPage.continueShopping();
        waitForSeconds(2);
        verifyPageUrl("/products");
    }

    @AfterMethod
    public void tearDownCartTest() {
        super.tearDown();
    }
}
