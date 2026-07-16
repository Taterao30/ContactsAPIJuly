package com.api.automation.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.api.automation.base.BaseAPI;
import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.ContactEndpoints;
import com.api.automation.payload.ContactPayload;
import com.api.automation.utils.TestContext;
import com.api.automation.utils.TokenManager;

import io.restassured.response.Response;

public class PatchContactTest extends BaseTest {

    private final BaseAPI api = new BaseAPI();

    @Test
    public void patchContact() {

        Response response = api.patch(
                ContactEndpoints.CONTACT_BY_ID,
                TestContext.getContactId(),
                ContactPayload.patchContact(),
                TokenManager.getToken());

        response.then()
                .statusCode(200)
                .body("firstName", equalTo("Updated"))
                .body("phone", equalTo("8888888888"));

        response.prettyPrint();
    }
}