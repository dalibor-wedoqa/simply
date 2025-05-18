package net.simplyanalytics.tests.view.edit.unsupportedvariables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.HistoricalYearEnum;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.GeneralSettingsPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.ScarboroughCheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.HistoricalYearWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class UnsupportedDataVariablesTests extends TestBase {

  private MapPage mapPage;
  private String[] dataSeedVariables = defaultDataVariables.stream()
	      .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()).toArray(new String[2]);
  
  /**
   * Signing in and creating new project.
   */
  @Before
  public void createProjectWithMapView() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }
  
  @Test
  public void testMapUnsupportedDataVariables() {
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    EditMapPage editMapPage = (EditMapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editMapPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editMapPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editMapPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editMapPage = (EditMapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP);
    disabledElements = editMapPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testRelatedDataReportUnsupportedDataVariables() {
    EditRelatedDataReportPage editRelatedDataReportPage = (EditRelatedDataReportPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.RELATED_DATA);
    ProjectSettingsPage projectSettingsPage = editRelatedDataReportPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editRelatedDataReportPage = (EditRelatedDataReportPage) newViewPage.getActiveView().clickCreate(ViewType.RELATED_DATA);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editRelatedDataReportPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editRelatedDataReportPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editRelatedDataReportPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editRelatedDataReportPage = (EditRelatedDataReportPage) newViewPage.getActiveView().clickCreate(ViewType.RELATED_DATA);
    disabledElements = editRelatedDataReportPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testTimeSeriesTablesUnsupportedDataVariables() {
    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.TIME_SERIES);
    ProjectSettingsPage projectSettingsPage = editTimeSeriesPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editTimeSeriesPage = (EditTimeSeriesPage) newViewPage.getActiveView().clickCreate(ViewType.TIME_SERIES);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editTimeSeriesPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editTimeSeriesPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editTimeSeriesPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editTimeSeriesPage = (EditTimeSeriesPage) newViewPage.getActiveView().clickCreate(ViewType.TIME_SERIES);
    disabledElements = editTimeSeriesPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testBarChartUnsupportedDataVariables() {
    EditBarChartPage editBarChartPage = (EditBarChartPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.BAR_CHART);
    ProjectSettingsPage projectSettingsPage = editBarChartPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(ViewType.BAR_CHART);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editBarChartPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editBarChartPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editBarChartPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(ViewType.BAR_CHART);
    disabledElements = editBarChartPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testComparisonUnsupportedDataVariables() {
    EditComparisonReportPage editComparisonPage = (EditComparisonReportPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.COMPARISON_REPORT);
    ProjectSettingsPage projectSettingsPage = editComparisonPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editComparisonPage = (EditComparisonReportPage) newViewPage.getActiveView().clickCreate(ViewType.COMPARISON_REPORT);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editComparisonPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel.clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE)
        .getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editComparisonPage.getActiveView().getDataPanel().getAllDisabledElements();

    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editComparisonPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editComparisonPage = (EditComparisonReportPage) newViewPage.getActiveView().clickCreate(ViewType.COMPARISON_REPORT);
    disabledElements = editComparisonPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }  
  
  @Test
  public void testRankingUnsupportedDataVariables() {
    EditRankingPage editRankingPage = (EditRankingPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.RANKING);
    ProjectSettingsPage projectSettingsPage = editRankingPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editRankingPage = (EditRankingPage) newViewPage.getActiveView().clickCreate(ViewType.RANKING);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editRankingPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editRankingPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editRankingPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editRankingPage = (EditRankingPage) newViewPage.getActiveView().clickCreate(ViewType.RANKING);
    disabledElements = editRankingPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testRingStudyUnsupportedDataVariables() {
    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.RING_STUDY);
    ProjectSettingsPage projectSettingsPage = editRingStudyPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(ViewType.RING_STUDY);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editRingStudyPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editRingStudyPage.getActiveView().getDataPanel().getAllDisabledElements();

    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editRingStudyPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(ViewType.RING_STUDY);
    disabledElements = editRingStudyPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testScatterPlotUnsupportedDataVariables() {
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.SCATTER_PLOT);
    ProjectSettingsPage projectSettingsPage = editScatterPlotPage.getHeaderSection().clickProjectSettings();
    GeneralSettingsPanel generalSettingsPanel = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
    generalSettingsPanel.getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(HistoricalYearEnum.HJ_1950);
    historicalYearWindow.clickOKButton();
    
    editScatterPlotPage = (EditScatterPlotPage) newViewPage.getActiveView().clickCreate(ViewType.SCATTER_PLOT);
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editScatterPlotPage.getLdbSection()
        .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    String historicalDataVariable = dataByDataFolderPanel
        .clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE).getDatasetSearchResultPanel().clickOnRandomData();
    
    List<String> disabledElements = editScatterPlotPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the data seed variables are disabled");
    Assert.assertArrayEquals("Data seed variables are not disabled", 
        dataSeedVariables, disabledElements.toArray());
    
    newViewPage = editScatterPlotPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    
    editScatterPlotPage = (EditScatterPlotPage) newViewPage.getActiveView().clickCreate(ViewType.SCATTER_PLOT);
    disabledElements = editScatterPlotPage.getActiveView().getDataPanel().getAllDisabledElements();
    
    verificationStep("Verify that the historical variable is disabled");
    Assert.assertEquals("Historical data variable is not disabled", historicalDataVariable, disabledElements.get(0).split(", 19")[0]);
  }
  
  @Test
  public void testScarboroughCrosstabUnsupportedDataVariables() {
    mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader()
            .clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView()
        .clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage
        .getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.CHICAGO);
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    List<String> dataVariables = new ArrayList<String>();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HEALTH);
    dataVariables.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    dataVariables.add(dataByCategoryDropwDown.clickOnARandomDataResult());

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
    
    ScarboroughCheckboxContainerPanel checkbowContainerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();
    
    verificationStep("Verify that message appeared about unsupported data variables");
    Assert.assertEquals("The message is not the expected", "These variables are not supported by this view type", 
        checkbowContainerPanel.getUnsupportedDataVariablesHeaderMessage());
    
    verificationStep("Verify that the unsupported data variables");
    Assert.assertArrayEquals("Data variables are not the expected", dataSeedVariables, 
        checkbowContainerPanel.getAllNotSupportedData().toArray());
  }
}
