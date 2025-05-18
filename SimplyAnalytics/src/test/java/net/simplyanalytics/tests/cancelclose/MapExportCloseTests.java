package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapExportCloseTests extends TestBase {
    
  private ExportWindow exportWindow;

  /**
   * Signing in, creating new project and open the export window.
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
    LayoutWindow layoutWindow = croppingWindow.clickContinueToLayout();
    exportWindow = layoutWindow.clickContinueToExport();
  }
  
  @Test
  public void closeMapExportWindow() {
    
    exportWindow.clickClose();
    
    verificationStep("Verify that the export window is disappeared");
    Assert.assertFalse("The export window should be disappeared",
        exportWindow.isDisplayed());
  }
  
  @Test
  public void cancelMapExportWindow() {
    
    exportWindow.clickCancel();
    
    verificationStep("Verify that the export window is disappeared");
    Assert.assertFalse("The export window should be disappeared",
        exportWindow.isDisplayed());
  }
}
