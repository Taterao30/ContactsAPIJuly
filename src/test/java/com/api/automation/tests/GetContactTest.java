package com.api.automation.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.api.automation.base.BaseAPI;
import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.ContactEndpoints;
import com.api.automation.utils.TestContext;
import com.api.automation.utils.TokenManager;

import io.restassured.response.Response;

public class GetContactTest extends BaseTest {

    private final BaseAPI api = new BaseAPI();

    @Test
    public void getContactById() {

        String contactId = TestContext.getContactId();

        Response response = api.getById(
                ContactEndpoints.CONTACT_BY_ID,
                contactId,
                TokenManager.getToken());

        response.then()
                .statusCode(200)
                .body("_id", equalTo(contactId));

        response.prettyPrint();
    }
}