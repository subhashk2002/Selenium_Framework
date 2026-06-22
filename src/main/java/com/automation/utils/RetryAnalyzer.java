package com.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.automation.config.ConfigManager;

@Slf4j
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final ConfigManager config = ConfigManager.getInstance();
    private final int maxRetryCount = config.getRetryCount();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount && !result.isSuccess()) {
            log.warn("Test {} failed. Retrying... ({}/{})",
                    result.getMethod().getMethodName(), retryCount + 1, maxRetryCount);
            retryCount++;
            return true;
        }
        return false;
    }
}
