package com.api.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.utils.TokenManager;

public class LoginTest extends BaseTest {

    @Test
    public void verifyTokenGeneration() {

        String token = TokenManager.getToken();

        Assert.assertNotNull(
                token,
                "Token should not be null.");

        Assert.assertFalse(
                token.isBlank(),
                "Token should not be empty.");

        System.out.println("Token generated successfully.");
    }
}