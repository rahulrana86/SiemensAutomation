package siemens.energy.org.crm.selenium.page;

import org.openqa.selenium.WebElement;

import siemens.energy.org.crm.selenium.common.BasePage;

public class AllTabPage extends BasePage
{
	
	// Search user and login as business user
		public void selectTab(String tabPath) throws Throwable
		{
			Step="Select Tab";
			
			// Verify "People" tab in "All Tab" page
			if (getElement(tabPath).isDisplayed())
			{
				// Select people from all tabs(+)
				click(tabPath);
			}
			else
			{
				System.out.println("Tab is missing");
			}
		}

	// Search user and login as business user
	public void selectPeople() throws Throwable
	{
		Step="Select people tab";
		
		// Verify "People" tab in "All Tab" page
		if (getElement("AllTabPPLs_XPATH").isDisplayed())
		{
			Thread.holdsLock(2000);
			// Select people from all tabs(+)
			click("AllTabPPLs_XPATH");
		}
		else
		{
			System.out.println("People tab is missing");
		}
	}

	// Select case tab navigate to "New case" page
	public void selectCaseTab() throws Throwable
	{
		Step="Select case tab";
		
		// Verify "Cases" tab in "All Tab" page
		if (getElement("AllTabCases_XPATH").isDisplayed())
		{
			// Select "Cases" tab from all tabs(+)
			click("AllTabCases_XPATH");
		}
		else
		{
			System.out.println("Case Tab is missing");
		}
	}
}
