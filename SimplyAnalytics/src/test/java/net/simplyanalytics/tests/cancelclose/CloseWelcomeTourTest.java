package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CloseWelcomeTourTest extends TestBase {

  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;

  /**
   * Signing in.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
  }

  @Test
  public void closeWelcomeTourDialog() {

    welcomeScreenTutorialWindow.clickClose();

    verificationStep("Verify that the Welcome Tour dialog is disappeared");
    Assert.assertFalse("The Welcome Tour screen should be disappeared",
        welcomeScreenTutorialWindow.welcomeScreenWindowIsDisplayed());
  }
}
