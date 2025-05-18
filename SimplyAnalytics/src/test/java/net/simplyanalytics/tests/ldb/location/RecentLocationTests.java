package net.simplyanalytics.tests.ldb.location;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecentLocationTests extends TestBase {

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
  public void testRecentLocationPresenceWhenProjectIsCreatedWithEmptyLocation() {
    NewProjectVariablesWindow newProjectWindow = createNewProjectWindow.clickNextButton();
    NewViewPage newViewPage = newProjectWindow.clickCreateProjectWithoutSeedVariables();
    MapPage mapPage = (MapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    verificationStep("Verify that the recent locations button is not present");
    Assert.assertFalse("The recent Locations button should not be present",
        locationTab.isRecentPresent());
  }

  @Test
  public void testRecentLocationPresenceWhenProjectIsCreated() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    verificationStep("Verify that the recent locations button is present");
    Assert.assertTrue("The recent Locations button should be present",
        locationTab.isRecentPresent());

    RecentFavoriteMenuItem menuItem = locationTab.clickRecent().getSelected();

    verificationStep("Verify that the recent locations menu contains the chosen location "
        + "(location from the creation of the project)");
    Assert.assertEquals("The First Item should ", menuItem.getTitle(), searchFor.getName());
  }

  @Test
  public void testRecentLocationSelection() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    MapToolbar mapToolbar = mapPage.getToolbar();

    verificationStep("Verify that the recent locations button is present");
    Assert.assertTrue("The Recent Locations button should be present",
        locationTab.isRecentPresent());

    List<RecentFavoriteMenuItem> locations;
    locations = locationTab.clickRecent().getMenuItems();

    for (RecentFavoriteMenuItem loc : locations) {
      loc.clickToSelect();
      verificationStep("Verify that the location is now the active location");
      Assert.assertEquals(
          "The Location name in recent menu should be the same as the Location name selected "
              + "on Map Page",
          mapToolbar.getActiveLocation().getName(), loc.getTitle());
    }
  }

  @Test
  public void testRemoveFromRecent() {
    Location searchFor = Location.LOS_ANGELES_CA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    mapPage = locationTab.chooseLocation(Location.CALIFORNIA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    mapPage = locationTab.chooseLocation(Location.USA);
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The recent data variables button should be present",
        locationTab.isRecentPresent());

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();
    final int recentSizeBefore = recentLocations.size();

    RecentFavoriteMenuItemWithSubMenu menuItem = (RecentFavoriteMenuItemWithSubMenu) recentLocations
        .get(recentLocations.size() - 1);
    menuItem.clickOnMoreOptions().clickRemoveFromRecentList();

    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = locationTab.clickRecent();
    recentLocations = recentFavoriteMenu.getMenuItems();

    verificationStep("Verify that the number of recent locations is reduced by one");
    Assert.assertEquals("The number of recent locations should be decreased by one",
        recentSizeBefore - 1, recentLocations.size());
  }

  @Test
  public void testAliasLocationNameRecent() {
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
    locationTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    final List<Location> locations = Arrays.asList(Location.USA, Location.ZIP_20001_WASHINGTON_DC,
        Location.CALIFORNIA, Location.LOS_ANGELES_CA_CITY);

    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The recent data variables button should be present",
        locationTab.isRecentPresent());

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
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

    verificationStep("Verify that the alias name appears on the recent location menu");
    recentLocations = recentFavoriteMenu.getMenuItems();
    Iterator<Location> expectedLocation = locations.iterator();
    for (RecentFavoriteMenuItem location : recentLocations) {
      Location locationEnum = expectedLocation.next();
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

    verificationStep("Verify that the edited alias name appears on the recent location menu");
    recentLocations = recentFavoriteMenu.getMenuItems();
    expectedLocation = locations.iterator();
    for (RecentFavoriteMenuItem location : recentLocations) {
      Location locationEnum = expectedLocation.next();
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

    verificationStep("Verify that the original name appears on the recent location menu");
    recentLocations = recentFavoriteMenu.getMenuItems();
    expectedLocation = locations.iterator();
    for (RecentFavoriteMenuItem location : recentLocations) {
      Location locationEnum = expectedLocation.next();
      String actual = location.getTitle();
      String expected = locationEnum.getName();
      Assert.assertEquals("The location name is not expected", expected, actual);
    }
  }
}
