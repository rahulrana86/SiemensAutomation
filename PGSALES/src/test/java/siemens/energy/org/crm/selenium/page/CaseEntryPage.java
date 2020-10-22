package siemens.energy.org.crm.selenium.page;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import siemens.energy.org.crm.selenium.common.BasePage;
import siemens.energy.org.crm.selenium.common.BaseTest;

/*
 * This class is containing methods of case entry page
 */
public class CaseEntryPage extends BasePage
{
	public static int totalRowCount;
	public static XSSFSheet sheetName;
	public static Actions doclick;
	public static ArrayList<String> missingElements = new ArrayList<String>();
	public static XSSFWorkbook woorkBook;
	public static File dataSheet;
	public static File dataSheetNew;
	public static FileOutputStream outputStream;
    public static String CaseOwnerQueue;
    
	// All the fields on case entry page for business user are verified
	public void caseFieldVerification(String user, String profile) throws Throwable
	{
		Step="Verify case fields";
		missingElements = new ArrayList<String>();

		String[] caseFields = { "Status", "Siemens Responsible Contact", "Reported on Behalf of", "Equipment",
				"PS Region", "Product Line", "Account Name", "Contact Name", "Send Notification", "Opportunity",
				"Quotation #", "Customer PO #", "Parent Case", "Customer Impact", "Customer Urgency", "Priority",
				"Case Origin", "NPS Score", "Survey ID", "Warranty Work", "Legal Assistance", "Issue Category",
				"Case Issue Type", "Component Part Number", "Subject", "Description" };
		
		// Iterate over each field element
		for (int i = 0; i < caseFields.length; i++)
		{
			// Get and save all text fields in list of case entry page
			List<WebElement> pageText = BaseTest.driver.findElements(By.xpath("//td[@id='bodyCell']"));

			// Iterate over all text fields present in case entry page
			for (int j = 0; j < pageText.size(); j++)
			{
				String text = pageText.get(j).getText();

				// Add missing fields of case entry page in arraylist
				if (!text.contains(caseFields[i]))
				{
					missingElements.add("Missing Element: " + caseFields[i]);
				}	
				
			}
		}

	}
	
	public static void caseRoutingDataManagement(String path) throws InvalidFormatException, IOException
	{
		//dataSheet= new File("D:\\Automation\\Projects\\CSM\\Resources\\Copy of Triage Routing Matrix - 28 Aug 18 Part 2 Only to IT_12th sep_Full_QA3.xlsx");
		dataSheet= new File(path);
		woorkBook= new XSSFWorkbook(dataSheet);
        sheetName=woorkBook.getSheet("Issue Routing P2");
        totalRowCount= sheetName.getLastRowNum();
        System.out.println(totalRowCount);
        
//        for(int i=1; i<=CaseEntryPage.totalRowCount; i++)
// 		{
//        
//        category = sheetName.getRow(i).getCell(5).getStringCellValue();
//        caseissue =sheetName.getRow(i).getCell(6).getStringCellValue();
//        equipment =sheetName.getRow(i).getCell(11).getStringCellValue();
//        queue =sheetName.getRow(i).getCell(10).getStringCellValue();
//        System.out.println(category+caseissue+equipment+queue);
//        //break;
// 		}
	}
	
	public  void selectEquipment(String equipmentNumber) throws InterruptedException
    {
		//select equipment//
			BaseTest.driver.findElement(By.xpath(".//*[@id='CF00N0000000731aA_lkwgt']/img")).click();
			
			// It will return the parent window name as a String
			String parent=BaseTest.driver.getWindowHandle();
			 
			// This will return the number of windows opened by Webdriver and will return Set of Strings
			Set<String>s1=BaseTest.driver.getWindowHandles();
			 
			// Now we will iterate using Iterator
			Iterator<String> I1= s1.iterator();
			 
			while(I1.hasNext())
			{
			 
			   String child_window=I1.next();
			 
			// Here we will compare if parent window is not equal to child window then we will close
			 
			if(!parent.equals(child_window))
			{
				BaseTest.driver.switchTo().window(child_window);
			 
			System.out.println(BaseTest.driver.switchTo().window(child_window).getTitle());
			
			BaseTest.driver.switchTo().frame("searchFrame");
			BaseTest.driver.findElement(By.xpath(".//*[@id='lksrch']")).sendKeys(equipmentNumber);
			BaseTest.driver.findElement(By.name("go")).click();
			BaseTest.driver.switchTo().defaultContent();
			
		    BaseTest.driver.switchTo().frame("resultsFrame");
			
			
			Thread.sleep(2000);
			BaseTest.driver.findElement(By.xpath("html/body/div[1]/div[3]/div/div/div[2]/div/div[2]/table/tbody/tr[2]/th/a")).click();
			
			}
			 
			}
			// once all pop up closed now switch to parent window
			BaseTest.driver.switchTo().window(parent);
			
			//Thread.sleep(3000);
    }
			
            //Issue Category de selected value//
			public  void deselectMultiPickListValues(String locaterkey) throws InterruptedException
			{
	 		    //WebElement Isscat1R = BaseTest.driver.findElement(By.id("00N1H00000B8c17_selected"));
				
				WebElement elm = getElement(locaterkey);	 		   
	 		    Select dropdown1R= new Select(elm);
	 		  
	 		    List<WebElement>  Isscatopt1R = dropdown1R.getOptions();
 		       doclick= new Actions(BaseTest.driver);
 		      
 			 for(; Isscatopt1R.size()>=0 ;) 
 			 {
 				System.out.println("Values DeSelected " +Isscatopt1R.get(0).getText());
 				doclick.doubleClick(Isscatopt1R.get(0)).build().perform();
 				Isscatopt1R = dropdown1R.getOptions();
 			    
 			if(Isscatopt1R.size()==0) {
 				break;
 			}
 				
 		}
			
    }
			//Issue Category selected value //
			public  void selectIssueCategoryValue(String locaterkey, String caseCategory, String button) throws InterruptedException
			{	
				WebElement elm = getElement(locaterkey);	 		   
	 		    Select dropdown1R= new Select(elm);
	 		   dropdown1R.selectByVisibleText(caseCategory);
	 		   click(button);
 		}
			
			// Select Multipicklist Value //
			public  void selectCaseIssueValue(String locaterkey, String button, String value) throws InterruptedException
			{	
				WebElement elm = getElement(locaterkey);	 		   
	 		    Select dropdown1R= new Select(elm);
	 		   dropdown1R.selectByVisibleText(value);
	 		   click(button);
 		}
			
    
			public void selectCaseIssueType(String issueCategory) throws InterruptedException
			{	
				   switch(issueCategory)
				   {
				     case "Parts":
				    	 selectCaseIssueValue("IssueTypesel_ID", "IssueTypRightClick_ID", "Parts - Availability");
				     break;

				     case "Field Service (FS)":
				    	 selectCaseIssueValue("IssueTypesel_ID", "IssueTypRightClick_ID", "FS - Pricing");
				     break;

				     case "Repair & Overhaul (R&O)":
				    	 selectCaseIssueValue("IssueTypesel_ID", "IssueTypRightClick_ID", "R&O - Other");
				     break;
				    
				     case "Mods & Ups":
				    	 selectCaseIssueValue("IssueTypesel_ID", "IssueTypRightClick_ID", "Mods & Ups - Reliability");
				     break;

				     case "LTP":
				    	 selectCaseIssueValue("IssueTypesel_ID", "IssueTypRightClick_ID", "LTP");
				     break;
				     
				     default:
				     System.out.println("Not in the list");
				     break;
				 }
				}
			
			public static void saveDetailsInWorkbook(int index, String caseQueue, String path) throws InterruptedException, IOException
			{
				
			//save details in excel //	
			BaseTest.driver.switchTo().frame("0661H0000021wok");	
			sheetName.getRow(index).createCell(12).setCellValue(getText("CaseOwner_XPATH"));
			CaseOwnerQueue=getText("CaseOwner_XPATH");
			BaseTest.driver.switchTo().defaultContent();
			
			sheetName.getRow(index).createCell(13).setCellValue(getText("PSRegion_XPATH"));
			sheetName.getRow(index).createCell(14).setCellValue(getText("ProductLine_XPATH"));
			sheetName.getRow(index).createCell(15).setCellValue(getText("CaseNumber_XPATH"));
			
			if(CaseOwnerQueue.equals(caseQueue))
			{
				System.out.println(caseQueue);
				sheetName.getRow(index).createCell(16).setCellValue("Pass");
			}
			else
			{
				sheetName.getRow(index).createCell(16).setCellValue("Fail");
			}
			
			//dataSheetNew= new File("D:\\Automation\\Projects\\CSM\\Resources\\Results\\Copy of Triage Routing Matrix - 28 Aug 18 Part 2 Only to IT_12th sep_Full_QA3.xlsx");
			dataSheetNew= new File(path);
			outputStream = new FileOutputStream(dataSheetNew);
			woorkBook.write(outputStream);
			
 		}
 		
 		    }
			

			
			
			
	
	
	
