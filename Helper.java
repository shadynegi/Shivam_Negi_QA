package chatbot;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@SuppressWarnings("unused")

public class Helper {
    private WebDriver driver;
    private WebDriverWait wait;
    private Xpaths xpaths;
    private Actions actions;
    private Variables variables = new Variables();
    private String timestamp = String.valueOf(System.currentTimeMillis());

    // Constructor for when you already have a driver instance
    public Helper(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.xpaths = new Xpaths(driver);
        this.actions = new Actions(driver);
        this.variables = new Variables();
    }

    public static WebDriver initializeBrowser() {
        // Clean up any existing processes first
        forceCleanupProcesses();
        
        try {
            // Setup WebDriverManager with specific Chrome version handling
            WebDriverManager.chromedriver().setup();
            
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            
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
            options.addArguments("--disable-prompt-on-repost");
            options.addArguments("--disable-sync");
            options.addArguments("--metrics-recording-only");
            options.addArguments("--no-first-run");
            options.addArguments("--safebrowsing-disable-auto-update");
            options.addArguments("--enable-automation");
            options.addArguments("--password-store=basic");
            options.addArguments("--use-mock-keychain");
            
            // Window and display settings
            options.addArguments("--disable-infobars");
            
            // Set a specific user data directory to avoid conflicts
            String userDataDir = System.getProperty("java.io.tmpdir") + "chrome_test_" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + userDataDir);
            
            // Remote debugging on a different port to avoid conflicts
            options.addArguments("--remote-debugging-port=0"); // Let Chrome choose available port
             
            // Notification preferences
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 1); // Allow notifications
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("profile.managed_default_content_settings.images", 1);
            options.setExperimentalOption("prefs", prefs);
            
            // Additional experimental options for stability
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            
            // Set page load strategy
            options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
            
            // Create driver with retry mechanism
            WebDriver driver = null;
            int maxRetries = 3;
            for (int i = 0; i < maxRetries; i++) {
                try {
                    driver = new ChromeDriver(options);
                    // Test if driver is working
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                    driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
                    break;
                } catch (Exception e) {
                    System.err.println("Attempt " + (i + 1) + " failed: " + e.getMessage());
                    if (driver != null) {
                        try {
                            driver.quit();
                        } catch (Exception ex) {
                            // Ignore quit errors
                        }
                    }
                    if (i == maxRetries - 1) {
                        throw e;
                    }
                    // Wait before retry
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            return driver;
            
        } catch (Exception e) {
            System.err.println("Failed to initialize browser: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Browser initialization failed", e);
        }
    }

    // Getters for accessing the initialized objects
    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Xpaths getXpaths() {
        return xpaths;
    }

    public Actions getActions() {
        return actions;
    }

    // Your existing helper methods
    public void clickElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    public void enterText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).click();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void createRC() {
        // Create new RC
        clickElement(xpaths.createRC);
        wait.until(ExpectedConditions.visibilityOf(xpaths.RCName)).sendKeys("test KB " + timestamp);
        xpaths.RCProceed.click();
        variables.Sleep();
    }

    // Method to close the browser
    public void closeBrowser() {
        // Step 1: Close WebDriver properly
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Browser closed successfully");
            } catch (Exception e) {
                System.err.println("Error during driver.quit(): " + e.getMessage());
            } finally {
                driver = null; // Always set to null
            }
        }
        
        // Step 2: Wait for processes to terminate
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Step 3: Force cleanup any remaining processes
        forceCleanupProcesses();
    }

    // Static method for process cleanup using ProcessBuilder
    public static void forceCleanupProcesses() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                // Windows - more thorough cleanup
                new ProcessBuilder("taskkill", "/F", "/IM", "chrome.exe", "/T").start().waitFor();
                new ProcessBuilder("taskkill", "/F", "/IM", "chromedriver.exe", "/T").start().waitFor();
                // Also kill any chrome processes that might be hanging
                new ProcessBuilder("wmic", "process", "where", "name='chrome.exe'", "delete").start().waitFor();
            } else if (os.contains("mac")) {
                // macOS
                new ProcessBuilder("pkill", "-f", "chrome").start().waitFor();
                new ProcessBuilder("pkill", "-f", "chromedriver").start().waitFor();
            } else {
                // Linux
                new ProcessBuilder("pkill", "chrome").start().waitFor();
                new ProcessBuilder("pkill", "chromedriver").start().waitFor();
            }
            
            System.out.println("Browser processes cleaned up");
            
        } catch (Exception e) {
            System.err.println("Error cleaning up processes: " + e.getMessage());
        }
    }
    
    // JS click using javascript executor
    public void jsClick(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
            variables.Sleep();
        } catch (Exception e) {
            System.err.println("Error clicking element using JS: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Upload file using Robot class
    public void uploadFileUsingRobot(String filePath) {
        try {
            // Create Robot instance
            Robot robot = new Robot();
            
            // Wait for file dialog to open
            variables.Sleep();
            
            // Copy file path to clipboard
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            
            // Paste the file path (Ctrl+V)
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            
            // Wait a bit
            variables.Sleep();
            
            // Press Enter to confirm
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
            // Wait for upload to process
            variables.Sleep();
            
        } catch (Exception e) {
            System.err.println("Error uploading file using Robot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Inner class to hold all browser setup objects
    public static class BrowserSetup {
        public final WebDriver driver;
        public final WebDriverWait wait;
        public final Xpaths xpaths;
        public final Actions actions;
        public final Helper helper;

        public BrowserSetup(WebDriver driver, WebDriverWait wait, Xpaths xpaths, Actions actions, Helper helper) {
            this.driver = driver;
            this.wait = wait;
            this.xpaths = xpaths;
            this.actions = actions;
            this.helper = helper;
        }
    }
}