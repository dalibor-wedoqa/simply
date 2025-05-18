package net.simplyanalytics.tests.ldb.location;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.RadiusUnits;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * .
 * 
 * @author wedoqa
 */
public class CombinatedCombinationAndRadiusLocationTests extends TestBase {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;
  private LocationsTab locationsTab;
  private final String customRadiusLocationName = "Radius Location";
  private final String customRadiusLocationName2 = "Radius Location Name";
  private final String customCombinationLocationName = "Combination Location";
  private final String customCombinationLocationName2 = "Combination Location Name";

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
  public void testCreateRadiusAndCombinationLocations() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        10, RadiusUnits.KILOMETERS);
    locationsTab = (LocationsTab) new MapPage(driver).getLdbSection().getLocationsTab();

    verificationStep("Verify that the Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName));

    locationsTab.clickHideCustomLocation();

    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName,
        Location.CHICAGO_IL_CITY, Location.WASHINGTON_DC_CITY);
    locationsTab = (LocationsTab) new MapPage(driver).getLdbSection().getLocationsTab();

    verificationStep("Verify that the Combination Location appears in the custom location list");
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customCombinationLocationName));

    locationsTab.clickHideCustomLocation();

    locationsTab.addNewCustomRadiusLocation(Location.MIAMI_FL_CITY, customRadiusLocationName2, 20,
        RadiusUnits.KILOMETERS);
    locationsTab = (LocationsTab) new MapPage(driver).getLdbSection().getLocationsTab();

    verificationStep("Verify that the new Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName2));

    locationsTab.clickHideCustomLocation();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName2,
        Location.CALIFORNIA, Location.FLORIDA);
    locationsTab = (LocationsTab) new MapPage(driver).getLdbSection().getLocationsTab();

    verificationStep(
        "Verify that the new Combination Location appears in the custom location list");
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customCombinationLocationName2));
  }

  @Test
  public void testSelectDifferentRadiusAndCombinationLocations() {
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
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName,
        Location.CHICAGO_IL_CITY, Location.WASHINGTON_DC_CITY);

    verificationStep("Verify that the Combination Location appears in the custom location list");
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customCombinationLocationName));

    locationsTab.clickHideCustomLocation();
    locationsTab.addNewCustomRadiusLocation(Location.MIAMI_FL_CITY, customRadiusLocationName2, 20,
        RadiusUnits.KILOMETERS);

    verificationStep("Verify that the new Radius Location appears in the custom location list");
    Assert.assertTrue("Radius Location Name should be present at view custom locations",
        locationsTab.getCustomLocations().clickViewCustomLocation()
            .isItemPresent(customRadiusLocationName2));

    locationsTab.clickHideCustomLocation();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName2,
        Location.CALIFORNIA, Location.FLORIDA);

    verificationStep(
        "Verify that the new Combination Location appears in the custom location list");
    RecentFavoriteMenu recentFavoriteMenu = locationsTab.getCustomLocations()
        .clickViewCustomLocation();
    Assert.assertTrue("Combination Location Name should be present at view custom locations",
        recentFavoriteMenu.isItemPresent(customCombinationLocationName2));

  }

}
