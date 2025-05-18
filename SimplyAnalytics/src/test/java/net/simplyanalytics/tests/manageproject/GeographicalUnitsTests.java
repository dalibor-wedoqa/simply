package net.simplyanalytics.tests.manageproject;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.GeneralSettingsPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.GeneralSettingsPanel.EnableGeographicUnitsPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.view.map.LegendMapCenterPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class GeographicalUnitsTests extends TestBase {
  
  private MapPage mapPage;
  
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);
  }
  
  @Test
  public void testCorrectGeograpicUnitIsEnabled() {
    GeneralSettingsPanel generalSettingsPagen = (GeneralSettingsPanel) mapPage.getHeaderSection().clickProjectSettings().getActiveView();
    EnableGeographicUnitsPanel geographicalUnitsPanel = generalSettingsPagen.getEnableGeographicUnitsPanel();
    
    verificationStep("Verify that the correct geographic unit is preselected");
    Assert.assertTrue("Nielsen Designated Marketing Area chould be selected", geographicalUnitsPanel.isCheckboxSelected(LocationType.NIELSEN_DESIGNATED_MARKETING_AREA));
    
    for(LocationType locationType: LocationType.values()) {
      if (locationType == LocationType.NIELSEN_DESIGNATED_MARKETING_AREA) {
        Assert.assertTrue("Location type: " + locationType.getPluralName() + "chould be not selected", 
            geographicalUnitsPanel.isCheckboxSelected(locationType));
      }
    }
  }
  
  @Test
  public void testEnableGeographicaUnits() {   
    LocationType[] locationTypeList = {LocationType.REGIONS, LocationType.DIVISIONS, LocationType.CORE_BASED_STATISTICAL_AREAS, 
        LocationType.CONGRESS_DISTRICTS, LocationType.STATE_UPPER_DISTRICTS, LocationType.STATE_LOWER_DISTRICTS, 
        LocationType.SECONDARY_SCHOOL_DISTRICTS, LocationType.ELEMENTARY_SCHOOL_DISTRICTS};
    for(LocationType locationType : locationTypeList) {
      ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
      GeneralSettingsPanel generalSettingsPage = (GeneralSettingsPanel) projectSettingsPage.getActiveView();
      EnableGeographicUnitsPanel geographicalUnitsPanel = generalSettingsPage.getEnableGeographicUnitsPanel();
      geographicalUnitsPanel.clickCheckbox(locationType);
      
      projectSettingsPage.getViewChooserSection().clickView("Map");
      
      LegendMapCenterPanel legendMapCenterPanel = mapPage.getActiveView().getLegend().clickOnMapCenter();
      List<LocationType> locationTypeListPresent = legendMapCenterPanel.getLocationTypeList();
      
      verificationStep("Verify that 'region' location type is present on the list");
      Assert.assertTrue("Region location type is not present", locationTypeListPresent.contains(locationType));
      
      mapPage.getActiveView().getLegend().clickOnMapCenter();
    }
  }
}
