package com.demo.base;

import com.demo.config.ConfigManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

/**
 * BasePage — Abstract base class for all Page Objects.
 * Pattern: Page Object Model + PageFactory
 * All page classes extend this — shared driver, waits, utilities.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout  = ConfigManager.getInstance().getTimeout();
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(driver, this); // lazy proxy initialization
    }

    // Every page must implement — verifies correct page is loaded
    public abstract boolean isPageLoaded();

    // ── Shared utility methods ─────────────────────────────────

    protected void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable: " + e.getMessage(), e);
        }
    }

    protected void safeType(WebElement element, String text) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOf(element));
            el.clear();
            el.sendKeys(text);
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible for typing: " + e.getMessage(), e);
        }
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element))
                   .getText()
                   .trim();
    }

    protected void waitForVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForUrl(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }
}
