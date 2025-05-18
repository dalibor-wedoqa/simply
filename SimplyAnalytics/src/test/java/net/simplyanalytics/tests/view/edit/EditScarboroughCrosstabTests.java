package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.view.ScarboroughCrosstabViewPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.ScarboroughCheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class EditScarboroughCrosstabTests extends TestBase {

  private EditScarboroughCrosstabPage editScarboroughCrosstabPage;
  private MapPage mapPage;
  
  @Before
  public void createProject() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView()
        .clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage
        .getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.CHICAGO);
  }

  @Test
  public void testClearAndSelectAllData() {
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editScarboroughCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();

    List<String> rows = new ArrayList<String>();
    List<String> columns = new ArrayList<String>();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for(int i = 0; i < 3; i++) {
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
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 3; i++) {
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
    
//    for(int i = 0; i < rows.size(); i++) {
//      verificationStep("Verify that row checkbox with index: " + i + " selected");
//      Assert.assertTrue("Row checkbox with index: " + i + " should be selected", checkbowContainerPanel.isRowCheckboxSelected(i));
//    }
//
//    for(int i = rows.size(); i < columns.size() + rows.size(); i++) {
//      verificationStep("Verify that column checkbox with index: " + i + " selected");
//      Assert.assertTrue("Column checkbox with index: " + i + " should be selected", checkbowContainerPanel.isColumnCheckboxSelected(i));
//    }
    for(String column : columns) {
      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
    }
    verificationStep("Verify that row checkbox selected");
    for(String row : rows) {
      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));
    }

    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) scarboroughCrosstabPage.getToolbar().clickViewActions().clickEditView();
    ScarboroughCheckboxContainerPanel containerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();
    containerPanel.clickClear();
    
    List<String> checkedRows = containerPanel.getAllCheckedDataRows();
    List<String> checkedColumns = containerPanel.getAllCheckedDataColumns();
    
    verificationStep("Verify that all row checkboxes are unchecked");
    Assert.assertTrue("Row for some data is still checked", checkedRows.isEmpty());
    
    verificationStep("Verify that all column checkboxes are unchecked");
    Assert.assertTrue("Column for some data is still checked", checkedColumns.isEmpty());
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editScarboroughCrosstabPage.getActiveView().getErrorMessage());
    
    containerPanel.clickSelectAll();
    
    checkedRows = containerPanel.getAllCheckedDataRows();
    checkedColumns = containerPanel.getAllCheckedDataColumns();
    
//    for(int i = 0; i < rows.size(); i++) {
//      verificationStep("Verify that row checkbox with index: " + i + " selected");
//      Assert.assertTrue("Row checkbox with index: " + i + " should be selected", checkbowContainerPanel.isRowCheckboxSelected(i));
//    }
//
//    for(int i = rows.size(); i < columns.size() + rows.size(); i++) {
//      verificationStep("Verify that column checkbox with index: " + i + " selected");
//      Assert.assertTrue("Column checkbox with index: " + i + " should be selected", checkbowContainerPanel.isColumnCheckboxSelected(i));
//    }

    for(String column : columns) {
      Assert.assertTrue("Column checkbox for data: " + column + " is not selected", checkbowContainerPanel.isColumnCheckboxSelected(column));
    }
    verificationStep("Verify that row checkbox selected");
    for(String row : rows) {
      Assert.assertTrue("Row checkbox for data: " + row + " is not selected", checkbowContainerPanel.isRowCheckboxSelected(row));
    }
  }
  
  @Test
  public void testEditCrosstabView() {
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editScarboroughCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);
    String data0 = dataByCategoryDropwDown.clickOnARandomDataResult();
    
    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    String data1 = dataByCategoryDropwDown.clickOnARandomDataResult();

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
    
    ScarboroughCheckboxContainerPanel checkbowContainerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();

    if(!data0.contains("Demographics")){
      checkbowContainerPanel.clickDataItem(0);
    }

    if(data1.contains("Demographics")){
      checkbowContainerPanel.clickDataItem(1);
    }

    verificationStep("Verify that column checkbox with index: 0 selected");
    Assert.assertTrue("Column checkbox with index: 0 should be selected", checkbowContainerPanel.isColumnCheckboxSelected(0));
    
    verificationStep("Verify that row checkbox with index: 1 selected");
    Assert.assertTrue("Row checkbox with index: 1 should be selected", checkbowContainerPanel.isRowCheckboxSelected(1));
    
    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) scarboroughCrosstabPage.getToolbar().clickViewActions().clickEditView();
    
    dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection().clickData().getBrowsePanel();

    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    String data2 = dataByCategoryDropwDown.clickOnARandomDataResult();

    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.HEALTH);
    String data3 = dataByCategoryDropwDown.clickOnARandomDataResult();

    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

    checkbowContainerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();

    if(!data2.contains("Demographics")){
      checkbowContainerPanel.clickDataItem(2);
    }
    if(data3.contains("Demographics")){
      checkbowContainerPanel.clickDataItem(3);
    }

    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);
    
    checkbowContainerPanel = editScarboroughCrosstabPage.getActiveView().getDataPanel();

    verificationStep("Verify that column checkbox with index: 2 selected");
    Assert.assertTrue("Column checkbox with index: 2 should be selected", checkbowContainerPanel.isColumnCheckboxSelected(2));
    
    verificationStep("Verify that row checkbox with index: 3 selected");
    Assert.assertTrue("Row checkbox with index: 3 should be selected", checkbowContainerPanel.isRowCheckboxSelected(3));
 
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    ScarboroughCrosstabViewPanel viewPanel = scarboroughCrosstabPage.getActiveView();

    verificationStep("Verify that column element is appeared");
    Assert.assertTrue("Column element is missing", viewPanel.getColumnHeaderValues().toString().contains(data2));
    
    verificationStep("Verify that row element is appeared");
    Assert.assertTrue("Row element is missing", viewPanel.getRowHeaderValues().toString().contains(data3));
  }
}
