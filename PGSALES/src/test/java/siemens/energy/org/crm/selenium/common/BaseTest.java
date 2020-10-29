package siemens.energy.org.crm.selenium.common;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//@Listeners(siemens.energy.org.crm.selenium.common.ListenerTest.class)

/*
 * This class is containing reusable methods which are going to be used in other classes
 */
public class BaseTest 
{
	public static Properties prop = null;
	public  static WebDriver driver;
	public final boolean isDebug = false;
	public static String projectPath = System.getProperty("user.dir");
	public  ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public  static ExtentTest test;
	
	/*
	 * Getting properties of config file
	 */
	{
		if (prop == null)
		{
			prop = new Properties();
			try {
				//FileInputStream propFile = new FileInputStream(
					//	projectPath + "\\src\\test\\java\\siemens\\energy\\org\\crm\\selenium\\common\\config.properties");
				FileInputStream propFile = new FileInputStream(
						projectPath + "/src/test/java/siemens/energy/org/crm/selenium/common/config.properties");
				prop = new Properties();
				prop.load(propFile);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

//	@BeforeTest
//	 public void setExtent() {
//	  // specify location of the report
//		
//		System.out.println("Before Test Started");
//	  htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myReport.html");
//
//	  htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
//	  htmlReporter.config().setReportName("Functional Testing"); // Name of the report
//	  htmlReporter.config().setTheme(Theme.STANDARD);
//	  
//	  extent = new ExtentReports();
//	  extent.attachReporter(htmlReporter);
//	  
//	  // Passing General information
//	  extent.setSystemInfo("Host name", "localhost");
//	  extent.setSystemInfo("Environemnt", "QA");
//	  extent.setSystemInfo("user", "Rahul R");
//	  
//	  System.out.println("Before Test Completed");
//	 }
//	
	 @AfterTest
	 public void endReport() {
	  extent.flush();
	 }

	
	// Launch chrome browser and login before every class
	//@Parameters({"username", "password"})
	@BeforeClass
	public void launchBrowser() throws Throwable
	{		
		
		
		  htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myReport.html");

		  htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
		  htmlReporter.config().setReportName("Functional Testing"); // Name of the report
		  htmlReporter.config().setTheme(Theme.STANDARD);
		  
		  extent = new ExtentReports();
		  extent.attachReporter(htmlReporter);
		  
		  // Passing General information
		  extent.setSystemInfo("Host name", "localhost");
		  extent.setSystemInfo("Environemnt", "Full");
		  extent.setSystemInfo("user", "Selenium TestUser");
		
		// Create browser instance and invoke
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
		System.setProperty("webdriver.chrome.driver","/usr/bin/google-chrome");
		//System.setProperty("webdriver.chrome.driver", projectPath + "\\Resources\\chromedriver.exe");
		//C:\Users\Z003r03h\eclipse-workspace\CEP\Resources\chromedriver.exe
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
	
//		 DesiredCapabilities caps = new DesiredCapabilities();
//	     caps.setCapability("username", "narverma@deloitte.de");
//	     caps.setCapability("accessKey", "76552ff5-54c9-41b7-a64e-c8a2c49bebe8");
//	     caps.setCapability("browserName", "chrome");
//	     caps.setCapability("version", "75");
//	     caps.setCapability("platform","WIN7");

//	     driver = new RemoteWebDriver(new URL("https://ondemand.saucelabs.com/wd/hub"), caps);
		
		ChromeOptions options = new ChromeOptions();
		
		options.setHeadless(true);
		//options.addArguments("--headless");
	    options.addArguments("--disable-gpu");
	    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	    options.addArguments("--ignore-certificate-errors");
	    //options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("disable-infobars");
		options.addArguments("--disable-notifications");
		
	
		options.addArguments("start-maximized"); 
	    options.addArguments("enable-automation"); 
	    options.addArguments("--no-sandbox"); 
	  
	    options.addArguments("--disable-dev-shm-usage"); 
	    options.addArguments("--disable-browser-side-navigation"); 
	 
		//options.setPageLoadStrategy(PageLoadStrategy.NONE);
		//options.setCapability(ChromeOptions.CAPABILITY, options);
		//options.setExperimentalOption("useAutomationExtension", false);
		
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		
//		Capabilities capb =((RemoteWebDriver) driver).getCapabilities();
//		String bromserName=capb.getBrowserName();
//		String browserVersion=capb.getVersion();
//		String os = System.getProperty("os.name").toLowerCase();
//		
//		System.out.println("OS: "+os+ "\n" + "Browser Name: "+bromserName+ "\n" +"Browser Version: " +browserVersion+"\n");
//		
		// Set default timeout for locating and element in the DOM (seconds)
		driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);

		//driver.get("http://localhost:9515/");
		// Navigate to saleforce page and login
		navigateToUrl();
		                                                
		driver.findElement(By.id("username")).sendKeys("selenium.test@siemens.com");
		driver.findElement(By.id("password")).sendKeys("RATEDare@1234");
		driver.findElement(By.id("Login")).click();	
		
		Thread.sleep(40000);
	}	
	
		
	// Close browser
	@AfterClass
	public void terminateBrowser()
	{
		extent.flush();
		//driver.quit();
	}

	// Open url in browser
	public void navigateToUrl()
	{
		driver.navigate().to(prop.getProperty("appUrlfull"));
	}
	
	
}
