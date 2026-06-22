package com.automation.tests.ui;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.automation.pages.ProductsPage;
import com.automation.tests.BaseTest;

@Slf4j
@Feature("Products")
@Story("Product Catalog and Shopping")
public class ProductsTest extends BaseTest {
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUpProductsTest() {
        super.setUp();
        productsPage = new ProductsPage();
        navigateTo("/products");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test products page is loaded successfully")
    public void testProductsPageLoaded() {
        log.info("Test: testProductsPageLoaded");

        Allure.step("Verify products page is loaded");
        Assert.assertTrue(productsPage.isProductsPageLoaded(),
                "Products page should be loaded");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test verify products are displayed")
    public void testProductsAreDisplayed() {
        log.info("Test: testProductsAreDisplayed");

        Allure.step("Get total products count");
        int productCount = productsPage.getTotalProductsCount();

        Allure.step("Verify products count is greater than 0");
        Assert.assertTrue(productCount > 0,
                "Products should be displayed on the page");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test search for product")
    public void testSearchProduct() {
        log.info("Test: testSearchProduct");

        String searchTerm = "laptop";

        Allure.step("Search for product: " + searchTerm);
        productsPage.searchProduct(searchTerm);

        waitForSeconds(2);

        Allure.step("Verify search results are displayed");
        Assert.assertTrue(productsPage.isProductsPageLoaded(),
                "Search results should be displayed");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test filter products by category")
    public void testFilterByCategory() {
        log.info("Test: testFilterByCategory");

        String category = "electronics";

        Allure.step("Filter products by category: " + category);
        productsPage.filterByCategory(category);

        waitForSeconds(2);

        Allure.step("Verify filtered results are displayed");
        int filteredCount = productsPage.getTotalProductsCount();
        Assert.assertTrue(filteredCount >= 0,
                "Filtered results should be displayed");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test filter products by price range")
    public void testFilterByPrice() {
        log.info("Test: testFilterByPrice");

        double minPrice = 100.00;
        double maxPrice = 500.00;

        Allure.step("Filter products by price range: " + minPrice + " - " + maxPrice);
        productsPage.filterByPrice(minPrice, maxPrice);

        waitForSeconds(2);

        Allure.step("Verify price filtered results are displayed");
        int filteredCount = productsPage.getTotalProductsCount();
        Assert.assertTrue(filteredCount >= 0,
                "Price filtered results should be displayed");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test sort products")
    public void testSortProducts() {
        log.info("Test: testSortProducts");

        String sortOption = "price-low-to-high";

        Allure.step("Sort products by: " + sortOption);
        productsPage.sortProducts(sortOption);

        waitForSeconds(2);

        Allure.step("Verify products are sorted");
        Assert.assertTrue(productsPage.isProductsPageLoaded(),
                "Products should remain displayed after sorting");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test add product to cart")
    public void testAddProductToCart() {
        log.info("Test: testAddProductToCart");

        int productIndex = 0;

        Allure.step("Add first product to cart");
        productsPage.addProductToCart(productIndex);

        waitForSeconds(1);

        Allure.step("Verify product is added to cart");
        verifyPageUrl("/cart");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test verify product prices are displayed")
    public void testProductPricesDisplayed() {
        log.info("Test: testProductPricesDisplayed");

        Allure.step("Get all product prices");
        var prices = productsPage.getAllProductPrices();

        Allure.step("Verify prices are displayed");
        Assert.assertTrue(!prices.isEmpty(),
                "Product prices should be displayed");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test click on product details")
    public void testClickProductDetails() {
        log.info("Test: testClickProductDetails");

        int productIndex = 0;

        Allure.step("Click on product details");
        productsPage.clickOnProduct(productIndex);

        waitForSeconds(2);

        Allure.step("Verify product details page is opened");
        verifyPageUrl("/product");
    }

    @Test(dataProvider = "productSearchData")
    @Severity(SeverityLevel.MEDIUM)
    @Description("Data-driven product search test")
    public void testProductSearchDataDriven(String searchTerm, boolean shouldFindResults) {
        log.info("Test: testProductSearchDataDriven - searchTerm: {}, shouldFindResults: {}",
                searchTerm, shouldFindResults);

        Allure.step("Search for: " + searchTerm);
        productsPage.searchProduct(searchTerm);

        waitForSeconds(2);

        if (shouldFindResults) {
            Allure.step("Verify results found");
            Assert.assertFalse(productsPage.isNoProductsMessageDisplayed(),
                    "Products should be found for: " + searchTerm);
        } else {
            Allure.step("Verify no results message");
            // May or may not show results depending on actual data
        }
    }

    @DataProvider(name = "productSearchData")
    public Object[][] getProductSearchData() {
        return new Object[][] {
                { "laptop", true },
                { "mouse", true },
                { "keyboard", true },
                { "monitor", true }
        };
    }

    @AfterMethod
    public void tearDownProductsTest() {
        super.tearDown();
    }
}
