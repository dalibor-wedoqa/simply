package net.simplyanalytics.tests;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageProjectLdbPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.sections.view.map.EditLegendPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditProjectVariablesTests extends TestBase {

  private NewProjectLocationWindow createNewProjectWindow;
  private List<String> selectedDatas;
  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private DataByCategoryDropwDown dataByCategoryBaseWindow;
  private MapToolbar mapToolbar;
  private final int numberOfCategories = 8;
  private EditLegendPanel editLegendPanel;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  /**
   * . Steps: Login with valid institution and valid password Click sign in as
   * quest Click Create Project with empty location and Click Got it Data by
   * category panel is open Click Income data Choose a data results Click close
   * and Manage Project Page should Open Remove data item Choose a location, map
   * page should open At legend panel click edit and change the number of
   * categories Expected result: Verify that new selected data are displayed at
   * the Manage Project Page and that the number of categories at the legend panel
   * is expected
   */
  // TODO create proper tests for map view
  @Test
  public void testChangeDataLocationNumberOfCategories() {
    NewViewPage newViewPage = createNewProjectWindow.clickCreateButtonWithEmptyLocation();
    
    MapPage mapPage = (MapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();
    
    dataByCategoryBaseWindow = ((DataByCategoryPanel) mapPage.getLdbSection().getDataTab()
    												.getBrowsePanel()).clickOnACategoryData(CategoryData.INCOME);

    dataByCategoryBaseWindow.clickOnADataResult(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024);
    selectedDatas = dataByCategoryBaseWindow.getDataSearchResultsPanel().allCheckedResults();
    
    mapPage = (MapPage) dataByCategoryBaseWindow.clickClose(Page.MAP_VIEW);

    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    ManageProjectLdbPanel manageProjectLdbPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickData();

    verificationStep("Verify that the selected data variables appear in the \"Locations,"
    					+ " Data, Business\" panel");
    
    //insert additional " " in selected string 
    if(selectedDatas.get(0).contains("|")) {
    	int i = selectedDatas.get(0).indexOf("|");
    	selectedDatas.set(0, selectedDatas.get(0).substring(0,i+1)+" "+selectedDatas.get(0).substring(i+1));
    }
    
    Assert.assertTrue("All checked elements are not at the Manage project Data list",
        manageProjectLdbPanel.getItemsName()
            .containsAll(selectedDatas));

    projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickData()
        .clickX(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024.getFullName());
    mapPage = (MapPage) projectSettingsPage.getViewChooserSection().clickView(ViewType.MAP.getDefaultName());
    mapPage = mapPage.getLdbSection()
        .clickLocationsEnterSearchAndChooseALocation("Chi", "Chicago, IL");
    mapToolbar = mapPage.getToolbar();
    mapToolbar.openDataVariableListMenu().clickDataVariable(firstDefaultDataVariable);
    
    LegendPanel legendPanel = mapPage.getActiveView().getLegend(); 
    editLegendPanel = legendPanel.clickEdit();
    editLegendPanel.changeNumberOfCategories(numberOfCategories);

    verificationStep("Verify that the number of categories present in the legend panel is 8");
    Assert.assertEquals("The number of categories in the Legend Panel is not the expected",
        editLegendPanel.numberOfCategoriesElements(), numberOfCategories);
  }

}
