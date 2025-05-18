package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.ResetPasswordPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResetPasswordPageCancelTest extends TestBase {

  private ResetPasswordPage resetPasswordPage;

  /**
   * Signing in and open the reset password page.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    resetPasswordPage = signInPage.clickResetPassword();
  }

  @Test
  public void cancelResetPassword() {

    resetPasswordPage.clickCancelButton();

    verificationStep("Verify that the Reset Password page is disappeared");
    Assert.assertFalse("The Reset Password page should be disappeared",
        resetPasswordPage.isDisplayed());
  }
}
