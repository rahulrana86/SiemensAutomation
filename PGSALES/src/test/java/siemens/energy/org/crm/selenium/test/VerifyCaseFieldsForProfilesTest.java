package siemens.energy.org.crm.selenium.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import siemens.energy.org.crm.selenium.common.BasePage;
import siemens.energy.org.crm.selenium.common.BaseTest;
import siemens.energy.org.crm.selenium.common.DataManager;
import siemens.energy.org.crm.selenium.page.AllPeoplePage;
import siemens.energy.org.crm.selenium.page.AllTabPage;
import siemens.energy.org.crm.selenium.page.CaseEntryPage;
import siemens.energy.org.crm.selenium.page.CaseHomePage;
import siemens.energy.org.crm.selenium.page.CaseRecordTypePage;
import siemens.energy.org.crm.selenium.page.HomePage;

/*
 * This class verifies proper fields displayed on the CSM case creation page for business user.
 */
public class VerifyCaseFieldsForProfilesTest extends BaseTest
{
	String projectPath = System.getProperty("user.dir");
	HomePage homePage = new HomePage();
	BasePage basePage = new BasePage();
	CaseEntryPage caseEntryPage = new CaseEntryPage();
	CaseRecordTypePage caseRecordTypePage = new CaseRecordTypePage();
	AllPeoplePage allPeoplePage = new AllPeoplePage();
	AllTabPage allTabPage = new AllTabPage();
	CaseHomePage casesHomePage = new CaseHomePage(); 
	public String testCaseName="Acknowledge Case";
	
	public static HashMap<String, List<String>> overAllResult = new HashMap<String, List<String>>();
	 
	 
	// Case field verification for business user
	@Test(priority = 1, dataProvider = "businessUserAndProfile")
	public void caseFieldVerification(String user, String profile) throws Throwable
	{
		test = extent.createTest("Case Element Verification");
		
		// boolean flag to verify login status of business user
		boolean loginSuccess = false;
		try
		{
			test.log(Status.PASS, "User Loggedin Successfully");
			
			
			// Select All Tab
			homePage.allTab();
			 
			
			// Select People Tab
			allTabPage.selectPeople();
			
			// Search and login as business user
			allPeoplePage.loginAsBusinessUser(user, profile);
			loginSuccess = true;
			test.log(Status.PASS, "User Loggedin as business user");
			

			// Get current url
			String currentUrl = driver.getCurrentUrl();
			
			// Switch to classic view
			//homePage.switchToClassicView();

			
			
			// Navigate to all Tab
			homePage.allTab();

			// Select case tab
			allTabPage.selectCaseTab();

			BasePage.capturescreenshot(BaseTest.driver,"screenshotName");
			//BasePage.capturescreenshot();
			
			// Navigate to new case page
			casesHomePage.newCase();

			// Select "Customer Issue" record type			
			caseRecordTypePage.selectRecordType("p3", "Customer Issue", user, profile);
				
			test.log(Status.PASS, "New Case Record Type is selected");
			
			// Verify case field on case entry page
			caseEntryPage.caseFieldVerification(user, profile);	
						
			test.log(Status.INFO, "Element verification completed for: " +user +" and " +profile);
			
			// Switch back to parent view after field verification
			homePage.restoreViewAndLogout(currentUrl);
			
			// Verify case fields and log result of each business user
			if (CaseEntryPage.missingElements.isEmpty())
			{
				System.out.println("Passed: " + user + ", " + profile);
				
			} 
			else
			{
				// Add missing element in iterator
				Iterator<String> missingEleIter = CaseEntryPage.missingElements.iterator();
				List<String> missingEleList = new ArrayList<String>();

				// Add all missing element in List
				while (missingEleIter.hasNext())
				{
					missingEleList.add(missingEleIter.next());
				}

				// Map missing element for each user in map
				if (missingEleList != null && missingEleList.size() > 0)
				{
					overAllResult.put(user + ", " + profile, missingEleList);
					
				}
				
				AssertJUnit.assertTrue(false);
			}
		}
		catch (Exception e) 
		{
			// Log exception for each user in List
			List<String> exceptionList = new ArrayList<String>();
			exceptionList.add("Unable to: " +BasePage.Step);
			overAllResult.put(user + ", " + profile, exceptionList);
			System.out.println("Error message: "+e.getMessage()+" Line"+e.getStackTrace());
			
			// Logout only if user logged in as business user
			if (loginSuccess == true)
			{
				homePage.logout();
			}
			    
			    AssertJUnit.assertTrue(false);
		    }
	}

	// Log and print failed result of business user
	@Test(priority = 2)
	public void testFailure()
	{
		// Print failed user along with reason
		if (overAllResult != null && overAllResult.size() > 0)
		{
			Set<String> userStatus = overAllResult.keySet();

			// Display user and profile for failed user
			for (String status : userStatus)
			{
				System.out.println("Failed: " + status);

				// Add user and reason of failure in list
				List<String> failedUser = overAllResult.get(status);

				if (failedUser != null && failedUser.size() > 0)
				{
					// Display user along with reason of failure
					for (String ele : failedUser)
					{
						System.out.println(ele);
						test.log(Status.INFO, "Element missing elements for users: " +ele);
					}
				}
			}
		}
	}

	// Print overall result
	@Test(priority = 3)
	public void overAllTestResult() 
	{
		if (!overAllResult.isEmpty())
		{
			System.out.println("\n*******************\n" + "Result: Failed" + "\n*******************\n");
		}
		else
		{
			System.out.println("\n*******************\n" + "Result: Passed" + "\n*******************\n");
		}
	}

	// This will run after testcase and it will capture screenshot and add in report
	
	@DataProvider(name = "businessUserAndProfile")
	public static Object[][] businessUserLogin() throws IOException
	{
		String path = BaseTest.projectPath + "\\Resources\\Test Data.xlsx";
		return DataManager.getData(path, "User");
	}

}
