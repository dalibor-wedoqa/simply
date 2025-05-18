package net.simplyanalytics.tests.activeview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.sections.view.ScatterPlotViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScatterPlotActiveViewTests extends TestBase {
  
  private ViewType viewType = ViewType.SCATTER_PLOT;
  private ScatterPlotPage scatterPlotPage;
  
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.MIAMI_FL_CITY);
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) newViewPage.getActiveView().clickCreate(viewType);
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
  }
  
  @Test
  public void testRenameChartTitleInActiveView() {
    String newChartTitle = "Entered Scatter Plot";
    ScatterPlotViewPanel scatterPlotViewPanel = scatterPlotPage.getActiveView();
    
    verificationStep("Verify that chart title is the expected");
    Assert.assertEquals("Chart title is not the expected", "Scatter Plot", scatterPlotViewPanel.getChartTitle());
    
    scatterPlotViewPanel.enterChartTitle(newChartTitle);
    verificationStep("Verify that chart title is changed");
    Assert.assertEquals("Chart title should be changed", newChartTitle, scatterPlotViewPanel.getChartTitle());
    
    verificationStep("Verify that chart title is changed in legend view");
    Assert.assertEquals("Chart title should be changed in legend view", newChartTitle, scatterPlotPage.getActiveView().getLegendPanel().getChartTitle());
  }
  
  @Test
  public void testOpenPointsPopups() {
    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    int pointsCount = viewPanel.getPointsCount();
    verificationStep("Verify that all point pop up opend correctly");
    for(int index = 0; index < pointsCount; index++) {
      viewPanel.clickToOpenPointPopup(index);
      Assert.assertTrue("The popup did not open", viewPanel.isPointPopupOpen(index));
      viewPanel.clickToClosePointPopup(index);
    }
  }
  
  @Test
  public void testAxisesDecimals () {
    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();

    List<String> valuesOfXAxis = viewPanel.getValuesOfXAxis();
    List<String> valuesOfYAxis = viewPanel.getValuesOfYAxis();
    
    verificationStep("Verify that x-axis tick label percent values have exactly 2 decimals");
    for(String value : valuesOfXAxis) {
      if(value.contains("%")) {
        Assert.assertEquals("The percent value does not have 2 decimals: " + value, 2, value.indexOf("%") - value.indexOf(".") - 1);
      }
    }
    
    verificationStep("Verify that y-axis tick label percent values have exactly 2 decimals");
    for(String value : valuesOfYAxis) {
      if(value.contains("%")) {
        Assert.assertEquals("The percent value does not have 2 decimals: " + value, 2, value.indexOf("%") - value.indexOf(".") - 1);
      }
    }
    
    Set<Integer> setK = new HashSet<Integer>(); 
    Set<Integer> setM = new HashSet<Integer>();
    Set<Integer> setB = new HashSet<Integer>();
    Set<Integer> setT = new HashSet<Integer>();
    Set<Integer> set = new HashSet<Integer>();

    
    verificationStep("Verify that x-axis tick label values with same power suffix have the same number of decimals");
    for(String value: valuesOfXAxis) {
      if(value.contains(".")) {
        if(value.endsWith("K"))
          setK.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("M"))
          setM.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("B"))
          setB.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("T"))
          setT.add(value.length() - value.indexOf(".") - 1);
        else 
          set.add(value.length() - value.indexOf(".") - 1);
      }
    }
    
    if (!setT.isEmpty())
      Assert.assertEquals("The values with suffix T do not have the same number of decimals", 1, setT.size());
    if (!setK.isEmpty())
      Assert.assertEquals("The values with suffix K do not have the same number of decimals", 1, setK.size());
    if (!setM.isEmpty())
      Assert.assertEquals("The values with suffix M do not have the same number of decimals", 1, setM.size());
    if (!setB.isEmpty())
      Assert.assertEquals("The values with suffix B do not have the same number of decimals", 1, setB.size());
    if (!set.isEmpty())
      Assert.assertEquals("The values without suffix do not have the same number of decimals", 1, set.size());
    
    
    setK = new HashSet<Integer>(); 
    setM = new HashSet<Integer>();
    setB = new HashSet<Integer>();
    setT = new HashSet<Integer>();
    set = new HashSet<Integer>();

    
    verificationStep("Verify that z-axis tick label values with same power suffix have the same number of decimals");
    for(String value: valuesOfYAxis) {
      if(value.contains(".")) {
        if(value.endsWith("K"))
          setK.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("M"))
          setM.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("B"))
          setB.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("T"))
          setT.add(value.length() - value.indexOf(".") - 1);
        else 
          set.add(value.length() - value.indexOf(".") - 1);
      }
    }
    
    if (!setT.isEmpty())
      Assert.assertEquals("The values with suffix T do not have the same number of decimals", 1, setT.size());
    if (!setK.isEmpty())
      Assert.assertEquals("The values with suffix K do not have the same number of decimals", 1, setK.size());
    if (!setM.isEmpty())
      Assert.assertEquals("The values with suffix M do not have the same number of decimals", 1, setM.size());
    if (!setB.isEmpty())
      Assert.assertEquals("The values with suffix B do not have the same number of decimals", 1, setB.size());
    if (!set.isEmpty())
      Assert.assertEquals("The values without suffix do not have the same number of decimals", 1, set.size());
  }
  
  @Test
  public void   testPopupDecimals() {
    
    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    int pointsCount = viewPanel.getPointsCount();
    List<String> xValueList = new ArrayList<String>();
    List<String> yValueList = new ArrayList<String>();

    for(int index = 0; index < pointsCount; index++) {
      viewPanel.clickToOpenPointPopup(index);
      verificationStep("Verify that x-axis tick label percent values have exactly 2 decimals");
      String xValue = viewPanel.getPopupXValue();
      String yValue = viewPanel.getPopupYValue();

      if(xValue.contains("%")) {
        Assert.assertEquals("The percent value does not have 2 decimals: " + xValue, 2, xValue.indexOf("%") - xValue.indexOf(".") - 1);
      }
      
      if(yValue.contains("%")) {
        Assert.assertEquals("The percent value does not have 2 decimals: " + yValue, 2, yValue.indexOf("%") - yValue.indexOf(".") - 1);
      }
      
      viewPanel.clickToClosePointPopup(index);
    }
    
    Set<Integer> setK = new HashSet<Integer>(); 
    Set<Integer> setM = new HashSet<Integer>();
    Set<Integer> setB = new HashSet<Integer>();
    Set<Integer> setT = new HashSet<Integer>();
    Set<Integer> set = new HashSet<Integer>();

    
    verificationStep("Verify that x-axis tick label values with same power suffix have the same number of decimals");
    for(String value: xValueList) {
      if(value.contains(".")) {
        if(value.endsWith("K"))
          setK.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("M"))
          setM.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("B"))
          setB.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("T"))
          setT.add(value.length() - value.indexOf(".") - 1);
        else
          setT.add(value.length() - value.indexOf(".") - 1);
      }
    }
    
    if (!setT.isEmpty())
      Assert.assertEquals("The values with suffix T do not have the same number of decimals", 1, setT.size());
    if (!setK.isEmpty())
      Assert.assertEquals("The values with suffix K do not have the same number of decimals", 1, setK.size());
    if (!setM.isEmpty())
      Assert.assertEquals("The values with suffix M do not have the same number of decimals", 1, setM.size());
    if (!setB.isEmpty())
      Assert.assertEquals("The values with suffix B do not have the same number of decimals", 1, setB.size());
    if (!set.isEmpty())
      Assert.assertEquals("The values without suffix do not have the same number of decimals", 1, set.size());
    
    
    setK = new HashSet<Integer>(); 
    setM = new HashSet<Integer>();
    setB = new HashSet<Integer>();
    setT = new HashSet<Integer>();
    set = new HashSet<Integer>();

    
    verificationStep("Verify that y-axis tick label values with same power suffix have the same number of decimals");
    for(String value: yValueList) {
      if(value.contains(".")) {
        if(value.endsWith("K"))
          setK.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("M"))
          setM.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("B"))
          setB.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("T"))
          setT.add(value.length() - value.indexOf(".") - 1);
        else
          set.add(value.length() - value.indexOf(".") - 1);
      }
    }
    
    if (!setT.isEmpty())
      Assert.assertEquals("The values with suffix T do not have the same number of decimals", 1, setT.size());
    if (!setK.isEmpty())
      Assert.assertEquals("The values with suffix K do not have the same number of decimals", 1, setK.size());
    if (!setM.isEmpty())
      Assert.assertEquals("The values with suffix M do not have the same number of decimals", 1, setM.size());
    if (!setB.isEmpty())
      Assert.assertEquals("The values with suffix B do not have the same number of decimals", 1, setB.size());
    if (!set.isEmpty())
      Assert.assertEquals("The values without suffix do not have the same number of decimals", 1, set.size()); 
  }
  
}
