package net.simplyanalytics.tests.tabledropdown.celldropdown.appearance;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.sections.view.base.CellDropdownQuickReport;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuickReportAppearanceTests extends TestBase {

  private QuickReportPage quickReportPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;

  /**
   * Signing in, creating new project and open the quck report page.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(losAngeles);
    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.QUICK_REPORT).clickDone();
  }

  @Test
  public void testDropdownAppeareance() {
    DataVariable expectedDataVariable = DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024;
    Location expectedLocation = losAngeles;

    CellDropdownQuickReport cellDropdown = quickReportPage.getActiveView()
        .openCellDropdown(DataVariable.TOTAL_POPULATION_2024.getFullName(), expectedLocation.getName());

    verificationStep("Verify that the selected data variable appears on the dropdown");
    DataVariable actualDataVariable = DataVariable.getByFullName(cellDropdown.getDataVariable());
    Assert.assertEquals("The actual data variable is not the selected", expectedDataVariable,
        actualDataVariable);

    verificationStep("Verify that the selected location appears on the dropdown");
    Location actualLocation = Location.getByName(cellDropdown.getLocation());
    Assert.assertEquals("The actual location is not the selected", expectedLocation,
        actualLocation);

    cellDropdown.clickCloseIcon();
  }
}
