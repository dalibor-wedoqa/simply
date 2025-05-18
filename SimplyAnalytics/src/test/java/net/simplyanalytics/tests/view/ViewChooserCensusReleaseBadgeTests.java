package net.simplyanalytics.tests.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.ConcurrentParameterizedTestRunner;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.HistoricalYearEnum;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.windows.HistoricalYearWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

@RunWith(ConcurrentParameterizedTestRunner.class)
public class ViewChooserCensusReleaseBadgeTests extends TestBase {
  
  ProjectSettingsPage projectSettingsPage;
  ComparisonReportPage comparisonReportPage;
  DataByDataFolderPanel dataByDataFolderPanel;
  
  private HistoricalYearEnum yearEnum;
  
  /**
 * .
 * @return
 */
 @Parameters(name = "{index}: year {0}")
 public static List<HistoricalYearEnum[]> data() {
   List<HistoricalYearEnum[]> list = new ArrayList<>();
   for (HistoricalYearEnum year : HistoricalYearEnum.values()) {
       list.add(new HistoricalYearEnum[] { year });
   }
   return list;
 }
  
 public ViewChooserCensusReleaseBadgeTests(HistoricalYearEnum year) {
   this.yearEnum = year;
 }
  
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
  }
  
  @Test
  public void testViewChooserCensusReleaseBadge() {
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    HistoricalYearWindow historicalYearWindow = newViewPage.getActiveView().clickHistoricalYearLink();
    historicalYearWindow.selectYear(yearEnum);
    if(yearEnum.getName().contains("2000–2009")){
        newViewPage = historicalYearWindow.clickOKButton();
        EditComparisonReportPage editComparisonReportPage = (EditComparisonReportPage) newViewPage.getActiveView().clickCreate(ViewType.COMPARISON_REPORT);

        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editComparisonReportPage.getLdbSection()
                .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
        DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(Dataset.PRIZM_US_RETIRED_2017);
        dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
        dataByDatasetDropdown.clickCloseVoid();
        comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();
    } else
    {
        newViewPage = historicalYearWindow.clickOKButton();
        EditComparisonReportPage editComparisonReportPage = (EditComparisonReportPage) newViewPage.getActiveView().clickCreate(ViewType.COMPARISON_REPORT);

        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) editComparisonReportPage.getLdbSection()
                .clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
        DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE);
        dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
        dataByDatasetDropdown.clickCloseVoid();
        comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();

    }

      String viewName = comparisonReportPage.getViewChooserSection().getActiveViewName();

    verificationStep("Verify that the correct view chooser census release badge appeared");
    Assert.assertEquals("The badge is not correct", yearEnum.getName(), comparisonReportPage.getViewChooserSection().getViewBadge(viewName));

  }

}
