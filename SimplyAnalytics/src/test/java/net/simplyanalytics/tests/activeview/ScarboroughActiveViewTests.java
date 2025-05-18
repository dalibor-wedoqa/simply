package net.simplyanalytics.tests.activeview;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
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
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScarboroughActiveViewTests extends TestBase {

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

  }
  
  @Test
  @DisplayName("Verify that data can be drag and dropped between columns and rows in the Scarborough Crosstab table")
  @Description("The test creates the Scarborough Crosstab view with random data. Selects an element from column and drags it into the row, after which verifies its removal from column and presence in the row. The same is done with a row element.")
  public void testDragAndDropRowToColumn() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = ((ScarboroughLocationsTab) startBySelectingPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.LOS_ANGELES);
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editScarboroughCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {

      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!column.contains(data))
      column.add(data);
      
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!row.contains(data))
      row.add(data);
    }
  
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
    
    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel viewPanel = scarboroughCrosstabPage.getActiveView();
    
    column.remove(column.size()-1);
    
    for(String columnElement : column) {
      viewPanel.moveColumnElement(columnElement, -300, 150);
      row.add(0, columnElement);
    }
    logger.info("Elements are moved");
    verificationStep("Verify that there is only one column header value");
    Assert.assertEquals("Should be only one column header value", viewPanel.getColumnHeaderValues().size(), 2);
      
    row.remove(row.size()-1);
    
    for(String rowElement : row) {
      viewPanel.moveRowElement(rowElement, 300, -150);
    }
    logger.info("Elements are moved");
    verificationStep("Verify that there is only one row header value");
    Assert.assertEquals("Should be only one row header value", viewPanel.getRowHeaderValues().size(), 2);
  }
  
  @Test
  public void testDataLimitMessage() throws Exception {

    //TODO: the test does not take into consideration the type of data added, and the column x row does not always gives the error. Need to add exact calculation e.g.

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = ((ScarboroughLocationsTab) startBySelectingPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.LOS_ANGELES);
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScarboroughCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editScarboroughCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.INCOME);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.EDUCATION);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.LANGUAGE);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSING);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HEALTH);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
  
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
    
    verificationStep("Verify that the \"" + EditViewWarning.CROSSTABLIMITMESSAGE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScarboroughCrosstabPage.getActiveView().isLimitErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.CROSSTABLIMITMESSAGE,
        editScarboroughCrosstabPage.getActiveView().getLimitErrorMessage());
  }
}
