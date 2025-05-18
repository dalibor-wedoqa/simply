package net.simplyanalytics.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class DefaultDataTests extends TestBase {
  
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  
  /**
   * Signing in.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
  }
    
  @Test
  @Severity(SeverityLevel.CRITICAL)
  public void testNewProjectDataVariablesWindowAppeareance() {
    
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    
    verificationStep("Verify that the default selected country is the USA");
    Assert.assertEquals("The selected country is not the default", "USA",
        createNewProjectWindow.getActiveCountry());
    
    NewProjectVariablesWindow newProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    
    verificationStep("Verify that the expected 3 default data variables are selected in the dialog");
    Assert.assertEquals("The present default data variables are not the expected",
        defaultDataVariables,
        newProjectVariablesWindow.getSelectedDataVariables());
    
    MainPage mainPage = newProjectVariablesWindow.clickCreateProjectButton();
    ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mainPage
        .getViewChooserSection().clickView(ViewType.COMPARISON_REPORT.getDefaultName());
    
    verificationStep("Verify that the default data variables appear in the comparison table");
    Assert.assertEquals("The present default data variables are not the expected",
        defaultDataVariables,
        comparisonReportPage.getActiveView().getDataVariables());
  }
  
  @Test
  public void testDataVariablesWithoutLocation() {
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    NewViewPage newViewPage = createNewProjectWindow
        .clickCreateButtonWithEmptyLocation();
    
    ProjectSettingsPage projectSettingsPage = newViewPage.getHeaderSection().clickProjectSettings();
    
    verificationStep("Verify that the \"There are no locations currently associated with this project\" text appear");
    Assert.assertEquals("The expected text is not present", 
        projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickLocations().getEmptyListMessage(), 
        "There are no locations currently associated with this project");
  }
  
  @Test
  public void testNewProjectDataVariablesWindowChangeVariable() {
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    NewProjectVariablesWindow newProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    newProjectVariablesWindow.clickDataVariable(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024.getName());
    newProjectVariablesWindow
        .clickDataVariable(medianDefaultDataVariable.getName());
    
    List<DataVariable> expectedVariables = new ArrayList<>();
    expectedVariables.add(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024);
    expectedVariables.addAll(defaultDataVariables);
    expectedVariables.remove(medianDefaultDataVariable);
    
    verificationStep("Verify that the selected data variables are selected in the dialog");
    Assert.assertEquals("The present default data variables are not the expected",
        expectedVariables, newProjectVariablesWindow.getSelectedDataVariables());
    
    MainPage mainPage = newProjectVariablesWindow.clickCreateProjectButton();
    
    ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mainPage
        .getViewChooserSection().clickView(ViewType.COMPARISON_REPORT.getDefaultName());
    
    verificationStep("Verify that the expected data variables appear in the comparison table");
    Assert.assertEquals("The present data variables are not the expected", expectedVariables,
        comparisonReportPage.getActiveView().getDataVariables());
  }
}
