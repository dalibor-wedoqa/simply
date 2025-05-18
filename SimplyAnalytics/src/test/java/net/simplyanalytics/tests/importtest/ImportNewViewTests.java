package net.simplyanalytics.tests.importtest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.BusinessSearchBy;
import net.simplyanalytics.enums.BusinessSearchConditions;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.SearchConditionRow;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByImportedDataseDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.barchart.BarChartToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessColumnsPanel;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessViewActionMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesToolbar;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.sections.view.BusinessesViewPanel;
import net.simplyanalytics.pageobjects.sections.view.ComparisonReportViewPanel;
import net.simplyanalytics.pageobjects.sections.view.QuickReportViewPanel;
import net.simplyanalytics.pageobjects.sections.view.RankingViewPanel;
import net.simplyanalytics.pageobjects.sections.view.TimeSeriesViewPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MinimapPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

public class ImportNewViewTests extends TestBase {
  
  private RingStudyPage ringStudyPage;
  private QuickReportPage quickReportPage;
  private RankingPage rankingPage;
  private RelatedDataReportPage relatedDataReportPage;
  private ComparisonReportPage comparisonReportPage;
  private BusinessesPage businessesPage;
  private TimeSeriesPage timeSeriesPage;
  private TimeSeriesToolbar timeSeriesToolbar;
  private MapViewPanel mapViewPanel;
  private BarChartPage barChartPage;
  private BarChartToolbar barChartToolbar;
  private MapPage mapPage;
  
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    NewProjectLocationWindow newProjectLocationWindow = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickNewProject();
    mapPage = newProjectLocationWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) mapPage.getLdbSection().clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByImportedDataseDropdown dataByImportedDatasetDropdown = dataByDataFolderPanel.clickRandomImportedDataset();
    dataByImportedDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
    mapPage = (MapPage) dataByImportedDatasetDropdown.clickClose(Page.MAP_VIEW);
  }
  
  @Test
  public void newMap() {
    mapViewPanel = mapPage.getActiveView();
    MinimapPanel minimapPanel = mapViewPanel.getMinimapPanel();
    
    verificationStep("Verify that the minimap is displayed");
    Assert.assertTrue("The minimap should be present", minimapPanel.isMapPresent());
   // Assert.assertFalse("The hide minimap button should be present", minimapPanel.isShowButtonPresent());
  }
  
  @Test
  public void newComparationTable() {
    comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection()
        .clickView(ViewType.COMPARISON_REPORT.getDefaultName());
    comparisonReportPage.getToolbar();
    ComparisonReportViewPanel comparisonReportViewPanel = comparisonReportPage.getActiveView();
    
    verificationStep("Verify that USA is present as a column header in the table");
    Assert.assertTrue("USA didn't appear in table's column headers",
        comparisonReportViewPanel.getNormalHeaderValues().contains(Location.USA.getName()));
  }
  
  @Test
  public void newRankingTable() {
    rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
    RankingViewPanel rankingViewPanel = rankingPage.getActiveView();
    
    verificationStep("Verify that the Ranking Table is loaded");
    Assert.assertTrue("There are no datas present", rankingViewPanel.getDataVariables().size() > 0);
  }
  
  @Test
  public void newQuickReport() {
    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.QUICK_REPORT).clickDone();
    QuickReportViewPanel quickReportViewPanel = quickReportPage.getActiveView();
    
    verificationStep("Verify that USA is present as a column header in the table");
    Assert.assertTrue("USA didn't appear in table's column headers",
        quickReportViewPanel.getNormalHeaderValues().contains(Location.USA.getName()));
  }
  
  @Test
  public void newRingStudy() {
    mapPage.getLdbSection().getLocationsTab();
    new MapPage(driver).getLdbSection().clickLocationsEnterSearchAndChooseALocation("Chi", "Chicago, IL");
    new MapPage(driver).getLdbSection().clickLocationsEnterSearchAndChooseALocation("Mia", "Miami, FL");
    ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.RING_STUDY).clickDone();
  }
  
  @Test
  public void newBusinessTable() {
    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab.clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(BusinessSearchBy.NAICS);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    searchConditionRow.selectRandomBusinessCode();
    advancedBusinessSearchWindow.clickSearch();
    mapPage = new MapPage(getDriver());
    businessesPage = (BusinessesPage) mapPage.getViewChooserSection().clickView(ViewType.BUSINESSES.getDefaultName());
    BusinessViewActionMenu viewActionsMenu = businessesPage.getToolbar().clickViewActions();
    BusinessColumnsPanel businessColumnsPanel = viewActionsMenu.clickColumnButton();
    
    Map<String, Boolean> columnValues = businessColumnsPanel.getCheckboxValues();
    columnValues = businessColumnsPanel.getCheckboxValues();
    businessesPage.getHeaderSection().clickOnBlankSpace();
    
    BusinessesViewPanel businessesViewPanel = businessesPage.getActiveView();
    List<String> tableColumns = businessesViewPanel.getNormalHeaderValues();
    tableColumns.add("Company Name");
    
    verificationStep("Verify that the columns are displayed in the business table");
    Assert.assertTrue(
        "The columns should be present in the business table. \ntableColumns: " + tableColumns + " \ncolumnValues: "
            + columnValues,
        tableColumns.containsAll(columnValues.entrySet().stream().filter(entry -> entry.getValue())
            .map(entry -> entry.getKey()).collect(Collectors.toList())));
  }
  
  @Test
  public void newRelatedDataTable() {
    relatedDataReportPage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.RELATED_DATA).clickDone();
    relatedDataReportPage.getToolbar().clickDataVariable().clickSortByDatavariable(defaultDataVariables.get(0));
    List<List<String>> rows = relatedDataReportPage.getActiveView().getCellValues("Data Variable");
    relatedDataReportPage.getToolbar().changeLocation(Location.USA);
    List<List<String>> rows1 = relatedDataReportPage.getActiveView().getCellValues("Data Variable");
    relatedDataReportPage.getToolbar().clickDataVariable()
        .clickSortByDatavariable(defaultDataVariables.get(1));
    List<List<String>> rows2 = relatedDataReportPage.getActiveView().getCellValues("Data Variable");
    
    Assert.assertNotEquals("Location is not changed", rows1, rows);
    Assert.assertNotEquals("Data is not changed", rows1, rows2);
    
  }
  
  @Test
  public void newTimeSeriesTable() {
    timeSeriesPage = (TimeSeriesPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.TIME_SERIES).clickDone();
    timeSeriesToolbar = timeSeriesPage.getToolbar();
    TimeSeriesViewPanel timeSeriesViewPanel = timeSeriesPage.getActiveView();
    List<String> previousContent = timeSeriesViewPanel.getTableContent();
    
    timeSeriesViewPanel = (TimeSeriesViewPanel) timeSeriesToolbar.openDataFilterDropdown()
        .clickRandomFilter(ViewType.TIME_SERIES);
    sleep(2000);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same", !(previousContent.equals(timeSeriesViewPanel.getTableContent())));
    previousContent = timeSeriesViewPanel.getTableContent();
    
  }
  
  @Test
  public void newBarChart() {
    barChartPage = (BarChartPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.BAR_CHART).clickDone();
    barChartToolbar = barChartPage.getToolbar();
    BarChartViewPanel barChartViewPanel = barChartPage.getActiveView();
    List<String> previousContent = barChartViewPanel.getValueOfBars();
    
    barChartViewPanel = (BarChartViewPanel) barChartToolbar.openDataFilterDropdown()
        .clickRandomFilter(ViewType.BAR_CHART);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same", !(previousContent.equals(barChartViewPanel.getValueOfBars())));
  }
  
  @After
  public void after() {
    unlockUser();
  }
  
}
