package chatbot;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ResetPassword
{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());

 // Set up ChromeDriver before all tests
    @BeforeMethod
    public void setUp(Method method)
    {
    	//setup of browser
ChromeOptions options = new ChromeOptions();
        
        // Essential Chrome arguments for stability
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--remote-allow-origins=*");
        
        // Network and connection stability
        options.addArguments("--disable-ipc-flooding-protection");
        options.addArguments("--disable-client-side-phishing-detection");
        options.addArguments("--disable-sync");
        options.addArguments("--metrics-recording-only");
        options.addArguments("--no-first-run");
        options.addArguments("--safebrowsing-disable-auto-update");
        options.addArguments("--enable-automation");
        options.addArguments("--password-store=basic");
        options.addArguments("--use-mock-keychain");

        // Remote debugging on a different port to avoid conflicts
        options.addArguments("--remote-debugging-port=0"); // Let Chrome choose available port
        
        // Notification preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 1); // Allow notifications
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.managed_default_content_settings.images", 1);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    	
    	wait = new WebDriverWait(driver, Duration.ofSeconds(Variables.maxWait));
    	wait.pollingEvery(Duration.ofMillis(1000)); // Poll every 1s
    	xpaths = new Xpaths(driver);
    	helper = new Helper(driver, wait);
    	actions = new Actions(driver);
        
     // Conditional URL based on test method name
        if (method.getName().equals("emailTestWithOldPass") || method.getName().equals("emailTestWithNewPass"))
        {
        	driver.get(Variables.resetURL);
        	xpaths.LoginEmail.sendKeys(Variables.validEmail);
        	xpaths.submit.click();
            driver.get(Variables.yopmailURL);
            Variables.Sleep();
        }
        else
        {
            driver.get(Variables.resetURL);
        }
        
    }

 // Test 1: Check if email input field is visible
    @Test(priority = 1, description = "Check if email input field is visible")
    public void emailFieldPresenceTest()
    {
        Assert.assertTrue(xpaths.LoginEmail.isDisplayed(), "Email input field is not present");
    }

 // Test 2: Submit a valid registered email and expect a confirmation toast
    @Test(priority = 2, description = "Submit a valid registered email and expect a confirmation toast")
    public void validRegisteredEmailTest()
    {
    	xpaths.LoginEmail.sendKeys(Variables.validEmail);
    	xpaths.submit.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        Assert.assertEquals(xpaths.toastMessage.getText(), "Email sent successfully", "No confirmation after valid email submission");
    }

 // Test 3: Submit an unregistered email and expect an error toast
    @Test(priority = 3, description = "Submit an unregistered email and expect an error toast")
    public void unregisteredEmailTest()
    {
    	xpaths.LoginEmail.sendKeys(Variables.invalidEmail);
    	xpaths.submit.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        Assert.assertEquals(xpaths.toastMessage.getText(), "No user found for this email.", "No error for unregistered email");
    }

 // Test 4: Submit invalid email format and check for HTML5 validation
    @Test(priority = 4, description = "Submit invalid email format and check for HTML5 validation")
    public void invalidEmailFormatTest()
    {
    	xpaths.LoginEmail.sendKeys(Variables.invalidFormatEmail);
    	xpaths.submit.click();
    	Assert.assertEquals(xpaths.invalidEmailValidation.getText(), "Please enter a valid email address!", "Incorrect email validation message");
    }

 // Test 5: Click submit without entering email and expect validation error
    @Test(priority = 5, description = "Click submit without entering email and expect validation error")
    public void emptyEmailFieldTest()
    {
    	xpaths.submit.click();
    	Assert.assertEquals(xpaths.emptyEmailValidation.getText(), "Please enter email!", "Incorrect email validation message");
    }
    
 // Test 6: Measure response time for valid reset password request
    @Test(priority = 6, description = "Measure response time for valid reset password request")
    public void responseTimeTest()
    {
        long start = System.currentTimeMillis();
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.submit.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage)).getText();
        long end = System.currentTimeMillis();
        Assert.assertTrue((end - start) < 10000, "Response time exceeded 10 seconds");
    }
    
 // Test 7: Check email is received and user types in old password
    @Test(priority = 7, description = "Check email is received and user types in old password")
    public void emailTestWithOldPass()
    {
    	xpaths.yopmailSearch.sendKeys(Variables.validEmail);
    	Variables.Sleep();
    	xpaths.yopmailSearch.sendKeys(Keys.ENTER);
    	Variables.Sleep();
    	driver.switchTo().frame("ifmail"); // switch to yopmail inbox
    	wait.until(ExpectedConditions.visibilityOf(xpaths.yopmailResetButton)).click();
    	
     // Switch to the new tab
    	for (String windowHandle : driver.getWindowHandles())
    	{
    	    driver.switchTo().window(windowHandle);
    	}
    	Variables.Sleep();
    	xpaths.newPassword.sendKeys(Variables.validPassword);
    	xpaths.confirmPassword.sendKeys(Variables.validPassword);
    	xpaths.resetPassword.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("New password should be different form old password"), "Check Password");
    }
    
 // Test 8: Check email is received and user types in new password
    @Test(priority = 8, description = "Check email is received and user types in new password")
    public void emailTestWithNewPass()
    {
    	xpaths.yopmailSearch.sendKeys(Variables.validEmail);
    	Variables.Sleep();
    	xpaths.yopmailSearch.sendKeys(Keys.ENTER);
    	Variables.Sleep();
    	driver.switchTo().frame("ifmail"); // switch to yopmail inbox
    	wait.until(ExpectedConditions.visibilityOf(xpaths.yopmailResetButton)).click();
    	
     // Switch to the new tab
    	for (String windowHandle : driver.getWindowHandles()) 
    	{
    	    driver.switchTo().window(windowHandle);
    	}
    	Variables.Sleep();
    	xpaths.newPassword.sendKeys(Variables.newPassword);
    	xpaths.confirmPassword.sendKeys(Variables.newPassword);
    	xpaths.resetPassword.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("Password changed successfully."), "Check Password");
    }
    
    @AfterMethod
    public void tearDown(Method method)
    {
    	if (method.getName().equals("emailTestWithNewPass")) 
    	{
    	    driver.get(Variables.resetURL);
    	    xpaths.LoginEmail.sendKeys(Variables.validEmail);
    	    xpaths.submit.click();
    	    driver.get(Variables.yopmailURL);
    	    xpaths.yopmailSearch.sendKeys(Variables.validEmail);
    	    Variables.Sleep();
        	xpaths.yopmailSearch.sendKeys(Keys.ENTER);
        	Variables.Sleep();
         // switch to yopmail inbox
        	driver.switchTo().frame("ifmail");
        	wait.until(ExpectedConditions.visibilityOf(xpaths.yopmailResetButton)).click();
        	
         // Switch to the new tab
        	for (String windowHandle : driver.getWindowHandles())
        	{
        	    driver.switchTo().window(windowHandle);
        	}
        	wait.until(ExpectedConditions.visibilityOf(xpaths.newPassword));
        	xpaths.newPassword.sendKeys(Variables.validPassword);
        	xpaths.confirmPassword.sendKeys(Variables.validPassword);
        	xpaths.resetPassword.click();
        	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	}

    	if (driver != null) {
            try {
                Variables.Sleep();
                driver.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
    	}
    }
}