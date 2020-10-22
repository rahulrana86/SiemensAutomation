package siemens.energy.org.crm.selenium.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
public class CaseRoutingMatrixTest extends BaseTest
{
	String projectPath = System.getProperty("user.dir");
	HomePage homePage = new HomePage();
	BasePage basePage = new BasePage();
	CaseEntryPage caseEntryPage = new CaseEntryPage();
	CaseRecordTypePage caseRecordTypePage = new CaseRecordTypePage();
	AllPeoplePage allPeoplePage = new AllPeoplePage();
	AllTabPage allTabPage = new AllTabPage();
	CaseHomePage casesHomePage = new CaseHomePage();
	public static String equipment;
	public static String category;
	public static String caseissue;
	public static String queue;
	public static String sheetPath="D:\\Automation\\Projects\\CSM\\Resources\\Copy of Triage Routing Matrix - 28 Aug 18 Part 2 Only to IT_12th sep_Full_QA3.xlsx";
	public static String resultSheetPath="D:\\Automation\\Projects\\CSM\\Resources\\Results\\Copy of Triage Routing Matrix - 28 Aug 18 Part 2 Only to IT_12th sep_Full_QA3.xlsx";
	public static HashMap<String, List<String>> overAllResult = new HashMap<String, List<String>>();
	
	// Case field verification for business user
	@Test(priority = 1, dataProvider = "businessUserAndProfile")
	public void caseFieldVerification(String user, String profile) throws Throwable
	{
		// boolean flag to verify login status of business user
		boolean loginSuccess = false;
		try
		{
			
			CaseEntryPage.caseRoutingDataManagement(sheetPath);
			
			// Select All Tab
			homePage.allTab();

			// Select People Tab
			allTabPage.selectPeople();

			// Search and login as business user
			allPeoplePage.loginAsBusinessUser(user, profile);
			loginSuccess = true;

			// Get current url
			String currentUrl = driver.getCurrentUrl();
			
			// Switch to classic view
			//homePage.switchToClassicView();

			// Search case number //
			basePage.setText("SearchUser_ID","808949");
			basePage.click("SearchButton_ID");
			basePage.click("SelectSearchedCase_XPATH");
			basePage.click("CaseDetailsButton_XPATH");
            
            // initiate method //
            System.out.println("New"+CaseEntryPage.totalRowCount);
            for(int j=1; j<=CaseEntryPage.totalRowCount; j++)
     		{
            	
//            	if(j>CaseEntryPage.totalRowCount)
//            	{
//	 				break;
//	 			}
            	
            	category = CaseEntryPage.sheetName.getRow(j).getCell(5).getStringCellValue();
            	System.out.println(category);
                caseissue =CaseEntryPage.sheetName.getRow(j).getCell(6).getStringCellValue();
                System.out.println(caseissue);
                equipment =CaseEntryPage.sheetName.getRow(j).getCell(11).getStringCellValue();
                System.out.println(equipment);
                queue =CaseEntryPage.sheetName.getRow(j).getCell(10).getStringCellValue();
                System.out.println(queue);
                System.out.println(category+caseissue+equipment+queue);
            	
		BaseTest.driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[4]")).click();
			
			
	    caseEntryPage.selectEquipment(equipment);
			
			
		caseEntryPage.deselectMultiPickListValues("ISScatdesel_ID");
			
		caseEntryPage.deselectMultiPickListValues("Isstypedesel_ID");
			
		
		caseEntryPage.selectIssueCategoryValue("ISScatsel_ID", category, "IssueCatRightClick_ID");
		
		System.out.println("Case issue type value" +caseissue);
		
		if(!caseissue.contains("ALL"))
		{
			caseEntryPage.selectCaseIssueValue("IssueTypesel_ID", "IssueTypRightClick_ID", caseissue);
		}
		else
		{
			caseEntryPage.selectCaseIssueType(category);
		}
		
		// Save Case //
		basePage.click("Save_XPATH");
		
		System.out.println("Latest "+queue);
		// Getting Case details//
		CaseEntryPage.saveDetailsInWorkbook(j, queue, resultSheetPath);
			
     		}
            CaseEntryPage.woorkBook.close();
            ;
            Assert.assertTrue(false);
            }
		catch (Exception e) 
		{
			System.out.println("Exception"+e);
			
	     }
		}

	
	@DataProvider(name = "businessUserAndProfile")
	public static Object[][] businessUserLogin() throws IOException
	{
		String path = BaseTest.projectPath + "\\Resources\\Test Data.xlsx";
		return DataManager.getData(path, "User");
	}

}
