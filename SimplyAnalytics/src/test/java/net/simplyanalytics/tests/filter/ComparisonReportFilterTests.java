package net.simplyanalytics.tests.filter;

import java.awt.Point;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.containers.SimpleFilter;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.DataVariableRelation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering.FilteringConditionRow;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.ComparisonReportViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.StringParsing;

public class ComparisonReportFilterTests extends TestBase {

  private ComparisonReportPage comparisonReportPage;
  private DataVariable income = medianDefaultDataVariable;

  /**
   * Signing in and creating new project and open comparision report page.
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

    LocationsTab locationsTab = mapPage.getLdbSection().clickLocations();
    locationsTab.chooseLocation(Location.CALIFORNIA);
    locationsTab.chooseLocation(Location.FLORIDA);
    locationsTab.chooseLocation(Location.WASHINGTON_DC_CITY);
    locationsTab.chooseLocation(Location.MIAMI_FL_CITY);
    locationsTab.chooseLocation(Location.CHICAGO_IL_CITY);
    locationsTab.chooseLocation(Location.ZIP_90034_LOS_ANGELES);
    locationsTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);

    comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection()
        .clickView(ViewType.COMPARISON_REPORT.getDefaultName());
  }

  @Test
  public void testFilterOpens() {
    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();

    verificationStep("Verify that the hide and strikeout buttons appeared");
    Assert.assertTrue("The hide button missing", filtering.isHidePresent());
    Assert.assertTrue("The strikeout button missing", filtering.isStrikeoutPresent());

    verificationStep("Verify that the default data variables are present");
    defaultDataVariables.stream()
        .forEach(dataVariable -> Assert.assertTrue("The data variable is missing: " + dataVariable,
            filtering.isDataVariableSelectable(dataVariable)));
  }

  @Test
  public void testHide() {
    DataVariable dataVariable = income;
    DataVariableRelation relation = DataVariableRelation.GREATEREQUAL;
    int restrictionValue = 65000;

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue);
    filtering.clickApply();

    filtering.clickHideRadioButton();
    verificationStep("Verify that the Hide radio button is active now");
    Assert.assertTrue("The Hide radio button should be active now", filtering.isHideActive());
    filtering.clickApply();
    comparisonReportPage = (ComparisonReportPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = comparisonReportPage.getActiveView()
        .getRow(dataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          int intValue = StringParsing.parsePriceToInt(value);
          return DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
        }));

    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
        .clickTransposeReport();

    verificationStep("Verify that the present locations fulfill the given restriction");
    values = comparisonReportPage.getActiveView().getColumns(dataVariable.getFullName());

    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          int intValue = StringParsing.parsePriceToInt(value);
          return DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
        }));
  }

  @Test
  public void testStrikeout() {
    DataVariable dataVariable = income;
    DataVariableRelation relation = DataVariableRelation.GREATEREQUAL;
    int restrictionValue = 65000;

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue);
    filtering.clickApply();

    filtering.clickStrikeoutRadioButton();
    verificationStep("Verify that the Strikeout radio button is active now");
    Assert.assertTrue("The Strikeout radio button should be active now",
        filtering.isStrikeoutActive());
    filtering.clickApply();
    comparisonReportPage = (ComparisonReportPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");

    ComparisonReportViewPanel comparisonReportViewPanel = comparisonReportPage.getActiveView();

    Map<String, String> values = comparisonReportPage.getActiveView()
        .getRow(dataVariable.getFullName());

    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean valueInFilterRange = DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
          boolean strikeout = comparisonReportViewPanel.isCellStrikeout(dataVariable.getFullName() ,entry.getKey());
          return valueInFilterRange ^ strikeout;// XOR, it should be in range or be strikeout
        }));

    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();
    ComparisonReportViewPanel comparisonReportViewPanel2 = comparisonReportPage.getActiveView();
    verificationStep("Verify that the present locations fulfill the given restriction");
    values = comparisonReportPage.getActiveView().getColumns(dataVariable.getFullName());

    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean valueInFilterRange = DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
          boolean strikeout = comparisonReportViewPanel2.isRowStrikeouted(entry.getKey());
          return valueInFilterRange ^ strikeout;// XOR, it should be in range or be strikeout
        }));

  }

  @Test
  public void testGreater() {
    verifyCondition(comparisonReportPage, DataVariableRelation.GREATER,
        income, 60853);
  }

  @Test
  public void testLess() {
    verifyCondition(comparisonReportPage, DataVariableRelation.LESS,
        income, 60853);
  }

  @Test
  public void testEquals() {
    verifyCondition(comparisonReportPage, DataVariableRelation.EQUAL,
        income, 60853);
  }

  @Test
  public void testGreaterEquals() {
    verifyCondition(comparisonReportPage, DataVariableRelation.GREATEREQUAL,
        income, 60853);
  }

  @Test
  public void testLessEquals() {
    verifyCondition(comparisonReportPage, DataVariableRelation.LESSEQUAL,
        income, 60853);
  }

  @Test
  public void testNotEquals() {
    verifyCondition(comparisonReportPage, DataVariableRelation.NOT_EQUAL,
        income, 60853);
  }

  @Test
  public void testBetween() {
    DataVariable dataVariable = income;
    DataVariableRelation relation = DataVariableRelation.BETWEEN;
    int restrictionValue1 = 60000;
    int restrictionValue2 = 70000;

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue1);
    filteringConditionRow.enterValue2("" + restrictionValue2);
    filtering.clickApply();

    verificationStep(
        "Verify that the entered filter is peresent and the condition is the expected");
    filteringConditionRow = filtering.getRowForDataVariable(dataVariable);
    SimpleFilter actual = filteringConditionRow.getActualValues();
    Assert.assertEquals("The data variabe is not the expected", dataVariable,
        actual.getDataVariable());
    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
    Assert.assertEquals("The value 1 is not the expected", restrictionValue1,
        convertPriceToInt(actual.getValue()));
    Assert.assertEquals("The value 2 is not the expected", restrictionValue2,
        convertPriceToInt(actual.getValue2()));

    filtering.clickHideRadioButton();
    filtering.clickApply();
    comparisonReportPage = (ComparisonReportPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = comparisonReportPage.getActiveView()
        .getRow(dataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          int intValue = StringParsing.parsePriceToInt(value);
          return (restrictionValue1 < intValue && intValue < restrictionValue2);
        }));
  }

  @Test
  public void testCombinatedConditions() {
    DataVariable income = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;
    DataVariableRelation greater = DataVariableRelation.GREATER;
    int incomeValue = 70000;

    // create AND filter
    final DataVariable housingBuilt = 
    		defaultDataVariables.get(0);
    final DataVariableRelation less = DataVariableRelation.LESS;
    final double educationPercentage = 40.0;

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();

    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(income);
    filteringConditionRow.selectRelation(greater);
    filteringConditionRow.enterValue1("" + incomeValue);

    verificationStep("Verify that the \"and\" is the default selected value for combination");
    String actual = filtering.getSelectedCombination(1);
    String expected = "and";
    Assert.assertEquals("The default value is not \"and\": " + actual, expected, actual);

    filteringConditionRow = filtering.clickDataVariable(housingBuilt);
    filteringConditionRow.selectRelation(less);
    filteringConditionRow.enterValue1("" + educationPercentage);

    verificationStep(
        "Verify that the \"and\" is the selected value for the new condition row as well");
    actual = filtering.getSelectedCombination(2);
    expected = "and";
    Assert.assertEquals("The value is not \"and\": " + actual, expected, actual);

    filtering.clickApply();
    comparisonReportPage = (ComparisonReportPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given combinated condition");
    ComparisonReportViewPanel comparisonReportViewPanel = comparisonReportPage.getActiveView();
    Map<String, String> incomeValues = comparisonReportViewPanel.getRow(income.getFullName());
    Map<String, String> educationValues = comparisonReportViewPanel.getRow(housingBuilt.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + incomeValues,
        incomeValues.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean incomeFilter = DataVariableRelation.getNumericCondition(greater)
              .test(new Point(intValue, incomeValue));

          double actualEducation = StringParsing
              .parsePercentageToDouble(educationValues.get(entry.getKey()));
          boolean educationFilter = DataVariableRelation.getNumericCondition(less)
              .test(new Point((int) (actualEducation * 100), (int) (educationPercentage * 100)));

          boolean strikeout = comparisonReportViewPanel.isCellStrikeout(income.getFullName(),
              entry.getKey());
          return (incomeFilter && educationFilter) ^ strikeout;
        }));

    // switch to or
    filtering = comparisonReportPage.getToolbar().clickFiltering(2);
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
    verificationStep("Verify that the \"or\" is the selected value for the combinations");
    expected = "and";
    actual = filtering.getSelectedCombination(1);
    Assert.assertEquals("The first combination condition is not \"and\": " + actual, expected,
        actual);

    actual = filtering.getSelectedCombination(2);
    Assert.assertEquals("The second combination condition is not \"and\": " + actual, expected,
        actual);

    filtering.clickAndOrButtonAfterCondition(1).clickOr();

    filtering.clickApply();
    comparisonReportPage = (ComparisonReportPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given combinated condition");
    ComparisonReportViewPanel comparisonReportViewPanel2 = comparisonReportPage.getActiveView();
    incomeValues = comparisonReportViewPanel2.getRow(income.getFullName());
    Map<String, String> educationValues2 = comparisonReportViewPanel2
        .getRow(housingBuilt.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + incomeValues,
        incomeValues.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean incomeFilter = DataVariableRelation.getNumericCondition(greater)
              .test(new Point(intValue, incomeValue));

          double actualEducation = StringParsing
              .parsePercentageToDouble(educationValues2.get(entry.getKey()));
          boolean educationFilter = DataVariableRelation.getNumericCondition(less)
              .test(new Point((int) (actualEducation * 100), (int) (educationPercentage * 100)));

          boolean strikeout = comparisonReportViewPanel2.isCellStrikeout(income.getFullName(),
              entry.getKey());
          return (incomeFilter || educationFilter) ^ strikeout;
        }));
  }

  private void verifyCondition(ComparisonReportPage comparisonReportPage,
      DataVariableRelation relation, DataVariable dataVariable, int restrictionValue) {
    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue);
    filtering.clickApply();

    verificationStep(
        "Verify that the entered filter is peresent and the condition is the expected");
    filteringConditionRow = filtering.getRowForDataVariable(dataVariable);
    SimpleFilter actual = filteringConditionRow.getActualValues();
    Assert.assertEquals("The data variabe is not the expected", dataVariable,
        actual.getDataVariable());
    Assert.assertEquals("The condition is not the expected", relation, actual.getCondition());
    Assert.assertEquals("The value is not the expected", restrictionValue,
        convertPriceToInt(actual.getValue()));

    filtering.clickHideRadioButton();
    filtering.clickApply();
    comparisonReportPage = (ComparisonReportPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = comparisonReportPage.getActiveView()
        .getRow(dataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          int intValue = StringParsing.parsePriceToInt(value);
          return DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
        }));
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
}
