package net.simplyanalytics.tests.manageproject;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ModeIndicator;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.view.NewViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class HistoricalViewsTests extends TestBase {
  
  private ProjectSettingsPage projectSettingsPage;
  private EditScarboroughCrosstabPage editScarboroughCrosstabPage;

  
  @Before
  public void createProject() {
    driver.manage().window().maximize();
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
  @DisplayName("Verify the mode indicator is displayed correctly for Simmons Crosstab view for Historicals")
  @Description("The test creates a Simmons Crosstab view with sample data. Turns the Historical data on, and checks the mode indicator at the upper part of the page.")
  public void testModeIndicatorCrosstabView() {
    System.out.println("Clicking on General Settings button in Project Settings Header...");
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton()
            .getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    System.out.println("Creating a new Crosstab Table view...");
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();

    System.out.println("Checking historical view checkbox and creating Crosstab Table...");
    EditCrosstabPage editCrosstabPage = (EditCrosstabPage) newViewPage
            .getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);

    System.out.println("Clicking on Data in LDB section to select category data...");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage
            .getLdbSection().clickData().getBrowsePanel();

    System.out.println("Clicking on Education category data...");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.EDUCATION);

    System.out.println("Selecting 3 random data results from Education category...");
    for (int i = 0; i < 3; i++) {
      System.out.println("Clicking on random data result " + (i + 1));
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Clicking on Technology category data...");
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);

    System.out.println("Selecting 3 random data results from Technology category...");
    for (int i = 0; i < 3; i++) {
      System.out.println("Clicking on random data result " + (i + 1));
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Returning to Data section...");
    editCrosstabPage.getLdbSection().clickData();

    System.out.println("Clicking Done to complete the Crosstab...");
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();

    System.out.println("Verifying the mode indicator for the Crosstab...");
    verificationStep("Verify that the mode indicator is right");

    ModeIndicator modeIndicator = crosstabPage.getHeaderSection().getModeIndicator();
    System.out.println("Mode indicator: " + modeIndicator);
    System.out.println("Mode indicator variable: " + ModeIndicator.SIMMONS);

    Assert.assertEquals("Mode indicator is not the expected", ModeIndicator.SIMMONS, modeIndicator);
  }

  
  @Test
  @DisplayName("Verify the mode indicator is displayed correctly for Scarborough Crosstab view for Historicals")
  @Description("The test creates a Scarborough Crosstab view with sample data. Turns the Historical data on, and cecks the mode indicator at the upper part of the page.")
  @Tag("TCE/Fix")
  public void testModeIndicatorScarboroughCrosstabView() {
    System.out.println("Clicking on General Settings button in Project Settings Header...");
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton()
            .getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    System.out.println("Creating a new Scarborough Crosstab Table view...");
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage
            .getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);

    System.out.println("Selecting Albany location in Scarborough Crosstab Table...");
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage
            .getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    System.out.println("Clicking on Data in LDB section to select category data...");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage
            .getLdbSection().clickData().getBrowsePanel();

    System.out.println("Clicking on Housing category data...");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSING);

    System.out.println("Selecting 3 random data results from Housing category...");
    for (int i = 0; i < 3; i++) {
      System.out.println("Clicking on random data result " + (i + 1));
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Clicking on Health category data...");
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.HEALTH);

    System.out.println("Selecting 3 random data results from Health category...");
    for (int i = 0; i < 3; i++) {
      System.out.println("Clicking on random data result " + (i + 1));
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Returning to Data section...");
    editScarboroughCrosstabPage.getLdbSection().clickData();

    System.out.println("Clicking Done to complete the Scarborough Crosstab...");
    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();

    System.out.println("Verifying the mode indicator for the Scarborough Crosstab...");
    verificationStep("Verify that the mode indicator is right");

    ModeIndicator modeIndicator = scarboroughCrosstabPage.getHeaderSection().getModeIndicator();
    System.out.println("Mode indicator: " + modeIndicator);
    System.out.println("Mode indicator variable: " + ModeIndicator.SCARBOROUGH);

    Assert.assertEquals("Mode indicator is not the expected", ModeIndicator.SCARBOROUGH, modeIndicator);
  }

  
  //test case with default historical year
  @Test
  public void testHistoricalView2000() {
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    newViewPage.getActiveView().clickHistoricalViewsCheckbox();
    EditComparisonReportPage editComparisonReportPage = (EditComparisonReportPage) newViewPage.getActiveView().clickCreate(ViewType.COMPARISON_REPORT);
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editComparisonReportPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for(int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    editComparisonReportPage.getLdbSection().clickData();
    ComparisonReportPage comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();
    verificationStep("Verify that the mode indicator is right");
    Assert.assertEquals("Mode indicator is not the expected", ModeIndicator.HISTORICAL_DATA_2010, comparisonReportPage.getHeaderSection().getModeIndicator());
  }
  
//  @Test
//  public void testCrosstabDisabledIfHistoricalViewEnabled() {
//    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
//    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
//    //newViewPage.getActiveView().clickHistoricalViewsCheckbox();
//    NewViewPanel newViewPanel = newViewPage.getActiveView();
//
//    verificationStep("Verify that Simmons Crosstab panel disabled");
//    Assert.assertFalse("Simmons crosstab panel should be disabled", newViewPanel.isViewEnabled(ViewType.CROSSTAB_TABLE));
//
//    verificationStep("Verify that Scarborough Crosstab panel disabled");
//    Assert.assertFalse("Scarborough crosstab panel should be disabled", newViewPanel.isViewEnabled(ViewType.SCARBOROUGH_CROSSTAB_TABLE));
//  }

  @Test
  public void testCrosstabEnabledIfHistoricalViewDisabled() {
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    NewViewPanel newViewPanel = newViewPage.getActiveView();

    verificationStep("Verify that Simmons Crosstab panel enabled");
    Assert.assertTrue("Simmons crosstab panel should be enabled", newViewPanel.isViewEnabled(ViewType.CROSSTAB_TABLE));
}
  @Test
  public void testCrosstabScarboroughEnabledHistoricalViewDisabled() {
    //projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
    NewViewPanel newViewPanel = newViewPage.getActiveView();

    verificationStep("Verify that Scarborough Crosstab panel is enabled");
    Assert.assertTrue("Scarborough crosstab panel should be enabled", newViewPanel.isViewEnabled(ViewType.SCARBOROUGH_CROSSTAB_TABLE));
  }
}
