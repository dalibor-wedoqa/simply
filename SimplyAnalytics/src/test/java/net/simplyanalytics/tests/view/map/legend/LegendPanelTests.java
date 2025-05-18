package net.simplyanalytics.tests.view.map.legend;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.map.LegendMapCenterPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.RenameViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Point;

public class LegendPanelTests extends TestBase {

  String business;
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

    business = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
  }

  @Test
  public void mapCenterTest() {

    String mapCenter = legendPanel.getMapCenter();

    LegendMapCenterPanel legendMapCenterPanel = legendPanel.clickOnMapCenter();
    legendMapCenterPanel.selectLocationType(LocationType.STATE);
    legendPanel.clickOnMapCenter();

    verificationStep("Verify that the map center is changed");
    Assert.assertNotEquals("The map center should be changed", mapCenter,
        legendPanel.getMapCenter());

  }

  @Test
  public void renameBusiness() {

    String newName = "test";
    String prevBusiness = legendPanel.getActiveBusiness();

    RenameViewPanel renameViewPanel = legendPanel.clickOnLegendBusinessName();
    renameViewPanel.enterText(newName);

    verificationStep("Verify that the business name is changed");
    Assert.assertNotEquals("The bussines name should be changed", prevBusiness,
        legendPanel.getActiveBusiness());

    verificationStep("Verify that the bussines name is equal to " + newName);
    Assert.assertEquals("The bussines name should be changed", newName,
        legendPanel.getActiveBusiness());
  }

  @Test
  public void removeBusiness() {

    legendPanel.removeBusiness();
    verificationStep("Verify that business is disappeared");
    Assert.assertFalse("The business should be disappeared",
        legendPanel.isLegendBusinessDisplayed());

  }
  
  @Test
  public void legendMoveTest() {
    Point beforeMoving = legendPanel.getLegendLocation();
    legendPanel.moveLegend(-300, 300);
    verificationStep("Verify that the legend panel is moved");
    Assert.assertNotEquals("The legend panel should be moved", beforeMoving,
        legendPanel.getLegendLocation());
  }

}
