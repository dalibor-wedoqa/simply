package net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.sections.view.base.HeaderDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class RelatedDataTableCreateBarChartTests extends TestBase {
  private BarChartPage barChartPage;
  private MapPage mapPage;
  private RelatedDataReportPage relatedDataReportPage;
  private final List<Location> defaultLocations = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY, Location.USA));
  
  private final DataVariable expectedDataVariable = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
  
  /**
   * Signing in and creating new project.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow newProjectLocationWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = newProjectLocationWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }
  
  @Test
  public void testCreateBarChart() {
    relatedDataReportPage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.RELATED_DATA).clickDone();
    
    verificationStep(
        "Verify that the expected locations are present in the related data " + "report table");
    
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
        relatedDataReportPage.getActiveView().getLocations());
    
    HeaderDropdown headerDropdown = relatedDataReportPage.getActiveView()
        .openRowHeaderDropdown(expectedDataVariable.getFullName());
    barChartPage = headerDropdown.clickCreateBarChartButton();
    
    verificationStep("Verify that the selected data variable appear on the BarChart view");
    DataVariable actualDataVariable = barChartPage.getToolbar().getActiveDataVariable();
    Assert.assertEquals("The actual data variable is not the selected", expectedDataVariable,
        actualDataVariable);
  }
  
}
