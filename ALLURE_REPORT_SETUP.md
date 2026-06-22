# Allure Report Setup & Configuration Guide

## Overview
This guide explains how to setup, generate, and view beautiful Allure reports for your test automation framework.

## What is Allure Report?
Allure is a flexible, lightweight multi-language test report tool that provides clear graphical representation of what has been tested and allows everyone involved in the development process to extract the maximum of information from the everyday execution of tests.

### Key Features
- Beautiful visual reports
- Allure TestNG integration
- Screenshot attachments
- Test categorization (Features, Stories, Severity)
- Failure analysis
- Historical trends
- Defect linking
- Customizable reports

## Prerequisites

### Required Software
1. **Java 11+** - Already installed
2. **Maven 3.6+** - Already configured
3. **Allure CLI** (Optional but recommended)

### Install Allure CLI

#### Windows (using Scoop or Manual)
```bash
# Using Scoop (if installed)
scoop install allure

# Or download from: https://github.com/allure-framework/allure2/releases
# Add to PATH
```

#### macOS (using Homebrew)
```bash
brew install allure
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt-add-repository ppa:qameta/allure
sudo apt-get update
sudo apt-get install allure2
```

## Configuration in pom.xml

The framework already includes Allure dependencies:

```xml
<!-- Allure Report -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>${allure.version}</version>
</dependency>
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-rest-assured</artifactId>
    <version>${allure.version}</version>
</dependency>

<!-- Allure Maven Plugin -->
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.11.2</version>
</plugin>
```

## Generating Allure Reports

### Method 1: Using Maven Allure Plugin (Recommended)

#### Step 1: Run Tests
```bash
mvn clean test
```

This generates test results in:
```
target/allure-results/
```

#### Step 2: Generate Report
```bash
mvn allure:report
```

Report is generated in:
```
target/site/allure-report/
```

#### Step 3: View Report
```bash
mvn allure:serve
```

This command:
- Generates the report
- Opens it in your default browser
- Continues serving until you stop the process (Ctrl+C)

### Method 2: Using Allure CLI

```bash
# Generate report from results
allure generate target/allure-results --clean -o target/allure-report

# Serve the report
allure serve target/allure-results
```

### Method 3: Open Generated Report in Browser

```bash
# Windows
start target/site/allure-report/index.html

# macOS
open target/site/allure-report/index.html

# Linux
xdg-open target/site/allure-report/index.html
```

## Complete Report Generation Workflow

### Full Workflow Commands
```bash
# Clean, test, generate report in one command
mvn clean test allure:report

# Or with environment-specific testing
mvn clean test -Denvironment=staging allure:report

# Then view the report
mvn allure:serve
```

### Recommended Workflow
```bash
# 1. Clean previous test results
mvn clean

# 2. Run tests
mvn test -Denvironment=local

# 3. Generate Allure report
mvn allure:report

# 4. View report interactively
mvn allure:serve
```

## Allure Report Structure

### Main Dashboard
The report opens to the Overview/Dashboard page showing:
- **Test Execution Summary** - Total, passed, failed, skipped
- **Pass Rate** - Pie chart of test results
- **Flaky Tests** - Tests that fail intermittently
- **Timeline** - Test duration visualization
- **Environment** - Browser and OS information

### Navigation Tabs

#### 1. **Overview**
- Summary statistics
- Pass rate and timeline
- Environment information
- Total test count

#### 2. **Categories**
- **By Feature** - Group tests by @Feature annotation
- **By Story** - Group tests by @Story annotation
- **By Severity** - Critical, High, Medium, Low
- **By Status** - Passed, Failed, Skipped

#### 3. **Suites**
- Complete test hierarchy
- Class-level organization
- Method-level details
- Individual test results

#### 4. **Graphs**
- Test execution timeline
- Pie charts for pass/fail rates
- Test severity distribution
- Category breakdown

#### 5. **Timeline**
- Chronological test execution
- Actual test duration
- Status indicators

## Annotating Tests for Better Reports

### Feature and Story Annotations
```java
@Feature("Authentication")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Description("Test successful login with valid credentials")
@Test
public void testLoginWithValidCredentials() {
    // Test code
}
```

### Step Annotations
```java
@Step("Enter username: {username}")
public void enterUsername(String username) {
    type(USERNAME_FIELD, username);
}

@Step("Verify successful login")
public void verifyLogin() {
    Assert.assertTrue(isLoggedIn());
}
```

### Severity Levels
```java
SeverityLevel.BLOCKER     // Application completely broken
SeverityLevel.CRITICAL    // Feature broken, no workaround
SeverityLevel.MAJOR       // Feature partially broken
SeverityLevel.NORMAL      // Minor issue
SeverityLevel.MINOR       // Very minor issue
SeverityLevel.TRIVIAL     // Cosmetic issues
```

### Example Test with Annotations
```java
@Feature("Products")
@Story("Product Catalog")
public class ProductsTest extends BaseTest {
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify products are displayed on catalog")
    public void testProductsDisplayed() {
        Allure.step("Navigate to products page");
        navigateTo("/products");
        
        Allure.step("Wait for products to load");
        waitForSeconds(2);
        
        Allure.step("Verify products count");
        int count = productsPage.getTotalProductsCount();
        Assert.assertTrue(count > 0);
    }
}
```

## Attaching Screenshots to Reports

### Automatic Screenshot on Failure
Already configured in TestListener:
```java
@Override
public void onTestFailure(ITestResult result) {
    String testName = result.getMethod().getMethodName();
    ScreenshotHelper.captureScreenshot("FAILED_" + testName);
}
```

### Manual Screenshot Attachment
```java
// Capture and attach screenshot
Allure.addAttachment("Page Screenshot", 
    "image/png", 
    new FileInputStream("screenshot.png"), 
    "png");

// Using helper method
ScreenshotHelper.captureScreenshot("MyScreenshot");
```

## Attaching Logs to Reports

### Add Text Attachment
```java
Allure.addAttachment("Test Log", 
    "text/plain", 
    "Log content here", 
    "log");
```

### Add Video/Media
```java
Allure.addAttachment("Video", 
    "video/mp4", 
    new FileInputStream("video.mp4"), 
    "mp4");
```

## Customizing Allure Configuration

### Create allure.properties (Optional)
File: `src/test/resources/allure.properties`

```properties
# Allure configuration
allure.results.directory=target/allure-results
allure.link.issue.pattern=https://jira.example.com/browse/{}
allure.link.testcase.pattern=https://jira.example.com/browse/{}
```

### Create environment.properties (Optional)
File: `src/test/resources/environment.properties`

```properties
Browser=Chrome
Browser.Version=120.0
OS=Windows 11
OS.Version=22H2
URL=https://dev.example.com
Platform=Windows
Parallel=true
Threads=2
```

## Report Sections Explained

### Test Summary
```
Total Tests: 59
Passed: 52 (88%)
Failed: 5 (8%)
Skipped: 2 (3%)
```

### By Features
Shows tests grouped by @Feature annotation:
- Authentication (5 tests)
- Products (8 tests)
- Shopping Cart (5 tests)
- etc.

### By Severity
Shows test distribution:
- CRITICAL: 10 tests
- HIGH: 20 tests
- MEDIUM: 25 tests
- LOW: 4 tests

### Failed Tests Details
For each failed test:
- Failure message
- Stack trace
- Screenshots (if captured)
- Test steps
- Duration
- Environment info

## Interpreting the Report

### Pass Rate Metrics
- **High Pass Rate (>95%)** - Framework is stable
- **Medium Pass Rate (80-95%)** - Some flaky tests
- **Low Pass Rate (<80%)** - Critical issues to address

### Flaky Tests
Tests that pass and fail intermittently:
- Listed in "Flaky Tests" section
- Needs investigation
- May need increased wait times
- Consider retry mechanism

### Timeline Analysis
- Test execution order
- Duration per test
- Performance trends
- Bottleneck identification

## Common Issues & Solutions

### Issue: Report not generating
**Solution:**
```bash
# Clear Maven cache
mvn clean

# Run tests with verbose output
mvn clean test -X

# Check target/allure-results directory exists
```

### Issue: Screenshots not attached
**Solution:**
```java
// Ensure ScreenshotHelper is called in listener
@Override
public void onTestFailure(ITestResult result) {
    ScreenshotHelper.captureScreenshot(testName);
}
```

### Issue: Allure serve not opening browser
**Solution:**
```bash
# Manually open the report
# Windows
start target/site/allure-report/index.html

# Or use CLI
allure open target/allure-report
```

### Issue: Steps not showing in report
**Solution:**
```java
// Add @Step annotation to methods
@Step("Perform login")
public void login(String user, String pass) {
    // ...
}

// Or use Allure.step directly
Allure.step("Verify result");
```

## Advanced Report Features

### Linking to Issue Tracker
```java
@Issue("BUG-123")
@Link("https://jira.example.com/browse/BUG-123")
@Test
public void testBugFix() {
    // Test code
}
```

### Test Description
```java
@Description("""
    This test verifies that:
    1. User can login with valid credentials
    2. Dashboard loads successfully
    3. User profile is displayed
""")
@Test
public void testLogin() {
    // Test code
}
```

### Parametrized Tests
```java
@Test(dataProvider = "loginData")
@Description("Test login with various credentials")
public void testLogin(String email, String password) {
    // Test code
}
```

Reports show separate entries for each parameter combination.

## Integration with CI/CD

### GitHub Actions Example
```yaml
name: Test & Report
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
      - run: mvn allure:report
      - uses: actions/upload-artifact@v2
        with:
          name: allure-report
          path: target/site/allure-report/
```

### Jenkins Pipeline Example
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                sh 'mvn allure:report'
                publishHTML([
                    reportDir: 'target/site/allure-report',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report'
                ])
            }
        }
    }
}
```

## Report Sharing

### Share as Archive
```bash
# Create archive of report
# Windows
tar -a -c -f allure-report.zip target/site/allure-report/

# Unix/Linux
tar -czf allure-report.tar.gz target/site/allure-report/
```

### Share via Web Server
```bash
# Copy report to web server
cp -r target/site/allure-report/ /var/www/html/

# Access at: http://server/allure-report/
```

## Best Practices for Allure Reports

✅ **Always add @Feature and @Story annotations**
✅ **Use descriptive test names**
✅ **Add @Step annotations to important actions**
✅ **Capture screenshots on failure**
✅ **Use appropriate severity levels**
✅ **Attach logs and additional information**
✅ **Update report on each test run**
✅ **Review flaky tests regularly**
✅ **Track pass rate trends**
✅ **Share reports with team**

## Complete Report Generation Script

Create file: `generate-report.sh` (for Linux/macOS)

```bash
#!/bin/bash

echo "========== Allure Report Generation =========="
echo ""

# Step 1: Clean and run tests
echo "Step 1: Running tests..."
mvn clean test -Denvironment=local

# Check if tests passed
if [ $? -ne 0 ]; then
    echo "Tests failed. Generating report anyway..."
fi

# Step 2: Generate Allure report
echo "Step 2: Generating Allure report..."
mvn allure:report

# Step 3: Check if report generated successfully
if [ -f "target/site/allure-report/index.html" ]; then
    echo "Step 3: Report generated successfully!"
    echo ""
    echo "Opening report in browser..."
    open target/site/allure-report/index.html 2>/dev/null || \
    xdg-open target/site/allure-report/index.html 2>/dev/null || \
    echo "Please open: target/site/allure-report/index.html"
else
    echo "Error: Report not generated!"
    exit 1
fi

echo ""
echo "========== Report Generation Complete =========="
```

For Windows: `generate-report.bat`

```batch
@echo off
echo ========== Allure Report Generation ==========
echo.

REM Step 1: Run tests
echo Step 1: Running tests...
call mvn clean test -Denvironment=local

REM Step 2: Generate report
echo Step 2: Generating Allure report...
call mvn allure:report

REM Step 3: Open report
echo Step 3: Opening report...
start target/site/allure-report/index.html

echo.
echo ========== Report Generation Complete ==========
```

## Useful Commands Reference

```bash
# Full workflow
mvn clean test allure:report allure:serve

# Run specific tests and generate report
mvn clean test -Dtest=LoginTest allure:report

# Run on specific environment
mvn clean test -Denvironment=staging allure:report

# Generate report only (without tests)
mvn allure:report

# Serve existing report
mvn allure:serve

# View specific report
mvn allure:serve target/allure-results

# Clear Allure results
rm -rf target/allure-results

# Generate and open report immediately
mvn clean test allure:report && mvn allure:serve
```

## Troubleshooting Report Generation

### Clear Cache and Regenerate
```bash
mvn clean
rm -rf target/
mvn test
mvn allure:report
```

### Verify Allure Plugin Version
```bash
mvn allure:help
```

### Debug Report Generation
```bash
mvn allure:report -X
```

### Check Results Directory
```bash
# Should contain JSON result files
ls -la target/allure-results/
```

## Summary

Allure reports provide:
- ✅ Beautiful visual representation
- ✅ Detailed test execution logs
- ✅ Screenshot attachments
- ✅ Test categorization
- ✅ Historical trends
- ✅ Team collaboration
- ✅ Quality metrics
- ✅ Failure analysis

**All configured and ready to use!**

---

**Next Steps:**
1. Run tests: `mvn clean test`
2. Generate report: `mvn allure:report`
3. View report: `mvn allure:serve`
4. Share with team!
