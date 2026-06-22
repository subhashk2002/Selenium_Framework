# Class Hierarchy & Dependencies - Hybrid Selenium Java Framework

## Table of Contents
1. [Class Hierarchy Overview](#class-hierarchy-overview)
2. [Detailed Class Definitions](#detailed-class-definitions)
3. [Dependency Matrix](#dependency-matrix)
4. [Interface Specifications](#interface-specifications)
5. [Design Patterns Used](#design-patterns-used)

---

## Class Hierarchy Overview

### Core Inheritance Tree

```
Object
├── ConfigManager (Singleton)
├── DriverManager (Singleton, ThreadLocal)
├── ReportManager (Singleton)
├── WaitHelper (Utility)
├── BasePage (Abstract)
│   ├── LoginPage
│   ├── RegisterPage
│   ├── ProductListPage
│   ├── ProductDetailPage
│   ├── CartPage
│   ├── CheckoutPage
│   ├── ProfilePage
│   └── [Other Page Classes]
├── BaseTest (Abstract)
│   ├── LoginTests
│   ├── ProductTests
│   ├── CartTests
│   └── [Other Test Classes]
├── BaseActions (Abstract)
│   ├── AuthActions
│   ├── ProductActions
│   ├── CartActions
│   ├── SearchActions
│   └── CommonActions
├── BaseAPI (Abstract)
│   ├── LoginAPITests
│   ├── UserAPITests
│   ├── ProductAPITests
│   ├── OrderAPITests
│   └── [Other API Test Classes]
└── [Utility Classes]
    ├── FileUtils
    ├── DateTimeUtils
    ├── RandomDataGenerator
    └── [Others]
```

---

## Detailed Class Definitions

### Configuration & Management Layer

#### 1. ConfigManager (Singleton)
```
Class: ConfigManager
Package: com.automation.framework.config
Extends: Object
Implements: None
Scope: Singleton

Responsibilities:
  - Load application properties
  - Manage environment configuration
  - Provide centralized config access
  - Cache configuration values

Key Methods:
  + getInstance(): ConfigManager
  + loadConfig(environment: EnvironmentConfig): void
  + getProperty(key: String): String
  + getURL(environment: String): String
  + getTimeout(): long
  + isHeadless(): boolean
  + getBrowser(): BrowserType

Dependencies:
  - PropertyReader
  - EnvironmentConfig
  - FrameworkConstants

Thread Safety: Yes (Synchronized)
Caching: Yes (Properties cached in memory)
```

#### 2. PropertyReader (Utility)
```
Class: PropertyReader
Package: com.automation.framework.config
Extends: Object
Implements: None
Scope: Utility (Static methods)

Responsibilities:
  - Read properties from .properties files
  - Parse property files
  - Handle property not found exceptions
  - Support default values

Key Methods:
  + readProperty(filePath: String, key: String): String
  + readProperty(filePath: String, key: String, defaultValue: String): String
  + readAllProperties(filePath: String): Properties
  + writeProperty(filePath: String, key: String, value: String): void

Dependencies:
  - FileUtils
  - FrameworkConstants

Thread Safety: Yes (Immutable operations)
```

#### 3. EnvironmentConfig (Enum)
```
Enum: EnvironmentConfig
Package: com.automation.framework.config
Values:
  - DEV
  - STAGING
  - PRODUCTION
  - LOCAL

Methods:
  + getPropertyFile(): String
  + getBaseURL(): String
  + isSSL(): boolean

Usage:
  EnvironmentConfig env = EnvironmentConfig.STAGING;
```

#### 4. FrameworkConstants (Utility)
```
Class: FrameworkConstants
Package: com.automation.framework.config.constants
Extends: Object
Implements: None
Scope: Utility (Static constants)

Constants:
  // Timeouts
  + public static final long IMPLICIT_WAIT = 10;
  + public static final long EXPLICIT_WAIT = 15;
  + public static final long PAGE_LOAD_TIMEOUT = 30;
  
  // Paths
  + public static final String PROJECT_PATH = System.getProperty("user.dir");
  + public static final String RESOURCES_PATH = PROJECT_PATH + "/src/test/resources";
  + public static final String REPORT_PATH = PROJECT_PATH + "/test-output/report";
  + public static final String SCREENSHOT_PATH = PROJECT_PATH + "/test-output/screenshots";
  
  // Browser
  + public static final String CHROME_DRIVER = "webdriver.chrome.driver";
  + public static final String FIREFOX_DRIVER = "webdriver.firefox.driver";
  
  // Logging
  + public static final String LOG_FILE_PATH = PROJECT_PATH + "/logs";
```

---

### Driver Management Layer

#### 5. DriverManager (Singleton with ThreadLocal)
```
Class: DriverManager
Package: com.automation.framework.driver
Extends: Object
Implements: None
Scope: Singleton (ThreadLocal<WebDriver>)

Responsibilities:
  - Manage WebDriver lifecycle per thread
  - Initialize browser instances
  - Handle driver configuration
  - Support parallel execution

Key Methods:
  + getInstance(): DriverManager
  + getDriver(): WebDriver
  + initializeDriver(browser: BrowserType): void
  + quitDriver(): void
  + quitAllDrivers(): void
  + isDriverInitialized(): boolean
  + addListener(listener: WebDriverListener): void

Properties:
  - ThreadLocal<WebDriver> driverThreadLocal
  - WebDriverListener listener

Thread Safety: Yes (ThreadLocal implementation)
Parallel Support: Yes (Per-thread isolation)
```

#### 6. DriverFactory (Factory Pattern)
```
Class: DriverFactory
Package: com.automation.framework.driver
Extends: Object
Implements: None
Scope: Utility (Static factory methods)

Responsibilities:
  - Create WebDriver instances
  - Apply browser-specific configurations
  - Handle driver initialization
  - Support multiple browsers

Key Methods:
  + createDriver(browser: BrowserType): WebDriver
  + createChromeDriver(): WebDriver
  + createFirefoxDriver(): WebDriver
  + createEdgeDriver(): WebDriver
  + addEventListener(driver: WebDriver): WebDriver

Dependencies:
  - CapabilityFactory
  - ChromeCapabilities
  - FirefoxCapabilities
  - EdgeCapabilities
  - WebDriverListener

Thread Safety: Yes
```

#### 7. WebDriverListener (Event Listener)
```
Class: WebDriverListener
Package: com.automation.framework.driver
Extends: Object
Implements: WebDriver.Listener (Selenium 4.0+)

Responsibilities:
  - Log all WebDriver actions
  - Capture screenshots on errors
  - Monitor performance metrics
  - Record actions for reporting

Key Methods:
  + beforeGet(url: String): void
  + afterGet(url: String): void
  + beforeClick(element: WebElement): void
  + afterClick(element: WebElement): void
  + beforeSendKeys(element: WebElement, keys: String): void
  + onException(throwable: Throwable): void

Dependencies:
  - ScreenshotUtils
  - ReportManager
  - SLF4J Logger

Thread Safety: Yes
```

#### 8. CapabilityFactory (Factory Pattern)
```
Class: CapabilityFactory
Package: com.automation.framework.driver.capabilities
Extends: Object
Implements: None
Scope: Utility

Responsibilities:
  - Create browser capabilities
  - Apply global settings
  - Configure headless mode
  - Handle proxy settings

Key Methods:
  + getCapabilities(browser: BrowserType): Capabilities
  + getChromCapabilities(): ChromeOptions
  + getFirefoxCapabilities(): FirefoxOptions
  + getEdgeCapabilities(): EdgeOptions
  + applyCommonCapabilities(options: MutableCapabilities): void

Dependencies:
  - ConfigManager
  - BrowserType
```

---

### Wait Strategies Layer

#### 9. WaitHelper (Utility)
```
Class: WaitHelper
Package: com.automation.framework.waits
Extends: Object
Implements: None
Scope: Reusable utility

Responsibilities:
  - Provide explicit wait wrappers
  - Create custom wait conditions
  - Handle wait timeouts
  - Support fluent API

Key Methods:
  + waitForElementPresent(locator: By): WebElement
  + waitForElementVisible(locator: By): WebElement
  + waitForElementClickable(locator: By): WebElement
  + waitForElementInvisible(locator: By): boolean
  + waitForElementText(locator: By, text: String): boolean
  + waitForCondition(condition: ExpectedCondition<T>): T
  + fluentWait(locator: By): WebElement

Constructor:
  + WaitHelper(driver: WebDriver)
  + WaitHelper(driver: WebDriver, timeout: long)

Dependencies:
  - WebDriver
  - ExpectedConditions
  - Selenium 4.0+ WebDriverWait

Thread Safety: No (Instance-based)
```

#### 10. CustomWait (Utility)
```
Class: CustomWait
Package: com.automation.framework.waits
Extends: Object
Implements: None
Scope: Utility

Key Methods:
  + waitUntilElementStateChange(locator: By): void
  + waitForAttributeValue(locator: By, attribute: String, value: String): boolean
  + waitForNumberOfElements(locator: By, count: int): boolean
  + waitForPageLoadComplete(): void
  + waitForAjaxComplete(): void
  + waitForJQueryComplete(): void
  + waitForCustomCondition(condition: Function<WebDriver, Boolean>): boolean

Thread Safety: No
```

---

### Base Classes

#### 11. BasePage (Abstract)
```
Abstract Class: BasePage
Package: com.automation.framework.base
Extends: Object
Implements: None
Scope: Abstract base class for all page objects

Responsibilities:
  - Provide common page operations
  - Manage element interactions
  - Handle waits and timeouts
  - Support logging and reporting

Key Methods:
  # protected constructor(driver: WebDriver)
  + findElement(locator: By): WebElement
  + findElements(locator: By): List<WebElement>
  + clickElement(locator: By): void
  + fillText(locator: By, text: String): void
  + getElementText(locator: By): String
  + isElementDisplayed(locator: By): boolean
  + isElementEnabled(locator: By): boolean
  + hoverOverElement(locator: By): void
  + switchToFrame(frameLocator: By): void
  + switchToDefaultContent(): void
  + waitForElement(locator: By): WebElement
  + scrollToElement(locator: By): void
  + takeScreenshot(name: String): void

Protected Fields:
  # protected WebDriver driver
  # protected WaitHelper waitHelper
  # protected Logger logger
  # protected AllureReportHelper reportHelper

Subclasses:
  - LoginPage
  - RegisterPage
  - ProductListPage
  - ProductDetailPage
  - CartPage
  - CheckoutPage
  - ProfilePage
  - (All page object classes)

Dependencies:
  - WebDriver
  - WaitHelper
  - AllureReportHelper
  - ScreenshotUtils
```

#### 12. BaseTest (Abstract)
```
Abstract Class: BaseTest
Package: com.automation.framework.base
Extends: Object
Implements: None
Scope: Abstract base class for all test classes

Responsibilities:
  - Setup/teardown test environment
  - Initialize driver
  - Configure logging
  - Setup reporting
  - Handle test listeners

Key Methods:
  + setUp(): void (Before each test)
  + tearDown(): void (After each test)
  + setupDriver(browser: BrowserType): void
  + setupDriver(): void (Uses default browser)
  + getDriver(): WebDriver
  + getLogger(): Logger
  + recordStep(description: String): void
  + recordStep(description: String, attachments: String...): void
  + takeScreenshot(name: String): void
  + fail(message: String): void
  + pass(message: String): void

Annotations:
  - @BeforeMethod
  - @AfterMethod
  - @Parameters
  - @Listeners

Dependencies:
  - DriverManager
  - ReportManager
  - ConfigManager
  - TestNG
```

#### 13. BaseActions (Abstract)
```
Abstract Class: BaseActions
Package: com.automation.framework.base
Extends: Object
Implements: None
Scope: Abstract base for all action classes

Responsibilities:
  - Provide reusable business logic
  - Encapsulate common workflows
  - Support action chaining
  - Handle complex interactions

Key Methods:
  # protected constructor(driver: WebDriver)
  + getDriver(): WebDriver
  + getWaitHelper(): WaitHelper
  + log(message: String): void
  + reportStep(description: String, status: boolean): void
  + handleError(error: Throwable): void

Protected Fields:
  # protected WebDriver driver
  # protected WaitHelper waitHelper
  # protected Logger logger

Subclasses:
  - AuthActions
  - ProductActions
  - CartActions
  - SearchActions
  - CommonActions

Dependencies:
  - WebDriver
  - WaitHelper
  - Logger
  - Report utilities
```

#### 14. BaseAPI (Abstract)
```
Abstract Class: BaseAPI
Package: com.automation.framework.base
Extends: Object
Implements: None
Scope: Abstract base for API tests

Responsibilities:
  - Setup REST API testing
  - Manage authentication tokens
  - Configure request/response specs
  - Handle API assertions

Key Methods:
  + setUp(): void
  + tearDown(): void
  + getRequestSpec(): RequestSpecification
  + getResponseSpec(expectedStatus: int): ResponseSpecification
  + extractToken(response: Response): String
  + setAuthToken(token: String): void
  + logRequest(requestSpec: RequestSpecification): void
  + logResponse(response: Response): void
  + attachResponseToReport(response: Response): void

Protected Fields:
  # protected RequestSpecification requestSpec
  # protected ResponseSpecification responseSpec
  # protected String baseURI
  # protected String authToken
  # protected Logger logger

Subclasses:
  - LoginAPITests
  - UserAPITests
  - ProductAPITests
  - OrderAPITests
  - (All API test classes)

Dependencies:
  - RestAssured
  - RequestSpecBuilder
  - ResponseSpecBuilder
  - APIClient
```

---

### Page Object Model Layer

#### 15. LoginPage (Concrete)
```
Class: LoginPage
Package: com.automation.framework.pages.auth
Extends: BasePage
Implements: None

Locators (Private):
  - By usernameField
  - By passwordField
  - By loginButton
  - By forgotPasswordLink
  - By errorMessage
  - By registerLink

Methods:
  + LoginPage(driver: WebDriver)
  + enterUsername(username: String): void
  + enterPassword(password: String): void
  + clickLoginButton(): void
  + clickForgotPassword(): void
  + clickRegister(): void
  + getErrorMessage(): String
  + isErrorMessageDisplayed(): boolean
  + login(username: String, password: String): HomePage
  + verifyPageTitle(): void
  + verifyPageElements(): void

Dependencies:
  - BasePage
  - HomePage (for return type)
  - WaitHelper
```

#### 16. RegisterPage (Concrete)
```
Class: RegisterPage
Package: com.automation.framework.pages.auth
Extends: BasePage

Locators (Private):
  - By firstNameField
  - By lastNameField
  - By emailField
  - By passwordField
  - By confirmPasswordField
  - By registerButton
  - By loginLink
  - By errorMessage
  - By successMessage

Methods:
  + RegisterPage(driver: WebDriver)
  + fillRegistrationForm(userData: UserTestData): void
  + clickRegisterButton(): void
  + getErrorMessage(): String
  + getSuccessMessage(): String
  + register(userData: UserTestData): HomePage
  + verifyPageTitle(): void

Dependencies:
  - BasePage
  - UserTestData
  - HomePage
```

#### 17-30. [Other Page Classes]
```
ProductListPage, ProductDetailPage, CartPage, CheckoutPage,
ProfilePage, SearchPage, CommonPage, NavigationBar, etc.
```

---

### Action Classes Layer

#### 31. AuthActions (Concrete)
```
Class: AuthActions
Package: com.automation.framework.actions
Extends: BaseActions

Methods:
  + AuthActions(driver: WebDriver)
  + login(username: String, password: String): void
  + loginWithValidCredentials(userData: UserTestData): void
  + loginWithInvalidCredentials(username: String, password: String): void
  + logout(): void
  + resetPassword(email: String): void
  + register(userData: UserTestData): void
  + verifyLoginSuccess(): void
  + verifyLoginFailure(expectedError: String): void

Dependencies:
  - BaseActions
  - LoginPage
  - HomePage
  - UserTestData
  - WaitHelper
```

#### 32. ProductActions (Concrete)
```
Class: ProductActions
Package: com.automation.framework.actions
Extends: BaseActions

Methods:
  + ProductActions(driver: WebDriver)
  + searchProduct(productName: String): void
  + filterByCategory(category: String): void
  + filterByPrice(minPrice: double, maxPrice: double): void
  + sortByPrice(ascending: boolean): void
  + sortByRating(): void
  + viewProductDetails(productName: String): void
  + verifyProductDisplayed(productName: String): void
  + addProductToCart(productName: String, quantity: int): void

Dependencies:
  - BaseActions
  - SearchPage
  - ProductListPage
  - ProductDetailPage
```

#### 33. CartActions (Concrete)
```
Class: CartActions
Package: com.automation.framework.actions
Extends: BaseActions

Methods:
  + CartActions(driver: WebDriver)
  + addToCart(productName: String): void
  + removeFromCart(productName: String): void
  + updateQuantity(productName: String, quantity: int): void
  + clearCart(): void
  + verifyItemInCart(productName: String): void
  + verifyCartTotal(expectedTotal: double): void
  + proceedToCheckout(): void

Dependencies:
  - BaseActions
  - CartPage
  - CheckoutPage
```

#### 34. CommonActions (Concrete)
```
Class: CommonActions
Package: com.automation.framework.actions
Extends: BaseActions

Methods:
  + CommonActions(driver: WebDriver)
  + navigateTo(url: String): void
  + refreshPage(): void
  + goBack(): void
  + switchToNewTab(): void
  + switchToWindow(windowIndex: int): void
  + closeCurrentWindow(): void
  + acceptAlert(): void
  + dismissAlert(): void
  + getAlertText(): String

Dependencies:
  - BaseActions
  - WaitHelper
```

#### 35. SearchActions (Concrete)
```
Class: SearchActions
Package: com.automation.framework.actions
Extends: BaseActions

Methods:
  + SearchActions(driver: WebDriver)
  + searchByKeyword(keyword: String): void
  + searchByCategory(category: String): void
  + verifySearchResults(expectedResults: int): void
  + selectSearchResult(index: int): void
  + clearSearchFilter(): void

Dependencies:
  - BaseActions
  - SearchPage
```

---

### API Testing Layer

#### 36. APIClient (Utility)
```
Class: APIClient
Package: com.automation.framework.api.helpers
Extends: Object

Key Methods:
  + setBaseURI(uri: String): void
  + setBasePath(path: String): void
  + addHeader(key: String, value: String): void
  + addQueryParam(key: String, value: String): void
  + addPathParam(key: String, value: String): void
  + setRequestBody(body: Object): void
  + setContentType(type: String): void
  + setAuth(username: String, password: String): void
  + setBearerToken(token: String): void

Methods returning Response:
  + get(endpoint: String): Response
  + post(endpoint: String, body: Object): Response
  + put(endpoint: String, body: Object): Response
  + patch(endpoint: String, body: Object): Response
  + delete(endpoint: String): Response

Dependencies:
  - RestAssured
  - RequestSpecification
  - Response
```

#### 37. APIHelper (Utility)
```
Class: APIHelper
Package: com.automation.framework.api.helpers
Extends: Object

Key Methods:
  + extractValue(response: Response, jsonPath: String): Object
  + extractValueAsString(response: Response, jsonPath: String): String
  + extractValueAsInt(response: Response, jsonPath: String): int
  + extractValueAsList(response: Response, jsonPath: String): List<Object>
  + parseResponse(response: Response): Map<String, Object>
  + validateStatusCode(response: Response, expectedCode: int): void
  + validateResponseTime(response: Response, maxTime: long): void

Dependencies:
  - RestAssured
  - JsonPath
```

#### 38. AuthTokenManager (Utility)
```
Class: AuthTokenManager
Package: com.automation.framework.api.helpers
Extends: Object
Implements: Singleton

Key Methods:
  + getInstance(): AuthTokenManager
  + setToken(token: String): void
  + getToken(): String
  + refreshToken(refreshToken: String): void
  + isTokenValid(): boolean
  + clearToken(): void
  + setTokenExpiry(expiry: LocalDateTime): void

Thread Safety: Yes (Synchronized)
```

#### 39-50. [API Test Classes & Models]
```
LoginAPITests, UserAPITests, ProductAPITests, OrderAPITests
LoginRequest, UserRequest, OrderRequest
LoginResponse, UserResponse, OrderResponse
```

---

### Data Handling Layer

#### 51. DataReader (Interface)
```
Interface: DataReader
Package: com.automation.framework.data.readers

Methods:
  + readTestData(testCaseName: String): TestData
  + readTestData(filePath: String, testCaseName: String): TestData
  + readAllTestData(): List<TestData>
  + readTestData(filePath: String): List<TestData>
```

#### 52. JSONDataReader (Concrete)
```
Class: JSONDataReader
Package: com.automation.framework.data.readers
Extends: Object
Implements: DataReader

Methods:
  + JSONDataReader(filePath: String)
  + readTestData(testCaseName: String): TestData
  + readTestData(filePath: String, testCaseName: String): TestData
  + readAllTestData(): List<TestData>

Dependencies:
  - Gson or Jackson
  - FileUtils
  - TestData model
```

#### 53. ExcelDataReader (Concrete)
```
Class: ExcelDataReader
Package: com.automation.framework.data.readers
Extends: Object
Implements: DataReader

Methods:
  + ExcelDataReader(filePath: String)
  + ExcelDataReader(filePath: String, sheetName: String)
  + readTestData(testCaseName: String): TestData
  + readTestData(rowNumber: int): TestData
  + readAllTestData(): List<TestData>

Dependencies:
  - Apache POI
  - FileUtils
```

#### 54. CSVDataReader (Concrete)
```
Class: CSVDataReader
Package: com.automation.framework.data.readers
Extends: Object
Implements: DataReader

Methods:
  + CSVDataReader(filePath: String)
  + readTestData(testCaseName: String): TestData
  + readAllTestData(): List<TestData>

Dependencies:
  - Apache Commons CSV
  - FileUtils
```

#### 55. TestDataBuilder (Utility)
```
Class: TestDataBuilder
Package: com.automation.framework.data.builders
Extends: Object

Key Methods:
  + TestDataBuilder()
  + withUsername(username: String): TestDataBuilder
  + withPassword(password: String): TestDataBuilder
  + withEmail(email: String): TestDataBuilder
  + withFirstName(firstName: String): TestDataBuilder
  + withLastName(lastName: String): TestDataBuilder
  + build(): TestData

Pattern: Builder pattern with fluent API
```

---

### AI Integration Layer

#### 56. OllamaAIHelper (Utility)
```
Class: OllamaAIHelper
Package: com.automation.framework.ai
Extends: Object
Implements: Singleton

Responsibilities:
  - Communicate with Ollama LLM
  - Send prompts and receive responses
  - Handle model configuration
  - Manage AI requests/responses

Key Methods:
  + getInstance(): OllamaAIHelper
  + sendPrompt(prompt: String): String
  + sendPrompt(prompt: String, model: String): String
  + generateTestData(requirement: String): String
  + analyzeError(errorMessage: String, stackTrace: String): String
  + suggestElementLocator(pageDescription: String): String
  + setBaseURL(url: String): void
  + setModel(model: String): void

Dependencies:
  - HttpClient
  - OllamaRequest
  - OllamaResponse
  - Gson/Jackson

Thread Safety: Yes (Synchronized)
```

#### 57. SelfHealingHelper (Utility)
```
Class: SelfHealingHelper
Package: com.automation.framework.ai
Extends: Object

Key Methods:
  + findElementWithHealing(originalLocator: By, 
                           pageDescription: String): WebElement
  + suggestAlternativeLocator(element: String): By
  + validateLocatorWithAI(locator: By): boolean
  + healLocatorForPage(pageName: String, elementName: String): By

Dependencies:
  - OllamaAIHelper
  - WebDriver
  - By (Selenium locator)
```

#### 58. TestDataGeneratorAI (Utility)
```
Class: TestDataGeneratorAI
Package: com.automation.framework.ai
Extends: Object

Key Methods:
  + generateTestUser(): UserTestData
  + generateTestProduct(): ProductTestData
  + generateTestOrder(): OrderRequest
  + generateEdgeCaseData(scenario: String): TestData
  + generateValidCredentials(count: int): List<UserTestData>

Dependencies:
  - OllamaAIHelper
  - TestData models
```

#### 59. FailureAnalyzerAI (Utility)
```
Class: FailureAnalyzerAI
Package: com.automation.framework.ai
Extends: Object

Key Methods:
  + analyzeTestFailure(testName: String, 
                       error: Throwable): String
  + suggestRootCause(screenshot: File, 
                     errorMessage: String): String
  + recommendFixAction(error: String): String

Dependencies:
  - OllamaAIHelper
  - ScreenshotUtils
```

---

### Utilities Layer

#### 60. FileUtils (Utility)
```
Class: FileUtils
Package: com.automation.framework.utilities
Extends: Object

Key Methods:
  + readFile(filePath: String): String
  + readFileAsBytes(filePath: String): byte[]
  + writeFile(filePath: String, content: String): void
  + appendToFile(filePath: String, content: String): void
  + deleteFile(filePath: String): boolean
  + createFile(filePath: String): File
  + createDirectory(dirPath: String): boolean
  + fileExists(filePath: String): boolean
  + copyFile(source: String, destination: String): void
  + deleteDirectory(dirPath: String): boolean

Thread Safety: No (File I/O operations)
```

#### 61. RandomDataGenerator (Utility)
```
Class: RandomDataGenerator
Package: com.automation.framework.utilities
Extends: Object

Key Methods:
  + generateRandomEmail(): String
  + generateRandomString(length: int): String
  + generateRandomNumber(min: int, max: int): int
  + generateRandomPhone(): String
  + generateRandomDate(): LocalDate
  + generateRandomUUID(): String
  + generateRandomAlphanumeric(length: int): String

Thread Safety: Yes (No mutable state)
```

#### 62. DateTimeUtils (Utility)
```
Class: DateTimeUtils
Package: com.automation.framework.utilities
Extends: Object

Key Methods:
  + getCurrentDate(): LocalDate
  + getCurrentTime(): LocalDateTime
  + formatDate(date: LocalDate, format: String): String
  + parseDate(dateString: String, format: String): LocalDate
  + addDays(date: LocalDate, days: int): LocalDate
  + getDaysBetween(date1: LocalDate, date2: LocalDate): long
  + getCurrentTimestamp(): long

Thread Safety: Yes (Immutable operations)
```

#### 63. ScreenshotUtils (Utility)
```
Class: ScreenshotUtils
Package: com.automation.framework.utilities
Extends: Object

Key Methods:
  + takeScreenshot(driver: WebDriver, fileName: String): File
  + takeScreenshotAsBase64(driver: WebDriver): String
  + annotateScreenshot(screenshot: File, annotation: String): File
  + compareScreenshots(expected: File, actual: File): boolean
  + highlightElement(driver: WebDriver, element: WebElement): void

Dependencies:
  - WebDriver
  - FileUtils
  - Allure
```

#### 64. EncryptionUtils (Utility)
```
Class: EncryptionUtils
Package: com.automation.framework.utilities
Extends: Object

Key Methods:
  + encrypt(data: String): String
  + decrypt(encryptedData: String): String
  + encryptPassword(password: String): String
  + verifyPassword(password: String, hash: String): boolean
  + generateHash(data: String): String

Dependencies:
  - Apache Commons Codec
```

#### 65. EmailValidator (Utility)
```
Class: EmailValidator
Package: com.automation.framework.utilities
Extends: Object

Key Methods:
  + isValidEmail(email: String): boolean
  + extractDomain(email: String): String
  + isValidDomain(domain: String): boolean
  + normalizeEmail(email: String): String

Thread Safety: Yes (Stateless)
```

---

### Listeners & Retry Layer

#### 66. TestListeners (Listener Implementation)
```
Class: TestListeners
Package: com.automation.framework.listeners
Extends: Object
Implements: ITestListener

Methods:
  + onTestStart(result: ITestResult): void
  + onTestSuccess(result: ITestResult): void
  + onTestFailure(result: ITestResult): void
  + onTestSkipped(result: ITestResult): void
  + onFinish(context: ITestContext): void

Responsibilities:
  - Log test execution events
  - Capture screenshots on failure
  - Manage driver lifecycle
  - Update test reports

Dependencies:
  - TestNG ITestListener
  - ScreenshotUtils
  - ReportManager
```

#### 67. RetryAnalyzer (Retry Implementation)
```
Class: RetryAnalyzer
Package: com.automation.framework.retry
Extends: Object
Implements: IRetryAnalyzer

Key Methods:
  + retry(result: ITestResult): boolean
  + getMaxRetryCount(): int
  + setMaxRetryCount(count: int): void

Configuration:
  - Max retry attempts: 2
  - Flaky test list (configurable)

Dependencies:
  - TestNG IRetryAnalyzer
```

---

### Reporting Layer

#### 68. ReportManager (Singleton)
```
Class: ReportManager
Package: com.automation.framework.reporting
Extends: Object
Implements: Singleton

Key Methods:
  + getInstance(): ReportManager
  + addStep(description: String): void
  + addStep(description: String, status: String): void
  + addAttachment(name: String, attachmentData: String): void
  + addScreenshot(name: String, screenshotPath: String): void
  + log(message: String): void
  + log(message: String, level: LogLevel): void
  + generateReport(): void

Thread Safety: Yes (Synchronized)
```

#### 69. AllureReportHelper (Utility)
```
Class: AllureReportHelper
Package: com.automation.framework.reporting
Extends: Object

Key Methods:
  + step(description: String, runnable: Runnable): void
  + addStep(description: String): void
  + addAttachment(name: String, content: String, mimeType: String): void
  + addScreenshot(name: String, image: byte[]): void
  + addFeature(featureName: String): void
  + addStory(storyName: String): void
  + addSeverity(severity: String): void
  + addTag(tag: String): void

Dependencies:
  - Allure framework
  - SLF4J
```

---

### Exception Layer

#### 70-73. Custom Exceptions
```
Class: FrameworkException
Package: com.automation.framework.exceptions
Extends: RuntimeException

Class: ElementNotFoundException
Extends: FrameworkException

Class: APIException
Extends: FrameworkException

Class: ConfigurationException
Extends: FrameworkException
```

---

## Dependency Matrix

### High-Level Dependencies

```
┌─────────────────────────────────────────────────────────────────┐
│                    Test Classes (70-100)                         │
└──────────────────────────┬──────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────────┐
│  BaseTest + BaseAPI (Setup/Teardown, Driver Initialization)     │
└────┬──────────────┬──────────────────────┬─────────────────────┘
     ↓              ↓                      ↓
┌──────────┐  ┌──────────┐  ┌─────────────────────────┐
│ Pages    │  │ Actions  │  │ APIHelper + APIClient   │
│ (POM)    │  │          │  │                         │
└──┬───────┘  └──┬───────┘  └──────────┬──────────────┘
   ↓             ↓                     ↓
   └─────────────┼─────────────────────┘
                 ↓
         ┌──────────────────┐
         │ DriverManager    │
         │ + WaitHelper     │
         └────┬─────────────┘
              ↓
         ┌──────────────────┐
         │ ConfigManager    │
         │ + PropertyReader │
         └──────────────────┘
```

### Module Dependencies

```
Test Execution
    ├── BaseTest/BaseAPI
    │   ├── DriverManager (ThreadLocal)
    │   ├── ConfigManager
    │   └── ReportManager
    │
    ├── Page Objects
    │   ├── BasePage
    │   ├── WaitHelper
    │   └── ScreenshotUtils
    │
    ├── Actions
    │   ├── BaseActions
    │   ├── Page Classes
    │   └── WaitHelper
    │
    ├── API Tests
    │   ├── BaseAPI
    │   ├── APIClient
    │   ├── APIHelper
    │   └── Model Classes
    │
    ├── Data Layer
    │   ├── DataReader interface
    │   ├── JSONDataReader
    │   ├── ExcelDataReader
    │   └── TestDataBuilder
    │
    ├── AI Layer
    │   ├── OllamaAIHelper
    │   ├── SelfHealingHelper
    │   ├── TestDataGeneratorAI
    │   └── FailureAnalyzerAI
    │
    ├── Utilities
    │   ├── FileUtils
    │   ├── RandomDataGenerator
    │   ├── DateTimeUtils
    │   ├── ScreenshotUtils
    │   ├── EncryptionUtils
    │   └── EmailValidator
    │
    ├── Listeners
    │   ├── TestListeners
    │   ├── RetryAnalyzer
    │   └── ScreenshotListener
    │
    └── Reporting
        ├── ReportManager
        ├── AllureReportHelper
        └── ExtentReportHelper
```

---

## Interface Specifications

### 1. IDataReader Interface
```java
public interface IDataReader {
    TestData readTestData(String testCaseName);
    TestData readTestData(String filePath, String testCaseName);
    List<TestData> readAllTestData();
    List<TestData> readTestData(String filePath);
}
```

### 2. IWaitStrategy Interface
```java
public interface IWaitStrategy {
    WebElement waitForElement(By locator, long timeout);
    boolean waitForCondition(Function<WebDriver, Boolean> condition, long timeout);
    void wait(long milliseconds);
}
```

### 3. IElementLocator Interface
```java
public interface IElementLocator {
    WebElement findElement(By locator);
    List<WebElement> findElements(By locator);
    boolean isElementPresent(By locator);
}
```

### 4. IAPIClient Interface
```java
public interface IAPIClient {
    Response get(String endpoint);
    Response post(String endpoint, Object body);
    Response put(String endpoint, Object body);
    Response delete(String endpoint);
    void setBaseURI(String uri);
}
```

---

## Design Patterns Used

### 1. Singleton Pattern
```
ConfigManager
  ├── getInstance()
  └── Private constructor

DriverManager
  ├── getInstance()
  ├── ThreadLocal<WebDriver>
  └── Private constructor

ReportManager
  ├── getInstance()
  └── Private constructor
```

### 2. Factory Pattern
```
DriverFactory
  ├── createDriver(BrowserType)
  ├── createChromeDriver()
  ├── createFirefoxDriver()
  └── createEdgeDriver()

CapabilityFactory
  ├── getCapabilities(BrowserType)
  └── applyCommonCapabilities()

WaitFactory
  ├── createWait(timeout)
  └── createFluentWait()
```

### 3. Template Method Pattern
```
BasePage
  ├── click(By)
  ├── fill(By, String)
  └── waitForElement(By) [uses WaitHelper]

BaseTest
  ├── setUp() [initializes driver]
  ├── tearDown() [quits driver]
  └── getDriver()

BaseActions
  ├── log(String)
  └── reportStep()

BaseAPI
  ├── setUp()
  ├── tearDown()
  └── getRequestSpec()
```

### 4. Builder Pattern
```
TestDataBuilder
  ├── withUsername()
  ├── withEmail()
  ├── withPassword()
  └── build()

RequestSpecBuilder
  ├── setBaseURI()
  ├── addHeader()
  └── build()

ResponseSpecBuilder
  ├── expectStatus()
  ├── expectContentType()
  └── build()
```

### 5. Strategy Pattern
```
DataReader (Interface)
  ├── JSONDataReader
  ├── ExcelDataReader
  ├── CSVDataReader
  └── DatabaseDataReader

WaitStrategy
  ├── ImplicitWait
  ├── ExplicitWait
  └── FluentWait
```

### 6. Listener Pattern
```
ITestListener (TestNG)
  └── TestListeners

WebDriver.Listener
  └── WebDriverListener

IRetryAnalyzer
  └── RetryAnalyzer
```

### 7. Utility/Helper Pattern
```
WaitHelper
FileUtils
RandomDataGenerator
DateTimeUtils
ScreenshotUtils
EncryptionUtils
EmailValidator
```

---

**Total Classes: 70+**
**Total Interfaces: 5+**
**Design Patterns: 7**
**Thread Safety: ThreadLocal for Driver Management**
**Parallel Execution: Fully Supported**

