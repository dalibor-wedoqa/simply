package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.CrosstabViewPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxCrosstabContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class EditCrosstabTests extends TestBase {

  private EditCrosstabPage editCrosstabPage;
  private MapPage mapPage;

  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
            InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);

    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
  }

  @Test
  @DisplayName("Verify that in Simmons Crosstab 'clear' and 'select all' data options work")
  @Description("The test creates a Simmons Crosstab view. Then clears checkboxes and verifies, then selects all and verifies.")
  @Tag("TCE/Fix")
  public void testClearAndSelectAllData() {
    // Step 1: Verify initial error message
    System.out.println("Step 1: Verifying initial error message.");
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
            editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    System.out.println("Error message displayed: " + editCrosstabPage.getActiveView().getErrorMessage());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());

    // Step 2: Initialize DataByCategoryPanel
    System.out.println("Step 2: Navigating to DataByCategoryPanel.");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    List<String> rows = new ArrayList<>();
    List<String> columns = new ArrayList<>();

    // Step 3: Process data for LANGUAGE category
    System.out.println("Step 3: Processing data for AGE category.");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);
    for (int i = 0; i < 3; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      System.out.println("AGE data fetched: " + data);
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

    // Step 4: Process data for TECHNOLOGY category
    System.out.println("Step 4: Processing data for TECHNOLOGY category.");
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);
    for (int i = 0; i < 3; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      System.out.println("TECHNOLOGY data fetched: " + data);
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

    System.out.println("Final Rows: " + rows);
    System.out.println("Final Columns: " + columns);

    // Step 5: Navigate back to EditCrosstabPage
    System.out.println("Step 5: Returning to EditCrosstabPage.");
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();

    // Step 6: Verify column and row checkbox selections
    System.out.println("Step 6: Verifying column and row checkbox selections.");
    for (String column : columns) {
      boolean isSelected = checkbowContainerPanel.isColumnCheckboxSelected(column);
      System.out.println("Column checkbox for data: " + column + " selected: " + isSelected);
      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", isSelected);
    }
    for (String row : rows) {
      boolean isSelected = checkbowContainerPanel.isRowCheckboxSelected(row);
      System.out.println("Row checkbox for data: " + row + " selected: " + isSelected);
      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", isSelected);
    }

    // Step 7: Clear and reverify checkboxes
    System.out.println("Step 7: Clearing selections and verifying all checkboxes are unchecked.");
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    System.out.println("click EditView");
    EditCrosstabPage editCrosstabPage = (EditCrosstabPage) crosstabPage.getToolbar().clickViewActions().clickEditView();
    System.out.println("get DataPanel");
    CheckboxCrosstabContainerPanel containerPanel = editCrosstabPage.getActiveView().getDataPanel();
    System.out.println("click Clear");
    containerPanel.clickClear();

    List<String> checkedRows = containerPanel.getAllCheckedDataRows();
    List<String> checkedColumns = containerPanel.getAllCheckedDataColumns();

    System.out.println("Checked Rows after clear: " + checkedRows);
    System.out.println("Checked Columns after clear: " + checkedColumns);

    verificationStep("Verify that all row checkboxes are unchecked");
    Assert.assertTrue("Row for some data is still checked", checkedRows.isEmpty());

    verificationStep("Verify that all column checkboxes are unchecked");
    Assert.assertTrue("Column for some data is still checked", checkedColumns.isEmpty());

    // Step 8: Select all and verify
    System.out.println("Step 8: Selecting all checkboxes and verifying.");
    containerPanel.clickSelectAll();
    checkedRows = containerPanel.getAllCheckedDataRows();
    checkedColumns = containerPanel.getAllCheckedDataColumns();

    System.out.println("Checked Rows after select all: " + checkedRows);
    System.out.println("Checked Columns after select all: " + checkedColumns);

    for (String column : columns) {
      boolean isSelected = checkbowContainerPanel.isColumnCheckboxSelected(column);
      System.out.println("Column checkbox for data: " + column + " selected after select all: " + isSelected);
      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", isSelected);
    }
    for (String row : rows) {
      boolean isSelected = checkbowContainerPanel.isRowCheckboxSelected(row);
      System.out.println("Row checkbox for data: " + row + " selected after select all: " + isSelected);
      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", isSelected);
    }
  }

//  public void testClearAndSelectAllData() {
//    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
//    Assert.assertTrue("An errors message should appear",
//            editCrosstabPage.getActiveView().isErrorMessageDisplayed());
//    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
//            editCrosstabPage.getActiveView().getErrorMessage());
//
//    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
//            .clickData().getBrowsePanel();
//
//    List<String> rows = new ArrayList<String>();
//    List<String> columns = new ArrayList<String>();
//
//    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
//            .clickOnACategoryData(CategoryData.LANGUAGE);
//    for (int i = 0; i < 3; i++) {
//      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
//      if (data.contains("DEMOGRAPHICS")) {
//        if (!columns.contains(data)) {
//          columns.add(data);
//        }
//      } else {
//        if (!rows.contains(data)) {
//          rows.add(data);
//        }
//      }
//    }
//
//    dataByCategoryDropwDown = dataByCategoryPanel
//            .clickOnACategoryData(CategoryData.TECHNOLOGY);
//    for (int i = 0; i < 3; i++) {
//      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
//      if (data.contains("DEMOGRAPHICS")) {
//        if (!columns.contains(data)) {
//          columns.add(data);
//        }
//      } else {
//        if (!rows.contains(data)) {
//          rows.add(data);
//        }
//      }
//    }
//
//    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);
//
//    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
//
////    for(int i = 0; i < rows.size(); i++) {
////      verificationStep("Verify that column checkbox with index: " + i + " selected");
////      Assert.assertTrue("Column checkbox with index: " + i + " should be selected", checkbowContainerPanel.isColumnCheckboxSelected(i));
////    }
////
////    for(int i = rows.size(); i < rows.size() + columns.size(); i++) {
////      verificationStep("Verify that row checkbox with index: " + i + " selected");
////      Assert.assertTrue("Row checkbox with index: " + i + " should be selected", checkbowContainerPanel.isRowCheckboxSelected(i));
////    }
////
//    for (String column : columns) {
//      logger.info("Data: " + column + "has column selected column:" + checkbowContainerPanel.isColumnCheckboxSelected(column));
//      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
//    }
//    verificationStep("Verify that row checkbox selected");
//    for (String row : rows) {
//      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));
//    }
//
//    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
//
//    EditCrosstabPage editCrosstabPage = (EditCrosstabPage) crosstabPage.getToolbar().clickViewActions().clickEditView();
//    CheckboxCrosstabContainerPanel containerPanel = editCrosstabPage.getActiveView().getDataPanel();
//    containerPanel.clickClear();
//
//    List<String> checkedRows = containerPanel.getAllCheckedDataRows();
//    List<String> checkedColumns = containerPanel.getAllCheckedDataColumns();
//
//    verificationStep("Verify that all row checkboxes are unchecked");
//    Assert.assertTrue("Row for some data is still checked", checkedRows.isEmpty());
//
//    verificationStep("Verify that all column checkboxes are unchecked");
//    Assert.assertTrue("Column for some data is still checked", checkedColumns.isEmpty());
//
//    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
//    Assert.assertTrue("An errors message should appear",
//            editCrosstabPage.getActiveView().isErrorMessageDisplayed());
//    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
//            editCrosstabPage.getActiveView().getErrorMessage());
//
//    containerPanel.clickSelectAll();
//
//    checkedRows = containerPanel.getAllCheckedDataRows();
//    checkedColumns = containerPanel.getAllCheckedDataColumns();
//
////    for(int i = 0; i < rows.size(); i++) {
////      verificationStep("Verify that column checkbox with index: " + i + " selected");
////      Assert.assertTrue("Column checkbox with index: " + i + " should be selected", checkbowContainerPanel.isColumnCheckboxSelected(i));
////    }
////
////    for(int i = rows.size(); i < rows.size() + columns.size(); i++) {
////      verificationStep("Verify that row checkbox with index: " + i + " selected");
////      Assert.assertTrue("Row checkbox with index: " + i + " should be selected", checkbowContainerPanel.isRowCheckboxSelected(i));
////    }
//
//    for (String column : columns) {
//      logger.info("Data: " + column + "has column selected column:" + checkbowContainerPanel.isColumnCheckboxSelected(column));
//      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
//    }
//    verificationStep("Verify that row checkbox selected");
//    for (String row : rows) {
//      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));
//    }
//  }

  @Test
  @DisplayName("Verify that the Simmons Crosstab view can be edited by adding data")
  @Description("The test creates a Simmons Crosstab view with base data. Edits it by adding 2 additional random data and verifies their presence in the table.")
  @Tag("Flakey")
  public void testEditCrosstabView() {

    // Verify that the SELECT_DATA_VARIABLE error appears
    System.out.println("Step 1: Verify that the SELECT_DATA_VARIABLE error appears");
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");

    // Assert that the error message is displayed
    System.out.println("Step 2: Check if the error message is displayed");
    Assert.assertTrue("An errors message should appear", editCrosstabPage.getActiveView().isErrorMessageDisplayed());

    // Assert that the error message content is what was expected
    System.out.println("Step 3: Verify the content of the error message");
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());

    // Navigate to DataByCategoryPanel
    System.out.println("Step 4: Navigate to DataByCategoryPanel through the LDB section");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    // Click on TECHNOLOGY category and select random data
    System.out.println("Step 5: Select random data from the TECHNOLOGY category");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);
    String data0 = dataByCategoryDropwDown.clickOnARandomDataResult();

    // Click on HOUSEHOLDS category and select random data
    System.out.println("Step 6: Select random data from the HOUSEHOLDS category");
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.HOUSEHOLDS);
    String data1 = dataByCategoryDropwDown.clickOnARandomDataResult();

    // Close the data selection panel
    System.out.println("Step 7: Close the data selection panel");
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    // Retrieve the checkbox container panel for verifying selections
    System.out.println("Step 8: Get the CheckboxCrosstabContainerPanel for further interaction");
    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();

    // Select the row if the data belongs to DEMOGRAPHICS
    System.out.println("Step 9: Select row checkbox for data0 if it contains 'DEMOGRAPHICS'");
    if (data0.contains("Demographics")) {
      checkbowContainerPanel.clickRowDataItem(0);
    }

    // Select the row if the data1 does not belong to DEMOGRAPHICS
    System.out.println("Step 10: Select column checkbox for data1 if it does not contain 'DEMOGRAPHICS'");
    if (!data1.contains("Demographics")) {
      checkbowContainerPanel.clickColumnDataItem(1);
    }

    // Verify row checkbox at index 0 is selected
    System.out.println("Step 11: Verify that row checkbox at index 0 is selected");
    verificationStep("Verify that row checkbox with index: 0 selected");
    Assert.assertTrue("Row checkbox with index: 0 should be selected", checkbowContainerPanel.isRowCheckboxSelected(0));

    // Verify column checkbox at index 1 is selected
    System.out.println("Step 12: Verify that column checkbox at index 1 is selected");
    verificationStep("Verify that column checkbox with index: 1 selected");
    Assert.assertTrue("Column checkbox with index: 1 should be selected", checkbowContainerPanel.isColumnCheckboxSelected(1));

    // Click Done and go back to the CrosstabPage
    System.out.println("Step 13: Click 'Done' to finish editing the Crosstab");
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();

    // Click Edit View again
    System.out.println("Step 14: Reopen the Edit View");
    editCrosstabPage = (EditCrosstabPage) crosstabPage.getToolbar().clickViewActions().clickEditView();

    // Go to the category Jobs and Employment and select random data
    System.out.println("Step 15: Select random data from JOBS_AND_EMPLOYMENT category");
    dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection().clickData().getBrowsePanel();
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    String data2 = dataByCategoryDropwDown.clickOnARandomDataResult();

    // If data2 does not belong to DEMOGRAPHICS, select row checkbox
    System.out.println("Step 16: Select row checkbox for data2 if it does not contain 'DEMOGRAPHICS'");
    if (!data2.contains("Demographics")) {
      System.out.println("The data does not contain 'DEMOGRAPHICS'");
      checkbowContainerPanel.clickRowDataItem(2);
    }

    // Go to the category AGE and select random data
    System.out.println("Step 17: Select random data from AGE category");
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);
    String data3 = dataByCategoryDropwDown.clickOnARandomDataResult();

    // If data3 does not belong to DEMOGRAPHICS, select row checkbox
    System.out.println("Step 18: Select row checkbox for data3 if it does not contain 'DEMOGRAPHICS'");
    if (!data3.contains("Demographics")) {
      checkbowContainerPanel.clickRowDataItem(3);
    }

    // Simulate a delay to allow for UI changes
    System.out.println("Step 19: Wait for 1 second for UI to reflect changes");
    editCrosstabPage.getLdbSection().clickData();
    sleep(1000);

    // Select row checkbox at index 2
    System.out.println("Step 20: Select row checkbox at index 2");
    checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
    checkbowContainerPanel.clickRowDataItem(2);

    // Verify row checkbox at index 2 is selected
    System.out.println("Step 21: Verify that row checkbox at index 2 is selected");
    verificationStep("Verify that row checkbox with index: 2 selected");
    Assert.assertTrue("Row checkbox with index: 2 should be selected", checkbowContainerPanel.isRowCheckboxSelected(2));

    // Verify column checkbox at index 3 is selected
    System.out.println("Step 22: Verify that column checkbox at index 3 is selected");
    verificationStep("Verify that column checkbox with index: 3 selected");
    Assert.assertTrue("Column checkbox with index: 3 should be selected", checkbowContainerPanel.isColumnCheckboxSelected(3));

    // Click Done to finalize
    System.out.println("Step 23: Click 'Done' to complete the edits");
    crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();

    // Get the CrosstabViewPanel to verify row and column header values
    System.out.println("Step 24: Get the CrosstabViewPanel to verify added data");
    CrosstabViewPanel viewPanel = crosstabPage.getActiveView();

    // Verify row element contains data2
    System.out.println("Step 25: Verify that row element contains data2");
    verificationStep("Verify that row element is appeared");
    Assert.assertTrue("Row element is missing", viewPanel.getRowHeaderValues().toString().contains(data2));

    // Verify column element contains data3
    System.out.println("Step 26: Verify that column element contains data3");
    verificationStep("Verify that column element is appeared");
    Assert.assertTrue("Column element is missing", viewPanel.getColumnHeaderValues().toString().contains(data3));
  }

}