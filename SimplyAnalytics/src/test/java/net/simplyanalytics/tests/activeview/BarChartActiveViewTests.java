package net.simplyanalytics.tests.activeview;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class BarChartActiveViewTests extends TestBase {
  
  private BarChartPage barChartPage;
  private BarChartViewPanel barChartViewPanel;

  
  /**
   * Signing in, creating new project and open the Bar Chart page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) mapPage.getLdbSection().clickData().getBrowsePanel();
    DataByCategoryDropwDown dropdown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);
    dropdown.clickOnARandomDataResult();
    dropdown = dataByCategoryPanel.clickOnACategoryData(CategoryData.EDUCATION);
    dropdown.clickOnARandomDataResult();
    dropdown = dataByCategoryPanel.clickOnACategoryData(CategoryData.ANCESTRY);
    dropdown.clickOnARandomDataResult();
    
    barChartPage = (BarChartPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.BAR_CHART).clickDone();
    barChartViewPanel = barChartPage.getActiveView();
  }
  
  @Test
  public void testDecimals() {
    
    barChartViewPanel = (BarChartViewPanel) barChartPage.getToolbar().openDataFilterDropdown().clickRandomFilter(ViewType.BAR_CHART);
    
    List<String> valuesOfBars = barChartViewPanel.getValueOfBars();
    List<String> valuesOfAxis = barChartViewPanel.getValuesOfYAxis();
    
    verificationStep("Verify that the tick label zero value has the correct form");
    if (valuesOfAxis.get(0).endsWith("%")) {
      Assert.assertEquals("Zero value does not have the correct form", "0.00%", valuesOfAxis.get(0));
    }
    else if (valuesOfAxis.get(0).startsWith("$")) {
      Assert.assertEquals("Zero value does not have the correct form", "$0", valuesOfAxis.get(0));
    }
    else {
      Assert.assertEquals("Zero value does not have the correct form", "0", valuesOfAxis.get(0));
    }
    
    valuesOfAxis.remove(0);
    
    verificationStep("Verify that tick label percent values have exactly 2 decimals");
    for(String value : valuesOfAxis) {
      if(value.contains("%")) {
        Assert.assertEquals("The percent value does not have 2 decimals: " + value, 2, value.indexOf("%") - value.indexOf(".") - 1);
      }
    }
    
    verificationStep("Verify that bar label percent values have exactly 2 decimals");
    for(String value : valuesOfBars) {
      if(value.contains("%")) {
        Assert.assertEquals("The percent value does not have 2 decimals: " + value, 2, value.indexOf("%")  - value.indexOf(".") - 1);
      }
    }
    
    Set<Integer> setK = new HashSet<Integer>(); 
    Set<Integer> setM = new HashSet<Integer>();
    Set<Integer> setB = new HashSet<Integer>();
    Set<Integer> setT = new HashSet<Integer>();
    
    verificationStep("Verify that tick label values with same power suffix have the same number of decimals");
    for(String value: valuesOfAxis) {
      if (value.contains(".")) {
        if(value.endsWith("K"))
          setK.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("M"))
          setM.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("B"))
          setB.add(value.length() - value.indexOf(".") - 1);
        else if(value.endsWith("T"))
          setT.add(value.length() - value.indexOf(".") - 1);
      }
    }
    
    if (!setT.isEmpty())
      Assert.assertEquals("The values with suffix T does not have the same number of decimals", 1, setT.size());
    if (!setK.isEmpty())
      Assert.assertEquals("The values with suffix K does not have the same number of decimals", 1, setK.size());
    if (!setM.isEmpty())
      Assert.assertEquals("The values with suffix M does not have the same number of decimals", 1, setM.size());
    if (!setB.isEmpty())
      Assert.assertEquals("The values with suffix B does not have the same number of decimals", 1, setB.size());
      
    verificationStep("Verify that bar label values have three significant digits");
    Set<Object> isThreeSignificantDigit = valuesOfBars.stream()
        .map(value -> {
          if (value.contains("%")) {
            return true;
          }
          else {
            String regex = "([0-9]+\\.?[0-9]*)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            if (m.find()) {
              if (m.group(0).startsWith("0.") && m.group(0).length() == 5) {
                logger.info(value);
                return true;
              }
              else if((m.group(0).length() == 4  && value.contains(".")) || (m.group(0).length() == 3  && !value.contains("."))) {
                logger.info("else if: " + value);
                return true;
              }
            }
            logger.info(value);
            return false;
          }
          
        }).collect(Collectors.toSet());
    Assert.assertTrue("The bar value does not have three significant digits", 
        isThreeSignificantDigit.iterator().next().equals(true) && 1 == isThreeSignificantDigit.size());
  }
}
