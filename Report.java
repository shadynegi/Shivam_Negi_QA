package chatbot;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Base64;

@SuppressWarnings("unused")

public class Report implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private String reportPath;
    Variables Variables = new Variables();
    private static Report instance;

    @Override
    public void onStart(ITestContext context) {
    	instance = this; // Set the static reference
        // Create reports directory if it doesn't exist
        String userDesktop = System.getProperty("user.home") + "\\OneDrive/Desktop";
        String reportsDir = userDesktop + "/TestReports";
        new File(reportsDir).mkdirs();

        // Create timestamp for report name and file name
        String timestamp = new SimpleDateFormat("dd_MM_yyyy HH_mm").format(new Date());
        
        reportPath = reportsDir + "/AutomationTestReport_" + timestamp + ".html";
        sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Configure report settings
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Chatbot Testing Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("'Date:' dd/MM/yyyy 'Time:' HH:mm");
        
     // Replace the default logo with itsbot logo using CSS
        try {
            String customCSS =
                ".nav-logo .logo { " +
                "    background-image: url('https://itsbot.ai/icons/svg/logoSvg.svg') !important; " +
                "    background-size: contain !important; " +
                "    background-repeat: no-repeat !important; " +
                "    background-position: center !important; " +
                "    width: 120px !important; " +
                "    height: 40px !important; " +
                "    min-width: 120px !important; " +
                "    min-height: 40px !important; " +
                "    margin-top: 4px !important; " +
                "} " +
                ".search-box { " +
                "    position: relative !important; " +
                "    left: 100px !important;" +
                "    margin-top: 3px !important;" +
                "} " +
				".search-input.active {" +
				"	margin-left: 100px !important;" +
				"    margin-top: -2px !important;" +
				"}";
            
            sparkReporter.config().setCss(customCSS);
        } catch (Exception e) {
        	logInfo("Failed to set custom logo: " + e.getMessage());
        }
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setReportUsesManualConfiguration(true);
        
        // Add system information
        extent.setSystemInfo("Created By", Variables.createdBy);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Test Suite", context.getSuite().getName());
        //extent.setSystemInfo("Environment", "Test Environment");
        
        logInfo("Report will be generated at: " + reportPath);
    }
    
    // Add this static method to get the current ExtentTest instance
    public static ExtentTest getCurrentExtentTest() {
        if (instance != null && instance.test.get() != null) {
            return instance.test.get();
        }
        return null;
    }
    
 // Add this static method for easier logging from test classes
    public static void logInfo(String message) {
        ExtentTest currentTest = getCurrentExtentTest();
        if (currentTest != null) {
            currentTest.log(Status.INFO, message);
        }
        System.out.println(message); // Also print to console
    }
    
    public static void logPass(String message) {
        ExtentTest currentTest = getCurrentExtentTest();
        if (currentTest != null) {
            currentTest.log(Status.PASS, message);
        }
        logInfo(message);
    }
    
    public static void logFail(String message) {
        ExtentTest currentTest = getCurrentExtentTest();
        if (currentTest != null) {
            currentTest.log(Status.FAIL, message);
        }
        logInfo(message);
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        String testDescription = result.getMethod().getDescription();
        ExtentTest extentTest = extent.createTest(result.getName(), testDescription != null ? testDescription : "");
        test.set(extentTest);
        
        // Log test start
        test.get().log(Status.INFO, "Test execution started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test PASSED: " + result.getName());
        
        // Log execution time
        long executionTime = result.getEndMillis() - result.getStartMillis();
        test.get().log(Status.INFO, "Execution time: " + executionTime + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test FAILED: " + result.getName());
        test.get().log(Status.FAIL, "Error: " + result.getThrowable().getMessage());
        
        // Log full stack trace
        if (result.getThrowable() != null) {
            test.get().log(Status.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");
        }

        // Generic way to get WebDriver from test class
        Object testClass = result.getInstance();
        WebDriver driver = getDriverFromTestClass(testClass);

        if (driver != null) {
            // Direct Base64 embedding
            embedScreenshotDirectly(driver, result.getName());
        }
    }
    
    private void embedScreenshotDirectly(WebDriver driver, String testName) {
        try {
            // Take screenshot and get as byte array directly
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            
            // Convert to base64
            String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);
            
            // Create HTML image tag
            String htmlImage = "<img src=\"data:image/png;base64," + base64Screenshot + 
                              "\" width=\"800\" height=\"auto\" style=\"border: 1px solid #ccc; border-radius: 5px;\" />";
            
            // Log to report
            test.get().log(Status.INFO, "Screenshot:");
            test.get().log(Status.INFO, htmlImage);
            
            logInfo("Screenshot embedded successfully for test: " + testName);
            
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Could not embed screenshot: " + e.getMessage());
            logInfo("Failed to embed screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test SKIPPED: " + result.getName());
        if (result.getThrowable() != null) {
            test.get().log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Add test summary
        int totalTests = context.getAllTestMethods().length;
        int passedTests = context.getPassedTests().size();
        int failedTests = context.getFailedTests().size();
        int skippedTests = context.getSkippedTests().size();
        
        logInfo("\n=== Test Execution Summary ===");
        logInfo("Total Tests: " + totalTests);
        logInfo("Passed: " + passedTests);
        logInfo("Failed: " + failedTests);
        logInfo("Skipped: " + skippedTests);
        logInfo("Report generated at: " + reportPath);
        logInfo("==============================\n");
        
        // Flush the report
        if (extent != null) {
            extent.flush();
        }
        
        // Clean up ThreadLocal
        test.remove();
        
        //open report in chrome
        openReportInBrowser();
    }

    // Cross-platform browser opening
    private void openReportInBrowser() {
        try {
            // Check if Desktop is supported
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                
                // Check if BROWSE action is supported
                if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    File reportFile = new File(reportPath);
                    desktop.browse(reportFile.toURI());
                    logInfo("Report opened in default browser successfully");
                    return;
                }
            }
            
        } catch (Exception e) {
            logInfo("Failed to open report automatically: " + e.getMessage());
            logInfo("Please manually open the report at: " + reportPath);
        }
    }

    // Generic method to get driver from any test class
    private WebDriver getDriverFromTestClass(Object testClass) {
        try {
            // Try to get driver using getDriver() method
            Method getDriverMethod = testClass.getClass().getMethod("getDriver");
            return (WebDriver) getDriverMethod.invoke(testClass);
        } catch (Exception e) {
            try {
                // Try to get driver field directly
                java.lang.reflect.Field driverField = testClass.getClass().getDeclaredField("driver");
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(testClass);
            } catch (Exception ex) {
            	logInfo("Could not get driver from test class: " + ex.getMessage());
                return null;
            }
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}