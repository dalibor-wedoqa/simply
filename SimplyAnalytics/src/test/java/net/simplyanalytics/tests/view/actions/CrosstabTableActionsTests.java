package net.simplyanalytics.tests.view.actions;

import java.util.*;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.CrosstabViewPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxCrosstabContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import javax.swing.*;

public class CrosstabTableActionsTests extends TestBase {

  private EditCrosstabPage editCrosstabPage;

  @Before
  public void createProject() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
            InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);
    //ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    //projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
  }

  @Test
  @DisplayName("Verify that Simmons Crosstab view can be created with a lot of data")
  @Description("The test creates a Simmons Crosstab view with at least 45 data value and verifies their present in the created table.")
  @Tag("TCE/Fix")
  public void testCreateCrosstabViewWithLotOfData() {
    // Verification Step

    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    System.out.println("Verifying error message presence: " + EditViewWarning.SELECT_DATA_VARIABLE);
    Assert.assertTrue("An errors message should appear",
            editCrosstabPage.getActiveView().isErrorMessageDisplayed());

    System.out.println("Checking if the error message matches expected: " + EditViewWarning.SELECT_DATA_VARIABLE);
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();
    System.out.println("DataByCategoryPanel initialized: " + dataByCategoryPanel);

    List<String> columns = new ArrayList<String>();
    List<String> rows = new ArrayList<String>();
    System.out.println("Initialized empty lists for columns and rows");

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HEALTH);
    System.out.println("Clicked on HEALTH category");
    int dataNum = 5;
    String dataYear = "2023";
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from HEALTH category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    System.out.println("Clicked on HOUSEHOLDS category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from HOUSEHOLDS category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.INCOME);
    System.out.println("Clicked on INCOME category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from INCOME category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.FINANCE);
    System.out.println("Clicked on FINANCE category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from FINANCE category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    System.out.println("Clicked on JOBS_AND_EMPLOYMENT category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from JOBS_AND_EMPLOYMENT category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.GENDER);
    System.out.println("Clicked on GENDER category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from GENDER category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);
    System.out.println("Clicked on AGE category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from AGE category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.EDUCATION);
    System.out.println("Clicked on EDUCATION category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from EDUCATION category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.TECHNOLOGY);
    System.out.println("Clicked on TECHNOLOGY category");
    for (int i = 0; i < dataNum; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult() + dataYear;
      System.out.println("Random data from TECHNOLOGY category: " + data);
      if (data.contains("Demographics")) {
        if (!columns.contains(data)) {
          columns.add(data);
          System.out.println("Added to columns: " + data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
          System.out.println("Added to rows: " + data);
        }
      }
    }

    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);
    System.out.println("Closed category selection, returned to EditCrosstabPage");

    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
    System.out.println("CheckboxCrosstabContainerPanel initialized");

    // Verification Step for Row Checkboxes
    verificationStep("Verify that row checkbox is selected. Total rows: " + rows.size());
    for (String row : rows) {
      System.out.println("Checking if row checkbox is selected for: " + row);
      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel
              .isRowCheckboxSelected(row));
    }

    // Verification Step for Column Checkboxes
    verificationStep("Verify that the column checkbox is selected. Total columns: " + columns.size());
    for (String column : columns) {
      System.out.println("Checking if column checkbox is selected for: " + column);
      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
    }

    // Verification Step for Selected Location
    verificationStep("Verify that selected location is USA");
    System.out.println("Checking if USA matches " + editCrosstabPage.getActiveView().getLocationsPanel().getSelectedElement());
    Assert.assertEquals("Location USA should be selected", "USA", editCrosstabPage.getActiveView().getLocationsPanel().getSelectedElement());

    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    CrosstabViewPanel viewPanel = crosstabPage.getActiveView();

    sleep(1000);
    System.out.println("About to retrieve column header values");
    List<String> columnValues = viewPanel.collectColumnHeaderValues();
    System.out.println("Retrieved column header values: " + columnValues);
    System.out.println("About to retrieve row header values");
    List<String> rowValues = viewPanel.collectRowHeaderValues();
    System.out.println("Retrieved row header values: " + rowValues);

    System.out.println("Rows list: " + rows.toString());
    System.out.println("Row values list: " + rowValues.toString());
    System.out.println("Columns list: " + columns.toString());
    System.out.println("Column values list: " + columnValues.toString());

    System.out.println("Verify that row elements match the expected values");
    Assert.assertEquals("Row elements are not the expected", rows.toString().replaceAll(",", ""), rowValues.toString().replaceAll(",", ""));

    System.out.println("Verify that column elements match the expected values");
    Assert.assertEquals("Column elements are not the expected", columns.toString().replaceAll(",", ""), columnValues.toString().replaceAll(",", ""));
  }

  @Test
  public void testChangeCellDisplay() {
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
            editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.INCOME);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.LANGUAGE);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
    for (int i = 0; i < 7; i++)
      checkbowContainerPanel.clickRowDataItem(i);

    verificationStep("Verify that selected location is USA");
    Assert.assertEquals("Location USA should be selected", "USA", editCrosstabPage.getActiveView().getLocationsPanel().getSelectedElement());

    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    CrosstabViewPanel viewPanel = crosstabPage.getActiveView();
    List<String[]> oldRow = viewPanel.getTableRowsVerticalDisplay().get(0);

    crosstabPage.getToolbar().openCellDisplayMenu().clickDisplayType(CellDisplayType.SAMPLE);

    verificationStep("Verify that Sample cell display type is active");
    Assert.assertEquals("Sample cell displaytype should be active",
            CellDisplayType.SAMPLE, crosstabPage.getToolbar().getActiveCellsDisplay());
    List<String[]> newRow = crosstabPage.getActiveView().getTableRowsSampleDisplay().get(0);

    verificationStep("Verify that Sample cell display type displayed");
    Assert.assertFalse("Sample cell displaytype should be displayed", Arrays.equals(oldRow.get(0), newRow.get(0)));

    crosstabPage.getToolbar().openCellDisplayMenu().clickDisplayType(CellDisplayType.VERTICAL);

    verificationStep("Verify that Vertical cell display type is active");
    Assert.assertEquals("Vertical cell displaytype should be active",
            CellDisplayType.VERTICAL, crosstabPage.getToolbar().getActiveCellsDisplay());

    verificationStep("Verify that Sample cell display type displayed");
    Assert.assertFalse("Sample cell displaytype should be displayed",
            Arrays.equals(newRow.get(0), crosstabPage.getActiveView().getTableRowsSampleDisplay().get(0).get(0)));

  }

  @Test
  public void testChangeRowOrder() {
    System.out.println("Step: Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
            editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    System.out.println("Verified: Error message is displayed");

    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());
    System.out.println("Verified: Error message matches expected value");

    System.out.println("Step: Access DataByCategoryPanel");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    System.out.println("Step: Click on category data - TECHNOLOGY");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.TECHNOLOGY);
    for (int i = 0; i < 3; i++) {
      System.out.println("Clicking on random data result in TECHNOLOGY category, iteration: " + i);
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Step: Click on category data - HOUSEHOLDS");
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for (int i = 0; i < 3; i++) {
      System.out.println("Clicking on random data result in HOUSEHOLDS category, iteration: " + i);
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Step: Close the DataByCategoryDropwDown and navigate to EditCrosstabPage");
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    System.out.println("Step: Retrieve CheckboxCrosstabContainerPanel");
    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();

    for (int i = 0; i < 3; i++) {
      System.out.println("Verifying row checkbox with index: " + i + " is selected");
      verificationStep("Verify that row checkbox with index: " + i + " selected");
      Assert.assertTrue("Row checkbox with index: " + i + " should be selected", checkbowContainerPanel.isRowCheckboxSelected(i));
    }

    System.out.println("Step: Click Done to navigate to CrosstabPage");
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();

    System.out.println("Step: Retrieve old row header values");
    logger.info("Row values");
    CrosstabViewPanel viewPanel = crosstabPage.getActiveView();
    List<String> oldRowValues = viewPanel.getRowHeaderValues();
    System.out.println("Old Row Values: " + oldRowValues);

    System.out.println("Step: Move row element with index 3 up");
    viewPanel.moveRowElementUp(3);
    List<String> newRowValues = viewPanel.getRowHeaderValues();
    System.out.println("New Row Values after moving index 3 up: " + newRowValues);

    verificationStep("Verify that row element moved up");
    Assert.assertNotEquals("Row element with index 3 should moved up", oldRowValues, newRowValues);

    System.out.println("Step: Move row element with index 1 down");
    viewPanel.moveRowElementDown(1);
    System.out.println("New Row Values after moving index 1 down: " + viewPanel.getRowHeaderValues());

    verificationStep("Verify that row element moved down");
    Assert.assertNotEquals("Row element with index 1 should moved down", newRowValues.equals(viewPanel.getRowHeaderValues()));
  }


  @Test
  @DisplayName("Verify that in Simmons Crosstab view table the order of columns can be changed")
  @Description("The test creates a Simmons Crosstab view and changes the order of a column by moving it left and then moving it right.")
  @Tag("Flaky")
  public void testChangeColumnOrder() {
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
            editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.TECHNOLOGY);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for (int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();

    verificationStep("Verify that row checkbox with index: 0 selected");
    Assert.assertTrue("Row checkbox with index: 0 should be selected", checkbowContainerPanel.isRowCheckboxSelected(0));

    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    logger.info("Column values");
    CrosstabViewPanel viewPanel = crosstabPage.getActiveView();
    List<String> oldColumnValues = viewPanel.getColumnHeaderValues();
    viewPanel.moveColumnElementLeft(3);
    List<String> newColumnValues = viewPanel.getColumnHeaderValues();

    verificationStep("Verify that column element moved left");
    Assert.assertNotEquals("Column element with index 3 should moved left", oldColumnValues, newColumnValues);

    viewPanel.moveColumnElementRight(1);
    verificationStep("Verify that column element moved right");
    Assert.assertNotEquals("Column element with index 1 should moved right", newColumnValues.equals(viewPanel.getColumnHeaderValues()));
  }

  @Test
  public void testDeleteCrosstabView() {
    System.out.println("Step 1: Verifying that the SELECT_DATA_VARIABLE error appears");

    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    System.out.println("Expected error message: " + EditViewWarning.SELECT_DATA_VARIABLE);

    boolean isErrorDisplayed = editCrosstabPage.getActiveView().isErrorMessageDisplayed();
    System.out.println("Is error message displayed? " + isErrorDisplayed);
    Assert.assertTrue("An errors message should appear", isErrorDisplayed);

    String actualErrorMessage = editCrosstabPage.getActiveView().getErrorMessage();
    System.out.println("Actual error message displayed: " + actualErrorMessage);
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE, actualErrorMessage);

    System.out.println("Step 2: Navigating to DataByCategoryPanel");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection().clickData().getBrowsePanel();
    System.out.println("DataByCategoryPanel obtained: " + dataByCategoryPanel);

    System.out.println("Step 3: Selecting TECHNOLOGY category");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);
    System.out.println("TECHNOLOGY dropdown obtained: " + dataByCategoryDropwDown);

    System.out.println("Step 4: Clicking a random data result for TECHNOLOGY");
    dataByCategoryDropwDown.clickOnARandomDataResult();

    System.out.println("Step 5: Selecting HOUSEHOLDS category");
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.HOUSEHOLDS);
    System.out.println("HOUSEHOLDS dropdown obtained: " + dataByCategoryDropwDown);

    System.out.println("Step 6: Clicking a random data result for HOUSEHOLDS");
    dataByCategoryDropwDown.clickOnARandomDataResult();

    System.out.println("Step 7: Closing dropdown and returning to EditCrosstabPage");
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);
    System.out.println("Returned to EditCrosstabPage: " + editCrosstabPage);

    System.out.println("Step 8: Getting CheckboxCrosstabContainerPanel from active view");
    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
    System.out.println("CheckboxCrosstabContainerPanel obtained: " + checkbowContainerPanel);

    System.out.println("Step 9: Verifying row checkbox with index 0 is selected");
    verificationStep("Verify that row checkbox with index: 0 selected");
    boolean isRowSelected = checkbowContainerPanel.isRowCheckboxSelected(0);
    System.out.println("Row checkbox at index 0 selected? " + isRowSelected);
    Assert.assertTrue("Row checkbox with index: 0 should be selected", isRowSelected);

    System.out.println("Step 10: Verifying column checkbox with index 1 is selected");
    verificationStep("Verify that column checkbox with index: 1 selected");
    boolean isColSelected = checkbowContainerPanel.isColumnCheckboxSelected(1);
    System.out.println("Column checkbox at index 1 selected? " + isColSelected);
    Assert.assertTrue("Column checkbox with index: 1 should be selected", isColSelected);

    System.out.println("Step 11: Clicking Done and navigating to CrosstabPage");
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    System.out.println("CrosstabPage obtained: " + crosstabPage);

    System.out.println("Step 12: Navigating to ProjectSettingsPage via toolbar > view actions > delete view");
    ProjectSettingsPage projectSettingPage = ((BaseViewPage) crosstabPage.getToolbar().clickViewActions().clickDeleteView())
            .getHeaderSection().clickProjectSettings();
    System.out.println("ProjectSettingsPage obtained: " + projectSettingPage);

    System.out.println("Step 13: Creating list of expected views (excluding CROSSTAB_TABLE)");
    List<ViewType> views = new ArrayList<>();
    views.add(ViewType.COMPARISON_REPORT);
    views.add(ViewType.QUICK_REPORT);
    views.add(ViewType.MAP);
    views.add(ViewType.RANKING);

    List<String> expectedViews = new ArrayList<>();
    views.stream().filter(v -> !v.equals(ViewType.CROSSTAB_TABLE)).forEach(v -> expectedViews.add(v.getDefaultName()));
    System.out.println("Expected views after deletion: " + expectedViews);

    System.out.println("Step 14: Verifying deleted view is removed from view list");
    verificationStep("Verify that the deleted view and only the deleted view disappears");
    List<String> actualViews = projectSettingPage.getProjectSettingsHeader().clickManageViewsButton().getAllViews();
    System.out.println("Actual views list from UI: " + actualViews);
    Assert.assertEquals("List of the views is not the expected", expectedViews, actualViews);
  }
}
