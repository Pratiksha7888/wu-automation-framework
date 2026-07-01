package com.demo.pages;

import com.demo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TextBoxPage — Page Object for https://demoqa.com/text-box
 * Demonstrates: form fill, submit, output validation.
 * Fluent interface pattern.
 */
public class TextBoxPage extends BasePage {

    @FindBy(id = "userName")
    private WebElement fullNameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;

    @FindBy(id = "permanentAddress")
    private WebElement permanentAddressField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    // Output section
    @FindBy(id = "name")
    private WebElement outputName;

    @FindBy(id = "email")
    private WebElement outputEmail;

    @FindBy(css = "#output #currentAddress")
    private WebElement outputCurrentAddress;

    public TextBoxPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return driver.getCurrentUrl().contains("/text-box")
            && isDisplayed(fullNameField);
    }

    public TextBoxPage navigateTo(String baseUrl) {
        driver.get(baseUrl + "/text-box");
        waitForVisible(fullNameField);
        return this;
    }

    public TextBoxPage enterFullName(String name) {
        safeType(fullNameField, name);
        return this;
    }

    public TextBoxPage enterEmail(String email) {
        safeType(emailField, email);
        return this;
    }

    public TextBoxPage enterCurrentAddress(String address) {
        safeType(currentAddressField, address);
        return this;
    }

    public TextBoxPage enterPermanentAddress(String address) {
        safeType(permanentAddressField, address);
        return this;
    }

    public TextBoxPage clickSubmit() {
        scrollToElement(submitButton);
        safeClick(submitButton);
        waitForVisible(outputName);
        return this;
    }

    public String getOutputName()           { return getText(outputName); }
    public String getOutputEmail()          { return getText(outputEmail); }
    public String getOutputCurrentAddress() { return getText(outputCurrentAddress); }
}
