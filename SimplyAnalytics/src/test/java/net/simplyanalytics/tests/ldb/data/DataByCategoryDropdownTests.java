package net.simplyanalytics.tests.ldb.data;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DatasetSearchResultPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class DataByCategoryDropdownTests extends TestBase {

  private MapPage mapPage;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  //@Disabled(Test skipped, the count, average and percent filters now contain same data, assertion not valid any more)
  public void  testDataType() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) dataTab.clickSearchBy(DataBrowseType.CATEGORY);
    DataByCategoryDropwDown dataByCategoryDropdown = dataByCategoryPanel.clickOnACategoryData(CategoryData.POPULATION);
    dataByCategoryDropdown.getDataFilterResultPanel().clickDataType(DataType.AVERAGE);
    
    sleep(3000);
    
    List<String> dataList = dataByCategoryDropdown.getDataSearchResultsPanel().getDataList();

    System.out.println("=======================================================================================");
    System.out.println("Godina za data folder select\n" + dataList);
    System.out.println("=======================================================================================");

    verificationStep("Verify that only average values are present");
    for(String data : dataList) {
      Assert.assertFalse("Data starts with %: " + data, data.startsWith("%"));
      Assert.assertFalse("Data starts with #: " + data, data.startsWith("#"));
    }
    
    dataByCategoryDropdown.getDataFilterResultPanel().clickDataType(DataType.AVERAGE);
    dataByCategoryDropdown.getDataFilterResultPanel().clickDataType(DataType.COUNT);
    
    sleep(3000);
    
    dataList = dataByCategoryDropdown.getDataSearchResultsPanel().getDataList();
    
    verificationStep("Verify that only count values are present");
    for(String data : dataList) {
      Assert.assertFalse("Data starts with %: " + data, data.startsWith("%"));
      Assert.assertTrue("Data does not start with #: " + data, data.startsWith("#"));
    }
    
    dataByCategoryDropdown.getDataFilterResultPanel().clickDataType(DataType.COUNT);
    dataByCategoryDropdown.getDataFilterResultPanel().clickDataType(DataType.PERCENT);
    
    sleep(3000);
    
    dataList = dataByCategoryDropdown.getDataSearchResultsPanel().getDataList();
    
    verificationStep("Verify that only percent values are present");
    for(String data : dataList) {
      Assert.assertTrue("Data does not start with %: " + data, data.startsWith("%"));
      Assert.assertFalse("Data starts with #: " + data, data.startsWith("#"));
    }
  }
  
  @Test
  public void testOpenDataFolderLink() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) dataTab.clickSearchBy(DataBrowseType.CATEGORY);
    DataByCategoryDropwDown dataByCategoryDropdown = dataByCategoryPanel.clickRandomDataCategory();
    String[] path = dataByCategoryDropdown.getFirstFolderPath();
    
    DataByDatasetDropDown dataByDatasetDropdown = dataByCategoryDropdown.clickFirstOpenDataFolderLink();
    sleep(2000);
    List<String> openedFolderPath = dataByDatasetDropdown.getDatasetNavigationPanel().getOpenedFolderPath();
    
    verificationStep("Verify that the correct data folder is opened");
    Assert.assertArrayEquals("The data folder path is not correct", path, openedFolderPath.toArray());
  }
  
  @Test
  public void testDropdownSearchField() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDatasetDropDown dataByDatasetDropdown = dataByDataPanel.clickDataset(Dataset.AGS_CENSUS_US);
    dataByDatasetDropdown = dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
    DatasetSearchResultPanel datasetSearchResultPanel = dataByDatasetDropdown.getDatasetSearchResultPanel();
    List<String> dataVariableList = datasetSearchResultPanel.getAllDataVariables();
    String dataVariableName = dataVariableList.get(0);
    logger.trace("Searched text: " + dataVariableName);
    dataByDatasetDropdown.searchText(dataVariableName);
    sleep(1000);
    
    List<String> searchResultsList = datasetSearchResultPanel.getAllDataVariables();
    
    verificationStep("Verify that a search result is present");
    Assert.assertTrue("There should be at least one result", searchResultsList.size() >= 1);
    
    verificationStep("Verify that search results contains the searched text");
    for(String result : searchResultsList) {
      Assert.assertTrue("Data variable : \"" + result + "\"does not contain the searched text", result.contains(dataVariableName));
    }

    dataByDatasetDropdown.clickClearSearchFiled();
    sleep(1000);

    Assert.assertArrayEquals("Original result list is different from the current list", 
        dataVariableList.toArray(), datasetSearchResultPanel.getAllDataVariables().toArray());
  }
}
