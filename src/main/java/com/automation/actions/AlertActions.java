package com.automation.actions;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;

import java.time.Duration;

/**
 * AlertActions - Specialized class for handling JavaScript alerts, confirms, and prompts
 */
@Slf4j
public class AlertActions {

    private static final WebDriver driver = DriverManager.getDriver();
    private static final ConfigManager config = ConfigManager.getInstance();
    private static final int EXPLICIT_WAIT = config.getExplicitWait();

    /**
     * Wait for alert to appear
     *
     * @return - Alert object
     */
    public static Alert waitForAlert() throws TimeoutException {
        log.debug("Waiting for alert to appear");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        log.info("Alert appeared");
        return alert;
    }

    /**
     * Get alert text
     *
     * @return - Alert text
     */
    public static String getAlertText() throws NoAlertPresentException {
        log.debug("Getting alert text");
        Alert alert = waitForAlert();
        String alertText = alert.getText();
        log.info("Alert text: {}", alertText);
        return alertText;
    }

    /**
     * Accept alert (click OK)
     */
    public static void acceptAlert() {
        try {
            log.debug("Accepting alert");
            Alert alert = waitForAlert();
            alert.accept();
            log.info("Alert accepted");
        } catch (NoAlertPresentException e) {
            log.warn("No alert to accept");
        }
    }

    /**
     * Dismiss alert (click Cancel)
     */
    public static void dismissAlert() {
        try {
            log.debug("Dismissing alert");
            Alert alert = waitForAlert();
            alert.dismiss();
            log.info("Alert dismissed");
        } catch (NoAlertPresentException e) {
            log.warn("No alert to dismiss");
        }
    }

    /**
     * Type text in prompt alert
     *
     * @param text - Text to type
     */
    public static void typeInAlertPrompt(String text) {
        try {
            log.debug("Typing in alert prompt: {}", text);
            Alert alert = waitForAlert();
            alert.sendKeys(text);
            log.info("Text typed in prompt");
        } catch (NoAlertPresentException e) {
            log.error("No alert prompt present");
            throw new RuntimeException("No alert prompt", e);
        }
    }

    /**
     * Handle alert with expected text and action
     *
     * @param expectedText - Expected alert text
     * @param accept - true to accept, false to dismiss
     */
    public static void handleAlertWithVerification(String expectedText, boolean accept) {
        try {
            log.debug("Handling alert with verification");
            Alert alert = waitForAlert();
            String alertText = alert.getText();

            if (!alertText.contains(expectedText)) {
                log.warn("Alert text does not contain expected text. Expected: {}, Got: {}", expectedText, alertText);
            }

            if (accept) {
                alert.accept();
                log.info("Alert accepted");
            } else {
                alert.dismiss();
                log.info("Alert dismissed");
            }
        } catch (NoAlertPresentException e) {
            log.error("No alert present");
            throw new RuntimeException("No alert present", e);
        }
    }

    /**
     * Check if alert is present
     *
     * @return - true if alert exists, false otherwise
     */
    public static boolean isAlertPresent() {
        try {
            log.debug("Checking if alert is present");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            log.debug("No alert present");
            return false;
        }
    }

    /**
     * Accept alert and get its text
     *
     * @return - Alert text
     */
    public static String acceptAlertAndGetText() {
        try {
            log.debug("Accepting alert and getting text");
            Alert alert = waitForAlert();
            String text = alert.getText();
            alert.accept();
            log.info("Alert accepted. Text: {}", text);
            return text;
        } catch (NoAlertPresentException e) {
            log.error("No alert present");
            throw new RuntimeException("No alert present", e);
        }
    }

    /**
     * Handle multiple sequential alerts
     *
     * @param count - Number of alerts expected
     * @param accept - true to accept all, false to dismiss all
     */
    public static void handleMultipleAlerts(int count, boolean accept) {
        try {
            log.debug("Handling {} alerts", count);
            for (int i = 0; i < count; i++) {
                Alert alert = waitForAlert();
                if (accept) {
                    alert.accept();
                    log.info("Alert {} accepted", i + 1);
                } else {
                    alert.dismiss();
                    log.info("Alert {} dismissed", i + 1);
                }
            }
        } catch (Exception e) {
            log.error("Error handling multiple alerts: {}", e.getMessage());
            throw new RuntimeException("Multiple alerts handling failed", e);
        }
    }
}
