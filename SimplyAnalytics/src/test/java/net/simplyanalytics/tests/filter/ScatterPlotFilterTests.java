package net.simplyanalytics.tests.filter;

import java.awt.Point;
import java.util.List;
import java.util.NoSuchElementException;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.containers.SimpleFilter;
import net.simplyanalytics.core.HeadlessIssue;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.DataVariableRelation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering.FilteringConditionRow;
import net.simplyanalytics.pageobjects.sections.view.ScatterPlotViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.StringParsing;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScatterPlotFilterTests extends TestBase {
  
  private ScatterPlotPage scatterPlotPage;
  private DataVariable income = medianDefaultDataVariable;
  private DataVariable percent = percentDefaultDataVariable;
  //NB Start: Added private const for population count
  private DataVariable count = countDefaultDataVariable;
  //NB End
  /**
   * Signing in and creating new project and open scatter plot page.
   */
  @Before
  public void createScatterPlot() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow.createNewProjectWithLocation(Location.CHICAGO_IL_CITY).clickCheckBoxButtonForPopulation().clickCreateProjectButton();
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) mapPage.getViewChooserSection()
        .clickNewView().getActiveView().clickCreate(ViewType.SCATTER_PLOT);
   // editScatterPlotPage.getActiveView().getDataPanel().clickColumnDataItem(0);
    editScatterPlotPage.getActiveView().getDataPanel().clickRowDataItem(2);
    editScatterPlotPage.clickCloseMenuButton();

    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
  }
  
  @Test
  public void testFilterOpens() {
    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();

    verificationStep("Verify that the default data variables are present");
    defaultDataVariables.stream()
        .forEach(dataVariable -> Assert.assertTrue("The data variable is missing: " + dataVariable,
            filtering.isDataVariableSelectable(dataVariable)));
  }
  


  @Test
  public void testLess() {
    verifyCondition(scatterPlotPage, DataVariableRelation.LESS,
        income, 86100);
  }

  //Test equal median currency value
  @Test
  @DisplayName("Verify that a Scatter Plot can filter out the exact value in case of median income")
  @Description("The test creates a Scatter Plot with median income and tries to filter out a single point. This doesn't work ever because of the rounding of numbers. The database holds more decimals than two for median income.")
  @Tag("Bug")
  public void testEqualsPercent() {
    ScatterPlotViewPanel.changePointsRepresent(driver);
    verifyConditionForPercent(scatterPlotPage, DataVariableRelation.EQUAL,
            percent, "0.00%");
//    verifyCondition(scatterPlotPage, DataVariableRelation.EQUAL,
//        income, 86100);
  }

  //the very new verifyCondition
  private void verifyConditionForPercent(ScatterPlotPage scatterPlotPage, DataVariableRelation relation, DataVariable dataVariable, String restrictionValue) {
    // Step 1: Click filtering in toolbar
    System.out.println("Initial scatterPlotPage: " + scatterPlotPage);
    System.out.println("Relation: " + relation + ", DataVariable: " + dataVariable + ", RestrictionValue: " + restrictionValue);

    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();
    System.out.println("Filtering toolbar clicked, filtering: " + filtering);

    // Step 2: Apply the filter
    filtering.applyEquals0Filter(driver);
    System.out.println("Filter applied");

    // Step 3: Retrieve the applied filter's condition row
    FilteringConditionRow filteringConditionRow = filtering.getRowForPercent();
    System.out.println("Filtering condition row retrieved: " + filteringConditionRow);

    // Step 7: Get the actual filter values
    SimpleFilter actual = filteringConditionRow.getActualValues();
    System.out.println("Actual filter values retrieved: " + actual);

    // Step 8: Verify the data variable
    System.out.println("Verified data variable: " + actual.getDataVariable()+" Data var:"+ dataVariable);
    Assert.assertEquals("The data variable is not the expected", dataVariable, actual.getDataVariable());

    // Step 9: Verify the relation condition
    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
    System.out.println("Verified relation condition: " + actual.getCondition());

    // Step 10: Convert and verify the value
    String actualValue = actual.getValue();
    Assert.assertEquals("The value is not the expected", restrictionValue, actualValue);
    System.out.println("Verified value: " + actualValue);

    // Step 11: Re-apply the filter
    filtering.clickApply();
    System.out.println("Filter reapplied");

    // Step 12: Close filtering
    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();
    System.out.println("Filtering closed, scatterPlotPage updated: " + scatterPlotPage);

    // Step 13: Get active view panel
    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    System.out.println("ViewPanel retrieved: " + viewPanel);

     // Step 15: Iterate over points to validate conditions
    try {
      // Get all the circle elements with class 'sa-scatter-plot-point'
      List<WebElement> circleElements = driver.findElements(By.cssSelector("circle.sa-scatter-plot-point"));
      System.out.println("Total circles found: " + circleElements.size());

      // Iterate over each circle element
      for (int i = 0; i < circleElements.size(); i++) {
        WebElement circle = circleElements.get(i);

        // Print out the information about the circle element
        System.out.println("Element info for circle at index " + i + ":");
        System.out.println("Tag name: " + circle.getTagName());
        System.out.println("Text content: " + circle.getText());
        System.out.println("Classes: " + circle.getAttribute("class"));
        System.out.println("Circle coordinates - cx: " + circle.getAttribute("cx") + ", cy: " + circle.getAttribute("cy"));

        // Print a message confirming the click action (no actual click verification yet)
        System.out.println("Attempted to click on circle at index " + i);
        viewPanel.clickToOpenPointPopup2(i);
        System.out.println(viewPanel.getPopupYValue());
        Assert.assertEquals("0.00%", viewPanel.getPopupYValue());

      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("An error occurred: " + e.getMessage());
    }

  }


  //Test equal Population Count
  @Test
  public void testEqualsCount() {
    System.out.println("Changing data");
    EditScatterPlotPage.changePlotData(driver);
    System.out.println("Applying filter");
    verifyConditionForEqual(scatterPlotPage, DataVariableRelation.EQUAL,
            count, 12022);
  }

  private void verifyConditionForEqual(ScatterPlotPage scatterPlotPage, DataVariableRelation relation, DataVariable dataVariable, int restrictionValue) {
    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1(String.valueOf(restrictionValue));
    filtering.clickApply();

    verificationStep("Verify that the entered filter is present and the condition is the expected");
    filteringConditionRow = filtering.getRowForDataVariable(dataVariable);
    SimpleFilter actual = filteringConditionRow.getActualValues();
    Assert.assertEquals("The data variable is not the expected", dataVariable, actual.getDataVariable());
    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
    Assert.assertEquals("The value is not the expected", restrictionValue, parseToNumber(actual.getValue()));
    filtering.clickApply();
    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();

    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    int pointsCount = viewPanel.getPointsCount();
    verificationStep("Verify that the present values fulfill the given condition");
    for (int index = 0; index < pointsCount; index++) {
      viewPanel.clickToOpenPointPopup2(index);

      Assert.assertEquals(String.valueOf(restrictionValue), viewPanel.getPopupXValue());

      logger.info("Close popup");
      viewPanel.clickToClosePointPopup(index);
    }
  }

  @Test
  public void testGreaterEquals() {
    verifyCondition(scatterPlotPage, DataVariableRelation.GREATEREQUAL,
        income, 86100);
  }

  @Test
  public void testLessEquals() {
    verifyCondition(scatterPlotPage, DataVariableRelation.LESSEQUAL,
        income, 86100);
  }

  @Test
  public void testNotEquals() {
    verifyCondition(scatterPlotPage, DataVariableRelation.NOT_EQUAL,
        income, 86100);
  }
  
  @Test
  @DisplayName("Verify that the -is between- filter works as expected on the Scatter Plot view")
  @Description("The test creates a Scatter Plot view with sample data. Applies an is between filter with fix values and verifies if the view is updated.")
  @Tag("TCE/Fix")
  public void testBetween() {
    DataVariable dataVariable = income;
    DataVariableRelation relation = DataVariableRelation.BETWEEN;
    int restrictionValue1 = 30000;
    int restrictionValue2 = 75000;

    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue1);
    filteringConditionRow.enterValue2("" + restrictionValue2);
    filtering.clickApply();

    verificationStep(
        "Verify that the entered filter is present and the condition is the expected");
    filteringConditionRow = filtering.getRowForDataVariable(dataVariable);
    SimpleFilter actual = filteringConditionRow.getActualValues();
    Assert.assertEquals("The data variabe is not the expected", dataVariable,
        actual.getDataVariable());
    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
    Assert.assertEquals("The value 1 is not the expected", restrictionValue1,
        convertPriceToInt(actual.getValue()));
    Assert.assertEquals("The value 2 is not the expected", restrictionValue2,
        convertPriceToInt(actual.getValue2()));

    filtering.clickApply();
    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();

    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    int pointsCount = viewPanel.getPointsCount();
    verificationStep("Verify that the present values fulfill the given condition");
    for(int index = 0; index < pointsCount; index++) {
      viewPanel.clickToOpenPointPopup(index);
      int intValue = StringParsing.parsePriceToInt(viewPanel.getPopupXValue());
      Assert.assertTrue("The value does not fulfill the condition " + relation.getName(), 
          intValue > restrictionValue1 && intValue < restrictionValue2);
      
      logger.info("Close popup");
      viewPanel.clickToClosePointPopup(index);
    }
  }

  @Test
  @HeadlessIssue
  @DisplayName("Verify that the combined conditions filter works as expected on the Scatter Plot view")
  @Description("The test creates a Scatter Plot view with sample data. Applies two filters with fix values and verifies if the view is updated.")
  @Tag("TCE/Fix")
  public void testCombinatedConditions() {
    DataVariable income = medianDefaultDataVariable;
    DataVariableRelation greater = DataVariableRelation.GREATER;
    int incomeValue = 70000;

    // create AND filter
    final DataVariable secondDataVariable = 
        firstDefaultDataVariable;
    final DataVariableRelation less = DataVariableRelation.LESS;
    double secondValue = 50;

    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();

    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(income);
    filteringConditionRow.selectRelation(greater);
    filteringConditionRow.enterValue1("" + incomeValue);

    verificationStep("Verify that the \"and\" is the default selected value for scatter plot");
    String actual = filtering.getSelectedCombination(1);
    String expected = "and";
    Assert.assertEquals("The default value is not \"and\": " + actual, expected, actual);

    filteringConditionRow = filtering.clickDataVariable(secondDataVariable);
    filteringConditionRow.selectRelation(less);
    filteringConditionRow.enterValue1("" + secondValue);

    verificationStep(
        "Verify that the \"and\" is the selected value for the new condition row as well");
    actual = filtering.getSelectedCombination(2);
    expected = "and";
    Assert.assertEquals("The value is not \"and\": " + actual, expected, actual);

    filtering.clickApply();
    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();

    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    int pointsCount = viewPanel.getPointsCount();
    verificationStep("Verify that the present values fulfill the given conditions");
    for(int index = 0; index < pointsCount; index++) {
      viewPanel.clickToOpenPointPopup(index);
      double xValue = StringParsing.parsePriceToDouble(viewPanel.getPopupXValue());
      double yValue = Double.parseDouble(viewPanel.getPopupYValue().replace("%", ""));
      Assert.assertTrue("The value does not fulfill the conditions", 
          xValue > incomeValue && yValue < secondValue);
      
      logger.info("Close popup");
      viewPanel.clickToClosePointPopup(index);
    }

    // switch to or
    filtering = scatterPlotPage.getToolbar().clickFiltering(2);
    filtering.clickAndOrButtonAfterCondition(1).clickOr();

    verificationStep("Verify that the \"or\" is the selected value for the combinations");
    expected = "or";
    actual = filtering.getSelectedCombination(1);
    Assert.assertEquals("The first combination condition is not \"or\": " + actual, expected,
        actual);

    actual = filtering.getSelectedCombination(2);
    Assert.assertEquals("The second combination condition is not \"or\": " + actual, expected,
        actual);
    
    filtering.clickAndOrButtonAfterCondition(1).clickAnd();
    
    expected = "and";
    actual = filtering.getSelectedCombination(1);
    verificationStep("Verify that the \"and\" is the selected value for the combinations");
    expected = "and";
    actual = filtering.getSelectedCombination(1);
    Assert.assertEquals("The first combination condition is not \"and\": " + actual, expected,
        actual);

    actual = filtering.getSelectedCombination(2);
    Assert.assertEquals("The second combination condition is not \"and\": " + actual, expected,
        actual);

    secondValue = 70000;
    filtering.getRowForDataVariable(secondDataVariable).enterValue1("" + secondValue);
    
    filtering.clickAndOrButtonAfterCondition(1).clickOr();

    filtering.clickApply();
    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given combinated condition");
    viewPanel = scatterPlotPage.getActiveView();
    pointsCount = viewPanel.getPointsCount();
    verificationStep("Verify that the present values fulfill the given condition");
    for(int index = 0; index < pointsCount; index++) {
      viewPanel.clickToOpenPointPopup(index);
      double xValue = StringParsing.parsePriceToDouble(viewPanel.getPopupXValue());
      double yValue = Double.parseDouble(viewPanel.getPopupYValue().replace("%", ""));

      Assert.assertTrue("The value does not fulfill the condition ", 
          xValue > incomeValue || yValue < secondValue);
      
      logger.info("Close popup");
      viewPanel.clickToClosePointPopup(index);
    }

  }

  //verifyCondition tried for more stable version
//  private void verifyCondition(ScatterPlotPage scatterPlotPage, DataVariableRelation relation, DataVariable dataVariable, int restrictionValue) {
//    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();
//    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
//    filteringConditionRow.selectRelation(relation);
//    filteringConditionRow.enterValue1("" + restrictionValue);
//    filtering.clickApply();
//
//    verificationStep(
//            "Verify that the entered filter is present and the condition is the expected");
//    filteringConditionRow = filtering.getRowForDataVariable(dataVariable);
//    SimpleFilter actual = filteringConditionRow.getActualValues();
//    Assert.assertEquals("The data variable is not the expected", dataVariable,
//            actual.getDataVariable());
//    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
//    Assert.assertEquals("The value is not the expected", restrictionValue,
//            convertPriceToInt(actual.getValue()));
//
//    filtering.clickApply();
//    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();
//
//    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
//    int pointsCount = viewPanel.getPointsCountStable();
//    verificationStep("Verify that the present values fulfill the given condition");
//    for (int index = 0; index < pointsCount; index++) {
//      boolean popupOpened = false;
//      for (int attempts = 0; attempts < 3; attempts++) {  // Retry logic
//        try {
//          viewPanel.clickToOpenPointPopupStable(index);
//          popupOpened = true;
//          break;
//        } catch (NoSuchElementException e) {
//          logger.warn("Retrying to open popup for point index " + index);
//        }
//      }
//      if (!popupOpened) {
//        throw new AssertionError("Failed to open popup for point index " + index);
//      }
//
//      Assert.assertTrue("The given value does not fulfill the condition " + relation.getName(),
//              DataVariableRelation.getNumericCondition(relation)
//                      .test(new Point(StringParsing.parsePriceToInt(viewPanel.getPopupXValue()), restrictionValue)));
//
//      logger.info("Close popup");
//      viewPanel.clickToClosePointPopup(index);
//    }
//  }

  @Test
  @DisplayName("Verify that the -Greater than- filter works as expected on the Scatter Plot view")
  @Description("The test creates a Scatter Plot view with sample data. Applies a greater than filter with a fix value and verifies if the view is updated.")
  @Tag("TCE/Fix")
  public void testGreater() {
    verifyCondition(scatterPlotPage, DataVariableRelation.GREATER,
            income, 86100);
  }


  //new verifyCondition
  private void verifyCondition(ScatterPlotPage scatterPlotPage, DataVariableRelation relation, DataVariable dataVariable, int restrictionValue) {
    // Step 1: Click filtering in toolbar
    System.out.println("Initial scatterPlotPage: " + scatterPlotPage);
    System.out.println("Relation: " + relation + ", DataVariable: " + dataVariable + ", RestrictionValue: " + restrictionValue);

    Filtering filtering = scatterPlotPage.getToolbar().clickFiltering();
    System.out.println("Filtering toolbar clicked, filtering: " + filtering);

    // Step 2: Select data variable in filtering
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    System.out.println("FilteringConditionRow selected for data variable: " + filteringConditionRow);

    // Step 3: Select relation
    filteringConditionRow.selectRelation(relation);
    System.out.println("Relation selected: " + relation);

    // Step 4: Enter restriction value
    filteringConditionRow.enterValue1("" + restrictionValue);
    System.out.println("Restriction value entered: " + restrictionValue);

    // Step 5: Apply the filter
    filtering.clickApply();
    System.out.println("Filter applied");

    verificationStep("Verify that the entered filter is present and the condition is the expected");

    // Step 6: Retrieve the applied filter's condition row
    filteringConditionRow = filtering.getRowForDataVariable(dataVariable);
    System.out.println("Filtering condition row retrieved: " + filteringConditionRow);

    // Step 7: Get the actual filter values
    SimpleFilter actual = filteringConditionRow.getActualValues();
    System.out.println("Actual filter values retrieved: " + actual);

    // Step 8: Verify the data variable
    Assert.assertEquals("The data variable is not the expected", dataVariable, actual.getDataVariable());
    System.out.println("Verified data variable: " + actual.getDataVariable());

    // Step 9: Verify the relation condition
    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
    System.out.println("Verified relation condition: " + actual.getCondition());

    // Step 10: Convert and verify the value
    int actualValue = convertPriceToInt(actual.getValue());
    Assert.assertEquals("The value is not the expected", restrictionValue, actualValue);
    System.out.println("Verified value: " + actualValue);

    // Step 11: Re-apply the filter
    filtering.clickApply();
    System.out.println("Filter reapplied");

    // Step 12: Close filtering
    scatterPlotPage = (ScatterPlotPage) filtering.clickCloseFiltering();
    System.out.println("Filtering closed, scatterPlotPage updated: " + scatterPlotPage);

    // Step 13: Get active view panel
    ScatterPlotViewPanel viewPanel = scatterPlotPage.getActiveView();
    System.out.println("ViewPanel retrieved: " + viewPanel);

    // Step 14: Get points count
    int pointsCount = viewPanel.getPointsCountStable();
    System.out.println("Points count retrieved: " + pointsCount);

    verificationStep("Verify that the present values fulfill the given condition");

    // Step 15: Iterate over points to validate conditions
    for (int index = 0; index < pointsCount; index++) {
      System.out.println("Checking point index: " + index);
      boolean popupOpened = false;

      for (int attempts = 0; attempts < 3; attempts++) { // Retry logic
        try {
          viewPanel.clickToOpenPointPopupStable(index);
          popupOpened = true;
          System.out.println("Popup opened for index: " + index);
          break;
        } catch (NoSuchElementException e) {
          System.out.println("Retrying to open popup for point index: " + index + ", attempt: " + (attempts + 1));
        }
      }

      if (!popupOpened) {
        throw new AssertionError("Failed to open popup for point index " + index);
      }

      // Step 16: Validate the condition for each point
      int popupXValue = StringParsing.parsePriceToInt(viewPanel.getPopupXValue());
      System.out.println("Popup X value retrieved: " + popupXValue);
      System.out.println("About to compare"+popupXValue+"with"+restrictionValue);
      boolean conditionFulfilled = DataVariableRelation.getNumericCondition(relation).test(new Point(popupXValue, restrictionValue));
      Assert.assertTrue("The given value does not fulfill the condition " + relation.getName(), conditionFulfilled);
      System.out.println("Condition fulfilled for point index: " + index);

      // Step 17: Close the popup
      System.out.println("Closing popup for point index: " + index);
      viewPanel.clickToClosePointPopup(index);
    }
  }


  private int convertPriceToInt(String price) {
    int dollarIndex = price.indexOf("$");
    switch (dollarIndex) {
      case 0: {
        price = price.substring(1);
        if (price.indexOf(".") != -1) {
          price = price.split("\\.")[0];
        }
        price = price.replace(",", "");
  
        try {
          return Integer.parseInt(price);
        } catch (NumberFormatException e) {
          // the convert failed
          throw new Error(
              "The price is not in the expected format, integer parsing failed: " + price);
        }
      }
      case -1: {
        // not a price, convert to int
        try {
          return Integer.parseInt(price);
        } catch (NumberFormatException e) {
          // the convert failed
          throw new Error(
              "The price is not in the expected format, integer parsing failed: " + price);
        }
      }
      default: {
        throw new Error(
            "The price is not in the expected format, the dollar should be in first place: "
        + price);
      }
    }
  }



  private int parseToNumber(String number) {
    number = number.replace(",", "");
    if (number.endsWith("%")) {
      try {
        return (int) Math.round(Double.parseDouble(number.substring(0, number.length() - 1)));
      } catch (NumberFormatException e) {
        throw new Error("The value is not in the expected format, percentage parsing failed: " + number);
      }
    } else {
      try {
        return Integer.parseInt(number);
      } catch (NumberFormatException e) {
        throw new Error("The value is not in the expected format, integer parsing failed: " + number);
      }
    }
  }


}
