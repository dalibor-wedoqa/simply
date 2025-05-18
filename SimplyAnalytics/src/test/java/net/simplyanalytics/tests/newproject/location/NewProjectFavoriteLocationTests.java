package net.simplyanalytics.tests.newproject.location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteSubMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class NewProjectFavoriteLocationTests extends TestBase {

  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;

  /**
   * Signing in.
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
  public void testFavoriteLocationSelection() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);

    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();
    
    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The recent location button should be present",
        newProjectLocationWindow.isRecentPresent());

    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
    List<RecentFavoriteMenuItem> locations = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem location : locations) {
      ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions().clickAddToFavorites();
    }

    MapToolbar mapToolbar = mapPage.getToolbar();

    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The Favorite Locations button should be present",
        newProjectLocationWindow.isFavoritesPresent());
    locations = newProjectLocationWindow.clickFavorites().getMenuItems();
    
    locations.get(2).clickToSelect();
    
    List<String> locationList = new ArrayList<String>();
    locationList.add(locations.get(2).getTitle() + " (Zip Code)");
    locationList.add(Location.USA.getName() + " (USA)");
    
    verificationStep("Verify that the location is now the selected");
    Assert.assertTrue(
        "Verify that location is selected", locations.get(2).isSelected());
    
    mapPage = newProjectLocationWindow.clickNextButton().clickCreateProjectButton();
    List<String> locationsList = mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickRemoveLDBButton().clickLocations().getItemsName();
    
    verificationStep("Verify that only two locations are present");
    Assert.assertTrue("Should be only two locations", locationsList.size() == 2);
    
    verificationStep("Verify that the correct location is present");
    Assert.assertArrayEquals("Not the correct locations", locationList.toArray(),  locationsList.toArray());
  }
  
  @Test
  public void testRemoveFromFavorites() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);

    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();
    
    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
    List<RecentFavoriteMenuItem> locations = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem location : locations) {
      ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions().clickAddToFavorites();
    }

    recentFavoriteMenu.clickCloseButton();

    verificationStep("Verify that the favorite button (star icon) is present");
    Assert.assertTrue("The Favorite location variables button should be present",
        newProjectLocationWindow.isFavoritesPresent());

    recentFavoriteMenu = newProjectLocationWindow.clickFavorites();
    locations = recentFavoriteMenu.getMenuItems();
    int locationCount = locations.size();

    verificationStep("Verify that the correct number of locations appear on the favorites menu");
    Assert.assertEquals(
        "The number of Favorite location variables should match the number of recent location "
            + "variables",
        locationCount, locations.size());

    RecentFavoriteMenuItemWithSubMenu menuItem = (RecentFavoriteMenuItemWithSubMenu) locations
        .get(locations.size() - 1);
    menuItem.clickOnMoreOptions().clickRemoveFromFavorites();
    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = newProjectLocationWindow.clickFavorites();
    locations = recentFavoriteMenu.getMenuItems();

    verificationStep("Verify that the number of locations is reduced by one");
    Assert.assertEquals("The number of Favorite location should be decreased by one",
        --locationCount, locations.size());
    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = newProjectLocationWindow.clickRecent();

    locations = recentFavoriteMenu.getMenuItems();
    RecentFavoriteSubMenu subMenu;
    for (RecentFavoriteMenuItem location : locations) {

      subMenu = ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions();
      if (subMenu.isInFavorites()) {
        subMenu.clickRemoveFromFavorites();
        break;
      }
    }
    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = newProjectLocationWindow.clickFavorites();
    locations = recentFavoriteMenu.getMenuItems();

    verificationStep("Verify that the number of locations is reduced by one");
    Assert.assertEquals("The number of Favorite location should be decreased by one",
        locationCount - 1, locations.size());
  }
  
  @Test
  public void testAliasLocationNameFavorite() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);

    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();
    
    final List<Location> locations = Arrays.asList(Location.LOS_ANGELES_CA_CITY,
        Location.CALIFORNIA, Location.ZIP_20001_WASHINGTON_DC, Location.USA);

    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
    List<RecentFavoriteMenuItem> locationList = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem location : locationList) {
      ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions().clickAddToFavorites();
    }

    recentFavoriteMenu = newProjectLocationWindow.clickFavorites();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = 
          (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);
      AliasLocationWindow aliasLocationWindow = location.clickOnMoreOptions()
          .clickAddAliasLocation();
      aliasLocationWindow.enterAliasName("alias name of " + locationName);
      aliasLocationWindow.clickSave();
      
      recentFavoriteMenu = new RecentFavoriteMenu(driver);
      recentFavoriteMenu.waitForLocationToAppear("alias name of " + locationName);
    }

    verificationStep("Verify that the alias name appears on the favorite location menu");
    recentLocations = recentFavoriteMenu.getMenuItems();
    Iterator<Location> expectedLocation = locations.iterator();
    for (RecentFavoriteMenuItem location : recentLocations) {
      Location locationEnum = expectedLocation.next();
      location.waitForNameToChange("alias name of " + locationEnum.getName());
      String actual = location.getTitle();
      String expected = "alias name of " + locationEnum.getName() + "  [" + locationEnum.getName()
          + "]";
      Assert.assertEquals("The alias name is not expected", expected, actual);
    }

    recentLocations = recentFavoriteMenu.getMenuItems();
    expectedLocation = locations.iterator();
    for (Location locationEnum : locations) {
      RecentFavoriteMenuItemWithSubMenu location = 
          (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationEnum.getName());
      AliasLocationWindow aliasLocationWindow = location.clickOnMoreOptions()
          .clickEditAliasLocation();
      aliasLocationWindow.enterAliasName("edited name of " + locationEnum.getName());
      aliasLocationWindow.clickSave();
      recentFavoriteMenu = new RecentFavoriteMenu(driver);
      recentFavoriteMenu.waitForLocationToAppear("edited name of " + locationEnum.getName());
    }

    verificationStep("Verify that the edited alias name appears on the favorite location menu");
    recentLocations = recentFavoriteMenu.getMenuItems();
    expectedLocation = locations.iterator();
    for (RecentFavoriteMenuItem location : recentLocations) {
      Location locationEnum = expectedLocation.next();
      location.waitForNameToChange("edited name of " + locationEnum.getName());
      String actual = location.getTitle();
      String expected = "edited name of " + locationEnum.getName() + "  [" + locationEnum.getName()
          + "]";
      Assert.assertEquals("The alias name is not expected", expected, actual);
    }

    recentLocations = recentFavoriteMenu.getMenuItems();
    expectedLocation = locations.iterator();
    for (Location locationEnum : locations) {
      RecentFavoriteMenuItemWithSubMenu location = 
          (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationEnum.getName());
      AliasLocationWindow aliasLocationWindow = location.clickOnMoreOptions()
          .clickEditAliasLocation();
      aliasLocationWindow.enterAliasName("");
      aliasLocationWindow.clickSave();
      recentFavoriteMenu = new RecentFavoriteMenu(driver);
      recentFavoriteMenu.waitForLocationToAppear(locationEnum.getName());
    }

    verificationStep("Verify that the original name appears on the favorite location menu");
    recentLocations = recentFavoriteMenu.getMenuItems();
    expectedLocation = locations.iterator();
    for (RecentFavoriteMenuItem location : recentLocations) {
      Location locationEnum = expectedLocation.next();
      location.waitForNameToChange(locationEnum.getName());
      String actual = location.getTitle();
      String expected = locationEnum.getName();
      logger.info("Expected location: " + expected);
      Assert.assertEquals("The location name is not expected", expected, actual);
    }
  }
  
  @Test
  public void testAddToCombinationLocationFavorite() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);
    
    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();

    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
    List<RecentFavoriteMenuItem> locationList = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem location : locationList) {
      ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions().clickAddToFavorites();
    }

    recentFavoriteMenu = newProjectLocationWindow.clickFavorites();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem location : recentLocations) {
      boolean present = ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions()
          .isAddToCombinationLocationPresent();
      if (location.getTitle().equals("USA")) {
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertFalse("The Add to Combination Location shouldn't be present", present);
      } else {
        verificationStep("Verify that the add to combination location is present");
        Assert.assertTrue("The Add to Combination Location should be present", present);
      }
    }
  }
  
  @Test
  public void testCreateRadiusLocationFavorite() {

    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);

    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();
    
    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
    List<RecentFavoriteMenuItem> locationList = recentFavoriteMenu.getMenuItems();

    for (RecentFavoriteMenuItem location : locationList) {
      ((RecentFavoriteMenuItemWithSubMenu) location).clickOnMoreOptions().clickAddToFavorites();
    }

    recentFavoriteMenu = newProjectLocationWindow.clickFavorites();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = 
          (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      if (location.getTitle().equals("USA") || location.getTitle().equals("California")) {

        boolean present = location.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the create radius location is not present");
        Assert.assertFalse("The Create radius location shouldn't be present", present);
      } else {

        boolean present = location.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the create radius location is present");
        Assert.assertTrue("The Create radius location should be present", present);
      }
    }
  }
  
}
