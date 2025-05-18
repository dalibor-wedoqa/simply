package net.simplyanalytics.tests.view.scatterplot.legend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Point;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.TestBrowser;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotEditLegendPanel;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotLegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScatterPlotLegendTests extends TestBase {
  
  private ViewType viewType = ViewType.SCATTER_PLOT;
  private ScatterPlotPage scatterPlotPage;
  private DataVariable income = medianDefaultDataVariable;
  private DataVariable secondDataVariable = defaultDataVariables.get(1);
  
  @Before
  public void createProjectWithMapView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    EditScatterPlotPage editScatterPlotPage 
      = (EditScatterPlotPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(viewType);
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
  }
  
  @Test
  public void testChangeChartTitle() {
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    String chartTitle = legendPanel.getChartTitle();
    
    verificationStep("Verify that chart title is 'Scatter Plot'");
    Assert.assertEquals("Chart title is not the expected", "Scatter Plot", chartTitle);
    
    ScatterPlotEditLegendPanel editLegendPanel = legendPanel.clickEdit();
    chartTitle = "Edited Scatter Plot";
    editLegendPanel.changeChartTitle(chartTitle);
    legendPanel = editLegendPanel.clickDone();

    verificationStep("Verify that chart title is changed in the legend panel");
    Assert.assertEquals("Chart title is not the expected", chartTitle , legendPanel.getChartTitle());
    
    verificationStep("Verify that chart title is changed in active view");
    Assert.assertEquals("Chart title is not the expected", chartTitle , scatterPlotPage.getActiveView().getChartTitle());
  }
  
  @Test
  public void testChangeXYAxisData() {
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    String xAxisTitle = legendPanel.getXAxisData();
    
    verificationStep("Verify that x-axis title is correct");
    Assert.assertEquals("x-axis title is not correct", xAxisTitle, scatterPlotPage.getActiveView().getXAxisTitle());

    String yAxisTitle = legendPanel.getYAxisData();
    
    System.out.println(scatterPlotPage.getActiveView().getYAxisTitle());
    
    verificationStep("Verify that y-axis title is correct");
    Assert.assertEquals("y-axis title is not correct", yAxisTitle, scatterPlotPage.getActiveView().getYAxisTitle());
    
    ScatterPlotEditLegendPanel editLegendPanel = legendPanel.clickEdit();
    editLegendPanel.changeXAxisDataByName(income);
    editLegendPanel.clickDone();
    
    xAxisTitle = legendPanel.getXAxisData();
    
    verificationStep("Verify that x-axis title is correct");
    Assert.assertEquals("x-axis title is not correct", xAxisTitle, scatterPlotPage.getActiveView().getXAxisTitle());
    
    editLegendPanel = legendPanel.clickEdit();
    editLegendPanel.changeYAxisDataByName(secondDataVariable);
    editLegendPanel.clickDone();
    
    yAxisTitle = legendPanel.getYAxisData();
    
    verificationStep("Verify that y-axis title is correct");
    Assert.assertEquals("y-axis title is not correct", yAxisTitle, scatterPlotPage.getActiveView().getYAxisTitle());
  }
  
  @Test
  public void testChangePointSize() {
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    ScatterPlotEditLegendPanel editLegendPanel = legendPanel.clickEdit();
    
    verificationStep("Verify that point size is the expected");
    Assert.assertEquals("Point size is not the expeced", "3", editLegendPanel.getActualPointSize());
    
    editLegendPanel.changePointSize(4);
    
    verificationStep("Verify that point size is the expected");
    Assert.assertEquals("Point size is not the expeced", "4", editLegendPanel.getActualPointSize());
    
    verificationStep("Verify that point size in active view is the expected");
    Assert.assertEquals("Point size is not the expeced", "4", scatterPlotPage.getActiveView().getPointSizeByIndex(0));
    
    editLegendPanel.changePointSize(1);
    
    verificationStep("Verify that point size is the expected");
    Assert.assertEquals("Point size is not the expeced", "1", editLegendPanel.getActualPointSize());
    
    legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point size in active view is the expected");
    Assert.assertEquals("Point size is not the expeced", "1", scatterPlotPage.getActiveView().getPointSizeByIndex(0));
  }
  
  @Test
  public void testLineOfBestFit() {
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    ScatterPlotEditLegendPanel editLegendPanel = legendPanel.clickEdit();
    
    verificationStep("Verify that line of best fit active");
    Assert.assertTrue("Line of best fit should be active", editLegendPanel.isLineOfBestFitActive());
    
    verificationStep("Verify that line of best fit active on the active view");
    Assert.assertTrue("Line of best fit should be visible", scatterPlotPage.getActiveView().isLineOfBestFitActive());
    
    editLegendPanel.clickLineBestFitSwitchButton();
    
    verificationStep("Verify that line of best fit inactive");
    Assert.assertFalse("Line of best fit should be inactive", editLegendPanel.isLineOfBestFitActive());
    
    verificationStep("Verify that line of best fit inactive on the active view");
    Assert.assertFalse("Line of best fit should be invisible", scatterPlotPage.getActiveView().isLineOfBestFitActive());
    
    editLegendPanel.clickLineBestFitSwitchButton();
    
    verificationStep("Verify that line of best fit active");
    Assert.assertTrue("Line of best fit should be active", editLegendPanel.isLineOfBestFitActive());
    
    verificationStep("Verify that line of best fit active on the active view");
    Assert.assertTrue("Line of best fit should be visible", scatterPlotPage.getActiveView().isLineOfBestFitActive());
    
    editLegendPanel.clickDone();
  }
  
  @Test
  public void testChangePointColorWithPalette() {
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    ScatterPlotEditLegendPanel editLegendPanel = legendPanel.clickEdit();
    
    verificationStep("Verify that point color is the expected");
    
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
        Assert.assertEquals("Point color is not the expeced", "rgb(128, 187, 255)", editLegendPanel.getActualPointColor());	
    }else {
        Assert.assertEquals("Point color is not the expeced", "rgba(128, 187, 255, 1)", editLegendPanel.getActualPointColor());
    }
    
    editLegendPanel.changePointColorByIndex(33);
    
    verificationStep("Verify that point color is the expected");
    
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
    	Assert.assertEquals("Point color is not the expeced", "rgb(255, 0, 0)", editLegendPanel.getActualPointColor());
    }else {
    	Assert.assertEquals("Point color is not the expeced", "rgba(255, 0, 0, 1)", editLegendPanel.getActualPointColor());
    }
    
    verificationStep("Verify that point color in active view is the expected");
    Assert.assertEquals("Point color is not the expeced", "#FF0000", scatterPlotPage.getActiveView().getPointsColor());
    
    editLegendPanel.changePointColorByIndex(48);
    
    verificationStep("Verify that point color is the expected");
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
        Assert.assertEquals("Point color is not the expeced", "rgb(96, 191, 0)", editLegendPanel.getActualPointColor());
    }else {
        Assert.assertEquals("Point color is not the expeced", "rgba(96, 191, 0, 1)", editLegendPanel.getActualPointColor());
    }
  
    legendPanel = editLegendPanel.clickDone();
    
    verificationStep("Verify that point color in active view is the expected");
    Assert.assertEquals("Point color is not the expeced", "#60BF00", scatterPlotPage.getActiveView().getPointsColor());
  }
  
  @Test
  public void legendMoveTest() {
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    Point beforeMoving = legendPanel.getLegendLocation();
    legendPanel.moveLegend(-300, 300);
    verificationStep("Verify that the legend panel is moved");
    Assert.assertNotEquals("The legend panel should be moved", beforeMoving,
        legendPanel.getLegendLocation());
  }
  
  @Test
  public void testDisplayLimitMessage() {
    scatterPlotPage.getToolbar().openLocationMenu().clickonLocation(Location.USA);
    ScatterPlotLegendPanel legendPanel = scatterPlotPage.getActiveView().getLegendPanel();
    
    verificationStep("Verify that display limit message appeared on legend panel");
    Assert.assertTrue("Limit message does not appeared", legendPanel.isLegendWarningMessageAppeared());
    
    String expectedMessage = "Scatter Plots can display a maximum of 10,000 locations (points). "
        + "There are more than 10,000 matching locations, so we are displaying a random selection of 10,000 locations.";
    
    verificationStep("Verify that limit message is correct");
    Assert.assertEquals("Display limit message is not correct", expectedMessage, legendPanel.getLegendWarningMessage());
  }

}
