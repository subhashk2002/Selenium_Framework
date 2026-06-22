package com.automation.ai;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import com.automation.config.ConfigManager;

import java.io.IOException;
import java.util.*;

@Slf4j
public class OllamaAIHelper {
    private static final ConfigManager config = ConfigManager.getInstance();
    private static final OkHttpClient client = new OkHttpClient();
    private static final String OLLAMA_API = "/api/generate";

    public static String generateTestData(String prompt) {
        if (!config.isOllamaEnabled()) {
            log.warn("Ollama AI is disabled. Returning empty response.");
            return "";
        }

        try {
            String baseUrl = config.getOllamaBaseUrl();
            String response = makeOllamaRequest(baseUrl + OLLAMA_API, prompt);
            log.info("AI-generated test data: {}", response);
            return response;
        } catch (IOException e) {
            log.error("Failed to generate test data with AI", e);
            return "";
        }
    }

    public static String generateTestDataForField(String fieldName, String fieldType) {
        if (!config.isOllamaEnabled()) {
            log.warn("Ollama AI is disabled. Returning default value.");
            return getDefaultValueForType(fieldType);
        }

        String prompt = String.format(
                "Generate a single realistic test data value for a '%s' field of type '%s'. " +
                "Return only the value without any explanation.",
                fieldName, fieldType
        );

        return generateTestData(prompt);
    }

    public static String analyzeFailure(String errorMessage, String stackTrace) {
        if (!config.isOllamaEnabled()) {
            log.warn("Ollama AI is disabled. Cannot analyze failure.");
            return "AI analysis disabled";
        }

        try {
            String prompt = String.format(
                    "Analyze this test failure and provide a root cause analysis:\n" +
                    "Error: %s\nStack Trace: %s\n" +
                    "Provide a brief analysis in 2-3 sentences.",
                    errorMessage, stackTrace
            );

            String baseUrl = config.getOllamaBaseUrl();
            String response = makeOllamaRequest(baseUrl + OLLAMA_API, prompt);
            log.info("AI failure analysis: {}", response);
            return response;
        } catch (IOException e) {
            log.error("Failed to analyze failure with AI", e);
            return "Analysis failed";
        }
    }

    public static String suggestSelfHealingLocator(String originalLocator, String locatorType) {
        if (!config.isOllamaEnabled()) {
            log.warn("Ollama AI is disabled. Cannot suggest alternative locator.");
            return originalLocator;
        }

        try {
            String prompt = String.format(
                    "The locator '%s' of type '%s' is not working. " +
                    "Suggest an alternative '%s' locator that would be more reliable. " +
                    "Return only the new locator without explanation.",
                    originalLocator, locatorType, locatorType
            );

            String baseUrl = config.getOllamaBaseUrl();
            String response = makeOllamaRequest(baseUrl + OLLAMA_API, prompt);
            log.info("AI-suggested locator: {}", response);
            return response;
        } catch (IOException e) {
            log.error("Failed to suggest self-healing locator with AI", e);
            return originalLocator;
        }
    }

    public static String generateTestScenario(String featureName) {
        if (!config.isOllamaEnabled()) {
            log.warn("Ollama AI is disabled. Cannot generate test scenario.");
            return "";
        }

        try {
            String prompt = String.format(
                    "Generate a comprehensive test scenario for the feature: '%s'. " +
                    "Include Given-When-Then format. Keep it concise.",
                    featureName
            );

            String baseUrl = config.getOllamaBaseUrl();
            String response = makeOllamaRequest(baseUrl + OLLAMA_API, prompt);
            log.info("AI-generated test scenario: {}", response);
            return response;
        } catch (IOException e) {
            log.error("Failed to generate test scenario with AI", e);
            return "";
        }
    }

    public static List<String> generateMultipleTestCases(String requirement, int count) {
        List<String> testCases = new ArrayList<>();
        if (!config.isOllamaEnabled()) {
            log.warn("Ollama AI is disabled. Returning empty list.");
            return testCases;
        }

        try {
            String prompt = String.format(
                    "Generate %d different test cases for this requirement: '%s'. " +
                    "Return each test case on a new line with a number prefix (1., 2., etc.).",
                    count, requirement
            );

            String baseUrl = config.getOllamaBaseUrl();
            String response = makeOllamaRequest(baseUrl + OLLAMA_API, prompt);
            String[] cases = response.split("\n");

            for (String testCase : cases) {
                if (!testCase.trim().isEmpty()) {
                    testCases.add(testCase.replaceAll("^\\d+\\.\\s*", "").trim());
                }
            }

            log.info("Generated {} test cases", testCases.size());
        } catch (IOException e) {
            log.error("Failed to generate multiple test cases with AI", e);
        }

        return testCases;
    }

    private static String makeOllamaRequest(String url, String prompt) throws IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "mistral");
        requestBody.addProperty("prompt", prompt);
        requestBody.addProperty("stream", false);

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Ollama API error: " + response.code());
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IOException("Empty response from Ollama");
            }

            String responseStr = responseBody.string();
            JsonObject jsonResponse = JsonParser.parseString(responseStr).getAsJsonObject();
            return jsonResponse.get("response").getAsString().trim();
        }
    }

    private static String getDefaultValueForType(String fieldType) {
        return switch (fieldType.toLowerCase()) {
            case "email" -> "test@example.com";
            case "password" -> "TestPassword@123";
            case "phone" -> "+1234567890";
            case "date" -> "2026-01-01";
            case "number" -> "123";
            case "text", "string" -> "Test Value";
            default -> "";
        };
    }

    public static boolean isOllamaAvailable() {
        if (!config.isOllamaEnabled()) {
            return false;
        }

        try {
            String baseUrl = config.getOllamaBaseUrl();
            Request request = new Request.Builder()
                    .url(baseUrl + "/api/tags")
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.isSuccessful();
            }
        } catch (IOException e) {
            log.warn("Ollama is not available: {}", e.getMessage());
            return false;
        }
    }
}
