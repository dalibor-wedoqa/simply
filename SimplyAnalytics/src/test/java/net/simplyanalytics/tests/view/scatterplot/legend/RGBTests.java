package net.simplyanalytics.tests.view.scatterplot.legend;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.RgbTab;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotEditLegendPanel;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotLegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.ColorUtils;

public class RGBTests extends TestBase {
  
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
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    EditScatterPlotPage editScatterPlotPage 
      = (EditScatterPlotPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(viewType);      
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
    legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    editLegendPanel = legendPanel.clickEdit();
  }
  
  @Test
  public void enteringRgbValuesTest() {
    int red = 128;
    int green = 20;
    int blue = 53;

    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickPointColor();
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();

    rgbTab.enterRedValue(red);
    rgbTab.enterGreenValue(green);
    rgbTab.enterBlueValue(blue);

    rgbTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly",
        ColorUtils.getHexCode(new Color(red, green, blue)),
        editLegendPanel.getSelectedColorCode());
    
   legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point color in active view is the expected");
    Assert.assertEquals("Point color is not the expeced", "#801435", scatterPlotPage.getActiveView().getPointsColor());
  }
  
  @Test
  public void enteringHexValueTest() {
    String hexValue = "72CD27";

    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickPointColor();
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();
    
    rgbTab.enterHexValue(hexValue);

    rgbTab.clickToUseNewColor();

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly", hexValue,
        editLegendPanel.getSelectedColorCode());
    
    legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point color in active view is the expected");
    Assert.assertEquals("Point color is not the expeced", "#72CD27", scatterPlotPage.getActiveView().getPointsColor());
  }

  @Test
  public void moveRgbSlidersTest() {
    int red = 255;
    int green = 187;
    int blue = 0;

    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickPointColor();
    RgbTab rgbTab = colorSelectionPanel.clickRgbButton();

    rgbTab.moveRedSliderToValue(red);
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
        editLegendPanel.getSelectedColorCode());
    
    legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point color in active view is the expected");
    Assert.assertEquals("Point color is not the expeced", "#FFBB00", scatterPlotPage.getActiveView().getPointsColor());
  }
}
