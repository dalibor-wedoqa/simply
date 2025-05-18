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
import net.simplyanalytics.enums.Location;
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
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class CrosstabHideHeaderTests extends TestBase {
  
  private EditCrosstabPage editCrosstabPage;
  private MapPage mapPage;
  CrosstabPage crosstabPage;
  
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
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.TECHNOLOGY);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }

    editCrosstabPage.getLdbSection().clickData();
    
    crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    
    CrosstabViewPanel crosstabViewPanel = crosstabPage.getActiveView();
    crosstabPage = (CrosstabPage) crosstabViewPanel.openRowHeaderDropdown(1).clickHideDataVariable();
    
    verificationStep("Verify that the data variable disappeared");
    List<String> rowHeaderValues = crosstabViewPanel.getRowHeaderValues();
    Assert.assertFalse("The data variable is still present",
        rowHeaderValues.contains(row.get(0)));
  }
  
  @Test
  public void testColumnHeaderDataVariableViewMetadata() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.TECHNOLOGY);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }

    editCrosstabPage.getLdbSection().clickData();
    
    crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    
    CrosstabViewPanel crosstabViewPanel = crosstabPage.getActiveView();
    crosstabPage = (CrosstabPage) crosstabViewPanel.openColumnHeaderDropdown(1).clickHideDataVariable();
    
    verificationStep("Verify that the data variable disappeared");
    List<String> columnHeaderValues = crosstabViewPanel.getColumnHeaderValues();
    Assert.assertFalse("The data variable is still present",
        columnHeaderValues.contains(column.get(0)));
  }

}
