package net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.*;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.*;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxCrosstabContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.ScarboroughCheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OpenDataFolderTests extends TestBase {

    private MapPage mapPage;
    private DataVariable educationBachelorDegree = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
    
    @Before
    public void login() {
        driver.manage().window().maximize();
        AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
        SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
        WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
        NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
        mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
        mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickGeneralSettingsButton()
                .getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    }

    @Test
    public void testRankingViewColumnHeaderOpenDataFolder() {

        RankingPage rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(educationBachelorDegree.getFullName());
        DataFolderPanel dataFolder = rankingPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegree
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getAllDataListSeedVariable();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolder.selectRandomDataSeedVariable());
        dataFolder.clickClose(Page.RANKING_VIEW);
        for (String data : selectedData) {
            List<String> columnHeadValues = rankingPage.getActiveView().getColumnHeaderValues();
            verificationStep("Verify that the selected \"" + data + "\" is present in the Ranking table");
            Assert.assertTrue("Selected data is not displayed in the Ranking table.",
                    columnHeadValues.contains(data));
        }

    }

    @Test
    public void testComparisonReportViewHeaderWithTransposeOpenDataFolder() {

        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickView(ViewType.COMPARISON_REPORT.getDefaultName());
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(educationBachelorDegree.getFullName());
        DataFolderPanel dataFolderPanel = comparisonReportPage.getActiveView().openRowHeaderDropdown(educationBachelorDegree.getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolderPanel.getAllDataListSeedVariable();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolderPanel.selectRandomDataSeedVariable());
        dataFolderPanel.clickClose(Page.COMPARISON_REPORT_VIEW);
        for (String data : selectedData) {
            verificationStep("Verify that the selected \"" + data + "\" is present in the Comparison table");
            Assert.assertTrue("Selected data is not displayed in the Comparison table.",
                    comparisonReportPage.getActiveView().getRowHeaderValues().contains(data));
        }

        comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
                .clickTransposeReport();
        dataFolderPanel = comparisonReportPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegree.getFullName()).clickOpenDataFolder();
        List<String> selectedDataTranspose = dataFolderPanel.getAllDataListSeedVariable();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertTrue("Incorrect Data Folder is opened or the data is not selected.", selectedDataTranspose.contains(educationBachelorDegree.getFullName()));
        selectedData.add(dataFolderPanel.selectRandomDataSeedVariable());
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
        dataVariablesList.add(educationBachelorDegree.getFullName());
        DataFolderPanel dataFolder = ringStudyPage.getActiveView().openRowHeaderDropdown(educationBachelorDegree
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getAllDataListSeedVariable();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolder.selectRandomDataSeedVariable());
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
        dataVariablesList.add(educationBachelorDegree.getFullName());
        DataFolderPanel dataFolder = timeSeriesPage.getActiveView().openRowHeaderDropdown(educationBachelorDegree
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getAllDataListSeedVariable();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        selectedData.add(dataFolder.selectRandomDataSeedVariable());
        dataFolder.clickClose(Page.TIME_SERIES_VIEW);
        List<String> rowData = timeSeriesPage.getActiveView().getRowHeaderValues();
        String dataForCheck = selectedData.get(1).substring(0, selectedData.get(1).length()-6);
        verificationStep("Verify that the selected \"" + dataForCheck + "\" is present in the Time Series table");
        sleep(3000);
        for (String data : rowData) {
            Assert.assertTrue("Selected data is not displayed in the Time Series table.",
                    data.startsWith(dataForCheck.trim()));
        }

        //TODO: Scroll implementation

        timeSeriesPage = ((TimeSeriesViewActionMenu) timeSeriesPage.getToolbar().clickViewActions()).clickTransposeReport();

        dataFolder = timeSeriesPage.getActiveView().openColumnHeaderDropdown(selectedData.get(1)).clickOpenDataFolder();
        selectedData = dataFolder.getAllDataListSeedVariable();
        selectedData.add(dataFolder.selectRandomDataSeedVariable());
        dataFolder.clickClose(Page.TIME_SERIES_VIEW);
        List<String> columnData = timeSeriesPage.getActiveView().getColumnHeaderValues();
        dataForCheck = selectedData.get(1).substring(0, selectedData.get(1).length()-6);
        columnData.remove(0);
        verificationStep("Verify that the selected \"" + dataForCheck + "\" is present in the Time Series table");
        for (String data : columnData) {
            Assert.assertTrue("Selected data is not displayed in the Time Series table. Expected: " + dataForCheck + " but was " + data,
                    data.startsWith(dataForCheck));
        }

    }

    @Test
    public void testRelatedDataViewRowHeaderOpenDataFolder() {

        RelatedDataReportPage relatedDataReportPage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RELATED_DATA).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(educationBachelorDegree.getFullName());
        DataFolderPanel dataFolder = relatedDataReportPage.getActiveView().openRowHeaderDropdown(educationBachelorDegree
                .getFullName()).clickOpenDataFolder();
        List<String> selectedData = dataFolder.getAllDataListSeedVariable();
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertEquals("Incorrect Data Folder is opened or the data is not selected.", selectedData,
                dataVariablesList);
        String data = dataFolder.selectRandomDataSeedVariable();
        dataFolder.clickClose(Page.RELATED_DATA_VIEW);
        verificationStep("Verify that the selected \"" + data + "\" is present in the Related Data table");
        Assert.assertTrue("Selected data is not displayed in the Related Data table.",
                    relatedDataReportPage.getActiveView().getRowHeaderValues().contains(data));
        
    }

    @Test
    @DisplayName("Verify that the Open Data Folder opens the correct data folder and new data can be added to the Scarborough Crosstab table")
    @Description("The test creates a Scarborough Crosstab table. Uses the Open Data Folder option on one column and one row element. Verifies that the folder is correct and adds a new data value to check the modal functionality.")
    @Tag("TCE/Fix")
    public void testScarboroughsCrosstabHeaderOpenDataFolder() {
        System.out.println("Step 1: Navigating to the New View Page.");
        NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();  // Navigating to the new view chooser section and clicking "New View"

        System.out.println("Step 2: Selecting Scarborough Crosstab table to create.");
        StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView()
                .clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);  // Creating a new Scarborough Crosstab table

        System.out.println("Step 3: Clicking on the DMA Location: Los Angeles.");
        EditScarboroughCrosstabPage editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage
                .getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.LOS_ANGELES);  // Selecting Los Angeles location

        System.out.println("Step 4: Verifying the error message 'SELECT_DATA_VARIABLE'.");
        verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");  // Verifying the error message
        Assert.assertTrue("An errors message should appear",
                editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());  // Ensuring that the error message is displayed
        Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
                editScarboroughCrosstabPage.getActiveView().getErrorMessage());  // Ensuring the error message is the expected one

        System.out.println("Step 5: Opening Data by Category Panel.");
        DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
                .clickData().getBrowsePanel();  // Clicking on the Data panel and opening the browse panel

        List<String> columns = new ArrayList<String>();  // List to store selected columns
        List<String> rows = new ArrayList<String>();  // List to store selected rows

        System.out.println("Step 6: Selecting Age category data.");
        DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
                .clickOnACategoryData(CategoryData.AGE);  // Selecting the Age category data

        for(int i = 0; i < 2; i++) {
            System.out.println("Selecting a random data item from the Age category.");
            String data = dataByCategoryDropwDown.clickOnARandomDataResult();  // Clicking on a random data result
            System.out.println("Selected data: " + data);
            if(data.contains("Demographics")){
                System.out.println("Adding to columns as it is related to Demographics.");
                if(!columns.contains(data)) {
                    columns.add(data);  // Adding to columns if the data contains "Demographics"
                }
            } else {
                System.out.println("Adding to rows as it is not related to Demographics.");
                if (!rows.contains(data)) {
                    rows.add(data);  // Adding to rows if it doesn't contain "Demographics"
                }
            }
        }

        System.out.println("Step 7: Selecting Consumer Behavior category data.");
        dataByCategoryDropwDown = dataByCategoryPanel
                .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);  // Selecting the Consumer Behavior category data

        for(int i = 0; i < 4; i++) {
            System.out.println("Selecting a random data item from the Consumer Behavior category.");
            String data = dataByCategoryDropwDown.clickOnARandomDataResult();  // Clicking on a random data result
            System.out.println("Selected data: " + data);
            if(data.contains("Demographics")){
                System.out.println("Adding to columns as it is related to Demographics.");
                if(!columns.contains(data)) {
                    columns.add(data);  // Adding to columns if the data contains "Demographics"
                }
            } else {
                System.out.println("Adding to rows as it is not related to Demographics.");
                if (!rows.contains(data)) {
                    rows.add(data);  // Adding to rows if it doesn't contain "Demographics"
                }
            }
        }

        System.out.println("Step 8: Closing Data by Category dropdown and returning to Edit Scarborough Crosstab Page.");
        editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);  // Closing the data dropdown and returning to the edit page

        System.out.println("Step 9: Verifying column checkboxes.");
        ScarboroughCheckboxContainerPanel checkbowContainerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();  // Accessing the checkbox container panel
        verificationStep("Verify that column checkbox selected");
        for(String column : columns) {
            System.out.println("Verifying that checkbox for column: " + column + " is selected.");
            Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));  // Verifying column checkboxes
        }

        System.out.println("Step 10: Verifying row checkboxes.");
        verificationStep("Verify that row checkbox selected");
        for(String row : rows) {
            System.out.println("Verifying that checkbox for row: " + row + " is selected.");
            Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));  // Verifying row checkboxes
        }

        System.out.println("Step 11: Clicking Done to complete the process.");
        ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();  // Clicking Done to complete the process
        sleep(1000);

        System.out.println("Step 12: Opening Data Folder for Column header.");
        DataFolderPanel dataFolder = scarboroughCrosstabPage.getActiveView().openColumnHeaderDropdown(1).clickOpenDataFolder();  // Opening the data folder for column header
        List<String> selectedData = dataFolder.getAllDataListNoYear(true);  // Getting all data from the Data Folder
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        System.out.println("Selected data for column: " + selectedData);
        Assert.assertTrue("Incorrect Data Folder is opened or the data is not selected.", selectedData.contains(columns.get(0)));  // Verifying that the selected data is present

        System.out.println("Step 13: Selecting random data from Data Folder for column.");
        String data = dataFolder.selectRandomData(true);  // Selecting a random data from the Data Folder for column
        System.out.println("Selected data: " + data);
        dataFolder.clickClose(Page.SCARBOROUGH_CROSSTAB_PAGE);  // Closing the Data Folder

        verificationStep("Verify that the selected \"" + data + "\" is present in the Scarborough Crosstab table");
        Assert.assertTrue("Selected data is not displayed in the Scarborough Crosstab table.",
                scarboroughCrosstabPage.getActiveView().getColumnHeaderValues().contains(data));  // Verifying that the selected data is present in the table

        System.out.println("Step 14: Opening Data Folder for Row header.");
        dataFolder = scarboroughCrosstabPage.getActiveView().openRowHeaderDropdown(1).clickOpenDataFolder();  // Opening the data folder for row header
        List<String> selectedDataRow = dataFolder.getAllDataListNoYear(true);  // Getting all data from the Data Folder for row
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        System.out.println("Selected data for row: " + selectedDataRow);
        Assert.assertTrue("Incorrect Data Folder is opened or the data is not selected.", selectedDataRow.contains(rows.get(0)));  // Verifying that the selected data is present

        System.out.println("Step 15: Selecting random data from Data Folder for row.");
        data = dataFolder.selectRandomData(true);  // Selecting a random data from the Data Folder for row
        System.out.println("Selected data: " + data);
        dataFolder.clickClose(Page.SCARBOROUGH_CROSSTAB_PAGE);  // Closing the Data Folder

        verificationStep("Verify that the selected \"" + data + "\" is present in the Scarborough Crosstab table");
        Assert.assertTrue("Selected data is not displayed in the Scarborough Crosstab table.",
                scarboroughCrosstabPage.getActiveView().getRowHeaderValues().contains(data));  // Verifying that the selected data is present in the table

    }


    @Test
    @DisplayName("Verify that the Open Data Folder opens the correct data folder and new data can be added to the table")
    @Description("The test creates a Simmons Crosstab table. Uses the Open Data Folder option on one column and one row element. Verifies that the folder is correct and adds a new data value to check the modal functionality.")
    @Tag("TCE/Fix")
    public void testCrosstabHeaderOpenDataFolder() {

        // Open new view for Crosstab table creation
        System.out.println("Opening new view for Crosstab table creation");
        NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

        // Create a new Crosstab table
        System.out.println("Creating a new Crosstab table");
        EditCrosstabPage editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);

        // Verify that the "Select Data Variable" error appears
        System.out.println("Verifying that the 'Select Data Variable' error appears");
        verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");

        // Assert that error message is displayed
        System.out.println("Checking if error message is displayed");
        Assert.assertTrue("An errors message should appear", editCrosstabPage.getActiveView().isErrorMessageDisplayed());

        // Assert the error message matches the expected text
        System.out.println("Verifying the error message content");
        Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
                editCrosstabPage.getActiveView().getErrorMessage());

        // Open Data By Category Panel
        System.out.println("Opening Data By Category Panel");
        DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
                .clickData().getBrowsePanel();

        // Initialize lists for columns and rows
        System.out.println("Initializing columns and rows lists");
        List<String> columns = new ArrayList<String>();
        List<String> rows = new ArrayList<String>();

        // Select random data for 'Age' category and add to columns or rows
        System.out.println("Selecting random data from 'Age' category");
        DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);
        for (int i = 0; i < 2; i++) {
            String data = dataByCategoryDropwDown.clickOnARandomDataResult();
            System.out.println("Selected data:"+data);
            if (data.contains("Demographics")) {
                if (!columns.contains(data)) {
                    columns.add(data);
                }
            } else {
                if (!rows.contains(data)) {
                    rows.add(data);
                }
            }
        }

        // Select random data for 'Technology' category and add to columns or rows
        System.out.println("Selecting random data from 'Technology' category");
        dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);
        for (int i = 0; i < 4; i++) {
            String data = dataByCategoryDropwDown.clickOnARandomDataResult();
            System.out.println("Selected data:"+data);
            if (data.contains("DEMOGRAPHICS")) {
                if (!columns.contains(data)) {
                    columns.add(data);
                }
            } else {
                if (!rows.contains(data)) {
                    rows.add(data);
                }
            }
        }

        // Close the dropdown and return to edit page
        System.out.println("Closing the dropdown and returning to Edit Crosstab page");
        editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

        // Verify that the checkbox for columns and rows is selected
        System.out.println("Verifying that the column checkboxes are selected");
        CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
        verificationStep("Verify that the checkbox is selected");
        for (String column : columns) {
            System.out.println("Checking if column checkbox for " + column + " is selected");
            Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
        }

        System.out.println("Verifying that the row checkboxes are selected");
        verificationStep("Verify that row checkbox selected");
        for (String row : rows) {
            System.out.println("Checking if row checkbox for " + row + " is selected");
            Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));
        }

        // Proceed by clicking done and navigating to Crosstab page
        System.out.println("Clicking Done and navigating to Crosstab page");
        CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();

        // Open Data Folder from the column header
        System.out.println("Opening Data Folder from the column header dropdown");
        DataFolderPanel dataFolder = crosstabPage.getActiveView().openColumnHeaderDropdown(1).clickOpenDataFolder();

        // Verify data folder content and add new data
        System.out.println("Verifying the data in the Data Folder");
        List<String> selectedData = dataFolder.getAllDataListNoYear(true);
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertTrue("Incorrect Data Folder is opened or the data is not selected.", selectedData.contains(columns.get(0)));

        // Select random Crosstab variable and close Data Folder
        String data = dataFolder.selectRandomCrosstabVariable();
        System.out.println("Selected new data: " + data);
        dataFolder.clickClose(Page.SIMMONS_CROSSTAB_PAGE);

        // Verify that the new data is added to the Crosstab table
        System.out.println("Verifying if the new data is added to the Crosstab table");
        List<String> columnHeadValues = crosstabPage.getActiveView().getColumnHeaderValues();
        System.out.println("Current column header values: " + columnHeadValues);
        verificationStep("Verify that the selected \"" + data + "\" is present in the Simmons Crosstab table");
        Assert.assertTrue("Selected data is not displayed in the Simmons Crosstab table.",
                columnHeadValues.contains(data));

        // Repeat the same steps for the row header
        System.out.println("Opening Data Folder from the row header dropdown");
        dataFolder = crosstabPage.getActiveView().openRowHeaderDropdown(1).clickOpenDataFolder();
        List<String> selectedDataRow = dataFolder.getAllDataListNoYear(true);
        verificationStep("Verify that the data variable, for which Data Folder is opened, is the selected one.");
        Assert.assertTrue("Incorrect Data Folder is opened or the data is not selected.", selectedDataRow.contains(rows.get(0)));

        data = dataFolder.selectRandomCrosstabVariable();
        dataFolder.clickClose(Page.SIMMONS_CROSSTAB_PAGE);

        System.out.println("Verifying if the new data is added to the Crosstab table from row header");
        List<String> rowHeadValues = crosstabPage.getActiveView().getRowHeaderValues();
        verificationStep("Verify that the selected \"" + data + "\" is present in the Simmons Crosstab table");
        Assert.assertTrue("Selected data is not displayed in the Simmons Crosstab table.",
                rowHeadValues.contains(data));
    }


}
