package chatbot;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.interactions.Actions;
import com.aventstack.extentreports.Status;

@SuppressWarnings("unused")

public class ChatbotCreation
{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());
    
  //Setup method to initialize WebDriver, login, and navigate to chatbot creation page.
    @BeforeMethod
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
        
        driver.get(Variables.loginURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
    	
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        
        Variables.Sleep();
        helper.clickElement(xpaths.chatbotHub);
        Variables.Sleep();
    	js.executeScript("document.body.style.zoom='80%'");
    	Variables.Sleep();
    	
        try {
        	wait.until(ExpectedConditions.visibilityOf(xpaths.gotItButton));
        if (xpaths.gotItButton.isDisplayed()) {
        	xpaths.gotItButton.click();
        }
        } catch (Exception e) {
        Report.logInfo("No guide modal openend");
    	}
        
        helper.clickElement(xpaths.createChatbot);
        Variables.Sleep();
        
        if (helper.isElementDisplayed(xpaths.toastMessage))
        {
        	String chatbotLimit = xpaths.toastMessage.getText();

     // If chatbot limit is reached, deletes the existing one to continue testing.
        if (chatbotLimit.contains("Chat Agent limit reached."))
            {
                actions.moveToElement(xpaths.deleteChatbot).perform();
                xpaths.deleteChatbot.click();
                xpaths.confirmDelete.click();
                Variables.Sleep();
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
                Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent deleted successfully"), "Chatbot was not deleted");
            	Variables.Sleep();
            	helper.clickElement(xpaths.createChatbot);
            	Variables.Sleep();
            	js.executeScript("document.body.style.zoom='80%'");
            	Variables.Sleep();
            }
        }
    }

 // Test to verify that all required fields are present on the chatbot creation page.
    @Test(priority = 1, description = "Test to verify that all required fields are present on the chatbot creation page.")
    public void verifyAllFieldsPresent()
    {  
    	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName));
        Assert.assertTrue(xpaths.chatbotName.isDisplayed(), "chatbot name not displayed");
        Variables.Sleep();
        Assert.assertTrue(xpaths.resourceCenterDropdown.isDisplayed(), "resource center not displayed");
        Assert.assertTrue(xpaths.industry.isDisplayed(), "industry not displayed");
        Variables.Sleep();
        wait.until(ExpectedConditions.visibilityOf(xpaths.resourceCenterDropdown));
        
        Variables.Sleep();
        xpaths.chatbotName.sendKeys(Variables.chatbotName);
        Variables.Sleep();
        xpaths.resourceCenterDropdown.click();
        xpaths.listItem.click();
        Variables.Sleep();
        xpaths.industry.sendKeys("industry 1");
        Variables.Sleep();
        helper.scrollToElement(xpaths.proceedButton);
        xpaths.ChatbotPurposeDropdown.click();
        Variables.Sleep();
        xpaths.ChatbotPurposeListing.click();
        
        xpaths.proceedButton.click();
        
        wait.until(ExpectedConditions.visibilityOf(xpaths.businessName));
        Assert.assertTrue(xpaths.businessName.isDisplayed(), "business name not displayed");
        Assert.assertTrue(xpaths.businessWebsite.isDisplayed(), "business website not displayed");
        Assert.assertTrue(xpaths.businessAddress.isDisplayed(), "business address not displayed");
        Assert.assertTrue(xpaths.businessEmail.isDisplayed(), "business email not displayed");
        Assert.assertTrue(xpaths.businessPhone.isDisplayed(), "business phone not displayed");      
        Assert.assertTrue(xpaths.chatbotLogo.isDisplayed(), "chatbot logo not displayed");
        Assert.assertTrue(xpaths.showBusinessDetailsToggle.isDisplayed(), "business toggle not displayed");
        Assert.assertTrue(xpaths.createButton.isDisplayed(), "create button not displayed");
    }
    
 // Test to create a chatbot with valid details.
    @Test(priority = 2, description = "Test to create a chatbot with valid details.")
    public void validChatbotCreation() 
    {
    	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName));
    	xpaths.chatbotName.sendKeys(Variables.chatbotName + timestamp);
    	Variables.Sleep();
    	xpaths.resourceCenterDropdown.click();
    	Variables.Sleep();
    	xpaths.listItem.click();
    	Variables.Sleep();
        xpaths.industry.sendKeys(Variables.industry);
        helper.scrollToElement(xpaths.proceedButton);
        xpaths.ChatbotPurposeDropdown.click();
        Variables.Sleep();
        xpaths.ChatbotPurposeListing.click();
        Variables.Sleep();
        
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
    	helper.scrollToElement(xpaths.showBusinessDetailsToggle);
    	helper.clickElement(xpaths.showBusinessDetailsToggle);
    	Variables.Sleep();
    	
    	xpaths.createButton.click();
    	Variables.Sleep();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent created successfully"), "Chatbot was not created");
    }

 // Test to validate form behavior when an invalid email format is entered.
    @Test(priority = 3, description = "Test to validate form behavior when an invalid email format is entered.")
    public void invalidEmailFormatTest()
    {
    	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName)).sendKeys(Variables.chatbotName + timestamp);
    	helper.clickElement(xpaths.resourceCenterDropdown);
    	Variables.Sleep();
    	xpaths.listItem.click();
    	Variables.Sleep();
        xpaths.industry.sendKeys(Variables.industry);
        helper.scrollToElement(xpaths.proceedButton);
        xpaths.ChatbotPurposeDropdown.click();
        Variables.Sleep();
        xpaths.ChatbotPurposeListing.click();
        
        xpaths.proceedButton.click();
        
        wait.until(ExpectedConditions.visibilityOf(xpaths.businessName)).sendKeys(Variables.businessName);
    	xpaths.businessWebsite.sendKeys(Variables.businessWebsite);
    	xpaths.businessAddress.sendKeys(Variables.businessAddress);    
    	xpaths.businessEmail.sendKeys(Variables.invalidFormatEmail); //invalid email
    	xpaths.businessPhone.sendKeys(Variables.businessPhone);
    	xpaths.createButton.click();
        Assert.assertTrue(xpaths.businessEmail.getAttribute("validationMessage").length() > 0);
    }

 // Test to check validation messages when required fields are left blank.
    @Test(priority = 4, description = "Test to check validation messages when required fields are left blank.")
    public void blankRequiredFieldsTest() 
    {
    	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName));
        xpaths.proceedButton.click();
        Assert.assertTrue(xpaths.chatbotValidation.getText().contains("Chat agent name is required!"), "check validation");
    }

 // Test to ensure chatbot names must be unique.
    @Test(priority = 5, description = "Test to ensure chatbot names must be unique.")
    public void duplicateChatbotNameTest()
    {
    	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName)).sendKeys(Variables.chatbotName);
    	helper.clickElement(xpaths.resourceCenterDropdown);
    	Variables.Sleep();
    	xpaths.listItem.click();
    	Variables.Sleep();
        xpaths.industry.sendKeys(Variables.industry);
        helper.scrollToElement(xpaths.proceedButton);
        xpaths.ChatbotPurposeDropdown.click();
        Variables.Sleep();
        xpaths.ChatbotPurposeListing.click();
        
        xpaths.proceedButton.click();
        
        //Fill second form after proceed
        wait.until(ExpectedConditions.visibilityOf(xpaths.businessName)).sendKeys(Variables.businessName);
    	xpaths.businessWebsite.sendKeys(Variables.businessWebsite);
    	xpaths.businessAddress.sendKeys(Variables.businessAddress);
    	xpaths.businessEmail.sendKeys(Variables.businessEmail);
    	xpaths.businessPhone.sendKeys(Variables.businessPhone);
    	Variables.Sleep();
    	xpaths.createButton.click();
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        Assert.assertTrue(xpaths.toastMessage.getText().contains("Chatbot name should be unique within your account"), "Pehle Test Chatbot 1 naam ka chatbot create kar ke rakho");
    }

 // Test to ensure chatbot name input only allows alphanumeric characters.
    @Test(priority = 6, description = "Test to ensure chatbot name input only allows alphanumeric characters.")
    public void alphanumericOnlyTest() 
    {
    	wait.until(ExpectedConditions.visibilityOf(xpaths.chatbotName)).sendKeys("Bot@!#123");
    	xpaths.resourceCenterDropdown.click();
    	Variables.Sleep();
    	xpaths.listItem.click();
    	Variables.Sleep();
        xpaths.industry.sendKeys(Variables.industry);
        helper.scrollToElement(xpaths.proceedButton);
        xpaths.ChatbotPurposeDropdown.click();
        Variables.Sleep();
        xpaths.ChatbotPurposeListing.click();
        
        xpaths.proceedButton.click();
        Assert.assertTrue(xpaths.chatbotValidation.getText().contains("Name must contain only alphanumeric characters"));
    }

 // Cleanup method to close the browser after each test.
    @AfterMethod
    public void tearDown(Method method)
    {
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