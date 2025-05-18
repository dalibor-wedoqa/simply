package net.simplyanalytics.tests.view.map;

import java.util.List;
import java.util.Random;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.sections.view.map.MapInformationPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class MapInformationTests extends TestBase {
  
  MapViewPanel mapViewPanel;
  MapPage mapPage;
  
  /**
   * Sign-in and creating a new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    mapViewPanel = mapPage.getActiveView();
  }
  
  @Test
  public void testOpenInformationdropdown() {
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    mapViewPanel.clickCenterOfMapInfo();
  }
  
  @Test
  public void testAddNewLocationFromInfoDropdown() {
    String previousActiveLocation = mapPage.getToolbar().getNameOfActiveLocation();
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    MapInformationPanel mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    mapPage = mapInformationPanel.clickAddLocationToProject();
    verificationStep("Verify that the location of the project os changed");
    Assert.assertFalse("The location is not changed", mapPage.getToolbar().getNameOfActiveLocation().equals(previousActiveLocation));
  }
  
  //@Disabled("Skipping test testInfoDropdownWithDifferenteLocationTypes")
  public void testInfoDropdownWithDifferenteLocationTypes() {
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    int x = new Random().nextInt(200);
    int y = new Random().nextInt(200);
    MapInformationPanel mapInformationPanel = mapViewPanel.clickOnMapInfoByCoordinates(x, y);
    String location = mapInformationPanel.getLocationName();
    mapPage = mapInformationPanel.clickAddLocationToProject();
    verificationStep("Verify that the location of the project is changed");
    Assert.assertTrue("The location is not changed", mapPage.getToolbar().getNameOfActiveLocation().equals(location));
    
    mapPage.getActiveView().getLegend().clickOnMapCenter().selectLocationType(LocationType.ZIP_CODE);
    
    verificationStep("Verify that the location of the project is changed");
    Assert.assertTrue("The location is not changed", mapPage.getActiveView().getLegend().getMapCenter().equals(location));
    
    mapPage.getToolbar().openLocationTypeListMenu().clickLocationType(LocationType.ZIP_CODE);

    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    verificationStep("Verify that the location of the info panel is the expected");
    Assert.assertTrue("The location is not correct", mapInformationPanel.getLocationName().equals(location));

    mapPage = mapInformationPanel.clickOnCloseButton();
    mapPage.getActiveView().getLegend().clickOnMapCenter().selectLocationType(LocationType.STATE);
    mapPage.getToolbar().openLocationTypeListMenu().clickLocationType(LocationType.STATE);
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();    
    location = mapInformationPanel.getLocationName();
    
    verificationStep("Verify that the location of the project is changed");
    Assert.assertTrue("The location is not changed", mapPage.getActiveView().getLegend().getMapCenter().equals(location));
    
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    verificationStep("Verify that the location of the info panel is the expected");
    Assert.assertTrue("The location is not correct", mapInformationPanel.getLocationName().equals(location));
    mapPage = mapInformationPanel.clickOnCloseButton();
    
    mapPage.getActiveView().getLegend().clickOnMapCenter().selectLocationType(LocationType.COUNTY);
    mapPage.getToolbar().openLocationTypeListMenu().clickLocationType(LocationType.COUNTY);
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();    
    location = mapInformationPanel.getLocationName();
    
    verificationStep("Verify that the location of the project is changed");
    Assert.assertTrue("The location is not changed", mapPage.getActiveView().getLegend().getMapCenter().equals(location));
    
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    verificationStep("Verify that the location of the info panel is the expected");
    Assert.assertTrue("The location is not correct", mapInformationPanel.getLocationName().equals(location));
    mapPage = mapInformationPanel.clickOnCloseButton();
    
    mapPage.getActiveView().getLegend().clickOnMapCenter().selectLocationType(LocationType.CITY);
    mapPage.getToolbar().openLocationTypeListMenu().clickLocationType(LocationType.CITY);
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();    
    location = mapInformationPanel.getLocationName();
    
    verificationStep("Verify that the location of the project is changed");
    Assert.assertTrue("The location is not changed", mapPage.getActiveView().getLegend().getMapCenter().equals(location));
    
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    verificationStep("Verify that the location of the info panel is the expected");
    Assert.assertTrue("The location is not correct", mapInformationPanel.getLocationName().equals(location));
    mapPage = mapInformationPanel.clickOnCloseButton();
    
    mapPage.getActiveView().getLegend().clickOnMapCenter().selectLocationType(LocationType.CENSUS_TRACT);
    mapPage.getToolbar().openLocationTypeListMenu().clickLocationType(LocationType.CENSUS_TRACT);
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();    
    location = mapInformationPanel.getLocationName();
    
    verificationStep("Verify that the location of the project is changed");
    Assert.assertTrue("The location is not changed", mapPage.getActiveView().getLegend().getMapCenter().equals(location));
    
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    verificationStep("Verify that the location of the info panel is the expected");
    Assert.assertTrue("The location is not correct", mapInformationPanel.getLocationName().equals(location));
  }
  
  @Test
  public void testAddLocationToFavorites() {
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    MapInformationPanel mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    String selectedLocation = mapInformationPanel.getLocationName();
    mapInformationPanel.clickAddLocationToFavotires();    
    LocationsTab locationTab = mapPage.getLdbSection().clickLocations();
    RecentFavoriteMenu recentFavoriteMenu = locationTab.clickFavorites();
    List<RecentFavoriteMenuItem> locations = recentFavoriteMenu.getMenuItems();
    verificationStep("Verify that the location is selected as Favorite location");
    for (RecentFavoriteMenuItem location : locations) {
      Assert.assertTrue("The location is not favorite", ((RecentFavoriteMenuItemWithSubMenu) location).getTitle().equals(selectedLocation));
    }
  }

  //@Disabled("Skipping test testAddAliasLocationName")
  @Test
  public void testAddAliasLocationName() {
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    MapInformationPanel mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    String aliasName = "alias name";
    String selectedLocation = mapInformationPanel.getLocationName();
    AliasLocationWindow aliasLocationWindow = mapInformationPanel.clickAddAliasLocationName();
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    mapPage = mapInformationPanel.clickAddLocationToProject();
    
    String newName = mapPage.getToolbar().getNameOfActiveLocation();
    
    verificationStep(
        "Verify that the new location name contains the alias name and the original name");
    String expectedName = aliasName + "  [" + selectedLocation + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);    
  }
  
  @Test
  public void testAlternateLocation() {
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    MapInformationPanel mapInformationPanel = mapViewPanel.clickCenterOfMapInfo();
    String oldLocation = mapInformationPanel.getLocationName();
    mapInformationPanel=mapInformationPanel.clickAlternateLocations().clickRandomGeographicUnit();
    String newLocation = mapInformationPanel.getLocationName();
    verificationStep("Verify that the location is changed");
    Assert.assertFalse("The location does not changed", oldLocation.equals(newLocation));
  }  
  
  @Test
  public void testLocationIsDisplayed() {
    int x = new Random().nextInt(200);
    int y = new Random().nextInt(200);
    mapViewPanel = mapViewPanel.getControlsPanel().clickInfoControlButton();
    MapInformationPanel mapInformationPanel = mapViewPanel.clickOnMapInfoByCoordinates(x, y);
    String location = mapInformationPanel.getLocationName();
    verificationStep("Verify that the location is not N/A");
    Assert.assertFalse("Location is N/A", location.contains("N/A"));
  }
}
