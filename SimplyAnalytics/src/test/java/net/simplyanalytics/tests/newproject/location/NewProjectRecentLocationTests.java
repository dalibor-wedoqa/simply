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
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class NewProjectRecentLocationTests extends TestBase {

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
    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();

    verificationStep("Verify that the recent locations button is not present");
    Assert.assertFalse("The recent Locations button should not be present",
        newProjectLocationWindow.isRecentPresent());
  }
  
  @Test
  public void testRecentLocationPresenceWhenProjectIsCreated() {
    List<Location> locationList = new ArrayList<Location>();
    locationList.add(Location.USA);
    locationList.add(Location.LOS_ANGELES_CA_CITY);
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(locationList.get(1));
    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();

    verificationStep("Verify that the recent locations button is present");
    Assert.assertTrue("The recent Locations button should be present",
        newProjectLocationWindow.isRecentPresent());

    List<RecentFavoriteMenuItem> menuItem = newProjectLocationWindow.clickRecent().getMenuItems();

    verificationStep("Verify that the recent locations menu contains the correct locations");
    Assert.assertArrayEquals("Incorrect locations", locationList.stream().map(location -> location.getName()).toArray(), 
        menuItem.stream().map(location -> location.getTitle()).toArray());
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
    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();

    verificationStep("Verify that the recent locations button is present");
    Assert.assertTrue("The Recent Locations button should be present",
        newProjectLocationWindow.isRecentPresent());

    List<RecentFavoriteMenuItem> locations;
    locations = newProjectLocationWindow.clickRecent().getMenuItems();

    locations.get(3).clickToSelect();
    
    List<String> locationList = new ArrayList<String>();
    locationList.add(locations.get(3).getTitle() + " (City)");
    locationList.add(Location.USA.getName() + " (USA)");
    
    verificationStep("Verify that the location is now the selected");
    Assert.assertTrue(
        "Verify that location is selected", locations.get(3).isSelected());
    
    mapPage = newProjectLocationWindow.clickNextButton().clickCreateProjectButton();
    List<String> locationsList = mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickRemoveLDBButton().clickLocations().getItemsName();
    
    verificationStep("Verify that only two locations are present");
    Assert.assertTrue("Should be only two locations", locationsList.size() == 2);
    
    verificationStep("Verify that the correct location is present");
    Assert.assertArrayEquals("Not the correct locations", locationList.toArray(),  locationsList.toArray());
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
    
    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();


    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The recent data variables button should be present",
        newProjectLocationWindow.isRecentPresent());

    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();
    final int recentSizeBefore = recentLocations.size();

    RecentFavoriteMenuItemWithSubMenu menuItem = (RecentFavoriteMenuItemWithSubMenu) recentLocations
        .get(recentLocations.size() - 1);
    menuItem.clickOnMoreOptions().clickRemoveFromRecentList();

    recentFavoriteMenu.clickCloseButton();

    recentFavoriteMenu = newProjectLocationWindow.clickRecent();
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

    NewProjectLocationWindow newProjectLocationWindow = mapPage.getHeaderSection().clickNewProject();
    
    final List<Location> locations = Arrays.asList(Location.USA, Location.ZIP_20001_WASHINGTON_DC,
        Location.CALIFORNIA, Location.LOS_ANGELES_CA_CITY);

    verificationStep("Verify that the recent data variables button is present");
    Assert.assertTrue("The recent data variables button should be present",
        newProjectLocationWindow.isRecentPresent());

    RecentFavoriteMenu recentFavoriteMenu = newProjectLocationWindow.clickRecent();
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
