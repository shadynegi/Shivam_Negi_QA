package chatbot;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")

public class SignUp

{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());
    

 // Setup method to initialize ChromeDriver with options
    @BeforeClass
    public void setUp()
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
    }

 // Navigate to the signup page before each test method
    @BeforeMethod
    public void navigateToSignUpPage()
    {
        driver.get(Variables.signUpUrl);
        ((JavascriptExecutor) driver).executeScript("window.focus(); document.body.focus();");
        JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
    }
 
 // Test to verify presence of all required signup fields
    @Test(priority = 1, description = "Test to verify presence of all required signup fields")
    public void verifyAllFieldsPresent()
    {
        Assert.assertTrue(xpaths.signUpFirstName.isDisplayed());
        Assert.assertTrue(xpaths.signUpLastName.isDisplayed());
        Assert.assertTrue(xpaths.signUpEmail.isDisplayed());
        Assert.assertTrue(xpaths.signUpPassword.isDisplayed());
        Assert.assertTrue(xpaths.signUpConfirmPassword.isDisplayed());
    }

 // Test valid signup process with correct details
    @Test(priority = 2, description = "Test valid signup process with correct details")
    public void validSignupTest()
    {
    	xpaths.signUpFirstName.sendKeys("shivam");
    	xpaths.signUpLastName.sendKeys("negi");
    	helper.clickElement(xpaths.dropdown);
    	Variables.Sleep();
    	
    	//select India as country
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].click();", xpaths.india);
    	//xpaths.india.click();
    	xpaths.signUpEmail.sendKeys("automatedUser" + timestamp + "@yopmail.com");
    	xpaths.signUpPassword.sendKeys(Variables.validPassword);
    	xpaths.signUpConfirmPassword.sendKeys(Variables.validPassword);
    	xpaths.tandc.click();
    	xpaths.signUpButton.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.signUpToast));
        Assert.assertTrue(xpaths.signUpToast.getText().contains("Registration Successful. Please check your email for verification"), "Check Email");
    }

 // Test signup with an already registered email
    @Test(priority = 3, description = "Test signup with an already registered email")
    public void signupWithRegisteredEmail()
    {
    	xpaths.signUpFirstName.sendKeys("shivam");
    	xpaths.signUpLastName.sendKeys("negi");
    	xpaths.dropdown.click();
    	Variables.Sleep();
    	
    	//select India as country
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].click();", xpaths.india);
    	//xpaths.india.click();
    	xpaths.signUpEmail.sendKeys(Variables.validEmail);
    	xpaths.signUpPassword.sendKeys(Variables.validPassword);
    	xpaths.signUpConfirmPassword.sendKeys(Variables.validPassword);
    	xpaths.tandc.click();
    	xpaths.signUpButton.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("This email already exists"), "Check Email");
    }

 // Test for password and confirm password mismatch validation
    @Test(priority = 4, description = "Test for password and confirm password mismatch validation")
    public void passwordMismatchTest()
    {
    	xpaths.signUpPassword.sendKeys(Variables.validPassword);
    	xpaths.signUpConfirmPassword.sendKeys(Variables.invalidPassword);
    	xpaths.signUpButton.click();
        Assert.assertTrue(xpaths.confirmPasswordMatchValidation.getText().contains("Password and confirm password must match"), "Check passwords");
    }

 // Test for validation message with invalid email format
    @Test(priority = 5, description = "Test for validation message with invalid email format")
    public void invalidEmailFormatTest()
    {
    	xpaths.signUpEmail.sendKeys(Variables.invalidFormatEmail);
    	xpaths.signUpButton.click();
        Assert.assertTrue(xpaths.emailValidationText.getText().contains("Please enter a valid email address!"), "Check Email");
    }

 // Test for blank required fields and validation messages
    @Test(priority = 6, description = "Test for blank required fields and validation messages")
    public void blankRequiredFieldsTest()
    {
    	helper.clickElement(xpaths.signUpButton);
    	Variables.Sleep();
    	
    	Assert.assertTrue(xpaths.firstNameValidationText.getText().contains("Please enter first name!"), "check first name");
    	Assert.assertTrue(xpaths.lastNameValidationText.getText().contains("Please enter last name!"), "check last name");
    	Assert.assertTrue(xpaths.countryValidation.getText().contains("Please select country!"), "check country");
    	Assert.assertTrue(xpaths.emailValidation.getText().contains("Please enter email!"), "check email");
    	Assert.assertTrue(xpaths.passwordValidation.getText().contains("Please enter password!"), "check password");
    	Assert.assertTrue(xpaths.confirmPasswordValidation.getText().contains("Please enter confirm password!"), "check confirm password");
    	Assert.assertTrue(xpaths.tandcValidation.getText().contains("You must agree to the Terms and Conditions to continue"), "check T and C");
    	
    }

 // Test password strength indicator with weak password
    @Test(priority = 7, description = "Test password strength indicator with weak password")
    public void passwordStrengthCheck()
    {
    	xpaths.signUpPassword.sendKeys("123");
    	xpaths.signUpButton.click();
    	Assert.assertTrue(xpaths.weakPasswordValidation.getText().contains("Your password should have at least one special character, digits, uppercase, and lowercase characters"));
    }

 // Test that password field is masked (hidden input)
    @Test(priority = 8, description = "Test that password field is masked (hidden input)")
    public void passwordMaskingCheck()
    {
        Assert.assertEquals(xpaths.signUpPassword.getAttribute("type"), "password");
        Assert.assertEquals(xpaths.signUpConfirmPassword.getAttribute("type"), "password");
    }

 // Test navigation from signup to login page
    @Test(priority = 9, description = "Test navigation from signup to login page")
    public void loginPageNavigationTest()
    {
    	helper.clickElement(xpaths.signIn);
    	wait.until(ExpectedConditions.urlContains(Variables.loginURL));
        Assert.assertTrue(driver.getCurrentUrl().equals(Variables.loginURL));
    }

 // Tear down method to quit the browser after all tests
    @AfterClass
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
