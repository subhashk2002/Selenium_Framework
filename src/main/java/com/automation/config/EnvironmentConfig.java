package com.automation.config;

import lombok.Getter;

@Getter
public enum EnvironmentConfig {
    LOCAL("http://localhost:8080", "chrome"),
    DEV("https://dev.example.com", "chrome"),
    STAGING("https://staging.example.com", "firefox"),
    PRODUCTION("https://example.com", "chrome");

    private final String baseUrl;
    private final String browser;

    EnvironmentConfig(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    public static EnvironmentConfig fromString(String env) {
        try {
            return EnvironmentConfig.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException e) {
            return LOCAL;
        }
    }
}
