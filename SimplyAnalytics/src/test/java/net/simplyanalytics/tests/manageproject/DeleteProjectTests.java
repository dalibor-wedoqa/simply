package net.simplyanalytics.tests.manageproject;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.windows.DeleteProjectWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteProjectTests extends TestBase {
  
  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;
  private DeleteProjectWindow deleteProjectWindow;
  private NewViewPage newViewPage;
  private ProjectSettingsPage projectSettingsPage;
  private DataVariable dataVariable = firstDefaultDataVariable;
  
  /**
   * Signing in.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    
  }
  
  /**
   * Steps: Login with valid institution and valid password Click sign in as quest
   * Click close on welcome screen Enter Los Angeles,CA., City then Click Create
   * Project and Click Got it Click Open Project and open "New Project" Click
   * Delete Button at Manage Project Page Click Delete Button at Delete Window
   * Expected result: Verify that the project is deleted and that New Project
   * window is open
   */
  @Test
  public void testDeleteProject() {
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickOpenProject()
        .clickToOpenProjectWithGivenName("New Project").getHeaderSection().clickProjectSettings();    
    deleteProjectWindow = projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().clickDeleteProject();
    createNewProjectWindow = (NewProjectLocationWindow) deleteProjectWindow.clickDelete();
    newViewPage = createNewProjectWindow.clickClose();
    
    verificationStep("Verify that the \"Please create a new view and add some locations and data variables to get started. If you have any questions or issues click on the support button at the top of the page.\" message appears");
    Assert.assertTrue("The new project message missing",
        newViewPage.getActiveView().isNewViewMessagePresent());
  }
  
  @Test
  public void testDeleteMoreProject() {
    int temp=0;
    while(temp<5) {
      createNewProjectWindow = mapPage.getHeaderSection().clickNewProject();
      mapPage = createNewProjectWindow
          .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

      verificationStep("Verify that the toolbar active location and data variable are the expected");
      Assert.assertEquals("The present data variable is not the default",
          dataVariable, mapPage.getToolbar().getActiveDataVariable());
      Assert.assertEquals("The present Location is not the selected", Location.WASHINGTON_DC_CITY,
          mapPage.getToolbar().getActiveLocation());
      temp++;
    }
    projectSettingsPage = mapPage.getHeaderSection().clickOpenProject()
        .clickToOpenProjectWithGivenName("New Project").getHeaderSection().clickProjectSettings(); 
    while(temp>0) {
      deleteProjectWindow = projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().clickDeleteProject();

      projectSettingsPage = ((BaseViewPage) deleteProjectWindow.clickDelete()).getHeaderSection().clickProjectSettings();
      temp--;
    }
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().clickDeleteProject();
    deleteProjectWindow.clickDelete();
    
  }

}
