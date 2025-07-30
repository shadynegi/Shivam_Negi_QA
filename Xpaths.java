package chatbot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//============================================================================
// Xpaths.java - Separate XPaths Class  
//============================================================================

public class Xpaths

{
	WebDriver driver;
	public Xpaths(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	 
	
// All Xpath webElement locators will be stored here	
	
//Login page
    
    //email input field
    @FindBy(xpath="//input[@id='email']")
    WebElement LoginEmail;

    //Password input field
    @FindBy(xpath="//input[@id='password']")
    WebElement LoginPassword;
    
    //login button
    @FindBy(xpath="//button[normalize-space()='Sign In']")
    WebElement LoginButton;
    
    //toast-message
    @FindBy(className="toast-message")
    WebElement toastMessage;
    
    //empty email validation text
    @FindBy(xpath="//span[normalize-space()='Please enter email!']")
    WebElement emptyEmailValidation;
    
    //empty password validation text
    @FindBy(xpath="//span[normalize-space()='Please enter password!']")
    WebElement emptyPasswordValidation;
    
    //invalid email text
    @FindBy(xpath="//span[normalize-space()='Please enter a valid email address!']")
    WebElement invalidEmailValidation;
    
    //Forgot password button
    @FindBy(xpath="//a[text()='Forgot Password?']")
    WebElement forgotPassword;
    
    
    //Forgot password submit button
    @FindBy(xpath="//button[normalize-space()='Submit']")
    WebElement submit;
    
    //yopmail search bar
    @FindBy(xpath="//input[@id='login']")
    WebElement yopmailSearch;
    
  //reset password page	
    
    //yopmail reset password button
    @FindBy(xpath="//a[normalize-space()='Reset My Password']")
    WebElement yopmailResetButton;
    
    // yopmail Verify my account button
    @FindBy(xpath="//a[text()='Verify My Account']")
    WebElement yopmailVerifyButton;
    
    //Create new password
    @FindBy(xpath="//input[@id='password']")
    WebElement newPassword;
    
    //Confirm new password
    @FindBy(xpath="//input[@id='confirmPassword']")
    WebElement confirmPassword;
    
    //Reset password button
    @FindBy(xpath="//button[normalize-space()='Reset Password']")
    WebElement resetPassword;
    
  //signup page	
    
    //Sign Up button
    @FindBy(xpath="//a[normalize-space()='Sign Up']")
    WebElement signUp;
    
    //Sign up form button
    @FindBy(xpath="//button[normalize-space()='Sign Up']")
    WebElement signUpButton;

    //Sign up URL
    @FindBy(xpath="//a[normalize-space()='Sign Up']")
    WebElement signUpUrl;
    
  //first name
    @FindBy(xpath="//input[@id='first_name']")
    WebElement signUpFirstName;
    
  //last name
    @FindBy(xpath="//input[@id='last_name']")
    WebElement signUpLastName;
    
  //Email
    @FindBy(xpath="//input[@id='email']")
    WebElement signUpEmail;
    
  //Password
    @FindBy(xpath="//input[@id='password']")
    WebElement signUpPassword;
    
  //Confirm password
    @FindBy(xpath="//input[@id='confirmPassword']")
    WebElement signUpConfirmPassword;
    
  //Country dropdown
    @FindBy(xpath="//div[@class='css-19bb58m']")
    WebElement dropdown;
    
  //accept terms and conditions
    @FindBy(xpath = "//input[@class='PrivateSwitchBase-input css-1m9pwf3']")
    WebElement tandc;
    
    //terms and conditions validation
    @FindBy(xpath = "//div[contains(text(), 'You must agree to the Terms and Conditions')]")
    WebElement tandcValidation;
    
  //India in dropdown
    @FindBy(xpath="//div[text()='India']")
    WebElement india;
    
  //Confirm password validation
    @FindBy(xpath="//div[normalize-space()='Password and confirm password must match']")
    WebElement confirmPasswordMatchValidation;
    
  //email validation text
    @FindBy(xpath="//div[contains(text(),'Please enter a valid email address!')]")
    WebElement emailValidationText;
    
  //first name validation
    @FindBy(xpath = "//div[text()='Please enter first name!']")
    WebElement firstNameValidationText;
    
  //last name validation
    @FindBy(xpath = "//div[text()='Please enter last name!']")
    WebElement lastNameValidationText;
    
  //country validation
    @FindBy(xpath = "//div[text()='Please select country!']")
    WebElement countryValidation;
    
  //email validation
    @FindBy(xpath = "//div[text()='Please enter email!']")
    WebElement emailValidation;
    
  //password validation
    @FindBy(xpath = "//div[text()='Please enter password!']")
    WebElement passwordValidation;
    
  //confirm password validation
    @FindBy(xpath = "//div[text()='Please enter confirm password!']")
    WebElement confirmPasswordValidation;
    
  //weak password validation
    @FindBy(xpath="//div[contains(text(),'Your password should have at least one special cha')]")
    WebElement weakPasswordValidation;
    
  //sign in button
    @FindBy(xpath="//a[normalize-space()='Sign In']")
    WebElement signIn;
    
  //sign up toast
    @FindBy(xpath="//div[contains(text(),'Registration Successful. Please check your email f')]")
    WebElement signUpToast;
    
  //Chatbot creation module
    
  //chatbot hub
    @FindBy(xpath="//a[normalize-space(text())='Chat Agents']")
    WebElement chatbotHub;
    
  //create new chatbot
    @FindBy(xpath = "//div[@class='icon-create text-center']")
    WebElement createChatbot;
    
    // create fresh chatbot
  @FindBy(xpath = "//button[normalize-space()='Set Up Your Chat Agent']")
  WebElement createNewChatbot;
  
  // chatbot name in listing page
  @FindBy(xpath = "//h4[contains(text(), 'Automated chatbot')]")
  WebElement activeChatbot;
    
  //chatbot name
    @FindBy(xpath="//input[@placeholder='Name Your Website Agent']")
    WebElement chatbotName;
    
  //chatbot name validation
    @FindBy(xpath="//span[contains(@class, 'error') and contains(text(), 'Chat agent name is required!')] | //span[contains(@class, 'error') and contains(text(), 'Name must contain only alphanumeric characters.')]")
    WebElement chatbotValidation;
    
  //resource center dropdown
    @FindBy(xpath="//input[@id='react-select-2-input']")
    WebElement resourceCenterDropdown;
    
  //industry field
    @FindBy(xpath="//input[@type='text' and @placeholder='Enter The Type Of Industry Associated WIth Your Business']")
    WebElement industry;
    
  //proceed button
    @FindBy(xpath="//button[normalize-space()='Proceed']")
    WebElement proceedButton;
    
 // list item
    @FindBy(xpath="//div[@class=\"css-uke2ha-option\"]")
    WebElement listItem;
    
  //resource center test KB
    @FindBy(xpath="//div[contains(@class, 'singleValue') and text()='test kb']")
    WebElement testKB;
    
  //business name
    @FindBy(xpath="//input[@placeholder='Enter business name']")
    WebElement businessName;
    
  //business website
    @FindBy(xpath="//input[@placeholder='Enter business Website']")
    WebElement businessWebsite;
    
  //business address
    @FindBy(xpath="//input[@placeholder='Specify Your Business Location']")
    WebElement businessAddress;
    
  //business email
    @FindBy(xpath="//input[@placeholder='Input Your Business Email Address']")
    WebElement businessEmail;
    
  //business phone
    @FindBy(xpath="//input[@placeholder='Provide Your Business Phone Number']")
    WebElement businessPhone;
    
  //business logo
    @FindBy(xpath="//div[@class=\"relative group MuiBox-root css-0\"]")
    WebElement chatbotLogo;
    
  //business details toggle
    @FindBy(xpath="//input[@id='toggle5']")
    WebElement showBusinessDetailsToggle;
    
  //create chatbot
    @FindBy(xpath="//button[text()='Create Chat Agent']")
    WebElement createButton;
    
  //logo upload
    @FindBy(xpath = "//input[@type='file' and @id='uploadedFile']")
    WebElement chatbotLogoUpload;
    
  //delete chatbot
    @FindBy(xpath = "//img[@alt='delete']")
    WebElement deleteChatbot;
    
  //confirm delete
    @FindBy(xpath = "//div[text()='Delete'] | //div[@class = 'flex items-center justify-center']")
    WebElement confirmDelete;
    
    // open chatbot
    @FindBy(xpath = "//img[@alt='delete']")
    WebElement openChatbot;
    
  //Resource center module
    
 // create new RC
    @FindBy(xpath = "//p[contains(text(), 'Create Resource Centre')] | //button[contains(text(), 'Create Resource Centre')]")
    WebElement createRC;
    
 // rc name
    @FindBy(xpath = "//input[@placeholder='Enter Resource Centre name']")
    WebElement RCName;
    
    // private RC radio button
    @FindBy(xpath = "//input[@type='radio' and @value='private']")
    WebElement privateRC;
    
  //RC Proceed
    @FindBy(xpath = "//button[normalize-space()='Proceed']")
    WebElement RCProceed;
    
  //RCLimit
    @FindBy(xpath = "//div[@class='MuiBox-root css-0']")
    WebElement RCLimit;
    
 // delete RC
    @FindBy(xpath ="//button[.//img[@alt='delete']]")
    WebElement RCDelete;
    
  //open RC
    @FindBy(xpath ="//div[contains(@class, 'support-article')]")
    WebElement RCOpen;
    
   //edit RC Name
    @FindBy(xpath = "//button[.//img[@alt='editicon']]")
    WebElement RCEdit;
    
  //edit RC Name input field
    @FindBy(xpath = "//input[@placeholder='Enter text...']")	
    WebElement RCEditInputl;
    
  //change RC name button
    @FindBy(xpath = "//button[normalize-space()='Change']")
    WebElement RCNameChange;
    
  //crawler input field
    @FindBy(xpath ="//input[@id='links']")
    WebElement CrawlerInputField;
    
    // RC status
    @FindBy(xpath = "(//div[normalize-space()='Empty' or normalize-space()='Untrained' or normalize-space()='Trained' or normalize-space()='In Training'])[1]")
    WebElement RCStatus;
    
  //Start website crawling button
    @FindBy(xpath = "//button[normalize-space()='Fetch Details']")
    WebElement StartWebsiteCrawling;
    
   // Add website links
    @FindBy(xpath = "//button[normalize-space()='Add Website Link']")
    WebElement AddWebsiteLink;
    
  //Confirm crawl start
    @FindBy(xpath ="//button[normalize-space()='Confirm']")
    WebElement CrawlConfirm;
    
  //stop crawling
    @FindBy(xpath ="//button[.//span[contains(normalize-space(), 'stop')]]")
    WebElement StopCrawl;
    
  //stop crawling confirmation
    @FindBy(xpath = "//button[normalize-space()='Yes, Stop Fetching']")
    WebElement ConfirmStopCrawl;
    
 // select all checkbox
    @FindBy(xpath ="//span[contains(@class, 'ant-checkbox')]/input[@type='checkbox']")
    WebElement SelectAll;
    
  //Update Resource centre
    @FindBy(xpath ="//button[text()='Update Resource Centre']")
    WebElement UpdateRC;
    
 // add website link button
    @FindBy(xpath ="//button[normalize-space(text())='Add Website Link']")
    WebElement AddWebsite;
    
 // Chatbot Setup Elements
 // Click on setup your chatbot
    @FindBy(xpath = "//img[@alt='Plus Icon']")
    WebElement setupChatbotButton;

    // edit chatbot details
    
    //Chat agent name edit input field
    @FindBy(xpath = "//input[@type='text' and @name='name']")
    WebElement websiteAgentName;
    
    // business name edit input field
    @FindBy(xpath = "//input[@type='text' and @name='business_name']")
    WebElement BusinessName;
    
    // business website edit input field
    @FindBy(xpath = "//input[@type='text' and @name='business_website']")
    WebElement BusinessWebsite;
    
    //business address edit input field
    @FindBy(xpath = "//input[@name='business_address']")
    WebElement editBusinessAddress;
    
    //business email edit input field
    @FindBy(xpath = "//input[@name='business_email']")
    WebElement editbusinessEmail;
    
    // business phone number edit input field
    @FindBy(xpath = "//input[@name='phone_number' and @type='tel']")
    WebElement editPhoneNumber;
    
    // edit industry
    @FindBy(xpath = "//input[@name='industry' and @type='text']")
    WebElement editindustry;
    
    // chatbot description
    @FindBy(xpath = "//textarea[@name='chatbot_desc' and contains(@placeholder, 'Description')]")
    WebElement chatbotDescription;
    
    // save changes button
    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Save Changes')]")
    WebElement saveChangesButton;
    
    //discard changes
    @FindBy(xpath = "//button[@type='button' and contains(text(), 'Discard Changes')]")
    WebElement discardChangesButton;
    
    
    
    
 // Customization Elements
 // Click on customization
    @FindBy(xpath = "//li[p[text()='Customize']]")
    WebElement customizeTab;
    
    //widget height
    @FindBy(xpath = "//input[@id='height']")
    WebElement widgetHeight;
    
    //widget width
    @FindBy(xpath = "//input[@id='width']")
    WebElement widgetWidth;

 // Upload image
    @FindBy(xpath = "(//button[contains(@class, 'text-sm') and contains(@class, 'rounded-md')])[1]")
    WebElement uploadImageButton;

 // Toggle button image
    @FindBy(xpath = "(//button[contains(@class, 'text-sm') and contains(@class, 'rounded-md')])[2]")
    WebElement toggleButtonImage;

 // Chat Agent Response text color
    @FindBy(xpath = "//div[@aria-label='This controls the color of the chat agent’s replies.']//div//div//div")
    WebElement ChatAgentResponseTextColor;

 // Color Change
    @FindBy(xpath = "//input[contains(@id, 'rc-editable-input-')]")
    WebElement colorChange;
    
    //color change black
    @FindBy(xpath = "//div[@title='#000000']")
    WebElement black;
    
    //color change white
    @FindBy(xpath = "//div[@title='#FFFFFF']")
    WebElement white;
    
    // check chat message
    @FindBy(xpath = "(//div[contains(@class,'chatbbot-chat')])[1]")
    WebElement chatMessageParagraph;
    
    // chat message text
    @FindBy(xpath = "//p[p]/p [1]")
    WebElement chatMessageParagraphtext;
    
    @FindBy(xpath = "//textarea[@placeholder='Type your message here..']")
    WebElement messageInputBox;
    
    @FindBy(xpath = "//p[contains(@class, 'font-break-words')]")
    WebElement sentMessage;
    
    // go out of color picker
    @FindBy(xpath ="//p[normalize-space()='Style Changer']")
    WebElement StyleChanger;
    
    // do out of color picker after scroll
    @FindBy(xpath ="//p[normalize-space()='Chat Agent Profile Picture Background Color']")
    WebElement ChatAgentBackgroundText;

 // Chat Agent Response background color
    @FindBy(xpath = "//div[@aria-label='This sets the background color behind the chat agent’s messages.']//div//div//div")
    WebElement chatAgentResponseBackgroundColor;

 // User message text color
    @FindBy(xpath = "//div[@aria-label='This sets the text color of the user’s messages.']//div//div//div")
    WebElement userMessageTextColor;

 // User message background color
    @FindBy(xpath = "//div[@aria-label='This sets the background color behind the user’s messages.']//div//div//div")
    WebElement userMessageBackgroundColor;

 // Input box text color
    @FindBy(xpath = "//div[@aria-label='This changes the color of the text typed in the question input field.']//div//div//div")
    WebElement inputBoxTextColor;

 // Send button icon color
    @FindBy(xpath = "//div[@aria-label='This sets the color of the icon used to submit the user’s message.']//div//div//div")
    WebElement sendButtonIconColor;

 // Chat agent header background color
    @FindBy(xpath = "//div[@aria-label='This sets the background color of the chat agent header.']//div//div//div")
    WebElement chatAgentHeaderBackgroundColor;

 // Header text
    @FindBy(xpath = "//div[@aria-label='Set the color of the chat agent header text to match your brand or improve readability against the background.']//div//div//div")
    WebElement headerText;

 // Toggle button color
    @FindBy(xpath = "//div[@aria-label='This changes the color of the floating chat agent button.']//div//div//div")
    WebElement toggleButtonColor;

 // Chat agent profile picture background color
    @FindBy(xpath = "//div[@aria-label='This sets the background color behind the chat agent’s profile picture inside the chat window and also in the header']//div//div//div")
    WebElement chatAgentProfilePictureBackgroundColor;

 // Chat agent profile picture background color change
    @FindBy(xpath = "//div[@title='#4A90E2']")
    WebElement chatAgentProfilePictureBackgroundColorChange;

 // Enter text
    @FindBy(xpath = "//input[@placeholder='Type your message here..']")
    WebElement enterText;

 // Copy
    @FindBy(xpath = "//img[@alt='icons']")
    WebElement copyButton;

 // Widget Settings Elements
    
 // Click on Widget Settings
    @FindBy(xpath = "//p[normalize-space()='Widget Settings']")
    WebElement widgetSettings;
    
    // general settings
    @FindBy(xpath = "//li[normalize-space()='General Settings']")
    WebElement generalSettings;

 // Click on back arrow
    @FindBy(xpath = "//img[@alt='left arrow icon']")
    WebElement backArrow;

 // Click on upgrade message of credits
    @FindBy(xpath = "//button[@class='rounded text-white bg-[#44B464] hover:bg-[#3f9357] DMSans700 w-[88px] h-[34px] text-sm grid place-items-center']")
    WebElement upgradeMessageCredits;

 // Welcome message
    @FindBy(xpath = "//input[@id='message']")
    WebElement welcomeMessageInput;

 // Tone selection
    @FindBy(xpath = "//input[contains(@id, 'react-select') and @role='combobox']")
    WebElement toneDropdown;

 // Select gpt model
    @FindBy(xpath = "(//div[@class='css-gng19k-control'])[1]")
    WebElement selectGptModel;

    // select language header
    @FindBy(xpath = "//label[contains(., 'Select Language')]")
    WebElement selectLanguageLabel;
    
 // Select language
    @FindBy(xpath = "//div[contains(@class,'css-1xc3v61-indicatorContainer')]")
    WebElement selectLanguage;
    
    //unavailable team member header
    @FindBy(xpath = "//label[contains(., 'Unavailable team member message.')]")
    WebElement unavailableTeamMsgLabel;

 // Unavailable team member message
    @FindBy(xpath = "//input[@name='escalation_message']")
    WebElement unavailableTeamMemberMessage;

 // Toggle of agentic rag
    @FindBy(xpath = "//button[@name='agentic_RAG']")
    WebElement toggleAgenticRAG;

 // Toggle of source document
    @FindBy(xpath = "//button[@name='source_document']")
    WebElement sourceDocumentToggle;

 // Toggle of limit exceed message
    @FindBy(xpath = "//button[@name='limit_exceed_message']")
    WebElement limitExceedToggle;

 // Input field of limit exceed message
    @FindBy(xpath = "//textarea[@id='limit_exceed_message']")
    WebElement inputFieldLimitExceedMessage;

 // Toggle of user prompt
    @FindBy(xpath = "(//span[@class='absolute left-2 text-white DMSans400 text-sm transition-opacity opacity-100'])[3]")
    WebElement toggleUserPrompt;

 // Input field of user prompt
    @FindBy(xpath = "//textarea[@id='user_prompt']")
    WebElement inputFieldUserPrompt;

 // Toggle of appointment scheduling
    @FindBy(xpath = "//button[@name='is_appointment']")
    WebElement isAppointmentToggle;

 // Input field of appointment scheduling
    @FindBy(xpath = "(//input[@class='false border text-[--text-black] bg-white rounded inputCommon px-4 h-[45px] border-[--border] text-sm DMSans400 text-black w-full'])[1]")
    WebElement inputFieldAppointmentScheduling;

 // Toggle of custom error message
    @FindBy(xpath = "//button[@name='is_error_message']")
    WebElement toggleCustomErrorMessage;

 // Input field of custom error message
    @FindBy(xpath = "(//input[@class='false border text-[--text-black] bg-white rounded inputCommon px-4 h-[45px] border-[--border] text-sm DMSans400 text-black w-full'])[2]")
    WebElement inputFieldCustomErrorMessage;

 // Click on save changes
    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary text-sm font-medium DMSans500 radius-4 whitespace-nowrap px-3 h-[40px] bg-[--textClr] text-white capitalize leading-[20px] flex items-center gap-2 hover:bg-[#00427c] css-1ujsas3']")
    WebElement saveChangesWidgetSettings;
    
 // Click on preview chatbot
    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary text-sm font-medium DMSans500 radius-4 whitespace-nowrap px-3 h-[40px] bg-[--textClr] text-white capitalize leading-[20px] flex items-center gap-2 !h-[45px] commonBtn hover:bg-[#00427c] css-1ujsas3']")
    WebElement previewChatbot;

 // Speech Bot Elements
 // Click on speech bot
    @FindBy(xpath = "(//li[@class='cursor-pointer hover:text-heading hover:bg-white text-sm p-[10px] DMSans500 whitespace-nowrap leading-[18px] text-lightGray100  '])[1]")
    WebElement speechBot;

 // Toggle of speech bot
    @FindBy(xpath = "//span[@class='absolute left-2 text-white DMSans400 text-sm transition-opacity opacity-100']")
    WebElement toggleSpeechBot;

 // First avatar
    @FindBy(xpath = "//img[@alt='whiteman avatar']")
    WebElement firstAvatar;

 // Male (professional & Clear)
    @FindBy(xpath = "//div[@class='bg-lightblur058 border-forgettxtclr hover:border-forgettxtclr rounded-lg p-[10px] border cursor-pointer max-smd:w-full smd:min-w-[48%] xlg:min-w-[32%] xlg:max-w-full MuiBox-root css-0']")
    WebElement maleProfessionalClear;

 // Male (Engaging and Expressive)
    @FindBy(xpath = "(//div[@class='bg-light84gray border-border1 hover:border-forgettxtclr rounded-lg p-[10px] border cursor-pointer max-smd:w-full smd:min-w-[48%] xlg:min-w-[32%] xlg:max-w-full MuiBox-root css-0'])[1]")
    WebElement maleEngagingExpressive;

 // Male (soft & warm)
    @FindBy(xpath = "(//div[@class='bg-light84gray border-border1 hover:border-forgettxtclr rounded-lg p-[10px] border cursor-pointer max-smd:w-full smd:min-w-[48%] xlg:min-w-[32%] xlg:max-w-full MuiBox-root css-0'])[2]")
    WebElement maleSoftWarm;

 // Second avatar
    @FindBy(xpath = "//div[@class='absolute inset-0 bg-black flex bg-opacity-40 items-center justify-center MuiBox-root css-0']")
    WebElement secondAvatar;

 // Third avatar
    @FindBy(xpath = "//img[@alt='whitewomen avatar']")
    WebElement thirdAvatar;

 // Fourth avatar
    @FindBy(xpath = "//div[@class='absolute inset-0 bg-black flex bg-opacity-40 items-center justify-center MuiBox-root css-0']")
    WebElement fourthAvatar;

 // Team Member Settings Elements
    @FindBy(xpath = "//li[normalize-space(text())='Team Member Settings']")
    WebElement teamMemberSettings;

    @FindBy(xpath = "//button[@name='agent_escalation']")
    WebElement toggleEscalateToSupportAgent;

    @FindBy(xpath = "//input[@id='min_messages_escalation']")
    WebElement minimumMessagesEscalation;

    @FindBy(xpath = "//button[@name='chat_assistance']")
    WebElement dedicatedInternalSupportBot;

    @FindBy(xpath = "//input[contains(@id, 'react-select')]")
    WebElement privateRCDropdown;
    
    

    @FindBy(xpath = "//button[@name='knowledge_assistance']")
    WebElement aiPoweredReplySuggestions;

 // Integration Elements
    @FindBy(xpath = "//li[@class='cursor-pointer hover:text-heading hover:bg-white text-sm p-[10px] DMSans500 whitespace-nowrap leading-[18px] text-heading lg:bg-white border border-border1 rounded']")
    WebElement integrate;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary text-sm font-medium DMSans500 radius-4 whitespace-nowrap px-3 h-[40px] bg-[--textClr] text-white capitalize leading-[20px] flex items-center gap-2 integratBtne hover:bg-[#00427c] css-1ujsas3'])[1]")
    WebElement getCodeEmbedIframe;

    @FindBy(xpath = "//div[@class='border border-border1 rounded-md min-h-[80px] max-h-[400px] my-4 p-4 MuiBox-root css-0']")
    WebElement inputFieldEmbedCode;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary text-sm font-medium DMSans500 radius-4 whitespace-nowrap px-3 h-[40px] bg-[--textClr] text-white capitalize leading-[20px] flex items-center gap-2 !h-[45px] commonBtn hover:bg-[#00427c] css-1ujsas3'])[2]")
    WebElement copyCode;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeMedium undefined css-1h3sa7w']")
    WebElement crossButton;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary text-sm font-medium DMSans500 radius-4 whitespace-nowrap px-3 h-[40px] bg-[--textClr] text-white capitalize leading-[20px] flex items-center gap-2 integratBtne hover:bg-[#00427c] css-1ujsas3'])[2]")
    WebElement getCodeEmbedScript;

    @FindBy(xpath = "(//a[@target='_blank'])[1]")
    WebElement userGuide;

 // Conversation Panel Elements
    @FindBy(xpath = "(//p[@class='text-base DMSans500 !mb-0 block'])[4]")
    WebElement conversationPanel;

    @FindBy(xpath = "(//button[@type='button'])[2]")
    WebElement ongoingChat;

    @FindBy(xpath = "(//button[@type='button'])[3]")
    WebElement chatHistory;

    @FindBy(xpath = "//input[@placeholder='Search conversation']")
    WebElement searchConversation;

    @FindBy(xpath = "//button[contains(., 'Accept')]")
    WebElement acceptConversation;

    @FindBy(xpath = "//button[contains(., 'Decline')]")
    WebElement declineConversation;

    @FindBy(xpath = "//img[@alt='deleteicon']")
    WebElement addNote;

    @FindBy(xpath = "//img[@alt='logout icon']")
    WebElement closeChat;

    @FindBy(xpath = "//textarea[@placeholder='Enter your message']")
    WebElement enterMessageInputField;

    @FindBy(xpath = "(//img[@alt='icon'])[2]")
    WebElement sendText;

    @FindBy(xpath = "//*[name()='svg' and contains(@class, 'cursor-pointer')]")
    WebElement dedicatedInternalSupportBotClick;

    @FindBy(xpath = "//input[@placeholder='Write a Message...']")
    WebElement writeMessageInputField;

    @FindBy(xpath = "//img[@alt='send msg icon']")
    WebElement sendMessage;

    @FindBy(xpath = "//*[name()='svg' and contains(@class, 'icon-tabler-x')]")
    WebElement closeDedicatedInternalSupportBot;

    @FindBy(xpath = "//button[@class='flex items-center text-white bg-[#297cc5] h-[39px] xs:px-5 xs:py-2 w-fit rounded justify-center']")
    WebElement archiveData;

    @FindBy(xpath = "//button[@class='flex items-center text-white bg-[#297cc5] xl:w-[162px] w-[50px] h-[39px] xl:px-5 xl:py-2 rounded justify-center']")
    WebElement exportChat;
    
 // Team Member Settings Additional Elements
    @FindBy(xpath = "(//li[@class='cursor-pointer hover:text-heading hover:bg-white text-sm p-[10px] DMSans500 whitespace-nowrap leading-[18px] text-lightGray100  '])[2]")
    WebElement teamMemberSetting;

    @FindBy(xpath = "//span[@class='absolute left-2 text-white DMSans400 text-sm transition-opacity opacity-100']")
    WebElement toggleEscalateToAgent;

    @FindBy(xpath = "//input[@id='min_messages_escalation']")
    WebElement minMessagesEscalation;

    @FindBy(xpath = "(//span[@class='absolute left-2 text-white DMSans400 text-sm transition-opacity opacity-100'])[2]")
    WebElement InternalSupportBot;
    
    //chatbot purpose
    @FindBy(xpath ="//input[@id='react-select-3-input']")
    WebElement ChatbotPurposeDropdown;
    
    //chatbot purpose listing
    @FindBy(xpath ="//div[contains(@class,'-option') and text()='Appointment Booking']")
    WebElement ChatbotPurposeListing;
    
    //logo crop
    @FindBy(xpath = "//button[text()='Use This Image']")
    WebElement chatbotLogoConfirm;
    
    //Text training module
    @FindBy(xpath = "//li[p[normalize-space(text())='Text']]")
    WebElement textRC;
    
    //Text area
    @FindBy(xpath = "//textarea[@id='textares']")
    WebElement textArea;
    
    // doc RC
    @FindBy(xpath = "//p[normalize-space()='Documents']")
    WebElement docRC;
    
    //doc upload
    @FindBy(xpath = "//input[@type='file' and @class='hidden']")
    WebElement docUpload;
    
    // FAQ RC
    @FindBy(xpath = "//p[normalize-space(text())='FAQ']")
    WebElement FAQRC;
    
    // Add FAQ
    @FindBy(xpath = "//button[text()='Add FAQ']")
    WebElement AddFAQ;
    
    //confirm add FAQ
    @FindBy(xpath = "//button[normalize-space()='Add']")
    WebElement AddFAQConfirm;
    
    //Question input field
    @FindBy(xpath = " //input[@placeholder='Question']")
    WebElement FAQQuestion;
    
    // Answer input field
    @FindBy(xpath = "//textarea[@placeholder='Enter your text here']")
    WebElement FAQAnswer;
    
    // Delete FAQ
    @FindBy(xpath = "//img[@alt='deleteicon' and contains(@src, 'grayDeleteNewIcon')]")
    WebElement DeleteFAQ;
    
    //select all button
    @FindBy(xpath = "//input[@class='PrivateSwitchBase-input css-1m9pwf3']")
    WebElement selectAll;

    //text max limit validation
    @FindBy(xpath = "//p[contains(text(), 'Max') and contains(text(), 'characters allowed')]")
    WebElement maxText;
    
    //image drag and drop area
    @FindBy(xpath ="//div[contains(@class, 'upload-box')]//input[@type='file']")
    WebElement uploadImageArea;
    
    // images RC
    @FindBy(xpath = "//p[normalize-space(text())='Image']")
    WebElement imagesRC;
    
    // audio RC
    @FindBy(xpath = "//p[text()='Audio']")
    WebElement audioRC;
    
    //upload audio file
    @FindBy(xpath="//button[text()='Upload Audio File']")
    WebElement uploadAudioFile;
    
    // audio upload area
    @FindBy(xpath ="//input[@type='file' and contains(@accept, '.mp3')]")
    WebElement uploadAudioArea;
  
    // got it button (it comes on first time sign up)
    @FindBy(xpath = "//button[contains(., 'Got It')]")
    WebElement gotItButton;
    
    // annual plan toggle
    @FindBy(xpath = "//input[@type='checkbox' and contains(@class, 'MuiSwitch-input')]")
    WebElement yearlyToggle;
    
    // upgrade plan
    @FindBy(xpath = "//button[text()='Upgrade']")
    WebElement upgradeButton;
    
    //proceed
    @FindBy(xpath = "//button[normalize-space(text())='Proceed']")
    WebElement Proceed;
    
    //input characters
    @FindBy(xpath = "//input[@id='idInputWords']")
    WebElement inputCharacters;
    
    //characters radio button
    @FindBy(xpath = "//input[@id='idRadioChars']")
    WebElement CharactersRadioButton;
    
    //copy to clipboard
    @FindBy(xpath = "//img[@id='idCopyToClipboard']")
    WebElement copyToClipboard;
    
    // blank area on web page
    @FindBy(xpath = "//body")
    WebElement blankArea;
}
