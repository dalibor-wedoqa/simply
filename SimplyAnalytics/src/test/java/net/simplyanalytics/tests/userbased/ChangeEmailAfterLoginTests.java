package net.simplyanalytics.tests.userbased;

import javax.mail.Message;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DialogTitleAndContent;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.DialogPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.sections.header.windows.AlmostDoneDialog;
import net.simplyanalytics.pageobjects.sections.header.windows.ChangeEmailAddressWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

public class ChangeEmailAfterLoginTests extends TestBase {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private QuickReportPage quickReportPage;
  private String newEmailAddress = "qtester98+0001@gmail.com";
  
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
  public void testChangeEmailAddress() throws Exception {
    ChangeEmailAddressWindow changeEmailWindow = quickReportPage.getHeaderSection().clickUser().clickChangeEmailAddressButton();
    changeEmailWindow.enterNewEmailAddress(newEmailAddress);
    changeEmailWindow.enterRetypeEmailAddress(newEmailAddress);
    changeEmailWindow.enterCurrentPassword(SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    AlmostDoneDialog almostDoneDialog = changeEmailWindow.clickContinueButton();
    almostDoneDialog.clickOkButton();
    quickReportPage.getHeaderSection().clickUser().clickSignOut();
    
    logger.info("Get link from the email");
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubject(Emails.CHANGE_EMAIL_ADDRESS.getTitle());
    String prefix = "http://test.simplyanalytics.net/login.html";
    String link = prefix + emailUtils.getValueFromMail(prefix, message);
    
    logger.debug("Open link from the email");
    quickReportPage.openNewTab(link);
    DialogPage dialogPage = new DialogPage(driver);
    
    verificationStep("Verify that the correct dialog appears, " + "Title: "
        + DialogTitleAndContent.CHANGE_EMAIL_ADDRESS.getTitle() + " Message: "
        + DialogTitleAndContent.CHANGE_EMAIL_ADDRESS.getMessages());
    Assert.assertEquals("Wrong dialog title", DialogTitleAndContent.CHANGE_EMAIL_ADDRESS.getTitle(),
        dialogPage.getTitle());
    Assert.assertEquals("Wrong dialog message", DialogTitleAndContent.CHANGE_EMAIL_ADDRESS.getMessages(),
        dialogPage.getMessages());
    
    verificationStep("Verify that user can login with new email address");
    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    quickReportPage = (QuickReportPage) signInPage.signIn(newEmailAddress, SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    quickReportPage.getHeaderSection().clickUser().clickSignOut();
  }
  
  @After
  public void after() throws Exception {
    sleep(5000);
    institutionPage = new AuthenticateInstitutionPage(getDriver());
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    quickReportPage = (QuickReportPage) signInPage.signIn(newEmailAddress, 
        SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    
    ChangeEmailAddressWindow changeEmailWindow = quickReportPage.getHeaderSection().clickUser().clickChangeEmailAddressButton();
  
    changeEmailWindow.enterNewEmailAddress(SignUser.USER_CHANGE_CREDENTIALS);
    changeEmailWindow.enterRetypeEmailAddress(SignUser.USER_CHANGE_CREDENTIALS);
    changeEmailWindow.enterCurrentPassword(SignUser.USER_CHANGE_CREDENTIALS_PASSWORD);
    AlmostDoneDialog almostDoneDialog = changeEmailWindow.clickContinueButton();
    almostDoneDialog.clickOkButton();
    
    quickReportPage.getHeaderSection().clickUser().clickSignOut();
    
    logger.info("Get link from the email");
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubject(Emails.CHANGE_EMAIL_ADDRESS.getTitle());
    String prefix = "http://test.simplyanalytics.net/login.html";
    String link = prefix + emailUtils.getValueFromMail(prefix, message);
    
    logger.debug("Open link from the email");
    quickReportPage.openNewTab(link);
    DialogPage dialogPage = new DialogPage(driver);
    unlockUser();
  }
  
}
