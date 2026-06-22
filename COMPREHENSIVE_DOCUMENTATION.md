# Hybrid Selenium Java Framework - Comprehensive Documentation

## Table of Contents
1. [Framework Overview](#framework-overview)
2. [Architecture & Design](#architecture--design)
3. [Installation & Setup](#installation--setup)
4. [Framework Components](#framework-components)
5. [Writing Tests](#writing-tests)
6. [Running Tests](#running-tests)
7. [Report Generation](#report-generation)
8. [Best Practices](#best-practices)
9. [Troubleshooting](#troubleshooting)
10. [FAQ](#faq)

---

## Framework Overview

### What is This Framework?
This is a **production-grade, scalable hybrid automation framework** combining:
- **UI Automation** - Selenium WebDriver 4.15
- **API Testing** - RestAssured 5.3
- **Test Execution** - TestNG 7.8
- **Reporting** - Allure 2.21
- **AI Capabilities** - Ollama integration
- **Data Management** - JSON, Excel, CSV support

### Key Features
✅ **Multi-Environment Support** (LOCAL, DEV, STAGING, PRODUCTION)
✅ **Multiple Browser Support** (Chrome, Firefox, Edge, Safari)
✅ **Parallel Test Execution** (Thread-safe WebDriver)
✅ **Data-Driven Testing** (TestNG DataProviders)
✅ **Page Object Model** (Clear separation of concerns)
✅ **AI-Powered Testing** (Ollama integration)
✅ **Comprehensive Reporting** (Allure with screenshots)
✅ **Automatic Retry** (Flaky test handling)
✅ **Professional Logging** (Log4j2)
✅ **CI/CD Ready** (Jenkins, GitHub Actions, etc.)

### Framework Statistics
- **45+ Java Classes**
- **59+ Test Cases** (31 UI + 28 API)
- **8 Documentation Files**
- **5 Configuration Files**
- **5,000+ Lines of Code**

---

## Architecture & Design

### Layered Architecture

```
┌─────────────────────────────────────────────────────┐
│              TEST LAYER                              │
│  (UI Tests, API Tests, E2E Tests)                   │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│         PAGE OBJECT MODEL & API LAYER               │
│  (LoginPage, ProductsPage, APIHelper, etc.)         │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│            UTILITIES & HELPERS LAYER                │
│  (WaitHelper, TestDataGenerator, ScreenshotHelper)  │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│         DRIVER & CONFIGURATION LAYER                │
│  (DriverManager, ConfigManager, Ollama)             │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│         SELENIUM WebDriver & RestAssured            │
└─────────────────────────────────────────────────────┘
```

### Design Patterns Used

1. **Singleton Pattern**
   - ConfigManager (single configuration instance)
   - DriverManager (single driver per thread)

2. **Factory Pattern**
   - DriverFactory (create different browser types)

3. **Page Object Model**
   - BasePage (base class for all pages)
   - Individual page classes (LoginPage, HomePage, etc.)

4. **Builder Pattern**
   - TestDataGenerator (build complex test objects)

5. **Template Method Pattern**
   - BaseTest (setup/teardown template)

6. **Strategy Pattern**
   - Multiple wait strategies (WaitHelper)

---

## Installation & Setup

### Prerequisites

#### System Requirements
- **OS**: Windows, macOS, Linux
- **Java**: 11 or higher
- **Maven**: 3.6 or higher
- **RAM**: 4GB minimum
- **Disk**: 2GB free space

#### Software Installation

**Step 1: Verify Java Installation**
```bash
java -version
# Should show Java 11 or higher
```

**Step 2: Verify Maven Installation**
```bash
mvn --version
# Should show Maven 3.6 or higher
```

**Step 3: Download Project**
```bash
# Navigate to the project directory
cd Selenium_Java
```

### Initial Setup

**Step 1: Install Dependencies**
```bash
mvn clean install
# Downloads all dependencies from pom.xml
# Takes 2-5 minutes on first run
```

**Step 2: Verify Installation**
```bash
mvn --version
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-surefire-plugin
```

**Step 3: Update Configuration**
Edit `src/main/resources/config_local.properties`:
```properties
baseUrl=http://your-app-url:port
browser=chrome
implicit.wait=10
explicit.wait=15
headless=false
ollama.enabled=false  # Set to true if using AI features
```

**Step 4: (Optional) Install Ollama for AI Features**
```bash
# Download from https://ollama.ai
# Install and run
ollama serve

# In another terminal, pull the model
ollama pull mistral
```

---

## Framework Components

### 1. Configuration Management

#### ConfigManager Class
Centralized configuration management:

```java
ConfigManager config = ConfigManager.getInstance();
String baseUrl = config.getBaseUrl();
String browser = config.getBrowser();
int waitTime = config.getExplicitWait();
boolean headless = config.isHeadless();
```

#### Property Files
Located in `src/main/resources/`:

| File | Purpose | Use Case |
|------|---------|----------|
| config_local.properties | Local development | Development testing |
| config_dev.properties | Development server | Dev environment testing |
| config_staging.properties | Staging server | Pre-production testing |
| config_production.properties | Production | Production monitoring |

### 2. WebDriver Management

#### DriverManager Class
Thread-safe WebDriver management:

```java
// Initialize driver
DriverManager.initializeDriver();
// or with specific browser
DriverManager.initializeDriver("firefox");

// Get driver instance
WebDriver driver = DriverManager.getDriver();

// Navigate
DriverManager.navigateToBaseUrl();
DriverManager.navigateTo("https://example.com/products");

// Cleanup
DriverManager.quitDriver();
```

#### DriverFactory Class
Creates WebDriver for different browsers:

```java
// Automatically selects browser based on config
WebDriver driver = DriverFactory.createDriver("chrome");
// Creates driver with optimized options for the browser
```

### 3. Page Object Model

#### BasePage Class
Base class for all page objects:

```java
public class LoginPage extends BasePage {
    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BTN = By.id("login");
    
    public void login(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BTN);
    }
}
```

#### Available Methods in BasePage

**Click & Type**
- `click(By locator)` - Click element
- `type(By locator, String text)` - Type text
- `type(WebElement element, String text)` - Type in WebElement

**Get Info**
- `getText(By locator)` - Get element text
- `getAttribute(By locator, String attribute)` - Get attribute value
- `getCurrentUrl()` - Get current URL
- `getPageTitle()` - Get page title

**Verification**
- `isElementDisplayed(By locator)` - Check if displayed
- `isElementPresent(By locator)` - Check if present

**Navigation**
- `navigateTo(String url)` - Navigate to URL
- `refreshPage()` - Refresh page
- `goBack()` - Go back
- `goForward()` - Go forward

**Advanced**
- `hoverOver(By locator)` - Mouse hover
- `dragAndDrop(By source, By target)` - Drag and drop
- `scrollIntoView(By locator)` - Scroll element into view
- `executeScript(String script)` - Execute JavaScript
- `switchToFrame(By locator)` - Switch to frame
- `acceptAlert()` - Accept alert
- `dismissAlert()` - Dismiss alert

### 4. Wait Utilities

#### WaitHelper Class
Comprehensive wait mechanisms:

```java
// Wait for visibility (explicit wait)
WebElement el = WaitHelper.waitForElementToBeVisible(By.id("id"));

// Wait for clickable
WebElement el = WaitHelper.waitForElementToBeClickable(By.id("id"));

// Wait for presence
WebElement el = WaitHelper.waitForElementPresence(By.id("id"));

// Wait for text
WaitHelper.waitForTextToBePresent(By.id("id"), "text");

// Wait for page load
WaitHelper.waitForPageLoadComplete();

// Custom timeout (20 seconds)
WebElement el = WaitHelper.waitForElementToBeVisible(By.id("id"), 20);

// Wait for jQuery AJAX
WaitHelper.waitForJQueryAjaxComplete();
```

### 5. REST API Testing

#### APIHelper Class
RestAssured wrapper for API testing:

```java
// Set base URI
APIHelper.setBaseURI("https://api.example.com");

// GET request
Response response = APIHelper.get("/users");

// GET with query parameter
Response response = APIHelper.get("/products", "category", "electronics");

// POST request
JsonObject body = new JsonObject();
body.addProperty("name", "John");
Response response = APIHelper.post("/users", body.toString());

// PUT request
Response response = APIHelper.put("/users/1", body.toString());

// DELETE request
Response response = APIHelper.delete("/users/1");

// Verify response
APIHelper.verifyStatusCode(response, 200);

// Extract data
String value = APIHelper.extractJsonPath(response, "data.name");

// Set headers
APIHelper.addHeader("Authorization", "Bearer token");
APIHelper.setAuthorizationHeader("token");
```

### 6. Test Data Management

#### TestDataGenerator Class
Generate random test data:

```java
// Generate random data
String email = TestDataGenerator.generateEmail();
String password = TestDataGenerator.generatePassword();
String phone = TestDataGenerator.generatePhoneNumber();
String username = TestDataGenerator.generateUsername();

// Generate complex data
String productName = TestDataGenerator.generateProductName();
double price = TestDataGenerator.generatePrice(10, 1000);
String date = TestDataGenerator.generateDate();

// Generate objects
Map<String, String> user = TestDataGenerator.generateTestUser();
Map<String, Object> product = TestDataGenerator.generateTestProduct();

// Validation
boolean isValidEmail = TestDataGenerator.isValidEmail(email);
boolean isValidPassword = TestDataGenerator.isValidPassword(password);
```

#### JsonDataReader Class
Read JSON test data:

```java
JsonDataReader reader = new JsonDataReader("path/to/data.json");

// Get data
JsonObject obj = reader.getObject("path.to.object");
JsonArray arr = reader.getArray("path.to.array");
String value = reader.getString("path.to.string");

// Get as list
List<Map<String, String>> users = reader.getDataAsList("users");
```

#### ExcelDataReader Class
Read Excel test data:

```java
ExcelDataReader reader = new ExcelDataReader("path/to/file.xlsx");

// Get sheet data
List<Map<String, String>> data = reader.getDataFromSheet("Sheet1");

// Get sheet count
int sheets = reader.getSheetCount();

// Get cell value
String value = reader.getCellValue(0, 0, 0);

// Close reader
reader.close();
```

### 7. AI Integration (Ollama)

#### OllamaAIHelper Class
AI-powered testing capabilities:

```java
// Generate test data
String email = OllamaAIHelper.generateTestDataForField("email", "email");

// Analyze failure
String analysis = OllamaAIHelper.analyzeFailure(errorMsg, stackTrace);

// Suggest self-healing locator
String newLocator = OllamaAIHelper.suggestSelfHealingLocator(
    "id=oldLocator", "id"
);

// Generate test scenario
String scenario = OllamaAIHelper.generateTestScenario("Product Purchase");

// Generate multiple test cases
List<String> cases = OllamaAIHelper.generateMultipleTestCases(
    "User login requirement", 5
);

// Check Ollama availability
boolean available = OllamaAIHelper.isOllamaAvailable();
```

---

## Writing Tests

### Basic UI Test Structure

```java
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;
import com.automation.tests.BaseTest;
import com.automation.pages.LoginPage;

@Feature("Authentication")
@Story("User Login")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    
    @BeforeMethod
    public void setUp() {
        super.setUp();  // Initialize driver
        loginPage = new LoginPage();
    }
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test successful login with valid credentials")
    public void testLoginWithValidCredentials() {
        Allure.step("Enter credentials");
        loginPage.enterUsername("user@example.com");
        loginPage.enterPassword("Password@123");
        
        Allure.step("Click login button");
        loginPage.clickLoginButton();
        
        Allure.step("Verify login success");
        verifyPageUrl("/dashboard");
    }
    
    @AfterMethod
    public void tearDown() {
        super.tearDown();  // Cleanup
    }
}
```

### Data-Driven Test

```java
@Test(dataProvider = "loginData")
@Description("Data-driven login test")
public void testLoginDataDriven(String email, String password, String expected) {
    loginPage.login(email, password);
    
    if ("success".equals(expected)) {
        verifyPageUrl("/dashboard");
    } else {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
    }
}

@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][] {
        { "valid@example.com", "ValidPass@123", "success" },
        { "valid@example.com", "WrongPass@123", "failure" },
        { "", "ValidPass@123", "failure" }
    };
}
```

### API Test

```java
@Feature("API - Users")
public class UserAPITest extends BaseTest {
    
    @BeforeMethod
    public void setUp() {
        APIHelper.setBaseURI("https://api.example.com");
        APIHelper.setAuthorizationHeader("token");
    }
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test get user by ID")
    public void testGetUserById() {
        Allure.step("Send GET request for user ID 1");
        Response response = APIHelper.get("/users/1");
        
        Allure.step("Verify response status");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200));
        
        Allure.step("Verify user data");
        String email = APIHelper.extractJsonPath(response, "email");
        Assert.assertNotNull(email);
    }
}
```

### E2E Test

```java
@Feature("End-to-End")
public class E2ETest extends BaseTest {
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete user purchase workflow")
    public void testCompleteCheckout() {
        LoginPage login = new LoginPage();
        
        Allure.step("Login user");
        login.login("user@example.com", "Password@123");
        
        Allure.step("Navigate to products");
        HomePage home = new HomePage();
        home.navigateToProducts();
        
        Allure.step("Add product to cart");
        ProductsPage products = new ProductsPage();
        products.addProductToCart(0);
        
        Allure.step("Proceed to checkout");
        CartPage cart = new CartPage();
        cart.proceedToCheckout();
        
        Allure.step("Verify checkout page");
        verifyPageUrl("/checkout");
    }
}
```

---

## Running Tests

### Basic Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LoginTest

# Run specific test method
mvn test -Dtest=LoginTest#testLoginWithValidCredentials

# Run tests matching pattern
mvn test -Dtest=*APITest

# Run with specific environment
mvn test -Denvironment=staging

# Run with specific browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true

# Run with parallel execution
mvn test -DparallelMode=methods -DthreadCount=4

# Skip tests
mvn clean install -DskipTests

# Run with verbose output
mvn test -X
```

### Advanced Commands

```bash
# Run UI tests only
mvn test -Dtest=com.automation.tests.ui.*

# Run API tests only
mvn test -Dtest=com.automation.tests.api.*

# Run with retries (uses RetryAnalyzer)
mvn test -Dretry.count=3

# Run from specific suite
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Generate reports after tests
mvn clean test allure:report

# Full workflow: test, generate report, serve
mvn clean test allure:report allure:serve

# Run tests and immediately serve report
mvn clean test && mvn allure:serve
```

---

## Report Generation

### Quick Start - Allure Reports

**Step 1: Run Tests**
```bash
mvn clean test
```

**Step 2: Generate Report**
```bash
mvn allure:report
```

**Step 3: View Report**
```bash
mvn allure:serve
```

### One-Command Workflow
```bash
# Everything in one command
mvn clean test allure:report allure:serve
```

### Report Sections

#### Overview Dashboard
- Test execution summary
- Pass rate percentage
- Test duration
- Environment information

#### Categories
- **By Feature** - Group tests by @Feature annotation
- **By Severity** - Critical, High, Medium, Low
- **By Status** - Passed, Failed, Skipped

#### Details
- Individual test results
- Screenshots on failure
- Test steps and logs
- Failure messages and stack traces

### For Complete Details
See: [ALLURE_REPORT_SETUP.md](ALLURE_REPORT_SETUP.md)

---

## Best Practices

### Code Organization
✅ **Page Object Model** - Separate pages from tests
✅ **Base Classes** - Inherit from BasePage and BaseTest
✅ **Locator Centralization** - Store locators as static final variables
✅ **Method Names** - Clear, descriptive names

### Test Writing
✅ **Single Responsibility** - One test, one scenario
✅ **Independent Tests** - No dependencies between tests
✅ **Data-Driven** - Use DataProviders for multiple scenarios
✅ **Allure Steps** - Add @Step annotations for clarity

### Wait Strategies
✅ **Always Use Explicit Waits** - Avoid Thread.sleep()
✅ **Appropriate Timeouts** - Adjust based on application
✅ **Wait for Right Condition** - Visibility, clickability, presence
✅ **Handle Timeouts** - Catch and log failures

### Assertions
✅ **Meaningful Messages** - Describe what failed
✅ **Use AssertJ** - Better assertion syntax
✅ **One Assertion Per Test** - Focus on one thing

### Configuration
✅ **Use ConfigManager** - Don't hardcode values
✅ **Environment-Specific** - Different configs per environment
✅ **Version Control** - Commit config files

### Logging
✅ **Log Important Steps** - What's happening in test
✅ **Log Failures** - Why test failed
✅ **Use Appropriate Levels** - info, warn, error
✅ **Review Logs** - Helps debugging

### Parallel Execution
✅ **ThreadLocal Driver** - Provided by DriverManager
✅ **Independent Tests** - No shared state
✅ **Separate Browser Sessions** - No interference

### Maintenance
✅ **Update Locators** - When UI changes
✅ **Fix Flaky Tests** - Increase waits or improve logic
✅ **Review Reports** - Monitor pass rates
✅ **Keep Code DRY** - Reuse common actions

---

## Troubleshooting

### Common Issues

#### Issue: WebDriver Not Initializing
```bash
# Solution: Check Java and Maven versions
java -version
mvn --version

# Clear cache and try again
mvn clean install
```

#### Issue: Element Not Found
```bash
# Causes:
- Locator incorrect
- Element not loaded yet
- Element in frame
- Element in shadow DOM

# Solutions:
WaitHelper.waitForElementToBeVisible(locator);
WaitHelper.waitForElementPresence(locator);
switchToFrame(frameLocator);
```

#### Issue: Test Timeout
```bash
# Increase wait timeout
config.explicit.wait=30

# Or specify in code
WaitHelper.waitForElementToBeVisible(locator, 30);
```

#### Issue: Tests Not Running
```bash
# Check TestNG configuration
# Verify testng.xml syntax
# Check test class extends BaseTest
# Verify @Test annotation present

mvn test -X  # Verbose output
```

#### Issue: Allure Report Not Generating
```bash
# Clear target directory
rm -rf target/

# Reinstall dependencies
mvn clean install

# Run tests and generate
mvn clean test allure:report
```

### Debug Mode

```bash
# Run with debug output
mvn test -X

# Run single test with debug
mvn test -Dtest=LoginTest -X

# Check logs in logs/ directory
tail -f logs/app.log
```

---

## FAQ

### Q: How to add a new test?
**A:**
1. Create test class in `src/test/java`
2. Extend `BaseTest`
3. Add `@Feature` and `@Test` annotations
4. Use Page Objects for page interactions
5. Add `@Step` annotations for clarity

### Q: How to add a new page object?
**A:**
1. Create class in `src/main/java/com/automation/pages/`
2. Extend `BasePage`
3. Define locators as static final variables
4. Create action methods
5. Use in test classes

### Q: How to change environment?
**A:**
```bash
mvn test -Denvironment=staging
# Or set in IDE configuration
```

### Q: How to run tests in parallel?
**A:**
Modify `testng.xml`:
```xml
<suite parallel="methods" thread-count="4">
```

### Q: How to add custom wait?
**A:**
```java
WaitHelper.waitUntil(ExpectedCondition<?> condition);
// Or
WaitHelper.waitForPageLoadComplete();
```

### Q: How to generate test data?
**A:**
```java
String email = TestDataGenerator.generateEmail();
Map<String, String> user = TestDataGenerator.generateTestUser();
```

### Q: How to attach screenshots to report?
**A:**
```java
ScreenshotHelper.captureScreenshot("testName");
// Automatic on failure in TestListener
```

### Q: How to use Ollama for AI?
**A:**
1. Install Ollama
2. Run: `ollama serve`
3. Enable in config: `ollama.enabled=true`
4. Use: `OllamaAIHelper.generateTestData(...)`

### Q: How to integrate with Jenkins?
**A:**
See: [ALLURE_REPORT_SETUP.md](ALLURE_REPORT_SETUP.md) - Jenkins Pipeline section

### Q: How to handle dynamic elements?
**A:**
```java
// Use flexible locators
By dynamicElement = By.xpath("//*[contains(text(), '" + dynamicText + "')]");
WaitHelper.waitForElementToBeVisible(dynamicElement);
```

### Q: How to handle multiple environments?
**A:**
Create separate config files and use:
```bash
mvn test -Denvironment=staging
```

---

## Summary

This framework provides:
- ✅ Complete UI automation with Selenium
- ✅ REST API testing with RestAssured
- ✅ AI-powered testing capabilities
- ✅ Beautiful Allure reports
- ✅ Professional code structure
- ✅ Comprehensive documentation
- ✅ Enterprise-grade features
- ✅ Easy to extend and maintain

**Get Started:**
1. Clone/download project
2. Run `mvn clean install`
3. Execute `mvn test`
4. View reports with `mvn allure:serve`

**Questions?** Refer to specific documentation files or review test examples.

---

**Framework Version:** 1.0.0  
**Last Updated:** 2026-06-22  
**Status:** Production Ready ✅
