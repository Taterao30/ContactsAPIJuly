package com.api.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String environment;

    static {
        environment = System.getProperty("env", "qa")
                .trim()
                .toLowerCase();

        String fileName = "config-" + environment + ".properties";

        try (InputStream inputStream =
                     ConfigManager.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Configuration file not found: " + fileName);
            }

            properties.load(inputStream);

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to load configuration file: " + fileName,
                    exception);
        }
    }

    private ConfigManager() {
        // Prevent object creation
    }

    public static String getEnvironment() {
        return environment;
    }

    public static String getBaseUri() {
        return getProperty("base.uri");
    }

    public static String getProperty(String key) {

        // 1. Check Maven/JVM system property:
        // -Dapi.email=value
        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }

        // 2. Check operating-system or Jenkins environment variable:
        // api.email becomes API_EMAIL
        String environmentKey =
                key.toUpperCase().replace(".", "_");

        String environmentValue =
                System.getenv(environmentKey);
        
        System.out.println("Looking for environment variable : " + environmentKey);
        System.out.println("Value : " + environmentValue);
        
        System.out.println(
        	    "API_PASSWORD exists = " +
        	    (System.getenv("API_PASSWORD") != null)
        	);

        if (environmentValue != null &&
                !environmentValue.isBlank()) {

            return environmentValue.trim();
        }

        // 3. Check config-qa.properties/config-uat.properties
        String fileValue = properties.getProperty(key);

        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue.trim();
        }

        throw new IllegalStateException(
                "Property not found: " + key);
    }
}