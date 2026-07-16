package com.api.automation.base;

import org.testng.annotations.BeforeSuite;

import com.api.automation.config.ConfigManager;

import io.restassured.RestAssured;

public abstract class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {

        RestAssured.baseURI = ConfigManager.getBaseUri();

        System.out.println(
                "Environment: " + ConfigManager.getEnvironment());

        System.out.println(
                "Base URI: " + ConfigManager.getBaseUri());
    }
}