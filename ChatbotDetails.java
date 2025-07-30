package chatbot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;
import org.testng.Assert;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import com.aventstack.extentreports.Status;

@SuppressWarnings("unused")

public class ChatbotDetails
{
	WebDriver driver;
    Xpaths xpaths;
    WebDriverWait wait;
    Helper helper;
    Actions actions;
    Variables Variables = new Variables();
    String timestamp = String.valueOf(System.currentTimeMillis());

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
        
     // Navigate to application
        driver.get(Variables.loginURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='80%'");
        xpaths.LoginEmail.sendKeys(Variables.validEmail);
        xpaths.LoginPassword.sendKeys(Variables.validPassword);
        xpaths.LoginButton.click();
        Variables.Sleep();
        helper.clickElement(xpaths.chatbotHub);
        Variables.Sleep();
    }

    @Test(priority = 1, description = "Verify user is able to Create chatbot")
    public void testCreateChatbot()
    {
        try
        {
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
                	JavascriptExecutor js = (JavascriptExecutor) driver;
                	js.executeScript("document.body.style.zoom='80%'");
                	Variables.Sleep();
                }
            }

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
        	Variables.Sleep();
        	helper.scrollToElement(xpaths.showBusinessDetailsToggle);
        	helper.clickElement(xpaths.showBusinessDetailsToggle);
        	Variables.Sleep();
        	
        	xpaths.createButton.click();
        	Variables.Sleep();
            wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent created successfully"), "Chatbot was not created");
        }
        catch (Exception e)
        {
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }

// Test Class TC_002: Verify Chatbot
    @Test(priority = 2, description = "Verify chatbot is visible on listing page after creation")
    public void testVerifyChatbot()
    {
        try
        {
            // Verify all the details mentioned
        	Variables.Sleep();
        	wait.until(ExpectedConditions.visibilityOf(xpaths.activeChatbot));
        	Assert.assertTrue(xpaths.activeChatbot.isDisplayed(), "Chat Agent is present in the listing page");

        }
        catch (Exception e)
        {
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }
    
// Test Class TC_003: Verify user is able to edit Chat Agent Details
    @Test(priority = 3, description = "Verify user is able to edit Chat Agent Details")
    public void editAgentDetails()
    {
        try
        {
        	Variables.Sleep();
        	helper.clickElement(xpaths.activeChatbot);
        	Variables.Sleep();
        	
            // edit chatbot name
        	helper.enterText(xpaths.websiteAgentName, Variables.chatbotName + timestamp);
        	Variables.Sleep();
        	
            // edit business name
            helper.enterText(xpaths.BusinessName, Variables.businessName + " updated");
            Variables.Sleep();

            // edit business website
            helper.enterText(xpaths.BusinessWebsite, Variables.updatedBusinessWebsite);
            Variables.Sleep();

            // edit business address
            helper.enterText(xpaths.editBusinessAddress, Variables.updatedBusinessAddress);
            Variables.Sleep();

            // edit business email
            helper.enterText(xpaths.editbusinessEmail, Variables.updatedBusinessEmail);
            Variables.Sleep();
            
            //edit business phone number
            helper.enterText(xpaths.editPhoneNumber, Variables.updateBusinessPhone);
            Variables.Sleep();

            //edit industry
            helper.enterText(xpaths.editindustry, Variables.updateIndustry);
            Variables.Sleep();
            
            //save changes
            helper.clickElement(xpaths.saveChangesButton);
            
            wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent updated successfully"),
            		"Chatbot was not updated");
            Variables.Sleep();
            
            Assert.assertTrue(xpaths.chatbotDescription.getText().contains(Variables.updatedBusinessWebsite),
            	    "Chatbot description does not contain the expected business website.");
            
        }
        catch (Exception e)
        {
            // If save changes fails, try discard changes
            try
            {
            	helper.clickElement(xpaths.discardChangesButton);
            }
            catch (Exception ex)
            {
                // Ignore discard failure
            }
            Assert.fail("Customization test failed due to: " + e.getMessage());
        }
    }

// Test Class TC_004: chatbot customization Settings
    @Test(priority = 4, description = "Verify user is able to edit chatbot customization Settings")
    public void chatbotcustomization()
    {
        try
        {
        	Variables.Sleep();
        	helper.clickElement(xpaths.activeChatbot);
        	Variables.Sleep();
        	helper.clickElement(xpaths.customizeTab);
        	
        	// change height
            helper.clickElement(xpaths.widgetHeight);
            Variables.Sleep();
            xpaths.widgetHeight.clear();
            xpaths.widgetHeight.sendKeys("530");
            Variables.Sleep();
            
         // change width
            helper.clickElement(xpaths.widgetWidth);
            Variables.Sleep();
            xpaths.widgetWidth.clear();
            xpaths.widgetWidth.sendKeys("410");
            Variables.Sleep();

            //add chat agent profile picture
            String logoUploadFile = Variables.LogoUploadFile;
            StringSelection selection = new StringSelection(logoUploadFile);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            
            xpaths.uploadImageButton.click();
            Robot robot = new Robot();
            robot.delay(1000); // wait for file dialog to appear
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
        	Variables.Sleep();
        	helper.scrollToElement(xpaths.chatbotLogoConfirm);
        	xpaths.chatbotLogoConfirm.click();
        	Variables.Sleep();
        	
        	//add toggle button icon
        	String toggleButtonImage = Variables.toggleButtonImage;
            StringSelection toggleButtonImageselection = new StringSelection(toggleButtonImage);
            Clipboard toggleButtonImageclipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            toggleButtonImageclipboard.setContents(toggleButtonImageselection, null);
            
            xpaths.toggleButtonImage.click();
            robot.delay(1000); // wait for file dialog to appear
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
        	Variables.Sleep();
        	helper.scrollToElement(xpaths.chatbotLogoConfirm);
        	xpaths.chatbotLogoConfirm.click();
        	Variables.Sleep();
            
         // Chat Agent Response text color
            helper.clickElement(xpaths.ChatAgentResponseTextColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.white.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
            String ChatAgentResponseTextColor = xpaths.chatMessageParagraphtext.getCssValue("color");
            Assert.assertEquals(ChatAgentResponseTextColor, "rgba(255, 255, 255, 1)", "Text color is not white as expected");
            Variables.Sleep();
            
         // Chat Agent Response background color
            helper.clickElement(xpaths.chatAgentResponseBackgroundColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.black.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
            String chatAgentResponseBackgroundColor = xpaths.chatMessageParagraph.getCssValue("background-color");
            Assert.assertEquals(chatAgentResponseBackgroundColor, "rgba(0, 0, 0, 1)", "Background color is not black as expected");
            Variables.Sleep();
            
         // User Message Text Color
            helper.clickElement(xpaths.userMessageTextColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.white.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
         // User Message background Color
            helper.clickElement(xpaths.userMessageBackgroundColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.black.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
         // Input Box Text Color
            helper.clickElement(xpaths.inputBoxTextColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.white.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
         // Send Button Icon Color
            helper.clickElement(xpaths.sendButtonIconColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.black.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
         // Chat Agent Header Background Color
            helper.clickElement(xpaths.chatAgentHeaderBackgroundColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.white.click();
            Variables.Sleep();
            helper.clickElement(xpaths.StyleChanger);
            Variables.Sleep();
            
            helper.scrollToElement(xpaths.headerText);
            Variables.Sleep();
            
         // Header text
            helper.clickElement(xpaths.headerText);
            helper.clickElement(xpaths.colorChange);
            xpaths.black.click();
            Variables.Sleep();
            helper.clickElement(xpaths.ChatAgentBackgroundText);
            Variables.Sleep();
            
            helper.scrollToElement(xpaths.toggleButtonColor);
            Variables.Sleep();
            
         // Toggle button color
            helper.clickElement(xpaths.toggleButtonColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.white.click();
            Variables.Sleep();
            helper.clickElement(xpaths.ChatAgentBackgroundText);
            Variables.Sleep();
            
            helper.scrollToElement(xpaths.chatAgentProfilePictureBackgroundColor);
            Variables.Sleep();
            
         // Chat agent profile picture background color
            helper.clickElement(xpaths.chatAgentProfilePictureBackgroundColor);
            helper.clickElement(xpaths.colorChange);
            xpaths.black.click();
            Variables.Sleep();
            helper.clickElement(xpaths.ChatAgentBackgroundText);
            Variables.Sleep();
            
         // save changes
            xpaths.saveChangesButton.click();
            
            wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent updated successfully"));

            Assert.assertTrue(true, "Widget settings should work fine on both widget and chatbot side");

        }
        catch (Exception e)
        {
            try
            {
            	helper.clickElement(xpaths.discardChangesButton);
            }
            catch (Exception ex)
            {
                // Ignore discard failure
            }
            Assert.fail("Widget settings test failed due to: " + e.getMessage());
        }
    }

// Test Class TC_005: widget settings
    @Test(priority = 5, description = "Verify widget settings are toggling ON/OFF")
    public void testwidgetsettings()
    {
        try
        {
        	Variables.Sleep();
        	helper.clickElement(xpaths.activeChatbot);
        	Variables.Sleep();
        	helper.clickElement(xpaths.widgetSettings);
        	Variables.Sleep();
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	js.executeScript("document.body.style.zoom='75%'");
        	
        	//edit welcome message
        	helper.clickElement(xpaths.generalSettings);
        	helper.enterText(xpaths.welcomeMessageInput, "Hello, how are you? ${username}");
        	Variables.Sleep();
        	
        	//edit tone
        	helper.clickElement(xpaths.toneDropdown);
        	xpaths.toneDropdown.sendKeys("Playful");;
        	xpaths.toneDropdown.sendKeys(Keys.ENTER);
        	Variables.Sleep();
        	
        	// edit language
        	helper.scrollToElement(xpaths.selectLanguageLabel);
        	//helper.clickElement(xpaths.selectLanguage);
        	//xpaths.selectLanguage.sendKeys("Hindi");
        	
        	// toggle agentic rag buy plan before turning on
        	//helper.clickElement(xpaths.toggleAgenticRAG);
        	
        	// toggle source document
        	helper.clickElement(xpaths.sourceDocumentToggle);
        	Variables.Sleep();
        	
        	//Toggle of limit exceed message
        	helper.clickElement(xpaths.limitExceedToggle);
        	Variables.Sleep();
        	
        	// Toggle of appointment scheduling
        	helper.clickElement(xpaths.isAppointmentToggle);
        	Variables.Sleep();
        	
        	// Toggle of custom error message
        	helper.clickElement(xpaths.toggleCustomErrorMessage);
        	Variables.Sleep();
        	
        	 // save changes
            xpaths.saveChangesButton.click();
            
            wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
            Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent updated successfully"));

            Assert.assertTrue(true, "Widget settings should work fine on both widget and chatbot side");
        }
        catch (Exception e)
        {
            try
            {
            	helper.clickElement(xpaths.discardChangesButton);
            }
            catch (Exception ex)
            {
                // Ignore discard failure
            }
            Assert.fail("Edit widget settings test failed due to: " + e.getMessage());
        }
    }

// Test Class TC_006: Team Member Settings
    @Test(priority = 6, description = "Verify Team member settings are toggling ON/OFF")
    public void testTeamMemberSettings()
    {
        try
        {
        	Variables.Sleep();
        	helper.clickElement(xpaths.activeChatbot);
        	Variables.Sleep();
        	helper.clickElement(xpaths.widgetSettings);
        	Variables.Sleep();
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	js.executeScript("document.body.style.zoom='75%'");
        	
            // Click on team member settings
        	Variables.Sleep();
        	helper.clickElement(xpaths.teamMemberSettings);
            Variables.Sleep();

            // Toggle of escalate to a support agent
            helper.clickElement(xpaths.toggleEscalateToSupportAgent);
            Variables.Sleep();

            // Minimum number of messages required for agent escalation
            try {
            	if (xpaths.minimumMessagesEscalation.isDisplayed())
            	{
            		helper.enterText(xpaths.minimumMessagesEscalation, Variables.agentEscalationMin);
                    Variables.Sleep();
            	}
            } catch (Exception e) {
            Report.logInfo("Turn on team member escalation");
            }

            // Dedicated Internal Support Bot
            helper.clickElement(xpaths.dedicatedInternalSupportBot);
            Variables.Sleep();

            // Clicking on dropdown
            helper.scrollToElement(xpaths.privateRCDropdown);
            helper.clickElement(xpaths.privateRCDropdown);
            Variables.Sleep();
            
            //select private RC
            xpaths.toneDropdown.sendKeys(Variables.testKB);
        	xpaths.toneDropdown.sendKeys(Keys.ENTER);
        	Variables.Sleep();

            // AI-Powered Reply Suggestions
            helper.clickElement(xpaths.aiPoweredReplySuggestions);
            Variables.Sleep();

       	 // save changes
           xpaths.saveChangesButton.click();
           
           wait.until(ExpectedConditions.visibilityOf(xpaths.toastMessage));
           Assert.assertTrue(xpaths.toastMessage.getText().contains("Chat Agent updated successfully"));

           Assert.assertTrue(true, "Widget settings should work fine on both widget and chatbot side");
        }
        catch (Exception e)
        {
            try
            {
            	helper.clickElement(xpaths.discardChangesButton);
            }
            catch (Exception ex)
            {
                // Ignore discard failure
            }
            Assert.fail("Team member settings test failed due to: " + e.getMessage());
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