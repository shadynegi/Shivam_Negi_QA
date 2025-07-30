package chatbot;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;


import com.aventstack.extentreports.Status;

@SuppressWarnings("unused")
public class RCTraining
{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());
    
    @BeforeMethod
    public void RCsetup()
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
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1); // 1 = Allow, 2 = Block microphone
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
        
    //  Login via correct creds
        driver.get(Variables.loginURL);
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
        
    //  Open Resource centre listing page
    	driver.get(Variables.RCURL);
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCLimit));
    	String RCLimit = xpaths.RCLimit.getText();
    	String[] validLimits = {"1/1", "2/2", "4/4", "6/6", "8/8", "10/10"};
    	
    //  Delete Resource centre if limit is full
    	for (String limit : validLimits) 
    	{
    	if (RCLimit.contains(limit))
    		{
    		actions.moveToElement(xpaths.RCDelete).perform();
    		helper.clickElement(xpaths.RCDelete);
    		helper.clickElement(xpaths.confirmDelete);
            Variables.Sleep();
            wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            Assert.assertTrue(xpaths.toastMessage.getText().contains("Resource centre deleted successfully"), "Resource centre was not deleted");
        	Variables.Sleep();
    		}
    	}
    } 
 
 // Verify user is able to crawl and train a website
    @Test (priority = 1, description = "Verify user is able to crawl and train a website")
    public void crawlerTest()
    {
    	//create RC
    	Variables.Sleep();
    	helper.createRC();
    	
    	//start crawling
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='75%'");
    	Variables.Sleep();
		helper.enterText(xpaths.CrawlerInputField, Variables.CrawlWebsite);
		Variables.Sleep();
    	helper.clickElement(xpaths.StartWebsiteCrawling);
    	Variables.Sleep();
    	helper.clickElement(xpaths.CrawlConfirm);
    	Variables.Sleep();
    	
    	//stop crawling
    	helper.clickElement(xpaths.StopCrawl);
    	Variables.Sleep();
    	xpaths.ConfirmStopCrawl.click();
        
    	// Assert that the correct message is displayed after stopping crawl
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("Crawling stopped") || xpaths.toastMessage.getText().contains("Your request has been received"), "Check crawling manually");
    	Variables.Sleep();
    	
    	// Click the "Update Resource Centre" button to begin training
    	Variables.Sleep(5000);
    	wait.until(ExpectedConditions.visibilityOf(xpaths.UpdateRC));
    	helper.clickElement(xpaths.UpdateRC);
    	Variables.Sleep();
    	
    	// Assert that the training status is in training
    	driver.get(Variables.RCURL);
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCStatus));
    	Variables.Sleep();
    	Assert.assertTrue(xpaths.RCStatus.getText().contains("In Training") || xpaths.RCStatus.getText().contains("Trained"), "check training");
    	if (xpaths.RCStatus.getText().contains("In Training")) {
            try {
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            } catch (TimeoutException | NoSuchElementException e) {
                Report.logInfo("Toast message did not appear, check if RC is trained: " + e.getMessage());
            }
        }
    }
    
 // Verify user is able to train text
    @Test (priority = 2, description = "Verify user is able to train text")
    public void textTest()
    {
    	helper.createRC();
    	
    	//Add text
    	helper.clickElement(xpaths.textRC);
    	helper.enterText(xpaths.textArea, "Hello This is Eminence Technology");
    	Variables.Sleep();
    	
    	// Start training
    	wait.until(ExpectedConditions.visibilityOf(xpaths.UpdateRC));
    	helper.clickElement(xpaths.UpdateRC);
    	Variables.Sleep();
    	
    	// Assert that the training status is in training
    	driver.get(Variables.RCURL);
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCStatus));
    	Assert.assertTrue(xpaths.RCStatus.getText().contains("In Training") || xpaths.RCStatus.getText().contains("Trained"), "check training");
    	if (xpaths.RCStatus.getText().contains("In Training")) {
            try {
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            } catch (TimeoutException | NoSuchElementException e) {
                Report.logInfo("Toast message did not appear: " + e.getMessage());
            }
        }
    }
  
    // test 1 lakh character limit in text field
    public void enterLargeTextInSmallChunks(WebElement element, String text) {
        element.clear();
        
        int chunkSize = 25; // Small chunks
        
        for (int i = 0; i < text.length(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, text.length());
            String chunk = text.substring(i, endIndex);
            element.sendKeys(chunk);
            
            // Longer delay to give browser time to process
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public static String generate100KText() {
        StringBuilder sb = new StringBuilder();
        String text = "Hello This is Eminence Technology and this is text with 100K characters to check max limit of text training. ";
        
        // Calculate how many complete repetitions we need
        int repetitions = 100000 / text.length();
        
        // Add complete repetitions
        for (int i = 0; i < repetitions; i++) {
            sb.append(text);
        }
        
        // Add remaining characters to reach exactly 50,000
        int remaining = 100000 - sb.length();
        if (remaining > 0) {
            sb.append(text.substring(0, remaining));
        }
        
        // Ensure exactly 50,000 characters
        return sb.substring(0, Math.min(sb.length(), 100000));
    }
    
    // javascript executer (do not delete)
    public void enterLargeTextWithJS(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Clear the element first
        js.executeScript("arguments[0].value = '';", element);
        
        // Set the text using JavaScript (much faster than sendKeys)
        js.executeScript("arguments[0].value = arguments[1];", element, text);
        
        // Trigger input events to notify the application
        js.executeScript(
            "var element = arguments[0];" +
            "var event = new Event('input', { bubbles: true });" +
            "element.dispatchEvent(event);" +
            "var changeEvent = new Event('change', { bubbles: true });" +
            "element.dispatchEvent(changeEvent);",
            element
        );
    }
    
// Verify there is a limit on train text
    @Test (priority = 3, description = "Verify there is a limit on train text")
    public void textNegativeTest () throws AWTException, InterruptedException
    {
    	helper.createRC();
    	helper.clickElement(xpaths.textRC);
    	Variables.Sleep();
    	
    	((JavascriptExecutor) driver).executeScript("window.open()");
    	Variables.Sleep();
    	
    	// Switch to the new tab
    	ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
    	driver.switchTo().window(tabs.get(1));
    	Variables.Sleep();
    	
    	driver.get(Variables.textgenerator);
    	Variables.Sleep();
    	helper.clickElement(xpaths.CharactersRadioButton);
    	helper.enterText(xpaths.inputCharacters, "100000");
    	Variables.Sleep();
    	helper.clickElement(xpaths.copyToClipboard);
    	Variables.Sleep();
    	driver.close();
    	
    	driver.switchTo().window(tabs.get(0));
    	Variables.Sleep();
    	    
    	actions.moveToElement(xpaths.textArea).click().perform();
    	Robot robot = new Robot();
    	
    	Variables.Sleep();
    	xpaths.textArea.click();
    	// Ctrl + V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Variables.Sleep();
        
        xpaths.textArea.sendKeys("x");
    	
    	// Add Text with 50k characters
//        String largeText = generate100KText();
//        Report.logInfo("Generated text length: " + largeText.length()); // Debug line  
//        enterLargeTextInSmallChunks(xpaths.textArea, largeText);
//        Variables.Sleep();
    	
    	// assert the max limit validation message comes up
    	wait.until(ExpectedConditions.visibilityOf(xpaths.maxText));
    	Assert.assertTrue(xpaths.maxText.getText().contains("Max 1,00,000 characters allowed"), "check text training");
    }
    
 // Verify user is able to train doc
    @Test (priority = 4, description = "Verify user is able to train doc")
    public void docTest()
    {
    	helper.createRC();
    	Variables.Sleep();
    	
    	//Add xls file
    	helper.clickElement(xpaths.docRC);
    	Variables.Sleep();

    	// upload file
    	xpaths.docUpload.sendKeys(Variables.docUploadFile);
    	Variables.Sleep(10000);
    	
    	helper.jsClick(xpaths.selectAll);
    	Variables.Sleep();
    	
    	// Start training
    	wait.until(ExpectedConditions.elementToBeClickable(xpaths.UpdateRC));
    	Variables.Sleep();
    	helper.clickElement(xpaths.UpdateRC);
    	Variables.Sleep();
    	
    	// Assert that the training status is in training
    	driver.get(Variables.RCURL);
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCStatus));
    	Assert.assertTrue(xpaths.RCStatus.getText().contains("In Training") || xpaths.RCStatus.getText().contains("Trained"), "check training");
    	if (xpaths.RCStatus.getText().contains("In Training")) {
            try {
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            } catch (TimeoutException | NoSuchElementException e) {
                Report.logInfo("Toast message did not appear: " + e.getMessage());
            }
        }
    }
    
    // Verify user is able to train FAQ
    @Test (priority = 5, description = "Verify user is able to train FAQ")
    public void FAQTest()
    {
    	helper.createRC();
    	
    	//Add FAQ Question and answer
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
    	helper.clickElement(xpaths.FAQRC);
    	Variables.Sleep();
    	helper.clickElement(xpaths.AddFAQ);
    	
    	helper.enterText(xpaths.FAQQuestion, "Who is the owner of itsbot product?");
    	helper.enterText(xpaths.FAQAnswer, "Rahul Nair is the owner of the itsbot product.");
    	Variables.Sleep();
    	
    	// Start training
    	wait.until(ExpectedConditions.visibilityOf(xpaths.UpdateRC));
    	helper.clickElement(xpaths.UpdateRC);
    	Variables.Sleep();
    	
    	// Assert that the training status is in training
    	driver.get(Variables.RCURL);
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCStatus));
    	Variables.Sleep();
    	Assert.assertTrue(xpaths.RCStatus.getText().contains("In Training") || xpaths.RCStatus.getText().contains("Trained"), "check training");
    	if (xpaths.RCStatus.getText().contains("In Training")) {
            try {
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            } catch (TimeoutException | NoSuchElementException e) {
                Report.logInfo("Success toast message did not appear: " + e.getMessage());
            }
        }
    }
    
    // Verify user is able to delete FAQ
    @Test (priority = 6, description = "Verify user is able to delete FAQ")
    public void FAQDeleteTest()
    {
    	
    	helper.clickElement(xpaths.RCOpen);
    	Variables.Sleep();
    	
    	//Add FAQ Question and answer
    	helper.clickElement(xpaths.FAQRC);
    	Variables.Sleep();
    	helper.clickElement(xpaths.AddFAQ);
    	Variables.Sleep();
    	
    	helper.enterText(xpaths.FAQQuestion, "Who is the owner of itsbot product?");
    	Variables.Sleep();
    	helper.enterText(xpaths.FAQAnswer, "Rahul Nair is the owner of the itsbot product.");
    	Variables.Sleep();
    	helper.clickElement(xpaths.AddFAQConfirm);
    	Variables.Sleep();
    	
    	//delete FAQ
    	helper.clickElement(xpaths.DeleteFAQ);
    	Variables.Sleep();
    	helper.clickElement(xpaths.confirmDelete);
    	Variables.Sleep();
    	
    	// Assert that the FAQ got deleted successfully
    	wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
    	Assert.assertTrue(xpaths.toastMessage.getText().contains("FAQ Deleted Successfully"), "check FAQ");
    }
    
 // Verify user is able to train images
    @Test (priority = 7, description = "Verify user is able to train images")
    public void imagesTest()
    {
    	helper.createRC();
    	Variables.Sleep();
    	
    	//Add image file
    	helper.clickElement(xpaths.imagesRC);
    	Variables.Sleep();
    	xpaths.uploadImageArea.sendKeys(Variables.imageUploadFile);
    	Variables.Sleep();

    	// Start training
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
    	
    	wait.until(ExpectedConditions.elementToBeClickable(xpaths.UpdateRC));
    	Variables.Sleep();
    	helper.clickElement(xpaths.UpdateRC);
    	Variables.Sleep();
    	
    	// Assert that the training status is in training
    	driver.get(Variables.RCURL);
    	Variables.Sleep();
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCStatus));
    	Assert.assertTrue(xpaths.RCStatus.getText().contains("In Training") || xpaths.RCStatus.getText().contains("Trained"), "check training");
    	if (xpaths.RCStatus.getText().contains("In Training")) {
            try {
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            } catch (TimeoutException | NoSuchElementException e) {
                Report.logInfo("Toast message did not appear: " + e.getMessage());
            }
        }
    }
    
 // Verify user is able to train audio
    @Test (priority = 8, description = "Verify user is able to train audio")
    public void audioTest()
    {
    	helper.createRC();
    	Variables.Sleep();
    	
    	//Add audio file
    	helper.clickElement(xpaths.audioRC);
    	Variables.Sleep();
    	helper.clickElement(xpaths.uploadAudioFile);
    	xpaths.uploadAudioArea.sendKeys(Variables.audioUploadFile);
    	Variables.Sleep(10000);
    	
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
    	Variables.Sleep();

    	// Start training
    	wait.until(ExpectedConditions.elementToBeClickable(xpaths.UpdateRC));
    	helper.clickElement(xpaths.UpdateRC);
    	Variables.Sleep();
    	
    	// Assert that the training status is in training
    	driver.get(Variables.RCURL);
    	wait.until(ExpectedConditions.visibilityOf(xpaths.RCStatus));
    	Assert.assertTrue(xpaths.RCStatus.getText().contains("In Training") || xpaths.RCStatus.getText().contains("Trained"), "check training");
    	if (xpaths.RCStatus.getText().contains("In Training")) {
            try {
                wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            } catch (TimeoutException | NoSuchElementException e) {
                Report.logInfo("Toast message did not appear: " + e.getMessage());
            }
        }
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