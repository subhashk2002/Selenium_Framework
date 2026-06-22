package com.automation.config;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

@Getter
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties properties;
    private final EnvironmentConfig environment;

    private ConfigManager() {
        this.properties = loadProperties();
        this.environment = initializeEnvironment();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        String env = System.getProperty("environment", "local").toUpperCase();
        String configFile = "config_" + env.toLowerCase() + ".properties";

        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream(configFile)) {
            if (input != null) {
                props.load(input);
                logger.info("Loaded configuration from: {}", configFile);
            } else {
                logger.warn("Configuration file not found: {}. Using defaults.", configFile);
                loadDefaultProperties(props);
            }
        } catch (IOException e) {
            logger.error("Error loading configuration", e);
            loadDefaultProperties(props);
        }
        return props;
    }

    private void loadDefaultProperties(Properties props) {
        props.setProperty("browser", "chrome");
        props.setProperty("baseUrl", "http://localhost:8080");
        props.setProperty("implicit.wait", "10");
        props.setProperty("explicit.wait", "15");
        props.setProperty("page.load.timeout", "30");
        props.setProperty("headless", "false");
        props.setProperty("enable.screenshots", "true");
        props.setProperty("retry.count", "2");
        props.setProperty("ollama.enabled", "true");
        props.setProperty("ollama.base.url", "http://localhost:11434");
    }

    private EnvironmentConfig initializeEnvironment() {
        String env = System.getProperty("environment", "local");
        return EnvironmentConfig.fromString(env);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for property: {}", key);
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl", environment.getBaseUrl());
    }

    public String getBrowser() {
        return properties.getProperty("browser", environment.getBrowser());
    }

    public int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public int getExplicitWait() {
        return getIntProperty("explicit.wait", 15);
    }

    public int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    public boolean enableScreenshots() {
        return getBooleanProperty("enable.screenshots", true);
    }

    public int getRetryCount() {
        return getIntProperty("retry.count", 2);
    }

    public boolean isOllamaEnabled() {
        return getBooleanProperty("ollama.enabled", false);
    }

    public String getOllamaBaseUrl() {
        return getProperty("ollama.base.url", "http://localhost:11434");
    }

    public static void reset() {
        instance = null;
    }
}
