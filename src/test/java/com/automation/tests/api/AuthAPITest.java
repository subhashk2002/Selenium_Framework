package com.automation.tests.api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.automation.api.APIHelper;
import com.automation.tests.BaseTest;
import com.google.gson.JsonObject;

@Slf4j
@Feature("API - Authentication")
@Story("User Authentication via REST API")
public class AuthAPITest extends BaseTest {

    @BeforeMethod
    public void setUpAuthAPITest() {
        APIHelper.setBaseURI(config.getBaseUrl() + "/api");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test user login via API with valid credentials")
    public void testLoginWithValidCredentials() {
        log.info("Test: testLoginWithValidCredentials");

        String loginEndpoint = "/auth/login";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", "testuser@example.com");
        requestBody.addProperty("password", "TestPassword@123");

        Allure.step("Send POST request to login endpoint");
        Response response = APIHelper.post(loginEndpoint, requestBody.toString());

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");

        Allure.step("Verify token is returned");
        String token = APIHelper.extractJsonPath(response, "token");
        Assert.assertNotNull(token, "Token should be returned in response");

        APIHelper.setAuthorizationHeader(token);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test login with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        log.info("Test: testLoginWithInvalidCredentials");

        String loginEndpoint = "/auth/login";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", "testuser@example.com");
        requestBody.addProperty("password", "WrongPassword");

        Allure.step("Send POST request with invalid password");
        Response response = APIHelper.post(loginEndpoint, requestBody.toString());

        Allure.step("Verify response status code is 401");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 401),
                "Response status code should be 401 for invalid credentials");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test login with empty email")
    public void testLoginWithEmptyEmail() {
        log.info("Test: testLoginWithEmptyEmail");

        String loginEndpoint = "/auth/login";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", "");
        requestBody.addProperty("password", "TestPassword@123");

        Allure.step("Send POST request with empty email");
        Response response = APIHelper.post(loginEndpoint, requestBody.toString());

        Allure.step("Verify response status code is 400");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 400),
                "Response status code should be 400 for empty email");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test user registration via API")
    public void testUserRegistration() {
        log.info("Test: testUserRegistration");

        String registerEndpoint = "/auth/register";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("firstName", "Test");
        requestBody.addProperty("lastName", "User");
        requestBody.addProperty("email", "newuser@example.com");
        requestBody.addProperty("password", "TestPassword@123");

        Allure.step("Send POST request to register endpoint");
        Response response = APIHelper.post(registerEndpoint, requestBody.toString());

        Allure.step("Verify response status code is 201");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 201),
                "Response status code should be 201 for successful registration");

        Allure.step("Verify user ID is returned");
        String userId = APIHelper.extractJsonPath(response, "userId");
        Assert.assertNotNull(userId, "User ID should be returned");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test logout via API")
    public void testLogout() {
        log.info("Test: testLogout");

        // First, login to get a token
        testLoginWithValidCredentials();

        String logoutEndpoint = "/auth/logout";

        Allure.step("Send POST request to logout endpoint");
        Response response = APIHelper.post(logoutEndpoint, "");

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200 for logout");
    }

    @Test(dataProvider = "loginDataProvider")
    @Severity(SeverityLevel.HIGH)
    @Description("Data-driven API login test")
    public void testLoginDataDriven(String email, String password, int expectedStatusCode) {
        log.info("Test: testLoginDataDriven - email: {}, expectedStatusCode: {}", email, expectedStatusCode);

        String loginEndpoint = "/auth/login";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", password);

        Allure.step("Send login request with email: " + email);
        Response response = APIHelper.post(loginEndpoint, requestBody.toString());

        Allure.step("Verify response status code is " + expectedStatusCode);
        Assert.assertTrue(APIHelper.verifyStatusCode(response, expectedStatusCode),
                "Response status code should be " + expectedStatusCode);
    }

    @DataProvider(name = "loginDataProvider")
    public Object[][] getLoginData() {
        return new Object[][] {
                { "testuser@example.com", "TestPassword@123", 200 },
                { "testuser@example.com", "WrongPassword", 401 },
                { "", "TestPassword@123", 400 },
                { "nonexistent@example.com", "Password@123", 401 }
        };
    }

    @AfterMethod
    public void tearDownAuthAPITest() {
        super.tearDown();
    }
}
