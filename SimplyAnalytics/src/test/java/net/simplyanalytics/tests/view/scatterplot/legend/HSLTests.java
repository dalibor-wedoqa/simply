package net.simplyanalytics.tests.view.scatterplot.legend;

import java.awt.geom.Point2D;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.TestBrowser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.HslTab;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotEditLegendPanel;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotLegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class HSLTests extends TestBase {

  private ViewType viewType = ViewType.SCATTER_PLOT;
  private ScatterPlotPage scatterPlotPage;
  private ScatterPlotLegendPanel legendPanel;
  private ScatterPlotEditLegendPanel editLegendPanel;

  @Before
  public void createProjectWithMapView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.NEW_YORK_STATE);

    EditScatterPlotPage editScatterPlotPage 
      = (EditScatterPlotPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(viewType);      
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
    legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    editLegendPanel = legendPanel.clickEdit();
  }
  
  @Test
  public void enteringValuesTest() {
    String hexValue = "33DB4A";
    int hue = 128;
    int sat = 70;
    int lum = 53;
    
    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickPointColor();
    HslTab hslTab = colorSelectionPanel.clickHslButton();

    
    Double hueActual = hslTab.getHueBar().getActualValue();
    Double satActual = hslTab.getSatBar().getActualValue();
    Double lumActual = hslTab.getLumBar().getActualValue();
    
    hslTab.enterHueValue(hue);
    hslTab.enterSatValue(sat);
    hslTab.enterLumValue(lum);

    verificationStep("Verify that the color window slide bars are changed properly");
    Assert.assertNotEquals("The hue bar should be changed", hueActual,
        hslTab.getHueBar().getActualValue());
    Assert.assertNotEquals("The sat bar should be changed", satActual,
        hslTab.getSatBar().getActualValue());
    Assert.assertNotEquals("The lum bar should be changed", lumActual,
        hslTab.getLumBar().getActualValue());
    
    hslTab.clickToUseNewColor();
    
    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly", hexValue,
        editLegendPanel.getSelectedColorCode());
    
    legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point color in active view is the expected");
    Assert.assertEquals("Point color is not the expeced", "#" + hexValue, scatterPlotPage.getActiveView().getPointsColor());
  }

  @Test
  public void moveHslSlidersTest() {
    String hexValue = "33DB4A";
    String hexValueFirefox = "33DB4C";
    int hue = 128;
    int sat = 70;
    int lum = 53;
    int hueFF = 129;

    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickPointColor();
    HslTab hslTab = colorSelectionPanel.clickHslButton();
    
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
		hslTab.moveHueSliderToValue(hueFF);
		hslTab.moveSatSliderToValue(sat);
		hslTab.moveLumSliderToValue(lum);
    }else {
		hslTab.moveHueSliderToValue(hue);
		hslTab.moveSatSliderToValue(sat);
		hslTab.moveLumSliderToValue(lum);
    }

    hslTab.clickToUseNewColor();
    
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
    	verificationStep("Verify that the color is changed properly");
        Assert.assertEquals("The color should be changed properly", hexValueFirefox,
            editLegendPanel.getSelectedColorCode());
    }else {
        verificationStep("Verify that the color is changed properly");
        Assert.assertEquals("The color should be changed properly", hexValue,
            editLegendPanel.getSelectedColorCode());
    }
    
    legendPanel = editLegendPanel.clickDone();
    
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
    	verificationStep("Verify that point color in active view is the expected");
        Assert.assertEquals("Point color is not the expeced", "#" + hexValueFirefox, scatterPlotPage.getActiveView().getPointsColor());
    }else {
    	verificationStep("Verify that point color in active view is the expected");
        Assert.assertEquals("Point color is not the expeced", "#" + hexValue, scatterPlotPage.getActiveView().getPointsColor());
    }
  }

  @Test
  public void colorSpectrumWindowTest() {
    String hexValue = "008F83";
    String hexValueFirefox = "008F81";
    
    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickPointColor();
    HslTab hslTab = colorSelectionPanel.clickHslButton();
    
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
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
        Assert.assertEquals("The color should be changed properly", hexValueFirefox, editLegendPanel.getSelectedColorCode());
    }else {
        Assert.assertEquals("The color should be changed properly", hexValue, editLegendPanel.getSelectedColorCode());
    }
    
    legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point color in active view is the expected");
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
        Assert.assertEquals("Point color is not the expeced", "#" + hexValueFirefox, scatterPlotPage.getActiveView().getPointsColor());
    }else {
        Assert.assertEquals("Point color is not the expeced", "#" + hexValue, scatterPlotPage.getActiveView().getPointsColor());
    }
    
    
  }
}
