package com.automation.actions;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.automation.driver.DriverManager;

import java.util.HashMap;
import java.util.Map;

/**
 * FormActions - Specialized class for handling form operations
 */
@Slf4j
public class FormActions {

    private static final WebDriver driver = DriverManager.getDriver();

    /**
     * Fill form with map of field locators and values
     *
     * @param formFieldsMap - Map of locators to values
     */
    public static void fillForm(Map<By, String> formFieldsMap) {
        try {
            log.info("Filling form with {} fields", formFieldsMap.size());
            for (Map.Entry<By, String> entry : formFieldsMap.entrySet()) {
                By locator = entry.getKey();
                String value = entry.getValue();
                CommonActions.clearAndType(locator, value);
            }
            log.info("Form filled successfully");
        } catch (Exception e) {
            log.error("Fill form failed: {}", e.getMessage());
            throw new RuntimeException("Fill form failed", e);
        }
    }

    /**
     * Fill form field by field name (for easier usage)
     *
     * @param fieldsData - Map of field names to values
     * @param fieldLocatorPrefix - Prefix to construct locators (e.g., "id=field_")
     */
    public static void fillFormByFieldNames(Map<String, String> fieldsData, String fieldLocatorPrefix) {
        try {
            log.info("Filling form with field names");
            for (Map.Entry<String, String> entry : fieldsData.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue();
                By locator = By.id(fieldLocatorPrefix + fieldName);
                CommonActions.clearAndType(locator, value);
            }
            log.info("Form filled by field names");
        } catch (Exception e) {
            log.error("Fill form by names failed: {}", e.getMessage());
            throw new RuntimeException("Fill form by names failed", e);
        }
    }

    /**
     * Submit form by pressing Enter key
     *
     * @param fieldLocator - Any field in the form
     */
    public static void submitFormByEnter(By fieldLocator) {
        try {
            log.debug("Submitting form by Enter key");
            CommonActions.waitForElementClickable(fieldLocator);
            WebElement element = driver.findElement(fieldLocator);
            element.sendKeys(Keys.RETURN);
            log.info("Form submitted by Enter key");
        } catch (Exception e) {
            log.error("Submit form failed: {}", e.getMessage());
            throw new RuntimeException("Submit form failed", e);
        }
    }

    /**
     * Submit form by clicking submit button
     *
     * @param submitButtonLocator - Submit button locator
     */
    public static void submitFormByButton(By submitButtonLocator) {
        try {
            log.debug("Submitting form by button click");
            CommonActions.clickWithRetry(submitButtonLocator);
            log.info("Form submitted by button");
        } catch (Exception e) {
            log.error("Submit form failed: {}", e.getMessage());
            throw new RuntimeException("Submit form failed", e);
        }
    }

    /**
     * Reset form fields to empty
     *
     * @param fieldLocators - List of field locators
     */
    public static void clearFormFields(By... fieldLocators) {
        try {
            log.debug("Clearing {} form fields", fieldLocators.length);
            for (By locator : fieldLocators) {
                CommonActions.clearInputField(locator);
            }
            log.info("Form fields cleared");
        } catch (Exception e) {
            log.error("Clear form failed: {}", e.getMessage());
            throw new RuntimeException("Clear form failed", e);
        }
    }

    /**
     * Get all form field values
     *
     * @param fieldLocators - Map of field names to locators
     * @return - Map of field names to values
     */
    public static Map<String, String> getFormFieldValues(Map<String, By> fieldLocators) {
        try {
            log.debug("Getting form field values");
            Map<String, String> values = new HashMap<>();
            for (Map.Entry<String, By> entry : fieldLocators.entrySet()) {
                String fieldName = entry.getKey();
                By locator = entry.getValue();
                String value = CommonActions.getAttribute(locator, "value");
                values.put(fieldName, value);
            }
            log.info("Retrieved {} field values", values.size());
            return values;
        } catch (Exception e) {
            log.error("Get form values failed: {}", e.getMessage());
            throw new RuntimeException("Get form values failed", e);
        }
    }

    /**
     * Get specific form field value
     *
     * @param fieldLocator - Field locator
     * @return - Field value
     */
    public static String getFormFieldValue(By fieldLocator) {
        try {
            log.debug("Getting form field value");
            return CommonActions.getAttribute(fieldLocator, "value");
        } catch (Exception e) {
            log.error("Get field value failed: {}", e.getMessage());
            throw new RuntimeException("Get field value failed", e);
        }
    }

    /**
     * Verify form field is required/mandatory
     *
     * @param fieldLocator - Field locator
     * @return - true if required attribute exists
     */
    public static boolean isFieldRequired(By fieldLocator) {
        try {
            log.debug("Checking if field is required");
            String required = CommonActions.getAttribute(fieldLocator, "required");
            return required != null && !required.isEmpty();
        } catch (Exception e) {
            log.warn("Error checking field requirement: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get form validation error message
     *
     * @param errorMessageLocator - Error message locator
     * @return - Error message text
     */
    public static String getFormErrorMessage(By errorMessageLocator) {
        try {
            log.debug("Getting form error message");
            return CommonActions.getText(errorMessageLocator);
        } catch (Exception e) {
            log.error("Get error message failed: {}", e.getMessage());
            throw new RuntimeException("Get error message failed", e);
        }
    }

    /**
     * Verify form error message
     *
     * @param errorMessageLocator - Error message locator
     * @param expectedMessage - Expected error message
     * @return - true if message matches
     */
    public static boolean verifyFormErrorMessage(By errorMessageLocator, String expectedMessage) {
        try {
            log.debug("Verifying form error message");
            String actualMessage = getFormErrorMessage(errorMessageLocator);
            boolean matches = actualMessage.contains(expectedMessage);
            log.info("Error message verification: {}", matches);
            return matches;
        } catch (Exception e) {
            log.error("Error message verification failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Fill and submit form in one action
     *
     * @param formData - Map of field locators to values
     * @param submitButtonLocator - Submit button locator
     */
    public static void fillAndSubmitForm(Map<By, String> formData, By submitButtonLocator) {
        try {
            log.info("Filling and submitting form");
            fillForm(formData);
            submitFormByButton(submitButtonLocator);
            log.info("Form filled and submitted");
        } catch (Exception e) {
            log.error("Fill and submit form failed: {}", e.getMessage());
            throw new RuntimeException("Fill and submit form failed", e);
        }
    }

    /**
     * Check if form field is enabled
     *
     * @param fieldLocator - Field locator
     * @return - true if enabled, false otherwise
     */
    public static boolean isFormFieldEnabled(By fieldLocator) {
        try {
            log.debug("Checking if form field is enabled");
            return CommonActions.isElementEnabled(fieldLocator);
        } catch (Exception e) {
            log.warn("Error checking field enabled status: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if form field is visible
     *
     * @param fieldLocator - Field locator
     * @return - true if visible, false otherwise
     */
    public static boolean isFormFieldVisible(By fieldLocator) {
        try {
            log.debug("Checking if form field is visible");
            return CommonActions.isElementDisplayed(fieldLocator);
        } catch (Exception e) {
            log.warn("Error checking field visibility: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Tab to next form field
     *
     * @param currentFieldLocator - Current field locator
     */
    public static void tabToNextField(By currentFieldLocator) {
        try {
            log.debug("Tabbing to next form field");
            CommonActions.waitForElementClickable(currentFieldLocator);
            WebElement element = driver.findElement(currentFieldLocator);
            element.sendKeys(Keys.TAB);
            log.info("Tabbed to next field");
        } catch (Exception e) {
            log.error("Tab to next field failed: {}", e.getMessage());
            throw new RuntimeException("Tab failed", e);
        }
    }

    /**
     * Enter data using Tab navigation (useful for form testing)
     *
     * @param fieldsData - Ordered map of field locators and values
     */
    public static void fillFormWithTabNavigation(Map<By, String> fieldsData) {
        try {
            log.info("Filling form with Tab navigation");
            int count = 0;
            for (Map.Entry<By, String> entry : fieldsData.entrySet()) {
                By locator = entry.getKey();
                String value = entry.getValue();
                CommonActions.clearAndType(locator, value);

                if (count < fieldsData.size() - 1) {
                    tabToNextField(locator);
                }
                count++;
            }
            log.info("Form filled with Tab navigation");
        } catch (Exception e) {
            log.error("Fill form with Tab failed: {}", e.getMessage());
            throw new RuntimeException("Fill form with Tab failed", e);
        }
    }
}
