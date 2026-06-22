# Hybrid Framework - Quick Reference Guide

## Common Operations

### Page Object Methods
```java
// Click element
click(By.id("buttonId"));

// Type text
type(By.id("inputId"), "text");

// Get text
String text = getText(By.id("elementId"));

// Get attribute
String value = getAttribute(By.id("elementId"), "value");

// Check if displayed
if (isElementDisplayed(By.id("elementId"))) { }

// Check if present
if (isElementPresent(By.id("elementId"))) { }

// Hover over element
hoverOver(By.id("elementId"));

// Scroll into view
scrollIntoView(By.id("elementId"));

// Switch to frame
switchToFrame(By.id("frameId"));

// Accept alert
acceptAlert();

// Get current URL
String url = getCurrentUrl();

// Get page title
String title = getPageTitle();
```

### Wait Methods
```java
// Wait for visibility
WebElement el = WaitHelper.waitForElementToBeVisible(By.id("id"));

// Wait for clickable
WebElement el = WaitHelper.waitForElementToBeClickable(By.id("id"));

// Wait for presence
WebElement el = WaitHelper.waitForElementPresence(By.id("id"));

// Wait for text
WaitHelper.waitForTextToBePresent(By.id("id"), "text");

// Wait for page load
WaitHelper.waitForPageLoadComplete();

// Custom timeout
WebElement el = WaitHelper.waitForElementToBeVisible(By.id("id"), 20);
```

### API Testing
```java
// GET request
Response response = APIHelper.get("/endpoint");

// GET with query parameter
Response response = APIHelper.get("/endpoint", "param", "value");

// POST request
Response response = APIHelper.post("/endpoint", jsonBody);

// PUT request
Response response = APIHelper.put("/endpoint", jsonBody);

// DELETE request
Response response = APIHelper.delete("/endpoint");

// Verify status
APIHelper.verifyStatusCode(response, 200);

// Extract JSON path
String value = APIHelper.extractJsonPath(response, "path.to.value");

// Set auth header
APIHelper.setAuthorizationHeader("token");
```

### Configuration
```java
// Get config manager
ConfigManager config = ConfigManager.getInstance();

// Get property
String value = config.getProperty("key");

// Get with default
String value = config.getProperty("key", "default");

// Get specific properties
String baseUrl = config.getBaseUrl();
String browser = config.getBrowser();
int waitTime = config.getExplicitWait();
boolean headless = config.isHeadless();
```

### Test Data
```java
// Generate random email
String email = TestDataGenerator.generateEmail();

// Generate random password
String password = TestDataGenerator.generatePassword();

// Generate phone number
String phone = TestDataGenerator.generatePhoneNumber();

// Generate product name
String product = TestDataGenerator.generateProductName();

// Generate test user
Map<String, String> user = TestDataGenerator.generateTestUser();

// Generate test product
Map<String, Object> product = TestDataGenerator.generateTestProduct();

// AI-assisted data generation
String data = TestDataGenerator.generateAITestData("email");
```

### AI Features
```java
// Generate test data
String testData = OllamaAIHelper.generateTestData("Generate an email");

// Analyze failure
String analysis = OllamaAIHelper.analyzeFailure(errorMsg, stackTrace);

// Self-healing locator
String newLocator = OllamaAIHelper.suggestSelfHealingLocator(oldLocator, "id");

// Generate test scenarios
String scenario = OllamaAIHelper.generateTestScenario("feature name");

// Check if Ollama available
boolean available = OllamaAIHelper.isOllamaAvailable();
```

### JSON Data
```java
// Read JSON file
JsonDataReader reader = new JsonDataReader("path/to/file.json");

// Get object
JsonObject obj = reader.getObject("path.to.object");

// Get array
JsonArray arr = reader.getArray("path.to.array");

// Get string
String value = reader.getString("path.to.string");

// Get as list of maps
List<Map<String, String>> data = reader.getDataAsList("path.to.array");
```

### Excel Data
```java
// Read Excel file
ExcelDataReader reader = new ExcelDataReader("path/to/file.xlsx");

// Get data from sheet
List<Map<String, String>> data = reader.getDataFromSheet("SheetName");

// Get sheet names
List<String> sheets = reader.getSheetNames();

// Get row count
int rows = reader.getRowCount("SheetName");

// Close reader
reader.close();
```

### Screenshots
```java
// Capture screenshot
String path = ScreenshotHelper.captureScreenshot("testName");

// Capture default screenshot
String path = ScreenshotHelper.captureScreenshot();

// Attach to Allure
ScreenshotHelper.attachScreenshotToAllure("ScreenName");
```

### Assertions
```java
// Assert with message
Assert.assertTrue(condition, "Message");
Assert.assertFalse(condition, "Message");
Assert.assertEquals(actual, expected, "Message");
Assert.assertNotNull(object, "Message");

// Allure steps
@Step("Do something important")
public void doSomething() { }
```

### Test Annotations
```java
@Feature("Feature Name")
@Story("Story Name")
@Severity(SeverityLevel.CRITICAL)
@Description("Test description")
@Test(dataProvider = "dataName")
public void testName() { }

@BeforeMethod
public void setup() { }

@AfterMethod
public void teardown() { }

@DataProvider(name = "dataName")
public Object[][] getData() { }
```

## Running Tests

### Command Line
```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=LoginTest

# Specific test method
mvn test -Dtest=LoginTest#testLoginWithValidCredentials

# Specific environment
mvn test -Denvironment=staging

# Specific browser
mvn test -Dbrowser=firefox

# Headless mode
mvn test -Dheadless=true

# With logging
mvn test -X

# Generate Allure report
mvn allure:report
mvn allure:serve
```

## File Locations

- **Configuration**: `src/main/resources/config_*.properties`
- **Test Data**: `src/test/resources/testdata/`
- **Page Objects**: `src/main/java/com/automation/pages/`
- **Tests**: `src/test/java/com/automation/tests/`
- **Logs**: `logs/`
- **Screenshots**: `target/screenshots/`
- **Reports**: `target/allure-results/`

## Environment Setup

```properties
# Local
environment=local
baseUrl=http://localhost:8080
browser=chrome
headless=false

# Dev
environment=dev
baseUrl=https://dev.example.com
browser=chrome
headless=false

# Staging
environment=staging
baseUrl=https://staging.example.com
browser=firefox
headless=false
```

## Troubleshooting

### Element Not Found
- Check locator accuracy
- Increase wait timeout
- Check for frames/pop-ups
- Enable screenshots for debugging

### Test Timeout
- Increase wait time in config
- Check network speed
- Verify application responsiveness
- Check for page load delays

### WebDriver Issues
- Verify browser version
- Update WebDriverManager
- Check PATH variables
- Restart browser

### Ollama Connection
- Ensure Ollama server is running
- Check `ollama.base.url` in config
- Verify model is installed: `ollama pull mistral`
- Disable if not needed: `ollama.enabled=false`

## Best Practices

✅ Use Page Object Model
✅ Use explicit waits (avoid Thread.sleep)
✅ Keep tests independent
✅ Use data providers for multiple scenarios
✅ Add meaningful Allure steps
✅ Capture screenshots on failures
✅ Use proper logging
✅ Follow naming conventions
✅ Clean up after tests
✅ Use configuration management

## Common Patterns

### Page Object Pattern
```java
public class MyPage extends BasePage {
    private static final By ELEMENT = By.id("id");
    
    public void doAction() {
        click(ELEMENT);
    }
    
    public String getResult() {
        return getText(ELEMENT);
    }
}
```

### Test Pattern
```java
@Feature("Feature")
public class MyTest extends BaseTest {
    private MyPage page;
    
    @BeforeMethod
    public void setUp() {
        super.setUp();
        page = new MyPage();
    }
    
    @Test
    public void testScenario() {
        page.doAction();
        Assert.assertEquals(page.getResult(), "expected");
    }
}
```

### Data-Driven Pattern
```java
@Test(dataProvider = "testData")
public void testWithData(String email, String password) {
    page.login(email, password);
}

@DataProvider(name = "testData")
public Object[][] getTestData() {
    return new Object[][] {
        { "user1@example.com", "pass1" },
        { "user2@example.com", "pass2" }
    };
}
```

---

**For more details, refer to:**
- GETTING_STARTED.md
- IMPLEMENTATION_SUMMARY.md
- CLASS_HIERARCHY.md
