# Actions Framework - Class Organization & Integration

## 📁 Complete Organization Structure

### Action Classes Location

```
src/main/java/com/automation/actions/
├── CommonActions.java          (50+ methods - Core UI interactions)
├── AlertActions.java           (10+ methods - Alert handling)
├── FormActions.java            (15+ methods - Form operations)
├── TableActions.java           (15+ methods - Table operations)
└── FileUploadActions.java      (15+ methods - File operations)
```

---

## 📋 Method Organization by Class

### **CommonActions.java** - Location & Methods

**File Path:** `src/main/java/com/automation/actions/CommonActions.java`

**Methods Organized by Category:**

#### Click Operations
```
clickWithRetry(By locator)
clickUsingJavaScript(By locator)
doubleClick(By locator)
rightClick(By locator)
clickWithOffset(By locator, int xOffset, int yOffset)
```

#### Text Operations
```
clearAndType(By locator, String text)
typeSlowly(By locator, String text, long delayMs)
getText(By locator)
getAttribute(By locator, String attributeName)
```

#### Wait Operations
```
waitForElementVisible(By locator)
waitForElementVisible(By locator, int timeoutSeconds)
waitForElementClickable(By locator)
waitForElementPresence(By locator)
waitForElementInvisible(By locator)
waitForPageLoader(By loaderLocator)
waitForTextPresent(By locator, String text)
waitForAttributeChange(By locator, String attributeName, String expectedValue)
waitForUrlContains(String urlPart)
waitForAllElementsVisible(List<By> locators)
```

#### Scroll Operations
```
scrollIntoView(By locator)
scrollToTop()
scrollToBottom()
scrollByPixels(int xPixels, int yPixels)
```

#### Dropdown Operations
```
selectByText(By locator, String text)
selectByValue(By locator, String value)
selectByIndex(By locator, int index)
getAllDropdownOptions(By locator)
```

#### Checkbox & Radio Operations
```
checkCheckbox(By locator)
uncheckCheckbox(By locator)
isElementSelected(By locator)
```

#### Visibility Checks
```
isElementDisplayed(By locator)
isElementEnabled(By locator)
doesElementExist(By locator)
```

#### Window/Tab Operations
```
switchToNewWindow()
switchToParentWindow()
getPageTitle()
getCurrentUrl()
```

#### Hover Operations
```
hoverOverElement(By locator)
hoverAndClick(By locator)
```

#### Drag & Drop Operations
```
dragAndDrop(By sourceLocator, By targetLocator)
dragByOffset(By sourceLocator, int xOffset, int yOffset)
```

#### JavaScript Operations
```
executeJavaScript(String script)
isElementInViewport(By locator)
```

#### Utility Methods
```
countElements(By locator)
getAllTextValues(By locator)
clearInputField(By locator)
pause(long milliseconds)
```

---

### **AlertActions.java** - Location & Methods

**File Path:** `src/main/java/com/automation/actions/AlertActions.java`

**All Methods:**
```
waitForAlert()
getAlertText()
acceptAlert()
dismissAlert()
typeInAlertPrompt(String text)
handleAlertWithVerification(String expectedText, boolean accept)
isAlertPresent()
acceptAlertAndGetText()
handleMultipleAlerts(int count, boolean accept)
```

---

### **FormActions.java** - Location & Methods

**File Path:** `src/main/java/com/automation/actions/FormActions.java`

**Methods Organized by Category:**

#### Fill & Submit
```
fillForm(Map<By, String> formFieldsMap)
fillAndSubmitForm(Map<By, String> formData, By submitButtonLocator)
submitFormByEnter(By fieldLocator)
submitFormByButton(By submitButtonLocator)
fillFormByFieldNames(Map<String, String> fieldsData, String fieldLocatorPrefix)
fillFormWithTabNavigation(Map<By, String> fieldsData)
```

#### Data Operations
```
getFormFieldValues(Map<String, By> fieldLocators)
getFormFieldValue(By fieldLocator)
clearFormFields(By... fieldLocators)
```

#### Verification
```
isFieldRequired(By fieldLocator)
getFormErrorMessage(By errorMessageLocator)
verifyFormErrorMessage(By errorMessageLocator, String expectedMessage)
isFormFieldEnabled(By fieldLocator)
isFormFieldVisible(By fieldLocator)
```

#### Navigation
```
tabToNextField(By currentFieldLocator)
```

---

### **TableActions.java** - Location & Methods

**File Path:** `src/main/java/com/automation/actions/TableActions.java`

**Methods Organized by Category:**

#### Data Extraction
```
getTableRowCount(By tableLocator, String rowXpath)
getTableColumnCount(By tableLocator, String cellXpath)
getTableCellValue(By tableLocator, int rowIndex, int columnIndex)
getTableRowData(By tableLocator, int rowIndex)
getTableColumnData(By tableLocator, int columnIndex, String rowXpath)
getTableDataAsMaps(By tableLocator, String headerRowXpath, String dataRowXpath)
getTableHeaders(By tableLocator, String headerXpath)
```

#### Search & Navigation
```
findRowIndexByText(By tableLocator, String searchText, String rowXpath)
doesRowExist(By tableLocator, String searchText, String rowXpath)
clickTableRowAction(By tableLocator, int rowIndex, String actionXpath)
```

#### Sorting
```
sortTableByColumn(By tableLocator, By columnHeaderLocator)
isTableSortedAscending(List<String> columnData)
```

---

### **FileUploadActions.java** - Location & Methods

**File Path:** `src/main/java/com/automation/actions/FileUploadActions.java`

**Methods Organized by Category:**

#### Upload
```
uploadFileBySendKeys(By fileInputLocator, String filePath)
uploadMultipleFiles(By fileInputLocator, String... filePaths)
uploadFileAndWaitForSuccess(By fileInputLocator, String filePath, By successElementLocator)
```

#### File Management
```
verifyFileExists(String filePath)
getFileSize(String filePath)
getFileExtension(String filePath)
hasCorrectExtension(String filePath, String expectedExtension)
deleteFile(String filePath)
```

#### Test File Creation
```
createTemporaryTestFile(String fileName, String content)
createTemporaryCsvFile(String fileName, String csvContent)
createTemporaryTextFile(String fileName, String content)
```

#### Download Handling
```
waitForFileDownload(String downloadDir, String fileName, int timeoutSeconds)
isFileDownloaded(String downloadDir, String fileName)
getFilesInDirectory(String directoryPath)
clearDirectory(String directoryPath)
```

---

## 🔗 How to Use in Page Objects

### Example: LoginPage with CommonActions

**File:** `src/main/java/com/automation/pages/LoginPage.java`

```java
package com.automation.pages;

import org.openqa.selenium.By;
import com.automation.actions.CommonActions;
import com.automation.actions.AlertActions;

public class LoginPage extends BasePage {
    
    // Locators
    private static final By USERNAME_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginBtn");
    private static final By ERROR_MESSAGE = By.className("error");
    private static final By PAGE_LOADER = By.id("loader");
    
    // Methods using CommonActions
    public void enterUsername(String username) {
        CommonActions.clearAndType(USERNAME_FIELD, username);
    }
    
    public void enterPassword(String password) {
        CommonActions.clearAndType(PASSWORD_FIELD, password);
    }
    
    public void clickLogin() {
        CommonActions.clickWithRetry(LOGIN_BUTTON);
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        CommonActions.waitForPageLoader(PAGE_LOADER);
    }
    
    public String getErrorMessage() {
        return CommonActions.getText(ERROR_MESSAGE);
    }
    
    public boolean isErrorDisplayed() {
        return CommonActions.isElementDisplayed(ERROR_MESSAGE);
    }
}
```

---

### Example: RegistrationForm with FormActions

**File:** `src/main/java/com/automation/pages/RegistrationPage.java`

```java
package com.automation.pages;

import org.openqa.selenium.By;
import com.automation.actions.CommonActions;
import com.automation.actions.FormActions;
import java.util.HashMap;
import java.util.Map;

public class RegistrationPage extends BasePage {
    
    // Locators
    private static final By FIRST_NAME = By.id("firstName");
    private static final By LAST_NAME = By.id("lastName");
    private static final By EMAIL = By.id("email");
    private static final By PASSWORD = By.id("password");
    private static final By COUNTRY_DROPDOWN = By.id("country");
    private static final By TERMS_CHECKBOX = By.id("terms");
    private static final By SUBMIT_BUTTON = By.id("submitBtn");
    private static final By SUCCESS_MESSAGE = By.id("successMsg");
    private static final By ERROR_MESSAGE = By.className("error-text");
    
    // Register with all fields
    public void registerUser(String firstName, String lastName, String email, 
                           String password, String country) {
        Map<By, String> formData = new HashMap<>();
        formData.put(FIRST_NAME, firstName);
        formData.put(LAST_NAME, lastName);
        formData.put(EMAIL, email);
        formData.put(PASSWORD, password);
        
        // Fill form using FormActions
        FormActions.fillForm(formData);
        
        // Select country using CommonActions
        CommonActions.selectByText(COUNTRY_DROPDOWN, country);
        
        // Check terms using CommonActions
        CommonActions.checkCheckbox(TERMS_CHECKBOX);
        
        // Submit form
        FormActions.submitFormByButton(SUBMIT_BUTTON);
    }
    
    // Verify registration success
    public boolean isRegistrationSuccessful() {
        CommonActions.waitForElementVisible(SUCCESS_MESSAGE);
        return CommonActions.isElementDisplayed(SUCCESS_MESSAGE);
    }
    
    // Get error message
    public String getErrorMessage() {
        return FormActions.getFormErrorMessage(ERROR_MESSAGE);
    }
}
```

---

### Example: DataTable with TableActions

**File:** `src/main/java/com/automation/pages/DataTablePage.java`

```java
package com.automation.pages;

import org.openqa.selenium.By;
import com.automation.actions.CommonActions;
import com.automation.actions.TableActions;
import java.util.List;
import java.util.Map;

public class DataTablePage extends BasePage {
    
    // Locators
    private static final By TABLE = By.id("dataTable");
    private static final String HEADER_XPATH = ".//thead/tr";
    private static final String ROW_XPATH = ".//tbody/tr";
    
    // Get all table data
    public List<Map<String, String>> getTableData() {
        return TableActions.getTableDataAsMaps(TABLE, HEADER_XPATH, ROW_XPATH);
    }
    
    // Get row count
    public int getRowCount() {
        return TableActions.getTableRowCount(TABLE, ROW_XPATH);
    }
    
    // Find user row
    public int findUserRow(String username) {
        return TableActions.findRowIndexByText(TABLE, username, ROW_XPATH);
    }
    
    // Click edit button in row
    public void editUser(int rowIndex) {
        TableActions.clickTableRowAction(
            TABLE, 
            rowIndex, 
            ".//td/a[@class='edit-btn']"
        );
    }
    
    // Verify row exists
    public boolean doesUserExist(String username) {
        return TableActions.doesRowExist(TABLE, username, ROW_XPATH);
    }
}
```

---

### Example: FileUpload with FileUploadActions

**File:** `src/main/java/com/automation/pages/FileUploadPage.java`

```java
package com.automation.pages;

import org.openqa.selenium.By;
import com.automation.actions.CommonActions;
import com.automation.actions.FileUploadActions;

public class FileUploadPage extends BasePage {
    
    // Locators
    private static final By FILE_INPUT = By.id("fileInput");
    private static final By UPLOAD_BUTTON = By.id("uploadBtn");
    private static final By SUCCESS_MESSAGE = By.id("uploadSuccess");
    private static final By ERROR_MESSAGE = By.id("uploadError");
    
    // Upload file
    public void uploadFile(String filePath) {
        FileUploadActions.uploadFileBySendKeys(FILE_INPUT, filePath);
        CommonActions.clickWithRetry(UPLOAD_BUTTON);
    }
    
    // Upload and wait for success
    public void uploadFileAndWait(String filePath) {
        FileUploadActions.uploadFileAndWaitForSuccess(
            FILE_INPUT,
            filePath,
            SUCCESS_MESSAGE
        );
    }
    
    // Upload multiple files
    public void uploadMultipleFiles(String... filePaths) {
        FileUploadActions.uploadMultipleFiles(FILE_INPUT, filePaths);
        CommonActions.clickWithRetry(UPLOAD_BUTTON);
    }
    
    // Check if upload successful
    public boolean isUploadSuccessful() {
        return CommonActions.isElementDisplayed(SUCCESS_MESSAGE);
    }
    
    // Get error message
    public String getUploadError() {
        return CommonActions.getText(ERROR_MESSAGE);
    }
}
```

---

### Example: AlertHandling with AlertActions

**File:** `src/main/java/com/automation/pages/DeleteConfirmationPage.java`

```java
package com.automation.pages;

import org.openqa.selenium.By;
import com.automation.actions.CommonActions;
import com.automation.actions.AlertActions;

public class DeleteConfirmationPage extends BasePage {
    
    // Locators
    private static final By DELETE_BUTTON = By.id("deleteBtn");
    private static final By SUCCESS_MESSAGE = By.id("deleteSuccess");
    
    // Delete with confirmation
    public void deleteWithConfirmation() {
        CommonActions.clickWithRetry(DELETE_BUTTON);
        
        // Handle confirmation alert
        String alertText = AlertActions.getAlertText();
        if (alertText.contains("Are you sure")) {
            AlertActions.acceptAlert();
        }
    }
    
    // Delete and verify success
    public boolean deleteAndVerify() {
        deleteWithConfirmation();
        CommonActions.waitForElementVisible(SUCCESS_MESSAGE);
        return CommonActions.isElementDisplayed(SUCCESS_MESSAGE);
    }
    
    // Handle multiple confirmations
    public void deleteMultipleItems(int count) {
        CommonActions.clickWithRetry(DELETE_BUTTON);
        AlertActions.handleMultipleAlerts(count, true);
    }
}
```

---

## 🧪 How to Use in Tests

### Example Test Using Action Classes

**File:** `src/test/java/com/automation/tests/ui/LoginTest.java`

```java
package com.automation.tests.ui;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.actions.CommonActions;
import com.automation.tests.BaseTest;

public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    
    @Test
    public void testSuccessfulLogin() {
        loginPage = new LoginPage();
        
        // Using CommonActions through page object
        loginPage.enterUsername("testuser@example.com");
        loginPage.enterPassword("TestPassword@123");
        loginPage.clickLogin();
        
        // Wait for navigation
        CommonActions.waitForUrlContains("/dashboard");
        
        // Verify success
        String url = CommonActions.getCurrentUrl();
        Assert.assertTrue(url.contains("/dashboard"));
    }
    
    @Test
    public void testLoginWithInvalidCredentials() {
        loginPage = new LoginPage();
        
        loginPage.login("invalid@example.com", "WrongPassword");
        
        // Verify error message
        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertEquals(
            loginPage.getErrorMessage(),
            "Invalid credentials"
        );
    }
}
```

---

## 📦 Action Classes Import Pattern

### Single Import
```java
import com.automation.actions.CommonActions;
```

### Multiple Imports
```java
import com.automation.actions.CommonActions;
import com.automation.actions.FormActions;
import com.automation.actions.AlertActions;
import com.automation.actions.TableActions;
import com.automation.actions.FileUploadActions;
```

### Static Import (Alternative)
```java
import static com.automation.actions.CommonActions.*;
import static com.automation.actions.FormActions.*;

// Usage:
clickWithRetry(locator);  // No prefix needed
fillForm(data);           // No prefix needed
```

---

## ✅ Best Practices for Organization

### ✅ DO:
```java
// Use action classes in page objects
public class MyPage extends BasePage {
    public void myMethod() {
        CommonActions.clickWithRetry(locator);
        FormActions.fillForm(data);
    }
}

// Use page objects in tests
public class MyTest extends BaseTest {
    MyPage page = new MyPage();
    page.myMethod();
}
```

### ❌ DON'T:
```java
// Don't use raw Selenium in tests
WebElement element = driver.findElement(locator);
element.click();

// Don't duplicate action logic
public void myClick(By locator) {
    CommonActions.clickWithRetry(locator);
}
// Already in CommonActions!
```

---

## 📁 Complete File Structure Summary

```
src/main/java/com/automation/
├── actions/
│   ├── CommonActions.java         ← Core UI methods (50+)
│   ├── AlertActions.java          ← Alert methods (10+)
│   ├── FormActions.java           ← Form methods (15+)
│   ├── TableActions.java          ← Table methods (15+)
│   └── FileUploadActions.java     ← File methods (15+)
│
├── pages/
│   ├── BasePage.java              ← Base page class
│   ├── LoginPage.java             ← Uses CommonActions
│   ├── RegistrationPage.java      ← Uses FormActions
│   ├── DataTablePage.java         ← Uses TableActions
│   ├── FileUploadPage.java        ← Uses FileUploadActions
│   └── DeletePage.java            ← Uses AlertActions
│
└── tests/
    ├── ui/
    │   ├── LoginTest.java         ← Uses LoginPage + CommonActions
    │   ├── RegistrationTest.java  ← Uses RegistrationPage + FormActions
    │   └── DataTableTest.java     ← Uses DataTablePage + TableActions
    │
    └── api/
        └── (API tests)
```

---

## 🎯 Summary

**Each action class stays in its file:**
- ✅ `CommonActions.java` - 50+ UI methods
- ✅ `AlertActions.java` - 10+ Alert methods
- ✅ `FormActions.java` - 15+ Form methods
- ✅ `TableActions.java` - 15+ Table methods
- ✅ `FileUploadActions.java` - 15+ File methods

**Use in page objects:**
- Import the action class
- Call methods in page object methods
- Keep page objects focused and readable

**Use in tests:**
- Create page object instances
- Call page object methods
- Page objects delegate to action classes

**Result:**
- ✅ Clean separation of concerns
- ✅ Reusable action methods
- ✅ Maintainable code
- ✅ Framework standards
