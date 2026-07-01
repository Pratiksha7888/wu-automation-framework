package com.demo.base;

import com.demo.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;

/**
 * DriverFactory — Factory Method Pattern + ThreadLocal.
 * Factory Method: hides browser creation — tests never call new ChromeDriver().
 * ThreadLocal: each parallel test thread gets its own WebDriver instance.
 */
public class DriverFactory {

    // ThreadLocal — thread-safe driver per test thread
    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driverHolder.get() == null) {
            initDriver();
        }
        return driverHolder.get();
    }

    private static void initDriver() {
        String browser  = ConfigManager.getInstance().getBrowser();
        boolean headless = ConfigManager.getInstance().isHeadless();

        WebDriver driver = switch (browser.toLowerCase()) {
            case "chrome"  -> createChrome(headless);
            case "firefox" -> createFirefox(headless);
            case "edge"    -> createEdge(headless);
            default        -> createChrome(headless); // default chrome
        };

        driver.manage().timeouts()
            .pageLoadTimeout(Duration.ofSeconds(30))
            .implicitlyWait(Duration.ofSeconds(0)); // use explicit waits only

        driverHolder.set(driver);
    }

    private static WebDriver createChrome(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        opts.addArguments("--disable-gpu");
        if (headless) {
            opts.addArguments("--headless=new");
            opts.addArguments("--window-size=1920,1080");
        }
        return new ChromeDriver(opts);
    }

    private static WebDriver createFirefox(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions opts = new FirefoxOptions();
        if (headless) opts.addArguments("--headless");
        return new FirefoxDriver(opts);
    }

    private static WebDriver createEdge(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions opts = new EdgeOptions();
        if (headless) opts.addArguments("--headless=new");
        return new EdgeDriver(opts);
    }

    public static void quitDriver() {
        WebDriver driver = driverHolder.get();
        if (driver != null) {
            driver.quit();
            driverHolder.remove(); // prevent memory leak!
        }
    }
}
