package net.simplyanalytics.tests.view.export.custommap;

import net.simplyanalytics.constants.ExportMapDialogTitle;
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

public class MapExportBackAndForthNavigationTests extends TestBase {

  private CroppingWindow croppingWindow;

  /**
   * Signing in and creating new project.
   * Open the export cropping window.
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
    croppingWindow = mapPage.getToolbar().clickExport();
  }

  @Test
  public void testNavigationBackAndForth() {
    verificationStep("Vertify that the dialog title is: " + ExportMapDialogTitle.CROPPING);
    Assert.assertEquals("The dialog title is not the expected", ExportMapDialogTitle.CROPPING,
        croppingWindow.getTitle());

    LayoutWindow layoutWindow = croppingWindow.clickContinueToLayout();
    verificationStep("Vertify that the dialog title is: " + ExportMapDialogTitle.LAYOUT);
    Assert.assertEquals("The dialog title is not the expected", ExportMapDialogTitle.LAYOUT,
        layoutWindow.getTitle());

    croppingWindow = layoutWindow.clickReturnToCropping();
    verificationStep("Vertify that the dialog title is: " + ExportMapDialogTitle.CROPPING);
    Assert.assertEquals("The dialog title is not the expected", ExportMapDialogTitle.CROPPING,
        croppingWindow.getTitle());

    layoutWindow = croppingWindow.clickContinueToLayout();
    verificationStep("Vertify that the dialog title is: " + ExportMapDialogTitle.LAYOUT);
    Assert.assertEquals("The dialog title is not the expected", ExportMapDialogTitle.LAYOUT,
        layoutWindow.getTitle());

    ExportWindow exportWindow = layoutWindow.clickContinueToExport();
    verificationStep("Vertify that the dialog title is: " + ExportMapDialogTitle.EXPORT);
    Assert.assertEquals("The dialog title is not the expected", ExportMapDialogTitle.EXPORT,
        exportWindow.getTitle());

    layoutWindow = exportWindow.clickReturnToLayout();
    verificationStep("Vertify that the dialog title is: " + ExportMapDialogTitle.LAYOUT);
    Assert.assertEquals("The dialog title is not the expected", ExportMapDialogTitle.LAYOUT,
        layoutWindow.getTitle());
  }
}
