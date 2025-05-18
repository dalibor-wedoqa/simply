package net.simplyanalytics.tests.color;

import java.awt.geom.Point2D;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.DriverConfiguration;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.TestBrowser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.HslTab;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HslTests extends TestBase {

  private LegendPanel legendPanel;
  private HslTab hslTab;
  private int index = 2;
  private String background;

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
    background = legendPanel.getRangeRow(index).getSelectedColorCode();
    ColorSelectionPanel colorSelectionPanel = legendPanel.getRangeRow(index).clickColorIcon();
    hslTab = colorSelectionPanel.clickHslButton();
  }

  @Test
  public void enteringValuesTest() {
    int hue = 128;
    int sat = 70;
    int lum = 53;
    
    Double hueActual = hslTab.getHueBar().getActualValue();
    Double satActual = hslTab.getSatBar().getActualValue();
    Double lumActual = hslTab.getLumBar().getActualValue();
    
    hslTab.enterHueValue(hue);
    hslTab.enterSatValue(sat);
    hslTab.enterLumValue(lum);

    hslTab.clickToUseNewColor();

    verificationStep("Verify that the color window slide bars are changed properly");
    Assert.assertNotEquals("The hue bar should be changed", hueActual,
        hslTab.getHueBar().getActualValue());
    Assert.assertNotEquals("The sat bar should be changed", satActual,
        hslTab.getSatBar().getActualValue());
    Assert.assertNotEquals("The lum bar should be changed", lumActual,
        hslTab.getLumBar().getActualValue());
    
    verificationStep("Verify that the color is changed properly");
  }

  @Test
  public void moveHslSlidersTest() {
    int hue = 128;
    int sat = 70;
    int lum = 53;
    
    int hueFF = 129;
    int satFF = 70;
    int lumFF = 53;

    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
    	 hslTab.moveHueSliderToValue(hueFF);
    	 hslTab.moveSatSliderToValue(satFF);
    	 hslTab.moveLumSliderToValue(lumFF);
    }else {
    	hslTab.moveHueSliderToValue(hue);
        hslTab.moveSatSliderToValue(sat);
        hslTab.moveLumSliderToValue(lum);
    }

    hslTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertNotEquals("The color should be changed properly", background,
        legendPanel.getRangeRow(index).getSelectedColorCode());
  }

  @Test
  public void colorSpectrumWindowTest() {
    
    Point2D.Double colorSpectrumPosition = hslTab.getColorSpectrumPosition();
    hslTab.getHueBar().clickOnValue(0.5);

    verificationStep("Verify that the color spectrum position is changed properly");
    Assert.assertNotEquals("The color spectrum x position should be changed properly",
        colorSpectrumPosition.x, hslTab.getColorSpectrumPosition().x);
    Assert.assertEquals("The color spectrum y position should not be changed",
        colorSpectrumPosition.y, hslTab.getColorSpectrumPosition().y, 0.1);

    colorSpectrumPosition = hslTab.getColorSpectrumPosition();

    hslTab.getLumBar().clickOnValue(0.3);
    
    verificationStep("Verify that the color spectrum position is changed properly");
    Assert.assertNotEquals("The color spectrum y position should be changed properly",
        colorSpectrumPosition.y, hslTab.getColorSpectrumPosition().y);
    Assert.assertEquals("The color spectrum x position should not be changed",
        colorSpectrumPosition.x, hslTab.getColorSpectrumPosition().x, 0.1);

    hslTab.clickOnColorSpectrum(0.3, 0.7);

    hslTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertNotEquals("The color should be changed properly", background,
        legendPanel.getRangeRow(index).getSelectedColorCode());
  }
}
