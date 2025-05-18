package net.simplyanalytics.tests.tabledropdown.headerdropdown.hide;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RelatedDataTableHideHeaderTests extends TestBase {

  private RelatedDataReportPage relatedDataTablePage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;

  /**
   * Signing in, creating new project and open the related data table page.
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
    relatedDataTablePage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.RELATED_DATA).clickDone();
  }

  @Test
  public void testHideColumnHeaderLocation() {
    relatedDataTablePage = (RelatedDataReportPage) relatedDataTablePage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickHideLocation();

    verificationStep("Verify that the location disappeared");
    List<String> columnHeaderValues = relatedDataTablePage.getActiveView().getNormalHeaderValues();
    Assert.assertFalse("The location is still present",
        columnHeaderValues.contains(losAngeles.getName()));
  }
}
