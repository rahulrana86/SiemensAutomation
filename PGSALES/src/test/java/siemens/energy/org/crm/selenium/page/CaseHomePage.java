package siemens.energy.org.crm.selenium.page;

import siemens.energy.org.crm.selenium.common.BasePage;

public class CaseHomePage extends BasePage
{
	// Select "New Case" button
	public void newCase()
	{
		Step="Select New Case Button";
		
		click("NewCaseButton_XPATH");
	}
}
