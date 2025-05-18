package net.simplyanalytics.tests.ldb.data;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.*;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageProjectLdbPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DatasetNavigationPanel;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VariableBadgesTests extends TestBase {

  private MapPage mapPage;
  
  @Before
  public void before() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    NewViewPage newViewPage = createNewProjectWindow.clickClose();
    newViewPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    newViewPage.getViewChooserSection().clickNewView().getActiveView().clickHistoricalViewsCheckbox();
    mapPage = (MapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();
    System.out.println("TEST START");
  }

  @Test
  public void testVariablesBadgesManageProjectPanel() {

/*
//Original code with fixed data variables from data folder
*/

//    DataTab dataTab = mapPage.getLdbSection().clickData();
//    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
//    List<String[]> dataList = new ArrayList<String[]>();
//    List<Dataset> datasetList = new ArrayList<Dataset>();
//    for(Dataset dataset : Dataset.getAllUsaDatasets()) {
////      if(dataset != Dataset.AGS_2000_SUPPLEMENTAL_US && dataset != Dataset.AGS_CENSUS_1980_US
////          && dataset != Dataset.AGS_CENSUS_1990_US && dataset != Dataset.AGS_CENSUS_2000_US
////          && dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE  && dataset != Dataset.AGS_2000_SUPPLEMENTAL_US
////          && dataset != Dataset.PRIZM_US_RETIRED_2017 && dataset != Dataset.PSYCLE_US
////          && dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US && dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY
////          && dataset != Dataset.DECENNIAL_CENSUS_2000 && dataset != Dataset.COVID_19_OUTBREAK_USA_2020 && dataset != Dataset.CONSUMER_EXPEDITURE_ESTIMATE) {
////        datasetList.add(dataset);
////      }
//      if (
//              dataset != Dataset.DECENNIAL_CENSUS_2000 &&
//                      dataset != Dataset.DECENNIAL_CENSUS_2010 &&
//                      dataset != Dataset.DECENNIAL_CENSUS_2020 &&
//                      dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
//                      dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
//                      dataset != Dataset.AGS_CENSUS_1980_US &&
//                      dataset != Dataset.AGS_CENSUS_1990_US &&
//                      dataset != Dataset.AGS_CENSUS_2000_US &&
//                      dataset != Dataset.PRIZM_US_RETIRED_2017 &&
//                      dataset != Dataset.PSYCLE_US &&
//                      dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
//                      dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
//                      dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
//                      dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE &&
//                      dataset != Dataset.US_ELECTION_DATA) {
//        datasetList.add(dataset);
//      }
//    }

    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    List<String[]> dataList = new ArrayList<>();
    List<Dataset> datasetList = new ArrayList<>();
    List<Dataset> filteredDatasets = new ArrayList<>();

    // Filter datasets based on the first condition
    for (Dataset dataset : Dataset.getAllUsaDatasets()) {
      if (
              dataset != Dataset.AMERICAN_COMMUNITY_SURVEY &&
                      dataset != Dataset.US_ELECTION_DATA &&
                      dataset != Dataset.DECENNIAL_CENSUS_2000 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2010 &&
                      dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_CENSUS_1980_US &&
                      dataset != Dataset.AGS_CENSUS_1990_US &&
                      dataset != Dataset.AGS_CENSUS_2000_US &&
                      dataset != Dataset.PRIZM_US_RETIRED_2017 &&
                      dataset != Dataset.PSYCLE_US &&
                      dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
                      dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
                      dataset != Dataset.SCARBOROUGH_LOCAL_INSIGHTS &&
                      dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
                      dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE)
      {
        filteredDatasets.add(dataset);
      }
    }

    // Shuffle the filtered list to randomize the order
    Collections.shuffle(filteredDatasets);

    // Select the first 7 from the shuffled list
    for (int i = 0; i < 7 && i < filteredDatasets.size(); i++) {
      datasetList.add(filteredDatasets.get(i));
    }

    // Now datasetList contains 7 random datasets from the first filtered list and all datasets from the second filtered list
    for (Dataset dataset : datasetList) {
      System.out.println("7 random Dataset-s" + dataset); // Assuming Dataset has a getName() method
    }


    for(Dataset dataset : datasetList) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data, dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    //year 2000
    dataTab.openYearDropdown().clickYear("2000");
    Dataset[] dataset2000 = {Dataset.AGS_2000_SUPPLEMENTAL_US, Dataset.AGS_CENSUS_2000_US};
    for(Dataset dataset : dataset2000) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data + ", 2000", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1990
    dataTab.openYearDropdown().clickYear("1990");
    Dataset[] dataset1990 = {Dataset.AGS_CENSUS_1990_US};
    for(Dataset dataset : dataset1990) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1990", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1980
    dataTab.openYearDropdown().clickYear("1980");
    Dataset[] dataset1980 = {Dataset.AGS_CENSUS_1980_US};
    for(Dataset dataset : dataset1980) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");

      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1980", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }
    
    ManageProjectLdbPanel manageProjectLDBPanel = mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickRemoveLDBButton().clickData();
    List<String[]> actualdataVariables = manageProjectLDBPanel.getItemNamesAndBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in manage project LDB panel are correct");
    
    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " with badge " + data[1] + " is not correct", isPresent);
    }
    
    //Hover all data badges
    for(String[] data : dataList) {
      List<String> tooltipInfo = manageProjectLDBPanel.getDatasetNameandVendorAfterHovering(data[0]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
    }
  }

  //Not compatible recent data and historical view always one data varibale less
  //@Disabled("Skipping test testVariablesBadgesRecentData")

  @Test
  @DisplayName("Verify that data variables from different data folders that were selected match recent data ")
  @Description("The test creates a Map View and adds data from the dataset in random order and verifies if the selected data is in the recent data")
  @Tag("Flaky")
  public void testVariablesBadgesRecentData() {

    // Step 1: Access the data tab in the LDB section of the map page
    System.out.println("Accessing the data tab from the LDB section of the map page.");
    DataTab dataTab = mapPage.getLdbSection().clickData();

    // Step 2: Select to search data by Data Folder
    System.out.println("Selecting 'Search by Data Folder' in the data tab.");
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);

    // Step 3: Initialize lists to store the data variables and datasets
    System.out.println("Initializing lists for storing dataset information and selected data.");
    List<String[]> dataList = new ArrayList<>();
    List<Dataset> datasetList = new ArrayList<>();

    // Step 4: Add datasets to the list, excluding specific datasets as per the conditions
    System.out.println("Iterating over all USA datasets and excluding specific datasets.");
    for(Dataset dataset : Dataset.getAllUsaDatasets()) {
      if (
              dataset != Dataset.DECENNIAL_CENSUS_2000 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2010 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2020 &&
                      dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_CENSUS_1980_US &&
                      dataset != Dataset.AGS_CENSUS_1990_US &&
                      dataset != Dataset.AGS_CENSUS_2000_US &&
                      dataset != Dataset.PRIZM_US_RETIRED_2017 &&
                      dataset != Dataset.PSYCLE_US &&
                      dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
                      dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
                      dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
                      dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE &&
                      dataset != Dataset.US_ELECTION_DATA &&
                      !dataset.getDatasetName().equals("County Business Patterns Summary")
//                    dataset.getDatasetName().equals("American Community Survey")
    ){
        datasetList.add(dataset);
        System.out.println("Added dataset: " + dataset.getDatasetName());
      }
    }

    // Step 5: Loop through each dataset, selecting data and capturing metadata
    for(Dataset dataset : datasetList) {
      System.out.println("Selecting random data from dataset: " + dataset.getDatasetName());
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      DatasetNavigationPanel.dataFolderPath = "";
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersB();

      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      System.out.println("Random data selected: " + data);

      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");

      metadataWindow.clickClose();
      System.out.println("Metadata for data: " + data + " | Vendor: " + vendor);

      String[] dataArray = {data, dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);

      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }

    // Repeat the process for the year 2000 datasets
    System.out.println("Selecting and verifying datasets for the year 2000.");
    dataTab.openYearDropdown().clickYear("2000");
    Dataset[] dataset2000 = {Dataset.AGS_2000_SUPPLEMENTAL_US, Dataset.AGS_CENSUS_2000_US};
    for(Dataset dataset : dataset2000) {
      System.out.println("Selecting data for dataset: " + dataset.getDatasetName() + " in 2000.");
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      DatasetNavigationPanel.dataFolderPath = "";
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersB();

      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();

      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();

      System.out.println("Selected data: " + data + " with vendor: " + vendor + "for the year 2000");
      String[] dataArray = {data + ", 2000", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);

      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }

    // Repeat the process for the year 1990 datasets
    System.out.println("Selecting and verifying datasets for the year 1990.");
    dataTab.openYearDropdown().clickYear("1990");
    Dataset[] dataset1990 = {Dataset.AGS_CENSUS_1990_US};
    for(Dataset dataset : dataset1990) {
      System.out.println("Selecting data for dataset: " + dataset.getDatasetName() + " in 1990.");
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      DatasetNavigationPanel.dataFolderPath = "";
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersB();

      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();

      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();

      System.out.println("Selected data: " + data + " with vendor: " + vendor + "for the year 1990");
      String[] dataArray = {data+ ", 1990", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);

      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }

    // Repeat the process for the year 1980 datasets
    System.out.println("Selecting and verifying datasets for the year 1980.");
    dataTab.openYearDropdown().clickYear("1980");
    Dataset[] dataset1980 = {Dataset.AGS_CENSUS_1980_US};
    for(Dataset dataset : dataset1980) {
      System.out.println("Selecting data for dataset: " + dataset.getDatasetName() + " in 1980.");
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      DatasetNavigationPanel.dataFolderPath = "";
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersB();

      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();

      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();

      System.out.println("Selected data: " + data + " with vendor: " + vendor + "for the year 1980");
      String[] dataArray = {data+ ", 1980", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);

      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }

    // Step 6: Get the list of recent data from the recent menu
    System.out.println("Retrieving the list of recent data from the recent menu.");
    List<RecentFavoriteMenuItem> recentFavoriteMenuItemList = dataTab.clickRecent().getMenuItems();

    List<String[]> actualdataVariables = new ArrayList<>();
    for(RecentFavoriteMenuItem item : recentFavoriteMenuItemList) {
      String[] dataArray = {item.getTitle(), item.getBadge()};
      actualdataVariables.add(dataArray);
      System.out.println("Recent data item added: " + item.getTitle());
    }

    // Step 7: Verify that the data count matches
    System.out.println("Verifying that the number of selected data matches the recent data count.");
    verificationStep("Verify that data count is correct");
    System.out.println("Added:"+dataList.size()+" Actual:"+actualdataVariables.size());
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());

    // Step 8: Verify that variables and badges in recent data are correct
    System.out.println("Verifying that the data variables and badges in the recent menu are correct.");
    verificationStep("Verify that variables and badges in recent data menu are correct");
    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }

    // Step 9: Hover over each dataset to view the metadata
    System.out.println("Hovering over each dataset to check metadata.");
    for (String[] data : actualdataVariables) {
      boolean metadataVerified = false;
      for (String[] dataItem : dataList) {
        if (data[0].contains(dataItem[0]) && data[1].equals(dataItem[1])) {
          System.out.println("Verifying metadata for dataset: " + dataItem[2]);
          Assert.assertEquals("Vendor mismatch", data[1], dataItem[1]);
          metadataVerified = true;
          break;
        }
      }
      Assert.assertTrue("Metadata not verified for dataset " + data[0], metadataVerified);
    }

    // Step 10: Wrap up by displaying success
    System.out.println("Test completed successfully, data variables match recent data.");
  }

  //(@Disable - Test skiped, not working correctly with historical view)
  public void testVariablesBadgesEditViewPages() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    List<String[]> dataList = new ArrayList<String[]>();
    List<Dataset> datasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.getAllUsaDatasets()) {
//      if(dataset != Dataset.AGS_2000_SUPPLEMENTAL_US && dataset != Dataset.AGS_CENSUS_1980_US
//          && dataset != Dataset.AGS_CENSUS_1990_US && dataset != Dataset.AGS_CENSUS_2000_US
//          && dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE  && dataset != Dataset.AGS_2000_SUPPLEMENTAL_US
//          && dataset != Dataset.PRIZM_US_RETIRED_2017 && dataset != Dataset.PSYCLE_US
//          && dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US && dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY
//          && dataset != Dataset.DECENNIAL_CENSUS_2000 && dataset != Dataset.COVID_19_OUTBREAK_USA_2020 && dataset != Dataset.CONSUMER_EXPEDITURE_ESTIMATE) {
//        datasetList.add(dataset);
//      }

      if (
              dataset != Dataset.SIMMONS_LOCAL_US &&
                      dataset != Dataset.RETAIL_MARKET_POWER_US &&
                      dataset != Dataset.PSYCLE_PREMIER_US &&
                      dataset != Dataset.FINANCIAL_CLOUT_US &&
                      dataset != Dataset.CONSUMER_BUYING_POWER_US &&
                      dataset != Dataset.CONNEXIONS_US &&
                      dataset != Dataset.PRIZM_Nielsen_PREMIER_US &&
                      dataset != Dataset.AGS_HEALTH_CARE_US &&
                      dataset != Dataset.AGS_CENSUS_US &&
                      dataset != Dataset.CLIMATE_DIVISIONAL_DATABASE &&
                      dataset != Dataset.UNIFORM_CRIME_REPORTING_US &&
                      dataset != Dataset.COUNTY_BUSINESS_PATTERS_SUMMARY &&


              dataset != Dataset.DECENNIAL_CENSUS_2000 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2010 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2020 &&
                      dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_CENSUS_1980_US &&
                      dataset != Dataset.AGS_CENSUS_1990_US &&
                      dataset != Dataset.AGS_CENSUS_2000_US &&
                      dataset != Dataset.PRIZM_US_RETIRED_2017 &&
                      dataset != Dataset.PSYCLE_US &&
                      dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
                      dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
                      dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
                      dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE &&
                      dataset != Dataset.US_ELECTION_DATA) {
        datasetList.add(dataset);
      }
    }
    for(Dataset dataset : datasetList) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data, dataset.getBadge(), dataset.getDatasetName(), vendor, null};
      dataList.add(dataArray);
    }
    
    //year 2000
    dataTab.openYearDropdown().clickYear("2000");
    Dataset[] dataset2000 = {Dataset.AGS_2000_SUPPLEMENTAL_US, Dataset.AGS_CENSUS_2000_US};
    for(Dataset dataset : dataset2000) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data + ", 2000", dataset.getBadge(), dataset.getDatasetName(), vendor, null};
      dataList.add(dataArray);
    }
    
    //year 1990
    dataTab.openYearDropdown().clickYear("1990");
    Dataset[] dataset1990 = {Dataset.AGS_CENSUS_1990_US};
    for(Dataset dataset : dataset1990) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1990", dataset.getBadge(), dataset.getDatasetName(), vendor, null};
      dataList.add(dataArray);
    }
    
    //year 1980
    dataTab.openYearDropdown().clickYear("1980");
    Dataset[] dataset1980 = {Dataset.AGS_CENSUS_1980_US};
    for(Dataset dataset : dataset1980) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1980", dataset.getBadge(), dataset.getDatasetName(), vendor, null};
      dataList.add(dataArray);
      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }
    
    EditComparisonReportPage editComparisonPage = (EditComparisonReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.COMPARISON_REPORT);
    List<String[]> actualdataVariables = editComparisonPage.getActiveView().getDataPanel().getSelectedElementsWithBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in Edit Comparison view data panel are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(int i = 0; i < dataList.size(); i++) {
        if(data[0].contains(dataList.get(i)[0]) && data[1].equals(dataList.get(i)[1])) {
          isPresent = true;
          //dataList.get(i)[4] = data[0];
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = editComparisonPage.getActiveView().getDataPanel().getDatasetNameandVendorAfterHovering(data[4]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      
    }
    
    EditRankingPage editRankingPage = (EditRankingPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RANKING);
    actualdataVariables = editRankingPage.getActiveView().getDataPanel().getSelectedElementsWithBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in Edit Ranking view data panel are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = editRankingPage.getActiveView().getDataPanel().getDatasetNameandVendorAfterHovering(data[4]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      
    }
    
    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RING_STUDY);
    actualdataVariables = editRingStudyPage.getActiveView().getDataPanel().getSelectedElementsWithBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in Edit Ring Study data panel are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = editRingStudyPage.getActiveView().getDataPanel().getDatasetNameandVendorAfterHovering(data[4]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      
    }
    
    EditRelatedDataReportPage editRelatedDataReportPage = (EditRelatedDataReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RELATED_DATA);
    actualdataVariables = editRelatedDataReportPage.getActiveView().getDataPanel().getAllElementsAndBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in Edit Related Data Report data panel are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = editRelatedDataReportPage.getActiveView().getDataPanel().getDatasetNameandVendorAfterHovering(data[4]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      
    }
    
    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.TIME_SERIES);
    actualdataVariables = editTimeSeriesPage.getActiveView().getDataPanel().getAllElementsAndBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in Edit Time Series data panel are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = editTimeSeriesPage.getActiveView().getDataPanel().getDatasetNameandVendorAfterHovering(data[4]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      
    }
    
    EditBarChartPage editBarChartPage = (EditBarChartPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.BAR_CHART);
    actualdataVariables = editBarChartPage.getActiveView().getDataPanel().getAllElementsAndBadges();
    
    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    
    verificationStep("Verify that variables and badges in Edit Bar Chart data panel are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = editBarChartPage.getActiveView().getDataPanel().getDatasetNameandVendorAfterHovering(data[4]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      
    }
  }

  @Test
  @DisplayName("Verify that added Data variables and badges in manage project LDB panel are correct")
  @Description("The test creates a Map and adds data variables and checks if the data badges match.")
  @Tag("TCE/Fix")
  public void testProjectedDataBadges() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    System.out.println("DataTab initialized: " + dataTab);

    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    System.out.println("DataByDataFolderPanel initialized: " + dataByDataFolderPanel);

    List<String[]> dataList = new ArrayList<>();
    System.out.println("Initialized empty dataList: " + dataList);

    String year = "2026";
    System.out.println("Year set to: " + year);

    Dataset[] dataset2026 = {
            Dataset.AGS_HEALTH_CARE_US,
            Dataset.COMMUNITY_DEMOGRAPHICS,
            Dataset.AMERICAN_COMMUNITY_SURVEY,
            Dataset.CONSUMER_BUYING_POWER_US
    };
    System.out.println("Datasets for year 2026: " + Arrays.toString(dataset2026));

    for (Dataset dataset : dataset2026) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      System.out.println("Clicked on dataset: " + dataset.getDatasetName());

      DatasetNavigationPanel.dataFolderPath = "";
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersB();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      System.out.println("Selected random data: " + data);

      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      System.out.println("Data name from metadata: " + data);

      String vendor = metadataWindow.getMetadataValue("Vendor");
      System.out.println("Vendor from metadata: " + vendor);

      metadataWindow.clickClose();
      String[] dataArray = {data + ", " + year, dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
      System.out.println("Added data to dataList: " + Arrays.toString(dataArray));
    }

    year = "2028";
    System.out.println("Year set to: " + year);

    Dataset[] dataset2028 = {
            Dataset.PRIZM_Nielsen_PREMIER_US,
            Dataset.CONNEXIONS_US,
            Dataset.CONSUMER_BUYING_POWER_US,
            Dataset.FINANCIAL_CLOUT_US,
            Dataset.PSYCLE_PREMIER_US,
            Dataset.RETAIL_MARKET_POWER_US
    };
    System.out.println("Datasets for year 2028: " + Arrays.toString(dataset2028));

    for (Dataset dataset : dataset2028) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      System.out.println("Clicked on dataset: " + dataset.getDatasetName());

      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      System.out.println("Selected random data: " + data);

      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      System.out.println("Data name from metadata: " + data);

      String vendor = metadataWindow.getMetadataValue("Vendor");
      System.out.println("Vendor from metadata: " + vendor);

      metadataWindow.clickClose();
      String[] dataArray = {data + ", " + year, dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
      System.out.println("Added data to dataList: " + Arrays.toString(dataArray));

      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
      System.out.println("MapPage updated: " + mapPage);
    }

    ManageProjectLdbPanel manageProjectLDBPanel = mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickRemoveLDBButton().clickData();
    System.out.println("ManageProjectLdbPanel initialized: " + manageProjectLDBPanel);

    List<String[]> actualdataVariables = manageProjectLDBPanel.getItemNamesAndBadges();
    System.out.println("Fetched actual data variables and badges: " + actualdataVariables);

    verificationStep("Verify that data count is correct");
    Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());
    System.out.println("Verified data count. Expected: " + dataList.size() + ", Actual: " + actualdataVariables.size());

    verificationStep("Verify that variables and badges in manage project LDB panel are correct");

    for (String[] data : actualdataVariables) {
      boolean isPresent = false;
      for (String[] dataListItem : dataList) {
        if (data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      System.out.println("Checked presence for data: " + Arrays.toString(data) + ", isPresent: " + isPresent);
      Assert.assertTrue(data[0] + " is not correct", isPresent);
      Assert.assertEquals("Data variable should have 'proj' badge", "proj", data[2]);
    }

    for (String[] data : dataList) {
      List<String> tooltipInfo = manageProjectLDBPanel.getDatasetNameandVendorAfterHovering(data[0]);
      System.out.println("Hovered data: " + Arrays.toString(data) + ", Tooltip info: " + tooltipInfo);

      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      System.out.println("Verified dataset name. Expected: " + data[2] + ", Actual: " + tooltipInfo.get(0).trim());

      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
      System.out.println("Verified vendor. Expected: " + data[3] + ", Actual: " + tooltipInfo.get(1).trim());
    }
  }

  @Test
  //@Disabled("Skipping test testVariableBadgesComparisonView")
  public void testVariableBadgesComparisonView() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    List<String[]> dataList = new ArrayList<String[]>();
    List<Dataset> datasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.getAllUsaDatasets()) {
//      if(dataset != Dataset.AGS_2000_SUPPLEMENTAL_US && dataset != Dataset.AGS_CENSUS_1980_US
//          && dataset != Dataset.AGS_CENSUS_1990_US && dataset != Dataset.AGS_CENSUS_2000_US
//          && dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE  && dataset != Dataset.AGS_2000_SUPPLEMENTAL_US
//          && dataset != Dataset.PRIZM_US_RETIRED_2017 && dataset != Dataset.PSYCLE_US
//          && dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US && dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY
//          && dataset != Dataset.DECENNIAL_CENSUS_2000 && dataset != Dataset.COVID_19_OUTBREAK_USA_2020 && dataset != Dataset.CONSUMER_EXPEDITURE_ESTIMATE) {
//        datasetList.add(dataset);
//      }

      if (
              dataset != Dataset.SIMMONS_LOCAL_US &&
                      dataset != Dataset.RETAIL_MARKET_POWER_US &&
                      dataset != Dataset.PSYCLE_PREMIER_US &&
                      dataset != Dataset.FINANCIAL_CLOUT_US &&
                      dataset != Dataset.CONSUMER_BUYING_POWER_US &&
                      dataset != Dataset.CONNEXIONS_US &&
                      dataset != Dataset.PRIZM_Nielsen_PREMIER_US &&
                      dataset != Dataset.AGS_HEALTH_CARE_US &&
                      dataset != Dataset.AGS_CENSUS_US &&
                      dataset != Dataset.CLIMATE_DIVISIONAL_DATABASE &&
                      dataset != Dataset.UNIFORM_CRIME_REPORTING_US &&
                      dataset != Dataset.COUNTY_BUSINESS_PATTERS_SUMMARY &&


                      dataset != Dataset.DECENNIAL_CENSUS_2000 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2010 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2020 &&
                      dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_CENSUS_1980_US &&
                      dataset != Dataset.AGS_CENSUS_1990_US &&
                      dataset != Dataset.AGS_CENSUS_2000_US &&
                      dataset != Dataset.PRIZM_US_RETIRED_2017 &&
                      dataset != Dataset.PSYCLE_US &&
                      dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
                      dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
                      dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
                      dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE &&
                      dataset != Dataset.US_ELECTION_DATA) {
        datasetList.add(dataset);
      }
    }
    //year 2000
    dataTab.openYearDropdown().clickYear("2000");
    Dataset[] dataset2000 = {Dataset.AGS_2000_SUPPLEMENTAL_US, Dataset.AGS_CENSUS_2000_US};
    for(Dataset dataset : dataset2000) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data + ", 2000", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1990
    dataTab.openYearDropdown().clickYear("1990");
    Dataset[] dataset1990 = {Dataset.AGS_CENSUS_1990_US};
    for(Dataset dataset : dataset1990) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1990", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1980
    dataTab.openYearDropdown().clickYear("1980");
    Dataset[] dataset1980 = {Dataset.AGS_CENSUS_1980_US};
    for(Dataset dataset : dataset1980) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1980", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }
    
    ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickNewView().
            getActiveView().
            clickCreate(ViewType.COMPARISON_REPORT).
            clickDone();
    
    List<String[]> actualdataVariables = comparisonReportPage.getActiveView().getDataVariablesAndBadges();
    
    verificationStep("Verify that variables and badges in Comparison Report view are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }

    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = comparisonReportPage.getActiveView().getDatasetNameandVendorAfterHovering(data[0]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
    }
  }

  @Test
  //@Disabled("Skipping test testVariableBadgesRankingView")
  public void testVariableBadgesRankingView() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    List<String[]> dataList = new ArrayList<String[]>();
    List<Dataset> datasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.values()) {
//      if(dataset != Dataset.AGS_2000_SUPPLEMENTAL_US && dataset != Dataset.AGS_CENSUS_1980_US
//          && dataset != Dataset.AGS_CENSUS_1990_US && dataset != Dataset.AGS_CENSUS_2000_US
//          && dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE  && dataset != Dataset.AGS_2000_SUPPLEMENTAL_US
//          && dataset != Dataset.PRIZM_US_RETIRED_2017 && dataset != Dataset.PSYCLE_US
//          && dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US && dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY
//          && dataset != Dataset.COUNTY_BUSINES_PATTERS_SUMMARY && dataset != Dataset.UNIFORM_CRIME_REPORTING_US
//          && dataset != Dataset.COVID_19_OUTBREAK_USA_2020 && dataset != Dataset.CONSUMER_EXPEDITURE_ESTIMATE) {
//        datasetList.add(dataset);
//      }

      if (
              dataset != Dataset.SIMMONS_LOCAL_US &&
                      dataset != Dataset.RETAIL_MARKET_POWER_US &&
                      dataset != Dataset.PSYCLE_PREMIER_US &&
                      dataset != Dataset.FINANCIAL_CLOUT_US &&
                      dataset != Dataset.CONSUMER_BUYING_POWER_US &&
                      dataset != Dataset.CONNEXIONS_US &&
                      dataset != Dataset.PRIZM_Nielsen_PREMIER_US &&
                      dataset != Dataset.AGS_HEALTH_CARE_US &&
                      dataset != Dataset.AGS_CENSUS_US &&
                      dataset != Dataset.CLIMATE_DIVISIONAL_DATABASE &&
                      dataset != Dataset.UNIFORM_CRIME_REPORTING_US &&
                      dataset != Dataset.COUNTY_BUSINESS_PATTERS_SUMMARY &&


              dataset != Dataset.DECENNIAL_CENSUS_2000 &&
              dataset != Dataset.DECENNIAL_CENSUS_2010 &&
              dataset != Dataset.DECENNIAL_CENSUS_2020 &&
              dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
              dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
              dataset != Dataset.AGS_CENSUS_1980_US &&
              dataset != Dataset.AGS_CENSUS_1990_US &&
              dataset != Dataset.AGS_CENSUS_2000_US &&
              dataset != Dataset.PRIZM_US_RETIRED_2017 &&
              dataset != Dataset.PSYCLE_US &&
              dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
              dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
              dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
              dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE &&
              dataset != Dataset.US_ELECTION_DATA) {
        datasetList.add(dataset);
      }
    }
    
    //year 2000
    dataTab.openYearDropdown().clickYear("2000");
    Dataset[] dataset2000 = {Dataset.AGS_2000_SUPPLEMENTAL_US, Dataset.AGS_CENSUS_2000_US};
    for(Dataset dataset : dataset2000) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data + ", 2000", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1990
    dataTab.openYearDropdown().clickYear("1990");
    Dataset[] dataset1990 = {Dataset.AGS_CENSUS_1990_US};
    for(Dataset dataset : dataset1990) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1990", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1980
    dataTab.openYearDropdown().clickYear("1980");
    Dataset[] dataset1980 = {Dataset.AGS_CENSUS_1980_US};
    for(Dataset dataset : dataset1980) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1980", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }
    
    EditRankingPage editRankingPage = (EditRankingPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RANKING);
    editRankingPage.getLdbSection().clickLocations().voidChooseLocation("Los An", "Los Angeles, CA");
    RankingPage rankingPage = (RankingPage) editRankingPage.clickDone();
    
    List<String[]> actualdataVariables = rankingPage.getActiveView().getDataVariablesWithBadges();
    
    verificationStep("Verify that variables and badges in Ranking view are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
        if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
          isPresent = true;
          break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
    
    //Hover all data badge
    for(String[] data : dataList) {
      List<String> tooltipInfo = rankingPage.getActiveView().getDatasetNameandVendorAfterHovering(data[0]);
      
      verificationStep("Verify that dataset name is correct");
      Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());
      
      verificationStep("Verify that vendor is correct");
      Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
    }
    
  }

  @Test
  //@Disabled("Skipping test testVariableBadgesRingStudy")
  public void testVariableBadgesRingStudy() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    List<String[]> dataList = new ArrayList<String[]>();
    List<Dataset> datasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.values()) {
//      if(dataset != Dataset.AGS_2000_SUPPLEMENTAL_US && dataset != Dataset.AGS_CENSUS_1980_US
//          && dataset != Dataset.AGS_CENSUS_1990_US && dataset != Dataset.AGS_CENSUS_2000_US
//          && dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE  && dataset != Dataset.AGS_2000_SUPPLEMENTAL_US
//          && dataset != Dataset.PRIZM_US_RETIRED_2017 && dataset != Dataset.PSYCLE_US
//          && dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US && dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY
//          && dataset != Dataset.COUNTY_BUSINES_PATTERS_SUMMARY && dataset != Dataset.UNIFORM_CRIME_REPORTING_US
//          && dataset != Dataset.COVID_19_OUTBREAK_USA_2020 && dataset != Dataset.CONSUMER_EXPEDITURE_ESTIMATE) {
//        datasetList.add(dataset);
//      }

      if (
              dataset != Dataset.SIMMONS_LOCAL_US &&
                      dataset != Dataset.RETAIL_MARKET_POWER_US &&
                      dataset != Dataset.PSYCLE_PREMIER_US &&
                      dataset != Dataset.FINANCIAL_CLOUT_US &&
                      dataset != Dataset.CONSUMER_BUYING_POWER_US &&
                      dataset != Dataset.CONNEXIONS_US &&
                      dataset != Dataset.PRIZM_Nielsen_PREMIER_US &&
                      dataset != Dataset.AGS_HEALTH_CARE_US &&
                      dataset != Dataset.AGS_CENSUS_US &&
                      dataset != Dataset.CLIMATE_DIVISIONAL_DATABASE &&
                      dataset != Dataset.UNIFORM_CRIME_REPORTING_US &&
                      dataset != Dataset.COUNTY_BUSINESS_PATTERS_SUMMARY &&



              dataset != Dataset.DECENNIAL_CENSUS_2000 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2010 &&
                      dataset != Dataset.DECENNIAL_CENSUS_2020 &&
                      dataset != Dataset.AGS_2000_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_2010_SUPPLEMENTAL_US &&
                      dataset != Dataset.AGS_CENSUS_1980_US &&
                      dataset != Dataset.AGS_CENSUS_1990_US &&
                      dataset != Dataset.AGS_CENSUS_2000_US &&
                      dataset != Dataset.PRIZM_US_RETIRED_2017 &&
                      dataset != Dataset.PSYCLE_US &&
                      dataset != Dataset.SIMMONS_NATIONAL_CONSUMER_SURVEY &&
                      dataset != Dataset.NIELSEN_SCARBOROUGH_CROSSTAB_US &&
                      dataset != Dataset.COVID_19_OUTBREAK_USA_2020 &&
                      dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE &&
                      dataset != Dataset.US_ELECTION_DATA)
       {
        datasetList.add(dataset);
      }
    }
    
    //year 2000
    dataTab.openYearDropdown().clickYear("2000");
    Dataset[] dataset2000 = {Dataset.AGS_2000_SUPPLEMENTAL_US, Dataset.AGS_CENSUS_2000_US};
    for(Dataset dataset : dataset2000) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data + ", 2000", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1990
    dataTab.openYearDropdown().clickYear("1990");
    Dataset[] dataset1990 = {Dataset.AGS_CENSUS_1990_US};
    for(Dataset dataset : dataset1990) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1990", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
    }
    
    //year 1980
    dataTab.openYearDropdown().clickYear("1980");
    Dataset[] dataset1980 = {Dataset.AGS_CENSUS_1980_US};
    for(Dataset dataset : dataset1980) {
      DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
      sleep(2000);
      dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
      String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
      MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
      data = metadataWindow.getMetadataValue("Name");
      String vendor = metadataWindow.getMetadataValue("Vendor");
      metadataWindow.clickClose();
      String[] dataArray = {data+ ", 1980", dataset.getBadge(), dataset.getDatasetName(), vendor};
      dataList.add(dataArray);
      mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
    }
    
    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RING_STUDY);
    editRingStudyPage.getLdbSection().clickLocations().voidChooseLocation("Los An", "Los Angeles, CA");
    RingStudyPage ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();
    
    List<String[]> actualdataVariables = ringStudyPage.getActiveView().getDataVariablesAndBadges();
    
    verificationStep("Verify that variables and badges in Ring Study are correct");

    for(String[] data : actualdataVariables) {
      boolean isPresent = false;
      for(String[] dataListItem : dataList) {
    	 if(data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
    	   isPresent = true;
    	   break;
        }
      }
      Assert.assertTrue(data[0] + " is not correct", isPresent);
    }
  }
}
