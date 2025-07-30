package chatbot;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;
import java.lang.reflect.Method;
import com.aventstack.extentreports.Status;

@SuppressWarnings("unused")

public class ResourceCenter
{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());

    @BeforeMethod
    public void setup(Method method)
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
        
        //Login via correct creds
        driver.get(Variables.loginURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        
        //Open Resource centre listing page
    	driver.get(Variables.RCURL);
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCLimit));
    	String RCLimit = xpaths.RCLimit.getText();
    	
        String freePlanLimit = "1/1";
        if (RCLimit.contains(freePlanLimit) && method.getName().equals("createDuplicateResourceCentreTest")) {
            throw new SkipException("Skipping test: " + method.getName() + " Skipping duplicate test because more than 1 RC can't be created on free plan.");
        }
    	
    	// Delete Resource centre if limit is full
    	for (String limit : Variables.validLimits) 
    	{
    	if (RCLimit.contains(limit))
    		{
    		actions.moveToElement(xpaths.RCDelete).perform();
            xpaths.RCDelete.click();
            xpaths.confirmDelete.click();
            Variables.Sleep();
            wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource centre deleted successfully"), "Resource centre was not deleted");
        	Variables.Sleep();
    		}
    	}
    }
 // Verify user is able to create Knowledge base
    @Test (priority = 1, description = "Verify user is able to create Knowledge base")
    public void createResourceCentreTest()
    {
    	helper.clickElement(xpaths.createRC);
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCName)).sendKeys(Variables.testKB + timestamp);
    	Variables.Sleep();
    	xpaths.RCProceed.click();
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource centre created successfully"), "Resource centre was not created");
    }

 
    
 // Verify user is not able to edit a Resource centre
    @Test (priority = 2, description = "Verify user is able to edit a Resource centre")
    public void editResourceCentreTest()
    {
    	if (!driver.findElements(By.xpath("//div[contains(@class, 'support-article')]")).isEmpty()) {
    	    try {
    	        helper.clickElement(xpaths.RCOpen);
    	        Variables.Sleep();
    	    } catch (Exception e) {
    	        Report.logInfo("Failed to click RCOpen: " + e.getMessage());
    	    }
    	} else {
    	    helper.clickElement(xpaths.createRC);
    	    Variables.Sleep();
        	wait.until(ExpectedConditions.visibilityOf(xpaths.RCName)).sendKeys(Variables.testKB + timestamp);
        	Variables.Sleep();
        	xpaths.RCProceed.click();
        	Variables.Sleep();
    	}
    	Variables.Sleep();
    	helper.clickElement(xpaths.RCEdit);
    	Variables.Sleep();
    	helper.enterText(xpaths.RCEditInputl,timestamp + Variables.testKB);
    	Variables.Sleep();
    	helper.clickElement(xpaths.RCNameChange);
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource Centre name changed successfully"), "Resource centre name was not changed, try again");
    }
    
 // Verify user is able to crawl a website and stop it
    @Test (priority = 3, description = "Verify user is able to crawl a website and stop it")
    public void crawlerTest()
    {
    	Variables.Sleep();
    	
    	if (xpaths.RCStatus.getText().equals("empty"))
    	{
    		helper.clickElement(xpaths.RCOpen);
    		Variables.Sleep();
    		helper.enterText(xpaths.CrawlerInputField, Variables.CrawlWebsite);
    	}
    	
    	Variables.Sleep();
    	if (!xpaths.RCStatus.getText().equals("empty"))
    	{
    		helper.clickElement(xpaths.RCOpen);
    		Variables.Sleep();
    		helper.clickElement(xpaths.AddWebsiteLink);
    		Variables.Sleep();
    		helper.enterText(xpaths.CrawlerInputField, Variables.CrawlWebsite);
    	}
    	Variables.Sleep();
    	xpaths.StartWebsiteCrawling.click();
    	Variables.Sleep();
    	helper.clickElement(xpaths.CrawlConfirm);
    	Variables.Sleep();
    	helper.clickElement(xpaths.StopCrawl);
    	xpaths.ConfirmStopCrawl.click();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("Crawling stopped") || xpaths.toastMessage.getText().contains("Your request has been received"), "Check crawling manually");
    }
    
 // Verify user is not able to create a duplicate Knowledge base
    @Test (priority = 4, description = "Verify user is not able to create a duplicate Knowledge base")
    public void createDuplicateResourceCentreTest()
    {
    	helper.clickElement(xpaths.createRC);
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCName)).sendKeys(Variables.testKB);
    	Variables.Sleep();
    	xpaths.RCProceed.click();
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource centre name already exist"), "Resource centre was created, try again");
    }
    
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