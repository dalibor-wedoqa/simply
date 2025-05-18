package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.utils.DatasetYearUpdater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.search.DataResultItem;
import net.simplyanalytics.pageobjects.windows.DataSheetWindow;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class DataSheetWindowCloseTests extends TestBase {
  
  private DataSheetWindow dataSheetWindow;
  String year = "2024";
  
  /**
   * Signing in and creating new project and open data sheet window.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    createNewProjectWindow.selectCountry("Canada");
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);
    DataByDataFolderPanel dataByDatasetPanel = (DataByDataFolderPanel) mapPage.getLdbSection()
        .getDataTab().clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel
        .clickDataset(Dataset.PRIZM_CA);
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Social Groups");
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
            .clickFolder("Households");
    DataResultItem itemWithSubMenu = dataByDatasetDropDown
        .getDatasetSearchResultPanel()
        .getDataVariableRow("# Households in U1: Urban Elite");
    MetadataWindow metadataWindow = itemWithSubMenu.clickOnMoreOptions().clickViewMetadata();
    dataSheetWindow = metadataWindow
        .clickDataSheetByName(DatasetYearUpdater.updateDatasetYear("# Households in U1: Urban Elite, 2023",year));
  }
  
  @Test
  public void closeDataSheetWindow() {
    
    dataSheetWindow.clickClose();
    
    verificationStep("Verify that the Data Sheet Window is disappeared");
    Assert.assertFalse("The Data Sheet Window should be disappeared",
        dataSheetWindow.isDisplayed());
  }
  
  @Test
  public void closeDataSheetWindowWithCloseButton() {
    
    dataSheetWindow.clickCloseButton();
    
    verificationStep("Verify that the Data Sheet Window is disappeared");
    Assert.assertFalse("The Data Sheet Window should be disappeared",
        dataSheetWindow.isDisplayed());
  }

}
