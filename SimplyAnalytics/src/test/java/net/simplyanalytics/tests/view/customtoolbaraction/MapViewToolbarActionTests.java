package net.simplyanalytics.tests.view.customtoolbaraction;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.RadiusUnits;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.sections.view.editview.BaseEditViewPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapViewToolbarActionTests extends TestBase {

  private MapPage mapPage;
  private LegendPanel legendPanel;
  private MapToolbar mapToolbar;
  private MapViewPanel mapViewPanel;
  private DataVariable expectedDataVariable = defaultDataVariables.get(1);

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
    mapToolbar = mapPage.getToolbar();
    legendPanel = mapPage.getActiveView().getLegend();
  }

  @Test
  public void dataVariablesTest() {

    mapToolbar.openDataVariableListMenu()
        .clickDataVariable(expectedDataVariable);

    verificationStep("Verify that the actual data variable is the selected");
    Assert.assertEquals("The actual data variable is not the selected",
        expectedDataVariable,
        mapToolbar.getActiveDataVariable());

    verificationStep("Verify that the actual data variable is mentioned in the legend panel");
    Assert.assertEquals("The legend panel title should contain the data variable name",
        expectedDataVariable, legendPanel.getActiveData());

    BaseEditViewPanel baseEditViewPanel = mapToolbar.clickViewActions().clickEditView()
        .getActiveView();

    verificationStep("Verify that the actual data variable is the selected");
    Assert.assertEquals("The actual data variable is not the selected",
        expectedDataVariable,
        baseEditViewPanel.getActiveDataVariable());

  }

  @Test
  public void dataVariablesNoneTest() {

    mapToolbar.openDataVariableListMenu().clickNoneButton();

    verificationStep("Verify that the actual data variable is 'None'");
    Assert.assertEquals("The actual data variable is 'None'", "None",
        mapToolbar.getNameOfActiveDataVariable());

  }

  @Test
  public void dataVariablesNoneCustomRadiusLocationTest() {
    String customRadiusLocationName = "Radius Location";

    LocationsTab locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomRadiusLocation(Location.LOS_ANGELES_CA_CITY, customRadiusLocationName,
        20, RadiusUnits.MILES);

    mapToolbar.openDataVariableListMenu().clickNoneButton();

    verificationStep("Verify that the data variable combo is closed");
    Assert.assertFalse("The the data variable combo should be closed",
        mapToolbar.isDataVariableComboPresent());

    verificationStep("Verify that the legend panel has been updated.");
    Assert.assertFalse("The legend panel should be updated.",
        legendPanel.mapColorRangesIsDisappeared() && legendPanel.isLegendTitleDisplayed());
  }

  @Test
  public void dataVariablesNoneCustomCombinationLocationTest() {
    String customCombinationLocationName = "Combination location";
    LocationsTab locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();
    locationsTab.addNewCustomCombinationLocation(customCombinationLocationName, Location.CALIFORNIA,
        Location.FLORIDA);

    mapToolbar.openDataVariableListMenu().clickNoneButton();

    verificationStep("Verify that the data variable combo is closed");
    Assert.assertFalse("The the data variable combo should be closed",
        mapToolbar.isDataVariableComboPresent());

    verificationStep("Verify that the legend panel has been updated.");
    Assert.assertFalse("The legend panel should be updated.",
        legendPanel.mapColorRangesIsDisappeared() && legendPanel.isLegendTitleDisplayed());
  }

  @Test
  public void locationTest() {
    mapToolbar.openLocationListMenu().clickLocation(Location.USA);

    verificationStep("Verify that the actual location is 'USA'");
    Assert.assertEquals("The actual data variable is not 'USA'", Location.USA,
        mapToolbar.getActiveLocation());
    verificationStep("Verify that the map center is changed in the legend panel");
    Assert.assertTrue("The legend panel map center should changed",
        legendPanel.getMapCenter().contains(Location.COUNTY_POTTAWATTAMIE_IA.getName()));

    BaseEditViewPanel baseEditViewPanel = mapToolbar.clickViewActions().clickEditView()
        .getActiveView();

    verificationStep("Verify that the actual location is 'USA'");
    Assert.assertEquals("The actual location is not the selected", Location.USA,
        baseEditViewPanel.getActiveLocation());
  }

  @Test
  public void locationNoneTest() {

    mapToolbar.openLocationListMenu().clickNoneButton();

    verificationStep("Verify that the actual location is 'None'");
    Assert.assertEquals("The actual location is 'None'", "None",
        mapToolbar.getNameOfActiveLocation());
  }

  @Test
  public void locationTypeTest() {

    mapToolbar.openLocationTypeListMenu().clickLocationType(LocationType.CITY);

    verificationStep("Verify that the actual location type is the selected");
    Assert.assertEquals("The actual location type is not the selected", LocationType.CITY,
        mapToolbar.getActiveLocationType());

    verificationStep("Verify that the actual location type is mentioned in the legend panel");
    Assert.assertEquals("The legend panel title should contain the location type name",
        LocationType.CITY, legendPanel.getActiveLocationType());

    mapToolbar.openLocationTypeListMenu().clickAutoLocationType();

    verificationStep("Verify that the auto location type is mentioned in the legend panel");
    Assert.assertEquals("The legend panel title should contain the auto location type name",
        mapToolbar.getActiveLocationType(), legendPanel.getActiveLocationType());
  }

  @Test
  public void locationTypeZoomTest() {

    LocationType before = mapToolbar.getActiveLocationType();
    LocationType after;

    switch (mapToolbar.getActiveLocationType()) {
      case ZIP_CODE:
        mapViewPanel.getZoomSlider().clickZoomOutButton();
        mapViewPanel.getZoomSlider().clickZoomOutButton();
        break;
  
      case COUNTY:
        mapViewPanel.getZoomSlider().clickZoomInButton();
        mapViewPanel.getZoomSlider().clickZoomInButton();
        break;
  
      default:
        break;
    }
    after = mapToolbar.getActiveLocationType();

    verificationStep("Verify that the location type is changed after zoom");
    Assert.assertNotEquals("The location type is not changed after zoom", before, after);
  }

}
