package siemens.energy.org.crm.selenium.page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import siemens.energy.org.crm.selenium.common.BasePage;
import siemens.energy.org.crm.selenium.common.BaseTest;
import siemens.energy.org.crm.selenium.test.ProbabilityCalculationTest;

public class LinkedOpportunityPage extends BasePage {

	public static String parent;
	public static Select viewDropDown;
	public static List<WebElement> apexRows;
	public static String statusResult1;
	public static WebElement resultColumns;
	public static String statusResult;
	public static WebElement apexTable;

	// Verify probability calculation for each opportunity
	public static void probabiltyValidation(String goRateValue, String getRateValue, String actualProbability,
			String opportunityName) {
		Step = "Verify record type value";

		double actualprobabilityValue= Integer.valueOf(actualProbability);
		
		//System.out.println("Actual Probal: " +actualRate);
		//int actualprobabilityValue = (int) Math.rint(actualRate);	
		
		double expected =(Double.parseDouble(goRateValue) * Double.parseDouble(getRateValue)) / 100;
		
		System.out.println("Expected Probal: " +expected);
		
		double expectedprobability= Math.rint(expected);
		
		//double  expectedprobability= (int)expectedprob;
		System.out.println("Expected Probability value: " + expectedprobability + "%");
		System.out.println("Actual Probability value: " + actualprobabilityValue + "%");

		if (expectedprobability == actualprobabilityValue) {

			System.out.println(
					"Result : Passed " + " Probability is matching for opportunity:  " + opportunityName + "\n");
			BaseTest.test.log(Status.PASS, "Probability is matching for opportunity: " + opportunityName + "\n" + "Expected Probability value: " + expectedprobability + "%" + " "+ "Actual Probability value: " + actualprobabilityValue + "%");
		} else {
			System.out.println(
					"Result : Failed " + " Probability is not matching for opportunity:  " + opportunityName + "\n");
			BaseTest.test.log(Status.FAIL, "Probability is not matching for opportunity: " + opportunityName + "\n" + "Expected Probability value: " + expectedprobability + "%" + " "+ "Actual Probability value: " + actualprobabilityValue + "%");
		}

	}

	// Verification over linked opportunities
	public static void linkedOpportunity(String userDetails) throws Throwable
	{
		ProbabilityCalculationTest.opportunityWindow = true;

		opportunityTable: for (int r = 0; r < ProbabilityCalculationTest.totalRows1; r++)
		{
			try {
				// Thread.sleep(4000);

				WebElement columns = ProbabilityCalculationTest.rows1.get(r).findElement(By.xpath("th"));

				// Select opportunity from table
				WebElement value = columns.findElement(By.tagName("a"));

				// Wait for page load//
				//waitForLoad(BaseTest.driver);

				String opportunityName = value.getText();

				System.out.println("Linked Opportunity Name: " + value.getText());

				ProbabilityCalculationTest.builder.keyDown(Keys.CONTROL).perform();
				value.click();
				ProbabilityCalculationTest.builder.keyUp(Keys.CONTROL).perform();

				// It will return the parent window name as a String
				parent = BaseTest.driver.getWindowHandle();

				// This will return the number of windows opened by Webdriver and will return
				// Set of St//rings
				Set<String> s1 = BaseTest.driver.getWindowHandles();

				// Now we will iterate using Iterator
				Iterator<String> I1 = s1.iterator();

				windows: while (I1.hasNext()) {

					String child_window = I1.next();

					// Here we will compare if parent window is not equal to child window then we

					if (!parent.equals(child_window)) {
						BaseTest.driver.switchTo().window(child_window);
						// Thread.sleep(10000);

						// Get Record Type//
						String recordType = getText("OppRecordType_XPATH");
						System.out.println("Opportunity Record Type: " + recordType);

						// System.out.println("User: " +userDetails);

						if ((recordType.equals("Process Solutions Sales Opportunity") || recordType.equals("SUS Opportunity"))
								&& userDetails.contains("PGEngineBusiness")) {
							System.out.println(
									"Process Solutions Sales Opportunity/SUS opportunities are not applicable for PG Engine Business Profile " + "\n");
							BaseTest.driver.close();
							BaseTest.test.log(Status.SKIP,
									"Process Solutions Sales Opportunity/SUS opportunities are not applicable for PG Engine Business Profile");

							// once all pop up closed now switch to parent window
							BaseTest.driver.switchTo().window(parent);
							continue opportunityTable;
						}

					
						// Edit Oportunity//
						
						//BasePage.click("EditGoRate_XPATH");

//						WebElement editGetRate=BasePage.getElement("EditGetRate_XPATH");
//						ProbabilityCalculationTest.js.executeScript("arguments[0].click();", editGetRate);
//
//						//Clear get rate text box 
//						BasePage.clear("GetRateTextBOX_XPATH");
//
//						// pass get rate value
//						BasePage.setText("GetRateTextBOX_XPATH", "45");
//
//						// click on Save button
//						BasePage.click("OppportunitySave_XPATH");
//						
						
						// Get Value in Get Rate Field//
						String getRateValue = getText("OppGetRate_XPATH");
						String projectgetRate = getRateValue.replace("%", "");

						// Get Probability Value//
						String probabilityValue1 = getText("OppProb_XPATH");
						String ProbabilityNew = probabilityValue1.replace("%", "");

						// Probability Validation method//
						probabiltyValidation(ProbabilityCalculationTest.projectGORate, projectgetRate, ProbabilityNew,
								opportunityName);

						BaseTest.driver.close();
					}
				}
				// once all pop up closed now switch to parent window
				BaseTest.driver.switchTo().window(parent);
				ProbabilityCalculationTest.opportunityWindow = false;
			}

			catch (Exception e)
			{
				BaseTest.driver.close();
				BaseTest.driver.switchTo().window(parent);
				ProbabilityCalculationTest.opportunityWindow = false;

			}
		}
	}

	public static void apexClassStatus(String Parent) throws Throwable
	{
		try {
			ProbabilityCalculationTest.apexWindow = true;

			Set<String> s11 = BaseTest.driver.getWindowHandles();

			// Now we will iterate using Iterator
			Iterator<String> I11 = s11.iterator();

			while (I11.hasNext()) {

				String child_window1 = I11.next();

				// Here we will compare if parent window is not equal to child window then we
				// will close

				if (!ProbabilityCalculationTest.parent1.equals(child_window1))
				{
					BaseTest.driver.switchTo().window(child_window1);

					BasePage.setText("ApexSearchBox_XPATH", "apex jobs");

					click("SelectApexLink_XPATH");

					Thread.sleep(20000);

					BaseTest.driver.switchTo().frame(0);

					// Select view from Dropdown//
					viewDropDown = new Select(
							BaseTest.driver.findElement(By.xpath("//*[@id=\"thePage:theTemplate:j_id18:fcf\"]")));
					viewDropDown.selectByVisibleText("AutomationTestView");

					Thread.sleep(4000);
					try
					{
						// Getting Apex job table details
						apexTable = BasePage.getElement("ApexTable1_XPATH");
					} catch (Exception e) {
						// Getting Apex job table details
						apexTable = BasePage.getElement("ApexTable2_XPATH");

					}

					apexRows = apexTable.findElements(By.tagName("tr"));

					int apexjobCount = LinkedOpportunityPage.classCount(ProbabilityCalculationTest.totalRows);
					System.out.println("Total no. of Apex classes: " + apexjobCount);

					for (int a = 0; a < apexjobCount; a++)
					{
						
						// Status//
						resultColumns = apexRows.get(a).findElement(By.xpath("//td[3]"));
						statusResult = resultColumns.getText();
						
						
						if (statusResult.equalsIgnoreCase("Completed"))
						{
							System.out.println("Status is completed");
							BaseTest.test.log(Status.PASS, "Apex job is Completed");

						} 
						else if (statusResult.equalsIgnoreCase("Processing")
								|| statusResult.equalsIgnoreCase("Queued"))
						{

							do 
							{
								Select viewDropDown1 = new Select(BaseTest.driver
										.findElement(By.xpath("//*[@id=\"thePage:theTemplate:j_id18:fcf\"]")));

							
								viewDropDown1.selectByIndex(1);
								
								Select viewDropDown2 = new Select(BaseTest.driver
										.findElement(By.xpath("//*[@id=\"thePage:theTemplate:j_id18:fcf\"]")));
								
								viewDropDown2.selectByVisibleText("AutomationTestView");
								

								try 
								{
									
									// Getting Apex job table details
									apexTable = BasePage.getElement("ApexTable1_XPATH");
																	}

								catch (Exception e)
								{
									
									// Getting Apex job table details
									apexTable = BasePage.getElement("ApexTable2_XPATH");
									
								}
								apexRows = apexTable.findElements(By.tagName("tr"));
								
								resultColumns = apexRows.get(a).findElement(By.xpath("//td[3]"));
								System.out.println("New Status: " + statusResult);
							}

							while (!statusResult.equalsIgnoreCase("Completed"));
							System.out.println("Latest Status: " + statusResult);
						
						}

					}
					BaseTest.driver.close();
				}
				}
		
			// once all pop up closed now switch to parent window
			BaseTest.driver.switchTo().window(ProbabilityCalculationTest.parent1);
			ProbabilityCalculationTest.apexWindow = false;
			System.out.println("\n");
		}

		catch (Exception e)
		
		{
			System.out.println("Failure 00 try");
			BaseTest.driver.close();
			BaseTest.driver.switchTo().window(ProbabilityCalculationTest.parent1);
			ProbabilityCalculationTest.apexWindow = true;
			System.out.println("\n");
		}

	}


	public static int classCount(int rowCount) {
		int oppCount = rowCount;
		int finalRowCount;
		double oppCounts = oppCount;
		double appexRowCount = oppCounts / 10;

		BigDecimal bd = new BigDecimal((appexRowCount - Math.floor(appexRowCount)) * 100);
		bd = bd.setScale(4, RoundingMode.HALF_DOWN);

		int gg = bd.intValue();
		String s = String.valueOf(gg);

		if (!(s.equals("0"))) {
			int appexRows = (int) appexRowCount;
			finalRowCount = appexRows + 1;
			return finalRowCount;
		} else {
			finalRowCount = (int) appexRowCount;
			return finalRowCount;
		}
	}
	
	public static void probabilityRate(String loginUser) throws Throwable
	{	
		   switch(loginUser)
		   {
		     case "TestDataDoNotUse_PGDRStandard":
		    	 BasePage.setText("GoRateTextBox_XPATH", "50");
		     break;

		     case "TestDataDoNotUse_PGEngineBusiness":
		    	 BasePage.setText("GoRateTextBox_XPATH", "70");
		     break;

		     case "TestDataDoNotUse_PGSStandard":
		    	 BasePage.setText("GoRateTextBox_XPATH", "15");
		     break;
		    
		     default:
		     System.out.println("Not in the list");
		     break;
		 }
		}

	
	
}
