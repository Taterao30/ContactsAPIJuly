package com.api.automation.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {

    private RequestSpecification getRequest(String token) {

        RequestSpecification request = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        if (token != null && !token.trim().isEmpty()) {
            request.header("Authorization", "Bearer " + token);
        }

        return request;
    }

    // ==================== GET ====================

    public Response get(String endpoint, String token) {

        return getRequest(token)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response get(String endpoint, String id, String token) {

        return getRequest(token)
                .pathParam("id", id)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    // ==================== POST ====================

    public Response post(
            String endpoint,
            Object payload,
            String token) {

        return getRequest(token)
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    // ==================== PUT ====================

    public Response put(
            String endpoint,
            Object payload,
            String token) {

        return getRequest(token)
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response put(
            String endpoint,
            String id,
            Object payload,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    // ==================== PATCH ====================

    public Response patch(
            String endpoint,
            Object payload,
            String token) {

        return getRequest(token)
                .body(payload)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response patch(
            String endpoint,
            String id,
            Object payload,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .body(payload)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    // ==================== DELETE ====================

    public Response delete(String endpoint, String token) {

        return getRequest(token)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response delete(
            String endpoint,
            String id,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}