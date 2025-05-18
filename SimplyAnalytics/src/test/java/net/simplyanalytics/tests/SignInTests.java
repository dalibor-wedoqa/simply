package net.simplyanalytics.tests;

import javax.mail.Message;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DialogTitleAndContent;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.CreateAccountPage;
import net.simplyanalytics.pageobjects.pages.login.DialogPage;
import net.simplyanalytics.pageobjects.pages.login.ResetPasswordPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.header.Header.UserDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.suites.CreateAccountCategory;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import java.util.Set;


@RunWith(JUnit4.class)
public class SignInTests extends TestBase {

  private SignInPage signInPage;
  private AuthenticateInstitutionPage institutionPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  
  /**
   * Signing in.
   */
  @Before
  public void before() {
    driver.manage().window().maximize();
    institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  public void testSignInAsGuest() {
    System.out.println("Admin verified. Entering user");
    signInPage.clickSignInAsGuest();
    verificationStep("Verify that the Tutorial dialog appears");
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  public void testLoginWithValidUser() {
    signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2);
    verificationStep("Verify that the site opens correctly");
  }

  @Test
  public void testLoginWithWrongEmail() {
    signInPage = signInPage.failedSignIn(SignUser.WRONG_USER, SignUser.PASSWORD2);

    verificationStep("Verify that the correct error dialog appears: "
        + DialogTitleAndContent.USER_FAILED.getSingleMessage());

    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.USER_FAILED.getSingleMessage(), signInPage.getMessage());
  }

  @Test
  public void testLoginWithWrongPassword() {
    signInPage = signInPage.failedSignIn(SignUser.USER1, SignUser.WRONG_PASSWORD);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.USER_FAILED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.USER_FAILED.getSingleMessage(), signInPage.getMessage());
  }

  @Test
  public void testLoginWithWrongEmailAndPassword() {
    signInPage = signInPage.failedSignIn(SignUser.WRONG_USER, SignUser.WRONG_PASSWORD);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.USER_FAILED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.USER_FAILED.getSingleMessage(), signInPage.getMessage());
  }

  @Test
  public void testLoginWithEmptyEmail() {
    signInPage = signInPage.failedSignIn("", SignUser.PASSWORD1);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.USER_EMAIL_REQUIRED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.USER_EMAIL_REQUIRED.getSingleMessage(), signInPage.getMessage());
  }

  @Test
  public void testLoginWithEmptyPassword() {
    signInPage = signInPage.failedSignIn(SignUser.USER1, "");

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.USER_PASSWORD_REQUIRED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.USER_PASSWORD_REQUIRED.getSingleMessage(), signInPage.getMessage());
  }

  @Test
  public void testLoginWithEmptyEmailAndPassword() {
    signInPage = signInPage.failedSignIn("", "");

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.USER_EMAIL_REQUIRED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.USER_EMAIL_REQUIRED.getSingleMessage(), signInPage.getMessage());
  }



  @Test
  public void testLogoutAsGuest() {
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    NewProjectVariablesWindow newProjectVariablesWindow = createNewProjectWindow.clickNextButton();
    NewViewPage newViewPage = newProjectVariablesWindow
        .clickCreateProjectButtonEmptyLocation();

    UserDropdown userDropdown = newViewPage.getHeaderSection().clickUser();
    userDropdown.clickSignOut();
    verificationStep("Verify that the sites home page appears");
  }

  @Test
  @Category(CreateAccountCategory.class)
  public void testCreateAccount() throws Exception {
    String randomNumber = RandomStringUtils.randomNumeric(8);
    String newEmail = GMailUser.USER + "+" + randomNumber + GMailUser.HOST;
    String password = RandomStringUtils.randomAlphanumeric(16);

    CreateAccountPage createAccountPage = signInPage.clickCreateAccount();

    createAccountPage.enterEmail(newEmail);
    createAccountPage.enterPassword(password);
    createAccountPage.enterPasswordAgain(password);
    createAccountPage.checkEula();
    DialogPage dialogPage = createAccountPage.clickCreateAccount();

    verificationStep("Verify that the correct dialog appears, " + "Title: "
        + DialogTitleAndContent.USER_CREATED.getTitle() + " Message: "
        + DialogTitleAndContent.USER_CREATED.getMessages());
    Assert.assertEquals("Wrong dialog title", DialogTitleAndContent.USER_CREATED.getTitle(),
        dialogPage.getTitle());
    Assert.assertEquals("Wrong dialog message", DialogTitleAndContent.USER_CREATED.getMessages(),
        dialogPage.getMessages());

    dialogPage.clickOk();
    signInPage = new SignInPage(driver);

    verificationStep("Verify that the email field contains the email");
    Assert.assertEquals("The email field should be filled", newEmail, signInPage.getEmail());

    signInPage.enterPass(password);

    signInPage.clickVoidSignIn();
    dialogPage = new DialogPage(driver);

    verificationStep("Verify that the correct dialog appears, " + "Title: "
        + DialogTitleAndContent.USER_INACTIVE.getTitle() + " Message: "
        + DialogTitleAndContent.USER_INACTIVE.getMessages());
    Assert.assertEquals("Wrong dialog title", DialogTitleAndContent.USER_INACTIVE.getTitle(),
        dialogPage.getTitle());
    Assert.assertEquals("Wrong dialog message", DialogTitleAndContent.USER_INACTIVE.getMessages(),
        dialogPage.getMessages());

    dialogPage.clickOk(); //no OK button present

    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubject(Emails.ACTIVATION.getTitle());
    String prefix = "http://test.simplyanalytics.net/login.html";
    String link = prefix + emailUtils.getValueFromMail(prefix, message);
    System.out.println(link);
    
    dialogPage.openNewTab(link);
    dialogPage = new DialogPage(driver);

    verificationStep("Verify that the correct dialog appears, " + "Title: "
        + DialogTitleAndContent.USER_ACTIVATED.getTitle() + " Message: "
        + DialogTitleAndContent.USER_ACTIVATED.getMessages());
    Assert.assertEquals("Wrong dialog title", DialogTitleAndContent.USER_ACTIVATED.getTitle(),
        dialogPage.getTitle());
    Assert.assertEquals("Wrong dialog message", DialogTitleAndContent.USER_ACTIVATED.getMessages(),
        dialogPage.getMessages());
    dialogPage.closeTab();

    // testing login again
    signInPage = new SignInPage(driver);

    verificationStep("Verify that the login form is filled");
    Assert.assertEquals("The email field should be filled", newEmail, signInPage.getEmail());
    //Assert.assertEquals("The password field should be filled", password, signInPage.getPassword());
    signInPage.enterPass(password);
    signInPage.clickSignIn();

    verificationStep("Verify that the Tutorial dialog appears");
    Assert.assertTrue("Welcome tutorial window does not appeared", BasePage.isPresent(WelcomeScreenTutorialWindow.class, driver));
  }

  //TODO: resend activation email test

  @Test
  @Category(CreateAccountCategory.class)
  public void testCreateAccountWithoutEula() throws Exception {
    String randomNumber = RandomStringUtils.randomNumeric(8);
    String newEmail = GMailUser.USER + "+" + randomNumber + GMailUser.HOST;
    String password = RandomStringUtils.randomAlphanumeric(16);

    CreateAccountPage createAccountPage = signInPage.clickCreateAccount();

    createAccountPage.enterEmail(newEmail);
    createAccountPage.enterPassword(password);
    createAccountPage.enterPasswordAgain(password);
    DialogPage dialogPage = createAccountPage.clickCreateAccount();
    
    verificationStep("Verify that the message appears: \"You must accept the terms of the EULA to create a new account.\"");
    Assert.assertEquals("You must accept the terms of the EULA to create a new account.",
    		dialogPage.getErrorMessage());
  }

  @Test
  public void testResetPassword() throws Exception {
    ResetPasswordPage resetPasswordPage = signInPage.clickResetPassword();
    resetPasswordPage.enterEmail(SignUser.RESET_PASSWORD_USER);
    DialogPage dialogPage = resetPasswordPage.clickResetPasswordButton();

    verificationStep("Verify that dialog appeared with correct title and messages: "
        + DialogTitleAndContent.RESET_PASSWORD);
    Assert.assertEquals("Wrong dialog title", DialogTitleAndContent.RESET_PASSWORD.getTitle(),
        dialogPage.getTitle());
    Assert.assertEquals("Wrong dialog message", DialogTitleAndContent.RESET_PASSWORD.getMessages(),
        dialogPage.getMessages());

    dialogPage.clickOk();

    EmailUtils emailUtils = new EmailUtils(GMailUser.RESET_EMAIL, GMailUser.RESET_PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubject(Emails.RESET_PASSWORD.getTitle());
    String prefix = "Password:";
    String newPassword = emailUtils.getValueFromMail(prefix, message);

    signInPage = new SignInPage(driver);
    signInPage.signIn(SignUser.RESET_PASSWORD_USER, newPassword);

    verificationStep("Verify that the site opens correctly");
  }

  @Test
  public void testLogoutAsUser() {

    UserDropdown userDropdown = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickUser();
    userDropdown.clickSignOut();
    sleep(5000);
    verificationStep("Verify that the sites home page appears");
    Assert.assertEquals("The URL is not the homepage url", "https://simplyanalytics.com/", driver.getCurrentUrl());
  }
/*
//Original Test didn't work
*/
//  @Test
//  public void testLoginFromAnotherTab() throws InterruptedException {
//	  //Guest user
//	  signInPage.clickSignInAsGuest();
//      //non-guest user sign in
////      signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2);
//	  signInPage.openNewTab("https://test.simplyanalytics.net/login.html?institution");
////
//	  WebElement continueButton = driver.findElement(By.cssSelector("input[value='Continue']"));
//
//	  new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(continueButton)).click();
//
//	  institutionPage = new AuthenticateInstitutionPage(driver);
//
//	  signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
//      welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
//      welcomeScreenTutorialWindow.clickClose();
////      signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2);
//	  signInPage.closeTab();
//      welcomeScreenTutorialWindow.clickClose();
////
//	  verificationStep("Verify that the session is expired and that the “Session Expired” pop-up is present");
//
//	  Assert.assertTrue("Session expired window is not present",
//				  		driver.findElement(By.cssSelector(".sa-session-replaced-popup")).isDisplayed());
//	  Assert.assertTrue("Popup title is not correct",
//		           		driver.findElement(By.cssSelector(".sa-session-replaced-popup-title")).getText().trim()
//		           		.equals("Session Expired"));
//	  Assert.assertTrue("Popup message is not correct",
//			  			driver.findElement(By.cssSelector(".sa-session-replaced-popup-body")).getText().trim().replaceAll("[\\n|\\r]+", " ")
//			  			.equals("You appear to have logged in from a different tab. Please close this tab."));
//
//  }

  @Test
  public void testLoginFromAnotherTab() throws InterruptedException {

    // Step 1: Click on "Sign in as Guest" in the first tab
    signInPage.clickSignInAsGuest();
    institutionPage.clickCloseTutorialButton();
    institutionPage.typeAndSelectLocation();
    institutionPage.clickOnNextButton();
    institutionPage.clickCreateProjectButton();

    // Wait for the Legends to be visible=
    Thread.sleep(5000);
    WebDriverWait wait = new WebDriverWait(driver, 10);  // 10 seconds timeout, adjust as needed
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sa-legend-ranges")));

     // Step 2: Open a new tab
    signInPage.openNewTab("https://test.simplyanalytics.net/login.html?institution");

    // Step 3: Perform actions in the new tab (enter credentials and login)
    WebElement continueButton = driver.findElement(By.cssSelector("input[value='Continue']"));
    new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);

    // Step 4: Perform actions in the new tab
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    welcomeScreenTutorialWindow.clickClose();

    institutionPage.typeAndSelectLocation();
    institutionPage.clickOnNextButton();
    institutionPage.clickCreateProjectButton();

    welcomeScreenTutorialWindow.clickOnTheGuestButton();
    welcomeScreenTutorialWindow.clickOnTheSignOutButton();

    // Get all window handles
    Set<String> handles = driver.getWindowHandles();

    // Switch back to the original tab (first tab)
    for (String handle : handles) {
      if (!handle.equals(driver.getWindowHandle())) {
        driver.switchTo().window(handle);
        break;
      }
    }

    // Perform verification steps in the first tab
    verificationStep("Verify that the session is expired and that the 'Session Expired' pop-up is present");

    WebElement errorTitleMessageElement = driver.findElement(By.cssSelector(".sa-session-replaced-popup-title"));
    String errorTitleText = errorTitleMessageElement.getText().trim();
    System.out.println("Title of the error message window is:\n" + errorTitleText);
    String expectedTitleMessageText = "Session Expired";
    System.out.println("Expected Title of the error message:\n" + expectedTitleMessageText);

    WebElement errorMessageElement = driver.findElement(By.cssSelector(".sa-session-replaced-popup-body"));
    String errorText = errorMessageElement.getText().trim().replaceAll("[\\n|\\r]+", " ");
    System.out.println("Text inside of the error message dialog is:\n" + errorText);
    String expectedMessageText = "You appear to have logged in to SimplyAnalytics from another tab. Please close this tab and continue to use SimplyAnalytics from the new tab.";
    System.out.println("Expected text inside of the dialog:\n" + expectedMessageText);

    Assert.assertTrue("Session expired window is not present",
            driver.findElement(By.cssSelector(".sa-session-replaced-popup")).isDisplayed());

      Assert.assertEquals("Popup title is not correct", expectedTitleMessageText, errorTitleText);

      Assert.assertEquals("Popup message is not correct", expectedMessageText, errorText);
  }

  @After
  public void after() {
    unlockUser();
  }
}