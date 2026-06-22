package com.automation.listeners;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import com.automation.ai.OllamaAIHelper;
import com.automation.utils.ScreenshotHelper;

@Slf4j
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.info("========== Test Started: {} ==========", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("========== Test Passed: {} ==========", result.getMethod().getMethodName());
        Allure.step("Test completed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("========== Test Failed: {} ==========", result.getMethod().getMethodName());

        String testName = result.getMethod().getMethodName();
        ScreenshotHelper.captureScreenshot("FAILED_" + testName);

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            String errorMessage = throwable.getMessage();
            String stackTrace = getStackTrace(throwable);

            log.error("Error: {}", errorMessage);
            log.error("Stack Trace: {}", stackTrace);

            Allure.step("Test failed with error: " + errorMessage);

            if (OllamaAIHelper.isOllamaAvailable()) {
                log.info("Analyzing failure with AI...");
                String analysis = OllamaAIHelper.analyzeFailure(errorMessage, stackTrace);
                Allure.step("AI Analysis: " + analysis);
                log.info("AI Analysis: {}", analysis);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("========== Test Skipped: {} ==========", result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            log.warn("Skipped reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("========== Test Failed But Within Success Percentage: {} ==========",
                result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("========== Test Suite Started: {} ==========", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("========== Test Suite Finished: {} ==========", context.getName());
        log.info("Total Tests: {}", context.getAllTestMethods().length);
        log.info("Passed: {}", context.getPassedTests().size());
        log.info("Failed: {}", context.getFailedTests().size());
        log.info("Skipped: {}", context.getSkippedTests().size());
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
