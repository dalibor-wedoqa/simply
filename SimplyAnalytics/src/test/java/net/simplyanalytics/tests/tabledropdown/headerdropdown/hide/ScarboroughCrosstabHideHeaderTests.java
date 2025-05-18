package net.simplyanalytics.tests.tabledropdown.headerdropdown.hide;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.Location;
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

public class ScarboroughCrosstabHideHeaderTests extends TestBase {

  private EditScarboroughCrosstabPage editScarboroughCrosstabPage;
  private MapPage mapPage;
  ScarboroughCrosstabPage scarboroughCrosstabPage;
  
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
  }
  
  @Test
  public void testRowHeaderDataVariableViewMetadata() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }

    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);
    
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel = scarboroughCrosstabPage.getActiveView();
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) scarboroughCrosstabViewPanel.openRowHeaderDropdown(1).clickHideDataVariable();
    
    verificationStep("Verify that the data variable disappeared");
    List<String> rowHeaderValues = scarboroughCrosstabViewPanel.getRowHeaderValues();
    Assert.assertFalse("The data variable is still present",
        rowHeaderValues.contains(row.get(0)));
  }
  
  @Test
  public void testColumnHeaderDataVariableViewMetadata() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.LANGUAGE);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }

    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);

    scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel = scarboroughCrosstabPage.getActiveView();
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) scarboroughCrosstabViewPanel.openColumnHeaderDropdown(1).clickHideDataVariable();

    
    verificationStep("Verify that the data variable disappeared");
    List<String> columnHeaderValues = scarboroughCrosstabViewPanel.getColumnHeaderValues();
    Assert.assertFalse("The data variable is still present",
        columnHeaderValues.contains(column.get(0)));
  }
  
}
