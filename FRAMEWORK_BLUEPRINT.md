# Hybrid Selenium Java Automation Framework - Complete Blueprint

## Overview
A scalable, maintainable hybrid framework supporting both UI (Selenium WebDriver) and API (RestAssured) testing with AI-powered self-healing, data-driven test execution, and comprehensive reporting.

---

## 1. PROJECT DIRECTORY STRUCTURE

```
SeleniumJavaFramework/
│
├── pom.xml                                 # Maven build configuration
├── FRAMEWORK_BLUEPRINT.md                  # This documentation
├── README.md                               # Quick start guide
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/automation/framework/
│   │           │
│   │           ├── config/                 # Configuration Management
│   │           │   ├── ConfigManager.java
│   │           │   ├── PropertyReader.java
│   │           │   ├── EnvironmentConfig.java
│   │           │   └── constants/
│   │           │       ├── FrameworkConstants.java
│   │           │       ├── BrowserType.java
│   │           │       └── Platform.java
│   │           │
│   │           ├── driver/                 # Driver Management
│   │           │   ├── DriverManager.java
│   │           │   ├── DriverFactory.java
│   │           │   ├── WebDriverListener.java
│   │           │   └── capabilities/
│   │           │       ├── CapabilityFactory.java
│   │           │       ├── ChromeCapabilities.java
│   │           │       ├── FirefoxCapabilities.java
│   │           │       └── EdgeCapabilities.java
│   │           │
│   │           ├── waits/                  # Wait Strategies
│   │           │   ├── WaitHelper.java
│   │           │   ├── CustomWait.java
│   │           │   └── WaitFactory.java
│   │           │
│   │           ├── base/                   # Base Classes
│   │           │   ├── BasePage.java
│   │           │   ├── BaseTest.java
│   │           │   ├── BaseActions.java
│   │           │   └── BaseAPI.java
│   │           │
│   │           ├── pages/                  # Page Object Model (POM)
│   │           │   ├── auth/
│   │           │   │   ├── LoginPage.java
│   │           │   │   ├── RegisterPage.java
│   │           │   │   └── ForgotPasswordPage.java
│   │           │   ├── dashboard/
│   │           │   │   ├── HomePage.java
│   │           │   │   ├── DashboardPage.java
│   │           │   │   └── ProfilePage.java
│   │           │   ├── product/
│   │           │   │   ├── ProductListPage.java
│   │           │   │   ├── ProductDetailPage.java
│   │           │   │   └── SearchPage.java
│   │           │   ├── cart/
│   │           │   │   ├── CartPage.java
│   │           │   │   └── CheckoutPage.java
│   │           │   └── common/
│   │           │       ├── CommonPage.java
│   │           │       └── NavigationBar.java
│   │           │
│   │           ├── actions/                # Action Classes (Reusable Methods)
│   │           │   ├── AuthActions.java
│   │           │   ├── ProductActions.java
│   │           │   ├── CartActions.java
│   │           │   ├── CommonActions.java
│   │           │   └── SearchActions.java
│   │           │
│   │           ├── api/                    # API Testing Layer
│   │           │   ├── endpoints/
│   │           │   │   ├── AuthEndpoints.java
│   │           │   │   ├── UserEndpoints.java
│   │           │   │   ├── ProductEndpoints.java
│   │           │   │   └── OrderEndpoints.java
│   │           │   ├── models/
│   │           │   │   ├── request/
│   │           │   │   │   ├── LoginRequest.java
│   │           │   │   │   ├── UserRequest.java
│   │           │   │   │   └── OrderRequest.java
│   │           │   │   └── response/
│   │           │   │       ├── LoginResponse.java
│   │           │   │       ├── UserResponse.java
│   │           │   │       └── OrderResponse.java
│   │           │   ├── helpers/
│   │           │   │   ├── APIHelper.java
│   │           │   │   ├── APIClient.java
│   │           │   │   ├── AuthTokenManager.java
│   │           │   │   └── ResponseValidator.java
│   │           │   └── specs/
│   │           │       ├── RequestSpecBuilder.java
│   │           │       └── ResponseSpecBuilder.java
│   │           │
│   │           ├── data/                   # Data Handling
│   │           │   ├── readers/
│   │           │   │   ├── DataReader.java
│   │           │   │   ├── JSONDataReader.java
│   │           │   │   ├── ExcelDataReader.java
│   │           │   │   ├── CSVDataReader.java
│   │           │   │   └── DatabaseDataReader.java
│   │           │   ├── models/
│   │           │   │   ├── TestData.java
│   │           │   │   ├── UserTestData.java
│   │           │   │   └── ProductTestData.java
│   │           │   └── builders/
│   │           │       ├── TestDataBuilder.java
│   │           │       └── UserDataBuilder.java
│   │           │
│   │           ├── ai/                     # AI Integration (Ollama)
│   │           │   ├── OllamaAIHelper.java
│   │           │   ├── SelfHealingHelper.java
│   │           │   ├── TestDataGeneratorAI.java
│   │           │   ├── FailureAnalyzerAI.java
│   │           │   └── models/
│   │           │       ├── OllamaRequest.java
│   │           │       └── OllamaResponse.java
│   │           │
│   │           ├── utilities/              # Utility Classes
│   │           │   ├── FileUtils.java
│   │           │   ├── DateTimeUtils.java
│   │           │   ├── RandomDataGenerator.java
│   │           │   ├── EncryptionUtils.java
│   │           │   ├── ImageComparisonUtils.java
│   │           │   ├── ScreenshotUtils.java
│   │           │   ├── EmailValidator.java
│   │           │   └── RetryUtils.java
│   │           │
│   │           ├── listeners/              # TestNG Listeners
│   │           │   ├── TestListeners.java
│   │           │   ├── RetryListener.java
│   │           │   └── ScreenshotListener.java
│   │           │
│   │           ├── retry/                  # Retry Mechanisms
│   │           │   ├── RetryAnalyzer.java
│   │           │   └── RetryPolicy.java
│   │           │
│   │           ├── reporting/              # Reporting
│   │           │   ├── ReportManager.java
│   │           │   ├── AllureReportHelper.java
│   │           │   ├── TestReporter.java
│   │           │   └── ExtentReportHelper.java
│   │           │
│   │           └── exceptions/             # Custom Exceptions
│   │               ├── FrameworkException.java
│   │               ├── ElementNotFoundException.java
│   │               ├── APIException.java
│   │               └── ConfigurationException.java
│   │
│   └── test/
│       └── java/
│           └── com/automation/framework/
│               │
│               ├── ui/                     # UI Test Classes
│               │   ├── auth/
│               │   │   ├── LoginTests.java
│               │   │   ├── RegisterTests.java
│               │   │   └── ForgotPasswordTests.java
│               │   ├── product/
│               │   │   ├── ProductListTests.java
│               │   │   ├── ProductSearchTests.java
│               │   │   └── ProductFilterTests.java
│               │   ├── cart/
│               │   │   ├── CartTests.java
│               │   │   ├── CheckoutTests.java
│               │   │   └── PaymentTests.java
│               │   ├── user/
│               │   │   ├── ProfileTests.java
│               │   │   ├── SettingsTests.java
│               │   │   └── OrderHistoryTests.java
│               │   └── integration/
│               │       ├── E2ELoginToCheckoutTests.java
│               │       └── FullUserJourneyTests.java
│               │
│               ├── api/                    # API Test Classes
│               │   ├── auth/
│               │   │   ├── LoginAPITests.java
│               │   │   └── TokenRefreshAPITests.java
│               │   ├── user/
│               │   │   ├── UserAPITests.java
│               │   │   └── UserProfileAPITests.java
│               │   ├── product/
│               │   │   ├── ProductAPITests.java
│               │   │   └── ProductSearchAPITests.java
│               │   ├── order/
│               │   │   ├── OrderAPITests.java
│               │   │   └── OrderStatusAPITests.java
│               │   └── integration/
│               │       ├── APIChainingTests.java
│               │       └── CrossAPIFlowTests.java
│               │
│               └── utilities/
│                   └── TestBase.java       # Common test utilities
│
├── src/test/resources/
│   ├── testng.xml                          # TestNG configuration
│   ├── application.properties               # Framework properties
│   ├── log4j2.xml                          # Logging configuration
│   │
│   ├── config/
│   │   ├── dev.properties
│   │   ├── staging.properties
│   │   ├── prod.properties
│   │   └── local.properties
│   │
│   ├── testdata/
│   │   ├── json/
│   │   │   ├── loginData.json
│   │   │   ├── userData.json
│   │   │   ├── productData.json
│   │   │   └── orderData.json
│   │   ├── excel/
│   │   │   ├── testCases.xlsx
│   │   │   └── testData.xlsx
│   │   └── csv/
│   │       ├── userTestData.csv
│   │       └── productTestData.csv
│   │
│   └── locators/
│       ├── loginPageLocators.properties
│       ├── productPageLocators.properties
│       └── cartPageLocators.properties
│
├── test-output/                            # Test execution outputs
│   ├── report/                             # Allure reports
│   └── screenshots/                        # Failure screenshots
│
├── logs/                                   # Application logs
│   └── automation.log
│
└── documentation/
    ├── SETUP.md                            # Setup instructions
    ├── ARCHITECTURE.md                     # Architecture details
    ├── CONTRIBUTING.md                     # Contribution guidelines
    └── TROUBLESHOOTING.md                  # Common issues
```

---

## 2. CORE DEPENDENCIES & VERSIONS

```xml
<!-- Core Selenium & Browser Automation -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.15.0</version>
</dependency>

<!-- Test Runner -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.1</version>
</dependency>

<!-- API Testing -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.2</version>
</dependency>

<!-- Reporting -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.21.0</version>
</dependency>

<!-- Excel Reading -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.0.0</version>
</dependency>

<!-- JSON Processing -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>

<!-- Logging -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.5</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.20.0</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>

<!-- HTTP Client for API/AI -->
<dependency>
    <groupId>org.apache.httpcomponents.client5</groupId>
    <artifactId>httpclient5</artifactId>
    <version>5.2.1</version>
</dependency>

<!-- Database (Optional) -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>

<!-- CSV Processing -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.10.0</version>
</dependency>

<!-- Property Handling -->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.13.0</version>
</dependency>

<!-- Assertions -->
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.24.1</version>
</dependency>

<!-- Image Comparison (Optional) -->
<dependency>
    <groupId>de.redsix</groupId>
    <artifactId>pdfcompare</artifactId>
    <version>1.6.2</version>
</dependency>

<!-- Encryption -->
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
    <version>1.15</version>
</dependency>

<!-- Maven Plugins -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0</version>
</plugin>

<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.12.0</version>
</plugin>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
</plugin>
```

---

## 3. MAIN CLASSES TO CREATE

### A. Configuration Management Layer

| Class Name | Purpose | Scope |
|------------|---------|-------|
| `ConfigManager` | Centralized config loading | Singleton - manages all environment configs |
| `PropertyReader` | Reads .properties files | Static utility for property lookup |
| `EnvironmentConfig` | Enum-based environment selection | Supports DEV, STAGING, PROD, LOCAL |
| `FrameworkConstants` | Global constants | Browser types, timeouts, paths |
| `BrowserType` | Browser enum | CHROME, FIREFOX, EDGE, SAFARI |

### B. Driver Management Layer

| Class Name | Purpose | Scope |
|------------|---------|-------|
| `DriverManager` | WebDriver lifecycle | ThreadLocal-based for parallel execution |
| `DriverFactory` | Browser instance creation | Factory pattern implementation |
| `WebDriverListener` | Event listener | Logs actions, captures screenshots |
| `CapabilityFactory` | Browser capabilities | Headless, proxy, mobile emulation support |
| `ChromeCapabilities` | Chrome-specific setup | Chrome options configuration |
| `FirefoxCapabilities` | Firefox-specific setup | Firefox options configuration |
| `EdgeCapabilities` | Edge-specific setup | Edge options configuration |

### C. Wait Strategies Layer

| Class Name | Purpose | Scope |
|------------|---------|-------|
| `WaitHelper` | Explicit waits wrapper | Fluent API for common waits |
| `CustomWait` | Custom wait conditions | Reusable wait implementations |
| `WaitFactory` | Wait strategy factory | Creates wait instances |

### D. Base Classes

| Class Name | Purpose | Scope |
|------------|---------|-------|
| `BasePage` | Page Object base | Setup, teardown, common element operations |
| `BaseActions` | Reusable action methods | Common actions across all pages |
| `BaseTest` | Test class base | Before/after hooks, driver setup |
| `BaseAPI` | API test base | Common API test setup/teardown |

### E. Page Object Model (POM)

#### Auth Module
- `LoginPage` - Login form elements & methods
- `RegisterPage` - Registration form elements & methods
- `ForgotPasswordPage` - Password reset elements & methods

#### Dashboard Module
- `HomePage` - Homepage elements & methods
- `DashboardPage` - Dashboard-specific elements & methods
- `ProfilePage` - User profile elements & methods

#### Product Module
- `ProductListPage` - Product listing elements & methods
- `ProductDetailPage` - Product details elements & methods
- `SearchPage` - Search functionality elements & methods

#### Cart & Checkout
- `CartPage` - Shopping cart elements & methods
- `CheckoutPage` - Checkout flow elements & methods

#### Common
- `CommonPage` - Shared page elements (modals, alerts)
- `NavigationBar` - Header/navigation elements & methods

### F. Actions Classes (Reusable Methods)

| Class Name | Purpose | Methods Include |
|------------|---------|------------------|
| `AuthActions` | Authentication workflows | login(), register(), logout(), resetPassword() |
| `ProductActions` | Product interactions | searchProduct(), filterProducts(), viewDetails() |
| `CartActions` | Cart operations | addToCart(), removeFromCart(), updateQuantity() |
| `CommonActions` | Shared interactions | click(), fill(), scroll(), switchWindow() |
| `SearchActions` | Search workflows | searchByKeyword(), applyFilters(), sortResults() |

### G. API Testing Layer

#### Endpoints
- `AuthEndpoints` - /auth/* endpoints
- `UserEndpoints` - /user/* endpoints
- `ProductEndpoints` - /product/* endpoints
- `OrderEndpoints` - /order/* endpoints

#### Request/Response Models
- `LoginRequest`, `UserRequest`, `OrderRequest` - Request DTOs
- `LoginResponse`, `UserResponse`, `OrderResponse` - Response DTOs

#### Helpers
- `APIHelper` - Common API operations
- `APIClient` - RestAssured client wrapper
- `AuthTokenManager` - Token handling & refresh
- `ResponseValidator` - Response validation utilities
- `RequestSpecBuilder` - RestAssured spec builder
- `ResponseSpecBuilder` - Response spec builder

### H. Data Handling Layer

#### Readers
- `DataReader` - Interface for data readers
- `JSONDataReader` - JSON file reading
- `ExcelDataReader` - Excel file reading
- `CSVDataReader` - CSV file reading
- `DatabaseDataReader` - Database query execution

#### Models & Builders
- `TestData` - Generic test data container
- `UserTestData` - User-specific test data
- `ProductTestData` - Product-specific test data
- `TestDataBuilder` - Builder pattern for test data
- `UserDataBuilder` - User data builder

### I. AI Integration Layer (Ollama)

| Class Name | Purpose | Capabilities |
|------------|---------|--------------|
| `OllamaAIHelper` | Main Ollama integration | LLM communication |
| `SelfHealingHelper` | Self-healing locators | AI-based element recovery |
| `TestDataGeneratorAI` | Intelligent test data | AI-generated test scenarios |
| `FailureAnalyzerAI` | Failure analysis | Root cause analysis via AI |
| `OllamaRequest` | Request model | Prompt + configuration |
| `OllamaResponse` | Response model | LLM output parsing |

### J. Utility Classes

| Class Name | Purpose | Key Methods |
|------------|---------|-------------|
| `FileUtils` | File operations | readFile(), writeFile(), deleteFile() |
| `DateTimeUtils` | Date/time handling | getCurrentDate(), formatDate(), waitUntil() |
| `RandomDataGenerator` | Test data generation | randomEmail(), randomString(), randomNumber() |
| `EncryptionUtils` | Data encryption | encrypt(), decrypt() |
| `ImageComparisonUtils` | Visual testing | compareImages(), highlightDifferences() |
| `ScreenshotUtils` | Screenshot capture | takeScreenshot(), annotateScreenshot() |
| `EmailValidator` | Email validation | isValidEmail(), extractEmailDomain() |
| `RetryUtils` | Retry operations | retryUntil(), executeWithRetry() |

### K. Listeners & Retry

| Class Name | Purpose | Implements |
|------------|---------|-----------|
| `TestListeners` | Main test listener | ITestListener (before/after test) |
| `RetryListener` | Retry coordinator | IRetryAnalyzer (flaky test handling) |
| `ScreenshotListener` | Screenshot on failure | Custom failure handling |
| `RetryAnalyzer` | Determines retry logic | IRetryAnalyzer interface |
| `RetryPolicy` | Retry configuration | Max attempts, wait duration |

### L. Reporting Layer

| Class Name | Purpose | Features |
|------------|---------|----------|
| `ReportManager` | Main report manager | Allure integration |
| `AllureReportHelper` | Allure operations | addStep(), addAttachment() |
| `TestReporter` | Report utilities | log(), status tracking |
| `ExtentReportHelper` | ExtentReports wrapper | HTML report generation |

### M. Exception Classes

| Class Name | Purpose | Use Case |
|------------|---------|----------|
| `FrameworkException` | Base exception | General framework errors |
| `ElementNotFoundException` | Element issues | Element not found/visible |
| `APIException` | API errors | HTTP errors, timeout |
| `ConfigurationException` | Config issues | Missing/invalid configuration |

---

## 4. TEST STRUCTURE - 30 TEST CASES

### UI Tests (15 Tests)

#### Auth Module (3 Tests)
1. `testValidLogin` - Successful login with valid credentials
2. `testInvalidLogin` - Failed login with invalid credentials
3. `testPasswordReset` - Password reset workflow

#### Product Module (4 Tests)
4. `testSearchProduct` - Search functionality
5. `testFilterProducts` - Product filtering
6. `testSortProducts` - Product sorting
7. `testProductDetail` - View product details

#### Cart & Checkout (4 Tests)
8. `testAddToCart` - Add product to cart
9. `testRemoveFromCart` - Remove product from cart
10. `testUpdateQuantity` - Update cart quantity
11. `testCheckoutFlow` - Complete checkout process

#### User Profile (2 Tests)
12. `testUpdateProfile` - Update user information
13. `testChangePassword` - Change user password

#### E2E (2 Tests)
14. `testEndToEndPurchase` - Full purchase workflow
15. `testGuestCheckout` - Checkout without login

### API Tests (15 Tests)

#### Auth API (3 Tests)
16. `testLoginAPI` - Login endpoint
17. `testTokenRefresh` - Token refresh endpoint
18. `testLogout` - Logout endpoint

#### User API (3 Tests)
19. `testGetUserProfile` - Fetch user profile
20. `testUpdateUserAPI` - Update user via API
21. `testDeleteUser` - Delete user account

#### Product API (3 Tests)
22. `testGetProducts` - List products
23. `testGetProductById` - Get single product
24. `testSearchProductAPI` - Search via API

#### Order API (3 Tests)
25. `testCreateOrder` - Create new order
26. `testGetOrderStatus` - Get order status
27. `testCancelOrder` - Cancel existing order

#### Cross-API Flows (3 Tests)
28. `testCreateUserAndLogin` - Multi-endpoint workflow
29. `testCompleteOrderFlow` - Create product → Create order → Get status
30. `testUpdateAndRetrieveUser` - Update user → Fetch & verify

---

## 5. ARCHITECTURE PATTERNS & PRINCIPLES

### Design Patterns Used
1. **Singleton Pattern** - DriverManager, ConfigManager, ReportManager
2. **Factory Pattern** - DriverFactory, CapabilityFactory, WaitFactory
3. **Page Object Model** - All page classes
4. **Builder Pattern** - TestDataBuilder, RequestSpecBuilder
5. **Strategy Pattern** - DataReader implementations
6. **Listener Pattern** - TestNG listeners
7. **Template Method** - BaseTest, BasePage

### SOLID Principles
- **Single Responsibility** - Each class has one reason to change
- **Open/Closed** - Base classes extensible for new page/test types
- **Liskov Substitution** - DataReader implementations are interchangeable
- **Interface Segregation** - Focused interfaces (IDataReader, IWait)
- **Dependency Inversion** - Depends on abstractions, not concrete classes

### Key Architectural Features

1. **ThreadLocal Driver Management** - Thread-safe parallel execution
2. **Environment Configuration** - Support for dev/staging/prod
3. **Explicit Waits** - No implicit waits, custom wait strategies
4. **Retry Mechanisms** - Automatic retry for flaky tests
5. **Self-Healing** - AI-powered element locator recovery
6. **Data-Driven Testing** - Multiple data source support
7. **Comprehensive Logging** - SLF4J with Log4j2
8. **Screenshot on Failure** - Automatic failure documentation
9. **Allure Reporting** - Detailed test reports with attachments
10. **API Chaining** - Support for dependent API calls

---

## 6. CONFIGURATION FILES STRUCTURE

### testng.xml
```xml
- Parallel execution settings
- Thread count configuration
- Suite definitions
- Test group configurations
- Listener declarations
```

### application.properties
```properties
- Base URLs (UI & API)
- Browser types
- Timeout values
- Headless mode flags
- Logging levels
- Report paths
- Allure config
```

### Environment-Specific Properties
```
config/dev.properties
config/staging.properties
config/prod.properties
config/local.properties
```

### Locator Properties
```
- Centralized locators by page
- XPath/CSS selector strategies
- Dynamic locator templates
- Fallback locators for self-healing
```

---

## 7. TEST DATA SOURCES

### JSON Format
```json
{
  "testUsers": [
    {"username": "user1", "password": "pass123", "role": "customer"}
  ],
  "testProducts": [
    {"id": 1, "name": "Product 1", "price": 99.99}
  ]
}
```

### Excel Format
```
Sheet: Users
  Username | Password | Email | Role
  
Sheet: Products
  ProductID | Name | Price | Category
```

### CSV Format
```
username,password,email
user1,pass123,user1@test.com
user2,pass456,user2@test.com
```

---

## 8. LOGGING & REPORTING STRATEGY

### Log4j2 Configuration
```
- Console appender (DEBUG level)
- File appender (INFO level)
- Daily rolling files
- Pattern: [%d{HH:mm:ss}] [%-5p] [%c{1}] - %m%n
```

### Allure Reporting
```
- Steps with @Step annotation
- Attachments (screenshots, logs)
- Test execution history
- Custom test data
- Failure attachments
```

---

## 9. CI/CD INTEGRATION POINTS

### Maven Profiles
```xml
<profile id="dev">
<profile id="staging">
<profile id="prod">
<profile id="parallel">
```

### Execution Commands
```bash
mvn clean test                          # All tests
mvn clean test -Denv=staging           # Staging tests
mvn test -Dgroups=smoke                # Smoke tests
mvn test -Dgroups=regression           # Regression tests
mvn test -Dthreads=4                   # Parallel execution
```

---

## 10. KEY IMPLEMENTATION CONSIDERATIONS

### Performance
- ThreadLocal for thread-safe driver management
- Connection pooling for API calls
- Lazy loading of configuration
- Parallel test execution support

### Scalability
- Modular page object structure
- Reusable action classes
- Centralized wait strategies
- Data-driven approach

### Maintainability
- Clear package organization
- Consistent naming conventions
- Comprehensive documentation
- Logging at key decision points

### Reliability
- Multiple retry mechanisms
- Self-healing locators via AI
- Explicit wait strategies
- Screenshot on every failure

### Extensibility
- Template method pattern in base classes
- Abstract methods for customization
- Interface-based implementations
- Plugin architecture for listeners

---

## 11. GLOSSARY OF KEY TERMS

| Term | Definition |
|------|-----------|
| **POM** | Page Object Model - Design pattern for organizing UI test code |
| **BaseTest** | Abstract class containing common test setup/teardown |
| **BasePage** | Abstract class containing common page element methods |
| **DriverManager** | Singleton managing WebDriver lifecycle per thread |
| **ConfigManager** | Centralized configuration management |
| **WaitHelper** | Utility for explicit waits |
| **Self-Healing** | AI-based automatic element locator recovery |
| **Data-Driven** | Test execution using external data sources |
| **TestNG Listener** | Hook for executing code at test lifecycle points |
| **Allure Report** | Framework for generating detailed test reports |

---

## 12. EXECUTION FLOW DIAGRAM

```
Test Execution Flow:
┌─────────────────────────────────────────────────────┐
│ 1. Load Configuration (ConfigManager)               │
│    ↓                                                 │
│ 2. Initialize Driver (DriverManager/DriverFactory)  │
│    ↓                                                 │
│ 3. Load Test Data (DataReader)                      │
│    ↓                                                 │
│ 4. Execute Test Steps (Page/Action Classes)         │
│    ↓                                                 │
│ 5. Assert Results (Assertions + Allure Steps)       │
│    ↓                                                 │
│ 6. On Failure: Screenshot + Analyze (AI)            │
│    ↓                                                 │
│ 7. Generate Report (AllureReportHelper)             │
│    ↓                                                 │
│ 8. Cleanup (DriverManager.quitDriver())             │
└─────────────────────────────────────────────────────┘
```

---

## 13. NEXT STEPS

1. Create Maven pom.xml with all dependencies
2. Create directory structure
3. Implement base classes (BasePage, BaseTest, BaseAPI)
4. Create DriverManager and configuration layer
5. Implement wait strategies
6. Create sample POM pages (Login, Product, Cart)
7. Implement data readers (JSON, Excel, CSV)
8. Create API helper classes and models
9. Implement Ollama AI integration layer
10. Create 30 test cases (UI and API)
11. Configure TestNG and Allure reporting
12. Setup CI/CD integration

---

**Framework Version**: 1.0.0  
**Created**: 2026-06-22  
**Last Updated**: 2026-06-22  
**Status**: Blueprint Complete - Ready for Implementation
