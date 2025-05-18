package net.simplyanalytics.tests.importtest;

import java.util.Arrays;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.HeadlessIssue;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.SingleTestRunner;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.header.Header;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ErrorWindow;
import net.simplyanalytics.pageobjects.sections.header.importpackage.IdentifyDataView;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ImportSection;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ManageWindow;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ReviewAndImportView;
import net.simplyanalytics.pageobjects.sections.header.importpackage.SuccessfulDialog;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByImportedDataseDropdown;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.ImportTermsWindow;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SingleTestRunner.class)
public class ImportAsGuest extends TestBase {
  private Header header;
  private NewProjectLocationWindow createNewProjectWindow;
  private ImportSection importSection;
  private IdentifyDataView identfyDataView;
  private MapPage mapPage;
  private ReviewAndImportView reviewAndImportView;
  private ImportTermsWindow importTermsWindow;
  
  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void createProject() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    header = mapPage.getHeaderSection();
    importTermsWindow = header.clickUser().clickImportWithImportTerms();
  }

  @Test
  public void testImportAsGuestBasicUseOfData() {  
    importSection = importTermsWindow.clickAgreeButton2();
    importSection.sendPath(LocationType.ZIP_CODE, getDriverConfiguration());
    importSection.selectGeographicUnit(LocationType.ZIP_CODE);
    
    verificationStep(
        "Verify that alert appears (Please correct the following error(s) to continue:)");
    ErrorWindow errorWindow = importSection.clickNextWithError();
    
    verificationStep("Verify that error dataset name is required appears ");
    Assert.assertEquals(Arrays.asList("A dataset name is required."), errorWindow.getErrors());
    
    errorWindow.clickOkButton(); 
    
    String dataSetName = importSection.sendNameOfDataset();
    
    identfyDataView=importSection.clickNext();
    identfyDataView.autoFillDataType();
    reviewAndImportView=identfyDataView.clickNext();
    SuccessfulDialog successfulDialog = reviewAndImportView.clickBeginImport();

    verificationStep("Verify that the Import is success");
    Assert.assertTrue("The Import is not success", successfulDialog.isDisplayed());
    
    mapPage = successfulDialog.clickOkButton();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByImportedDataseDropdown importedDatasetDropdown = dataByDataFolderPanel.clickImportedDataset(dataSetName);

    importedDatasetDropdown.getDatasetSearchResultPanel().clickDataVariableRow(DataVariable.PERCENT_HOUSING_BUILT_1939_OR_EARLIER_2019.getFullName());
    mapPage = (MapPage) importedDatasetDropdown.clickClose(Page.MAP_VIEW);
    
    String search = DataVariable.PERCENT_HOUSING_BUILT_1939_OR_EARLIER_2019.getFullName();
    
    verificationStep("Verify that actual data variable contains the searched text");
    Assert.assertTrue("The actual data variable should containing the searched text",
        mapPage.getToolbar().getNameOfActiveDataVariable().contains(search));
    
    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The Recent Data Variables button should be present",
        dataTab.isRecentPresent());
    
    RecentFavoriteMenuItemWithSubMenu dataVar = (RecentFavoriteMenuItemWithSubMenu) dataTab
        .clickRecent().getNthMenuItem(1);
    String dataVarName = dataVar.getTitle();
    MetadataWindow metadataWindow = dataVar.clickOnMoreOptions().clickViewMetadata();
    verificationStep("Verify that the correct metadata appears on the Metadata window");
    Assert.assertEquals(
        "The data variable name in recent menu should be the same as the data variable in Variable "
          + "MetaData Window",
        metadataWindow.getMetadataValue("Name") + ", " + metadataWindow.getMetadataValue("Year"),
        dataVarName);
  }
  
  @Test
  public void testDeleteImportedDatasetAsGuest() {  
    importSection = importTermsWindow.clickAgreeButton2();
    importSection.sendPath(LocationType.ZIP_CODE, getDriverConfiguration());
    importSection.selectGeographicUnit(LocationType.ZIP_CODE);
    
    verificationStep(
        "Verify that alert appears (Please correct the following error(s) to continue:)");
    ErrorWindow errorWindow = importSection.clickNextWithError();
    
    verificationStep("Verify that error dataset name is required appears ");
    Assert.assertEquals(Arrays.asList("A dataset name is required."), errorWindow.getErrors());
    
    errorWindow.clickOkButton(); 
    
    String dataSetName = importSection.sendNameOfDataset();
    identfyDataView=importSection.clickNext();
    identfyDataView.autoFillDataType();
    reviewAndImportView=identfyDataView.clickNext();
    SuccessfulDialog successfulDialog = reviewAndImportView.clickBeginImport(); 
    
    verificationStep("Verify that the Import is success");
    Assert.assertTrue("The Import is not success", successfulDialog.isDisplayed());
    
    successfulDialog.clickOkButton();
    
    ManageWindow manageWindow = header.clickUser().clickManageImport();
    
    verificationStep("Verify that dataset name is appeared");
    Assert.assertTrue("The imported dataset is missing: " + dataSetName, manageWindow.getAllImportedDatasetName().contains(dataSetName));

    manageWindow.deleteSingleDatasets();
    
    verificationStep("Verify that manage import button is disabled");
    Assert.assertFalse("Manage import button is still enabled", header.clickUser().verfiyManageImport());
  }
  
  @Test
  @HeadlessIssue
  @DisplayName("Verify that Imported data is uploaded properly")
  @Description("The test loads a csv data file and checks if it's opened properly in the project")
  @Tag("TCE/Fix")
  public void testImportedDataByCategoryDropdown() {
    importSection = importTermsWindow.clickAgreeButton();
    importSection.sendPath(LocationType.ZIP_CODE, getDriverConfiguration());
    importSection.selectGeographicUnit(LocationType.ZIP_CODE);
    
    verificationStep(
        "Verify that alert appears (Please correct the following error(s) to continue:)");
    ErrorWindow errorWindow = importSection.clickNextWithError();
    
    verificationStep("Verify that error dataset name is required appears ");
    Assert.assertEquals(Arrays.asList("A dataset name is required."), errorWindow.getErrors());
    
    errorWindow.clickOkButton(); 
    
    String dataSetName = importSection.sendNameOfDataset();
    
    identfyDataView=importSection.clickNext();
    identfyDataView.autoFillDataType();
    reviewAndImportView=identfyDataView.clickNext();
    SuccessfulDialog successfulDialog = reviewAndImportView.clickBeginImport(); 
    
    verificationStep("Verify that the Import is success");
    Assert.assertTrue("The Import is not success", successfulDialog.isDisplayed());
    
    successfulDialog.clickOkButton();
    mapPage = new MapPage(getDriver());
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) dataTab.getBrowsePanel();
    
    verificationStep("Verify that My Imported Data category appeared");
    Assert.assertTrue("My imported data category is missing", dataByCategoryPanel.isDataCategoryPresent(CategoryData.MY_IMPORTED_DATA));
    
    DataByImportedDataseDropdown dataByDatasetDropdown = dataByCategoryPanel
        .clickOnMyImportedDataCategory(dataSetName);
    dataByDatasetDropdown.getDatasetNavigationPanel().clickOnDataset(dataSetName);
    String dataResult = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
    dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    
    verificationStep("Verify that actual data variable contains the searched text");
    Assert.assertTrue("The actual data variable should containing the searched text",
        mapPage.getToolbar().getNameOfActiveDataVariable().contains(dataResult));
    
    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The Recent Data Variables button should be present",
        dataTab.isRecentPresent());
    
    RecentFavoriteMenuItemWithSubMenu dataVar = (RecentFavoriteMenuItemWithSubMenu) dataTab
        .clickRecent().getNthMenuItem(1);
    String dataVarName = dataVar.getTitle();
    MetadataWindow metadataWindow = dataVar.clickOnMoreOptions().clickViewMetadata();
    verificationStep("Verify that the correct metadata appears on the Metadata window");
    Assert.assertEquals(
        "The data variable name in recent menu should be the same as the data variable in Variable "
          + "MetaData Window",
        metadataWindow.getMetadataValue("Name") + ", " + metadataWindow.getMetadataValue("Year"),
        dataVarName);
  }
  
  @After
  public void after() {
  }
}