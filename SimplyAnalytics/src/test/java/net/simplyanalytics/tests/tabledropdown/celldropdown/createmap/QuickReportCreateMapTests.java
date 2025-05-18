package net.simplyanalytics.tests.tabledropdown.celldropdown.createmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class QuickReportCreateMapTests extends TestBase {

  private MapPage mapPage;
  private QuickReportPage quickReportPage;
  private final List<String> defaultLocations = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY.getName(), Location.USA.getName()));
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow newProjectLocationWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = newProjectLocationWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  @Test
  public void testCreateMap() {
    final String cellName = DataVariable.TOTAL_POPULATION_2024.getFullName();
    final String expectedDataVariable = DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024.getFullName();
    final Location expectedLocation = losAngeles;

    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.QUICK_REPORT).clickDone();

    verificationStep("Verify that the expected locations are present in the quick report table");
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
        quickReportPage.getActiveView().getColumnHeaders());

    CellDropdownQuickReport cellDropdown = quickReportPage.getActiveView()
        .openCellDropdown(cellName, expectedLocation.getName());
    MapPage mapPage = cellDropdown.clickCreateMapButton();

    verificationStep("Verify that the selected data variable appears on the map view");
    DataVariable actualDataVariable = mapPage.getToolbar().getActiveDataVariable();
    Assert.assertEquals("The actual data variable is not the selected", expectedDataVariable,
        actualDataVariable.getFullName());

    verificationStep("Verify that the selected location appears on the map view");
    Location actualLocation = mapPage.getToolbar().getActiveLocation();
    Assert.assertEquals("The actual location is not the selected", expectedLocation,
        actualLocation);
  }

}
