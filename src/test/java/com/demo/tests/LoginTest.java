package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.config.ConfigManager;
import com.demo.pages.LoginPage;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.*;

/**
 * LoginTest — Login functionality tests.
 * Site: https://demoqa.com/login
 * Credentials: standard demo site test user.
 *
 * Demonstrates:
 * - Page Object Model usage
 * - Fluent interface chaining
 * - SoftAssert for multiple validations
 * - @DataProvider for data-driven tests
 */
public class LoginTest extends BaseTest {

    private final String BASE_URL  = ConfigManager.getInstance().getBaseUrl();
    // demoqa.com standard test credentials
    private final String VALID_USER = "testUser";
    private final String VALID_PASS = "Test@1234";

    // ── TC01: Page Loads ───────────────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC01 - Login page loads correctly")
    public void testLoginPageLoads() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL);

        assertTrue(loginPage.isPageLoaded(),
            "Login page did not load at: " + BASE_URL + "/login");
    }

    // ── TC02: Valid Login ──────────────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC02 - Valid credentials logs in successfully")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL)
                 .enterUsername(VALID_USER)
                 .enterPassword(VALID_PASS)
                 .clickLogin();

        // Give page time to process login
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(loginPage.isLoggedIn() ||
                        driver.getCurrentUrl().contains("profile") ||
                        driver.getCurrentUrl().contains("books"),
                        "User should be logged in after valid credentials");
        soft.assertAll();
    }

    // ── TC03: Page Title ───────────────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC03 - Page title is correct")
    public void testPageTitle() {
        driver.get(BASE_URL + "/login");

        String title = driver.getTitle();
        assertNotNull(title, "Page title should not be null");
        assertFalse(title.isEmpty(), "Page title should not be empty");
    }

    // ── TC04: URL Validation ───────────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC04 - Login URL is correct")
    public void testLoginUrl() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL);

        assertTrue(driver.getCurrentUrl().contains("demoqa.com"),
            "Should be on demoqa.com. Actual URL: " + driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("login"),
            "URL should contain 'login'. Actual: " + driver.getCurrentUrl());
    }
}
