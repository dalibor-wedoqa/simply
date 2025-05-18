package net.simplyanalytics.tests.userbased;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *.
 * @author wedoqa
 */
public class EditComboLocationTests extends BaseUserBasedTest {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private ProjectSettingsPage projectSettingsPage;
  private MapPage mapPage;
  private LocationsTab locationsTab;
  private final String customCombinatioLocationName = "Combo Location Cal & Flo";

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {

    driver.manage().deleteAllCookies();
    institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    NewProjectLocationWindow newProjectLocationWindow = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickNewProject();
   
    //Need time to the new project window to appear in case it wants to appear
    sleep(2000);
    if (NewProjectLocationWindow.isPresent(NewProjectLocationWindow.class, driver)) {
      mapPage = newProjectLocationWindow.createNewProjectWithStateAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    }
  }

  @Test
  public void testEditComboLocation() {

    locationsTab = (LocationsTab) mapPage.getLdbSection().getLocationsTab();

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickShowCustomLocations()
        .clickViewCustomLocation();

    if (!recentFavoriteMenu.isItemPresent(customCombinatioLocationName)) {
      locationsTab.clickHideCustomLocation();
      locationsTab.addNewCustomCombinationLocation(customCombinatioLocationName,
          Location.CALIFORNIA, Location.FLORIDA);
    } else {
      recentFavoriteMenu.getMenuItem(customCombinatioLocationName).clickToSelect();

      DataTab dataTab = mapPage.getLdbSection().getDataTab();
      ((DataByCategoryPanel) dataTab.getBrowsePanel()).chooseRandomDataVariable(Page.MAP_VIEW);
      mapPage.getToolbar().openLocationListMenu().clickComboLocation();
      //mapPage = new MapPage(driver);
    }
    
    String activeLocationName = mapPage.getToolbar().getNameOfActiveLocation();

    verificationStep("Verify that the created custom location " + customCombinatioLocationName
        + " is the active one");
    Assert.assertEquals("The created custom location is not the active one",
        customCombinatioLocationName.trim(), activeLocationName.trim());

    projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();

    verificationStep("Verify that the created custom location " + customCombinatioLocationName
        + " is present at the manage project locations");
    Assert.assertTrue("The created custom location is not present at the manage project locations",
        projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickLocations().getItemsName()
            .contains(customCombinatioLocationName.trim()));

    projectSettingsPage.getHeaderSection().clickUser().clickSignOut();

    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    projectSettingsPage = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickProjectSettings();

    mapPage = (MapPage) projectSettingsPage.getViewChooserSection()
        .clickView(ViewType.MAP.getDefaultName());
    verificationStep("Verify that the created custom location " + customCombinatioLocationName
        + " is the active one at the Map page");
    Assert.assertEquals("The created custom location is not the active one",
        customCombinatioLocationName.trim(), activeLocationName.trim());
    
    mapPage.getToolbar().openLocationListMenu().clickComboLocation();
    locationsTab = mapPage.getLdbSection().clickLocations();
    recentFavoriteMenu = locationsTab.clickShowCustomLocations().clickViewCustomLocation();

    boolean isCustomLocationSelected = false;

    if (recentFavoriteMenu.isItemPresent(activeLocationName)) {
      isCustomLocationSelected = recentFavoriteMenu.getMenuItem(activeLocationName).isSelected();
    }
    verificationStep("Verify that the created custom location " + customCombinatioLocationName
        + " is selected at the view custom location");
    Assert.assertTrue("The created custom location is not selected", isCustomLocationSelected);

    projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();

    verificationStep("Verify that the created custom location " + customCombinatioLocationName
        + " is present at the manage project locations");
    Assert.assertTrue("The created custom location is not present at the manage project locations",
        projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickLocations().getItemsName()
            .contains(customCombinatioLocationName.trim()));
  }
  
  @After
  public void after() {
    unlockUser();
  }
  
}