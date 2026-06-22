package com.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;

import java.time.Duration;
import java.util.List;

@Slf4j
public class WaitHelper {
    private static final ConfigManager config = ConfigManager.getInstance();

    private static WebDriverWait getWait(int timeoutSeconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds));
    }

    private static WebDriverWait getDefaultWait() {
        return getWait(config.getExplicitWait());
    }

    public static WebElement waitForElementToBeVisible(By locator) {
        try {
            log.debug("Waiting for element to be visible: {}", locator);
            return getDefaultWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.error("Element not visible within timeout: {}", locator);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }

    public static WebElement waitForElementToBeVisible(By locator, int timeoutSeconds) {
        try {
            log.debug("Waiting for element to be visible: {} with timeout: {}s", locator, timeoutSeconds);
            return getWait(timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.error("Element not visible within timeout: {} seconds", timeoutSeconds);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        try {
            log.debug("Waiting for element to be clickable: {}", locator);
            return getDefaultWait().until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            log.error("Element not clickable within timeout: {}", locator);
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }

    public static WebElement waitForElementToBeClickable(By locator, int timeoutSeconds) {
        try {
            log.debug("Waiting for element to be clickable: {} with timeout: {}s", locator, timeoutSeconds);
            return getWait(timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            log.error("Element not clickable within timeout");
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }

    public static WebElement waitForElementPresence(By locator) {
        try {
            log.debug("Waiting for element presence: {}", locator);
            return getDefaultWait().until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.error("Element not present within timeout: {}", locator);
            throw new RuntimeException("Element not present: " + locator, e);
        }
    }

    public static List<WebElement> waitForElementsPresence(By locator) {
        try {
            log.debug("Waiting for elements presence: {}", locator);
            return getDefaultWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            log.error("Elements not present within timeout: {}", locator);
            throw new RuntimeException("Elements not present: " + locator, e);
        }
    }

    public static void waitForElementToDisappear(By locator) {
        try {
            log.debug("Waiting for element to disappear: {}", locator);
            getDefaultWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
            log.info("Element disappeared: {}", locator);
        } catch (TimeoutException e) {
            log.warn("Element still visible after timeout: {}", locator);
        }
    }

    public static void waitForElementToBeInvisible(By locator, int timeoutSeconds) {
        try {
            log.debug("Waiting for element to be invisible: {} with timeout: {}s", locator, timeoutSeconds);
            getWait(timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.warn("Element still visible after timeout");
        }
    }

    public static void waitForTextToBePresent(By locator, String text) {
        try {
            log.debug("Waiting for text in element: {}", text);
            getDefaultWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (TimeoutException e) {
            log.error("Text not present in element within timeout");
            throw new RuntimeException("Text not found: " + text, e);
        }
    }

    public static void waitForAttributeContains(By locator, String attribute, String value) {
        try {
            log.debug("Waiting for attribute: {} to contain: {}", attribute, value);
            getDefaultWait().until(ExpectedConditions.attributeContains(locator, attribute, value));
        } catch (TimeoutException e) {
            log.error("Attribute condition not met within timeout");
            throw new RuntimeException("Attribute condition not met", e);
        }
    }

    public static void waitForPageLoadComplete() {
        WebDriver driver = DriverManager.getDriver();
        int timeout = config.getPageLoadTimeout();
        try {
            log.debug("Waiting for page load to complete");
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(
                    driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete")
            );
            log.info("Page load completed");
        } catch (TimeoutException e) {
            log.warn("Page load timeout exceeded");
        }
    }

    public static void waitForJQueryAjaxComplete() {
        WebDriver driver = DriverManager.getDriver();
        int timeout = config.getExplicitWait();
        try {
            log.debug("Waiting for jQuery AJAX to complete");
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(
                    driver1 -> (boolean) ((JavascriptExecutor) driver1)
                            .executeScript("return jQuery.active == 0")
            );
            log.info("jQuery AJAX completed");
        } catch (Exception e) {
            log.warn("jQuery AJAX wait failed: {}", e.getMessage());
        }
    }

    public static void implicitWait(long millis) {
        try {
            log.debug("Implicit wait for: {}ms", millis);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Sleep interrupted", e);
        }
    }

    public static void waitUntil(ExpectedCondition<?> condition) {
        try {
            log.debug("Waiting for custom condition");
            getDefaultWait().until(condition);
        } catch (TimeoutException e) {
            log.error("Custom condition not met within timeout");
            throw new RuntimeException("Custom condition failed", e);
        }
    }

    public static void waitUntil(ExpectedCondition<?> condition, int timeoutSeconds) {
        try {
            log.debug("Waiting for custom condition with timeout: {}s", timeoutSeconds);
            getWait(timeoutSeconds).until(condition);
        } catch (TimeoutException e) {
            log.error("Custom condition not met within timeout");
            throw new RuntimeException("Custom condition failed", e);
        }
    }
}
