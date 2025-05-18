package net.simplyanalytics.tests.ldb.data;

import net.simplyanalytics.enums.*;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.windows.HistoricalYearWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class BrowseDataByDatasetTests extends TestBase {
  
  private MapPage mapPage;
  
  /**
   * Signing in and creating new project.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }
  
  @Test
  public void testAddNewDataByDataset() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDataFolderPanel dataByDatasetPanel = dataTab.openYearDropdown().clickYear(DataVariable.getActualYear());
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel.clickRandomizedDataset();
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel().openRandomFolders();
    String data = dataByDatasetDropDown.getDatasetSearchResultPanel().clickOnRandomData();
    mapPage = (MapPage) dataByDatasetDropDown.clickClose(Page.MAP_VIEW);
    
    verificationStep("Verify that the selected data variable is the active data variable");
    Assert.assertEquals("The data variable is not the expected",
        DataVariable.getByFullName(data), mapPage.getToolbar().getActiveDataVariable());
  }
  
  @Test
  public void testAddOldDataByDataset() {
    mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickGeneralSettingsButton()
            .getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    mapPage.getViewChooserSection().clickNewView().getActiveView().clickHistoricalViewsCheckbox();
    HistoricalYearWindow historicalYearWindow = mapPage.getViewChooserSection().clickNewView().getActiveView().clickHistoricalYearLink();

    NewViewPage newViewPage = historicalYearWindow.clickOKButton();
    newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();

    DataTab dataTab = newViewPage.getLdbSection().clickData();
    dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDataFolderPanel dataByDatasetPanel = dataTab.openYearDropdown().clickYear("2010");
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel
        .clickDataset(Dataset.COMMUNITY_DEMOGRAPHICS);
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Population, Age, Sex");
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Population");
   // dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel().clickFolder("Total");
    dataByDatasetDropDown.getDatasetSearchResultPanel()
        .clickOnDataVariable(DataVariable.HASHTAG_TOTAL_POPULATION_2010);
    mapPage = (MapPage) dataByDatasetDropDown.clickClose(Page.MAP_VIEW);
    
    verificationStep("Verify that the selected data variable is the active data variable");
    Assert.assertEquals("The data variable is not the expected",
            DataVariable.HASHTAG_TOTAL_POPULATION_2010.getFullName(), mapPage.getToolbar().getNameOfActiveDataVariable());
  }
  
  @Test
  public void testAddFeatureDataByDataset() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDataFolderPanel dataByDatasetPanel = dataTab.openYearDropdown().clickYear("2020");
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel
        .clickDataset(Dataset.COMMUNITY_DEMOGRAPHICS);
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Population, Age, Sex");
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Population");
    //dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel().clickFolder("Population");
    dataByDatasetDropDown.getDatasetSearchResultPanel()
        .clickOnDataVariable(DataVariable.HASHTAG_TOTAL_POPULATION_2020);
    mapPage = (MapPage) dataByDatasetDropDown.clickClose(Page.MAP_VIEW);
    
    verificationStep("Verify that the selected data variable is the active data variable");
    Assert.assertEquals("The data variable is not the expected",
        DataVariable.HASHTAG_TOTAL_POPULATION_2020.getFullName(), mapPage.getToolbar().getNameOfActiveDataVariable());
  }
  
  @Test
  public void testDatasetWithoutDataInTheGivenYear() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDataFolderPanel dataByDatasetPanel = dataTab.openYearDropdown().clickYear("2028");
    
    verificationStep("Verify that the \"" + Dataset.CONSUMER_EXPENDITURE_ESTIMATE.getId() + "\" dataset is disabled");
    String datasetName = Dataset.CONSUMER_EXPENDITURE_ESTIMATE.getDatasetName();
    Assert.assertFalse("The dataset should be disabled",
        dataByDatasetPanel.isDataSetClickable(Dataset.CONSUMER_EXPENDITURE_ESTIMATE));
    try {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      System.out.println(datasetName);
      WebElement specificDatasetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+datasetName+"']/..")));
      logger.debug("Found specific dataset element. Scrolling to it.");

      Actions actions = new Actions(driver);
      actions.moveToElement(specificDatasetElement).perform();

      logger.debug("Attempting to click the specific dataset element.");
      specificDatasetElement.click();
    }
    catch (ElementClickInterceptedException e) {
    verificationStep("Verify that the dropdown panel not appeared");
    Assert.assertFalse("The dropdown panel should not appear",
        BasePage.isPresent(DataByDatasetDropDown.class, driver)); }
  }
}
