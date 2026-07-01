package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * APITest — REST API automation tests.
 * API: https://demoqa.com (BookStore API — free, no auth needed for GET)
 * Also tests: https://reqres.in (free public REST API)
 *
 * Demonstrates:
 * - RestAssured given/when/then pattern
 * - Status code validation
 * - Response body JSON path extraction
 * - Hamcrest matchers
 * - Negative test cases (404, 400)
 */
public class APITest extends BaseTest {

    // Using reqres.in — completely free public REST API for testing
    private static final String REQRES_URL = "https://reqres.in";

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = REQRES_URL;
    }

    // ── TC01: GET Users — 200 OK ───────────────────────────────
    @Test(groups = {"smoke", "api"},
          description = "TC01 - GET /users returns 200 with user list")
    public void testGetUsers() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/api/users?page=1")
        .then()
            .statusCode(200)
            .body("page",    equalTo(1))
            .body("data",    not(empty()))
            .body("data[0].id",    notNullValue())
            .body("data[0].email", notNullValue());
    }

    // ── TC02: GET Single User — 200 OK ────────────────────────
    @Test(groups = {"smoke", "api"},
          description = "TC02 - GET /users/2 returns user with id=2")
    public void testGetSingleUser() {
        Response response =
        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/api/users/2")
        .then()
            .statusCode(200)
            .body("data.id",         equalTo(2))
            .body("data.email",      notNullValue())
            .body("data.first_name", notNullValue())
            .body("data.last_name",  notNullValue())
            .extract().response();

        // Extract and validate
        String email = response.jsonPath().getString("data.email");
        assertNotNull(email, "Email should not be null");
        assertTrue(email.contains("@"), "Email should be valid format: " + email);
    }

    // ── TC03: GET Non-existent User — 404 ─────────────────────
    @Test(groups = {"smoke", "api"},
          description = "TC03 - GET non-existent user returns 404")
    public void testGetUserNotFound() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/api/users/9999")
        .then()
            .statusCode(404);
    }

    // ── TC04: POST Create User — 201 ──────────────────────────
    @Test(groups = {"smoke", "api"},
          description = "TC04 - POST /users creates user and returns 201")
    public void testCreateUser() {
        String requestBody = """
            {
                "name": "Pratiksha Khade",
                "job": "SDET"
            }
            """;

        Response response =
        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("Pratiksha Khade"))
            .body("job",  equalTo("SDET"))
            .body("id",   notNullValue())
            .extract().response();

        // Validate response fields
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(response.jsonPath().getString("name"), "Pratiksha Khade",
            "Created user name mismatch");
        soft.assertEquals(response.jsonPath().getString("job"), "SDET",
            "Created user job mismatch");
        soft.assertNotNull(response.jsonPath().getString("id"),
            "Created user should have ID");
        soft.assertNotNull(response.jsonPath().getString("createdAt"),
            "Created user should have createdAt timestamp");
        soft.assertAll();
    }

    // ── TC05: PUT Update User — 200 ───────────────────────────
    @Test(groups = {"api"},
          description = "TC05 - PUT /users/2 updates user successfully")
    public void testUpdateUser() {
        String requestBody = """
            {
                "name": "Pratiksha Khade",
                "job": "Senior SDET"
            }
            """;

        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .put("/api/users/2")
        .then()
            .statusCode(200)
            .body("name", equalTo("Pratiksha Khade"))
            .body("job",  equalTo("Senior SDET"))
            .body("updatedAt", notNullValue());
    }

    // ── TC06: Response Time Validation ────────────────────────
    @Test(groups = {"api"},
          description = "TC06 - API response time is under 3 seconds")
    public void testResponseTime() {
        long startTime  = System.currentTimeMillis();

        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/api/users?page=1")
        .then()
            .statusCode(200);

        long responseTime = System.currentTimeMillis() - startTime;
        assertTrue(responseTime < 3000,
            "API response time exceeded 3 seconds: " + responseTime + "ms");
    }
}
