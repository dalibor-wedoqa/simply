package net.simplyanalytics.tests.ldb;

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
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditQuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class DisabledLDBTabTests extends TestBase {
  
  private MapPage mapPage;
  
  @Before
  public void before() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithStateAndDefaultVariables(Location.CHICAGO_IL_CITY);
  }
  
  @Test
  public void testComparisonDisabledTab() {
    EditComparisonReportPage editComparisonPage = (EditComparisonReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.COMPARISON_REPORT);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editComparisonPage.getLdbSection().isBusinessesTabEnabled());
    
    ComparisonReportPage comparisonReportPage = (ComparisonReportPage) editComparisonPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", comparisonReportPage.getLdbSection().isBusinessesTabEnabled());
  }
  
  @Test
  public void testRankingDisabledTab() {
    EditRankingPage editRankingPage = (EditRankingPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RANKING);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editRankingPage.getLdbSection().isBusinessesTabEnabled());
    
    RankingPage rankingPage = (RankingPage) editRankingPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", rankingPage.getLdbSection().isBusinessesTabEnabled());
  }
  
  @Test
  public void testQuickReportDisabledTab() {
    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.QUICK_REPORT);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editQuickReportPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Data tab is disabled");
    Assert.assertFalse("Data tab should be disabled", editQuickReportPage.getLdbSection().isDataTabEnabled());
    
    QuickReportPage quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", quickReportPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Data tab is disabled");
    Assert.assertFalse("Data tab should be disabled", quickReportPage.getLdbSection().isDataTabEnabled());
  }
  
  @Test
  public void testRingStudyDisabledTab() {
    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RING_STUDY);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editRingStudyPage.getLdbSection().isBusinessesTabEnabled());
    
    RingStudyPage ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", ringStudyPage.getLdbSection().isBusinessesTabEnabled());
  }

  @Test
  @DisplayName("Verify that the Data tab is disabled in Business Table view")
  @Description("The test creates a Business Table and checks if the Data tab is disabled. Then it adds a random business, generates the table and tests if the Data tab is still disabled.")
  @Tag("Flaky")
  public void testBusinessesDisabledTab() {
    EditBusinessesPage editBusinessesPage = (EditBusinessesPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.BUSINESSES);
    
    verificationStep("Verify that Data tab is disabled");
    Assert.assertFalse("Data tab should be disabled", editBusinessesPage.getLdbSection().isDataTabEnabled());
    
    editBusinessesPage.getLdbSection().clickBusinesses().addRandomBusinesses();
    BusinessesPage businessesPage = (BusinessesPage) editBusinessesPage.clickDone();
    
    verificationStep("Verify that Data tab is disabled");
    Assert.assertFalse("Data tab should be disabled", businessesPage.getLdbSection().isDataTabEnabled());
  }
  
  @Test
  public void testRelatedDataDisabledTab() {
    EditRelatedDataReportPage editRelatedDataPage = (EditRelatedDataReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RELATED_DATA);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editRelatedDataPage.getLdbSection().isBusinessesTabEnabled());
    
    RelatedDataReportPage relatedDataReportPage = (RelatedDataReportPage) editRelatedDataPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", relatedDataReportPage.getLdbSection().isBusinessesTabEnabled());
  }
  
  @Test
  public void testTimeSeriesDisabledTab() {
    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.TIME_SERIES);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editTimeSeriesPage.getLdbSection().isBusinessesTabEnabled());
    
    TimeSeriesPage timeSeriesPage = (TimeSeriesPage) editTimeSeriesPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", timeSeriesPage.getLdbSection().isBusinessesTabEnabled());
  }
  
  @Test
  public void testBarChartDisabledTab() {
    EditBarChartPage editBarChartPage = (EditBarChartPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.BAR_CHART);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editBarChartPage.getLdbSection().isBusinessesTabEnabled());
    
    BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", barChartPage.getLdbSection().isBusinessesTabEnabled());
  }
  
  @Test
  public void testScatterPlotDisabledTab() {
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.SCATTER_PLOT);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editScatterPlotPage.getLdbSection().isBusinessesTabEnabled());
    
    ScatterPlotPage scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", scatterPlotPage.getLdbSection().isBusinessesTabEnabled());
  }
  
  @Test
  public void testSimmonsCrosstabDisabledTab() {
    //mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader()
    //        .clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    EditCrosstabPage editCrosstabPage = (EditCrosstabPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editCrosstabPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Locations tab is disabled");
    Assert.assertFalse("Locations tab should be disabled", editCrosstabPage.getLdbSection().isLocationTabEnabled());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.EDUCATION);
    for(int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.TECHNOLOGY);
    for(int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout

    By enabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box");
    By disabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box.x-item-disabled");

    try {
      wait.until(ExpectedConditions.or(
              ExpectedConditions.presenceOfElementLocated(enabledDoneButton),
              ExpectedConditions.presenceOfElementLocated(disabledDoneButton)
      ));

      if (driver.findElements(disabledDoneButton).size() > 0) {
        logger.debug("No row elements are selected, selecting random row elements");
        List<WebElement> rowElements = driver.findElements(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap>.sa-check-button"));
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
          rowElements.get(rand.nextInt(rowElements.size())).click();
        }
      } else {
        logger.debug("Row elements selected, clicking on the Done button");
      }
    } catch (TimeoutException e) {
      logger.debug("Timed out waiting for the Done button state.");
    }
    
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", crosstabPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Locations tab is disabled");
    Assert.assertFalse("Locations tab should be disabled", editCrosstabPage.getLdbSection().isLocationTabEnabled());
  }
  
  @Test
  public void testScarboroughCrosstabDisabledTab() {
    //mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader()
    //        .clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", startBySelectingScarboroughPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Data tab is disabled");
    Assert.assertFalse("Data tab should be disabled", startBySelectingScarboroughPage.getLdbSection().isDataTabEnabled());
    
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", editScarboroughCrosstabPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Data tab is enabled");
    Assert.assertTrue("Data tab should be enabled", editScarboroughCrosstabPage.getLdbSection().isDataTabEnabled());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 2; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for(int i = 0; i < 2; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout

    By enabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box");
    By disabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box.x-item-disabled");

    try {
      wait.until(ExpectedConditions.or(
              ExpectedConditions.presenceOfElementLocated(enabledDoneButton),
              ExpectedConditions.presenceOfElementLocated(disabledDoneButton)
      ));

      if (driver.findElements(disabledDoneButton).size() > 0) {
        logger.debug("No row elements are selected, selecting random row elements");
        List<WebElement> rowElements = driver.findElements(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap>.sa-check-button"));
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
          rowElements.get(rand.nextInt(rowElements.size())).click();
        }
      } else {
        logger.debug("Row elements selected, clicking on the Done button");
      }
    } catch (TimeoutException e) {
      logger.debug("Timed out waiting for the Done button state.");
    }

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    verificationStep("Verify that Businesses tab is disabled");
    Assert.assertFalse("Businesses tab should be disabled", scarboroughCrosstabPage.getLdbSection().isBusinessesTabEnabled());
    
    verificationStep("Verify that Locations tab is disabled");
    Assert.assertFalse("Locations tab should be disabled", scarboroughCrosstabPage.getLdbSection().isLocationTabEnabled());
  }
  
}
