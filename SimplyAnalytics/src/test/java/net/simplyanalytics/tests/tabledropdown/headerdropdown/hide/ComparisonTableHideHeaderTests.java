package net.simplyanalytics.tests.tabledropdown.headerdropdown.hide;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparisonTableHideHeaderTests extends TestBase {
  
  private ComparisonReportPage comparisonReportPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable houseIncome = medianDefaultDataVariable;
  
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
  public void testHideColumnHeaderLocation() {
    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickHideLocation();
    
    verificationStep("Verify that the location disappeared");
    List<String> columnHeaderValues = comparisonReportPage.getActiveView().getNormalHeaderValues();
    Assert.assertFalse("The location is still present",
        columnHeaderValues.contains(losAngeles.getName()));
  }
  
  @Test
  public void testHideRowHeaderDataVariable() {
    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickHideDataVariable();
    
    verificationStep("Verify that the data variable disappeared");
    List<String> rowHeaderValues = comparisonReportPage.getActiveView().getRowHeaderValues();
    Assert.assertFalse("The data variable is still present",
        rowHeaderValues.contains(houseIncome.getFullName()));
  }
  
  @Test
  public void testHideColumnHeaderDataVariable() {
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();
    
    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickHideDataVariable();
    
    verificationStep("Verify that the data variable disappeared");
    List<String> columnHeaderValues = comparisonReportPage.getActiveView().getNormalHeaderValues();
    Assert.assertFalse("The data variable is still present",
        columnHeaderValues.contains(houseIncome.getFullName()));
  }
  
  @Test
  public void testHideRowHeaderLocation() {
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();
    
    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickHideLocation();
    
    verificationStep("Verify that the location disappeared");
    List<String> rowHeaderValues = comparisonReportPage.getActiveView().getRowHeaderValues();
    Assert.assertFalse("The location is still present",
        rowHeaderValues.contains(losAngeles.getName()));
  }
}
