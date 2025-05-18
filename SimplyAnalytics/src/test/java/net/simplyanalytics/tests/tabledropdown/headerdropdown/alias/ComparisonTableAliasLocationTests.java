package net.simplyanalytics.tests.tabledropdown.headerdropdown.alias;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparisonTableAliasLocationTests extends TestBase {
  
  private ComparisonReportPage comparisonReportPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  
  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(losAngeles);
    comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection()
        .clickView(ViewType.COMPARISON_REPORT.getDefaultName());
  }
  
  @Test
  public void testColumnHeaderAliasLocation() {
    String aliasName = "alias name";
    
    AliasLocationWindow aliasLocationWindow = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickAddAliasLocationName();
    
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    
    comparisonReportPage = new ComparisonReportPage(driver);
    
    String newName = comparisonReportPage.getActiveView().getNormalHeaderValues().get(0);
    
    verificationStep(
        "Verify that the new location name contains the alias name and the original name");
    String expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);
    
    aliasLocationWindow = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(expectedName).clickEditAliasLocationName();
    
    aliasName = "alias name edited";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    
    comparisonReportPage = new ComparisonReportPage(driver);
    
    newName = comparisonReportPage.getActiveView().getNormalHeaderValues().get(0);
    
    verificationStep(
        "Verify that the new location name contains the edited alias name and the original name");
    expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);
    
    aliasLocationWindow = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(expectedName).clickEditAliasLocationName();
    
    aliasName = "";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    
    comparisonReportPage = new ComparisonReportPage(driver);
    
    newName = comparisonReportPage.getActiveView().getNormalHeaderValues().get(0);
    
    verificationStep("Verify that the location name is the original name");
    Assert.assertEquals("The location name is not the expected", losAngeles.getName(), newName);
  }
  
  @Test
  public void testRowHeaderAliasLocation() {
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();
    
    String aliasName = "alias name";
    
    AliasLocationWindow aliasLocationWindow = comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickAddAliasLocationName();
    
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    
    comparisonReportPage = new ComparisonReportPage(driver);
    
    String newName = comparisonReportPage.getActiveView().getRowHeaderValues().get(0);
    
    verificationStep(
        "Verify that the new location name contains the alias name and the original name");
    String expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);
    
    aliasLocationWindow = comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickEditAliasLocationName();
    
    aliasName = "alias name edited";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    sleep(2000);
    
    comparisonReportPage = new ComparisonReportPage(driver);
    
    newName = comparisonReportPage.getActiveView().getRowHeaderValues().get(0);
    
    verificationStep(
        "Verify that the new location name contains the edited alias name and the original name");
    expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);
    
    aliasLocationWindow = comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickEditAliasLocationName();
    
    aliasName = "";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    
    comparisonReportPage = new ComparisonReportPage(driver);
    
    newName = comparisonReportPage.getActiveView().getRowHeaderValues().get(0);
    
    verificationStep("Verify that the location name is the original name");
    Assert.assertEquals("The location name is not the expected", losAngeles.getName(), newName);
  }
}
