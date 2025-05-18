/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.simplyanalytics.tests.userbased;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;

/**
 * .
 * 
 * @author wedoqa
 */
public class SessionExpiredTests extends BaseUserBasedTest {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private MapPage mapPage;

  @Test
  @Ignore("Remote run now not supports tests with half an hour wait (browser timeout)")
  public void testSession() throws InterruptedException {

    driver.manage().deleteAllCookies();
    institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    ProjectSettingsPage projectSettingsPage = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickProjectSettings();

    EditMapPage editMapPage = (EditMapPage) projectSettingsPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.MAP);
    editMapPage.getLdbSection().clickLocations()
        .voidChooseLocation(Location.LOS_ANGELES_CA_CITY);
    ((DataByCategoryPanel) editMapPage.getLdbSection().clickData().getBrowsePanel())
        .chooseRandomDataVariable(Page.EDIT_MAP);
    mapPage = (MapPage) editMapPage.clickDone();

    logger.debug("Waiting more than 20 minutes for session to expire");
    Thread.sleep(1200000);

    if (!mapPage.isSessionExpiredWindowPresent()) {
      mapPage.getToolbar().clickExportButtonVoid();
    }

    verificationStep("Verify that the session is expired and that the “Session Expired” pop-up is present");

    Assert.assertTrue("Popup title is not correct", 
        driver.findElement(By.cssSelector(".sa-session-expired-popup-title")).getText().trim()
        .equals("Session Expired"));
    Assert.assertTrue("Popup message is not correct", 
        driver.findElement(By.cssSelector(".sa-session-expired-popup-body")).getText().trim().replaceAll("[\\n|\\r]+", " ")
        .equals("Your session has expired due to inactivity. You will need to sign in again to continue using SimplyAnalytics."));
   
    
    driver.findElement(By.xpath(".//span[normalize-space(.)='Go to login page']")).click();
    //TODO add verification
    
  }
  
  @After
  public void after() {
    unlockUser();
  }

}
