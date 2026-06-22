#!/bin/bash

# Allure Report Generation Script
# Usage: ./generate-allure-report.sh [environment]
# Example: ./generate-allure-report.sh staging

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Get environment parameter (default: local)
ENVIRONMENT="${1:-local}"

# Get current date and time
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

echo ""
echo "========================================"
echo "  Hybrid Selenium Framework"
echo "  Allure Report Generation"
echo "========================================"
echo ""

echo -e "${BLUE}[INFO]${NC} Report generation started at: $TIMESTAMP"
echo ""

# Step 1: Verify Maven installation
echo -e "${BLUE}[STEP 1]${NC} Verifying Maven installation..."

if ! command -v mvn &> /dev/null; then
    echo -e "${RED}[ERROR]${NC} Maven is not installed"
    echo "Please install Maven first: https://maven.apache.org/install.html"
    exit 1
fi

echo -e "${GREEN}[SUCCESS]${NC} Maven found: $(mvn --version | head -1)"
echo ""

# Step 2: Verify Java installation
echo -e "${BLUE}[STEP 2]${NC} Verifying Java installation..."

if ! command -v java &> /dev/null; then
    echo -e "${RED}[ERROR]${NC} Java is not installed"
    echo "Please install Java 11 or higher"
    exit 1
fi

echo -e "${GREEN}[SUCCESS]${NC} Java found: $(java -version 2>&1 | head -1)"
echo ""

# Step 3: Display selected environment
echo -e "${BLUE}[STEP 3]${NC} Test Environment Configuration"
echo "  Environment: $ENVIRONMENT"
echo "  Config file: src/main/resources/config_${ENVIRONMENT}.properties"
echo ""

# Step 4: Clean previous builds
echo -e "${BLUE}[STEP 4]${NC} Cleaning previous test results..."

mvn clean --quiet

if [ $? -ne 0 ]; then
    echo -e "${RED}[ERROR]${NC} Maven clean failed"
    echo "Try running: mvn clean install"
    exit 1
fi

echo -e "${GREEN}[SUCCESS]${NC} Clean completed"
echo ""

# Step 5: Run tests
echo -e "${BLUE}[STEP 5]${NC} Running tests (environment: $ENVIRONMENT)..."
echo ""

mvn test -Denvironment=$ENVIRONMENT -q

TEST_RESULT=$?

if [ $TEST_RESULT -ne 0 ]; then
    echo -e "${YELLOW}[WARNING]${NC} Some tests may have failed"
    echo "[INFO] Generating report anyway..."
else
    echo -e "${GREEN}[SUCCESS]${NC} All tests passed!"
fi

echo ""

# Step 6: Generate Allure report
echo -e "${BLUE}[STEP 6]${NC} Generating Allure report..."
echo ""

mvn allure:report --quiet

if [ $? -ne 0 ]; then
    echo -e "${RED}[ERROR]${NC} Failed to generate Allure report"
    echo "Try running: mvn allure:report -X (verbose output)"
    exit 1
fi

echo -e "${GREEN}[SUCCESS]${NC} Report generated successfully"
echo ""

# Step 7: Verify report generation
echo -e "${BLUE}[STEP 7]${NC} Verifying report..."

if [ ! -f "target/site/allure-report/index.html" ]; then
    echo -e "${RED}[ERROR]${NC} Report file not found"
    echo "Expected: target/site/allure-report/index.html"
    exit 1
fi

echo -e "${GREEN}[SUCCESS]${NC} Report verified"
echo ""

# Step 8: Display summary
echo "========================================"
echo -e "${GREEN}  Report Generation Completed${NC}"
echo "========================================"
echo ""

# Count test results
TEST_COUNT=$(find target/allure-results -name "*.json" 2>/dev/null | wc -l)

echo -e "${BLUE}Test Results Summary:${NC}"
echo "  Total test result files: $TEST_COUNT"
echo ""

echo -e "${BLUE}Report Location:${NC}"
echo "  $PWD/target/site/allure-report/index.html"
echo ""

echo -e "${BLUE}Options:${NC}"
echo "  1. View report in browser (auto-open):"
echo "     open target/site/allure-report/index.html"
echo ""
echo "  2. Serve report interactively:"
echo "     mvn allure:serve"
echo ""
echo "  3. View detailed test results:"
echo "     grep -r 'name' target/allure-results/ | head -20"
echo ""

# Step 9: Ask to open report
echo -e "${BLUE}[STEP 8]${NC} Opening report in browser..."
echo ""

if command -v open &> /dev/null; then
    # macOS
    open target/site/allure-report/index.html
    echo -e "${GREEN}[SUCCESS]${NC} Report opened in Safari (default browser)"
elif command -v xdg-open &> /dev/null; then
    # Linux
    xdg-open target/site/allure-report/index.html
    echo -e "${GREEN}[SUCCESS]${NC} Report opened in default browser"
else
    echo -e "${YELLOW}[INFO]${NC} Please open manually:"
    echo "  target/site/allure-report/index.html"
fi

echo ""

# Final timestamp
END_TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')
echo -e "${BLUE}[INFO]${NC} Report generation completed at: $END_TIMESTAMP"
echo ""

echo "========================================"
echo "  For more information, see:"
echo "  - ALLURE_REPORT_SETUP.md"
echo "  - COMPREHENSIVE_DOCUMENTATION.md"
echo "========================================"
echo ""

exit 0
