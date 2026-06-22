package com.automation.tests;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;
import com.automation.utils.ScreenshotHelper;

@Slf4j
public class BaseTest {
    protected ConfigManager config;

    @BeforeMethod
    public void setUp() {
        log.info("===== Test Setup Started =====");
        config = ConfigManager.getInstance();
        DriverManager.initializeDriver();
        DriverManager.navigateToBaseUrl();
        log.info("===== Test Setup Completed =====");
    }

    @AfterMethod
    public void tearDown() {
        log.info("===== Test Teardown Started =====");
        try {
            if (DriverManager.isDriverActive()) {
                if (config.enableScreenshots()) {
                    String testName = getTestName();
                    ScreenshotHelper.captureScreenshot(testName);
                }
                DriverManager.quitDriver();
            }
        } catch (Exception e) {
            log.error("Error during teardown", e);
        }
        log.info("===== Test Teardown Completed =====");
    }

    @Step("Get test name")
    protected String getTestName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    @Step("Navigate to URL: {url}")
    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        DriverManager.navigateTo(url);
    }

    @Step("Verify page title contains: {expectedTitle}")
    protected void verifyPageTitle(String expectedTitle) {
        String actualTitle = DriverManager.getDriver().getTitle();
        if (actualTitle.contains(expectedTitle)) {
            log.info("Page title verified: {}", actualTitle);
        } else {
            throw new AssertionError("Expected title to contain: " + expectedTitle + ", but got: " + actualTitle);
        }
    }

    @Step("Verify page URL contains: {expectedUrl}")
    protected void verifyPageUrl(String expectedUrl) {
        String actualUrl = DriverManager.getDriver().getCurrentUrl();
        if (actualUrl.contains(expectedUrl)) {
            log.info("Page URL verified: {}", actualUrl);
        } else {
            throw new AssertionError("Expected URL to contain: " + expectedUrl + ", but got: " + actualUrl);
        }
    }

    @Step("Wait for seconds: {seconds}")
    protected void waitForSeconds(int seconds) {
        try {
            log.debug("Waiting for {} seconds", seconds);
            Thread.sleep((long) seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Sleep interrupted", e);
        }
    }
}
