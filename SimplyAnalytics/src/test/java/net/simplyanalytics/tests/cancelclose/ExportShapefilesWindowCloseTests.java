package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.toolbar.map.ExportShapefilesWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapViewActionMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExportShapefilesWindowCloseTests extends TestBase {

  private ExportShapefilesWindow exportShapefilesWindow;

  /**
   * Signing in, creating new project and open the export shapefiles window.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.FLORIDA);
    MapToolbar mapToolbar = mapPage.getToolbar();
    MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
    exportShapefilesWindow = viewActionsMenu.clickExportShapefiles();
  }
  
  @Test
  public void testCloseExportShapefilesWindow() {
    
    exportShapefilesWindow.clickOnClose();
    
    verificationStep("Verify that the Export Shapefiles Window is disappeared");
    Assert.assertFalse("The Export Shapefiles Window should be disappeared",
        exportShapefilesWindow.isDisplayed());
  }
  
  @Test
  public void testCancelExportShapefilesWindow() {
    
    exportShapefilesWindow.clickOnCancelButton();
    
    verificationStep("Verify that the Export Shapefiles Window is disappeared");
    Assert.assertFalse("The Export Shapefiles Window should be disappeared",
        exportShapefilesWindow.isDisplayed());
  }

}
