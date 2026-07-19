package com.api.automation.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.api.automation.base.BaseAPI;
import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.ContactEndpoints;
import com.api.automation.payload.ContactPayload;
import com.api.automation.utils.TestContext;
import com.api.automation.utils.TokenManager;

import io.restassured.response.Response;

public class CreateContactTest extends BaseTest {

    private final BaseAPI api = new BaseAPI();

    @Test
    public void createContact() {
    	

        Response response = api.post(
                ContactEndpoints.CONTACTS,
                ContactPayload.createContact(),
                TokenManager.getToken());

        response.then()
                .statusCode(201)
                .body("_id", notNullValue())
                .body("firstName", equalTo("Taterao"));

        String contactId =
                response.jsonPath().getString("_id");

        TestContext.setContactId(contactId);

        System.out.println(
                "Created Contact ID: " + contactId);
    }
}