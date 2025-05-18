package net.simplyanalytics.tests.ldb.data;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
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

public class FavoriteDataTests extends TestBase {

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
  public void testFavoriteDataVariableSelection() {
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();

    RecentFavoriteMenu recentDataMenu = dataTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentData = 
        (RecentFavoriteMenuItemWithSubMenu) recentDataMenu
        .getNthMenuItem(1);
    recentData.clickOnMoreOptions().clickAddToFavorites();
    recentDataMenu.clickCloseButton();

    mapPage = new MapPage(driver);
    dataTab = mapPage.getLdbSection().getDataTab();

    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The Favorite Data Variables button should be present",
        dataTab.isFavoritesPresent());
    List<RecentFavoriteMenuItem> dataVars = dataTab.clickFavorites().getMenuItems();

    verificationStep("Verify that the selected data variables appear on the favories menu");
    for (RecentFavoriteMenuItem data : dataVars) {
      data.clickToSelect();
      Assert.assertEquals(
          "The data variable name in favorites menu should be the same as the data variable "
              + "selected" + " on Map Page",
          mapPage.getToolbar().getActiveDataVariable().getFullName(), data.getTitle());
    }
  }

  @Test
  public void testFavoriteDataVaribleMetaDataPresense() {
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();

    RecentFavoriteMenu recentDataMenu = dataTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentData = 
        (RecentFavoriteMenuItemWithSubMenu) recentDataMenu
        .getNthMenuItem(1);
    recentData.clickOnMoreOptions().clickAddToFavorites();
    recentDataMenu.clickCloseButton();

    mapPage = new MapPage(driver);
    dataTab = mapPage.getLdbSection().getDataTab();

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

  // TODO recheck this
  @Test
  public void testRemoveFromFavorites() {
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();

    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickRecent();
    List<RecentFavoriteMenuItem> dataVars = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem data : dataVars) {
      ((RecentFavoriteMenuItemWithSubMenu) data).clickOnMoreOptions().clickAddToFavorites();
    }

    recentFavoriteMenu.clickCloseButton();

    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The Favorite data variables button should be present",
        dataTab.isFavoritesPresent());

    recentFavoriteMenu = dataTab.clickFavorites();
    dataVars = recentFavoriteMenu.getMenuItems();
    int dataCount = dataVars.size();

    verificationStep(
        "Verify that the correct number of data variables appear on the favorites menu");
    Assert.assertEquals(
        "The number of Favorite data variables should match the number of recent data variables",
        dataCount, dataVars.size());

    RecentFavoriteMenuItemWithSubMenu menuItem = (RecentFavoriteMenuItemWithSubMenu) dataVars
        .get(dataVars.size() - 1);
    menuItem.clickOnMoreOptions().clickRemoveFromFavorites();
    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = dataTab.clickFavorites();
    dataVars = recentFavoriteMenu.getMenuItems();
    
    verificationStep("Verify that the number of data variables is reduced by one");
    Assert.assertEquals("The number of Favorite data variables should be decreased by one",
        --dataCount, dataVars.size());
    String dataVariableName = dataVars.get(0).getTitle();
    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = dataTab.clickRecent();

    ((RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu.getMenuItem(dataVariableName))
        .clickOnMoreOptions().clickRemoveFromFavorites();
    recentFavoriteMenu.clickCloseButton();

    verificationStep("Verify that the Favorites button doesn't appear anymore");
    dataTab.waitFavoritesToDisappear();
    Assert.assertFalse("Favorites button should not appear", dataTab.isFavoritesPresent());
  }

}
