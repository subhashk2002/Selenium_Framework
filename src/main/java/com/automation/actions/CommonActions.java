package com.automation.actions;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * CommonActions class - Contains all reusable common methods for testing framework
 * Implements framework standards for UI interactions
 */
@Slf4j
public class CommonActions {

    private static final WebDriver driver = DriverManager.getDriver();
    private static final ConfigManager config = ConfigManager.getInstance();
    private static final int EXPLICIT_WAIT = config.getExplicitWait();
    private static final int IMPLICIT_WAIT = config.getImplicitWait();

    // ==================== CLICK OPERATIONS ====================

    /**
     * Enhanced click with retry mechanism
     * Handles stale element exceptions and retries automatically
     *
     * @param locator - Element locator
     */
    public static void clickWithRetry(By locator) {
        int retries = 3;
        for (int i = 0; i < retries; i++) {
            try {
                log.debug("Attempt {} to click element: {}", i + 1, locator);
                waitForElementClickable(locator);
                WebElement element = driver.findElement(locator);
                element.click();
                log.info("Element clicked successfully: {}", locator);
                return;
            } catch (StaleElementReferenceException e) {
                log.warn("Stale element exception on attempt {}, retrying...", i + 1);
                if (i == retries - 1) {
                    throw new RuntimeException("Failed to click element after " + retries + " attempts", e);
                }
            } catch (ElementClickInterceptedException e) {
                log.warn("Element click intercepted, trying JavaScript click");
                clickUsingJavaScript(locator);
                return;
            } catch (Exception e) {
                log.error("Error clicking element: {}", e.getMessage());
                throw new RuntimeException("Click failed for: " + locator, e);
            }
        }
    }

    /**
     * Click using JavaScript (for hard-to-click elements)
     *
     * @param locator - Element locator
     */
    public static void clickUsingJavaScript(By locator) {
        try {
            log.debug("Clicking using JavaScript: {}", locator);
            waitForElementPresence(locator);
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            log.info("Element clicked using JavaScript: {}", locator);
        } catch (Exception e) {
            log.error("JavaScript click failed: {}", e.getMessage());
            throw new RuntimeException("JavaScript click failed", e);
        }
    }

    /**
     * Double click on element
     *
     * @param locator - Element locator
     */
    public static void doubleClick(By locator) {
        try {
            log.debug("Double clicking element: {}", locator);
            waitForElementClickable(locator);
            WebElement element = driver.findElement(locator);
            new Actions(driver).doubleClick(element).perform();
            log.info("Element double clicked: {}", locator);
        } catch (Exception e) {
            log.error("Double click failed: {}", e.getMessage());
            throw new RuntimeException("Double click failed", e);
        }
    }

    /**
     * Right click (context click) on element
     *
     * @param locator - Element locator
     */
    public static void rightClick(By locator) {
        try {
            log.debug("Right clicking element: {}", locator);
            waitForElementClickable(locator);
            WebElement element = driver.findElement(locator);
            new Actions(driver).contextClick(element).perform();
            log.info("Element right clicked: {}", locator);
        } catch (Exception e) {
            log.error("Right click failed: {}", e.getMessage());
            throw new RuntimeException("Right click failed", e);
        }
    }

    /**
     * Click on element with offset
     *
     * @param locator - Element locator
     * @param xOffset - X axis offset
     * @param yOffset - Y axis offset
     */
    public static void clickWithOffset(By locator, int xOffset, int yOffset) {
        try {
            log.debug("Clicking element with offset ({}, {}): {}", xOffset, yOffset, locator);
            waitForElementClickable(locator);
            WebElement element = driver.findElement(locator);
            new Actions(driver).moveToElement(element, xOffset, yOffset).click().perform();
            log.info("Element clicked with offset: {}", locator);
        } catch (Exception e) {
            log.error("Click with offset failed: {}", e.getMessage());
            throw new RuntimeException("Click with offset failed", e);
        }
    }

    // ==================== TYPE/TEXT OPERATIONS ====================

    /**
     * Clear and type text with verification
     *
     * @param locator - Element locator
     * @param text - Text to type
     */
    public static void clearAndType(By locator, String text) {
        try {
            log.debug("Clearing and typing in element: {}", locator);
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);

            // Clear existing text
            element.clear();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);

            // Type new text
            element.sendKeys(text);

            // Verify text was entered
            String enteredText = element.getAttribute("value");
            if (!enteredText.equals(text)) {
                log.warn("Entered text doesn't match expected. Expected: {}, Got: {}", text, enteredText);
            }
            log.info("Text entered successfully: {}", text);
        } catch (Exception e) {
            log.error("Clear and type failed: {}", e.getMessage());
            throw new RuntimeException("Clear and type failed", e);
        }
    }

    /**
     * Type slowly (character by character) for difficult input fields
     *
     * @param locator - Element locator
     * @param text - Text to type
     * @param delayMs - Delay between characters in milliseconds
     */
    public static void typeSlowly(By locator, String text, long delayMs) {
        try {
            log.debug("Typing slowly in element: {}", locator);
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);
            element.click();

            for (char character : text.toCharArray()) {
                element.sendKeys(String.valueOf(character));
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            log.info("Text typed slowly: {}", text);
        } catch (Exception e) {
            log.error("Slow type failed: {}", e.getMessage());
            throw new RuntimeException("Slow type failed", e);
        }
    }

    /**
     * Get text from element with trim
     *
     * @param locator - Element locator
     * @return - Text content
     */
    public static String getText(By locator) {
        try {
            log.debug("Getting text from element: {}", locator);
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);
            String text = element.getText().trim();
            log.info("Text retrieved: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Get text failed: {}", e.getMessage());
            throw new RuntimeException("Get text failed", e);
        }
    }

    /**
     * Get attribute value from element
     *
     * @param locator - Element locator
     * @param attributeName - Attribute name
     * @return - Attribute value
     */
    public static String getAttribute(By locator, String attributeName) {
        try {
            log.debug("Getting attribute '{}' from element: {}", attributeName, locator);
            waitForElementPresence(locator);
            WebElement element = driver.findElement(locator);
            String value = element.getAttribute(attributeName);
            log.info("Attribute value: {}", value);
            return value;
        } catch (Exception e) {
            log.error("Get attribute failed: {}", e.getMessage());
            throw new RuntimeException("Get attribute failed", e);
        }
    }

    // ==================== WAIT OPERATIONS ====================

    /**
     * Wait for element to be visible
     *
     * @param locator - Element locator
     */
    public static void waitForElementVisible(By locator) {
        try {
            log.debug("Waiting for element visibility: {}", locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug("Element is visible: {}", locator);
        } catch (TimeoutException e) {
            log.error("Element visibility timeout: {}", locator);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }

    /**
     * Wait for element to be visible with custom timeout
     *
     * @param locator - Element locator
     * @param timeoutSeconds - Custom timeout in seconds
     */
    public static void waitForElementVisible(By locator, int timeoutSeconds) {
        try {
            log.debug("Waiting for element visibility with timeout: {}", timeoutSeconds);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug("Element is visible: {}", locator);
        } catch (TimeoutException e) {
            log.error("Element visibility timeout after {} seconds", timeoutSeconds);
            throw new RuntimeException("Element not visible within timeout", e);
        }
    }

    /**
     * Wait for element to be clickable
     *
     * @param locator - Element locator
     */
    public static void waitForElementClickable(By locator) {
        try {
            log.debug("Waiting for element to be clickable: {}", locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            log.debug("Element is clickable: {}", locator);
        } catch (TimeoutException e) {
            log.error("Element clickable timeout: {}", locator);
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }

    /**
     * Wait for element to be present in DOM
     *
     * @param locator - Element locator
     */
    public static void waitForElementPresence(By locator) {
        try {
            log.debug("Waiting for element presence: {}", locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            log.debug("Element is present: {}", locator);
        } catch (TimeoutException e) {
            log.error("Element presence timeout: {}", locator);
            throw new RuntimeException("Element not present: " + locator, e);
        }
    }

    /**
     * Wait for element to be invisible/hidden
     *
     * @param locator - Element locator
     */
    public static void waitForElementInvisible(By locator) {
        try {
            log.debug("Waiting for element to be invisible: {}", locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            log.info("Element is now invisible: {}", locator);
        } catch (TimeoutException e) {
            log.warn("Element still visible after timeout");
        }
    }

    /**
     * Wait for page loader/spinner to disappear
     * Common pattern: wait for loader to appear, then disappear
     *
     * @param loaderLocator - Loader element locator
     */
    public static void waitForPageLoader(By loaderLocator) {
        try {
            log.info("Waiting for page loader to disappear: {}", loaderLocator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));

            // Wait for loader to be invisible
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));

            log.info("Page loader disappeared");
        } catch (TimeoutException e) {
            log.warn("Page loader still visible after timeout");
        } catch (NoSuchElementException e) {
            log.info("Page loader element not found (may have never appeared)");
        }
    }

    /**
     * Wait for text to be present in element
     *
     * @param locator - Element locator
     * @param text - Expected text
     */
    public static void waitForTextPresent(By locator, String text) {
        try {
            log.debug("Waiting for text '{}' in element: {}", text, locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            log.info("Text found: {}", text);
        } catch (TimeoutException e) {
            log.error("Text not found within timeout: {}", text);
            throw new RuntimeException("Text not found: " + text, e);
        }
    }

    /**
     * Wait for attribute value to change
     *
     * @param locator - Element locator
     * @param attributeName - Attribute name
     * @param expectedValue - Expected attribute value
     */
    public static void waitForAttributeChange(By locator, String attributeName, String expectedValue) {
        try {
            log.debug("Waiting for attribute change: {} = {}", attributeName, expectedValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.attributeContains(locator, attributeName, expectedValue));
            log.info("Attribute changed to: {}", expectedValue);
        } catch (TimeoutException e) {
            log.error("Attribute change timeout");
            throw new RuntimeException("Attribute change timeout", e);
        }
    }

    /**
     * Wait for URL to contain specific text
     *
     * @param urlPart - URL part to match
     */
    public static void waitForUrlContains(String urlPart) {
        try {
            log.debug("Waiting for URL to contain: {}", urlPart);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            wait.until(ExpectedConditions.urlContains(urlPart));
            log.info("URL contains: {}", urlPart);
        } catch (TimeoutException e) {
            log.error("URL change timeout");
            throw new RuntimeException("URL change timeout", e);
        }
    }

    /**
     * Wait for all elements to load
     *
     * @param locators - List of locators to wait for
     */
    public static void waitForAllElementsVisible(List<By> locators) {
        try {
            log.debug("Waiting for {} elements to be visible", locators.size());
            for (By locator : locators) {
                waitForElementVisible(locator);
            }
            log.info("All elements are visible");
        } catch (Exception e) {
            log.error("Error waiting for elements: {}", e.getMessage());
            throw new RuntimeException("Elements wait failed", e);
        }
    }

    // ==================== SCROLL OPERATIONS ====================

    /**
     * Scroll element into view
     *
     * @param locator - Element locator
     */
    public static void scrollIntoView(By locator) {
        try {
            log.debug("Scrolling element into view: {}", locator);
            waitForElementPresence(locator);
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500); // Small delay after scroll
            log.info("Element scrolled into view");
        } catch (Exception e) {
            log.error("Scroll into view failed: {}", e.getMessage());
            throw new RuntimeException("Scroll into view failed", e);
        }
    }

    /**
     * Scroll to top of page
     */
    public static void scrollToTop() {
        try {
            log.debug("Scrolling to top");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
            log.info("Scrolled to top");
        } catch (Exception e) {
            log.error("Scroll to top failed: {}", e.getMessage());
        }
    }

    /**
     * Scroll to bottom of page
     */
    public static void scrollToBottom() {
        try {
            log.debug("Scrolling to bottom");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            log.info("Scrolled to bottom");
        } catch (Exception e) {
            log.error("Scroll to bottom failed: {}", e.getMessage());
        }
    }

    /**
     * Scroll by pixel amount
     *
     * @param xPixels - X axis pixels
     * @param yPixels - Y axis pixels
     */
    public static void scrollByPixels(int xPixels, int yPixels) {
        try {
            log.debug("Scrolling by pixels: x={}, y={}", xPixels, yPixels);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + xPixels + ", " + yPixels + ");");
            log.info("Scrolled by pixels");
        } catch (Exception e) {
            log.error("Scroll by pixels failed: {}", e.getMessage());
        }
    }

    // ==================== DROPDOWN OPERATIONS ====================

    /**
     * Select dropdown option by visible text
     *
     * @param locator - Dropdown locator
     * @param text - Visible text to select
     */
    public static void selectByText(By locator, String text) {
        try {
            log.debug("Selecting dropdown option by text: {}", text);
            waitForElementClickable(locator);
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            select.selectByVisibleText(text);
            log.info("Option selected: {}", text);
        } catch (Exception e) {
            log.error("Select by text failed: {}", e.getMessage());
            throw new RuntimeException("Select by text failed", e);
        }
    }

    /**
     * Select dropdown option by value
     *
     * @param locator - Dropdown locator
     * @param value - Option value
     */
    public static void selectByValue(By locator, String value) {
        try {
            log.debug("Selecting dropdown option by value: {}", value);
            waitForElementClickable(locator);
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            select.selectByValue(value);
            log.info("Option selected by value: {}", value);
        } catch (Exception e) {
            log.error("Select by value failed: {}", e.getMessage());
            throw new RuntimeException("Select by value failed", e);
        }
    }

    /**
     * Select dropdown option by index
     *
     * @param locator - Dropdown locator
     * @param index - Option index
     */
    public static void selectByIndex(By locator, int index) {
        try {
            log.debug("Selecting dropdown option by index: {}", index);
            waitForElementClickable(locator);
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            select.selectByIndex(index);
            log.info("Option selected by index: {}", index);
        } catch (Exception e) {
            log.error("Select by index failed: {}", e.getMessage());
            throw new RuntimeException("Select by index failed", e);
        }
    }

    /**
     * Get all dropdown options
     *
     * @param locator - Dropdown locator
     * @return - List of option texts
     */
    public static List<String> getAllDropdownOptions(By locator) {
        try {
            log.debug("Getting all dropdown options");
            waitForElementPresence(locator);
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            List<WebElement> options = select.getOptions();
            List<String> optionTexts = options.stream()
                    .map(WebElement::getText)
                    .toList();
            log.info("Retrieved {} dropdown options", optionTexts.size());
            return optionTexts;
        } catch (Exception e) {
            log.error("Get dropdown options failed: {}", e.getMessage());
            throw new RuntimeException("Get dropdown options failed", e);
        }
    }

    // ==================== CHECKBOX & RADIO OPERATIONS ====================

    /**
     * Check checkbox if not already checked
     *
     * @param locator - Checkbox locator
     */
    public static void checkCheckbox(By locator) {
        try {
            log.debug("Checking checkbox: {}", locator);
            waitForElementClickable(locator);
            WebElement checkbox = driver.findElement(locator);
            if (!checkbox.isSelected()) {
                checkbox.click();
                log.info("Checkbox checked");
            } else {
                log.info("Checkbox already checked");
            }
        } catch (Exception e) {
            log.error("Check checkbox failed: {}", e.getMessage());
            throw new RuntimeException("Check checkbox failed", e);
        }
    }

    /**
     * Uncheck checkbox if currently checked
     *
     * @param locator - Checkbox locator
     */
    public static void uncheckCheckbox(By locator) {
        try {
            log.debug("Unchecking checkbox: {}", locator);
            waitForElementClickable(locator);
            WebElement checkbox = driver.findElement(locator);
            if (checkbox.isSelected()) {
                checkbox.click();
                log.info("Checkbox unchecked");
            } else {
                log.info("Checkbox already unchecked");
            }
        } catch (Exception e) {
            log.error("Uncheck checkbox failed: {}", e.getMessage());
            throw new RuntimeException("Uncheck checkbox failed", e);
        }
    }

    /**
     * Check if element is selected
     *
     * @param locator - Element locator
     * @return - true if selected, false otherwise
     */
    public static boolean isElementSelected(By locator) {
        try {
            log.debug("Checking if element is selected: {}", locator);
            WebElement element = driver.findElement(locator);
            return element.isSelected();
        } catch (Exception e) {
            log.warn("Error checking element selection: {}", e.getMessage());
            return false;
        }
    }

    // ==================== VISIBILITY CHECKS ====================

    /**
     * Check if element is displayed
     *
     * @param locator - Element locator
     * @return - true if displayed, false otherwise
     */
    public static boolean isElementDisplayed(By locator) {
        try {
            log.debug("Checking if element is displayed: {}", locator);
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            log.debug("Element not found");
            return false;
        } catch (Exception e) {
            log.warn("Error checking element visibility: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if element is enabled
     *
     * @param locator - Element locator
     * @return - true if enabled, false otherwise
     */
    public static boolean isElementEnabled(By locator) {
        try {
            log.debug("Checking if element is enabled: {}", locator);
            WebElement element = driver.findElement(locator);
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            log.debug("Element not found");
            return false;
        } catch (Exception e) {
            log.warn("Error checking element enabled status: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if element exists in DOM
     *
     * @param locator - Element locator
     * @return - true if exists, false otherwise
     */
    public static boolean doesElementExist(By locator) {
        try {
            log.debug("Checking if element exists: {}", locator);
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            log.warn("Error checking element existence: {}", e.getMessage());
            return false;
        }
    }

    // ==================== WINDOW/TAB OPERATIONS ====================

    /**
     * Switch to new window/tab
     */
    public static void switchToNewWindow() {
        try {
            log.debug("Switching to new window/tab");
            Set<String> windows = driver.getWindowHandles();
            String newWindow = windows.stream().skip(windows.size() - 1).findFirst().orElse(null);
            if (newWindow != null) {
                driver.switchTo().window(newWindow);
                log.info("Switched to new window");
            }
        } catch (Exception e) {
            log.error("Switch to new window failed: {}", e.getMessage());
            throw new RuntimeException("Switch window failed", e);
        }
    }

    /**
     * Switch to parent window
     */
    public static void switchToParentWindow() {
        try {
            log.debug("Switching to parent window");
            Set<String> windows = driver.getWindowHandles();
            String parentWindow = windows.stream().findFirst().orElse(null);
            if (parentWindow != null) {
                driver.switchTo().window(parentWindow);
                log.info("Switched to parent window");
            }
        } catch (Exception e) {
            log.error("Switch to parent window failed: {}", e.getMessage());
            throw new RuntimeException("Switch to parent window failed", e);
        }
    }

    /**
     * Get current page title
     *
     * @return - Page title
     */
    public static String getPageTitle() {
        try {
            log.debug("Getting page title");
            return driver.getTitle();
        } catch (Exception e) {
            log.error("Get page title failed: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get current page URL
     *
     * @return - Page URL
     */
    public static String getCurrentUrl() {
        try {
            log.debug("Getting current URL");
            return driver.getCurrentUrl();
        } catch (Exception e) {
            log.error("Get URL failed: {}", e.getMessage());
            return "";
        }
    }

    // ==================== HOVER OPERATIONS ====================

    /**
     * Hover over element
     *
     * @param locator - Element locator
     */
    public static void hoverOverElement(By locator) {
        try {
            log.debug("Hovering over element: {}", locator);
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);
            new Actions(driver).moveToElement(element).perform();
            log.info("Hovered over element");
        } catch (Exception e) {
            log.error("Hover failed: {}", e.getMessage());
            throw new RuntimeException("Hover failed", e);
        }
    }

    /**
     * Hover and click (hover then click)
     *
     * @param locator - Element locator
     */
    public static void hoverAndClick(By locator) {
        try {
            log.debug("Hover and click on element: {}", locator);
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);
            new Actions(driver).moveToElement(element).click().perform();
            log.info("Hover and click completed");
        } catch (Exception e) {
            log.error("Hover and click failed: {}", e.getMessage());
            throw new RuntimeException("Hover and click failed", e);
        }
    }

    // ==================== DRAG & DROP OPERATIONS ====================

    /**
     * Drag and drop element
     *
     * @param sourceLocator - Source element locator
     * @param targetLocator - Target element locator
     */
    public static void dragAndDrop(By sourceLocator, By targetLocator) {
        try {
            log.debug("Dragging element from {} to {}", sourceLocator, targetLocator);
            waitForElementVisible(sourceLocator);
            waitForElementVisible(targetLocator);
            WebElement source = driver.findElement(sourceLocator);
            WebElement target = driver.findElement(targetLocator);
            new Actions(driver).dragAndDrop(source, target).perform();
            log.info("Drag and drop completed");
        } catch (Exception e) {
            log.error("Drag and drop failed: {}", e.getMessage());
            throw new RuntimeException("Drag and drop failed", e);
        }
    }

    /**
     * Drag element by offset
     *
     * @param sourceLocator - Source element locator
     * @param xOffset - X axis offset
     * @param yOffset - Y axis offset
     */
    public static void dragByOffset(By sourceLocator, int xOffset, int yOffset) {
        try {
            log.debug("Dragging element by offset: x={}, y={}", xOffset, yOffset);
            waitForElementVisible(sourceLocator);
            WebElement source = driver.findElement(sourceLocator);
            new Actions(driver).dragAndDropBy(source, xOffset, yOffset).perform();
            log.info("Drag by offset completed");
        } catch (Exception e) {
            log.error("Drag by offset failed: {}", e.getMessage());
            throw new RuntimeException("Drag by offset failed", e);
        }
    }

    // ==================== JAVASCRIPT OPERATIONS ====================

    /**
     * Execute JavaScript
     *
     * @param script - JavaScript code
     * @return - Execution result
     */
    public static Object executeJavaScript(String script) {
        try {
            log.debug("Executing JavaScript");
            return ((JavascriptExecutor) driver).executeScript(script);
        } catch (Exception e) {
            log.error("JavaScript execution failed: {}", e.getMessage());
            throw new RuntimeException("JavaScript execution failed", e);
        }
    }

    /**
     * Check if element is in viewport
     *
     * @param locator - Element locator
     * @return - true if in viewport, false otherwise
     */
    public static boolean isElementInViewport(By locator) {
        try {
            log.debug("Checking if element is in viewport: {}", locator);
            WebElement element = driver.findElement(locator);
            Object result = ((JavascriptExecutor) driver).executeScript(
                    "var elem = arguments[0];" +
                    "var rect = elem.getBoundingClientRect();" +
                    "return (rect.top >= 0 && rect.left >= 0 && " +
                    "rect.bottom <= window.innerHeight && rect.right <= window.innerWidth);",
                    element
            );
            return (boolean) result;
        } catch (Exception e) {
            log.warn("Error checking viewport: {}", e.getMessage());
            return false;
        }
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Count number of elements matching locator
     *
     * @param locator - Element locator
     * @return - Number of matching elements
     */
    public static int countElements(By locator) {
        try {
            log.debug("Counting elements: {}", locator);
            int count = driver.findElements(locator).size();
            log.info("Found {} matching elements", count);
            return count;
        } catch (Exception e) {
            log.error("Count elements failed: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get list of all text values from elements
     *
     * @param locator - Elements locator
     * @return - List of text values
     */
    public static List<String> getAllTextValues(By locator) {
        try {
            log.debug("Getting all text values: {}", locator);
            List<WebElement> elements = driver.findElements(locator);
            List<String> textValues = elements.stream()
                    .map(WebElement::getText)
                    .toList();
            log.info("Retrieved {} text values", textValues.size());
            return textValues;
        } catch (Exception e) {
            log.error("Get all text values failed: {}", e.getMessage());
            throw new RuntimeException("Get all text values failed", e);
        }
    }

    /**
     * Clear all text from input element
     *
     * @param locator - Element locator
     */
    public static void clearInputField(By locator) {
        try {
            log.debug("Clearing input field: {}", locator);
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);
            log.info("Input field cleared");
        } catch (Exception e) {
            log.error("Clear input field failed: {}", e.getMessage());
            throw new RuntimeException("Clear input field failed", e);
        }
    }

    /**
     * Pause execution (use sparingly, last resort)
     *
     * @param milliseconds - Pause duration in milliseconds
     */
    public static void pause(long milliseconds) {
        try {
            log.warn("Pausing for {} ms (use explicit waits instead)", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Pause interrupted", e);
        }
    }
}
