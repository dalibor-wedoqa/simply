package net.simplyanalytics.tests.view.quickreport;

import java.util.Map;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.view.base.CellDropdownQuickReport;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuickReportTableContentTests extends TestBase {
  private DataVariable expectedDataVariable = DataVariable.TOTAL_POPULATION_2024;
  private QuickReportPage quickReportPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable dataVariable = DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024;

  /**
   * Signing in, creating new project and open the quck report page.
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
    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.QUICK_REPORT).clickDone();
  }

  @Test
  public void createMapTest() {
    CellDropdownQuickReport cellDropdown = quickReportPage.getActiveView()
        .openCellDropdown(expectedDataVariable.getFullName(), losAngeles.getName());
    MapPage map2Page = cellDropdown.clickCreateMapButton();

    verificationStep("Verify that the new map view is created");
    Assert.assertEquals("The actual actual view is the new created view", "Map 2",
        map2Page.getViewChooserSection().getActiveViewName());

    verificationStep("Verify that the new map view legend shows data correctly");
    Assert.assertEquals("The actual actual view legend active data should be shown correctly",
        dataVariable, map2Page.getActiveView().getLegend().getActiveData());
  }

  @Test
  public void createRankingTest() {
    CellDropdownQuickReport cellDropdown = quickReportPage.getActiveView()
        .openCellDropdown(expectedDataVariable.getFullName(), losAngeles.getName());
    RankingPage rankingPage = cellDropdown.clickCreateRankingButton();

    verificationStep("Verify that the new map view is created");
    Assert.assertEquals("The actual actual view is the new created view", "Ranking 2",
        rankingPage.getViewChooserSection().getActiveViewName());
    verificationStep("Verify that the ranking table data are shown correctly");
    Assert.assertEquals("The view legend active data should be shown correctly",
        dataVariable, rankingPage.getActiveView().getDataVariables().get(0));
  }

  @Test
  public void createBarChartTest() {
    CellDropdownQuickReport cellDropdown = quickReportPage.getActiveView()
        .openCellDropdown(expectedDataVariable.getFullName(), losAngeles.getName());
    BarChartPage barChartPage = cellDropdown.clickCreateBarChartButton();

    verificationStep("Verify that the new map view is created");
    Assert.assertEquals("The actual actual view is the new created view", "Bar Chart",
        barChartPage.getViewChooserSection().getActiveViewName());

    verificationStep("Verify that the actual data variable is correct");
    Assert.assertEquals("The actual data variable should be shown correctly", dataVariable,
        barChartPage.getToolbar().getActiveDataVariable());
  }

  @Test
  public void testTableContentValues() {
    String populationLa = quickReportPage.getActiveView().getCellValue(expectedDataVariable,
        Location.LOS_ANGELES_CA_CITY.getName());
    String populationUsa = quickReportPage.getActiveView()
        .getCellValue(expectedDataVariable, Location.USA.getName());

    verificationStep("Verify that the table content is shown properly");
    Assert.assertTrue("Population of USA is not bigger than population os LA",
        Integer.parseInt(populationLa.replace(",", "")) < Integer
            .parseInt(populationUsa.replace(",", "")));
  }

  @Test
  public void testRow() {
    String populationLa = quickReportPage.getActiveView().getCellValue(expectedDataVariable,
        Location.LOS_ANGELES_CA_CITY.getName());
    Map<String, String> row = quickReportPage.getActiveView()
        .getRow(expectedDataVariable.getFullName());

    verificationStep("Verify that the table row is shown properly");
    Assert.assertEquals("The table row is not shown propperly", populationLa,
        row.get(Location.LOS_ANGELES_CA_CITY.getName()));
  }

  @Test
  public void testLocationType() {
    String locationType = quickReportPage.getActiveView()
        .getLocationType(Location.LOS_ANGELES_CA_CITY.getName());

    verificationStep("Verify that the location type is shown properly");
    Assert.assertEquals("The location type is not shown propperly",
        LocationType.CITY.getSingularName(), locationType);
    
    locationType = quickReportPage.getActiveView()
        .getLocationType(Location.USA.getName());

    verificationStep("Verify that the location type is shown properly");
    Assert.assertEquals("The location type is not shown propperly",
        LocationType.USA.getSingularName(), locationType);
  }
}
