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

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class CheckWarningsTests extends TestBase {
  private ProjectSettingsPage projectSettingsPage;
  
  private List<DataVariable> actualDataVariables = 
  		  Arrays.asList(DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024,
  		  DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024);
                                    
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
    projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
  }

  @Test
  public void testCheckWarningIfLocationsDeleted() {
    projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton().deleteAllViews();

    List<Location> actualLocations = new ArrayList<>();
    actualLocations.add(Location.LOS_ANGELES_CA_CITY);
    actualLocations.add(Location.USA);// default value
    actualLocations.add(Location.CHICAGO_IL_CITY);

    ManageProjectLdbPanel manageProjectLdbPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().getItemsPanel();
    
    verificationStep(
        "Verify that locations (" + actualLocations + ") appears on the location panel");
    Assert.assertEquals(
        "The locations are not the expected", actualLocations.stream()
            .map(location -> location.getNameWithType()).collect(Collectors.toList()),
            manageProjectLdbPanel.getItemsName());

    manageProjectLdbPanel.clickX(Location.LOS_ANGELES_CA_CITY.getNameWithType());
    actualLocations.remove(Location.LOS_ANGELES_CA_CITY);
    manageProjectLdbPanel.clickX(Location.USA.getNameWithType());
    actualLocations.remove(Location.USA);
    manageProjectLdbPanel.clickX(Location.CHICAGO_IL_CITY.getNameWithType());
    actualLocations.remove(Location.CHICAGO_IL_CITY);

    verificationStep("Verify that check warning appears if locations are deleted");
    Assert.assertEquals("The warning message is not expected", manageProjectLdbPanel.getEmptyListMessage(),
        "There are no locations currently associated with this project");
  }

  @Test
  public void testCheckWarningIfDataVariablesDeleted() {
    projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton().deleteAllViews();
    ManageProjectLdbPanel manageProjectLdbPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickData();

    verificationStep("Verify that data variable (" + actualDataVariables
        + ") appears on the data variable panel");
    List<String> data = actualDataVariables.stream().map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList());
    assertThat("List equality without order", 
            data, containsInAnyOrder(manageProjectLdbPanel.getItemsName().toArray()));

    manageProjectLdbPanel.clickX(actualDataVariables.get(0).getFullName());
    manageProjectLdbPanel
        .clickX(actualDataVariables.get(1).getFullName());

    verificationStep("Verify that check warning appears if data variables are deleted");
    Assert.assertEquals("The warning message is not expected", manageProjectLdbPanel.getEmptyListMessage(),
        "There are no data variables currently associated with this project");
  }

}
