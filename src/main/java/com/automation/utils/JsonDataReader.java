package com.automation.utils;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class JsonDataReader {
    private final JsonElement root;

    public JsonDataReader(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            this.root = JsonParser.parseReader(reader);
            log.info("JSON file loaded successfully: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to load JSON file: {}", filePath, e);
            throw e;
        }
    }

    public JsonObject getObject(String path) {
        return navigateToElement(path).getAsJsonObject();
    }

    public JsonArray getArray(String path) {
        return navigateToElement(path).getAsJsonArray();
    }

    public String getString(String path) {
        return navigateToElement(path).getAsString();
    }

    public int getInt(String path) {
        return navigateToElement(path).getAsInt();
    }

    public double getDouble(String path) {
        return navigateToElement(path).getAsDouble();
    }

    public boolean getBoolean(String path) {
        return navigateToElement(path).getAsBoolean();
    }

    public List<Map<String, String>> getDataAsList(String arrayPath) {
        List<Map<String, String>> dataList = new ArrayList<>();
        JsonArray jsonArray = getArray(arrayPath);

        for (JsonElement element : jsonArray) {
            JsonObject jsonObj = element.getAsJsonObject();
            Map<String, String> map = new HashMap<>();

            for (String key : jsonObj.keySet()) {
                map.put(key, jsonObj.get(key).getAsString());
            }
            dataList.add(map);
        }
        return dataList;
    }

    private JsonElement navigateToElement(String path) {
        String[] keys = path.split("\\.");
        JsonElement current = root;

        for (String key : keys) {
            if (current.isJsonObject()) {
                current = current.getAsJsonObject().get(key);
            } else if (current.isJsonArray()) {
                int index = Integer.parseInt(key);
                current = current.getAsJsonArray().get(index);
            }

            if (current == null) {
                throw new RuntimeException("Path not found: " + path);
            }
        }
        return current;
    }

    public JsonObject getRootObject() {
        return root.getAsJsonObject();
    }

    public JsonArray getRootArray() {
        return root.getAsJsonArray();
    }
}
