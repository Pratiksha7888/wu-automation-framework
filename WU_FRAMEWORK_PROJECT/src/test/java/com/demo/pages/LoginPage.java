package com.demo.pages;

import com.demo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage — Page Object for https://demoqa.com/login
 * Fluent interface: methods return 'this' or next page for chaining.
 * @FindBy: PageFactory lazy proxy — element found when first accessed.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "userName")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy(id = "name")
    private WebElement loggedInUsername;

    @FindBy(css = "#output #name")
    private WebElement displayedName;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return driver.getCurrentUrl().contains("/login")
            && isDisplayed(loginButton);
    }

    public LoginPage navigateTo(String baseUrl) {
        driver.get(baseUrl + "/login");
        waitForVisible(usernameField);
        return this;
    }

    public LoginPage enterUsername(String username) {
        safeType(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        safeType(passwordField, password);
        return this;
    }

    public LoginPage clickLogin() {
        safeClick(loginButton);
        return this;
    }

    public boolean isLoggedIn() {
        try {
            waitForVisible(loggedInUsername);
            return loggedInUsername.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoggedInUsername() {
        return getText(loggedInUsername);
    }
}
