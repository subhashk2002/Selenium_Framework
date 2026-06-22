package com.automation.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Slf4j
public class ProductsPage extends BasePage {
    private static final By PRODUCTS_CONTAINER = By.className("products-grid");
    private static final By PRODUCT_ITEM = By.className("product-card");
    private static final By PRODUCT_NAME = By.className("product-name");
    private static final By PRODUCT_PRICE = By.className("product-price");
    private static final By ADD_TO_CART_BTN = By.className("add-to-cart");
    private static final By FILTER_BY_CATEGORY = By.id("categoryFilter");
    private static final By FILTER_BY_PRICE = By.id("priceFilter");
    private static final By SORT_DROPDOWN = By.id("sortBy");
    private static final By SEARCH_BOX = By.id("productSearch");
    private static final By NO_RESULTS = By.className("no-results");

    @Step("Verify products page is loaded")
    public boolean isProductsPageLoaded() {
        log.info("Verifying products page is loaded");
        return isElementDisplayed(PRODUCTS_CONTAINER);
    }

    @Step("Get total number of products")
    public int getTotalProductsCount() {
        log.info("Getting total number of products");
        try {
            List<WebElement> products = waitForElementsPresence(PRODUCT_ITEM);
            return products.size();
        } catch (Exception e) {
            log.warn("Error getting products count: {}", e.getMessage());
            return 0;
        }
    }

    @Step("Search for product: {searchTerm}")
    public void searchProduct(String searchTerm) {
        log.info("Searching for product: {}", searchTerm);
        type(SEARCH_BOX, searchTerm);
    }

    @Step("Filter by category: {category}")
    public void filterByCategory(String category) {
        log.info("Filtering by category: {}", category);
        click(FILTER_BY_CATEGORY);
        // Additional logic to select category from dropdown
    }

    @Step("Filter by price range: {minPrice} - {maxPrice}")
    public void filterByPrice(double minPrice, double maxPrice) {
        log.info("Filtering by price: {} - {}", minPrice, maxPrice);
        click(FILTER_BY_PRICE);
        // Additional logic to set price range
    }

    @Step("Sort products by: {sortOption}")
    public void sortProducts(String sortOption) {
        log.info("Sorting products by: {}", sortOption);
        click(SORT_DROPDOWN);
        // Additional logic to select sort option
    }

    @Step("Get all product names")
    public List<String> getAllProductNames() {
        log.info("Getting all product names");
        try {
            List<WebElement> products = waitForElementsPresence(PRODUCT_NAME);
            return products.stream().map(WebElement::getText).toList();
        } catch (Exception e) {
            log.warn("Error getting product names: {}", e.getMessage());
            return List.of();
        }
    }

    @Step("Get all product prices")
    public List<String> getAllProductPrices() {
        log.info("Getting all product prices");
        try {
            List<WebElement> prices = waitForElementsPresence(PRODUCT_PRICE);
            return prices.stream().map(WebElement::getText).toList();
        } catch (Exception e) {
            log.warn("Error getting product prices: {}", e.getMessage());
            return List.of();
        }
    }

    @Step("Click on product: {productIndex}")
    public void clickOnProduct(int productIndex) {
        log.info("Clicking on product at index: {}", productIndex);
        try {
            List<WebElement> products = waitForElementsPresence(PRODUCT_ITEM);
            if (productIndex < products.size()) {
                products.get(productIndex).click();
            } else {
                throw new RuntimeException("Product index out of bounds");
            }
        } catch (Exception e) {
            log.error("Error clicking on product: {}", e.getMessage());
            throw new RuntimeException("Click on product failed", e);
        }
    }

    @Step("Add product to cart: {productIndex}")
    public void addProductToCart(int productIndex) {
        log.info("Adding product to cart at index: {}", productIndex);
        try {
            List<WebElement> products = waitForElementsPresence(PRODUCT_ITEM);
            WebElement product = products.get(productIndex);
            WebElement addBtn = product.findElement(ADD_TO_CART_BTN);
            addBtn.click();
        } catch (Exception e) {
            log.error("Error adding product to cart: {}", e.getMessage());
            throw new RuntimeException("Add to cart failed", e);
        }
    }

    @Step("Verify no products message")
    public boolean isNoProductsMessageDisplayed() {
        return isElementDisplayed(NO_RESULTS);
    }

    @Step("Verify product exists: {productName}")
    public boolean isProductPresent(String productName) {
        log.info("Verifying product exists: {}", productName);
        List<String> productNames = getAllProductNames();
        return productNames.contains(productName);
    }
}
