@echo off
REM Allure Report Generation Script for Windows
REM This script runs tests and generates Allure reports

setlocal enabledelayedexpansion

echo.
echo ========================================
echo  Hybrid Selenium Framework - Allure Report
echo ========================================
echo.

REM Define colors for output (if supported)
set RESET=[0m
set BOLD=[1m
set GREEN=[32m
set YELLOW=[33m
set RED=[31m

REM Get current timestamp
for /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set DATEVAR=%%c-%%a-%%b)
for /f "tokens=1-2 delims=/:" %%a in ('time /t') do (set TIMEVAR=%%a-%%b)

echo [INFO] Report generation started at: %DATEVAR% %TIMEVAR%
echo.

REM Step 1: Confirm environment
echo [STEP 1] Select Test Environment:
echo.
echo   1 = LOCAL (default)
echo   2 = DEV
echo   3 = STAGING
echo   4 = PRODUCTION
echo.

set ENVIRONMENT=local

if "%1"=="" (
    echo Using default environment: LOCAL
) else (
    if "%1"=="dev" set ENVIRONMENT=dev
    if "%1"=="staging" set ENVIRONMENT=staging
    if "%1"=="prod" set ENVIRONMENT=production
)

echo [INFO] Selected environment: %ENVIRONMENT%
echo.

REM Step 2: Clean previous builds
echo [STEP 2] Cleaning previous test results...
call mvn clean

if errorlevel 1 (
    echo [ERROR] Maven clean failed
    goto :error
)

echo [SUCCESS] Clean completed
echo.

REM Step 3: Run tests
echo [STEP 3] Running tests (environment: %ENVIRONMENT%)...
echo.

call mvn test -Denvironment=%ENVIRONMENT%

if errorlevel 1 (
    echo [WARNING] Some tests may have failed
    echo [INFO] Generating report anyway...
) else (
    echo [SUCCESS] All tests passed!
)

echo.

REM Step 4: Generate Allure report
echo [STEP 4] Generating Allure report...
call mvn allure:report

if errorlevel 1 (
    echo [ERROR] Failed to generate Allure report
    goto :error
)

echo [SUCCESS] Report generated successfully
echo.

REM Step 5: Check if report was generated
if not exist "target\site\allure-report\index.html" (
    echo [ERROR] Report file not found: target\site\allure-report\index.html
    goto :error
)

echo [STEP 5] Opening report in default browser...
echo.

REM Try to open report
start target\site\allure-report\index.html

echo [SUCCESS] Report should open in your default browser
echo.
echo ========================================
echo  Report Generation Completed Successfully!
echo ========================================
echo.
echo Report Location: target\site\allure-report\index.html
echo Results Directory: target\allure-results\
echo.

REM Display test results summary
echo Test Results Summary:
echo ====================
echo.

REM Count test results (JSON files)
setlocal enabledelayedexpansion
set COUNT=0
for %%F in (target\allure-results\*.json) do (
    set /a COUNT+=1
)

echo Total test result files: %COUNT%
echo.

echo [INFO] View the report at: target\site\allure-report\index.html
echo [INFO] To serve the report interactively, run: mvn allure:serve
echo.

echo Report generation completed at: %DATEVAR% %TIMEVAR%
echo.

endlocal
exit /b 0

:error
echo.
echo ========================================
echo  ERROR: Report Generation Failed
echo ========================================
echo.
echo Troubleshooting:
echo 1. Ensure Maven is installed: mvn --version
echo 2. Ensure Java is installed: java -version
echo 3. Check configuration files exist in src/main/resources/
echo 4. Try running: mvn clean install
echo 5. Check logs in target/ directory
echo.
echo For more details, run with verbose output:
echo   mvn test -X
echo.
exit /b 1
