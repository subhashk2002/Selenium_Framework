package com.automation.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import com.automation.config.ConfigManager;

@Slf4j
public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ConfigManager config = ConfigManager.getInstance();

    public static void initializeDriver() {
        if (driver.get() == null) {
            String browser = config.getBrowser();
            WebDriver webDriver = DriverFactory.createDriver(browser);
            driver.set(webDriver);
            webDriver.manage().window().maximize();
            log.info("WebDriver initialized for browser: {}", browser);
        }
    }

    public static void initializeDriver(String browser) {
        if (driver.get() == null) {
            WebDriver webDriver = DriverFactory.createDriver(browser);
            driver.set(webDriver);
            webDriver.manage().window().maximize();
            log.info("WebDriver initialized for browser: {}", browser);
        }
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                log.info("WebDriver quit successfully");
            } catch (Exception e) {
                log.error("Error while quitting WebDriver", e);
            } finally {
                driver.remove();
            }
        }
    }

    public static void resetDriver() {
        quitDriver();
        driver.remove();
        log.info("WebDriver reset");
    }

    public static boolean isDriverActive() {
        return driver.get() != null;
    }

    public static void navigateToBaseUrl() {
        String baseUrl = config.getBaseUrl();
        getDriver().navigate().to(baseUrl);
        log.info("Navigated to base URL: {}", baseUrl);
    }

    public static void navigateTo(String url) {
        getDriver().navigate().to(url);
        log.info("Navigated to: {}", url);
    }
}
