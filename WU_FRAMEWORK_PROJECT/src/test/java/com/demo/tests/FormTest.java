package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.config.ConfigManager;
import com.demo.pages.TextBoxPage;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.*;

/**
 * FormTest — Text Box form fill and validation tests.
 * Site: https://demoqa.com/text-box
 *
 * Demonstrates:
 * - Form automation (fill + submit)
 * - Output validation after form submit
 * - @DataProvider data-driven testing
 * - SoftAssert multiple field validation
 */
public class FormTest extends BaseTest {

    private final String BASE_URL = ConfigManager.getInstance().getBaseUrl();

    // ── TC01: Form Page Loads ──────────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC01 - Text box form page loads correctly")
    public void testFormPageLoads() {
        TextBoxPage page = new TextBoxPage(driver);
        page.navigateTo(BASE_URL);

        assertTrue(page.isPageLoaded(),
            "Text box page did not load at: " + BASE_URL + "/text-box");
    }

    // ── TC02: Form Fill + Submit ───────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC02 - Form fill and submit shows correct output")
    public void testFormFillAndSubmit() {
        TextBoxPage page = new TextBoxPage(driver);
        page.navigateTo(BASE_URL)
            .enterFullName("Pratiksha Khade")
            .enterEmail("pratiksha@test.com")
            .enterCurrentAddress("Navi Mumbai, Maharashtra")
            .enterPermanentAddress("Mumbai, Maharashtra")
            .clickSubmit();

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(page.getOutputName().contains("Pratiksha Khade"),
            "Output name should contain 'Pratiksha Khade'. Got: " + page.getOutputName());
        soft.assertTrue(page.getOutputEmail().contains("pratiksha@test.com"),
            "Output email should match. Got: " + page.getOutputEmail());
        soft.assertTrue(page.getOutputCurrentAddress().contains("Navi Mumbai"),
            "Output address should contain 'Navi Mumbai'. Got: " + page.getOutputCurrentAddress());
        soft.assertAll();
    }

    // ── TC03: Data-Driven Form Test ────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC03 - Data-driven form submission",
          dataProvider = "formData")
    public void testFormWithMultipleDataSets(String name, String email, String city) {
        TextBoxPage page = new TextBoxPage(driver);
        page.navigateTo(BASE_URL)
            .enterFullName(name)
            .enterEmail(email)
            .enterCurrentAddress(city)
            .clickSubmit();

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(page.getOutputName().contains(name),
            "Output name mismatch for: " + name);
        soft.assertTrue(page.getOutputEmail().contains(email),
            "Output email mismatch for: " + email);
        soft.assertAll();
    }

    @DataProvider(name = "formData")
    public Object[][] formData() {
        return new Object[][] {
            { "Raj Kumar",    "raj@test.com",    "Mumbai"    },
            { "Priya Sharma", "priya@test.com",  "Pune"      },
            { "Amit Singh",   "amit@test.com",   "Bangalore" }
        };
    }

    // ── TC04: URL Test ─────────────────────────────────────────
    @Test(groups = {"smoke"},
          description = "TC04 - Text box page URL is correct")
    public void testTextBoxUrl() {
        driver.get(BASE_URL + "/text-box");
        assertTrue(driver.getCurrentUrl().contains("text-box"),
            "URL should contain 'text-box'. Actual: " + driver.getCurrentUrl());
    }
}
