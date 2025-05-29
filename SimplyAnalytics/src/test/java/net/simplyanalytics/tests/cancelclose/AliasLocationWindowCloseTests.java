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
    System.out.println("Method before() called");

    System.out.println("Instantiating AuthenticateInstitutionPage");
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);

    System.out.println("Logging in with institution credentials");
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);

    System.out.println("Clicking 'Sign in as Guest'");
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();

    System.out.println("Closing welcome screen tutorial window");
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    System.out.println("Creating new project with location: " + searchFor);
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    System.out.println("Waiting for map page active view to fully load");
    mapPage.getActiveView().waitForFullLoad();

    System.out.println("Clicking 'Locations' in LDB section");
    mapPage.getLdbSection().clickLocations();

    System.out.println("Clicking 'Locations' again to get LocationsTab");
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    System.out.println("Clicking 'Recent' in LocationsTab to get RecentFavoriteMenu");
    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();

    System.out.println("Getting recent favorite menu items");
    List<RecentFavoriteMenuItem> locationList = recentFavoriteMenu.getMenuItems();
    System.out.println("Number of recent favorite menu items found: " + locationList.size());

    System.out.println("Clicking on more options of first RecentFavoriteMenuItemWithSubMenu to add alias location");
    aliasLocationWindow = ((RecentFavoriteMenuItemWithSubMenu) locationList.get(0))
            .clickOnMoreOptions().clickAddAliasLocation();
  }


  @Test
  public void closeAliasLocationWindow() {
    
    aliasLocationWindow.clickClose();
    //
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
