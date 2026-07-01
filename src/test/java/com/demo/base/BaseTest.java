package com.demo.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/**
 * BaseTest — All test classes extend this.
 * Manages: driver lifecycle (setup/teardown per test method).
 * ThreadLocal in DriverFactory ensures parallel test safety.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
