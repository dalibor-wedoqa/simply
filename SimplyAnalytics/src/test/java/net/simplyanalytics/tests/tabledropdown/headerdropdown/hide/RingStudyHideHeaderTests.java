package net.simplyanalytics.tests.tabledropdown.headerdropdown.hide;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
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

public class RingStudyHideHeaderTests extends TestBase {

  private RingStudyPage ringStudyPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable houseIncome = medianDefaultDataVariable;

  /**
   * Signing in, creating new project and open the ring study page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(losAngeles);

    ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.RING_STUDY).clickDone();

  }

  @Test
  public void testHideRowHeaderDataVariable() {
    ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickHideDataVariable();

    verificationStep("Verify that the data variable disappeared");
    List<String> rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();
    Assert.assertFalse("The data variable is still present",
        rowHeaderValues.contains(houseIncome.getFullName()));
  }
}
