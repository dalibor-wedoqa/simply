package net.simplyanalytics.tests.view.barchart;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.toolbar.barchart.BarChartToolbar;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.sections.view.RankingViewPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class BaseBarChart extends TestBase {
  
  private BarChartToolbar barChartToolbar;
  private BarChartViewPanel barChartViewPanel;
  private BarChartPage barChartPage;

  /**
   * Signing in, creating new project and open the comparison report page.
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
    barChartPage = (BarChartPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.BAR_CHART).clickDone();
    barChartToolbar = barChartPage.getToolbar();
    barChartViewPanel = barChartPage.getActiveView();
  }

  @Test
  public void testVerifyBarChanges() {
    List<String> previousContent = barChartViewPanel.getValueOfBars();

    barChartViewPanel = (BarChartViewPanel) barChartToolbar.openDataFilterDropdown()
        .clickRandomFilter(ViewType.BAR_CHART);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
        !(previousContent.equals(barChartViewPanel.getValueOfBars())));
    previousContent = barChartViewPanel.getValueOfBars();

    barChartViewPanel = (BarChartViewPanel) barChartToolbar.openDataFilterDropdown()
        .clickRandomFilter(ViewType.BAR_CHART);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
        !(previousContent.equals(barChartViewPanel.getValueOfBars())));
    previousContent = barChartViewPanel.getValueOfBars();

    barChartViewPanel = (BarChartViewPanel) barChartToolbar.openSortingMenu()
        .clickRandomFilter(ViewType.BAR_CHART);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
        !(previousContent.equals(barChartViewPanel.getValueOfBars())));
    previousContent = barChartViewPanel.getValueOfBars();
  }

  @Test
  public void testCreatRanking() {
    barChartViewPanel.clickBar().removeBar();
    barChartViewPanel = new BarChartViewPanel(driver);
    RankingViewPanel rankingViewPanel = barChartViewPanel.clickBar().createRanking();

    verificationStep("Verify that the Ranking Table is loaded");
    Assert.assertTrue("There are no datas present", rankingViewPanel.getDataVariables().size() > 0);

  }

  @Test
  public void testCreatMap() {
    barChartViewPanel.clickBar().removeBar();
    barChartViewPanel = new BarChartViewPanel(driver);
    MapViewPanel mapViewPanel = barChartViewPanel.clickBar().createMap();

    verificationStep("Verify that the Label on the map is present");
    Assert.assertTrue("Map labels are not present", !mapViewPanel.isMapLabelsPresent());

  }

  @Test
  public void testCompareBarHight() {
    int previousHight = barChartViewPanel.getBarHight(0);
    barChartViewPanel = (BarChartViewPanel) barChartToolbar.openDataFilterDropdown()
        .clickRandomFilter(ViewType.BAR_CHART);

    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same", previousHight != barChartViewPanel.getBarHight(0));
  }
  
  @Test
  public void testSortByValues() {
    barChartPage.getLdbSection().clickLocations().chooseLocation(Location.MIAMI_FL_CITY, ViewType.BAR_CHART);
    barChartPage.getLdbSection().clickLocations().chooseLocation(Location.NEW_YORK_STATE, ViewType.BAR_CHART);
    
    verificationStep("Verify that bar chart is sorted by Descending by default");
    Assert.assertEquals("Bar chart should be sorted by Descending", "Descending", barChartToolbar.getActiveSortedByOption());
    
    List<Double> barValues = barChartViewPanel.getDoubleValueOfBars();
    List<Double> expectedList = barChartViewPanel.getDoubleValueOfBars();
    Collections.sort(expectedList, Collections.reverseOrder());
    
    verificationStep("Verify that the values are in the right order");
    Assert.assertTrue("The list order is not the expected", expectedList.equals(barValues));
    
    barChartPage.getToolbar().openSortingMenu().clickSortByAscending();
    sleep(5000);
    
    verificationStep("Verify that bar chart is sorted by Ascending by default");
    Assert.assertEquals("Bar chart should be sorted by Ascending", "Ascending", barChartToolbar.getActiveSortedByOption());
    
    barValues = barChartViewPanel.getDoubleValueOfBars();
    expectedList = barChartViewPanel.getDoubleValueOfBars();
    Collections.sort(expectedList);
    
    verificationStep("Verify that the values are in the right order");
    Assert.assertTrue("The list order is not the expected", expectedList.equals(barValues));
  }

}
