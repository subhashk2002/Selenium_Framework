package com.automation.utils;

import lombok.extern.slf4j.Slf4j;
import com.automation.ai.OllamaAIHelper;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class TestDataGenerator {

    public static String generateEmail() {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "testuser_" + randomString + "@example.com";
    }

    public static String generatePassword() {
        return "TestPassword@" + UUID.randomUUID().toString().substring(0, 6);
    }

    public static String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder phone = new StringBuilder("+1");
        for (int i = 0; i < 10; i++) {
            phone.append(random.nextInt(10));
        }
        return phone.toString();
    }

    public static String generateUsername() {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomString;
    }

    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateRandomNumericString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateProductName() {
        String[] adjectives = { "Premium", "Professional", "Advanced", "Ultimate", "Smart" };
        String[] products = { "Widget", "Device", "Tool", "System", "Solution" };
        Random random = new Random();
        return adjectives[random.nextInt(adjectives.length)] + " " +
               products[random.nextInt(products.length)] + " " +
               generateRandomString(3);
    }

    public static double generatePrice(double min, double max) {
        return min + (Math.random() * (max - min));
    }

    public static String generateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, new Random().nextInt(365));
        return String.format("%tY-%tm-%td", calendar, calendar, calendar);
    }

    public static String generateFutureDate(int daysFromNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysFromNow);
        return String.format("%tY-%tm-%td", calendar, calendar, calendar);
    }

    public static String generateCreditCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    public static String generateCVV() {
        Random random = new Random();
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(10));
        }
        return cvv.toString();
    }

    public static String generateAddress() {
        return generateRandomNumericString(4) + " " + generateRandomString(10) + " Street";
    }

    public static String generateCity() {
        String[] cities = { "New York", "Los Angeles", "Chicago", "Houston", "Phoenix" };
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateState() {
        String[] states = { "CA", "NY", "TX", "FL", "IL", "PA", "OH", "GA", "NC", "MI" };
        return states[new Random().nextInt(states.length)];
    }

    public static String generateZipCode() {
        return generateRandomNumericString(5);
    }

    public static String generateCountry() {
        String[] countries = { "United States", "Canada", "United Kingdom", "Australia", "Germany" };
        return countries[new Random().nextInt(countries.length)];
    }

    public static String generateAITestData(String fieldType) {
        if (!OllamaAIHelper.isOllamaAvailable()) {
            log.warn("Ollama not available. Using default test data generator.");
            return getDefaultTestData(fieldType);
        }

        return OllamaAIHelper.generateTestDataForField("testField", fieldType);
    }

    private static String getDefaultTestData(String fieldType) {
        return switch (fieldType.toLowerCase()) {
            case "email" -> generateEmail();
            case "password" -> generatePassword();
            case "phone" -> generatePhoneNumber();
            case "username" -> generateUsername();
            case "date" -> generateDate();
            case "product" -> generateProductName();
            case "address" -> generateAddress();
            case "credit_card" -> generateCreditCardNumber();
            default -> generateRandomString(10);
        };
    }

    public static Map<String, String> generateTestUser() {
        Map<String, String> user = new HashMap<>();
        user.put("email", generateEmail());
        user.put("password", generatePassword());
        user.put("username", generateUsername());
        user.put("firstName", "Test");
        user.put("lastName", "User");
        user.put("phone", generatePhoneNumber());
        return user;
    }

    public static Map<String, Object> generateTestProduct() {
        Map<String, Object> product = new HashMap<>();
        product.put("name", generateProductName());
        product.put("price", generatePrice(10, 1000));
        product.put("quantity", new Random().nextInt(1000) + 1);
        product.put("category", "electronics");
        product.put("sku", generateRandomString(8));
        return product;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\+?[0-9]{10,}$";
        return Pattern.matches(phoneRegex, phone);
    }

    public static boolean isValidPassword(String password) {
        // At least 8 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
}
