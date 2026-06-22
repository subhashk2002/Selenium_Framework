package com.automation.tests.ui;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.automation.pages.HomePage;
import com.automation.tests.BaseTest;

@Slf4j
@Feature("Home Page")
@Story("User Navigation")
public class HomePageTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void setUpHomePageTest() {
        super.setUp();
        homePage = new HomePage();
        loginUserBeforeTest();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test home page is loaded successfully")
    public void testHomePageLoaded() {
        log.info("Test: testHomePageLoaded");

        Allure.step("Verify home page is loaded");
        Assert.assertTrue(homePage.isHomePageLoaded(),
                "Home page should be loaded");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test user profile is displayed on home page")
    public void testUserProfileDisplayed() {
        log.info("Test: testUserProfileDisplayed");

        Allure.step("Verify user profile is displayed");
        Assert.assertTrue(homePage.isUserProfileDisplayed(),
                "User profile should be displayed");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test search functionality on home page")
    public void testSearchFunctionality() {
        log.info("Test: testSearchFunctionality");

        String searchTerm = "selenium";

        Allure.step("Perform search for: " + searchTerm);
        homePage.search(searchTerm);

        waitForSeconds(2);
        verifyPageUrl("search");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test navigation to products page")
    public void testNavigateToProducts() {
        log.info("Test: testNavigateToProducts");

        Allure.step("Navigate to products page");
        homePage.navigateToProducts();

        waitForSeconds(2);
        verifyPageUrl("/products");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test navigation to dashboard")
    public void testNavigateToDashboard() {
        log.info("Test: testNavigateToDashboard");

        Allure.step("Navigate to dashboard");
        homePage.navigateToDashboard();

        waitForSeconds(2);
        verifyPageUrl("/dashboard");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test user logout")
    public void testUserLogout() {
        log.info("Test: testUserLogout");

        Allure.step("Click user profile");
        homePage.clickUserProfile();

        waitForSeconds(1);

        Allure.step("Click logout button");
        homePage.logout();

        waitForSeconds(2);
        verifyPageUrl("/login");
    }

    @Test
    @Severity(SeverityLevel.LOW)
    @Description("Test page title is correct")
    public void testPageTitle() {
        log.info("Test: testPageTitle");

        Allure.step("Get and verify page title");
        String title = homePage.getPageTitle();
        Assert.assertNotNull(title, "Page title should not be null");
    }

    private void loginUserBeforeTest() {
        log.info("Pre-test: Login user before test execution");
        // This would typically call the login API or UI flow
        // For now, we assume user is already logged in
    }

    @AfterMethod
    public void tearDownHomePageTest() {
        super.tearDown();
    }
}
