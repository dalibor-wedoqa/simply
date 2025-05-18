package net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RankingViewMetadataTests extends TestBase {
  
  private RankingPage rankingPage;
  private DataVariable houseIncome = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;
  
  /**
   * Signing in, creating new project and open the ranking page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    rankingPage = (RankingPage) mapPage.getViewChooserSection()
        .clickView(ViewType.RANKING.getDefaultName());
  }
  
  @Test
  public void testColumnHeaderDataVariableViewMetadata() {
    MetadataWindow metadataWindow = rankingPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickViewMetadata();
    
    verificationStep("Verify that the metadata windows contains the correct name and year");
    Assert.assertEquals("The name is not the expected", houseIncome.getName(),
        metadataWindow.getMetadataValue("Name"));
    Assert.assertEquals("The year is not the expected", "" + houseIncome.getYear(),
        metadataWindow.getMetadataValue("Year"));
  }
  
}
