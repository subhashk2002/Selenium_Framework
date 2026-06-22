package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import com.automation.config.ConfigManager;

@Slf4j
public class DriverFactory {
    private static final ConfigManager config = ConfigManager.getInstance();

    public static WebDriver createDriver(String browserName) {
        String browser = browserName.toLowerCase().trim();
        log.info("Creating WebDriver for browser: {}", browser);

        return switch (browser) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            case "safari" -> createSafariDriver();
            default -> {
                log.warn("Unknown browser: {}. Defaulting to Chrome", browser);
                yield createChromeDriver();
            }
        };
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
            log.debug("Chrome running in headless mode");
        }

        options.addArguments(
                "--disable-blink-features=AutomationControlled",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
        );

        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (config.isHeadless()) {
            options.addArguments("-headless");
            log.debug("Firefox running in headless mode");
        }

        options.addArguments("--disable-blink-features=AutomationControlled");

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
            log.debug("Edge running in headless mode");
        }

        options.addArguments(
                "--disable-blink-features=AutomationControlled",
                "--no-sandbox"
        );

        return new EdgeDriver(options);
    }

    private static WebDriver createSafariDriver() {
        log.info("Creating Safari WebDriver (no WebDriverManager needed)");
        return new SafariDriver();
    }
}
