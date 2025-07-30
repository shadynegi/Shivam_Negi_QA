package chatbot;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings ("unused")

public class ClassforTestingPurpose
{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());
    
    public WebDriver getDriver()
    {
        return driver;
    }
    
    @BeforeClass
    public void Test()
    {
    	// Set up WebDriver using WebDriverManager
    	WebDriverManager.chromedriver().setup();
    	
    	//setup of browser
    	ChromeOptions options = new ChromeOptions();

    	//comment this if performing server testing
    	options.addArguments("--start-maximized");
    	options.addArguments("--disable-popup-blocking");
    	options.addArguments("--no-sandbox");
    	options.addArguments("--disable-dev-shm-usage");
    	Map<String, Object> prefs = new HashMap<>();
    	prefs.put("profile.default_content_setting_values.notifications", 1); // 1 = Allow, 2 = Block, 0 = Ask
    	options.setExperimentalOption("prefs", prefs);

    	driver = new ChromeDriver(options);
    	wait = new WebDriverWait(driver, Duration.ofSeconds(Variables.maxWait));
    	xpaths = new Xpaths(driver);
    	helper = new Helper(driver, wait);
    	actions = new Actions(driver);
        
        driver.get(Variables.loginURL);
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        Variables.Sleep();
    }

 // Verify everything
    @Test()
    public void Test2()
    {

    	
    	
    }

 // Close the browser after all tests are completed
    @AfterMethod
    public void tearDown() 
    {
        if (driver != null)
        {
        	Variables.Sleep();
        	driver.quit();
        }
    }
}