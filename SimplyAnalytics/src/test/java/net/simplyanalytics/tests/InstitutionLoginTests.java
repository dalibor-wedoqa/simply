package net.simplyanalytics.tests;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DialogTitleAndContent;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class InstitutionLoginTests extends TestBase {

  private AuthenticateInstitutionPage institutionPage;

  @Before
  public void before() {
    institutionPage = new AuthenticateInstitutionPage(driver);
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  public void testValidLogin() {
    institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    logger.info("Verify that the User sign in page appears");
  }

  @Test
  public void testLoginWithWrongInstitution() {
    institutionPage = institutionPage.failedInstitutionLogin(InstitutionUser.WRONG_INSTITUTION,
        InstitutionUser.PASSWORD);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.INSTITUTION_FAILED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.INSTITUTION_FAILED.getSingleMessage(), institutionPage.getMessage());
  }

  @Test
  public void testLoginWithWrongPassword() {
    institutionPage = institutionPage.failedInstitutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.WRONG_PASSWORD);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.INSTITUTION_FAILED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.INSTITUTION_FAILED.getSingleMessage(), institutionPage.getMessage());
  }

  @Test
  public void testLoginWithWrongInstitutionAndPassword() {
    institutionPage = institutionPage.failedInstitutionLogin(InstitutionUser.WRONG_INSTITUTION,
        InstitutionUser.WRONG_PASSWORD);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.INSTITUTION_FAILED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.INSTITUTION_FAILED.getSingleMessage(), institutionPage.getMessage());
  }

  @Test
  public void testLoginWithEmptyInstitution() {
    institutionPage = institutionPage.failedInstitutionLogin("", InstitutionUser.PASSWORD);

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.INSTITUTION_USERNAME_REQUIRED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.INSTITUTION_USERNAME_REQUIRED.getSingleMessage(),
        institutionPage.getMessage());
  }

  @Test
  public void testLoginWithEmptyPassword() {
    institutionPage = institutionPage.failedInstitutionLogin(InstitutionUser.INSTITUTION, "");

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.INSTITUTION_PASSWORD_REQUIRED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.INSTITUTION_PASSWORD_REQUIRED.getSingleMessage(),
        institutionPage.getMessage());
  }

  @Test
  public void testLoginWithEmptyInstitutionAndPassword() {
    institutionPage = institutionPage.failedInstitutionLogin("", "");

    verificationStep("Verify that the correct error dialog appears with message: "
        + DialogTitleAndContent.INSTITUTION_USERNAME_REQUIRED.getSingleMessage());
    Assert.assertEquals("Wrong dialog message",
        DialogTitleAndContent.INSTITUTION_USERNAME_REQUIRED.getSingleMessage(),
        institutionPage.getMessage());
  }

}
