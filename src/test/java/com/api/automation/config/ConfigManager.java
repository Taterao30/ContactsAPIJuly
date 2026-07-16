package com.api.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();
    private static final String ENVIRONMENT;

    static {
        ENVIRONMENT = System.getProperty("env", "qa")
                .trim()
                .toLowerCase(Locale.ROOT);

        String fileName = "config-" + ENVIRONMENT + ".properties";

        try (InputStream inputStream =
                     ConfigManager.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Configuration file not found: " + fileName);
            }

            PROPERTIES.load(inputStream);

            System.out.println("Environment: " + ENVIRONMENT);
            System.out.println("Configuration file: " + fileName);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load configuration file: " + fileName,
                    exception);
        }
    }

    private ConfigManager() {
        // Prevent object creation
    }

    public static String getEnvironment() {
        return ENVIRONMENT;
    }

    public static String getBaseUri() {
        return getRequiredProperty("base.uri");
    }

    public static String getProperty(String key) {
        validateKey(key);

        // Priority 1: Maven/JVM property
        // Example: -Dapi.email=value
        String systemValue = System.getProperty(key);

        if (hasText(systemValue)) {
            return systemValue.trim();
        }

        // Priority 2: Operating system/Jenkins environment variable
        // api.email -> API_EMAIL
        // api.password -> API_PASSWORD
        String environmentKey = toEnvironmentVariable(key);
        String environmentValue = System.getenv(environmentKey);

        if (hasText(environmentValue)) {
            return environmentValue.trim();
        }

        // Priority 3: Environment properties file
        // config-qa.properties, config-uat.properties, etc.
        String fileValue = PROPERTIES.getProperty(key);

        if (hasText(fileValue)) {
            return fileValue.trim();
        }

        return null;
    }

    public static String getRequiredProperty(String key) {
        String value = getProperty(key);

        if (!hasText(value)) {
            String environmentKey = toEnvironmentVariable(key);

            throw new IllegalStateException(
                    "Required property is missing: " + key
                            + ". Provide it using JVM property '-D"
                            + key
                            + "=value', Jenkins environment variable '"
                            + environmentKey
                            + "', or config-"
                            + ENVIRONMENT
                            + ".properties.");
        }

        return value;
    }

    public static boolean isAvailable(String key) {
        return hasText(getProperty(key));
    }

    private static String toEnvironmentVariable(String key) {
        return key.trim()
                .toUpperCase(Locale.ROOT)
                .replace('.', '_')
                .replace('-', '_');
    }

    private static void validateKey(String key) {
        if (!hasText(key)) {
            throw new IllegalArgumentException(
                    "Configuration key cannot be null or blank.");
        }
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}