package net.simplyanalytics.tests.importtest;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DatasetSearchImportedDataPanel;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImportFavoriteDataTest extends TestBase {
  private MapPage mapPage;
  private DataByDataFolderPanel dataByDataFolderPanel;
  private DataTab dataTab;
  
  
  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    ProjectSettingsPage projectSettingsPage = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickProjectSettings();
    NewProjectLocationWindow newProjectLocationWindow = projectSettingsPage.getHeaderSection()
        .clickNewProject();
    mapPage = newProjectLocationWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    dataTab = mapPage.getLdbSection().clickData();
    dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
  }
  
  @Test
  public void testFavoriteDataVariableSelection() {    
    DatasetSearchImportedDataPanel importedDataPanel = dataByDataFolderPanel.clickRandomImportedDataset().getDatasetSearchResultPanel();
    
    String search = importedDataPanel.clickOnRandomData();
    
    verificationStep("Verify that actual data variable contains the searched text");
    Assert.assertTrue("The actual data variable should containing the searched text",
        mapPage.getToolbar().getNameOfActiveDataVariable().contains(search));
    
    RecentFavoriteMenu recentDataMenu = dataTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentData = 
        (RecentFavoriteMenuItemWithSubMenu) recentDataMenu
        .getNthMenuItem(1);
    recentData.clickOnMoreOptions().clickAddToFavorites();
    recentDataMenu.clickCloseButton();
    
    mapPage = new MapPage(driver);
    
    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The Favorite Data Variables button should be present",
        dataTab.isFavoritesPresent());
    
  }
  
  @Test
  public void testFavoriteDataVaribleMetaDataPresense() {
    DatasetSearchImportedDataPanel importedDataPanel = dataByDataFolderPanel.clickRandomImportedDataset().getDatasetSearchResultPanel();
    String search = importedDataPanel.clickOnRandomData();    
    
    verificationStep("Verify that actual data variable contains the searched text");
    Assert.assertTrue("The actual data variable should contain the searched text",
        mapPage.getToolbar().getNameOfActiveDataVariable().contains(search));
    
    RecentFavoriteMenu recentDataMenu = dataTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentData = 
        (RecentFavoriteMenuItemWithSubMenu) recentDataMenu
        .getNthMenuItem(1);
    recentData.clickOnMoreOptions().clickAddToFavorites();
    recentDataMenu.clickCloseButton();
    
    mapPage = new MapPage(driver);
    
    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The Favorite Data Variables button should be present",
        dataTab.isFavoritesPresent());
    RecentFavoriteMenuItemWithSubMenu dataVar = (RecentFavoriteMenuItemWithSubMenu) dataTab
        .clickFavorites().getNthMenuItem(1);
    String dataVarName = dataVar.getTitle();
    MetadataWindow metadataWindow = dataVar.clickOnMoreOptions().clickViewMetadata();
    
    verificationStep("Verify that the correct metadata appears on the Metadata window");
    Assert.assertEquals(
        "The data variable name in favorites should be the same as the data variable in Variable "
            + "MetaData Window",
            metadataWindow.getMetadataValue("Name") + ", " + metadataWindow.getMetadataValue("Year"),
            dataVarName);
  }
  
  @After
  public void after() {
    unlockUser();
  }
  
}
