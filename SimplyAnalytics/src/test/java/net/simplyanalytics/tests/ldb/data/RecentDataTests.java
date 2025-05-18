package net.simplyanalytics.tests.ldb.data;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecentDataTests extends TestBase {
  
  private NewProjectVariablesWindow createNewProjectVariablesWindow;
  
  /**
   * Signing in and creating new project.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    createNewProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    
  }
  
  @Test
  public void testRecentMenuPresenceAfterSelectingOneFromDatasets() {
    NewViewPage newViewPage = createNewProjectVariablesWindow
        .clickCreateProjectWithoutSeedVariables();
    MapPage mapPage = (MapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    
    verificationStep("Verify that the recent data variables button is not present");
    Assert.assertFalse("The recent data variables button should not be present",
        dataTab.isRecentPresent());
    
    dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDataFolderPanel dataByDatasetPanel = dataTab.openYearDropdown().clickYear("2021");
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel
        .clickDataset(Dataset.COMMUNITY_DEMOGRAPHICS);
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Population, Age, Sex");
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Population");
   // dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel().clickFolder("Total");
    dataByDatasetDropDown.getDatasetSearchResultPanel()
        .clickOnDataVariable(DataVariable.HASHTAG_TOTAL_POPULATION_2017);
    dataByDatasetDropDown.clickClose(Page.MAP_VIEW);
    dataTab = new DataTab(driver, DataBrowseType.DATA_FOLDER);
    
    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The recent data variables button should be present",
        dataTab.isRecentPresent());
  }
  
  @Test
  public void testRecentDataVariablesAfterTheProjectIsCreated() {
    final List<DataVariable> dataVariables = createNewProjectVariablesWindow
        .getSelectedDataVariables();
    
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    
    List<RecentFavoriteMenuItem> recentDataVariables = dataTab.clickRecent().getMenuItems();
    
    verificationStep(
        "Verify that the recent data variable menu contains the active data variables");
    Assert.assertEquals(
        "The recent data variables count should match the number of data variables selected in the "
          + "create project modal",
        dataVariables.size(), recentDataVariables.size());
    
    for (RecentFavoriteMenuItem recentData : recentDataVariables) {
      String title = recentData.getTitle();
      Assert.assertTrue(
          "The data variable names in recent searches should be match with data variables added in "
            + "create project modal",
          dataVariables.stream().anyMatch(item -> title.equals(item.getFullName())));
    }
  }
  
  @Test
  public void testRecentDataVariableSelection() {
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    
    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The Recent Data Variables button should be present",
        dataTab.isRecentPresent());
    
    List<RecentFavoriteMenuItem> dataVars;
    dataVars = dataTab.clickRecent().getMenuItems();
    
    for (RecentFavoriteMenuItem data : dataVars) {
      data.clickToSelect();
      verificationStep("Verify that the data variables is now the active data variable");
      Assert.assertEquals(
          "The data variable name in recent menu should be the same as the data variable selected "
            + "on Map Page",
          mapPage.getToolbar().getActiveDataVariable().getFullName(), data.getTitle());
    }
  }
  
  @Test
  public void testRecentDataVaribleMetaDataPresense() {
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    
    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The Recent Data Variables button should be present",
        dataTab.isRecentPresent());
    
    RecentFavoriteMenuItemWithSubMenu dataVar = (RecentFavoriteMenuItemWithSubMenu) dataTab
        .clickRecent().getNthMenuItem(1);
    String dataVarName = dataVar.getTitle();
    MetadataWindow metadataWindow = dataVar.clickOnMoreOptions().clickViewMetadata();
    
    verificationStep("Verify that the correct metadata appears on the Metadata window");
    Assert.assertEquals(
        "The data variable name in recent menu should be the same as the data variable in Variable "
          + "MetaData Window",
        metadataWindow.getMetadataValue("Name") + ", " + metadataWindow.getMetadataValue("Year"),
        dataVarName);
  }
  
  @Test
  public void testRemoveFromRecent() {
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    
    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The recent data variables button should be present",
        dataTab.isRecentPresent());
    
    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickRecent();
    List<RecentFavoriteMenuItem> recentDataVars = recentFavoriteMenu.getMenuItems();
    final int recentSizeBefore = recentDataVars.size();
    
    RecentFavoriteMenuItemWithSubMenu menuItem = (RecentFavoriteMenuItemWithSubMenu) recentDataVars
        .get(recentDataVars.size() - 1);
    menuItem.clickOnMoreOptions().clickRemoveFromRecentList();
    
    recentFavoriteMenu.clickCloseButton();
    recentFavoriteMenu = dataTab.clickRecent();
    recentDataVars = recentFavoriteMenu.getMenuItems();
    
    verificationStep("Verify that the number of recent data variables is reduced by one");
    Assert.assertEquals("The number of recent data variables should be decreased by one",
        recentSizeBefore - 1, recentDataVars.size());
  }
  
}
