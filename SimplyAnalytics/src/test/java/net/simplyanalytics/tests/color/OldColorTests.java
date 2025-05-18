package net.simplyanalytics.tests.color;

import java.awt.Color;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.HslTab;
import net.simplyanalytics.pageobjects.panels.color.RgbTab;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OldColorTests extends TestBase {

  private ColorSelectionPanel colorSelectionPanel;

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
    LegendPanel legendPanel = mapPage.getActiveView().getLegend();
    colorSelectionPanel = legendPanel.getRangeRow(2).clickColorIcon();
  }

  @Test
  public void rgbOldColorTest() {
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();

    int red = rgbTab.getRedValue();
    int green = rgbTab.getGreenValue();
    int blue = rgbTab.getBlueValue();

    rgbTab.enterRedValue(128);
    rgbTab.enterGreenValue(20);
    rgbTab.enterBlueValue(53);
    
    Color oldColor = rgbTab.getOldColor();
    rgbTab.clickOnOldColor();

    verificationStep("Verify that the color is the same as before changing");
    Assert.assertEquals("The red color should be the same as before changing", red,
        rgbTab.getRedValue());
    Assert.assertEquals("The green color should be the same as before changing", green,
        rgbTab.getGreenValue());
    Assert.assertEquals("The blue color should be the same as before changing", blue,
        rgbTab.getBlueValue());
        
    verificationStep("Verify that the hex value is the same as before changing color");
    Assert.assertEquals("The hex value should be the same as before changing", oldColor.getRGB(),
        Color.decode("#"+rgbTab.getHexValue()).getRGB());
  }

  @Test
  public void hslOldColorTest() {
    HslTab hslTab = colorSelectionPanel.clickHslButton();

    int hue = hslTab.getHueValue();
    int sat = hslTab.getSatValue();
    int lum = hslTab.getLumValue();

    hslTab.enterHueValue(128);
    hslTab.enterSatValue(70);
    hslTab.enterLumValue(53);

    
    hslTab.clickOnOldColor();

    verificationStep("Verify that the color is the same as before changing the values");
    Assert.assertEquals("The Hue value should be the same as before changing", hue,
        hslTab.getHueValue());
    Assert.assertEquals("The Sat value should be the same as before changing", sat,
        hslTab.getSatValue());
    Assert.assertEquals("The Lum value should be the same as before changing", lum,
        hslTab.getLumValue());

    hslTab.clickOnColorSpectrum(0.5, 0.3);
    hslTab.clickOnOldColor();

    verificationStep("Verify that the color is the same as before moving the spectrum");
    Assert.assertEquals("The Hue value should be the same as before changing", hue,
        hslTab.getHueValue());
    Assert.assertEquals("The Sat value should be the same as before changing", sat,
        hslTab.getSatValue());
    Assert.assertEquals("The Lum value should be the same as before changing", lum,
        hslTab.getLumValue());
  }

}
