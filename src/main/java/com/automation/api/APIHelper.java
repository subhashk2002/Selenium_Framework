package com.automation.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import com.automation.config.ConfigManager;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class APIHelper {
    private static final ConfigManager config = ConfigManager.getInstance();
    private static RequestSpecification requestSpec;
    private static final Map<String, String> headers = new HashMap<>();

    static {
        RestAssured.baseURI = config.getBaseUrl();
        RestAssured.filters(new AllureRestAssured());
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
    }

    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
        log.info("Base URI set to: {}", baseURI);
    }

    public static void addHeader(String key, String value) {
        headers.put(key, value);
        log.debug("Header added: {} = {}", key, value);
    }

    public static void removeHeader(String key) {
        headers.remove(key);
        log.debug("Header removed: {}", key);
    }

    public static void setAuthorizationHeader(String token) {
        headers.put("Authorization", "Bearer " + token);
        log.debug("Authorization header set");
    }

    private static RequestSpecification getRequestSpec() {
        RequestSpecification spec = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON);
        return spec;
    }

    public static Response get(String endpoint) {
        log.info("GET request to: {}", endpoint);
        return getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response get(String endpoint, String queryParam, String value) {
        log.info("GET request to: {} with param: {}={}", endpoint, queryParam, value);
        return getRequestSpec()
                .queryParam(queryParam, value)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post(String endpoint, String body) {
        log.info("POST request to: {} with body: {}", endpoint, body);
        return getRequestSpec()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object body) {
        log.info("POST request to: {}", endpoint);
        return getRequestSpec()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response put(String endpoint, String body) {
        log.info("PUT request to: {}", endpoint);
        return getRequestSpec()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body) {
        log.info("PUT request to: {}", endpoint);
        return getRequestSpec()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response patch(String endpoint, String body) {
        log.info("PATCH request to: {}", endpoint);
        return getRequestSpec()
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        log.info("DELETE request to: {}", endpoint);
        return getRequestSpec()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String endpoint, String pathParam, String value) {
        log.info("DELETE request to: {}/{}", endpoint, value);
        return getRequestSpec()
                .pathParam(pathParam, value)
                .when()
                .delete(endpoint + "/{" + pathParam + "}")
                .then()
                .extract()
                .response();
    }

    public static boolean verifyStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        boolean isMatch = actualStatusCode == expectedStatusCode;
        log.info("Status Code Verification - Expected: {}, Actual: {}, Match: {}",
                expectedStatusCode, actualStatusCode, isMatch);
        return isMatch;
    }

    public static String extractJsonPath(Response response, String jsonPath) {
        log.debug("Extracting JSON path: {}", jsonPath);
        return response.jsonPath().get(jsonPath).toString();
    }

    public static void logResponseDetails(Response response) {
        log.info("Response Status Code: {}", response.getStatusCode());
        log.info("Response Content Type: {}", response.getContentType());
        log.info("Response Body: {}", response.getBody().asString());
    }
}
