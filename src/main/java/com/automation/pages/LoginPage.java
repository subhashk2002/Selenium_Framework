package com.automation.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class LoginPage extends BasePage {
    private static final By USERNAME_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginButton");
    private static final By ERROR_MESSAGE = By.className("error-message");
    private static final By REMEMBER_ME = By.id("rememberMe");

    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(USERNAME_FIELD, username);
    }

    @Step("Enter password: {password}")
    public void enterPassword(String password) {
        log.info("Entering password");
        type(PASSWORD_FIELD, password);
    }

    @Step("Click login button")
    public void clickLoginButton() {
        log.info("Clicking login button");
        click(LOGIN_BUTTON);
    }

    @Step("Check remember me checkbox")
    public void checkRememberMe() {
        log.info("Checking remember me checkbox");
        if (!isElementSelected(REMEMBER_ME)) {
            click(REMEMBER_ME);
        }
    }

    @Step("Get error message")
    public String getErrorMessage() {
        log.info("Getting error message");
        return getText(ERROR_MESSAGE);
    }

    @Step("Verify error message is displayed")
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(ERROR_MESSAGE);
    }

    @Step("Login with username: {username}")
    public void login(String username, String password) {
        log.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    private boolean isElementSelected(By locator) {
        try {
            return waitForElementPresence(locator).isSelected();
        } catch (Exception e) {
            log.debug("Element not found or not selected: {}", locator);
            return false;
        }
    }
}
