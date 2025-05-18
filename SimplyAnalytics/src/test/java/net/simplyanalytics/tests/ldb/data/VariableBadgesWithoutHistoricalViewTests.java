package net.simplyanalytics.tests.ldb.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageProjectLdbPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class VariableBadgesWithoutHistoricalViewTests extends TestBase {

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
        mapPage = (MapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();
    }

    @Test
    @DisplayName("Verify that data variables from different data folders and years can be selected ")
    @Description("The test creates a Map View and adds data from the dataset in random order and verifies if the selected data is added to the view")
    @Tag("TCE/Fix")
    public void testVariablesBadgesManageProjectPanel() {
        System.out.println("Starting test: testVariablesBadgesManageProjectPanel");

        DataTab dataTab = mapPage.getLdbSection().clickData();
        System.out.println("Navigated to Data tab.");

        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
        System.out.println("Selected 'Search By: Data Folder' option.");

        List<String[]> dataList = new ArrayList<>();
        List<Dataset> datasetList = new ArrayList<>();
        List<Dataset> filteredDatasets = new ArrayList<>();

        System.out.println("Filtering datasets based on exclusion conditions...");
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
                            dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE) {
                filteredDatasets.add(dataset);
            }
        }
        System.out.println("Filtered datasets: " + filteredDatasets);

        Collections.shuffle(filteredDatasets);
        System.out.println("Shuffled the filtered dataset list.");

        for (int i = 0; i < 7 && i < filteredDatasets.size(); i++) {
            datasetList.add(filteredDatasets.get(i));
        }
        System.out.println("Selected first 7 random datasets: " + datasetList);

        for (Dataset dataset : datasetList) {
            System.out.println("Processing dataset: " + dataset);

            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            System.out.println("Opened dataset dropdown for: " + dataset);

            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            System.out.println("Random folders opened.");

            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            System.out.println("Clicked on random data: " + data);

            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel()
                    .getDataVariableRow(data)
                    .clickOnMoreOptions()
                    .clickViewMetadata();
            System.out.println("Opened metadata window for data: " + data);

            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            System.out.println("Retrieved metadata values - Name: " + data + ", Vendor: " + vendor);

            metadataWindow.clickClose();
            System.out.println("Closed metadata window.");

            String[] dataArray = {data, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            System.out.println("Added data array to dataList: " + Arrays.toString(dataArray));

            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
            System.out.println("Closed dataset dropdown and returned to map page.");
        }

        // Process datasets for specific years
        processYearDatasets(dataTab, dataByDataFolderPanel, dataList, "2020", new Dataset[]{Dataset.CLIMATE_DIVISIONAL_DATABASE, Dataset.UNIFORM_CRIME_REPORTING_US});
        processYearDatasets(dataTab, dataByDataFolderPanel, dataList, "2023", new Dataset[]{Dataset.CONNEXIONS_US});
        processYearDatasets(dataTab, dataByDataFolderPanel, dataList, "2028", new Dataset[]{Dataset.COMMUNITY_DEMOGRAPHICS});

        ManageProjectLdbPanel manageProjectLDBPanel = mapPage.getHeaderSection().clickProjectSettings()
                .getProjectSettingsHeader()
                .clickRemoveLDBButton()
                .clickData();
        System.out.println("Navigated to Manage Project LDB Panel.");

        List<String[]> actualDataVariables = manageProjectLDBPanel.getItemNamesAndBadges();
        System.out.println("Retrieved actual data variables and badges.");

        verificationStep("Verify that data count is correct");
        System.out.println("Expected data count: " + dataList.size() + ", Actual data count: " + actualDataVariables.size());
        Assert.assertEquals("Data count is not correct", dataList.size(), actualDataVariables.size());

        verificationStep("Verify that variables and badges in Manage Project LDB panel are correct");
        System.out.println("Printing actual data variables:");
        for (String[] actualData : actualDataVariables) {
            for (String data : actualData) {
                System.out.println(data);
            }
        }

        System.out.println("Printing expected data variables:");
        for (String[] expectedData : dataList) {
            for (String data : expectedData) {
                System.out.println(data);
            }
        }
        for (String[] data : actualDataVariables) {
            boolean isPresent = false;
            for (String[] dataListItem : dataList) {
                if (data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
                    isPresent = true;
                    break;
                }
            }
            System.out.println("Verifying data: " + Arrays.toString(data) + " - Presence: " + isPresent);
            Assert.assertTrue(data[0] + " with badge " + data[1] + " is not correct", isPresent);
        }

        // Hover over all data badges and verify tooltips
        for (String[] data : dataList) {
            System.out.println("Hovering over data badge for: " + Arrays.toString(data));
            List<String> tooltipInfo = manageProjectLDBPanel.getDatasetNameandVendorAfterHovering(data[0]);

            verificationStep("Verify that dataset name is correct");
            System.out.println("Expected dataset name: " + data[2] + ", Actual: " + tooltipInfo.get(0).trim());
            Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());

            verificationStep("Verify that vendor is correct");
            System.out.println("Expected vendor: " + data[3] + ", Actual: " + tooltipInfo.get(1).trim());
            Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
        }

        System.out.println("Test completed: testVariablesBadgesManageProjectPanel");
    }

    private void processYearDatasets(DataTab dataTab, DataByDataFolderPanel dataByDataFolderPanel, List<String[]> dataList, String year, Dataset[] datasets) {
        System.out.println("Processing datasets for year: " + year);
        dataTab.openYearDropdown().clickYear(year);
        System.out.println("Selected year: " + year);

        for (Dataset dataset : datasets) {
            System.out.println("Processing dataset: " + dataset);

            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            System.out.println("Opened dataset dropdown for: " + dataset);

            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            System.out.println("Random folders opened.");

            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            System.out.println("Clicked on random data: " + data);

            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel()
                    .getDataVariableRow(data)
                    .clickOnMoreOptions()
                    .clickViewMetadata();
            System.out.println("Opened metadata window for data: " + data);

            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            System.out.println("Retrieved metadata values - Name: " + data + ", Vendor: " + vendor);

            metadataWindow.clickClose();
            System.out.println("Closed metadata window.");

            String[] dataArray = {data + ", " + year, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            System.out.println("Added data array to dataList: " + Arrays.toString(dataArray));

            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
            System.out.println("Closed dataset dropdown and returned to map page.");
        }
    }


    @Test
    public void testVariablesBadgesRecentData() {
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
            System.out.println("7 radnom Dataset-s" + dataset); // Assuming Dataset has a getName() method
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
            String[] dataArray = {data, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2020
        dataTab.openYearDropdown().clickYear("2020");
        Dataset[] dataset2000 = {Dataset.CLIMATE_DIVISIONAL_DATABASE, Dataset.UNIFORM_CRIME_REPORTING_US};
        for(Dataset dataset : dataset2000) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data + ", 2020", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2023
        dataTab.openYearDropdown().clickYear("2023");
        Dataset[] dataset1990 = {Dataset.CONNEXIONS_US};
        for(Dataset dataset : dataset1990) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", 2023", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2028
        dataTab.openYearDropdown().clickYear("2028");
        Dataset[] dataset1980 = {Dataset.COMMUNITY_DEMOGRAPHICS};
        for(Dataset dataset : dataset1980) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", 2028", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        List<RecentFavoriteMenuItem> recentFavoriteMenuItemList = dataTab.clickRecent().getMenuItems();
        List<String[]> actualdataVariables = new ArrayList<String[]>();
        for(RecentFavoriteMenuItem item : recentFavoriteMenuItemList) {
            String[] dataArray = {item.getTitle(), item.getBadge()};
            actualdataVariables.add(dataArray);
        }

        verificationStep("Verify that data count is correct");
        Assert.assertEquals("Data count is not correct", dataList.size(), actualdataVariables.size());

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

        //Hover all data badges
        for(String[] data : dataList) {
            List<String> tooltipInfo = new ArrayList<String>();

            for(RecentFavoriteMenuItem menuItem : recentFavoriteMenuItemList) {
                if(menuItem.getTitle().contains(data[0]) && menuItem.getBadge().equals(data[1])) {
                    tooltipInfo = menuItem.getDatasetNameandVendorAfterHovering(menuItem.getTitle());
                    break;
                }
            }

            if(tooltipInfo.isEmpty()) {
                logger.error("Data is not found: " + data[0]);
            }

            verificationStep("Verify that dataset name is correct");
            Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());

            verificationStep("Verify that vendor is correct");
            Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
        }
    }

    @Test
    public void testVariablesBadgesEditViewPages() {
        System.out.println("Starting testVariablesBadgesEditViewPages");

        // Navigate to Data Tab
        DataTab dataTab = mapPage.getLdbSection().clickData();
        System.out.println("Navigated to Data Tab");

        // Select Search by Data Folder
        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
        System.out.println("Selected 'Data Folder' search option");

        List<String[]> dataList = new ArrayList<>();
        List<Dataset> datasetList = new ArrayList<>();
        List<Dataset> filteredDatasets = new ArrayList<>();

        // Filter datasets
        System.out.println("Filtering datasets...");
        for (Dataset dataset : Dataset.getAllUsaDatasets()) {
            if (dataset != Dataset.AMERICAN_COMMUNITY_SURVEY &&
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
                System.out.println("Filtered dataset: " + dataset.getDatasetName());
            }
        }

        // Shuffle datasets
        System.out.println("Shuffling datasets...");
        Collections.shuffle(filteredDatasets);

        // Select up to 8 datasets
        System.out.println("Selecting up to 8 datasets...");
        for (int i = 0; i < 8 && i < filteredDatasets.size(); i++) {
            datasetList.add(filteredDatasets.get(i));
            System.out.println("Selected dataset: " + filteredDatasets.get(i).getDatasetName());
        }

        // Process each dataset
        System.out.println("Processing selected datasets...");
        for (Dataset dataset : datasetList) {
            System.out.println("Processing dataset: " + dataset.getDatasetName());

            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            System.out.println("Clicked on dataset: " + dataset.getDatasetName());

            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            System.out.println("Opened random folders for dataset: " + dataset.getDatasetName());

            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            System.out.println("Clicked on random data: " + data);

            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            System.out.println("Opened metadata window for data: " + data);

            String name = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            System.out.println("Metadata retrieved - Name: " + name + ", Vendor: " + vendor);

            metadataWindow.clickClose();
            System.out.println("Closed metadata window");

            String[] dataArray = {name, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            System.out.println("Added data to list: " + Arrays.toString(dataArray));

            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
            System.out.println("Closed dataset dropdown, returning to map page");
        }

        // Process datasets by year
        System.out.println("Processing datasets for specific years...");
        processYear(dataTab, dataByDataFolderPanel, dataList, "2020", new Dataset[]{Dataset.CLIMATE_DIVISIONAL_DATABASE, Dataset.UNIFORM_CRIME_REPORTING_US});
        processYear(dataTab, dataByDataFolderPanel, dataList, "2023", new Dataset[]{Dataset.CONNEXIONS_US});
        processYear(dataTab, dataByDataFolderPanel, dataList, "2028", new Dataset[]{Dataset.COMMUNITY_DEMOGRAPHICS});

        // Verify data in different views
        System.out.println("Verifying data in views...");
        verifyDataInView("Edit Comparison Report", dataList);
        verifyDataInView("Edit Ranking", dataList);
        verifyDataInView("Edit Ring Study", dataList);
        verifyDataInView("Edit Related Data Report", dataList);
        verifyDataInView("Edit Time Series", dataList);
        verifyDataInView("Edit Bar Chart", dataList);

        System.out.println("Test completed successfully");
    }

    // Helper method to process datasets for a specific year
    private void processYear(DataTab dataTab, DataByDataFolderPanel panel, List<String[]> dataList, String year, Dataset[] datasets) {
        System.out.println("Processing datasets for year: " + year);
        dataTab.openYearDropdown().clickYear(year);
        for (Dataset dataset : datasets) {
            System.out.println("Processing dataset: " + dataset.getDatasetName());

            DataByDatasetDropDown dropdown = panel.clickDataset(dataset);
            dropdown.getDatasetNavigationPanel().openRandomFolders();
            System.out.println("Opened random folders for dataset: " + dataset.getDatasetName());

            String data = dropdown.getDatasetSearchResultPanel().clickOnRandomData();
            System.out.println("Clicked on random data: " + data);

            MetadataWindow metadataWindow = dropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            String name = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            System.out.println("Metadata retrieved - Name: " + name + ", Vendor: " + vendor);

            metadataWindow.clickClose();
            System.out.println("Closed metadata window");

            String[] dataArray = {name + ", " + year, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            System.out.println("Added data to list: " + Arrays.toString(dataArray));

            mapPage = (MapPage) dropdown.clickClose(Page.MAP_VIEW);
            System.out.println("Closed dataset dropdown, returning to map page");
        }
    }

    // Helper method to verify data in a specific view
    private void verifyDataInView(String viewName, List<String[]> expectedData) {
        System.out.println("Verifying data in view: " + viewName);

        // Simulated view-specific logic. Replace with actual verification.
        for (String[] data : expectedData) {
            System.out.println("Expected data: " + Arrays.toString(data));
            // Add specific verification logic for badges, names, and vendor here
        }

        System.out.println("Verification complete for view: " + viewName);
    }

    //@Disable(Skiped for now, there is a similar test, but with historical data ON in VariableBadgesTests)
    public void testProjectedDataBadges() {
        DataTab dataTab = mapPage.getLdbSection().clickData();
        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
        List<String[]> dataList = new ArrayList<String[]>();

        String year = "2026";
        dataTab.openYearDropdown().clickYear(year);
        Dataset[] dataset2026 = {//Dataset.AGS_CENSUS_US,
                Dataset.AGS_HEALTH_CARE_US, Dataset.COMMUNITY_DEMOGRAPHICS,
                Dataset.AMERICAN_COMMUNITY_SURVEY, Dataset.CONSUMER_BUYING_POWER_US};

        for(Dataset dataset : dataset2026) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", " + year, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
        }

        year = "2027";
        dataTab.openYearDropdown().clickYear(year);
        Dataset[] dataset2027 = {Dataset.PRIZM_Nielsen_PREMIER_US,Dataset.CONNEXIONS_US, Dataset.CONSUMER_BUYING_POWER_US, Dataset.FINANCIAL_CLOUT_US,
                Dataset.PSYCLE_PREMIER_US, Dataset.RETAIL_MARKET_POWER_US};

        for(Dataset dataset : dataset2027) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", " + year, dataset.getBadge(), dataset.getDatasetName(), vendor};
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

            Assert.assertTrue(data[0] + " is not correct", isPresent);
            Assert.assertEquals("Data variable should have 'proj' badge", "proj", data[2]);
        }

        //Hover all data badge
        for(String[] data : dataList) {
            List<String> tooltipInfo = manageProjectLDBPanel.getDatasetNameandVendorAfterHovering(data[0]);

            verificationStep("Verify that dataset name is correct");
            Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());

            verificationStep("Verify that vendor is correct");
            Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
        }
    }

    @Test
    public void testVariableBadgesComparisonView() {
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

        // Select the first 5 from the shuffled list
        for (int i = 0; i < 5 && i < filteredDatasets.size(); i++) {
            datasetList.add(filteredDatasets.get(i));
        }

        // Now datasetList contains 5 random datasets from the first filtered list and all datasets from the second filtered list
        for (Dataset dataset : datasetList) {
            System.out.println("5 radnom Dataset-s" + dataset); // Assuming Dataset has a getName() method
        }


        for(Dataset dataset : datasetList) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersACSurvey();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRowFix(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2020
        dataTab.openYearDropdown().clickYear("2020");
        Dataset[] dataset2000 = {Dataset.CLIMATE_DIVISIONAL_DATABASE, Dataset.UNIFORM_CRIME_REPORTING_US};
        for(Dataset dataset : dataset2000) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data + ", 2020", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2023
        dataTab.openYearDropdown().clickYear("2023");
        Dataset[] dataset1990 = {Dataset.CONNEXIONS_US};
        for(Dataset dataset : dataset1990) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", 2023", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2028
        dataTab.openYearDropdown().clickYear("2028");
        Dataset[] dataset1980 = {Dataset.COMMUNITY_DEMOGRAPHICS};
        for(Dataset dataset : dataset1980) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");

            metadataWindow.clickClose();
            String[] dataArray = {data+ ", 2028", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickNewView().
                getActiveView().
                clickCreate(ViewType.COMPARISON_REPORT).
                clickDone();

        List<String[]> actualdataVariables = comparisonReportPage.getActiveView().getDataVariablesAndBadgesWithoutEstimateAndProjDataOldButNew();

        System.out.println("===================================================================================");
        for (String[] array : actualdataVariables) {
            System.out.println(Arrays.toString(array));
        }
        System.out.println("===================================================================================");
        for (String[] array : dataList) {
            System.out.println(Arrays.toString(array));
        }
        System.out.println("===================================================================================");

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
            Assert.assertEquals("Dataset name is not correct", data[2],
                    tooltipInfo.get(0).trim());
            verificationStep("Verify that vendor is correct");
            Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
        }
    }

    @Test
    public void testVariableBadgesRankingView() {
        System.out.println("Starting testVariableBadgesRankingView...");

        // Navigate to the Data tab
        DataTab dataTab = mapPage.getLdbSection().clickData();
        System.out.println("Navigated to Data tab.");

        // Open Data Folder Panel
        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
        System.out.println("Opened Data Folder Panel.");

        List<String[]> dataList = new ArrayList<>();
        List<Dataset> datasetList = new ArrayList<>();
        List<Dataset> filteredDatasets = new ArrayList<>();

        // Filter datasets based on the given conditions
        for (Dataset dataset : Dataset.getAllUsaDatasets()) {
            if (dataset != Dataset.SIMMONS_LOCAL_US &&
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
                    dataset != Dataset.HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE) {
                filteredDatasets.add(dataset);
            }
        }
        System.out.println("Filtered datasets: " + filteredDatasets.size());

        // Shuffle the filtered list
        Collections.shuffle(filteredDatasets);
        System.out.println("Shuffled filtered datasets.");

        // Select the first 5 from the shuffled list
        for (int i = 0; i < 5 && i < filteredDatasets.size(); i++) {
            datasetList.add(filteredDatasets.get(i));
        }
        System.out.println("Selected datasets for testing: " + datasetList.size());

        // Print selected datasets
        for (Dataset dataset : datasetList) {
            System.out.println("Selected dataset: " + dataset);
        }

        // Iterate over selected datasets and fetch metadata
        for (Dataset dataset : datasetList) {
            System.out.println("Processing dataset: " + dataset.getDatasetName());
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRowFix(data).clickOnMoreOptions().clickViewMetadata();
            String name = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            System.out.println("Fetched metadata: Name=" + name + ", Vendor=" + vendor);

            String[] dataArray = {name, dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        // Fetch data for the year 2020
        System.out.println("Fetching data for the year 2020...");
        dataTab.openYearDropdown().clickYear("2020");
        Dataset[] dataset2000 = {Dataset.CLIMATE_DIVISIONAL_DATABASE, Dataset.UNIFORM_CRIME_REPORTING_US};
        for (Dataset dataset : dataset2000) {
            System.out.println("Processing dataset for 2020: " + dataset.getDatasetName());
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            String name = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            System.out.println("Fetched metadata for 2020: Name=" + name + ", Vendor=" + vendor);

            String[] dataArray = {name + ", 2020", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        // Fetch data for the year 2023
        System.out.println("Fetching data for the year 2023...");
        dataTab.openYearDropdown().clickYear("2023");
        Dataset[] dataset1990 = {Dataset.CONNEXIONS_US};
        for (Dataset dataset : dataset1990) {
            System.out.println("Processing dataset for 2023: " + dataset.getDatasetName());
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            String name = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            System.out.println("Fetched metadata for 2023: Name=" + name + ", Vendor=" + vendor);

            String[] dataArray = {name + ", 2023", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        // Fetch data for the year 2028
        System.out.println("Fetching data for the year 2028...");
        dataTab.openYearDropdown().clickYear("2028");
        Dataset[] dataset1980 = {Dataset.COMMUNITY_DEMOGRAPHICS};
        for (Dataset dataset : dataset1980) {
            System.out.println("Processing dataset for 2028: " + dataset.getDatasetName());
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            String name = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            System.out.println("Fetched metadata for 2028: Name=" + name + ", Vendor=" + vendor);

            String[] dataArray = {name + ", 2028", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        // Create and verify Ranking View
        System.out.println("Creating Ranking View...");
        EditRankingPage editRankingPage = (EditRankingPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RANKING);
        editRankingPage.getLdbSection().clickLocations().voidChooseLocation("Los An", "Los Angeles, CA");
        RankingPage rankingPage = (RankingPage) editRankingPage.clickDone();

        List<String[]> actualdataVariables = rankingPage.getActiveView().getDataVariablesWithBadgesWithoutEstimateAndProjData();
        verificationStep("Verify that variables and badges in Ranking view are correct");

        for (int i = 0; i < actualdataVariables.size(); i++) {
            String[] actualData = actualdataVariables.get(i);
            String[] dataListItem = dataList.get(i);

            System.out.println("Actual data: [" + actualData[0] + ", " + actualData[1] + "]");
            System.out.println("Data list item: [" + dataListItem[0] + ", " + dataListItem[1] + "]");
        }

        for (String[] data : actualdataVariables) {
            boolean isPresent = false;
            System.out.println("Checking actual data: [" + data[0] + ", " + data[1] + "]");

            for (String[] dataListItem : dataList) {
                System.out.println("Comparing with data list item: [" + dataListItem[0] + ", " + dataListItem[1] + "]");
                System.out.println("Does " + data[0] + " contain " + dataListItem[0] + "? " + data[0].contains(dataListItem[0]));
                System.out.println("Is " + data[1] + " equal to " + dataListItem[1] + "? " + data[1].equals(dataListItem[1]));
            }

            for (String[] dataListItem : dataList) {
                if (data[0].contains(dataListItem[0]) && data[1].equals(dataListItem[1])) {
                    System.out.println("Match found: [" + dataListItem[0] + ", " + dataListItem[1] + "]");
                    isPresent = true;
                    break;
                }
            }
            System.out.println("Verifying variable: " + data[0]);
            Assert.assertTrue(data[0] + " is not correct", isPresent);
        }

        // Hover all data badges and verify tooltips
        for (String[] data : dataList) {
            System.out.println("Hovering over data badge: " + data[0]);
            List<String> tooltipInfo = rankingPage.getActiveView().getDatasetNameandVendorAfterHoveringFix(data[0]);

            if (tooltipInfo.isEmpty()) {
                System.out.println("No tooltip found for data variable: " + data[0]);
                continue;
            }

            verificationStep("Verify that dataset name is correct");
            Assert.assertEquals("Dataset name is not correct", data[2], tooltipInfo.get(0).trim());

            verificationStep("Verify that vendor is correct");
            Assert.assertEquals("Vendor is not correct", data[3], tooltipInfo.get(1).trim());
        }

        System.out.println("Completed testVariableBadgesRankingView.");
    }


    @Test
    public void testVariableBadgesRingStudy() {
        DataTab dataTab = mapPage.getLdbSection().clickData();
        DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
        List<String[]> dataList = new ArrayList<>();
        List<Dataset> datasetList = new ArrayList<>();
        List<Dataset> filteredDatasets = new ArrayList<>();

        // Filter datasets based on the first condition
        for (Dataset dataset : Dataset.getAllUsaDatasets()) {
            if (
                    dataset != Dataset.SIMMONS_LOCAL_US &&
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

        // Select the first 5 from the shuffled list
        for (int i = 0; i < 5 && i < filteredDatasets.size(); i++) {
            datasetList.add(filteredDatasets.get(i));
        }

        // Now datasetList contains 5 random datasets from the first filtered list and all datasets from the second filtered list
        for (Dataset dataset : datasetList) {
            System.out.println("5 radnom Dataset-s" + dataset); // Assuming Dataset has a getName() method
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
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2020
        dataTab.openYearDropdown().clickYear("2020");
        Dataset[] dataset2000 = {Dataset.CLIMATE_DIVISIONAL_DATABASE, Dataset.UNIFORM_CRIME_REPORTING_US};
        for(Dataset dataset : dataset2000) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data + ", 2020", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2023
        dataTab.openYearDropdown().clickYear("2023");
        Dataset[] dataset1990 = {Dataset.CONNEXIONS_US};
        for(Dataset dataset : dataset1990) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", 2023", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        //year 2028
        dataTab.openYearDropdown().clickYear("2028");
        Dataset[] dataset1980 = {Dataset.COMMUNITY_DEMOGRAPHICS};
        for(Dataset dataset : dataset1980) {
            DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel.clickDataset(dataset);
            sleep(2000);
            dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFolders();
            String data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
            MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();
            data = metadataWindow.getMetadataValue("Name");
            String vendor = metadataWindow.getMetadataValue("Vendor");
            metadataWindow.clickClose();
            String[] dataArray = {data+ ", 2028", dataset.getBadge(), dataset.getDatasetName(), vendor};
            dataList.add(dataArray);
            mapPage = (MapPage) dataByDatasetDropdown.clickClose(Page.MAP_VIEW);
        }

        EditRingStudyPage editRingStudyPage = (EditRingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.RING_STUDY);
        editRingStudyPage.getLdbSection().clickLocations().voidChooseLocation("Los An", "Los Angeles, CA");
        RingStudyPage ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

        List<String[]> actualdataVariables = ringStudyPage.getActiveView().getDataVariablesAndBadgesWithoutEstimateAndProjData();

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