package net.simplyanalytics.tests.view.actions;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.TimeSeriesViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeSeriesViewActionTests extends TestBase {

  private TimeSeriesPage timeSeriesPage;
  private TimeSeriesToolbar timeSeriesToolbar;

  /**
   * Signing in, creating new project and open the comarison report page.
   */
  @Before
  public void login() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

    timeSeriesPage = (TimeSeriesPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.TIME_SERIES).clickDone();

    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDatasetDropDown datasetDropDown = dataByDataFolderPanel
            .clickDataset(Dataset.CLIMATE_DIVISIONAL_DATABASE);
    datasetDropDown.getDatasetNavigationPanel().openRandomFoldersACSurvey();
    datasetDropDown.getDatasetSearchResultPanel().
            clickOnRandomData();
    datasetDropDown.clickCloseButtonForDataPanel();

    timeSeriesToolbar = timeSeriesPage.getToolbar();
  }

  @Test
  public void testTransposeReport() {
    TimeSeriesViewPanel timeSeriesViewPanel = timeSeriesPage.getActiveView();

    List<String> prevColumnHeaders = timeSeriesViewPanel.getNormalHeaderValues();
    List<String> prevRowHeaders = timeSeriesViewPanel.getRowHeaderValues();

    TimeSeriesViewActionMenu viewActionsMenu = (TimeSeriesViewActionMenu) timeSeriesToolbar
        .clickViewActions();
    timeSeriesPage = viewActionsMenu.clickTransposeReport();
    timeSeriesViewPanel = timeSeriesPage.getActiveView();

    List<String> newColumnHeaders = timeSeriesViewPanel.getRowHeaderValues();
    List<String> newRowHeaders = timeSeriesViewPanel.getNormalHeaderValues();

    verificationStep("Verify that the location variables are now present as column headers");
    Assert.assertArrayEquals("All location variables should be present in column headers section",
        prevColumnHeaders.toArray(), newColumnHeaders.toArray());

    verificationStep("Verify that the data are now present as row headers");
    Assert.assertArrayEquals("All data should be present in row headers section",
        prevRowHeaders.toArray(), newRowHeaders.toArray());

    timeSeriesViewPanel = timeSeriesPage.getActiveView();
    timeSeriesToolbar = timeSeriesPage.getToolbar();
    viewActionsMenu = (TimeSeriesViewActionMenu) timeSeriesToolbar.clickViewActions();
    timeSeriesPage = viewActionsMenu.clickTransposeReport();
    timeSeriesViewPanel = timeSeriesPage.getActiveView();

    verificationStep("Verify that the Data variables are now present as row headers");
    Assert.assertArrayEquals("All data variables should be present in row headers section",
    		prevRowHeaders.toArray(), timeSeriesViewPanel.getRowHeaderValues().toArray());

    verificationStep("Verify that the Locations are now present as column headers");
    Assert.assertArrayEquals("All locations should be present in column headers section",
    		prevColumnHeaders.toArray(), timeSeriesViewPanel.getNormalHeaderValues().toArray());

    timeSeriesViewPanel = timeSeriesPage.getActiveView();
    timeSeriesToolbar = timeSeriesPage.getToolbar();
    viewActionsMenu = (TimeSeriesViewActionMenu) timeSeriesToolbar.clickViewActions();
    timeSeriesPage = viewActionsMenu.clickTransposeReport();
    timeSeriesViewPanel = timeSeriesPage.getActiveView();

    verificationStep("Verify that the Data variables are now present as row headers");
    Assert.assertArrayEquals("All data variables should be present in row headers section",
            prevRowHeaders.toArray(), timeSeriesViewPanel.getNormalHeaderValues().toArray());

    verificationStep("Verify that the Locations are now present as column headers");
    Assert.assertArrayEquals("All locations should be present in column headers section",
            prevColumnHeaders.toArray(), timeSeriesViewPanel.getRowHeaderValues().toArray());

    timeSeriesViewPanel = timeSeriesPage.getActiveView();
    timeSeriesToolbar = timeSeriesPage.getToolbar();
    viewActionsMenu = (TimeSeriesViewActionMenu) timeSeriesToolbar.clickViewActions();
    timeSeriesPage = viewActionsMenu.clickTransposeReport();
    timeSeriesViewPanel = timeSeriesPage.getActiveView();

    verificationStep("Verify that the Data variables are now present as row headers");
    Assert.assertArrayEquals("All data variables should be present in row headers section",
            prevRowHeaders.toArray(), timeSeriesViewPanel.getRowHeaderValues().toArray());

    verificationStep("Verify that the Locations are now present as column headers");
    Assert.assertArrayEquals("All locations should be present in column headers section",
            prevColumnHeaders.toArray(), timeSeriesViewPanel.getNormalHeaderValues().toArray());

  }

  @Test
  public void testTransposeReportAndChangeData() {
    TimeSeriesViewPanel timeSeriesViewPanel = timeSeriesPage.getActiveView();

    List<String> prevColumnHeaders = timeSeriesViewPanel.getNormalHeaderValues();
    timeSeriesViewPanel.getRowHeaderValues();
    
    TimeSeriesViewActionMenu viewActionsMenu = (TimeSeriesViewActionMenu) timeSeriesToolbar
        .clickViewActions();
    timeSeriesPage = viewActionsMenu.clickTransposeReport();
    timeSeriesViewPanel = timeSeriesPage.getActiveView();

    verificationStep("Verify that the Data variables are now present as column headers");
    Assert.assertArrayEquals("All data variables should be present in column headers section",
        prevColumnHeaders.toArray(), timeSeriesViewPanel.getRowHeaderValues().toArray());
    
    timeSeriesPage.getToolbar().openDataFilterDropdown().clickRandomFilter(ViewType.TIME_SERIES);
    
    verificationStep("Verify that time series table is not empty");
    Assert.assertFalse("time series table is empty",
        timeSeriesViewPanel.getRowHeaderValues().isEmpty());
    
    verificationStep("Verify that new Data variables are changed");
    Assert.assertFalse("Data variables does not changed",
        prevColumnHeaders.equals(timeSeriesViewPanel.getNormalHeaderValues()));
    
  }

}
