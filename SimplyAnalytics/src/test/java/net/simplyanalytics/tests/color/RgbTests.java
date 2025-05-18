package net.simplyanalytics.tests.color;

import java.awt.Color;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.RgbTab;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.ColorUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RgbTests extends TestBase {

  private LegendPanel legendPanel;

  /**
   * Sign-in and creating a new project.
   */
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    legendPanel = mapPage.getActiveView().getLegend();
  }

  @Test
  public void enteringRgbValuesTest() {
    int index = 2;
    int red = 128;
    int green = 20;
    int blue = 53;

    ColorSelectionPanel colorSelectionPanel = legendPanel.getRangeRow(index).clickColorIcon();
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();

    rgbTab.enterRedValue(red);
    rgbTab.enterGreenValue(green);
    rgbTab.enterBlueValue(blue);

    rgbTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly",
        ColorUtils.getHexCode(new Color(red, green, blue)),
        legendPanel.getRangeRow(index).getSelectedColorCode());
  }

  @Test
  public void enteringHexValueTest() {
    int index = 2;
    String hexValue = "72CD27";

    ColorSelectionPanel colorSelectionPanel = legendPanel.getRangeRow(index).clickColorIcon();
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();

    rgbTab.enterHexValue(hexValue);

    rgbTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly", hexValue,
        legendPanel.getRangeRow(index).getSelectedColorCode());
  }

  @Test
  public void moveRgbSlidersTest() {
    int index = 2;
    int red = 128;
    int green = 200;
    int blue = 52;

    ColorSelectionPanel colorSelectionPanel = legendPanel.getRangeRow(index).clickColorIcon();
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();

    rgbTab.moveRedSliderToValue(red);
    rgbTab.moveGreenSliderToValue(green);
    rgbTab.moveBlueSliderToValue(blue);

    verificationStep("Verify that the red color is changed properly");
    Assert.assertEquals("The red color should be changed properly", red, rgbTab.getRedValue());

    verificationStep("Verify that the green color is changed properly");
    Assert.assertEquals("The green color should be changed properly", green,
        rgbTab.getGreenValue());

    verificationStep("Verify that the blue color is changed properly");
    Assert.assertEquals("The blue color should be changed properly", blue, rgbTab.getBlueValue());

    rgbTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly",
        ColorUtils.getHexCode(new Color(red, green, blue)),
        legendPanel.getRangeRow(index).getSelectedColorCode());
  }

}
