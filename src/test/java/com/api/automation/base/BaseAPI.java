package com.api.automation.base;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {

    private RequestSpecification getRequest(String token) {

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .log()
                .ifValidationFails();
    }

    public Response get(String endpoint, String token) {

        return getRequest(token)
                .when()
                .get(endpoint);
    }

    public Response getById(
            String endpoint,
            String id,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .when()
                .get(endpoint);
    }

    public Response post(
            String endpoint,
            Object requestBody,
            String token) {

        return getRequest(token)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public Response put(
            String endpoint,
            String id,
            Object requestBody,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public Response patch(
            String endpoint,
            String id,
            Object requestBody,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    public Response delete(
            String endpoint,
            String id,
            String token) {

        return getRequest(token)
                .pathParam("id", id)
                .when()
                .delete(endpoint);
    }
}