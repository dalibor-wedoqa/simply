package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapExportLayoutCloseTests extends TestBase {
  
  private LayoutWindow layoutWindow;
  
  /**
   * Signing in, creating new project and open the export layout window.
   */
  @Before
  public void before() {
    
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();
    layoutWindow = croppingWindow.clickContinueToLayout();
  }
  
  @Test
  public void closeMapExportLayout() {
    
    layoutWindow.clickClose();
    
    verificationStep("Verify that the map export layout window is disappeared");
    Assert.assertFalse("The map export layout window should be disappeared",
        layoutWindow.isDisplayed());
  }
  
  @Test
  public void cancelMapExportLayout() {
    
    layoutWindow.clickCancel();
    
    verificationStep("Verify that the map export layout window is disappeared");
    Assert.assertFalse("The map export layout window should be disappeared",
        layoutWindow.isDisplayed());
  }
}
