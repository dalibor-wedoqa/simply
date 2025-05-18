package net.simplyanalytics.tests;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.toolbar.map.ByMapViewComboElements;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewProjectTests extends TestBase {

  private NewProjectLocationWindow createNewProjectWindow;
  private DataVariable dataVariable = firstDefaultDataVariable;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  @Test
  public void testNewProjectDataVariablesWindowAppeareance() {
    NewProjectVariablesWindow newProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    MapPage mapPage = newProjectVariablesWindow.clickCreateProjectButton();

    verificationStep("Verify that the toolbar active location and data variable are the expected");
    Assert.assertEquals("The present data variable is not the default",
        dataVariable, mapPage.getToolbar().getActiveDataVariable());
    Assert.assertEquals("The present Location is not the selected", Location.LOS_ANGELES_CA_CITY,
        mapPage.getToolbar().getActiveLocation());

    LegendPanel legendSection = mapPage.getActiveView().getLegend();

    verificationStep(
        "Verify that the legend title and map central text corresponds"
        + "  with the map and the selected location and variable");
    Assert.assertEquals("The legent tile and the header values are not matching",
        mapPage.getToolbar().getActiveDataVariable().getFullName() + " by "
            + mapPage.getToolbar().getActiveLocationType().getPluralName(),
        legendSection.getLegendTitle());
    Assert.assertEquals("The map center is not the expected",
        Location.ZIP_90034_LOS_ANGELES.getName(), legendSection.getMapCenter());
  }

  @Test
  public void testCreateTwoProjectsWithDifferentLocation() {
    NewProjectVariablesWindow newProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    MapPage mapPage = newProjectVariablesWindow.clickCreateProjectButton();
    createNewProjectWindow = mapPage.getHeaderSection().clickNewProject();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    verificationStep("Verify that the toolbar active location and data variable are the expected");
    Assert.assertEquals("The present data variable is not the default",
        dataVariable, mapPage.getToolbar().getActiveDataVariable());
    Assert.assertEquals("The present Location is not the selected", Location.WASHINGTON_DC_CITY,
        mapPage.getToolbar().getActiveLocation());

    LegendPanel legendSection = mapPage.getActiveView().getLegend();

    verificationStep(
        "Verify that the legend title and map central"
        + " text corresponds  with the map and the selected location and variable");
    Assert.assertEquals("The legent tile and the header values are not matching",
        mapPage.getToolbar().getActiveDataVariable().getFullName() + " by "
            + mapPage.getToolbar().getActiveLocationType().getPluralName(),
        legendSection.getLegendTitle());
    Assert.assertEquals("The map center is not the expected",
        Location.ZIP_20001_WASHINGTON_DC.getName(), legendSection.getMapCenter());

  }

  @Test
  public void testCreateProjectsChangeViewToStates() {
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.MIAMI_FL_CITY);
    ByMapViewComboElements byMapViewComboElements = mapPage.getToolbar().openLocationTypeListMenu();
    byMapViewComboElements.clickLocationType(LocationType.STATE);

    verificationStep("Verify that the toolbar active location and data variable are the expected");
    Assert.assertEquals("The present data variable is not the default",
        dataVariable, mapPage.getToolbar().getActiveDataVariable());
    Assert.assertEquals("The present Location is not the selected", Location.MIAMI_FL_CITY,
        mapPage.getToolbar().getActiveLocation());

    LegendPanel legendPanel = mapPage.getActiveView().getLegend();

    verificationStep(
        "Verify that the legend title and map central text corresponds"
        + "  with the map and the selected location and variable");
    Assert.assertEquals("The legent tile and the header values are not matching",
        mapPage.getToolbar().getActiveDataVariable().getFullName() + " by "
            + mapPage.getToolbar().getActiveLocationType().getPluralName(),
        legendPanel.getLegendTitle());
    Assert.assertEquals("The map center is not the expected", Location.FLORIDA.getName(),
        legendPanel.getMapCenter());

  }

  @Test
  public void testOpenProject() {
    MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    
    createNewProjectWindow = mapPage.getHeaderSection().clickNewProject();
    
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    ProjectSettingsPage projectSettingPage = mapPage.getHeaderSection().clickOpenProject()
        .clickToOpenProjectWithGivenName("New Project").getHeaderSection().clickProjectSettings();

    verificationStep("Verify that the Manage Project button in the header is blue");
    Assert.assertTrue("Manage Project button should be blue ", projectSettingPage.getHeaderSection().isManageProjectButtonBlue());
  }

  @Test
  public void testOpenAndRenameProject() {
    String originalProjectName = "New Project";
    String renamedProjectName = "New Project 1";

    MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    
    createNewProjectWindow = mapPage.getHeaderSection().clickNewProject();
    
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickOpenProject()
        .clickToOpenProjectWithGivenName(originalProjectName).getHeaderSection().clickProjectSettings();
    projectSettingsPage.getHeaderSection().renameProjectName(renamedProjectName);

    verificationStep("Verify that the project name updated correctly");
    Assert.assertTrue("Project doesn't have the new given Name", renamedProjectName
        .equalsIgnoreCase(projectSettingsPage.getHeaderSection().getNameOfCurrentProject()));
  }

}
