package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.AddTextLabeLPanel;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.TextLabel;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.TextLabelEditPanel;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColorSelectionPanelCloseTests extends TestBase {

  private MapPage mapPage;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
  }

  @Test
  public void closeLegendColorSelectionPanel() {

    ColorSelectionPanel colorSelectionPanel = mapPage.getActiveView().getLegend()
        .getRangeRow(0).clickColorIcon();

    colorSelectionPanel.clickCloseButton();

    verificationStep("Verify that the color selection panel is disappeared");
    Assert.assertFalse("The color selection panel should be disappeared",
        colorSelectionPanel.isDisplayed());

  }

  @Test
  public void closeMapExportTextLabelColorSelectionPanelTest() {

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();
    LayoutWindow layoutWindow = croppingWindow.clickContinueToLayout();
    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    sleep(2000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    ColorSelectionPanel colorSelectionPanel = labelEditPanel.clickTextColorButton();

    colorSelectionPanel.clickCloseButton();

    verificationStep("Verify that the color selection panel is disappeared");
    Assert.assertFalse("The color selection panel should be disappeared",
        colorSelectionPanel.isDisplayed());
  }

}
