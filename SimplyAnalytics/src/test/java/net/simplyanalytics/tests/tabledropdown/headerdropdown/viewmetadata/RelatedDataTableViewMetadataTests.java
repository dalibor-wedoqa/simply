package net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata;

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
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class RelatedDataTableViewMetadataTests extends TestBase {
  
  private RelatedDataReportPage relatedDataTablePage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable dataVariable = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
  
  /**
   * Signing in, creating new project and open the related data table page.
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
    relatedDataTablePage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.RELATED_DATA).clickDone();
  }
  
  @Test
  public void testRowHeaderDataVariableViewMetadata() {
    MetadataWindow metadataWindow = relatedDataTablePage.getActiveView()
        .openRowHeaderDropdown(dataVariable.getFullName()).clickViewMetadata();
    
    verificationStep("Verify that the metadata windows contains the correct name and year");
    Assert.assertEquals("The name is not the expected", dataVariable.getName(),
        metadataWindow.getMetadataValue("Name"));
    Assert.assertEquals("The year is not the expected", "" + dataVariable.getYear(),
        metadataWindow.getMetadataValue("Year"));
  }
}
