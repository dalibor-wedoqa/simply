package net.simplyanalytics.tests.userbased;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.sections.header.windows.ChangePasswordWindow;

public class ChangePasswordAfterLoginTests extends TestBase {
  
  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private QuickReportPage quickReportPage;
  private String newPassword = "testpassword";
  
  @Before
  public void before() {
    institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    quickReportPage = (QuickReportPage) signInPage.signIn(SignUser.USER_CHANGE_CREDENTIALS, 
        SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
  }
  
  @Test
  public void testChangePassword() throws Exception {
    ChangePasswordWindow changePasswordWindow = quickReportPage.getHeaderSection().clickUser().clickChangePasswordButton();
    changePasswordWindow.enterOldPassword(SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    changePasswordWindow.enterNewPassword(newPassword);
    changePasswordWindow.retypeNewPassword(newPassword);
    changePasswordWindow.clickSaveButton();
    
    verificationStep("Verify that 'Your password has been changed.' alert message appears");
    Assert.assertEquals("Message is not correct", 
        "Your password has been changed.", quickReportPage.getAlertMessage());
    
    quickReportPage.getHeaderSection().clickUser().clickSignOut();
    
    verificationStep("Verify that user can login with new password");
    institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    quickReportPage = (QuickReportPage) signInPage.signIn(SignUser.USER_CHANGE_CREDENTIALS, newPassword);
    
    quickReportPage.getHeaderSection().clickUser().clickSignOut();
  }
  
  @After
  public void after() throws Exception {
    institutionPage = new AuthenticateInstitutionPage(getDriver());
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    quickReportPage = (QuickReportPage) signInPage.signIn(SignUser.USER_CHANGE_CREDENTIALS, 
        newPassword);
    
    ChangePasswordWindow changePasswordWindow = quickReportPage.getHeaderSection().clickUser().clickChangePasswordButton();
    changePasswordWindow.enterOldPassword(newPassword);
    changePasswordWindow.enterNewPassword(SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    changePasswordWindow.retypeNewPassword(SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    changePasswordWindow.clickSaveButton();
    
    quickReportPage.getHeaderSection().clickUser().clickSignOut();
    unlockUser();
  }
}
