package com.api.automation.utils;

import java.util.HashMap;
import java.util.Map;

import com.api.automation.config.ConfigManager;
import com.api.automation.endpoints.ContactEndpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class TokenManager {

    private static String token;

    private TokenManager() {
        // Prevent object creation
    }

    public static synchronized String getToken() {

        // Reuse the token if it is already generated
        if (token != null && !token.isBlank()) {
            return token;
        }

        Map<String, String> loginPayload = new HashMap<>();
        
        System.out.println("API_EMAIL    = " + System.getenv("API_EMAIL"));
        System.out.println("API_PASSWORD = " + System.getenv("API_PASSWORD"));

        loginPayload.put(
                "email",
                ConfigManager.getProperty("api.email"));

        loginPayload.put(
                "password",
                ConfigManager.getProperty("api.password"));

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post(ContactEndpoints.LOGIN);

        int statusCode = response.statusCode();

        if (statusCode != 200) {
            throw new IllegalStateException(
                    "Token generation failed. Status code: "
                            + statusCode
                            + ", response: "
                            + response.asString());
        }

        String contentType = response.contentType();

        if (contentType == null
                || !contentType.toLowerCase().contains("json")) {

            throw new IllegalStateException(
                    "Login API did not return JSON. Content-Type: "
                            + contentType
                            + ", response: "
                            + response.asString());
        }

        token = response.jsonPath().getString("token");

        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                    "Token was not found in the login response.");
        }

        System.out.println("Token generated successfully.");

        return token;
    }

    public static synchronized void clearToken() {
        token = null;
    }
}