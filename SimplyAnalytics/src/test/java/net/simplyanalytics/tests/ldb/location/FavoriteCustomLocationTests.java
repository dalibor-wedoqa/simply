package net.simplyanalytics.tests.ldb.location;

import java.util.List;
import java.util.stream.Collectors;

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
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FavoriteCustomLocationTests extends TestBase {

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

//  @Test
  public void testCongressDistCombinationLocationFavorite() {
    Location searchFor = Location.CONGRESS_DIST_CD07_IL;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();
    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }
  }

  @Test
  public void testCountyCombinationLocationFavorite() {
    Location searchFor = Location.COUNTY_MADERA_COUNTY_CA;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }
    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }
  }

  @Test
  public void testCensusTractCombinationLocationFavorite() {
    Location searchFor = Location.CENSUS_TRACT_CT843400_COOK_COUNTY_IL;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }

  }

  @Test
  public void testBlockGroupCombinationLocationFavorite() {
    Location searchFor = Location.BLOCK_GROUP_BG0065002_KERN_COUNTY_CA;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);
      }
    }
  }

  @Test
  public void testStateCombinationLocationFavorite() {
    Location searchFor = Location.FLORIDA;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }
  }

  @Test
  public void testZipCodeCombinationLocationFavorite() {
    Location searchFor = Location.ZIP_44113_CLEVELAND;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();
    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }
  }

  @Test
  public void testCityCombinationLocationFavorite() {
    Location searchFor = Location.WASHINGTON_DC_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu
          .getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }
  }

  @Test
  public void testShortWashingtonDC() {
    Location searchFor = Location.WASHINGTON_DC_CITY;
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariablesShort(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is not present");
        Assert.assertEquals("The Add to Combination Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isAddToCombinationLocationPresent();
        verificationStep("Verify that the add to combination location is present");
        Assert.assertEquals("The Add to Combination Location should be present", true, present);

      }
    }
  }

//  @Test
  public void testCongressDistRadiusLocationFavorite() {
    Location searchFor = Location.CONGRESS_DIST_CD11_OH;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is not present");
        Assert.assertEquals("The Radius Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }

  @Test
  public void testCountyRadiusLocationFavorite() {
    Location searchFor = Location.COUNTY_COOK_COUNTY_CHICAGO_IL;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is not present");
        Assert.assertEquals("The Radius Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }

  @Test
  public void testZipCodeRadiusLocationFavorite() {
    Location searchFor = Location.ZIP_93505_CALIFORNIA_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is not present");
        Assert.assertEquals("The Radius Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }

  @Test
  public void testCensusTractRadiusLocationFavorite() {
    Location searchFor = Location.CENSUS_TRACT_CT830500_COOK_COUNTY_IL;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is not present");
        Assert.assertEquals("The Radius Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }

  @Test
  public void testBlockGrouptRadiusLocationFavorite() {
    Location searchFor = Location.BLOCK_GROUP_BG0065002_KERN_COUNTY_CA;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is not present");
        Assert.assertEquals("The Radius Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }

  @Test
  public void testCitytRadiusLocationFavorite() {
    Location searchFor = Location.MIAMI_FL_CITY;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")) {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is not present");
        Assert.assertEquals("The Radius Location shouldn't be present", false, present);
      } else {
        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that the radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }

  @Test
  public void testStatetRadiusLocationFavorite() {
    Location searchFor = Location.CALIFORNIA;
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    mapPage.getActiveView().waitForFullLoad();
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();

    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickRecent();
    List<RecentFavoriteMenuItem> recentLocations = recentFavoriteMenu.getMenuItems();

    for (String locationName : recentLocations.stream().map(location -> location.getTitle())
        .collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu location = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(locationName);

      location.clickOnMoreOptions().clickAddToFavorites();
    }

    RecentFavoriteMenu favoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> favoriteLocations = favoriteMenu.getMenuItems();

    for (String favoriteLocationName : favoriteLocations.stream()
        .map(favoriteLocation -> favoriteLocation.getTitle()).collect(Collectors.toList())) {
      RecentFavoriteMenuItemWithSubMenu favoriteLocation = (RecentFavoriteMenuItemWithSubMenu) 
          recentFavoriteMenu.getMenuItem(favoriteLocationName);

      if (favoriteLocation.getTitle().equals("USA")
          || favoriteLocation.getTitle().equals("California")) {

        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that radius location is not present");
        Assert.assertEquals("The radius location shouldn't be present", false, present);
      } else {

        boolean present = favoriteLocation.clickOnMoreOptions().isCreateRadiusLocationPresent();
        verificationStep("Verify that radius location is present");
        Assert.assertEquals("The radius location should be present", true, present);
      }
    }
  }
}
