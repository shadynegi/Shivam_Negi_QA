package chatbot;

import java.io.File;

import org.openqa.selenium.interactions.Actions;

public class Variables
{
//============================================================================
// Variables.java - Separate Variables Class
//============================================================================

Xpaths xpaths;
Actions actions;
String timestamp = String.valueOf(System.currentTimeMillis());


	// All variables will be stored here	
	
		// loginURL
		public String loginURL = "https://chat.itsbot.ai/login";
	    
		// RC URL
		public String RCURL = "https://chat.itsbot.ai/knowledge-base";
		
	    // valid email
	    public String validEmail = "automation.email89@yopmail.com";
	    
	    // valid password
	    public String validPassword = "Test@123";
	    
	    //buy plan URL
	    public String buyPlanURL = "https://chat.itsbot.ai/profile/upgradeplan";
	    
	    //invalid email
	    public String invalidEmail = "invalid.email@yopmail.com";
	    
	    //invalid password
	    public String invalidPassword = "wrongpass@123";
	    
	    //explicit max wait time in seconds
	    public static final int DEFAULT_MAX_WAIT = 60;
	    public int maxWait = DEFAULT_MAX_WAIT;
	    
	    // thread sleep time
	    public void Sleep() {
	        Sleep(3000); // Default to 3000ms
	    }

	    public void Sleep(int millis) {
	        try {
	            Thread.sleep(millis);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    //Email which is in invalid format
	    public String invalidFormatEmail = "test.com";
	    
	    // reset password
	    public String resetURL = "https://chat.itsbot.ai/reset-password";
	    
	    //yopmail
	    public String yopmailURL = "https://yopmail.com/";
	    
	    //new password
	    public String newPassword = "Test@1234";
	    
	    //signUp URL
	    public String signUpUrl = "https://chat.itsbot.ai/signup";
	    
	    //chatbot URL
	    public String chatbotURL = "https://chat.itsbot.ai/dashboard/website-chatbot";
	    
	    //kb name
	    public String testKB = "Test KB";
	    
	    // business name
	    public String businessName = "Eminence technology";
	    
	    // chatbot name
	    public String chatbotName = "Automated chatbot";
	    
	    // industry
	    public String industry = "Information Technology";
	    
	    // business website
	    public String businessWebsite = "https://eminencetechnology.com/";
	    
	    // business address
	    public String businessAddress = "Plot F5, Mohali";
	    
	    // business email
	    public String businessEmail = "test@eminence.com";
	    
	    // business phone
	    public String businessPhone = "9876543210";
	    
	    // website which user wants to crawl
	    public String CrawlWebsite = "https://eminencetechnology.com/";

	    // file path
	    // change the path according to system
	    public static String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
	    
	    //logo upload file location
	    public String LogoUploadFile = filePath + "Screenshot_622.png.png";
	    
	    // toggle button image file location
	    public String toggleButtonImage = filePath + "eminence logo.jpg";
	    
	    // image file upload
	    public String imageUploadFile = filePath + "eminence visiting card.jpg";
	    
	    //doc file upload
	    public String docUploadFile = filePath + "hrdata.csv";
	    
	    //audio file upload
	    public String audioUploadFile = filePath + "harvard.mp3";
	    
	    // Test data for customization
	    public String chatbotHeight = "400";
	    public String chatbotWidth = "300";
	    public String testMessage = "Test message for customization";
	    public String welcomeMsg = "Welcome to our AI chatbot!";
	    public String teamMemberMsg = "Team member is currently unavailable";
	    public String limitExceedMsg = "Message limit exceeded";
	    public String userPromptMsg = "Please enter your question";
	    public String appointmentMsg = "Schedule appointment";
	    public String customErrorMsg = "Custom error occurred";
	    public String agentEscalationMin = "2";
	    public String conversationSearchText = "test conversation";
	    public String agentMessage = "Test message from agent";
	    public String internalSupportMsg = "Internal support message";
	    
	    // Chatbot names with timestamp
	    public String getChatbotName()
	    {
	        return "Test Chatbot " + System.currentTimeMillis();
	    }
	    
	    // RC names with timestamp
	    public String getRCName()
	    {
	        return "Test RC " + System.currentTimeMillis();
	    }
	    
	    public String createdBy = "Shivam Negi";

	    public String[] validLimits = {"1/1", "2/2", "4/4", "6/6", "8/8", "10/10"};
	    
	    public String textgenerator ="https://www.blindtextgenerator.com/lorem-ipsum";
	    
	    public String updatedBusinessWebsite ="https://www.infosys.com/";
	    
	    public String updatedBusinessAddress = "Plot no F5-F6, Phase 8 industrial area, Mohali";
	    
	    public String updatedBusinessEmail = "testing@eminence.com";
	    
	    public String updateBusinessPhone = "9888514572";
	    
	    public String updateIndustry = "Healthcare";
	    
	    public String inTrainingToast = "Resource centre is in training. Please wait for it to complete.";
	    
	    // hex color codes
	    public String black = "000000";
	    public String white = "FFFFFF";
	    
	}
 