package net.simplyanalytics.tests.view.map.legend;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.TestBrowser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.map.LegendHeaderMenuPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LegendHeaderMenuPanelTests extends TestBase {

  LegendPanel legendPanel;

  /**
   * Sign-in and creating a new project. Adding random businesses.
   */
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    legendPanel = mapPage.getActiveView().getLegend();

    mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
  }

  @Test
  public void testHideMapCenter() {

    LegendHeaderMenuPanel legendHeaderMenuPanel = legendPanel.clickOnHeaderButton();

    legendHeaderMenuPanel.clickOnShowMapCenterCheckBox();

    legendPanel.clickOnHeaderButton();
    
    verificationStep("Verify that the map center is disappeared");
    Assert.assertTrue(
        "Map center should not be displayed but result is " + legendPanel.mapCenterIsDisappeared(),
        legendPanel.mapCenterIsDisappeared());
  }

  @Test
  public void testHideTitle() {

    LegendHeaderMenuPanel legendHeaderMenuPanel = legendPanel.clickOnHeaderButton();

    legendHeaderMenuPanel.clickOnShowTitleCheckBox();

    legendPanel.clickOnHeaderButton();
    
    verificationStep("Verify that the title is disappeared");
    Assert.assertTrue(
        "Title should not be displayed but result is " + legendPanel.mapTitleIsDisappeared(),
        legendPanel.mapTitleIsDisappeared());
  }

  @Test
  public void testHideBusinesses() {
    LegendHeaderMenuPanel legendHeaderMenuPanel = legendPanel.clickOnHeaderButton();

    legendHeaderMenuPanel.clickOnShowBusinessesCheckBox();

    legendPanel.clickOnHeaderButton();

    verificationStep("Verify that the businesses are disappeared");
    Assert.assertTrue("Businesses should not be displayed but result is "
        + legendPanel.mapBusinessesIsDisappeared(), legendPanel.mapBusinessesIsDisappeared());
  }

  @Test
  public void testHideColorRanges() {

    LegendHeaderMenuPanel legendHeaderMenuPanel = legendPanel.clickOnHeaderButton();

    legendHeaderMenuPanel.clickOnShowColorRangesCheckBox();

    legendPanel.clickOnHeaderButton();

    verificationStep("Verify that the color rangers are disappeared");
    Assert.assertTrue("Color ranges should not be displayed but result is "
        + legendPanel.mapColorRangesIsDisappeared(), legendPanel.mapColorRangesIsDisappeared());
  }

  @Test
  public void testThemeChange() {

    LegendHeaderMenuPanel legendHeaderMenuPanel = legendPanel.clickOnHeaderButton();

    legendHeaderMenuPanel.clickOnLightTheme();

    legendPanel.clickOnHeaderButton();

    verificationStep("Verify that the color theme is changed");
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    {
    	Assert.assertTrue("The theme color is not light", legendPanel.isLightTheme());
    }else {
    	Assert.assertTrue(
    	        "The Light theme should be activated but the background is: "
    	            + legendPanel.getLegendBackgroundAfterChangingTheme(),
    	        legendPanel.getLegendBackgroundAfterChangingTheme().contains("rgba(255, 255, 255,"));	
    }
    
    legendHeaderMenuPanel = legendPanel.clickOnHeaderButton();
    
    legendHeaderMenuPanel.clickOnDarkTheme();
    
    legendPanel.clickOnHeaderButton();
    
    verificationStep("Verify that the color theme is changed to Dark theme");
    Assert.assertTrue(
        "The Dark theme should be activated but the background is: "
            + legendPanel.getLegendBackgroundAfterChangingTheme(),
        legendPanel.isDarkTheme());
    
  }

}
