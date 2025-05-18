package net.simplyanalytics.tests.tabledropdown.celldropdown.createranking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ProjectDataEnum;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.view.base.CellDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeSeriesCreateRankingTests extends TestBase {
  private MapPage mapPage;
  private TimeSeriesPage timeSeriesPage;
  private RankingPage rankingPage;
  
  private final List<String> defaultLocations = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY.getName(), Location.USA.getName()));
  private DataVariable dataVariable = defaultDataVariables.get(0);
  
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
  public void testCreateRanking() {
    final Location expectedLocation = Location.LOS_ANGELES_CA_CITY;
    
    timeSeriesPage = (TimeSeriesPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.TIME_SERIES).clickDone();
    
    timeSeriesPage.getToolbar().openProjectDataDropdown().clickOnFilter(ProjectDataEnum.INCLUDING_PROJECT_DATA.getName(), ViewType.TIME_SERIES);
    
    verificationStep("Verify that the expected locations are present in the Time Series table");
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
        timeSeriesPage.getActiveView().getColumnHeaders());
    
    CellDropdown cellDropdown = timeSeriesPage.getActiveView()
        .openCellDropdown(dataVariable.getFullName(), expectedLocation.getName());
    rankingPage = cellDropdown.clickCreateRankingButton();
    rankingPage.getToolbar().clickDataVariable().clickSortByDatavariable(dataVariable);
    verificationStep("Verify that the selected data variable appears on the map view");
    DataVariable actualDataVariable = rankingPage.getToolbar().getActiveDataVariable();
    Assert.assertEquals("The actual data variable is not the selected", dataVariable.getFullName(),
        actualDataVariable.getFullName());
    
    verificationStep("Verify that the selected location appears on the map view");
    Location actualLocation = rankingPage.getToolbar().getActiveLocation();
    Assert.assertEquals("The actual location is not the selected", expectedLocation,
        actualLocation);
  }
  
}
