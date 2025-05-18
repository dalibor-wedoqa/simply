package net.simplyanalytics.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DialogTitleAndContent;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MinimalTest extends TestBase {

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
  public void testFailedValidLogin() {
    institutionPage.institutionLogin("invalid", "login");
    logger.info("Verify that the User sign in page appears");
  }

}
