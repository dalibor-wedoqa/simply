package net.simplyanalytics.tests.tabledropdown.celldropdown.appearance;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ProjectDataEnum;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.view.base.CellDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeSeriesTableAppearanceTests extends TestBase {

  private TimeSeriesPage timeSeriesPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable expectedDataVariable = defaultDataVariables.get(0);


  /**
   * Signing in, creating new project and open the time series page.
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
    timeSeriesPage = (TimeSeriesPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.TIME_SERIES).clickDone();
    timeSeriesPage.getToolbar().openProjectDataDropdown().clickOnFilter(ProjectDataEnum.INCLUDING_PROJECT_DATA.getName(), ViewType.TIME_SERIES);
  }

  @Test
  public void testDropdownAppeareance() {
    Location expectedLocation = losAngeles;

    CellDropdown cellDropdown = timeSeriesPage.getActiveView()
        .openCellDropdown(expectedDataVariable.getFullName(), expectedLocation.getName());

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
