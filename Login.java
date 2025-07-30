package chatbot;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.Status;

@SuppressWarnings("unused")

public class Login
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
    
    @BeforeClass (description = "Verify is the password is Test@123 as default, if not change to default")
    public void CheckPassword()
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
        
        driver.get(Variables.loginURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
        
     // check password
        wait.until(ExpectedConditions.visibilityOf(xpaths.LoginEmail));
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        
        if (xpaths.toastMessage.getText().contains("Login successful"))
        {
        	Variables.Sleep();
        	driver.quit();
        }
        else
        	//reset password
        {
        	driver.get(Variables.resetURL);
    	    xpaths.LoginEmail.sendKeys(Variables.validEmail);
    	    xpaths.submit.click();
    	    driver.get(Variables.yopmailURL);
    	    xpaths.yopmailSearch.sendKeys(Variables.validEmail);
        	xpaths.yopmailSearch.sendKeys(Keys.ENTER);
        	driver.switchTo().frame("ifmail"); // switch to yopmail inbox
        	helper.clickElement(xpaths.yopmailResetButton);
        	
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
        	Variables.Sleep();
        	driver.quit();
        } 
    }
    

 // Start the browser and Navigate to the login page before each test method
    @BeforeMethod
    public void navigateToLogin() 
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Variables.maxWait));
        xpaths = new Xpaths(driver);
        driver.get(Variables.loginURL);
    }

 // Test case 1: Valid login credentials should redirect to the dashboard
    @Test(priority = 1, description = "Valid login credentials should redirect to the dashboard")
    public void validLoginTest() 
    {
    	xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        
     // Assert that the toast message contains "Login successful"
        Assert.assertTrue(xpaths.toastMessage.getText().contains("Login successful"), "Check Email and Password");
    }

 // Test case 2: Invalid login with wrong password
    @Test(priority = 2, description = "Invalid login with wrong password")
    public void invalidPasswordTest() 
    {
    	xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.invalidPassword);
        xpaths.LoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        Assert.assertTrue(xpaths.toastMessage.getText().contains("Password is incorrect."), "Check email");
    }

 // Test case 3: Invalid login with unregistered email
    @Test(priority = 3, description = "Invalid login with unregistered email")
    public void unregisteredEmailTest() 
    {
    	xpaths.LoginEmail.sendKeys(Variables.invalidEmail);
    	xpaths.LoginPassword.sendKeys(Variables.validPassword);
    	xpaths.LoginButton.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("User not found."), "toast message not found");
    }

 // Test case 4: Attempt login with blank fields
    @Test(priority = 4, description = "Attempt login with blank fields")
    public void emptyFieldsTest() 
    {
    	xpaths.LoginButton.click();
    	Assert.assertEquals(xpaths.emptyEmailValidation.getText(), "Please enter email!", "Incorrect email validation message");
        Assert.assertEquals(xpaths.emptyPasswordValidation.getText(), "Please enter password!", "Incorrect password validation message");
    }

 // Test case 5: Attempt login with only email
    @Test(priority = 5, description = "Attempt login with only email")
    public void onlyEmailTest() 
    {
    	xpaths.LoginEmail.sendKeys(Variables.validEmail);
    	xpaths.LoginButton.click();
    	Assert.assertEquals(xpaths.emptyPasswordValidation.getText(), "Please enter password!", "Incorrect password validation message");
    }

 // Test case 6: Attempt login with only password
    @Test(priority = 6, description = "Attempt login with only password")
    public void onlyPasswordTest()
    {
    	xpaths.LoginPassword.sendKeys(Variables.validPassword);
    	xpaths.LoginButton.click();
    	Assert.assertEquals(xpaths.emptyEmailValidation.getText(), "Please enter email!", "Incorrect email validation message");
    }

 // Test case 7: Check if email format validation is working
    @Test(priority = 7, description = "Check if email format validation is working")
    public void invalidEmailFormatTest() 
    {
    	xpaths.LoginEmail.sendKeys(Variables.invalidFormatEmail);
    	xpaths.LoginPassword.sendKeys(Variables.validPassword);
    	xpaths.LoginButton.click();
    	Assert.assertEquals(xpaths.invalidEmailValidation.getText(), "Please enter a valid email address!", "Incorrect email validation message");
    }

 // Test case 8: Ensure password input is masked
    @Test(priority = 8, description = "Ensure password input is masked")
    public void passwordFieldMaskingTest() 
    {
        Assert.assertEquals(xpaths.LoginPassword.getAttribute("type"), "password", "Password is not masked");
    }

 // Test case 9: Verify the presence of "Forgot Password" link
    @Test(priority = 9, description = "Verify the presence of \"Forgot Password\" link")
    public void forgotPasswordLinkTest() 
    {
    	Variables.Sleep();
        Assert.assertTrue(xpaths.forgotPassword.isDisplayed(), "Forgot Password link is not displayed");
    }

 // Test case 10: Check if navigation to Sign Up page works
    @Test(priority = 10, description = "Check if navigation to Sign Up page works")
    public void signUpNavigationTest()
    {
    	wait.until(ExpectedConditions.visibilityOf(xpaths.signUp));
    	xpaths.signUp.click();
    	wait.until(ExpectedConditions.urlContains("signup"));
        Assert.assertTrue(driver.getCurrentUrl().contains("signup"), "Navigation to Sign Up failed");
    }

 // Close the browser after all tests are completed
    @AfterMethod
    public void tearDown() {
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