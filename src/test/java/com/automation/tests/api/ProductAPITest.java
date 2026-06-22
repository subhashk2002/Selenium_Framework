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
@Feature("API - Products")
@Story("Product Catalog Operations")
public class ProductAPITest extends BaseTest {

    @BeforeMethod
    public void setUpProductAPITest() {
        APIHelper.setBaseURI(config.getBaseUrl() + "/api");
        APIHelper.setAuthorizationHeader("dummy_token");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test retrieve all products")
    public void testGetAllProducts() {
        log.info("Test: testGetAllProducts");

        String endpoint = "/products";

        Allure.step("Send GET request to fetch all products");
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test retrieve product by ID")
    public void testGetProductById() {
        log.info("Test: testGetProductById");

        String productId = "1";
        String endpoint = "/products/" + productId;

        Allure.step("Send GET request to fetch product with ID: " + productId);
        Response response = APIHelper.get(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");

        Allure.step("Verify product data is returned");
        String productName = APIHelper.extractJsonPath(response, "name");
        Assert.assertNotNull(productName, "Product name should be present");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test search products by category")
    public void testSearchProductsByCategory() {
        log.info("Test: testSearchProductsByCategory");

        String endpoint = "/products";

        Allure.step("Send GET request with category filter");
        Response response = APIHelper.get(endpoint, "category", "electronics");

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test search products with price range")
    public void testSearchProductsByPriceRange() {
        log.info("Test: testSearchProductsByPriceRange");

        String endpoint = "/products";

        Allure.step("Send GET request with price range filter");
        Response response = APIHelper.get(endpoint, "minPrice", "10");

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test create new product (admin only)")
    public void testCreateProduct() {
        log.info("Test: testCreateProduct");

        String endpoint = "/products";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", "New Product");
        requestBody.addProperty("description", "Product Description");
        requestBody.addProperty("price", 99.99);
        requestBody.addProperty("category", "electronics");
        requestBody.addProperty("quantity", 100);

        Allure.step("Send POST request to create product");
        Response response = APIHelper.post(endpoint, requestBody.toString());

        Allure.step("Verify response status code is 201");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 201),
                "Response status code should be 201");
    }

    @Test
    @Severity(SeverityLevel.HIGH)
    @Description("Test update product details")
    public void testUpdateProduct() {
        log.info("Test: testUpdateProduct");

        String productId = "1";
        String endpoint = "/products/" + productId;
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", "Updated Product");
        requestBody.addProperty("price", 149.99);

        Allure.step("Send PUT request to update product");
        Response response = APIHelper.put(endpoint, requestBody.toString());

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test
    @Severity(SeverityLevel.MEDIUM)
    @Description("Test delete product")
    public void testDeleteProduct() {
        log.info("Test: testDeleteProduct");

        String productId = "1";
        String endpoint = "/products/" + productId;

        Allure.step("Send DELETE request to delete product");
        Response response = APIHelper.delete(endpoint);

        Allure.step("Verify response status code is 200");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200),
                "Response status code should be 200");
    }

    @Test(dataProvider = "productDataProvider")
    @Severity(SeverityLevel.HIGH)
    @Description("Data-driven product creation test")
    public void testCreateProductDataDriven(String name, String category, double price, int expectedStatusCode) {
        log.info("Test: testCreateProductDataDriven - name: {}, category: {}, price: {}", name, category, price);

        String endpoint = "/products";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", name);
        requestBody.addProperty("category", category);
        requestBody.addProperty("price", price);

        Allure.step("Send POST request to create product");
        Response response = APIHelper.post(endpoint, requestBody.toString());

        Allure.step("Verify response status code is " + expectedStatusCode);
        Assert.assertTrue(APIHelper.verifyStatusCode(response, expectedStatusCode),
                "Response status code should be " + expectedStatusCode);
    }

    @DataProvider(name = "productDataProvider")
    public Object[][] getProductData() {
        return new Object[][] {
                { "Laptop", "electronics", 999.99, 201 },
                { "", "electronics", 999.99, 400 },
                { "Product", "", 999.99, 400 },
                { "Product", "electronics", -50.00, 400 }
        };
    }

    @AfterMethod
    public void tearDownProductAPITest() {
        super.tearDown();
    }
}
