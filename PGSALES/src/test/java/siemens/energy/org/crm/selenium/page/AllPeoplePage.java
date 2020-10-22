package siemens.energy.org.crm.selenium.page;

import org.openqa.selenium.WebElement;

import siemens.energy.org.crm.selenium.common.BasePage;

public class AllPeoplePage extends BasePage
{
	// Search user and login as business user
	public void loginAsBusinessUser(String user, String profile) throws Throwable
	{
		Step="Login as Business user";
		
		Thread.sleep(3000);
		
		// Search and select business user
		setText("PPLSearch_ID", user);

		if (getElement("SelectUser_XPATH").isDisplayed())
		{
			// Select business user
			click("ALLPeopleSelect_XPATH");

			// Login as business user
			click("LoginMenu_XPATH");
			click("UserLogin_XPATH");
			click("LoginBut_XPATH");			
		} 
		else
		{
			System.out.println("user is missing: " + user + ", " + profile);
		}
	}

}
