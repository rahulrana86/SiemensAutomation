package siemens.energy.org.crm.selenium.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import siemens.energy.org.crm.selenium.common.BasePage;
import siemens.energy.org.crm.selenium.common.BaseTest;

public class CaseRecordTypePage extends BasePage
{

	// Select case record type
	public void selectRecordType(String recordType, String rectypeName, String userName, String profileName)
	{
		Step="Select Record Type";
		
		// Verify record type drop down
		if (getElement("RecTypeDropDwn_XPATH").isDisplayed())
		{
			Select recordTypeDropdown = new Select(BaseTest.driver.findElement(By.id(recordType)));
			recordTypeDropdown.selectByVisibleText(rectypeName);
			click("SaveRT_XPATH");
		}
		else
		{
			System.out.println("Record type is missing for: " + userName + ", " + profileName);
		}
	}

	// Verify drop down value is present or missing
	public boolean dropDownValueVerification(String dropDownId, String value)
	{
		Step="Verify record type value";
		
		boolean dropDownValueVerification = false;

		WebElement recordTypeDropDownElement = getElement(dropDownId);
		Select recordTypeSelect = new Select(recordTypeDropDownElement);
		List<WebElement> recordTypeOptions = recordTypeSelect.getOptions();

		// Iterate over record type drop down
		for (int i = 0; i < recordTypeOptions.size(); i++)
		{
			String recordTypeName = recordTypeOptions.get(i).getText();

			// Verify expected required type present in record type drop down
			if (recordTypeName!=null && recordTypeName.contains(value))
			{
				dropDownValueVerification = true;
				break;
			}
			else
			{
				if (isDebug)
					System.out.println("Drop down value is missing");
				dropDownValueVerification = false;
			}
		}
		return dropDownValueVerification;
	}

}
