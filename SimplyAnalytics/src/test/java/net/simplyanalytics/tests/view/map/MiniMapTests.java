package net.simplyanalytics.tests.view.map;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MinimapPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MiniMapTests extends TestBase {

  MapViewPanel mapViewPanel;

  /**
   * Signing in, creating new project and open the related data table page.
   */
  @Before
  public void login() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    mapViewPanel = mapPage.getActiveView();
  }
//Orignial test
/*
  @Test
  public void testMinimapAppearance() {
    MinimapPanel minimapPanel = mapViewPanel.getMinimapPanel();

    verificationStep("Verify that the minimap is displayed");
    Assert.assertTrue("The minimap should be present", minimapPanel.isMapPresent());
    Assert.assertFalse("The hide minimap button should be present",
        minimapPanel.isShowButtonPresent());
    
    minimapPanel.clickOnTheMiniMapButton();

    verificationStep("Verify that the minimap is not displayed");
    Assert.assertFalse("The minimap should not be present", minimapPanel.isMapPresent());
    Assert.assertTrue("The hide minimap button should be present",
        minimapPanel.isShowButtonPresent());

    minimapPanel.clickShowHideButton();

    verificationStep("Verify that the minimap is displayed");
    Assert.assertTrue("The minimap should be present", minimapPanel.isMapPresent());
    Assert.assertFalse("The hide minimap button should be present",
        minimapPanel.isShowButtonPresent());

  }
  */

  @Test
  public void testMinimapAppearance() throws InterruptedException {

    MinimapPanel minimapPanel = mapViewPanel.getMinimapPanel();

    // Click to hide the minimap
    minimapPanel.clickOnTheMiniMapButton();
    Thread.sleep(1000);

    // Click to hide the minimap
    minimapPanel.clickOnTheMiniMapButton();
    Thread.sleep(1000);

    // Verify that the minimap is displayed
    verificationStep("Verify that the minimap is displayed");
    Assert.assertTrue("The minimap should be present",
            minimapPanel.isMinimapPresent());
    Assert.assertTrue("The minimap should be present",
            minimapPanel.isMapPresent());

    // Click to hide the minimap
    minimapPanel.clickOnTheMiniMapButton();

    verificationStep("Verify that the minimap is not displayed");
    // Verify that the minimap is not displayed
    Assert.assertTrue("The element should be present",
            minimapPanel.isCollapsedMinimapPresent());
//    Assert.assertFalse("The minimap should not be present",
//            minimapPanel.isMinimapPresent());
//    Assert.assertFalse("The minimap should not be present",
//            minimapPanel.isMapPresent());


    // Click to show the minimap again
    minimapPanel.clickOnTheMiniMapButton();

    verificationStep("Verify that the minimap is displayed");
    // Verify that the minimap is displayed
    Assert.assertTrue("The minimap should be present",
            minimapPanel.isMinimapPresent());
    Assert.assertTrue("The minimap should be present",
            minimapPanel.isMapPresent());

  }
}