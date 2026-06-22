package com.automation.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Slf4j
public class HomePage extends BasePage {
    private static final By HEADER_TITLE = By.className("page-title");
    private static final By USER_PROFILE = By.id("userProfile");
    private static final By LOGOUT_BUTTON = By.id("logoutBtn");
    private static final By SEARCH_BOX = By.id("searchBox");
    private static final By NAVIGATION_MENU = By.className("nav-menu");
    private static final By PRODUCTS_LINK = By.linkText("Products");
    private static final By DASHBOARD_LINK = By.linkText("Dashboard");

    @Step("Verify home page is loaded")
    public boolean isHomePageLoaded() {
        log.info("Verifying home page is loaded");
        return isElementDisplayed(HEADER_TITLE);
    }

    @Step("Get page title")
    public String getPageTitle() {
        log.info("Getting page title");
        return getText(HEADER_TITLE);
    }

    @Step("Search for: {searchTerm}")
    public void search(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        type(SEARCH_BOX, searchTerm);
    }

    @Step("Click on user profile")
    public void clickUserProfile() {
        log.info("Clicking user profile");
        click(USER_PROFILE);
    }

    @Step("Logout from application")
    public void logout() {
        log.info("Logging out from application");
        click(LOGOUT_BUTTON);
    }

    @Step("Navigate to products")
    public void navigateToProducts() {
        log.info("Navigating to products");
        click(PRODUCTS_LINK);
    }

    @Step("Navigate to dashboard")
    public void navigateToDashboard() {
        log.info("Navigating to dashboard");
        click(DASHBOARD_LINK);
    }

    @Step("Get navigation menu items")
    public List<WebElement> getNavigationItems() {
        log.info("Getting navigation menu items");
        return waitForElementsPresence(NAVIGATION_MENU);
    }

    @Step("Verify user profile is displayed")
    public boolean isUserProfileDisplayed() {
        return isElementDisplayed(USER_PROFILE);
    }
}
