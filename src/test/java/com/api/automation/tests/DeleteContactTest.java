package com.api.automation.tests;

import org.testng.annotations.Test;

import com.api.automation.base.BaseAPI;
import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.ContactEndpoints;
import com.api.automation.utils.TestContext;
import com.api.automation.utils.TokenManager;

import io.restassured.response.Response;

public class DeleteContactTest extends BaseTest {

    private final BaseAPI api = new BaseAPI();

    @Test
    public void deleteContact() {

        String contactId = TestContext.getContactId();

        Response response = api.delete(
                ContactEndpoints.CONTACT_BY_ID,
                contactId,
                TokenManager.getToken());

        response.then()
                .statusCode(200);

        System.out.println("Deleted Contact ID: " + contactId);
    }
}