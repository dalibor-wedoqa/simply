package net.simplyanalytics.tests.view.map;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AutoLocationTypeTests extends TestBase {
  MapToolbar mapToolbar;
  Location blockGroupLocation = Location.BLOCK_GROUP_BG1033001_CUYAHOGA_COUNTY_OH;
  Location censusTractLocation = Location.CENSUS_TRACT_CT830500_COOK_COUNTY_IL;
  Location zipCodeLocation = Location.CHICAGO_IL_CITY;
  Location countryLocation = Location.USA;

  /**
   * Sign-in, creating a new project and addig locations.
   */
  @Before
  public void before() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    mapToolbar = mapPage.getToolbar();
    LocationsTab locationsTab = mapPage.getLdbSection().clickLocations();
    locationsTab.chooseLocation(blockGroupLocation);
    locationsTab.chooseLocation(censusTractLocation);
  }

  @Test
  public void testAutoLocationType() {
    // Block group location
    mapToolbar.openLocationListMenu().clickLocation(blockGroupLocation);
    mapToolbar.openLocationTypeListMenu().clickAutoLocationType();
    verificationStep("Verify that the auto location type is " + LocationType.BLOCK_GROUP);
    Assert.assertEquals("The outline color should be changed", LocationType.BLOCK_GROUP,
        mapToolbar.getActiveLocationType());
    // Census tract location
    mapToolbar.openLocationListMenu().clickLocation(censusTractLocation);
    mapToolbar.openLocationTypeListMenu().clickAutoLocationType();
    verificationStep("Verify that the auto location type is " + LocationType.CENSUS_TRACT);
    Assert.assertEquals("The outline color should be changed", LocationType.CENSUS_TRACT,
        mapToolbar.getActiveLocationType());
    // Zip code Location
    mapToolbar.openLocationListMenu().clickLocation(zipCodeLocation);
    mapToolbar.openLocationTypeListMenu().clickAutoLocationType();
    verificationStep("Verify that the auto location type is " + LocationType.ZIP_CODE);
    Assert.assertEquals("The outline color should be changed", LocationType.ZIP_CODE,
        mapToolbar.getActiveLocationType());
    // countries location
    mapToolbar.openLocationListMenu().clickLocation(countryLocation);
    mapToolbar.openLocationTypeListMenu().clickAutoLocationType();
    verificationStep("Verify that the auto location type is " + LocationType.COUNTY);
    Assert.assertEquals("The outline color should be changed", LocationType.COUNTY,
        mapToolbar.getActiveLocationType());
  }
}
