package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.AddTextLabeLPanel;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextLablePanelCloseTests extends TestBase {

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

    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

  }

  @Test
  public void closeAddTextLablePanel() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.clickClose();

    verificationStep("Verify that the Add Text LablePanel is disappeared");
    Assert.assertFalse("The Add Text LablePanel should be disappeared",
        textLabelPanel.isDisplayed());
  }

  @Test
  public void cancelAddTextLablePanel() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.clickCancel();

    verificationStep("Verify that the Add Text LablePanel is disappeared");
    Assert.assertFalse("The Add Text LablePanel should be disappeared",
        textLabelPanel.isDisplayed());
  }

}
