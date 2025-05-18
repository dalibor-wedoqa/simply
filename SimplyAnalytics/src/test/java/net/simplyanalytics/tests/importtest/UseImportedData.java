package net.simplyanalytics.tests.importtest;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.header.Header;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ManageWindow;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByImportedDataseDropdown;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UseImportedData extends TestBase {
  private Header header;
  private ProjectSettingsPage projectSettingsPage;
  private ManageWindow manageWindow;
  private MapPage mapPage;
  
  
  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    projectSettingsPage = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickProjectSettings();
  }
  
  @Test
  public void clearProjects() {
    header = projectSettingsPage.getHeaderSection(); 
    while(!header.getNameOfCurrentProject().contains("10")) {
      projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().clickDeleteProject().clickDelete();
      if (BasePage.isPresent(NewProjectLocationWindow.class, driver)) {
        projectSettingsPage.getHeaderSection()
                .clickNewProject().createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
      }
      else {
          projectSettingsPage.getHeaderSection().clickProjectSettings();
      }
    }
  }
  
  @Test
  public void testImportedData() {  
    NewProjectLocationWindow newProjectLocationWindow = projectSettingsPage.getHeaderSection()
        .clickNewProject();
    mapPage = newProjectLocationWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    header = mapPage.getHeaderSection();   
    manageWindow = header.clickUser().clickManageImport();
    String firstImportedDataset = manageWindow.getFirstDataSetName();
    manageWindow.clickClose();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByImportedDataseDropdown dataByImportedDatasetDropdown = dataByDataFolderPanel.clickImportedDataset(firstImportedDataset);
    String search = dataByImportedDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
    
    verificationStep("Verify that actual data variable contains the searched text");
    Assert.assertTrue("The actual data variable should containing the searched text",
        mapPage.getToolbar().getNameOfActiveDataVariable().contains(search));
    
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
  
  @After
  public void after() {
    unlockUser();
  }
  
}
