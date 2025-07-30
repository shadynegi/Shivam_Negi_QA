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

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Map;
import java.util.HashMap;
import com.aventstack.extentreports.Status;

@SuppressWarnings("unused")

public class Initialization
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
    public void CheckAccount()
    {
    	 // Setup WebDriverManager with specific Chrome version handling
        WebDriverManager.chromedriver().setup();
        
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
        
     // check account exists
        wait.until(ExpectedConditions.visibilityOf(xpaths.LoginEmail));
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        
        if (xpaths.toastMessage.getText().contains("User not found."))
        {
        	//create account
        	driver.get(Variables.loginURL);
        	helper.clickElement(xpaths.signUp);
        	Variables.Sleep();
        	xpaths.signUpFirstName.sendKeys("shivam");
        	xpaths.signUpLastName.sendKeys("negi");
        	helper.clickElement(xpaths.dropdown);
        	xpaths.india.click();
        	xpaths.signUpEmail.sendKeys(Variables.validEmail);
        	xpaths.signUpPassword.sendKeys(Variables.validPassword);
        	xpaths.signUpConfirmPassword.sendKeys(Variables.validPassword);
        	xpaths.tandc.click();
        	xpaths.signUpButton.click();
        	wait.until(ExpectedConditions.visibilityOf(xpaths.signUpToast));
            Assert.assertTrue(xpaths.signUpToast.getText().contains("Registration Successful. Please check your email for verification"), "Check Email");
            
         // verify on yopmail
            Variables.Sleep();
            driver.get(Variables.yopmailURL);
            Variables.Sleep();
            xpaths.yopmailSearch.sendKeys(Variables.validEmail);
        	xpaths.yopmailSearch.sendKeys(Keys.ENTER);
        	Variables.Sleep();
        	driver.switchTo().frame("ifmail"); // switch to yopmail inbox
        	Variables.Sleep();
        	helper.clickElement(xpaths.yopmailVerifyButton);
        	Variables.Sleep();
        	
         // Switch to the new tab
        	for (String windowHandle : driver.getWindowHandles()) 
        	{
        	    driver.switchTo().window(windowHandle);
        	}
        	Variables.Sleep();
        	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        	
        	    if (xpaths.toastMessage.getText().contains("User verified successfully")) {
        	        Reporter.log("Login and verification successful", true);
        	    } else {
        	        Assert.fail("Check account manually");
        	    }
        	    driver.get(Variables.loginURL);
            	xpaths.LoginEmail.sendKeys(Variables.validEmail);
                xpaths.LoginPassword.sendKeys(Variables.validPassword);
                xpaths.LoginButton.click();
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
                
             // Assert that the toast message contains "Login successful"
                Assert.assertTrue(xpaths.toastMessage.getText().contains("Login successful"), "Check Email and Password");
                
                //create new RC
                driver.get(Variables.RCURL);
                Variables.Sleep();
                
                //close guide modal
                try {
                	wait.until(ExpectedConditions.visibilityOf(xpaths.gotItButton));
                if (xpaths.gotItButton.isDisplayed()) {
                	xpaths.gotItButton.click();
                }
            } catch (Exception e) {
                Report.logInfo("No guide modal openend");
            }
                helper.clickElement(xpaths.createRC);
            	wait.until(ExpectedConditions.visibilityOf(xpaths.RCName)).sendKeys(Variables.testKB);
            	xpaths.RCProceed.click();
            	Variables.Sleep();
            	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            	Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource centre created successfully"), "Resource centre was not created");
            	
            	//create private RC
            	Variables.Sleep();
            	driver.get(Variables.RCURL);
                Variables.Sleep();
                helper.clickElement(xpaths.createRC);
                Variables.Sleep();
            	xpaths.privateRC.click();
                Variables.Sleep();
            	wait.until(ExpectedConditions.visibilityOf(xpaths.RCName)).sendKeys(Variables.testKB + " private");
            	Variables.Sleep();
            	xpaths.RCProceed.click();
            	Variables.Sleep();
            	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            	Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource centre created successfully"), "Resource centre was not created");
            	
            	//create new chatbot
            	driver.get(Variables.chatbotURL);
            	Variables.Sleep();
            	
            	//close guide modal
            	try {
                	wait.until(ExpectedConditions.visibilityOf(xpaths.gotItButton));
                if (xpaths.gotItButton.isDisplayed()) {
                	xpaths.gotItButton.click();
                }
            } catch (Exception e) {
                Report.logInfo("No guide modal openend");
            }
            	helper.clickElement(xpaths.createNewChatbot);
            	Variables.Sleep();
            	js.executeScript("document.body.style.zoom='80%'");
            	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName));
            	xpaths.chatbotName.sendKeys(Variables.chatbotName);
            	xpaths.resourceCenterDropdown.click();
            	Variables.Sleep();
            	xpaths.listItem.click();
            	Variables.Sleep();
                xpaths.industry.sendKeys(Variables.industry);
                helper.scrollToElement(xpaths.proceedButton);
                xpaths.ChatbotPurposeDropdown.click();
                Variables.Sleep();
                xpaths.ChatbotPurposeListing.click();
                
                // proceed to next form
                xpaths.proceedButton.click();
                
                wait.until(ExpectedConditions.visibilityOf(xpaths.businessName)).sendKeys(Variables.businessName);
            	xpaths.businessWebsite.sendKeys(Variables.businessWebsite);
            	xpaths.businessAddress.sendKeys(Variables.businessAddress);
            	xpaths.businessEmail.sendKeys(Variables.businessEmail);
            	xpaths.businessPhone.sendKeys(Variables.businessPhone);
            	xpaths.chatbotLogoUpload.sendKeys(Variables.LogoUploadFile);
            	Variables.Sleep();
            	helper.scrollToElement(xpaths.chatbotLogoConfirm);
            	xpaths.chatbotLogoConfirm.click();
            	Variables.Sleep();
            	Variables.Sleep();
            	helper.scrollToElement(xpaths.showBusinessDetailsToggle);
            	helper.clickElement(xpaths.showBusinessDetailsToggle);
            	
            	// create chatbot
            	xpaths.createButton.click();
            	Variables.Sleep();
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
                Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent created successfully"), "Chatbot was not created");
                
                // buy plan
//                driver.get(Variables.buyPlanURL);
//                Variables.Sleep();
//                helper.clickElement(xpaths.yearlyToggle);
//                Variables.Sleep();
//                helper.clickElement(xpaths.upgradeButton);
//                Variables.Sleep();
//                helper.clickElement(xpaths.Proceed);
                //add production buy plan code here
                
        	}
        else
        {
        	Assert.assertTrue(xpaths.toastMessage.getText().contains("Login successful"));
        }
    }

 // create RC
    @Test(description = "Verify user account exists, if not create a new account, new RC and new chatbot")
    public void createAcc()
    {
    	Report.logInfo("Automation test account Created successfully, Proceeding with test case execution");
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