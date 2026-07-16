package com.api.automation.listeners;

import java.io.File;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.api.automation.config.ConfigManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extentReports;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        File reportFolder = new File("target/extent-report");

        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(
                        "target/extent-report/ExtentReport.html");

        sparkReporter.config()
                .setDocumentTitle("Contact API Automation Report");

        sparkReporter.config()
                .setReportName("REST Assured API Test Results");

        extentReports = new ExtentReports();

        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo(
                "Environment",
                ConfigManager.getEnvironment());

        extentReports.setSystemInfo(
                "Base URI",
                ConfigManager.getBaseUri());

        extentReports.setSystemInfo(
                "Java Version",
                System.getProperty("java.version"));

        extentReports.setSystemInfo(
                "Operating System",
                System.getProperty("os.name"));
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test = extentReports.createTest(
                result.getTestClass().getRealClass().getSimpleName()
                        + " - "
                        + result.getMethod().getMethodName());

        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get()
                .pass("Test executed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get()
                .fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get()
                .skip("Test was skipped.");

        if (result.getThrowable() != null) {
            extentTest.get().skip(result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        if (extentReports != null) {
            extentReports.flush();
        }

        extentTest.remove();
    }
}