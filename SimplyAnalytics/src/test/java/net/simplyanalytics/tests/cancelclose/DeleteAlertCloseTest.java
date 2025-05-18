package net.simplyanalytics.tests.cancelclose;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.RadiusUnits;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.DeleteAlert;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteSubMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class DeleteAlertCloseTest extends TestBase {

  private NewProjectLocationWindow createNewProjectWindow;
  private LocationsTab locationsTab;
  private final String customRadiusLocationName = "Radius Location";

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  @Test
  public void closeDeleteAlertTest() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        100, RadiusUnits.METERS);

    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItem = 
        ((RecentFavoriteMenuItemWithSubMenu) locationsTab
        .getCustomLocations().clickViewCustomLocation().getMenuItem(customRadiusLocationName));
    
    RecentFavoriteSubMenu subMenu = recentFavoriteMenuItem.clickOnMoreOptions();

    DeleteAlert deleteAlert = subMenu.clickDeleteWithAlert();

    deleteAlert.clickClose();
    
    verificationStep("Verify that the Delete Alert is disappeared");
    Assert.assertFalse("The Delete Alert should be disappeared",
        deleteAlert.isDisplayed());
  }

}
