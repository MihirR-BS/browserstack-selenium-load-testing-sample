package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class SimpleTest {
    
    private WebDriver driver;
    private static final String HUB_URL = "http://localhost:4444/wd/hub";
    
    @BeforeMethod
    public void setUp() throws Exception {
        try {
            System.out.println("Setting up Chrome options...");
            
            // Try a simpler approach first - just basic capabilities
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("chrome");
            
            // Add Chrome options as a map
            ChromeOptions chromeOptions = new ChromeOptions();
            // Use timestamp for unique user data directory
            String uniqueUserDataDir = "/tmp/chrome-user-data-" + System.currentTimeMillis();
            chromeOptions.addArguments(
                "--headless",
                "--no-first-run",
                "--no-default-browser-check",
                "--disable-extensions",
                "--disable-default-apps",
                "--disable-gpu",
                "--disable-dev-shm-usage",
                "--disable-software-rasterizer",
                "--no-sandbox",
                "--disable-background-timer-throttling",
                "--disable-backgrounding-occluded-windows",
                "--disable-renderer-backgrounding",
                "--disable-features=TranslateUI",
                "--disable-ipc-flooding-protection",
                "--disable-web-security",
                "--disable-features=VizDisplayCompositor",
                "--disable-logging",
                "--silent"
            );
            
            System.out.println("Chrome options configured. User data dir: " + uniqueUserDataDir);
            
            // Merge the chrome options with capabilities
            
            System.out.println("Connecting to Selenium Grid at: " + HUB_URL);
            driver = new RemoteWebDriver(new URL(HUB_URL), chromeOptions);
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            
            System.out.println("Successfully connected to Selenium Grid!");
            
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Test
    public void testGoogleSearch() {
        // Go to Google
        driver.get("https://www.google.com");
        
        // Wait for 2 minutes
        try {
            Thread.sleep(120000); // 2 minutes = 120,000 milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify page title
        Assert.assertTrue(driver.getTitle().contains("Google"));
        
        // Find search box and search
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.submit();
        
        // Wait and verify results
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertTrue(driver.getTitle().contains("Selenium WebDriver"));
        
        System.out.println("Test passed: Google search working!");
    }
    
    @Test
    public void testGitHub() {
        // Go to GitHub
        driver.get("https://github.com");
        
        // Verify page loaded
        Assert.assertTrue(driver.getTitle().contains("GitHub"));
        
        // Check sign-in link exists
        WebElement signInLink = driver.findElement(By.linkText("Sign in"));
        Assert.assertTrue(signInLink.isDisplayed());
        
        System.out.println("Test passed: GitHub page loaded correctly!");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}
