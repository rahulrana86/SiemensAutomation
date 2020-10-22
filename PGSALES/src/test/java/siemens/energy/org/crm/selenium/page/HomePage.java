package siemens.energy.org.crm.selenium.page;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import siemens.energy.org.crm.selenium.common.BasePage;
import siemens.energy.org.crm.selenium.common.BaseTest;

/*
 * This class is containing reusable method of Home Page      
 * 
 */
public class HomePage extends BasePage
{
	BaseTest objBaseTest = new BaseTest();
	private Actions action;

	// Click alltab(+)
	public void allTab()
	{
		Step="Select alltab "+"";
		
		// Select all tabs(+)
		if(!getElement("AllTab_XPATH").isDisplayed())
		{
			BaseTest.driver.navigate().refresh();
			click("AllTab_XPATH");
		}
		
			click("AllTab_XPATH");
	}

	// Logout from user profile
	public void logout()
	{
		Step="Logout";
		try
		{
			click("LogoutMenu_XPATH");
			click("LogoutLinkTest_LINK");
		}
		catch(Exception e)
		{
			click("PSCDLogout_XPATH");
			click("PSCDLogout_LINK");
		}
	}

	// Switch to classic view from other view
	public void switchToClassicView()
	{
		Step="Switch to classic view";
		
		// Get page url
		String currUrl = BaseTest.driver.getCurrentUrl();

		// Switch to classic view user is in "console" view
		if (currUrl.contains("console"))
		{
			click("SwitchToClassis_XPATH");
		}

		// Switch to classic view user is in "lightning" view
		else if (currUrl.contains("lightning"))
		{
			//click("LightCuIssClose_XPATH");
			click("LightLogouImg_XPATH");
			click("SwitchToClassicLight_LINK");
		
			}

		// Switch app view to "Sales" in classic view
		else
		{
			if (objBaseTest.isDebug)
				System.out.println("User is in classic view");
		}
	}
	
	
	// Switch to lightning view from other view
	public void switchTolightningView()
	{
		Step="Switch to classic view";
		
		// Get page url
		String currUrl = BaseTest.driver.getCurrentUrl();

		// Switch to classic view user is in "console" view
		if (currUrl.contains("console"))
		{
			click("SwitchToClassis_XPATH");
		}

		// Switch to classic view user is in "lightning" view
		else if (currUrl.contains("lightning"))
		{
			//click("LightCuIssClose_XPATH");
			click("LightLogouImg_XPATH");
			click("SwitchToClassicLight_LINK");
		
			}

		// Switch app view to "Sales" in classic view
		else
		{
			if (objBaseTest.isDebug)
				System.out.println("User is in classic view");
		}
	}
	
	// Restore view and logout from business user
	public void restoreViewAndLogout(String currentView) throws AWTException
	{
		WebElement userStoryElement=null;
		
		Step="Restore view";
		// Switch to lightning view and logout
		if (currentView.contains("lightning"))
		{
			click("SwitchToLightLink_LINK");
			
			try
			{
				Thread.sleep(5000);
				
				click("LightCuIssClose_XPATH");
			    userStoryElement= getElement("LogoutFromLightView_XPATH");
			    
			    Thread.sleep(5000);
			    
			    action =new Actions(objBaseTest.driver);
			    action.click(userStoryElement).sendKeys(Keys.ENTER).click().perform();
			}
			catch(Exception e)
			{
			   objBaseTest.driver.navigate().refresh();
			   action.click(userStoryElement).sendKeys(Keys.ENTER).click().perform();		    
			}
		}

		// Switch to console view and logout
		else if (currentView.contains("console"))
		{
			click("ViewArrow_ID");
			click("PsDgServiceLink_LINK");
			click("LogoutPsDgMenu_ID");
			click("LogoutPsDgLink_LINK");
		}

		// User is in classic view and logout
		else
		{
			logout();
		}
	}	
}
