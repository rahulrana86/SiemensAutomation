package siemens.energy.org.crm.selenium.common;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

/*
 * This class is containing reusable methods which are going to be used in all the pages
 */
public class BasePage
{
	public final boolean isDebug = false;
	public static SoftAssert softAssert = new SoftAssert();
	public static String Step="";
	public static String projectPath = System.getProperty("user.dir");
	public static WebDriverWait wait;
	
	// Getting element path from config file
	public static WebElement getElement(String locatorKey)
	{
		WebElement elm = null;
		if (locatorKey != null)
		{
			if (locatorKey.endsWith("XPATH"))
			{
				elm = BaseTest.driver.findElement(By.xpath(BaseTest.prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("ID"))
			{
				elm = BaseTest.driver.findElement(By.id(BaseTest.prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("LINK"))
			{
				elm = BaseTest.driver.findElement(By.linkText(BaseTest.prop.getProperty(locatorKey)));
			}
			return elm;
		}
		else
		{
			System.out.println("Element is missing");
		}
		return elm;
	}

	// Get text of webelement
	public static String getText(String locatorKey)
	{
		WebElement elm = null;
		elm = getElement(locatorKey);
		return elm.getText();
	}

	// Verify webelement displays on page
	public boolean isDisplayed(String locatorKey)
	{
		WebElement elm = null;
		elm = getElement(locatorKey);
		return elm.isDisplayed();
	}

	// Pass input values in textfield
	public static void setText(String locatorKey, String inputValue) throws Throwable
	{
		WebElement elm = null;
		elm = getElement(locatorKey);
		elm.sendKeys(inputValue);
	}

	// Click on webelement
	public static void click(String locatorKey)
	{
		WebElement elm = null;
		elm = getElement(locatorKey);
		elm.click();
	}
	
	// 
	// Clear on webelement
		public static void clear(String locatorKey)
		{
			WebElement elm = null;
			elm = getElement(locatorKey);
			elm.clear();;
		}
	
	// Switch to window
	public void swithToNewWindow()
	{
		for (String handle : BaseTest.driver.getWindowHandles())
  		{
			BaseTest.driver.switchTo().window(handle); 			
  		}
	}
		
	public static String capturescreenshot(WebDriver driver,String screenshotName) throws IOException
	{
		  
		try {
			
			TakesScreenshot ts =(TakesScreenshot)driver;
			File source=ts.getScreenshotAs(OutputType.FILE);
			Date d=new Date();
			String fileName="_Screenshot_"+d.toString().replace(":", "_").replace(" ", "_");	
			String dest=projectPath+ "\\Resources\\Results"+screenshotName+fileName+".png";
			File destination=new File(dest);
			
			
			
			FileUtils.copyFile(source, destination);
			System.out.print("Screenshot Taken");
		return dest;
		}
		
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		
		//System.setProperty("webdriver.gecko.driver","D://Selenium Environment//Drivers//geckodriver.exe");
		 //File screenshotFile = ((TakesScreenshot)BaseTest.driver).getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(screenshotFile, new File("D:\\Automation\\Projects\\CSM\\Resources\\Results.png"));
		// System.out.println("Screenshot Taken");
		}
			
	// wait for element for specified amount of time before throwing exception
	public static void waitAndClick(String locatorKey) throws IOException
	{
	
	wait=new WebDriverWait(BaseTest.driver,30);
	 
	WebElement elm = null;
	elm = getElement(locatorKey);
	
	// Wait till the element is not visible
	WebElement elem=wait.until(ExpectedConditions.elementToBeClickable(elm));
	
	boolean elemle = elem.isEnabled();
	// if else condition
			if (elemle)
			{
				System.out.println("===== WebDriver is visible======");
				elem.click();
			} 
			else
			{
				System.out.println("===== WebDriver is not visible======");
			}
	
	elem.click();
	}
	
	// wait for element for specified amount of time before throwing exception
		public static void waitForElement(String locatorKey) throws IOException
		{
		
		wait=new WebDriverWait(BaseTest.driver,20);
		 
		WebElement elm = null;
		elm = getElement(locatorKey);
		
		// Wait till the element is not visible
		WebElement elem=wait.until(ExpectedConditions.visibilityOf(elm));
		
		boolean elemle = elem.isDisplayed();
		// if else condition
				if (elemle)
				{
					System.out.println("===== WebDriver is visible======");
					
				} 
				else
				{
					System.out.println("===== WebDriver is not visible======");
				}
		
				elem.isDisplayed();
				
				}
	
	 public static void waitForLoad(WebDriver driver)
	 {
	        ExpectedCondition<Boolean> pageLoadCondition = new
	                ExpectedCondition<Boolean>()
	        {
	                    public Boolean apply(WebDriver driver)
	                    {
	                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	                    }
	                };
	        WebDriverWait wait = new WebDriverWait(driver, 30);
	        wait.until(pageLoadCondition);
	    }
	 
	 
	 public static boolean isAlertPresent(){
		 try{
		  BaseTest.driver.switchTo().alert();
		  return true;
		 }
		 catch(NoAlertPresentException ex)
		 {
		  return false;
		 }
		}
	
}