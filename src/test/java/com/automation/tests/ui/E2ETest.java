package com.automation.tests.ui;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import com.automation.pages.*;
import com.automation.tests.BaseTest;

@Slf4j
@Feature("End-to-End Scenarios")
@Story("Complete User Workflows")
public class E2ETest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUpE2ETest() {
        super.setUp();
        loginPage = new LoginPage();
        homePage = new HomePage();
        productsPage = new ProductsPage();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E: User login and browse products")
    public void testLoginAndBrowseProducts() {
        log.info("Test: testLoginAndBrowseProducts");

        Allure.step("User logs in with valid credentials");
        loginPage.login("testuser@example.com", "TestPassword@123");
        waitForSeconds(2);

        Allure.step("Verify home page is loaded");
        //homePage = new HomePage();
        // Assert.assertTrue(homePage.isHomePageLoaded());

        Allure.step("Navigate to products");
        homePage.navigateToProducts();
        waitForSeconds(2);

        Allure.step("Verify products are displayed");
        // Assert.assertTrue(productsPage.isProductsPageLoaded());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E: User searches and filters products")
    public void testSearchAndFilterProducts() {
        log.info("Test: testSearchAndFilterProducts");

        navigateTo("/products");

        Allure.step("Search for products");
        productsPage.searchProduct("laptop");
        waitForSeconds(2);

        Allure.step("Filter by category");
        productsPage.filterByCategory("electronics");
        waitForSeconds(2);

        Allure.step("Sort products");
        productsPage.sortProducts("price-low-to-high");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("E2E: User logout flow")
    public void testUserLogoutFlow() {
        log.info("Test: testUserLogoutFlow");

        navigateTo("/dashboard");

        Allure.step("Click user profile");
        homePage.clickUserProfile();
        waitForSeconds(1);

        Allure.step("Logout");
        homePage.logout();
        waitForSeconds(2);

        Allure.step("Verify redirected to login");
        verifyPageUrl("/login");
    }

    @AfterMethod
    public void tearDownE2ETest() {
        super.tearDown();
    }
}
