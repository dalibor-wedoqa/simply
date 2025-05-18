package net.simplyanalytics.tests.manageproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageProjectLdbPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteLdbTests extends TestBase {

  private ProjectSettingsPage projectSettingsPage;

  private String business1;
  private String business2;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    mapPage.getLdbSection().getLocationsTab().chooseLocation(Location.CHICAGO_IL_CITY);
    business1 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    business2 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
  }

  @Test
  public void testDeleteLocation() {
    final ManageProjectLdbPanel manageProjectLdbPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickLocations();
    List<Location> actualLocations = new ArrayList<>();
    actualLocations.add(Location.LOS_ANGELES_CA_CITY);
    actualLocations.add(Location.USA);// default value
    actualLocations.add(Location.CHICAGO_IL_CITY);

    verificationStep(
        "Verify that locations (" + actualLocations + ") appears on the location panel");
    Assert.assertEquals(
        "The locations are not the expected", actualLocations.stream()
            .map(location -> location.getNameWithType()).collect(Collectors.toList()),
        manageProjectLdbPanel.getItemsName());

    projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickLocations();
    manageProjectLdbPanel.clickX(Location.LOS_ANGELES_CA_CITY.getNameWithType());
    actualLocations.remove(Location.LOS_ANGELES_CA_CITY);

    verificationStep(
        "Verify that locations (" + actualLocations + ") appears on the location panel");
    Assert.assertEquals(
        "The locations are not the expected", actualLocations.stream()
            .map(location -> location.getNameWithType()).collect(Collectors.toList()),
        manageProjectLdbPanel.getItemsName());
  }

  @Test
  public void testDeleteData() {
    ManageProjectLdbPanel manageProjectLdbPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickData();

    List<DataVariable> dataVariables = defaultDataVariables;
    List<String> dataVariablesWithFullName = dataVariables.stream().map(variable -> variable.getFullName()).collect(Collectors.toList());
    
    verificationStep("Verify that data variables (\""
        + dataVariables.get(0).getFullName() + "\", \""
        + dataVariables.get(1).getFullName()
        + "\") appears on the data panel");
    Assert.assertEquals("The data variables are not the expected", dataVariablesWithFullName, manageProjectLdbPanel.getItemsName());

    manageProjectLdbPanel
        .clickX(dataVariables.get(0).getFullName());
    
    dataVariablesWithFullName.remove(0);

    verificationStep("Verify that only data variables (\""
        + dataVariables.get(1).getFullName() + "\", \""
        + "\") appears on the data panel");
    Assert.assertEquals("The data variables are not the expected", dataVariablesWithFullName,
        manageProjectLdbPanel.getItemsName());
  }

  @Test
  public void testDeleteBusinesses() {
    ManageProjectLdbPanel manageProjectLdbPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickBusinesses();

    verificationStep("Verify that the selected businesses appear on the businesses panel");
    Assert.assertEquals("The data variables are not the expected",
        Arrays.asList(business1, business2), manageProjectLdbPanel.getItemsName());

    manageProjectLdbPanel.clickX(business1);

    verificationStep("Verify that the deleted business disappears from the businesses panel");
    Assert.assertEquals("The data variables are not the expected", Arrays.asList(business2),
        manageProjectLdbPanel.getItemsName());
  }
}
