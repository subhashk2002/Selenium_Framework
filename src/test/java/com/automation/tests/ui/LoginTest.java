package com.automation.tests.ui;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.automation.pages.LoginPage;
import com.automation.tests.BaseTest;

@Slf4j
@Feature("Authentication")
@Story("User Login")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpLoginTest() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test successful login with valid credentials")
    public void testLoginWithValidCredentials() {
        log.info("Test: testLoginWithValidCredentials");

        String username = "testuser@example.com";
        String password = "TestPassword@123";

        loginPage.login(username, password);

        Allure.step("Verify successful login");
        verifyPageUrl("/dashboard");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test login with invalid password")
    public void testLoginWithInvalidPassword() {
        log.info("Test: testLoginWithInvalidPassword");

        String username = "testuser@example.com";
        String password = "WrongPassword";

        loginPage.login(username, password);

        Allure.step("Verify error message is displayed");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test login with empty username")
    public void testLoginWithEmptyUsername() {
        log.info("Test: testLoginWithEmptyUsername");

        loginPage.enterPassword("TestPassword@123");
        loginPage.clickLoginButton();

        Allure.step("Verify error message for empty username");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty username");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test login with empty password")
    public void testLoginWithEmptyPassword() {
        log.info("Test: testLoginWithEmptyPassword");

        loginPage.enterUsername("testuser@example.com");
        loginPage.clickLoginButton();

        Allure.step("Verify error message for empty password");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty password");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test remember me checkbox functionality")
    public void testRememberMeCheckbox() {
        log.info("Test: testRememberMeCheckbox");

        loginPage.enterUsername("testuser@example.com");
        loginPage.enterPassword("TestPassword@123");
        loginPage.checkRememberMe();
        loginPage.clickLoginButton();

        Allure.step("Verify login successful with remember me enabled");
        verifyPageUrl("/dashboard");
    }

    @Test(dataProvider = "loginData")
    @Severity(SeverityLevel.HIGH)
    @Description("Data-driven login test")
    public void testLoginWithDataProvider(String username, String password, String expectedResult) {
        log.info("Test: testLoginWithDataProvider - username: {}, expectedResult: {}", username, expectedResult);

        loginPage.login(username, password);

        if ("success".equals(expectedResult)) {
            Allure.step("Verify successful login");
            verifyPageUrl("/dashboard");
        } else {
            Allure.step("Verify login failed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                    "Error message should be displayed");
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                { "testuser@example.com", "TestPassword@123", "success" },
                { "testuser@example.com", "WrongPassword", "failure" },
                { "invaliduser@example.com", "TestPassword@123", "failure" }
        };
    }

    @AfterMethod
    public void tearDownLoginTest() {
        super.tearDown();
    }
}
