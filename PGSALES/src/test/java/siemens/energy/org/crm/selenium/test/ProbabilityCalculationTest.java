/**
 * 
 */
package siemens.energy.org.crm.selenium.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import siemens.energy.org.crm.selenium.common.BasePage;
import siemens.energy.org.crm.selenium.common.BaseTest;
import siemens.energy.org.crm.selenium.common.DataManager;
import siemens.energy.org.crm.selenium.page.AllPeoplePage;
import siemens.energy.org.crm.selenium.page.AllTabPage;
import siemens.energy.org.crm.selenium.page.CaseEntryPage;
import siemens.energy.org.crm.selenium.page.CaseHomePage;
import siemens.energy.org.crm.selenium.page.CaseRecordTypePage;
import siemens.energy.org.crm.selenium.page.HomePage;
import siemens.energy.org.crm.selenium.page.LinkedOpportunityPage;

/**
 * @author z003r03h
 *
 */

public class ProbabilityCalculationTest extends BaseTest {

	String projectPath = System.getProperty("user.dir");
	HomePage homePage = new HomePage();
	BasePage basePage = new BasePage();
	CaseEntryPage caseEntryPage = new CaseEntryPage();
	CaseRecordTypePage caseRecordTypePage = new CaseRecordTypePage();
	AllPeoplePage allPeoplePage = new AllPeoplePage();
	AllTabPage allTabPage = new AllTabPage();
	CaseHomePage casesHomePage = new CaseHomePage(); 
	LinkedOpportunityPage linkedOpportunityPage = new LinkedOpportunityPage();

	public String testCaseName="Acknowledge Case";
	public static Actions builder;
	public static  JavascriptExecutor js;
	private int a;
	public static String projectGORate;
	public static String user;
	public static String profile;
	public static int totalRows;
	public static List<WebElement> rows;
	public static int totalRows1;
	public static List<WebElement> rows1;
	public static boolean apexWindow = false;
	public static boolean opportunityWindow=false;
	public static String parent1;
	public static String goRateValue="24";
	public static WebElement searchBox;
	@Test(priority = 1, dataProvider = "businessUserAndProfile")
	public void probablityValidation(String user, String profile) throws Throwable
	{

		test = extent.createTest("Opportunity Probablity Validation");
		//BasePage.capturescreenshot(BaseTest.driver, "New Login");
		// boolean flag to verify login status of business user
		boolean loginSuccess = false;
		apexWindow = false;
		opportunityWindow=false;

		try
		{	
			js = (JavascriptExecutor)driver;  

			//Thread.sleep(5000);
			// Select All Tab
			homePage.allTab();

			// Select People Tab
			allTabPage.selectPeople();

			Thread.sleep(3000);

			// Search and login as business user
			allPeoplePage.loginAsBusinessUser(user, profile);
			loginSuccess = true;
			test.log(Status.PASS, "Loggedin As "+user);

			Thread.sleep(10000);

			
			searchBox= driver.findElement(By.xpath("//*[@id=\"input-4\"]"));
			searchBox.click();
			//BasePage.click("SearchDDPG_XPATH");

			builder = new Actions(driver);
			builder.sendKeys(searchBox, Keys.BACK_SPACE).perform();
			Thread.sleep(2000);
			builder.sendKeys(searchBox, Keys.BACK_SPACE).perform();
			Thread.sleep(2000);
			builder.sendKeys(searchBox, Keys.BACK_SPACE).perform();


			Thread.sleep(5000);
			
			//BasePage.setText("SearchDDPG_XPATH", "Projects");
			searchBox.sendKeys("Projects");
			
			Thread.sleep(5000);
			builder.sendKeys(searchBox, Keys.ENTER).perform();

			// Searchi Project//
			BasePage.setText("SearchBox_XPATH", "AutomationTestProject");
			Thread.sleep(3000);
			BasePage.getElement("SearchBox_XPATH").sendKeys(Keys.ENTER);

			Thread.sleep(3000);
			try {
		    BasePage.click("SelectSearchedUser_XPATH");

			test.log(Status.PASS, "Navigated to AutomationTestProject");
			Thread.sleep(5000);
			}
			catch(Exception e)
			{
				driver.findElement(By.xpath("//*[@id=\"brandBand_2\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div[1]/div[1]/div[2]/div/div/div/div[2]/div[2]/div[1]/div/div/table/tbody/tr[1]/th/span/a")).click();
			}
			//BasePage.waitForLoad(BaseTest.driver);

			try
			{
				BasePage.click("AlertOK_XPATH");
			}
			catch(Exception e)
			{
				System.out.println("No Such Alert");
			}

			Thread.sleep(2000);
			
			// Scroll up
			js = (JavascriptExecutor)driver;  
			js.executeScript("window.scrollBy(0,1050)", "");

			// BasePage.click("EditGoRate_XPATH");

			WebElement editGoRate=BasePage.getElement("EditGoRate_XPATH");
			js.executeScript("arguments[0].click();", editGoRate);
			
			Thread.sleep(3000);
			
			// Clear go rate text box 
			BasePage.clear("GoRateTextBox_XPATH");

			// pass go rate value			
			LinkedOpportunityPage.probabilityRate(user);

			
			// click on Save button
			BasePage.click("ProjectSaveButton_XPATH");

			test.log(Status.PASS, "Go Rate is updated");

			String goRateValue =BasePage.getText("GetGoRate_XPATH");

			projectGORate =goRateValue.replace("%", "");
			System.out.println("Go Rate Value: " +projectGORate);


			// Scroll up
			
			js.executeScript("window.scrollBy(0,-750)", "");

			
			// select opportunity as per user
			System.out.println("\n"+"Profile Name: " +profile +"\n");
//			if(profile.contains("PG Engine Business"))
//			{ 
//
//				WebElement relatedTab= BasePage.getElement("RelatedTab_XPATH");
//
//				js.executeScript("arguments[0].click();", relatedTab);
//
//
//				BasePage.waitForElement("RelLinkedOpp_XPATH");
//				WebElement opportunity= BasePage.getElement("RelLinkedOpp_XPATH");
//
//				js.executeScript("arguments[0].click();", opportunity);
//
//			}
//			else
//			{
//				Thread.sleep(5000);
				WebElement element =  BasePage.getElement("LinkedOppor_XPATH");
				js.executeScript("arguments[0].click();", element);
			//}

			
			Thread.sleep(5000);
			
			// Scroll Webpage//

			EventFiringWebDriver eventfiringWebDriver = new EventFiringWebDriver(BaseTest.driver);
			eventfiringWebDriver.executeScript(
					"document.querySelector('#brandBand_2 > div > div > div.windowViewMode-normal.oneContent.active.lafPageHost > div > div > div.slds-grid.listDisplays.safari-workaround-anchor > div > div.slds-col.slds-no-space.forceListViewManagerPrimaryDisplayManager > div.hideSelection.forceListViewManagerGrid > div.listViewContent.slds-table--header-fixed_container > div.uiScroller.scroller-wrapper.scroll-bidirectional.native').scrollTop=3000");
			
			// Loop over all opportunities
			WebElement relatedList = BasePage.getElement("LinkedOppTable_XPATH");

			rows = relatedList.findElements(By.tagName("tr"));
			totalRows = rows.size();
			System.out.println("Total number of opportunity table rows: " + totalRows);


			Thread.sleep(950000);
			
			// It will return the parent window name as a String
			parent1=driver.getWindowHandle();	    		    	 

			Thread.sleep(3000);
			// click on setup
			WebElement setUp =BasePage.getElement("SetupButton_XPATH");

			Thread.sleep(3000);

			// Select Setup option
			builder = new Actions(driver);
			builder.sendKeys(setUp, Keys.ENTER).sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(2000);
			builder.sendKeys(Keys.ENTER).build().perform();

			Thread.sleep(5000);

			LinkedOpportunityPage.apexClassStatus(parent1);
			if(apexWindow==false)
			{// Loop over all opportunities

				WebElement relatedList1 = BasePage.getElement("LinkedOppTable_XPATH");

				rows1 = relatedList1.findElements(By.tagName("tr"));

				totalRows1 = rows1.size();

				
				
				LinkedOpportunityPage.linkedOpportunity(user);

			}	    	    
		}
		catch(Exception e)
		{

			System.out.println(e.getMessage());
			if(apexWindow==true)
			{
				
				driver.close();
				BaseTest.driver.switchTo().window(parent1);
				BasePage.click("LogoutBusiUser_XPATH");
				AssertJUnit.assertTrue(false);
				
			}
			else if(opportunityWindow==true)
			{
				
				BaseTest.driver.switchTo().window(LinkedOpportunityPage.parent);
				BasePage.click("LogoutBusiUser_XPATH");
				AssertJUnit.assertTrue(false);
				
			}
		}

		
		// Logout from Business User//
		BasePage.click("LogoutBusiUser_XPATH");
		
	}

	@DataProvider(name = "businessUserAndProfile")
	public static Object[][] businessUserLogin() throws IOException
	{
		String path = BaseTest.projectPath + "\\Resources\\Test Data.xlsx";
		return DataManager.getData(path, "User");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException
	{

		if(result.getStatus()==ITestResult.FAILURE)
		{
			String screenshot_path=BasePage.capturescreenshot(BaseTest.driver,result.getName());
			BaseTest.test.addScreenCaptureFromPath(screenshot_path);
			BaseTest.test.log(Status.FAIL, result.getThrowable());

		}

		extent.flush();


	}
}
