package net.simplyanalytics.tests.view.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.CellDisplayType;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.view.ScarboroughCrosstabViewPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.ScarboroughCheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScarboroughCrosstabActionsTests extends TestBase {

  private EditScarboroughCrosstabPage editScarboroughCrosstabPage;

  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
            InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView()
            .clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage
            .getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.CHICAGO);
  }

  @Test
  public void testCreateScarboroughCrosstabViewWithLotOfData() {
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
            editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editScarboroughCrosstabPage.getActiveView().getErrorMessage());

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    List<String> columns = new ArrayList<String>();
    List<String> rows = new ArrayList<String>();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);

    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.GENDER);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.INCOME);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.EDUCATION);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.LANGUAGE);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.FAMILY_TYPE);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HOUSING);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.HEALTH);
    for(int i = 0; i < 5; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(data.contains("Demographics")){
        if(!columns.contains(data)) {
          columns.add(data);
        }
      } else {
        if (!rows.contains(data)) {
          rows.add(data);
        }
      }
    }

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    ScarboroughCheckboxContainerPanel checkbowContainerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();
    verificationStep("Verify that column checkbox selected");
    for(String column : columns) {
      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
    }
    verificationStep("Verify that row checkbox selected");
    for(String row : rows) {
      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));
    }

//    for(String data_stream : data_map){
///*      logger.info("Data: " + data_stream
//              + " has column:" + checkbowContainerPanel.isColumnCheckboxSelected(data_stream)
//              + "| has row:" + checkbowContainerPanel.isRowCheckboxSelected(data_stream));*/
//      if(checkbowContainerPanel.isColumnCheckboxSelected(data_stream))
//        columns.add(data_stream);
//      else if (checkbowContainerPanel.isRowCheckboxSelected(data_stream))
//        rows.add(data_stream);
//      else
//        Assert.assertTrue("Checkbox for " + data_stream + "not selected.",
//                checkbowContainerPanel.isColumnCheckboxSelected(data_stream) ^ checkbowContainerPanel.isRowCheckboxSelected(data_stream));
//    }
//
    verificationStep("Verify that selected location is Chicago, IL (DMA)");
    Assert.assertEquals("Location Chicago, IL (DMA) should be selected", DMAsLocation.CHICAGO.getFullLocationName(),
            editScarboroughCrosstabPage.getActiveView().getLocationsPanel().getSelectedElement());

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    sleep(1000);

    verificationStep("Verify that active location is Chicago, IL (DMA)");
    Assert.assertEquals("Location Chicago, IL (DMA) should be active", DMAsLocation.CHICAGO.getFullLocationName(), scarboroughCrosstabPage.getToolbar().getActiveLocation());
  }

  @Test
  public void testChangeCellDisplay() {
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    List<String> columns = new ArrayList<String>();
    List<String> rows = new ArrayList<String>();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    for(int i = 0; i < 5; i++) {
      String row = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!rows.contains(row))
        rows.add(row);
    }
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for(int i = 0; i < 5; i++) {
      String column = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!columns.contains(column))
        columns.add(column);
    }

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();

    ScarboroughCrosstabViewPanel viewPanel = scarboroughCrosstabPage.getActiveView();
    List<String[]> oldRow = viewPanel.getTableRowsVerticalDisplay().get(0);

    scarboroughCrosstabPage.getToolbar().openCellDisplayMenu().clickDisplayType(CellDisplayType.SAMPLE);

    verificationStep("Verify that Sample cell display type is active");
    Assert.assertEquals("Sample cell display type should be active",
            CellDisplayType.SAMPLE, scarboroughCrosstabPage.getToolbar().getActiveCellsDisplay());
    List<String[]> newRow = scarboroughCrosstabPage.getActiveView().getTableRowsSampleDisplay().get(0);

    verificationStep("Verify that Sample cell display type displayed");
    Assert.assertFalse("Sample cell displaytype should be displayed", Arrays.equals(oldRow.get(0), newRow.get(0)));

    scarboroughCrosstabPage.getToolbar().openCellDisplayMenu().clickDisplayType(CellDisplayType.VERTICAL);

    verificationStep("Verify that Vertical cell display type is active");
    Assert.assertEquals("Vertical cell displaytype should be active",
            CellDisplayType.VERTICAL, scarboroughCrosstabPage.getToolbar().getActiveCellsDisplay());

    verificationStep("Verify that Sample cell display type displayed");
    Assert.assertFalse("Sample cell displaytype should be displayed",
            Arrays.equals(newRow.get(0), scarboroughCrosstabPage.getActiveView().getTableRowsSampleDisplay().get(0).get(0)));

  }

  @Test
  public void testChangeRowOrder() {
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> rows = new ArrayList<String>();

    while(rows.size() < 3) {
      String row = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(!rows.contains(row)) {
        rows.add(row);
      }
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 3 ; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
    ScarboroughCheckboxContainerPanel dataPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();

    for (String rowName: rows) {
      if(!(dataPanel.isRowCheckboxSelected(rowName))){
        dataPanel.getRowByName(rowName).clickRowCheckbox();
      }
    }

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();

    logger.info("Row values");
    ScarboroughCrosstabViewPanel viewPanel = scarboroughCrosstabPage.getActiveView();
    List<String> oldRowValues = viewPanel.getRowHeaderValues();
    viewPanel.moveRowElementUp(3);
    List<String> newRowValues = viewPanel.getRowHeaderValues();

    verificationStep("Verify that row element moved up");
    Assert.assertNotEquals("Row element with index 3 should moved up", oldRowValues, newRowValues);

    viewPanel.moveRowElementDown(1);
    verificationStep("Verify that row element moved down");
    Assert.assertNotEquals("Row element with index 1 should moved down", newRowValues.equals(viewPanel.getRowHeaderValues()));
  }

  @Test
  @DisplayName("Verify that in Scarborough Crosstab Table the order of columns is permutable")
  @Description("The test creates a Scarborough Crosstab Table with three columns. Then moves one column from left to right and then from right to left.")
  @Tag("Flaky")
  public void testChangeColumnOrder() {
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    List<String> rows = new ArrayList<String>();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    while(rows.size() < 3) {
      String row = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(!rows.contains(row)) {
        rows.add(row);
      }
    }

    List<String> columns = new ArrayList<String>();
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);
    while(columns.size() < 3) {
      String column = dataByCategoryDropwDown.clickOnARandomDataResult();
      if(!columns.contains(column)) {
        columns.add(column);
      }
    }

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();

    logger.info("Column values");
    ScarboroughCrosstabViewPanel viewPanel = scarboroughCrosstabPage.getActiveView();
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
  @DisplayName("Verify that Scarborough Crosstab view can be deleted")
  @Description("The test creates a Scarborough Crosstab view and verifies that it can be deleted via view actions, delete view by getting a list of all views from the View Toolbar.")
  @Tag("Flaky")
  public void testDeleteCrosstabView() {
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    dataByCategoryDropwDown.clickOnARandomDataResult();

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);
    dataByCategoryDropwDown.clickOnARandomDataResult();

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();

    ProjectSettingsPage projectSettingPage = ((BaseViewPage) scarboroughCrosstabPage.getToolbar().clickViewActions().clickDeleteView())
            .getHeaderSection().clickProjectSettings();

    List<ViewType> views = new ArrayList<>();
    views.add(ViewType.COMPARISON_REPORT);
    views.add(ViewType.QUICK_REPORT);
    views.add(ViewType.MAP);
    views.add(ViewType.RANKING);

    List<String> expectedViews = new ArrayList<>();
    views.stream().filter(v -> !v.equals(ViewType.SCARBOROUGH_CROSSTAB_TABLE)).forEach(v -> expectedViews.add(v.getDefaultName()));

    verificationStep("Verify that the deleted view and only the deleted view disappears");
    Assert.assertEquals("List of the views is not the expected", expectedViews,
            projectSettingPage.getProjectSettingsHeader().clickManageViewsButton().getAllViews());
  }

  @Test
  public void testLocationsTooltip() {
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    List<String> columns = new ArrayList<String>();
    List<String> rows = new ArrayList<String>();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);

    for(int i = 0; i < 5; i++) {
      String row = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!rows.contains(row))
        rows.add(row);
    }

    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for(int i = 0; i < 5; i++) {
      String column = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!columns.contains(column))
        columns.add(column);
    }

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();

    verificationStep("Verify that Locations tooltip appears after hovering the Locations tab");
    Assert.assertEquals("Tooltip content is not the expected",
            "A Scarborough Crosstab Table can only have one location. "
                    + "You can create a new Scarborough Crosstab view and select a different location for this view.",
            scarboroughCrosstabPage.getLdbSection().getLocationsTooltipContent());
  }

}
