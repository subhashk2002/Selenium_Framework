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
@Feature("API - Orders")
@Story("Order Management Operations")
public class OrderAPITest extends BaseTest {

    @BeforeMethod
    public void setUpOrderAPITest() {
        APIHelper.setBaseURI(config.getBaseUrl() + "/api");
        APIHelper.setAuthorizationHeader("dummy_token");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test create new order")
    public void testCreateOrder() {
        log.info("Test: testCreateOrder");

        String endpoint = "/orders";
        JsonObject orderItems = new JsonObject();
        orderItems.addProperty("productId", 1);
        orderItems.addProperty("quantity", 2);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("userId", "user123");
        requestBody.addProperty("totalAmount", 199.98);
        requestBody.add("items", new com.google.gson.JsonArray());

        Allure.step("Send POST request to create order");
        Response response = APIHelper.post(endpoint, requestBody.toString());

        Allure.step("Verify response status code is 201");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 201),
                "Response status code should be 201");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test retrieve order by ID")
    public void testGetOrderById() {
        log.info("Test: testGetOrderById");

        String orderId = "ORD-123";
        String endpoint = "/orders/" + orderId;

        Allure.step("Send GET request to fetch order with ID: " + orderId);
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");

        Allure.step("Verify order data is returned");
        String status = APIHelper.extractJsonPath(response, "status");
        Assert.assertNotNull(status, "Order status should be present");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test retrieve user orders")
    public void testGetUserOrders() {
        log.info("Test: testGetUserOrders");

        String endpoint = "/users/me/orders";

        Allure.step("Send GET request to fetch user orders");
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test update order status")
    public void testUpdateOrderStatus() {
        log.info("Test: testUpdateOrderStatus");

        String orderId = "ORD-123";
        String endpoint = "/orders/" + orderId;
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("status", "shipped");

        Allure.step("Send PUT request to update order status");
        Response response = APIHelper.put(endpoint, requestBody.toString());

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test cancel order")
    public void testCancelOrder() {
        log.info("Test: testCancelOrder");

        String orderId = "ORD-123";
        String endpoint = "/orders/" + orderId + "/cancel";

        Allure.step("Send POST request to cancel order");
        Response response = APIHelper.post(endpoint, "");

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test track order shipment")
    public void testTrackOrder() {
        log.info("Test: testTrackOrder");

        String orderId = "ORD-123";
        String endpoint = "/orders/" + orderId + "/track";

        Allure.step("Send GET request to track order");
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");

        Allure.step("Verify tracking information is returned");
        String trackingStatus = APIHelper.extractJsonPath(response, "status");
        Assert.assertNotNull(trackingStatus, "Tracking status should be present");
    }

    @Test(dataProvider = "orderDataProvider")
    @Severity(SeverityLevel.HIGH)
    @Description("Data-driven order creation test")
    public void testCreateOrderDataDriven(String productId, int quantity, int expectedStatusCode) {
        log.info("Test: testCreateOrderDataDriven - productId: {}, quantity: {}", productId, quantity);

        String endpoint = "/orders";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("productId", productId);
        requestBody.addProperty("quantity", quantity);
        requestBody.addProperty("userId", "user123");

        Allure.step("Send POST request to create order");
        Response response = APIHelper.post(endpoint, requestBody.toString());

        Allure.step("Verify response status code is " + expectedStatusCode);
        Assert.assertTrue(APIHelper.verifyStatusCode(response, expectedStatusCode),
                "Response status code should be " + expectedStatusCode);
    }

    @DataProvider(name = "orderDataProvider")
    public Object[][] getOrderData() {
        return new Object[][] {
                { "PROD-001", 2, 201 },
                { "PROD-002", 1, 201 },
                { "", 5, 400 },
                { "PROD-001", -1, 400 }
        };
    }

    @AfterMethod
    public void tearDownOrderAPITest() {
        super.tearDown();
    }
}
