package net.simplyanalytics.tests.ldb.location;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.CreateOrEditCombinationLocation;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteSubMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteSubMenu.CombinationLocationSubMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

/**
 * .
 * 
 * @author wedoqa
 */
public class CombinationLocationTests extends TestBase {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;
  private LocationsTab locationsTab;
  private final String customCombinationLocationName = "State combination location";
  private final String customCombinationLocationName2 = "City combination location";

  /**
   * Signing in.
   */
  @Before
  public void before() {
    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  @Test
  public void testCreateCustomCombinationLocation() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.NEW_YORK_STATE);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName, Location.CALIFORNIA,
        Location.FLORIDA);

    verificationStep("Verify that the Combination Location appears in the custom location list");
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customCombinationLocationName));

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickRecent();
    verificationStep("Verify that the Combination Location appears in the recent location list");
    Assert.assertTrue("The combination location should present on the recent location list",
        recentFavoriteMenu.isItemPresent(customCombinationLocationName));

    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItemWithSubMenu = 
        (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
        .getMenuItem(Location.NEW_YORK_STATE.getName());
    RecentFavoriteSubMenu recentFavoriteSubMenu = recentFavoriteMenuItemWithSubMenu
        .clickOnMoreOptions();
    CombinationLocationSubMenu combinationLocationSubMenu = recentFavoriteSubMenu
        .clickAddToCombinationLocation();
    List<String> combinationLocations = combinationLocationSubMenu.getCustomLocations();

    verificationStep(
        "Verify that the new combination location "
        + "and only that is present in the combination location list");
    Assert.assertEquals("Only one combination location should appear", 1,
        combinationLocations.size());
    Assert.assertTrue("The combianation location should be present",
        combinationLocations.get(0).equals(customCombinationLocationName));
  }

  @Test
  public void testCreateCustomCombinationLocationFromRecentLocation() {
    Location location = Location.WASHINGTON_DC_CITY;

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(location);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItemWithSubMenu = 
        (RecentFavoriteMenuItemWithSubMenu) locationsTab
        .clickRecent().getMenuItem(location.getName());
    CombinationLocationSubMenu combinationLocationSubMenu = recentFavoriteMenuItemWithSubMenu
        .clickOnMoreOptions().clickAddToCombinationLocation();
    CreateOrEditCombinationLocation createOrEditCombinationLocation = combinationLocationSubMenu
        .clickCreateNewCombinationButton();

    List<String> locations = createOrEditCombinationLocation.getSelectedLocations();
    verificationStep(
        "Verify that the recent location, and only that location appears in the location list");

    Assert.assertEquals("The location list should contain only 1 location", 1, locations.size());
    Assert.assertEquals("The location list should contain the recent location", location,
        Location.getByName(locations.get(0)));
  }

  @Test
  public void testAddRecentLocationToCustomCombinationLocation() {
    Location location = Location.NEW_YORK_STATE;

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(location);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName, Location.CALIFORNIA,
        Location.FLORIDA);

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItemWithSubMenu = 
        (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
        .getMenuItem(location.getName());
    CombinationLocationSubMenu combinationLocationSubMenu = recentFavoriteMenuItemWithSubMenu
        .clickOnMoreOptions().clickAddToCombinationLocation();
    combinationLocationSubMenu.clickOnCustomLocation(customCombinationLocationName);

    recentFavoriteMenuItemWithSubMenu = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
        .getMenuItem(customCombinationLocationName);
    CreateOrEditCombinationLocation createOrEditCombinationLocation = 
        recentFavoriteMenuItemWithSubMenu
        .clickOnMoreOptions().clickEditCombinationLocation();

    List<String> locations = createOrEditCombinationLocation.getSelectedLocations();
    verificationStep(
        "Verify that the new location appears in the list of locations in the custom location");
    Assert.assertEquals("The number of locations is not the expected", 3, locations.size());
    Assert.assertTrue(
        "The location should be present in the list of location in the custom location",
        locations.contains(location.getName()));

  }

  @Test
  public void testCreateMultipleCustomCombinationLocations() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.NEW_YORK_STATE);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.chooseLocation(Location.MIAMI_FL_CITY);

    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName, Location.CALIFORNIA,
        Location.FLORIDA);

    verificationStep("Verify that the Combination Location appears in the custom location list");
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customCombinationLocationName));

    locationsTab.clickHideCustomLocation();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName2,
        Location.CHICAGO_IL_CITY, Location.WASHINGTON_DC_CITY);

    verificationStep(
        "Verify that the new Combination Location appears in the custom location list");
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customCombinationLocationName2));
    locationsTab.clickHideCustomLocation();

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickRecent();
    verificationStep("Verify that the combination locations appear in the recent location list");
    Assert.assertTrue("The combination location should present on the recent location list",
        recentFavoriteMenu.isItemPresent(customCombinationLocationName));
    Assert.assertTrue("The combination location 2 should present on the recent location list",
        recentFavoriteMenu.isItemPresent(customCombinationLocationName2));

    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItemWithSubMenu = 
        (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
        .getMenuItem(Location.NEW_YORK_STATE.getName());
    RecentFavoriteSubMenu recentFavoriteSubMenu = recentFavoriteMenuItemWithSubMenu
        .clickOnMoreOptions();
    CombinationLocationSubMenu combinationLocationSubMenu = recentFavoriteSubMenu
        .clickAddToCombinationLocation();
    List<String> combinationLocations = combinationLocationSubMenu.getCustomLocations();

    verificationStep(
        "Verify that the state combination location "
        + "and only that is present in the combination location list");
    Assert.assertEquals("Only one combination location should appear", 1,
        combinationLocations.size());
    Assert.assertTrue("The state combianation location should be present",
        combinationLocations.get(0).equals(customCombinationLocationName));

    recentFavoriteMenuItemWithSubMenu = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
        .getMenuItem(Location.MIAMI_FL_CITY.getName());
    recentFavoriteSubMenu = recentFavoriteMenuItemWithSubMenu.clickOnMoreOptions();
    combinationLocationSubMenu = recentFavoriteSubMenu.clickAddToCombinationLocation();
    combinationLocations = combinationLocationSubMenu.getCustomLocations();

    verificationStep(
        "Verify that the city combination location "
        + "and only that is present in the combination location list");
    Assert.assertEquals("Only one combination location should appear", 1,
        combinationLocations.size());
    Assert.assertTrue("The city combianation location should be present",
        combinationLocations.get(0).equals(customCombinationLocationName2));
  }

  @Test
  public void testEditCustomCombinationLocation() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName, Location.CALIFORNIA,
        Location.FLORIDA);

    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItem = 
        ((RecentFavoriteMenuItemWithSubMenu) locationsTab
        .getCustomLocations().clickViewCustomLocation().getMenuItem(customCombinationLocationName));

    CreateOrEditCombinationLocation editCombinationLocation = recentFavoriteMenuItem
        .clickOnMoreOptions().clickEditCombinationLocation();

    verificationStep("Verify that the combinated location name is the expected");
    Assert.assertEquals("The custom location name is not updated properly",
        customCombinationLocationName, editCombinationLocation.getLocatinoName());

    List<String> locations = editCombinationLocation.getSelectedLocations();

    verificationStep("Verify that the number of location present in the window is the expected");
    Assert.assertEquals("The location number is not the expected", 2, locations.size());

    verificationStep(
        "Verify that the selected custom locations "
        + "are present on the edit combination location window");
    Assert.assertTrue("California missing", locations.contains(Location.CALIFORNIA.getName()));
    Assert.assertTrue("Florida missing", locations.contains(Location.FLORIDA.getName()));

    editCombinationLocation.enterCombinationName(customCombinationLocationName2);
    editCombinationLocation.removeLocation(Location.CALIFORNIA.getName());
    editCombinationLocation.chooseLocation(Location.NEW_YORK_STATE);
    editCombinationLocation.clickSave();

    mapPage = new MapPage(driver);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    recentFavoriteMenuItem = ((RecentFavoriteMenuItemWithSubMenu) locationsTab.getCustomLocations()
        .clickViewCustomLocation().getMenuItem(customCombinationLocationName2));
    editCombinationLocation = recentFavoriteMenuItem.clickOnMoreOptions()
        .clickEditCombinationLocation();

    verificationStep("Verify that the combinated location name is updated");
    Assert.assertEquals("The custom location name is not updated properly",
        customCombinationLocationName2, editCombinationLocation.getLocatinoName());

    locations = editCombinationLocation.getSelectedLocations();

    verificationStep("Verify that the number of location present in the window is the expected");
    Assert.assertEquals("The location number is not the expected", 2, locations.size());

    verificationStep(
        "Verify that the updated locations are present on the edit combination location window");
    Assert.assertTrue("Florida missing", locations.contains(Location.FLORIDA.getName()));
    Assert.assertTrue("New York missing", locations.contains(Location.NEW_YORK_STATE.getName()));

  }

}
