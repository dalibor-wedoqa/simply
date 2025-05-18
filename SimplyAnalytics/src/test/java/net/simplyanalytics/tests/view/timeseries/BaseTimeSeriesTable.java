package net.simplyanalytics.tests.view.timeseries;

import java.util.List;

import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesToolbar;
import net.simplyanalytics.pageobjects.sections.view.TimeSeriesViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import static net.simplyanalytics.enums.ViewType.TIME_SERIES;

public class BaseTimeSeriesTable extends TestBase {
  private TimeSeriesPage timeSeriesPage;
  private TimeSeriesToolbar timeSeriesToolbar;
  private NewViewPage newViewPage;
  private final ViewType view = ViewType.TIME_SERIES;

  /**
   * Signing in, creating new project and open the comparison report page.
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

    timeSeriesPage = (TimeSeriesPage) mapPage
            .getViewChooserSection()
            .clickNewView()
            .getActiveView()
            .clickCreate(TIME_SERIES)
            .clickDone();

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
  public void testVerifyChanges() {
    TimeSeriesViewPanel timeSeriesViewPanel = timeSeriesPage.getActiveView();
    List<String> previousContent = timeSeriesViewPanel.getTableContent();

    timeSeriesViewPanel = (TimeSeriesViewPanel) timeSeriesToolbar.openDataFilterDropdown()
            .clickRandomFilter(TIME_SERIES);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
            !(previousContent.equals(timeSeriesViewPanel.getTableContent())));
    previousContent = timeSeriesViewPanel.getTableContent();

    timeSeriesViewPanel = (TimeSeriesViewPanel) timeSeriesToolbar.openDataFilterDropdown()
            .clickRandomFilter(TIME_SERIES);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
            !(previousContent.equals(timeSeriesViewPanel.getTableContent())));
    previousContent = timeSeriesViewPanel.getTableContent();
/*
//Estimate and project data are not provided for every data variable so it's excluded from the test
 */
//    timeSeriesViewPanel = (TimeSeriesViewPanel) timeSeriesToolbar.openProjectDataDropdown()
//            .clickRandomFilter(ViewType.TIME_SERIES);
//    verificationStep("Verify that the Table Content is changed");
//    Assert.assertTrue("The content is the same",
//            !(previousContent.equals(timeSeriesViewPanel.getTableContent())));
//    previousContent = timeSeriesViewPanel.getTableContent();

    timeSeriesViewPanel = (TimeSeriesViewPanel) timeSeriesToolbar.openLocationDropdown()
            .clickRandomFilter(ViewType.TIME_SERIES);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
            !(previousContent.equals(timeSeriesViewPanel.getTableContent())));
    previousContent = timeSeriesViewPanel.getTableContent();

    timeSeriesViewPanel = (TimeSeriesViewPanel) timeSeriesToolbar.openDataFilterDropdown()
            .clickRandomFilter(TIME_SERIES);
    verificationStep("Verify that the Table Content is changed");
    Assert.assertTrue("The content is the same",
            !(previousContent.equals(timeSeriesViewPanel.getTableContent())));
  }

}
