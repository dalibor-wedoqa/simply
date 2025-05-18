package net.simplyanalytics.tests.ldb.location;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.RadiusUnits;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.RemoveLDBPanel;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.CreateRadiusLocation;
import net.simplyanalytics.pageobjects.sections.ldb.locations.DeleteAlert;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteSubMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

/**
 * .
 * 
 * @author wedoqa
 */
public class RadiusLocationTests extends TestBase {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;
  private LocationsTab locationsTab;
  private BusinessesTab businessesTab;
  private BusinessesPage businessesPage;
  private NewViewPage newViewPage;
  private MapPage mapPage;
  private final String customRadiusLocationName = "Radius Location";
  private final String customRadiusLocationName2 = "Radius Location Name";

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
  public void testCreateMilesRadiusLocation() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    verificationStep("Verify that the Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
  }

  @Test
  public void testCreateKilometersRadiusLocation() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        100, RadiusUnits.KILOMETERS);

    verificationStep("Verify that the Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
  }

  @Test
  public void testCreateFeetRadiusLocation() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        100, RadiusUnits.FEET);

    verificationStep("Verify that the Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
  }

  @Test
  public void testCreateMetersRadiusLocation() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        100, RadiusUnits.METERS);

    verificationStep("Verify that the Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));
  }

  @Test
  public void testCreateMultipleRadiusLocations() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        10, RadiusUnits.KILOMETERS);

    verificationStep("Verify that the Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));

    locationsTab.clickHideCustomLocation();
    locationsTab.addNewCustomRadiusLocation(Location.MIAMI_FL_CITY, customRadiusLocationName2, 20,
        RadiusUnits.KILOMETERS);

    verificationStep("Verify that the new Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName2));
  }

  @Test
  public void testDeleteCustomRadiusLocation() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        100, RadiusUnits.METERS);

    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItem =
        ((RecentFavoriteMenuItemWithSubMenu) locationsTab
        .getCustomLocations().clickViewCustomLocation().getMenuItem(customRadiusLocationName));
    RecentFavoriteSubMenu subMenu = recentFavoriteMenuItem.clickOnMoreOptions();

    verificationStep("Verify that the delete button appears");
    Assert.assertTrue("The delete button should be present", subMenu.isDeletePresent());

    verificationStep(
        "Verify that the delete location alert appears (Location still in use in a project)");
    DeleteAlert deleteAlert = subMenu.clickDeleteWithAlert();

    verificationStep(
        "Verify that the delete location alert appears (Location still in use in a project)");
    verificationStep("Verify that the project is the expected New Project");
    Assert.assertEquals("The project name is incorrect", Arrays.asList("New Project"),
        deleteAlert.getProjects());

    deleteAlert.clickOkButton();    

    mapPage = new MapPage(driver);
    mapPage.getToolbar().getActiveLocation();
    //TODO verification
   
    ProjectSettingsPage projectsSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    RemoveLDBPanel LDBPanel = projectsSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton();
    LDBPanel.clickLocations().getItemsName();
    //TODO verify that custom location is not on the list
    mapPage = (MapPage) projectsSettingsPage.getViewChooserSection().clickView(ViewType.MAP.getDefaultName());
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    RecentFavoriteMenu customLocationsPanel = locationsTab.getCustomLocations()
        .clickViewCustomLocation();
    List<RecentFavoriteMenuItem> customLocations = customLocationsPanel.getMenuItems();

    verificationStep("Verify that the no custom location left in the panel");
    Assert.assertEquals("Mo more custom location should left", 0, customLocations.size());
  }

  @Test
  public void testAddToFavoritesCustomRadiusLocation() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        100, RadiusUnits.METERS);

    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItem =
        ((RecentFavoriteMenuItemWithSubMenu) locationsTab
        .getCustomLocations().clickViewCustomLocation().getMenuItem(customRadiusLocationName));
    recentFavoriteMenuItem.clickOnMoreOptions().clickAddToFavorites();

    RecentFavoriteMenu favoritesLocations = locationsTab.clickFavorites();

    verificationStep("Verify that the custom location appears in the favorite locations list");
    Assert.assertTrue("The custom location missing from the favorite locations lists",
        favoritesLocations.isItemPresent(customRadiusLocationName));
  }

  @Test
  public void testCreateRadiusLocationFromRecentLocation() {

    Location location = Location.CHICAGO_IL_CITY;

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(location);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    RecentFavoriteMenu recentMenu = locationsTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentMenuItemWithSubMenu =
        (RecentFavoriteMenuItemWithSubMenu) recentMenu
        .getMenuItem(location.getName());
    CreateRadiusLocation createRadiusLocation = recentMenuItemWithSubMenu.clickOnMoreOptions()
        .clickCreateRadiusLocation();

    verificationStep(
        "Verify that the selected location is the actual location"
        + " in the create radius location dialog");
    Assert.assertEquals("The location is not the expected", location,
        createRadiusLocation.getSelectedLocation());
  }
  
  @Test
  public void testCreateRadiusLocationFromFavoriteLocation() {
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditBusinessesPage editBusinessesPage = (EditBusinessesPage) newViewPage.getActiveView()
    .clickCreate(ViewType.BUSINESSES);
    
    verificationStep("Verify that the \"" + EditViewWarning.BUSINESS_MISSING + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editBusinessesPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.BUSINESS_MISSING,
        editBusinessesPage.getActiveView().getErrorMessage());
    businessesTab = mapPage.getLdbSection().clickBusinesses();
    businessesTab.addRandomBusinesses();
    businessesPage = (BusinessesPage) editBusinessesPage.clickDone();
    
    verificationStep("Verify that Businesses Page is loaded");
    businessesPage.getActiveView().isLoaded();

    logger.trace("Add favorit location");
    RecentFavoriteMenu recentFavoriteMenu = ((LocationsTab) businessesPage.getLdbSection().getLocationsTab()).clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentFavoriteMenuItem = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu.getMenuItems().get(1);
    recentFavoriteMenuItem.clickToSelect();
    recentFavoriteMenuItem.clickOnMoreOptions().clickAddToFavorites();
    
    logger.trace("Create radius location from Favorite location");
    recentFavoriteMenu = ((LocationsTab) mapPage.getLdbSection().getLocationsTab()).clickFavorites();
    recentFavoriteMenuItem = (RecentFavoriteMenuItemWithSubMenu) recentFavoriteMenu.getMenuItems().get(0);
    recentFavoriteMenuItem.clickToSelect();
    CreateRadiusLocation createRadiusLocation = recentFavoriteMenuItem.clickOnMoreOptions().clickCreateRadiusLocation();
 
    createRadiusLocation.addRadiusLocation(5, "miles", customRadiusLocationName);
    
    verificationStep("Verify that Businesses Page is loaded");
    businessesPage.getActiveView().isLoaded();
    
    verificationStep("Verify that radius location active");
    Assert.assertEquals("Radius Location should be active", Location.RADIUS_LOCATION, businessesPage.getToolbar().getActiveLocation());
    
  }
  
  @Test
  public void testMaxRadiusMessageAppearForRadiusLocation() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        251, RadiusUnits.MILES);
    String message = "Please enter a radius less than or equal to 250 miles.";
    String messageOnSite = locationsTab.getMaximumRadiusExceededMessage();
    
    verificationStep("Verify that the Maximum Radius Exceeded message appeared");
    Assert.assertEquals("Message is not as displayed, should be " + message + "but was: " + messageOnSite, message, messageOnSite);
    
    CreateRadiusLocation createRadiusLocation = locationsTab.clickOkOnMessage();
    createRadiusLocation.chooseRadiusUnits(RadiusUnits.KILOMETERS);
    createRadiusLocation.enterRadiusSize(351);
    createRadiusLocation.clickSave();
    message = "Please enter a radius less than or equal to 350 kilometers.";
    messageOnSite = locationsTab.getMaximumRadiusExceededMessage();
    
    verificationStep("Verify that the Maximum Radius Exceeded message appeared");
    Assert.assertEquals("Message is not as displayed, should be " + message + "but was: " + messageOnSite, message, messageOnSite);
    
    createRadiusLocation = locationsTab.clickOkOnMessage();
    createRadiusLocation.chooseRadiusUnits(RadiusUnits.FEET);
    createRadiusLocation.enterRadiusSize(1320001);
    createRadiusLocation.clickSave();
    message = "Please enter a radius less than or equal to 250 miles.";
    messageOnSite = locationsTab.getMaximumRadiusExceededMessage();
    
    verificationStep("Verify that the Maximum Radius Exceeded message appeared");
    Assert.assertEquals("Message is not as displayed, should be " + message + "but was: " + messageOnSite, message, messageOnSite);
    
    createRadiusLocation = locationsTab.clickOkOnMessage();
    createRadiusLocation.chooseRadiusUnits(RadiusUnits.METERS);
    createRadiusLocation.enterRadiusSize(350001);
    createRadiusLocation.clickSave();
    message = "Please enter a radius less than or equal to 350 kilometers.";
    messageOnSite = locationsTab.getMaximumRadiusExceededMessage();
    
    verificationStep("Verify that the Maximum Radius Exceeded message appeared");
    Assert.assertEquals("Message is not as displayed, should be " + message + "but was: " + messageOnSite, message, messageOnSite);
    
    locationsTab.clickOkOnMessage();
  }

}
