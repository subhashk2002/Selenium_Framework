package com.automation.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import com.automation.driver.DriverManager;
import com.automation.utils.WaitHelper;

@Slf4j
public class BasePage {
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.navigate().to(url);
    }

    protected void click(By locator) {
        try {
            log.debug("Clicking element: {}", locator);
            WebElement element = WaitHelper.waitForElementToBeClickable(locator);
            element.click();
        } catch (Exception e) {
            log.error("Failed to click element: {}", locator, e);
            throw new RuntimeException("Click failed for: " + locator, e);
        }
    }

    protected void click(WebElement element) {
        try {
            log.debug("Clicking element");
            element.click();
        } catch (Exception e) {
            log.error("Failed to click element", e);
            throw new RuntimeException("Click failed", e);
        }
    }

    protected void type(By locator, String text) {
        try {
            log.debug("Typing text in element: {}", locator);
            WebElement element = WaitHelper.waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            log.error("Failed to type text in element: {}", locator, e);
            throw new RuntimeException("Type failed for: " + locator, e);
        }
    }

    protected void type(WebElement element, String text) {
        try {
            log.debug("Typing text in element");
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            log.error("Failed to type text", e);
            throw new RuntimeException("Type failed", e);
        }
    }

    protected String getText(By locator) {
        try {
            log.debug("Getting text from element: {}", locator);
            WebElement element = WaitHelper.waitForElementToBeVisible(locator);
            return element.getText();
        } catch (Exception e) {
            log.error("Failed to get text from element: {}", locator, e);
            throw new RuntimeException("Get text failed for: " + locator, e);
        }
    }

    protected String getText(WebElement element) {
        try {
            log.debug("Getting text from element");
            return element.getText();
        } catch (Exception e) {
            log.error("Failed to get text", e);
            throw new RuntimeException("Get text failed", e);
        }
    }

    protected String getAttribute(By locator, String attribute) {
        try {
            log.debug("Getting attribute {} from element: {}", attribute, locator);
            WebElement element = WaitHelper.waitForElementToBeVisible(locator);
            return element.getAttribute(attribute);
        } catch (Exception e) {
            log.error("Failed to get attribute from element: {}", locator, e);
            throw new RuntimeException("Get attribute failed", e);
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            log.debug("Checking if element is displayed: {}", locator);
            return WaitHelper.waitForElementToBeVisible(locator).isDisplayed();
        } catch (Exception e) {
            log.debug("Element not displayed: {}", locator);
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            log.debug("Checking if element is present: {}", locator);
            WaitHelper.waitForElementPresence(locator);
            return true;
        } catch (Exception e) {
            log.debug("Element not present: {}", locator);
            return false;
        }
    }

    protected void hoverOver(By locator) {
        try {
            log.debug("Hovering over element: {}", locator);
            WebElement element = WaitHelper.waitForElementToBeVisible(locator);
            new Actions(driver).moveToElement(element).perform();
        } catch (Exception e) {
            log.error("Failed to hover over element: {}", locator, e);
            throw new RuntimeException("Hover failed for: " + locator, e);
        }
    }

    protected void dragAndDrop(By source, By target) {
        try {
            log.debug("Dragging element {} to {}", source, target);
            WebElement sourceElement = WaitHelper.waitForElementToBeVisible(source);
            WebElement targetElement = WaitHelper.waitForElementToBeVisible(target);
            new Actions(driver).dragAndDrop(sourceElement, targetElement).perform();
        } catch (Exception e) {
            log.error("Failed to drag and drop", e);
            throw new RuntimeException("Drag and drop failed", e);
        }
    }

    protected void scrollIntoView(By locator) {
        try {
            log.debug("Scrolling element into view: {}", locator);
            WebElement element = WaitHelper.waitForElementPresence(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            log.error("Failed to scroll element into view: {}", locator, e);
            throw new RuntimeException("Scroll failed for: " + locator, e);
        }
    }

    protected void scrollToTop() {
        log.debug("Scrolling to top of page");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        log.debug("Scrolling to bottom of page");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected Object executeScript(String script, Object... args) {
        try {
            log.debug("Executing JavaScript");
            return ((JavascriptExecutor) driver).executeScript(script, args);
        } catch (Exception e) {
            log.error("Failed to execute script", e);
            throw new RuntimeException("Script execution failed", e);
        }
    }

    protected void switchToFrame(By locator) {
        try {
            log.debug("Switching to frame: {}", locator);
            WebElement frameElement = WaitHelper.waitForElementPresence(locator);
            driver.switchTo().frame(frameElement);
        } catch (Exception e) {
            log.error("Failed to switch to frame: {}", locator, e);
            throw new RuntimeException("Frame switch failed", e);
        }
    }

    protected void switchToDefaultContent() {
        log.debug("Switching to default content");
        driver.switchTo().defaultContent();
    }

    protected void acceptAlert() {
        try {
            log.debug("Accepting alert");
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            log.warn("No alert present");
        }
    }

    protected void dismissAlert() {
        try {
            log.debug("Dismissing alert");
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            log.warn("No alert present");
        }
    }

    protected String getAlertText() {
        try {
            log.debug("Getting alert text");
            return driver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {
            log.warn("No alert present");
            return null;
        }
    }

    protected void typeInAlert(String text) {
        try {
            log.debug("Typing in alert");
            driver.switchTo().alert().sendKeys(text);
        } catch (NoAlertPresentException e) {
            log.warn("No alert present");
        }
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void waitForPageTitle(String title) {
        log.debug("Waiting for page title: {}", title);
        WaitHelper.waitForPageLoadComplete();
    }

    protected void refreshPage() {
        log.info("Refreshing page");
        driver.navigate().refresh();
    }

    protected void goBack() {
        log.info("Going back");
        driver.navigate().back();
    }

    protected void goForward() {
        log.info("Going forward");
        driver.navigate().forward();
    }
}
