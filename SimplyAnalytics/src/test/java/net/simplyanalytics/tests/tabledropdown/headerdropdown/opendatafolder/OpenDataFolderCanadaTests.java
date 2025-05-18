package net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.*;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataFolderPanel;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OpenDataFolderCanadaTests extends TestBase {

    private MapPage mapPage;
    private DataVariable percentHousholdPopulation = DataVariable.PERCENT_HOUSEHOLD_POPULATION_25_TO_64_2024;
    private DataVariable basicTotalPopulation = DataVariable.HASHTAG_BASIC_POPULATION_2024;

    @Before
    public void login() {

        driver.manage().window().maximize();
        AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
        SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
        WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
        NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
        createNewProjectWindow.selectCountry("Canada");
        driver.manage().window().fullscreen();
        mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);

    }

    @Test
    public void testRankingViewColumnHeaderOpenDataFolder() {
        // Starting the test
        System.out.println("Test: testRankingViewColumnHeaderOpenDataFolder started");

        // Clicking on the "Ranking" view in the view chooser section and assigning it to 'rankingPage'
        RankingPage rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
        System.out.println("Ranking view selected and RankingPage object created");

        // Creating an empty list to store data variables for later verification
        List<String> dataVariablesList = new ArrayList<>();
        System.out.println("Empty dataVariablesList created");

        // Adding the full name of the data variable 'percentHousholdPopulation' to the list
        dataVariablesList.add(percentHousholdPopulation.getFullName());
        System.out.println("Data variable 'percentHousholdPopulation' added to dataVariablesList: " + percentHousholdPopulation.getFullName());

        // Opening the Data Folder from the column header dropdown for the selected data variable
        DataFolderPanel dataFolder = rankingPage.getActiveView().openColumnHeaderDropdown(percentHousholdPopulation
                .getFullName()).clickOpenDataFolder();
        System.out.println("Data folder opened for the column header: " + percentHousholdPopulation.getFullName());

        // Retrieving the list of selected data from the 'Canada All Data' list
        List<String> selectedData = dataFolder.getCanadaAllDataList();
        System.out.println("Selected data from 'Canada All Data' list: " + selectedData);

        // Verifying that the correct data variable is opened and selected
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        System.out.println("Verification step: Data variable matches the one that opened the Data Folder");

        // Asserting that the selected data matches the expected data variable list
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData, dataVariablesList);
        System.out.println("Assertion passed: Correct Data Folder is opened, and the data is selected");

        // Selecting a random data item from the 'Canada All Data' list and adding it to the selected data
        selectedData.add(dataFolder.selectRandomCanadaData());
        System.out.println("Random data selected from 'Canada All Data' list and added to selectedData: " + selectedData);

        // Closing the Data Folder panel in the Ranking view
        dataFolder.clickClose(Page.RANKING_VIEW);
        System.out.println("Data folder closed");

        // Iterating through the selected data to verify that each one is present in the Ranking table
        for (String data : selectedData) {
            // Printing the current data being verified
            verificationStep("Verify that the selected \"" + data + "\" is present in the Ranking table");
            System.out.println("Verification step: Checking if selected data is present in the Ranking table: " + data);

            // Asserting that the data is displayed in the Ranking table
            Assert.assertTrue("Selected data is not displayed in the Ranking table.",
                    rankingPage.getActiveView().getColumnHeaderValues().contains(data));
            System.out.println("Assertion passed: Selected data is displayed in the Ranking table");
        }

        // Indicating that the test has completed successfully
        System.out.println("Test: testRankingViewColumnHeaderOpenDataFolder completed successfully");
    }


    @Test
    public void testComparisonReportViewHeaderWithTransposeOpenDataFolder() {

        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickView(ViewType.COMPARISON_REPORT.getDefaultName());
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(percentHousholdPopulation.getFullName());
        DataFolderPanel dataFolderPanel = comparisonReportPage.getActiveView().openRowHeaderDropdown(percentHousholdPopulation.getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolderPanel.getCanadaAllDataList();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolderPanel.selectRandomCanadaData());
        dataFolderPanel.clickClose(Page.COMPARISON_REPORT_VIEW);
        for (String data : selectedData) {
            verificationStep("Verify that the selected \"" + data + "\" is present in the Comparison table");
            Assert.assertTrue("Selected data is not displayed in the Comparison table.",
                    comparisonReportPage.getActiveView().getRowHeaderValues().contains(data));
        }

        comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
                .clickTransposeReport();
        dataFolderPanel = comparisonReportPage.getActiveView().openColumnHeaderDropdown(percentHousholdPopulation.getFullName()).clickOpenDataFolder();
        List<String> selectedDataTranspose = dataFolderPanel.getCanadaAllDataList();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertTrue("Incorrect Data Folder is opened or the data is not selected.", selectedDataTranspose.contains(percentHousholdPopulation.getFullName()));
        selectedData.add(dataFolderPanel.selectRandomCanadaData());
        dataFolderPanel.clickClose(Page.COMPARISON_REPORT_VIEW);
        for (String data : selectedData) {
            verificationStep("Verify that the selected \"" + data + "\" is present in the Comparison table");
            Assert.assertTrue("Selected data is not displayed in the Comparison table.",
                    comparisonReportPage.getActiveView().getColumnHeaderValues().contains(data));
        }

    }

    @Test
    public void testRingStudyViewRowHeaderOpenDataFolder() {

        RingStudyPage ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RING_STUDY).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(percentHousholdPopulation.getFullName());
        DataFolderPanel dataFolder = ringStudyPage.getActiveView().openRowHeaderDropdown(percentHousholdPopulation
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getCanadaAllDataList();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolder.selectRandomCanadaData());
        dataFolder.clickClose(Page.RING_STUDY_VIEW);
        for (String data : selectedData) {
            verificationStep("Verify that the selected \"" + data + "\" is present in the Ring Study table");
            Assert.assertTrue("Selected data is not displayed in the Ring Study table.",
                    ringStudyPage.getActiveView().getRowHeaderValues().contains(data));
        }

    }

    @Test
    public void testTimeSeriesViewRowHeaderOpenDataFolder() {

        TimeSeriesPage timeSeriesPage = (TimeSeriesPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.TIME_SERIES).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(basicTotalPopulation.getFullName());
        DataFolderPanel dataFolder = timeSeriesPage.getActiveView().openRowHeaderDropdown(basicTotalPopulation
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getCanadaAllDataList();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolder.selectRandomCanadaData());
        dataFolder.clickClose(Page.TIME_SERIES_VIEW);
        sleep(3000);
        List<String> rowData = timeSeriesPage.getActiveView().getRowHeaderValues();
        String dataForCheck = selectedData.get(1).substring(0, selectedData.get(1).length()-6);
        verificationStep("Verify that the selected \"" + dataForCheck + "\" is present in the Time Series table");
        for (String data : rowData) {
          String upData = data.toUpperCase();
          String upDataForCheck = dataForCheck.toUpperCase();
            Assert.assertTrue("Selected data is not displayed in the Time Series table.",
                    upData.startsWith(upDataForCheck));
        }
//TODO: add transpose

    }

    @Test
    public void testRelatedDataViewRowHeaderOpenDataFolder() {

        RelatedDataReportPage relatedDataReportPage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RELATED_DATA).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(basicTotalPopulation.getFullName());
        DataFolderPanel dataFolder = relatedDataReportPage.getActiveView().openRowHeaderDropdown(basicTotalPopulation
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getCanadaAllDataList();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.",
                selectedData,
                dataVariablesList);

        String data = dataFolder.selectRandomCanadaData();
        dataFolder.clickClose(Page.RELATED_DATA_VIEW);

        verificationStep("Verify that the selected \"" + data + "\" is present in the Related Data table");
        Assert.assertTrue("Selected data is not displayed in the Related Data table.",
                    relatedDataReportPage.getActiveView().getRowHeaderValues().contains(data));
    }
}
