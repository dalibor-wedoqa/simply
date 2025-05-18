package net.simplyanalytics.tests.view.map;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.map.BusinessPointsPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class MapBusinessPointsTests extends TestBase {
  
  MapPage mapPage;
  
  /**
   * Sign-in and creating a new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.USA);
  }
  
  //@Disabled("Skipping test testBusinessPointPanel")
  public void testBusinessPointPanel() {
    mapPage.getLdbSection().clickBusinesses().enterBusinessSearch("apple");
    int randomPoint = new Random().nextInt(mapPage.getActiveView().getMapPointsCount());
    int businessesNumber = mapPage.getActiveView().getNumberOnMapPoint(randomPoint);
    BusinessPointsPanel businessPointsPanel = mapPage.getActiveView().clickOnMapPointByIndex(randomPoint);
    
    verificationStep("Verify that the correct business count is present in the panel");
    Assert.assertEquals("Businesses count is not correct", businessesNumber, businessPointsPanel.getBusinessesCount());
  }

  //@Disabled("Skipping test testAllBusinessesArePresent")
  public void testAllBusinessesArePresent() {
    mapPage.getLdbSection().clickBusinesses().enterBusinessSearch("apple");
    int randomPoint = new Random().nextInt(mapPage.getActiveView().getMapPointsCount());
    int businessesNumber = mapPage.getActiveView().getNumberOnMapPoint(randomPoint);
    BusinessPointsPanel businessPointsPanel = mapPage.getActiveView().clickOnMapPointByIndex(randomPoint);
    businessPointsPanel.clickBack();
    List<String> businessesList = businessPointsPanel.getAllBusinessesRow();
    
    verificationStep("Verify that the correct number od businesses is displayed");
    Assert.assertEquals("Number of businesses is not correct", businessesNumber, businessesList.size());
  }
}
