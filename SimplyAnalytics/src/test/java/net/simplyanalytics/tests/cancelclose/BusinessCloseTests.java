package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesCategoriesPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusinessCloseTests extends TestBase {
  private MapPage mapPage;

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

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  @Test
  public void closeAdvancedBusinessSearch() {

    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = mapPage.getLdbSection()
        .clickBusinesses().clickUseAdvancedSearch();

    advancedBusinessSearchWindow.clickCloseAdvancedBusinessSearch();

    verificationStep("Verify that the Advanced Business Search dialog is disappeared");
    Assert.assertFalse("The Advanced Business Search dialog should be disappeared",
        advancedBusinessSearchWindow.isDisplayed());
  }

  @Test
  public void cancelAdvancedBusinessSearch() {

    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = mapPage.getLdbSection()
        .clickBusinesses().clickUseAdvancedSearch();

    advancedBusinessSearchWindow.clickCancelAdvancedBusinessSearch();

    verificationStep("Verify that the Advanced Business Search dialog is disappeared");
    Assert.assertFalse("The Advanced Business Search dialog should be disappeared",
        advancedBusinessSearchWindow.isDisplayed());
  }

  @Test
  public void closeBrowseBusinessCategories() {

    BusinessesCategoriesPanel businessesCategoriesPanel = mapPage.getLdbSection().clickBusinesses()
        .clickBusinessCategories();
    
    businessesCategoriesPanel.clickCloseBusinessesCategoriesPanel();
    
    verificationStep("Verify that the Businesses Categories Panel is disappeared");
    Assert.assertFalse("The Businesses Categories Panel should be disappeared",
        businessesCategoriesPanel.isDisplayed());
  }
}
