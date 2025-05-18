package net.simplyanalytics.tests.ldb.location;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.RadiusUnits;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditQuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class RadiusLocationForDifferentViewsTests extends TestBase{
  
  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;
  private LocationsTab locationsTab;
  private BusinessesTab businessesTab;
  private final String customRadiusLocationName = "Radius Location";
  private String column = "City";
  private BusinessesPage businessesPage;
  private NewViewPage newViewPage;
  private MapPage mapPage;
  
  @Before
  public void createProject() {
    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
  }
  
  @Test
  public void testRadiusLocationAppearedInBusinessView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditBusinessesPage editBusinessesPage = (EditBusinessesPage) newViewPage.getActiveView()
    .clickCreate(ViewType.BUSINESSES);
    
    verificationStep("Verify that the \"" + EditViewWarning.BUSINESS_MISSING + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editBusinessesPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.BUSINESS_MISSING,
        editBusinessesPage.getActiveView().getErrorMessage());
    
    businessesTab = mapPage.getLdbSection().clickBusinesses();
    businessesTab.addRandomBusinesses();
    editBusinessesPage.getActiveView().getLocationsPanel().clickElement(customRadiusLocationName);
    
    businessesPage = (BusinessesPage) editBusinessesPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " active");
    Assert.assertEquals(customRadiusLocationName + " is not active", 
        businessesPage.getToolbar().getActiveLocation().getName(), customRadiusLocationName); 
  }
  
  @Test
  public void testRadiusLocationAppearedInRankingView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditRankingPage editRankingPage = (EditRankingPage) newViewPage.getActiveView()
    .clickCreate(ViewType.RANKING);
    
    editRankingPage.getActiveView().getLocationsPanel().clickElement(customRadiusLocationName);
    
    RankingPage rankingPage = (RankingPage) editRankingPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " active");
    Assert.assertEquals(customRadiusLocationName + " is not active", 
        rankingPage.getToolbar().getActiveLocation().getName(), customRadiusLocationName); 
  }
  
  @Test
  public void testRadiusLocationAppearedInComparisonView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditComparisonReportPage editComparisonReportPage = (EditComparisonReportPage) newViewPage.getActiveView()
    .clickCreate(ViewType.COMPARISON_REPORT);
    
    List<String> selectedElements = editComparisonReportPage.getActiveView().getLocationsPanel().getSelectedElements();
    logger.info("Verify that " + customRadiusLocationName + " checkbox is checked'");
    Assert.assertTrue(customRadiusLocationName + " checkbox is not checked", selectedElements.contains(customRadiusLocationName));
    
    ComparisonReportPage comperisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " is appeard in the table");
    Assert.assertTrue(customRadiusLocationName + " is not active", 
        comperisonReportPage.getActiveView().getLocations().contains(Location.RADIUS_LOCATION)); 
  }
  
  @Test
  public void testRadiusLocationAppearedInQuickReportView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) newViewPage.getActiveView()
    .clickCreate(ViewType.QUICK_REPORT);
    
    List<String> selectedElements = editQuickReportPage.getActiveView().getLocationsPanel().getSelectedElements();
    logger.info("Verify that " + customRadiusLocationName + " checkbox is checked'");
    Assert.assertTrue(customRadiusLocationName + " checkbox is not checked", selectedElements.contains(customRadiusLocationName));
    
    QuickReportPage quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " is appeard in the table");
    Assert.assertTrue(customRadiusLocationName + " is not active", 
        quickReportPage.getActiveView().getColumnHeaders().contains(customRadiusLocationName)); 
  }
  
  @Test
  public void testRadiusLocationAppearedInRingStudyView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView()
    .clickCreate(ViewType.RING_STUDY);
    
    editRingStudyPage.getActiveView().getLocationsPanel().clickElement(customRadiusLocationName);
    
    RingStudyPage ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " active");
    Assert.assertEquals(customRadiusLocationName + " is not active", 
        ringStudyPage.getToolbar().getActiveLocation().getName(), customRadiusLocationName); 
  }
  
  @Test
  public void testRadiusLocationAppearedInRelatedDataTableView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditRelatedDataReportPage editRelatedDataReportPage = (EditRelatedDataReportPage) newViewPage.getActiveView()
    .clickCreate(ViewType.RELATED_DATA);
    
    List<String> selectedElements = editRelatedDataReportPage.getActiveView().getLocationsPanel().getSelectedElements();
    logger.info("Verify that " + customRadiusLocationName + " checkbox is checked'");
    Assert.assertTrue(customRadiusLocationName + " checkbox is not checked", selectedElements.contains(customRadiusLocationName));
    
    RelatedDataReportPage relatedDataReportPage = (RelatedDataReportPage) editRelatedDataReportPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " is appeard in the table");
    Assert.assertTrue(customRadiusLocationName + " is not active", 
        relatedDataReportPage.getActiveView().getLocations().contains(Location.RADIUS_LOCATION)); 
  }
  
  @Test
  public void testRadiusLocationAppearedInTimeSeriesTableView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) newViewPage.getActiveView()
    .clickCreate(ViewType.TIME_SERIES);
    
    List<String> selectedElements = editTimeSeriesPage.getActiveView().getLocationsPanel().getSelectedElements();
    logger.info("Verify that " + customRadiusLocationName + " checkbox is checked'");
    Assert.assertTrue(customRadiusLocationName + " checkbox is not checked", selectedElements.contains(customRadiusLocationName));
    
    TimeSeriesPage timeSeriesPage = (TimeSeriesPage) editTimeSeriesPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " is appeard in the table");
    Assert.assertTrue(customRadiusLocationName + " is not active", 
        timeSeriesPage.getActiveView().getLocations().contains(Location.RADIUS_LOCATION)); 
  }
  
  @Test
  public void testRadiusLocationAppearedInBarChartView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView()
    .clickCreate(ViewType.BAR_CHART);
    
    List<String> selectedElements = editBarChartPage.getActiveView().getLocationsPanel().getSelectedElements();
    logger.info("Verify that " + customRadiusLocationName + " checkbox is checked'");
    Assert.assertTrue(customRadiusLocationName + " checkbox is not checked", selectedElements.contains(customRadiusLocationName));
    
    BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();
    
    barChartPage.isLoaded();
    verificationStep("Verify that " + customRadiusLocationName + " is appeard in the table");
    Assert.assertTrue(customRadiusLocationName + " is not active", 
        barChartPage.getActiveView().getLocations().contains(Location.RADIUS_LOCATION)); 
  }
  
  @Test
  public void testRadiusLocationAppearedInScatterPlotView() {
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.CHICAGO_IL_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the " + customRadiusLocationName + " appears in the custom location list");
    Assert.assertTrue(customRadiusLocationName + " Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
    
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) newViewPage.getActiveView()
    .clickCreate(ViewType.SCATTER_PLOT);
    
    editScatterPlotPage.getActiveView().getLocationsPanel().clickElement(customRadiusLocationName);
    
    ScatterPlotPage scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
    
    verificationStep("Verify that " + customRadiusLocationName + " active");
    Assert.assertEquals(customRadiusLocationName + " is not active", 
        scatterPlotPage.getToolbar().getActiveLocation().getName(), customRadiusLocationName); 
  }
  
  @Test
  public void testAddRadiusLocationToBusinessesView() {
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditBusinessesPage editBusinessesPage = (EditBusinessesPage) newViewPage.getActiveView()
    .clickCreate(ViewType.BUSINESSES);
    
    verificationStep("Verify that the \"" + EditViewWarning.BUSINESS_MISSING + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editBusinessesPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.BUSINESS_MISSING,
        editBusinessesPage.getActiveView().getErrorMessage());
    businessesTab = mapPage.getLdbSection().clickBusinesses();
    businessesTab.addRandomBusinesses();
    businessesPage = (BusinessesPage) editBusinessesPage.clickDone();
    verificationStep("Verify that Businesses Page is loaded");
    businessesPage.getActiveView().isLoaded();
    List<String> columnList = businessesPage.getActiveView().getCellValuesForSingleColumn(10, column);
    
    logger.info("Add radius location to Businesses view");
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that Businesses Page is loaded");
    businessesPage.getActiveView().isLoaded();
    List<String> newColumnList = businessesPage.getActiveView().getCellValuesForSingleColumn(10, column);
    
    if(newColumnList.size() == 0) {
      throw new AssertionError("Table is empty");
    }

    verificationStep("Verify that City column changed");
    Assert.assertNotEquals("Radius Location is not active", newColumnList, columnList);
  }
  
  @Test
  public void testAddRadiusLocationToTimeSeriesView() {
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) newViewPage.getActiveView()
    .clickCreate(ViewType.TIME_SERIES);
    
    TimeSeriesPage timeSeriesPage = (TimeSeriesPage) editTimeSeriesPage.clickDone();
    verificationStep("Verify that Time Series Page is loaded");
    timeSeriesPage.getActiveView().isLoaded();
    
    logger.info("Add radius location to Time Series view");
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that Time Series table is loaded");
    Assert.assertTrue("Table is not loaded", timeSeriesPage.getActiveView().isTableLoaded());
    
    List<String> radiusLocationColumn = timeSeriesPage.getActiveView().getCellValuesForSingleColumn(10, customRadiusLocationName);
    verificationStep("Verify that " + customRadiusLocationName + "is appeared in the table");
    Assert.assertFalse("Radius Location column is not active", radiusLocationColumn.isEmpty());
  }
  
}
