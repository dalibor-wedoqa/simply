package net.simplyanalytics.tests.cancelclose;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AliasLocationWindowCloseTests extends TestBase {

  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;
  private AliasLocationWindow aliasLocationWindow;

  /**
   * Signing in, creating new project and open alias location window.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();
   
    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> locationList = recentFavoriteMenu.getMenuItems();

    aliasLocationWindow = ((RecentFavoriteMenuItemWithSubMenu) locationList
        .get(0)).clickOnMoreOptions().clickAddAliasLocation();
  }

  @Test
  public void closeAliasLocationWindow() {
    
    aliasLocationWindow.clickClose();
    
    verificationStep("Verify that the Alias Location Window is disappeared");
    Assert.assertFalse("The Alias Location Window should be disappeared",
        aliasLocationWindow.isDisplayed());
  }
  
  @Test
  public void cancelAliasLocationWindow() {
    
    aliasLocationWindow.clickCancel();
    
    verificationStep("Verify that the Alias Location Window is disappeared");
    Assert.assertFalse("The Alias Location Window should be disappeared",
        aliasLocationWindow.isDisplayed());
  }

}
