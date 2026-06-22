# Hybrid Selenium Java Testing Framework - Getting Started Guide

## Overview
This is a **production-ready, scalable hybrid testing framework** built with:
- **Selenium 4.15** for UI automation
- **RestAssured 5.3** for API testing
- **TestNG 7.8** for test execution
- **Allure 2.21** for beautiful reports
- **Ollama AI** for intelligent test data generation and failure analysis
- **Apache POI** for Excel data handling
- **Log4j2** for comprehensive logging

## Quick Start

### Prerequisites
- **Java 11+** installed
- **Maven 3.6+** installed
- **Git** installed

### Installation Steps

1. **Clone/Download the project**
   ```bash
   cd Selenium_Java
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run all tests**
   ```bash
   mvn test
   ```

4. **Run specific test suite**
   ```bash
   mvn test -Dsuites=src/test/resources/testng.xml
   ```

5. **Run with specific environment**
   ```bash
   mvn test -Denvironment=dev
   ```

6. **Run with specific browser**
   ```bash
   mvn test -Dbrowser=firefox
   ```

## Project Structure

```
Selenium_Java/
├── src/
│   ├── main/
│   │   ├── java/com/automation/
│   │   │   ├── config/          # Configuration management
│   │   │   ├── driver/          # WebDriver management
│   │   │   ├── pages/           # Page Object Model classes
│   │   │   ├── actions/         # Reusable action classes
│   │   │   ├── api/             # REST API helpers
│   │   │   ├── utils/           # Utility classes
│   │   │   ├── ai/              # Ollama AI integration
│   │   │   ├── listeners/       # TestNG listeners
│   │   │   ├── report/          # Reporting utilities
│   │   │   └── exceptions/      # Custom exceptions
│   │   └── resources/
│   │       ├── config_*.properties   # Environment configs
│   │       └── log4j2.xml           # Logging config
│   └── test/
│       ├── java/com/automation/tests/
│       │   ├── ui/          # UI test classes
│       │   ├── api/         # API test classes
│       │   └── testdata/    # Test data management
│       └── resources/
│           ├── testng.xml        # TestNG configuration
│           └── testdata/         # Test data files (JSON, Excel)
├── pom.xml                  # Maven configuration
├── README.md               # This file
└── GETTING_STARTED.md      # Quick start guide
```

## Configuration

### Setting Environment
The framework supports multiple environments with property files:
- `config_local.properties` - Local development
- `config_dev.properties` - Development server
- `config_staging.properties` - Staging server
- `config_production.properties` - Production (if needed for monitoring)

**To run tests on a specific environment:**
```bash
mvn test -Denvironment=staging
```

### Key Configuration Properties
- `baseUrl` - Application base URL
- `browser` - Browser to use (chrome, firefox, edge, safari)
- `headless` - Run browser in headless mode
- `implicit.wait` - Implicit wait timeout (seconds)
- `explicit.wait` - Explicit wait timeout (seconds)
- `ollama.enabled` - Enable AI capabilities
- `retry.count` - Number of retry attempts for flaky tests

## Writing Tests

### Page Object Model Example
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

### UI Test Example
```java
@Feature("Authentication")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    
    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithValidCredentials() {
        loginPage.login("user@example.com", "password");
        verifyPageUrl("/dashboard");
    }
}
```

### API Test Example
```java
@Feature("API - Users")
public class UserAPITest extends BaseTest {
    
    @Test
    public void testGetUser() {
        Response response = APIHelper.get("/users/1");
        Assert.assertTrue(APIHelper.verifyStatusCode(response, 200));
    }
}
```

### Data-Driven Test Example
```java
@Test(dataProvider = "loginData")
public void testLoginDataDriven(String username, String password) {
    loginPage.login(username, password);
}

@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][] {
        { "user1@example.com", "password1" },
        { "user2@example.com", "password2" }
    };
}
```

## Using Test Data

### JSON Test Data
```java
JsonDataReader reader = new JsonDataReader("src/test/resources/testdata/login_testdata.json");
List<Map<String, String>> users = reader.getDataAsList("validUsers");
```

### Excel Test Data
```java
ExcelDataReader reader = new ExcelDataReader("path/to/testdata.xlsx");
List<Map<String, String>> data = reader.getDataFromSheet("Sheet1");
reader.close();
```

## AI-Powered Features (Ollama)

### Generate Test Data
```java
String testData = OllamaAIHelper.generateTestData(
    "Generate a valid email address for testing"
);
```

### Analyze Test Failures
```java
String analysis = OllamaAIHelper.analyzeFailure(
    errorMessage, stackTrace
);
```

### Self-Healing Locators
```java
String newLocator = OllamaAIHelper.suggestSelfHealingLocator(
    "id=oldLocator", "id"
);
```

## Running Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=LoginTest
```

### Run specific test method
```bash
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
```

### Run tests in parallel
Tests are configured to run in parallel (2 threads by default). Modify in `testng.xml`:
```xml
<suite parallel="methods" thread-count="4">
```

### Run with Allure Reports
```bash
mvn test
mvn allure:report
mvn allure:serve
```

## Reports

### Allure Reports
The framework generates beautiful Allure reports automatically:
- Reports are generated in `target/allure-results/`
- View reports with: `mvn allure:serve`

### Screenshots
- Automatically captured on test failure
- Stored in `target/screenshots/`
- Attached to Allure reports

### Logging
- Comprehensive logging with Log4j2
- Logs stored in `logs/` directory
- Separate logs for test execution and application logs

## CI/CD Integration

### GitHub Actions Example
```yaml
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '11'
      - run: mvn clean test
      - uses: actions/upload-artifact@v2
        if: always()
        with:
          name: allure-results
          path: target/allure-results
```

### Jenkins Example
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test -Denvironment=staging'
            }
        }
        stage('Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target/allure-results',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report'
                ])
            }
        }
    }
}
```

## Troubleshooting

### WebDriver Issues
- Ensure WebDriverManager is configured correctly
- Check browser versions compatibility
- Verify ChromeDriver/GeckoDriver in PATH

### Timeout Issues
- Increase wait times in configuration
- Check network connectivity
- Verify page load times

### Ollama AI Issues
- Ensure Ollama server is running: `ollama serve`
- Check Ollama connectivity: `ollama pull mistral`
- Disable if not available: Set `ollama.enabled=false`

## Best Practices

1. **Page Object Model**: Use POM for all UI interactions
2. **Data-Driven Tests**: Use data providers for multiple test scenarios
3. **Wait Strategies**: Always use explicit waits, avoid Thread.sleep()
4. **Error Handling**: Capture meaningful error messages
5. **Logging**: Log important steps and failures
6. **Screenshots**: Attach on failures for debugging
7. **Parallel Execution**: Use ThreadLocal for thread-safe WebDriver
8. **Clean Code**: Follow naming conventions and DRY principle

## Support & Documentation

- **Framework Blueprint**: See `FRAMEWORK_BLUEPRINT.md`
- **Test Cases Documentation**: See `TEST_CASES_AND_ROADMAP.md`
- **Class Hierarchy**: See `CLASS_HIERARCHY.md`
- **Framework Architecture**: See `README.md`

## Contributing

1. Create a new branch for your feature
2. Write tests following the framework conventions
3. Ensure all tests pass
4. Submit a pull request

## License

This framework is provided as-is for testing purposes.

## Contact

For issues, questions, or improvements, please refer to the project documentation.

---

**Happy Testing!** 🚀
