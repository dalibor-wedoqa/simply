package net.simplyanalytics.tests.view.actions;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RingStudyToolbarTest extends TestBase {
  
  private RingStudyPage ringStudyPage;

  /**
   * Signing in, creating new project, and open the quick report page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    
    mapPage.getLdbSection().getLocationsTab();
    new MapPage(driver).getLdbSection().clickLocationsEnterSearchAndChooseALocation("Chi", "Chicago, IL");
    new MapPage(driver).getLdbSection().clickLocationsEnterSearchAndChooseALocation("Mia", "Miami, FL");
    ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.RING_STUDY).clickDone();
  }

  @Test
  public void testRingStudyToolbar() {
    
    List<List<String>> rows = ringStudyPage.getActiveView().getCellValues("1 mile radius");
    ringStudyPage.getToolbar().changeLocation(Location.CHICAGO_IL_CITY);
    List<List<String>> rows2 = ringStudyPage.getActiveView().getCellValues("1 mile radius");
    
    verificationStep("Verify that location changed at 1 mile radius column in the table");
    Assert.assertNotEquals("Location changed", rows2, rows);
  }
  
}
