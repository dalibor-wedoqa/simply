package net.simplyanalytics.tests.userbased;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.header.Header;
import net.simplyanalytics.pageobjects.windows.DeleteProjectWindow;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;

@RunWith(JUnit4.class)
public abstract class BaseUserBasedTest extends TestBase {
  
  /**
   * Logout and delete Project.
   */
//  @After
//  public void after() {
//    // logout
//    try {
//      Header header = new Header(driver);
//      header.clickUser().clickSignOut();
//      driver.findElement(By.cssSelector(".vc_btn3-container a[title]"));
//    } catch (Throwable e) {
//      TestBase.logger.trace("BaseUserBasedTest afterLogout failed");
//    }
//    
//     delete project
//    ProjectSettingsPage projectSettingsPage;
//    
//    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
//    institutionPage.voidInstitutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
//    
//    if (BasePage.isPresent(SignInPage.class, driver)) {
//      new SignInPage(driver).signIn(SignUser.USER, SignUser.PASSWORD).getHeaderSection().clickProjectSettings();
//    }
//    
//    if (BasePage.isPresent(ProjectSettingsPage.class, driver)) {
//      projectSettingsPage = new ProjectSettingsPage(driver);
//      DeleteProjectWindow deleteProjectWindow = projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton()
//          .clickDeleteProject();
//        deleteProjectWindow.clickDelete();
//    }
//      
//    try {
//      Header header = new Header(driver);
//      header.clickUser().clickSignOut();
//      driver.findElement(By.cssSelector(".vc_btn3-container a[title]"));
//    } catch (Throwable e) {
//      TestBase.logger.trace("BaseUserBasedTest afterLogout failed");
//    }
//  }
  
}
