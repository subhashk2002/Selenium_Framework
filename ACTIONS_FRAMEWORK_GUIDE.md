# Actions Framework Guide - Production-Grade Reusable Methods

## Overview

The framework includes specialized **Action Classes** containing production-grade, reusable methods for common testing scenarios. These follow testing framework standards and implement best practices.

## Action Classes Available

### 1. **CommonActions** - Core UI Interactions
The main class with 50+ methods for standard UI operations.

### 2. **AlertActions** - JavaScript Alerts
Specialized handling for alerts, confirms, and prompts.

### 3. **FormActions** - Form Operations
Complete form filling, submission, and validation.

### 4. **TableActions** - Table Interactions
Table data retrieval, searching, and verification.

### 5. **FileUploadActions** - File Operations
File upload, download, and management.

---

## CommonActions - Click Operations

### Standard Click with Retry
```java
import com.automation.actions.CommonActions;

// Click with automatic retry (handles stale elements)
CommonActions.clickWithRetry(By.id("submitButton"));
```

**Features:**
- ✅ Automatic retry (3 attempts)
- ✅ Handles StaleElementReferenceException
- ✅ Handles ElementClickInterceptedException
- ✅ Comprehensive logging

### Click using JavaScript
```java
// For hard-to-click elements
CommonActions.clickUsingJavaScript(By.xpath("//button[text()='Submit']"));
```

### Double Click
```java
// Double click element
CommonActions.doubleClick(By.id("doubleClickElement"));
```

### Right Click
```java
// Right click for context menu
CommonActions.rightClick(By.id("contextMenuElement"));
```

### Click with Offset
```java
// Click at specific coordinates within element
CommonActions.clickWithOffset(By.id("element"), 50, 100);
```

---

## CommonActions - Text Operations

### Clear and Type with Verification
```java
// Clear existing text and type new text
CommonActions.clearAndType(By.id("inputField"), "Hello World");
```

**Features:**
- ✅ Clears existing text completely
- ✅ Types new text
- ✅ Verifies text was entered
- ✅ Handles input validation

### Type Slowly (for difficult fields)
```java
// Type slowly with 100ms delay between characters
CommonActions.typeSlowly(By.id("slowField"), "Password123", 100);
```

### Get Text
```java
// Get and trim text from element
String buttonText = CommonActions.getText(By.id("button"));
```

### Get Attribute
```java
// Get attribute value
String placeholder = CommonActions.getAttribute(By.id("input"), "placeholder");
```

---

## CommonActions - Wait Operations

### Wait for Element Visible
```java
// Wait using default timeout (from config)
CommonActions.waitForElementVisible(By.id("popup"));

// Wait with custom timeout
CommonActions.waitForElementVisible(By.id("popup"), 20); // 20 seconds
```

### Wait for Element Clickable
```java
// Wait for element to be both visible and enabled
CommonActions.waitForElementClickable(By.id("button"));
```

### Wait for Element Presence
```java
// Wait for element in DOM (doesn't need visibility)
CommonActions.waitForElementPresence(By.id("hiddenElement"));
```

### Wait for Element Invisible
```java
// Wait for element to disappear
CommonActions.waitForElementInvisible(By.id("loader"));
```

### Wait for Page Loader to Disappear
```java
// Common pattern: wait for loading spinner to disappear
CommonActions.waitForPageLoader(By.className("spinner"));
```

**Usage in Page Objects:**
```java
public class DashboardPage {
    private static final By PAGE_LOADER = By.id("pageLoader");
    private static final By CONTENT = By.id("mainContent");
    
    public void waitForPageLoad() {
        CommonActions.waitForPageLoader(PAGE_LOADER);
        CommonActions.waitForElementVisible(CONTENT);
    }
}
```

### Wait for Text Present
```java
// Wait for specific text in element
CommonActions.waitForTextPresent(By.id("message"), "Success");
```

### Wait for URL Change
```java
// Wait for URL to contain specific part
CommonActions.waitForUrlContains("/dashboard");
```

---

## CommonActions - Scroll Operations

### Scroll Element Into View
```java
// Scroll element into viewport
CommonActions.scrollIntoView(By.id("bottomElement"));
```

### Scroll to Top
```java
// Go to top of page
CommonActions.scrollToTop();
```

### Scroll to Bottom
```java
// Go to bottom of page
CommonActions.scrollToBottom();
```

### Scroll by Pixels
```java
// Scroll by specific amount
CommonActions.scrollByPixels(0, 500); // Scroll down 500px
```

---

## CommonActions - Dropdown Operations

### Select by Text
```java
// Select option by visible text
CommonActions.selectByText(By.id("dropdown"), "Option 1");
```

### Select by Value
```java
// Select option by value attribute
CommonActions.selectByValue(By.id("dropdown"), "opt1");
```

### Select by Index
```java
// Select option by position (0-based)
CommonActions.selectByIndex(By.id("dropdown"), 2);
```

### Get All Options
```java
// Get all dropdown options as list
List<String> options = CommonActions.getAllDropdownOptions(By.id("dropdown"));
// Result: ["Option 1", "Option 2", "Option 3"]
```

---

## CommonActions - Checkbox & Radio Operations

### Check Checkbox
```java
// Check if not already checked
CommonActions.checkCheckbox(By.id("termsCheckbox"));
```

### Uncheck Checkbox
```java
// Uncheck if currently checked
CommonActions.uncheckCheckbox(By.id("termsCheckbox"));
```

### Check Selection Status
```java
// Check if element is selected
boolean isSelected = CommonActions.isElementSelected(By.id("checkbox"));
```

---

## CommonActions - Visibility & Element Checks

### Check if Displayed
```java
// Non-blocking check
if (CommonActions.isElementDisplayed(By.id("element"))) {
    // Element is visible
}
```

### Check if Enabled
```java
// Check if element is enabled
if (CommonActions.isElementEnabled(By.id("button"))) {
    // Button is clickable
}
```

### Check if Exists
```java
// Check if element exists in DOM
if (CommonActions.doesElementExist(By.id("element"))) {
    // Element exists
}
```

---

## CommonActions - Hover Operations

### Hover Over Element
```java
// Hover to display submenu or tooltip
CommonActions.hoverOverElement(By.id("menu"));
```

### Hover and Click
```java
// Hover then click
CommonActions.hoverAndClick(By.id("hiddenMenuItem"));
```

---

## CommonActions - Drag & Drop

### Drag and Drop
```java
// Drag source to target
CommonActions.dragAndDrop(By.id("draggable"), By.id("droppable"));
```

### Drag by Offset
```java
// Drag element by pixel amount
CommonActions.dragByOffset(By.id("element"), 100, 200);
```

---

## AlertActions - JavaScript Alerts

### Wait for Alert
```java
import com.automation.actions.AlertActions;

// Wait for alert to appear
Alert alert = AlertActions.waitForAlert();
```

### Get Alert Text
```java
// Get alert message
String message = AlertActions.getAlertText();
```

### Accept Alert
```java
// Click OK on alert
AlertActions.acceptAlert();
```

### Dismiss Alert
```java
// Click Cancel on alert
AlertActions.dismissAlert();
```

### Type in Prompt
```java
// Type text in prompt dialog
AlertActions.typeInAlertPrompt("user@example.com");
```

### Handle Alert with Verification
```java
// Verify alert text and accept/dismiss
AlertActions.handleAlertWithVerification("Are you sure?", true); // true = accept
```

### Handle Multiple Alerts
```java
// Handle sequence of alerts
AlertActions.handleMultipleAlerts(3, true); // 3 alerts, accept all
```

---

## FormActions - Form Operations

### Fill Form with Map
```java
import com.automation.actions.FormActions;

Map<By, String> formData = new HashMap<>();
formData.put(By.id("firstName"), "John");
formData.put(By.id("lastName"), "Doe");
formData.put(By.id("email"), "john@example.com");

FormActions.fillForm(formData);
```

### Fill and Submit Form
```java
// Fill and submit in one action
Map<By, String> data = new HashMap<>();
data.put(By.id("username"), "testuser");
data.put(By.id("password"), "TestPass@123");

FormActions.fillAndSubmitForm(data, By.id("loginBtn"));
```

### Submit Form by Enter
```java
// Submit by pressing Enter key
FormActions.submitFormByEnter(By.id("passwordField"));
```

### Get Form Field Values
```java
// Get all form values
Map<String, By> fieldMap = new HashMap<>();
fieldMap.put("firstName", By.id("firstName"));
fieldMap.put("lastName", By.id("lastName"));

Map<String, String> values = FormActions.getFormFieldValues(fieldMap);
```

### Verify Form Error
```java
// Check error message
boolean hasError = FormActions.verifyFormErrorMessage(
    By.id("errorMessage"), 
    "This field is required"
);
```

### Check if Field Required
```java
// Verify field has required attribute
if (FormActions.isFieldRequired(By.id("email"))) {
    // Field is mandatory
}
```

### Tab Through Form Fields
```java
// Fill form using Tab key to navigate
Map<By, String> fields = new LinkedHashMap<>();
fields.put(By.id("firstName"), "John");
fields.put(By.id("lastName"), "Doe");

FormActions.fillFormWithTabNavigation(fields);
```

---

## TableActions - Table Operations

### Get Row Count
```java
import com.automation.actions.TableActions;

int rowCount = TableActions.getTableRowCount(
    By.id("dataTable"),
    ".//tbody/tr"
);
```

### Get Column Count
```java
int columnCount = TableActions.getTableColumnCount(
    By.id("dataTable"),
    ".//thead/tr/th"
);
```

### Get Cell Value
```java
// Get cell at row 2, column 3
String cellValue = TableActions.getTableCellValue(
    By.id("dataTable"),
    2,  // row index (1-based)
    3   // column index (1-based)
);
```

### Get Entire Row
```java
// Get all cells in row 2 as list
List<String> rowData = TableActions.getTableRowData(
    By.id("dataTable"),
    2
);
```

### Get Table as Maps
```java
// Get entire table as list of maps (header -> value)
List<Map<String, String>> tableData = TableActions.getTableDataAsMaps(
    By.id("dataTable"),
    ".//thead/tr",      // header row xpath
    ".//tbody/tr"       // data rows xpath
);

// Usage:
for (Map<String, String> row : tableData) {
    String name = row.get("Name");
    String email = row.get("Email");
}
```

### Search in Table
```java
// Find row containing text
int rowIndex = TableActions.findRowIndexByText(
    By.id("dataTable"),
    "John Doe",
    ".//tbody/tr"
);

if (rowIndex > 0) {
    // Row found at index
}
```

### Click Table Action
```java
// Click action button in specific row
TableActions.clickTableRowAction(
    By.id("dataTable"),
    2,          // row index
    ".//td/button[@class='edit']"  // action xpath
);
```

### Verify Row Exists
```java
// Check if row with specific text exists
if (TableActions.doesRowExist(By.id("dataTable"), "Jane Smith", ".//tbody/tr")) {
    // Row found
}
```

### Get Column Data
```java
// Get all values in column 2
List<String> columnData = TableActions.getTableColumnData(
    By.id("dataTable"),
    2,
    ".//tbody/tr"
);
```

### Sort and Verify
```java
// Click column header to sort
TableActions.sortTableByColumn(
    By.id("dataTable"),
    By.xpath(".//thead/tr/th[1]")  // click to sort
);

// Get column data and verify sort
List<String> columnData = TableActions.getTableColumnData(
    By.id("dataTable"),
    1,
    ".//tbody/tr"
);

boolean isSorted = TableActions.isTableSortedAscending(columnData);
```

---

## FileUploadActions - File Operations

### Upload File
```java
import com.automation.actions.FileUploadActions;

// Upload file to input[type=file]
FileUploadActions.uploadFileBySendKeys(
    By.id("fileInput"),
    "C:/path/to/document.pdf"
);
```

### Upload Multiple Files
```java
// Upload multiple files
FileUploadActions.uploadMultipleFiles(
    By.id("multiFileInput"),
    "C:/file1.pdf",
    "C:/file2.pdf",
    "C:/file3.pdf"
);
```

### Upload and Wait for Success
```java
// Upload file and wait for success element
FileUploadActions.uploadFileAndWaitForSuccess(
    By.id("fileInput"),
    "C:/document.pdf",
    By.id("uploadSuccessMessage")
);
```

### Create Temporary Test File
```java
// Create test file dynamically
String filePath = FileUploadActions.createTemporaryTextFile(
    "testfile.txt",
    "Test content here"
);

// Use file
FileUploadActions.uploadFileBySendKeys(By.id("fileInput"), filePath);

// Clean up
FileUploadActions.deleteFile(filePath);
```

### Create CSV for Testing
```java
// Create CSV test data
String csvContent = "Name,Email,Age\nJohn,john@example.com,30\nJane,jane@example.com,28";
String csvPath = FileUploadActions.createTemporaryCsvFile("data.csv", csvContent);

// Use it
FileUploadActions.uploadFileBySendKeys(By.id("csvInput"), csvPath);
```

### Wait for Download
```java
// Wait for file to be downloaded (timeout 10 seconds)
if (FileUploadActions.waitForFileDownload(
    "C:/Downloads",
    "report.pdf",
    10
)) {
    // File downloaded successfully
}
```

### Verify Download
```java
// Check if file was downloaded
if (FileUploadActions.isFileDownloaded("C:/Downloads", "report.pdf")) {
    // File exists
}
```

### Get File Extension
```java
String ext = FileUploadActions.getFileExtension("C:/document.pdf");
// Result: "pdf"
```

### Verify File Type
```java
if (FileUploadActions.hasCorrectExtension("C:/file.pdf", "pdf")) {
    // Correct file type
}
```

### Clear Download Directory
```java
// Clean up test downloads
FileUploadActions.clearDirectory("C:/Downloads");
```

---

## Complete Test Examples

### Example 1: Simple Login Test
```java
public class LoginPageTest extends BaseTest {
    
    @Test
    public void testLogin() {
        // Use CommonActions for all interactions
        CommonActions.waitForElementVisible(By.id("username"));
        CommonActions.clearAndType(By.id("username"), "testuser");
        CommonActions.clearAndType(By.id("password"), "TestPass@123");
        CommonActions.clickWithRetry(By.id("loginBtn"));
        
        // Wait for success
        CommonActions.waitForUrlContains("/dashboard");
    }
}
```

### Example 2: Complex Form Test
```java
public class RegistrationTest extends BaseTest {
    
    @Test
    public void testComplexFormRegistration() {
        // Prepare form data
        Map<By, String> formData = new HashMap<>();
        formData.put(By.id("firstName"), "John");
        formData.put(By.id("lastName"), "Doe");
        formData.put(By.id("email"), "john@example.com");
        formData.put(By.id("phone"), "+1234567890");
        
        // Fill and submit form
        FormActions.fillAndSubmitForm(formData, By.id("submitBtn"));
        
        // Verify success
        CommonActions.waitForPageLoader(By.id("loader"));
        CommonActions.waitForElementVisible(By.id("successMessage"));
    }
}
```

### Example 3: Table Data Extraction
```java
public class ReportTest extends BaseTest {
    
    @Test
    public void testExtractTableData() {
        // Get table data as maps
        List<Map<String, String>> data = TableActions.getTableDataAsMaps(
            By.id("resultsTable"),
            ".//thead/tr",
            ".//tbody/tr"
        );
        
        // Verify data
        for (Map<String, String> row : data) {
            String name = row.get("Name");
            String email = row.get("Email");
            System.out.println(name + " - " + email);
        }
    }
}
```

### Example 4: File Upload Test
```java
public class FileUploadTest extends BaseTest {
    
    @Test
    public void testFileUpload() {
        // Create test file
        String filePath = FileUploadActions.createTemporaryTextFile(
            "testdata.txt",
            "Test data for upload"
        );
        
        // Upload file
        FileUploadActions.uploadFileAndWaitForSuccess(
            By.id("fileUpload"),
            filePath,
            By.id("uploadSuccess")
        );
        
        // Verify upload message
        String message = CommonActions.getText(By.id("uploadSuccess"));
        Assert.assertTrue(message.contains("uploaded successfully"));
        
        // Clean up
        FileUploadActions.deleteFile(filePath);
    }
}
```

### Example 5: Alert Handling
```java
public class AlertTest extends BaseTest {
    
    @Test
    public void testAlertHandling() {
        // Click button that triggers alert
        CommonActions.clickWithRetry(By.id("deleteBtn"));
        
        // Get and verify alert
        String alertText = AlertActions.getAlertText();
        Assert.assertEquals(alertText, "Are you sure?");
        
        // Accept alert
        AlertActions.acceptAlert();
        
        // Verify action
        CommonActions.waitForElementVisible(By.id("successMsg"));
    }
}
```

---

## Best Practices for Using Action Classes

### 1. ✅ Always Use Type-Specific Methods
```java
// Good - use specialized methods
CommonActions.clickWithRetry(locator);
CommonActions.selectByText(dropdown, "Option");
FormActions.fillForm(data);

// Avoid - don't use generic click
element.click();
```

### 2. ✅ Wait Properly
```java
// Good - use explicit waits
CommonActions.waitForElementVisible(locator);
CommonActions.waitForPageLoader(loaderLocator);

// Avoid - never use Thread.sleep
Thread.sleep(5000);
```

### 3. ✅ Handle Errors Gracefully
```java
// Good - let actions throw meaningful exceptions
try {
    CommonActions.clickWithRetry(locator);
} catch (RuntimeException e) {
    log.error("Click failed", e);
    // Handle gracefully
}
```

### 4. ✅ Combine Actions Logically
```java
// Good - chain related actions
CommonActions.waitForElementVisible(locator);
CommonActions.scrollIntoView(locator);
CommonActions.clickWithRetry(locator);

// Group in page objects
loginPage.fillCredentials(username, password);
loginPage.submitLogin();
```

### 5. ✅ Use Maps for Forms
```java
// Good - organized form data
Map<By, String> formData = new HashMap<>();
formData.put(By.id("field1"), "value1");
FormActions.fillForm(formData);

// Avoid - scattered individual calls
CommonActions.clearAndType(By.id("field1"), "value1");
CommonActions.clearAndType(By.id("field2"), "value2");
```

---

## Summary

The Action Classes provide:
- ✅ **50+ Reusable Methods** - Production-grade, battle-tested
- ✅ **Best Practices** - Error handling, retry logic, logging
- ✅ **Framework Standards** - Clear naming, consistent behavior
- ✅ **Comprehensive Logging** - Debug difficult issues easily
- ✅ **Type Safety** - Compile-time checking
- ✅ **Easy Maintenance** - Centralized, single point of change

**Use these action classes instead of raw Selenium calls for faster, more reliable test development!**
