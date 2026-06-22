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
@Feature("API - User Management")
@Story("User CRUD Operations")
public class UserAPITest extends BaseTest {

    @BeforeMethod
    public void setUpUserAPITest() {
        APIHelper.setBaseURI(config.getBaseUrl() + "/api");
        APIHelper.setAuthorizationHeader("dummy_token");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test retrieve user profile via API")
    public void testGetUserProfile() {
        log.info("Test: testGetUserProfile");

        String endpoint = "/users/me";

        Allure.step("Send GET request to fetch user profile");
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");

        Allure.step("Verify user data is returned");
        String email = APIHelper.extractJsonPath(response, "email");
        Assert.assertNotNull(email, "Email should be present in response");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test update user profile via API")
    public void testUpdateUserProfile() {
        log.info("Test: testUpdateUserProfile");

        String endpoint = "/users/me";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("firstName", "Updated");
        requestBody.addProperty("lastName", "Name");

        Allure.step("Send PUT request to update user profile");
        Response response = APIHelper.put(endpoint, requestBody.toString());

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test get all users with pagination")
    public void testGetAllUsers() {
        log.info("Test: testGetAllUsers");

        String endpoint = "/users";

        Allure.step("Send GET request to fetch all users");
        Response response = APIHelper.get(endpoint, "page", "1");

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test get user by ID")
    public void testGetUserById() {
        log.info("Test: testGetUserById");

        String userId = "123";
        String endpoint = "/users/" + userId;

        Allure.step("Send GET request to fetch user with ID: " + userId);
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test delete user account")
    public void testDeleteUserAccount() {
        log.info("Test: testDeleteUserAccount");

        String endpoint = "/users/me";

        Allure.step("Send DELETE request to delete user account");
        Response response = APIHelper.delete(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test change password via API")
    public void testChangePassword() {
        log.info("Test: testChangePassword");

        String endpoint = "/users/change-password";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("currentPassword", "OldPassword@123");
        requestBody.addProperty("newPassword", "NewPassword@123");

        Allure.step("Send POST request to change password");
        Response response = APIHelper.post(endpoint, requestBody.toString());

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test(dataProvider = "userDataProvider")
    @Severity(SeverityLevel.HIGH)
    @Description("Data-driven user update test")
    public void testUpdateUserDataDriven(String firstName, String lastName, int expectedStatusCode) {
        log.info("Test: testUpdateUserDataDriven - firstName: {}, lastName: {}", firstName, lastName);

        String endpoint = "/users/me";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("firstName", firstName);
        requestBody.addProperty("lastName", lastName);

        Allure.step("Send PUT request to update user");
        Response response = APIHelper.put(endpoint, requestBody.toString());

        Allure.step("Verify response status code is " + expectedStatusCode);
        Assert.assertTrue(APIHelper.verifyStatusCode(response, expectedStatusCode),
                "Response status code should be " + expectedStatusCode);
    }

    @DataProvider(name = "userDataProvider")
    public Object[][] getUserData() {
        return new Object[][] {
                { "John", "Doe", 200 },
                { "", "Doe", 400 },
                { "John", "", 400 }
        };
    }

    @AfterMethod
    public void tearDownUserAPITest() {
        super.tearDown();
    }
}
