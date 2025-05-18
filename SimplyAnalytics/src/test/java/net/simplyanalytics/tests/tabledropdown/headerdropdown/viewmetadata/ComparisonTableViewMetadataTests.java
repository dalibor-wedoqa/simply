package net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata;

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
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparisonTableViewMetadataTests extends TestBase {

  private ComparisonReportPage comparisonReportPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable houseIncome = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;

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
  public void testRowHeaderDataVariableViewMetadata() {
    MetadataWindow metadataWindow = comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickViewMetadata();

    verificationStep("Verify that the metadata windows contains the correct name and year");
    Assert.assertEquals("The name is not the expected", houseIncome.getName(),
        metadataWindow.getMetadataValue("Name"));
    Assert.assertEquals("The year is not the expected", "" + houseIncome.getYear(),
        metadataWindow.getMetadataValue("Year"));
  }

  @Test
  public void testColumnHeaderDataVariableViewMetadata() {
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();

    String houseIncomeName = houseIncome.getName();
    int houseIncomeYear = houseIncome.getYear();

    MetadataWindow metadataWindow = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickViewMetadata();

    String metaDataName = metadataWindow.getMetadataValue("Name");
    String metaDataYear = metadataWindow.getMetadataValue("Year");

    verificationStep("Verify that the metadata windows contains the correct name and year");
    Assert.assertEquals("The name is not the expected",houseIncomeName ,metaDataName);
    Assert.assertEquals("The year is not the expected", "" + houseIncomeYear,
            metaDataYear);
  }
}
