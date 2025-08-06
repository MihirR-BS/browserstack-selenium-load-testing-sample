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
    public void testAddToCartBStackDemo() {
        // Visit BStackDemo
        driver.get("https://bstackdemo.com/");

        // Get name of product to add to cart (first product)
        WebElement productNameElem = driver.findElement(By.cssSelector("#\\33  > p"));
        String productToAdd = productNameElem.getText();

        // Click on add to cart
        WebElement addToCartBtn = driver.findElement(By.cssSelector("#\\33 > .shelf-item__buy-btn"));
        addToCartBtn.click();

        // Get name of item in cart
        WebElement productInCartElem = driver.findElement(By.cssSelector("#__next > div > div > div.float-cart.float-cart--open > div.float-cart__content > div.float-cart__shelf-container > div > div.shelf-item__details > p.title"));
        String productInCart = productInCartElem.getText();

        // Check if product in cart is same as one added
        Assert.assertEquals(productInCart, productToAdd);
        System.out.println("Test passed: Add to cart works!");
    }

    @Test
    public void testCheckoutFlowBStackDemo() {
        // Visit BStackDemo
        driver.get("https://bstackdemo.com/");

        // Sign in
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username svg")).click();
        driver.findElement(By.id("react-select-2-option-0-0")).click();
        driver.findElement(By.cssSelector("#password svg")).click();
        driver.findElement(By.id("react-select-3-option-0-0")).click();
        driver.findElement(By.id("login-btn")).click();

        // Wait for login
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // Click on buy item
        driver.findElement(By.cssSelector("#\\31 > .shelf-item__buy-btn")).click();
        driver.findElement(By.cssSelector("div.float-cart__close-btn")).click();
        driver.findElement(By.cssSelector("#\\32 > .shelf-item__buy-btn")).click();
        driver.findElement(By.cssSelector(".buy-btn")).click();

        // Add address details
        driver.findElement(By.id("firstNameInput")).sendKeys("first");
        driver.findElement(By.id("lastNameInput")).sendKeys("last");
        driver.findElement(By.id("addressLine1Input")).sendKeys("address");
        driver.findElement(By.id("provinceInput")).sendKeys("province");
        driver.findElement(By.id("postCodeInput")).sendKeys("pincode");

        // Checkout
        driver.findElement(By.id("checkout-shipping-continue")).click();
        driver.findElement(By.xpath("//*[text()='Continue']")).click();
        driver.findElement(By.xpath("//*[text()='Orders']")).click();

        System.out.println("Test passed: Checkout flow works!");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}
