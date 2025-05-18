package net.simplyanalytics.tests.filter;

import java.awt.Point;
import java.util.Map;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.containers.SimpleFilter;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.DataVariableRelation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering.FilteringConditionRow;
import net.simplyanalytics.pageobjects.sections.toolbar.ranking.LocationTypeDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.ranking.RankingToolbar;
import net.simplyanalytics.pageobjects.sections.view.RankingViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.StringParsing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RankingFilterTests extends TestBase {

  private RankingPage rankingPage;
  private DataVariable income = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;

  /**
   * Signing in and creating new project and open ranking page.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

    LocationsTab locationsTab = mapPage.getLdbSection().clickLocations();
    mapPage = locationsTab.chooseLocation(Location.CALIFORNIA);

    rankingPage = (RankingPage) mapPage.getViewChooserSection()
        .clickView(ViewType.RANKING.getDefaultName());
  }

  @Test
  public void testFilterOpens() {
    Filtering filtering = rankingPage.getToolbar().clickFiltering();

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
    final DataVariable dataVariable = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;
    final DataVariableRelation relation = DataVariableRelation.GREATEREQUAL;
    final int restrictionValue = 65073;

    RankingToolbar toolbar = rankingPage.getToolbar();
    toolbar.clickTopNumberOfDataInTableCombo(10);
    LocationTypeDropdown filterBy = toolbar.clickFilterByLocationTypeListMenu();
    rankingPage = filterBy.clickFilterBy(LocationType.ZIP_CODE);
    rankingPage = (RankingPage) toolbar.clickDataVariable().clickOnLocationName();

    Filtering filtering = rankingPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue);
    filtering.clickApply();

    filtering.clickHideRadioButton();
    verificationStep("Verify that the Hide radio button is active now");
    Assert.assertTrue("The Hide radio button should be active now", filtering.isHideActive());
    filtering.clickApply();
    rankingPage = (RankingPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = rankingPage.getActiveView().getColumns(dataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          int intValue = StringParsing.parsePriceToInt(value);
          return DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
        }));
  }

  @Test
  public void testStrikeout() {
    final DataVariable dataVariable = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;
    final DataVariableRelation relation = DataVariableRelation.GREATEREQUAL;
    final int restrictionValue = 110753;

    RankingToolbar toolbar = rankingPage.getToolbar();
    toolbar.clickTopNumberOfDataInTableCombo(10);
    LocationTypeDropdown filterBy = toolbar.clickFilterByLocationTypeListMenu();
    filterBy.clickFilterBy(LocationType.ZIP_CODE);
    toolbar.clickDataVariable()
        .clickSortByDatavariable(DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024);

    Filtering filtering = rankingPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(relation);
    filteringConditionRow.enterValue1("" + restrictionValue);
    filtering.clickApply();

    filtering.clickStrikeoutRadioButton();
    verificationStep("Verify that the Strikeout radio button is active now");
    Assert.assertTrue("The Strikeout radio button should be active now",
        filtering.isStrikeoutActive());
    filtering.clickApply();
    rankingPage = (RankingPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = rankingPage.getActiveView().getColumns(dataVariable.getFullName());

    RankingViewPanel rankingViewPanel = rankingPage.getActiveView();

    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean valueInFilterRange = DataVariableRelation.getNumericCondition(relation)
              .test(new Point(intValue, restrictionValue));
          boolean strikeout = rankingViewPanel.isRowStrikeouted(entry.getKey());
          return valueInFilterRange ^ strikeout;// XOR, it should be in range or be strikeout
        }));
  }

  @Test
  public void testGreater() {
    verifyCondition(rankingPage, DataVariableRelation.GREATER,
        income, 65073);
  }

  @Test
  public void testLess() {
    verifyCondition(rankingPage, DataVariableRelation.LESS,
    		income, 65073);
  }

  @Test
  public void testEquals() {
    verifyCondition(rankingPage, DataVariableRelation.EQUAL,
    		income, 65073);
  }

  @Test
  public void testGreaterEquals() {
    verifyCondition(rankingPage, DataVariableRelation.GREATEREQUAL,
    		income, 65073);
  }

  @Test
  public void testLessEquals() {
    verifyCondition(rankingPage, DataVariableRelation.LESSEQUAL,
    		income, 65073);
  }

  @Test
  public void testNotEquals() {
    verifyCondition(rankingPage, DataVariableRelation.NOT_EQUAL,
    		income, 65073);
  }

  @Test
  public void testBetween() {
    DataVariable dataVariable = income;
    DataVariableRelation relation = DataVariableRelation.BETWEEN;
    int restrictionValue1 = 60000;
    int restrictionValue2 = 70000;

    Filtering filtering = rankingPage.getToolbar().clickFiltering();
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
    rankingPage = (RankingPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = rankingPage.getActiveView().getColumns(dataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          int intValue = StringParsing.parsePriceToInt(value);
          return (restrictionValue1 < intValue && intValue < restrictionValue2);
        }));
  }

  @Test
  public void testCombinatedConditions() {
    DataVariable income = medianDefaultDataVariable;
    DataVariableRelation greater = DataVariableRelation.GREATER;
    int incomeValue = 55000;

    // create AND filter
    final DataVariable secondDataVariable = firstDefaultDataVariable;
    final DataVariableRelation less = DataVariableRelation.LESS;
    double secondValue = 50;

    //added -> will be removed
    RankingToolbar toolbar = rankingPage.getToolbar();
    toolbar.clickTopNumberOfDataInTableCombo(10);
    //
    Filtering filtering = rankingPage.getToolbar().clickFiltering();

    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(income);
    filteringConditionRow.selectRelation(greater);
    filteringConditionRow.enterValue1("" + incomeValue);

    verificationStep("Verify that the \"and\" is the default selected value for combination");
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
    rankingPage = (RankingPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given combinated condition");
    RankingViewPanel rankingViewPanel = rankingPage.getActiveView();
    Map<String, String> incomeValues = rankingViewPanel.getColumns(income.getFullName());
    Map<String, String> educationValues = rankingViewPanel.getColumns(secondDataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + incomeValues,
        incomeValues.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean incomeFilter = DataVariableRelation.getNumericCondition(greater)
              .test(new Point(intValue, incomeValue));

          double actualEducation = StringParsing
              .parsePercentageToDouble(educationValues.get(entry.getKey()));
          boolean educationFilter = DataVariableRelation.getNumericCondition(less)
              .test(new Point((int) (actualEducation * 100), (int) (secondValue * 100)));

          boolean strikeout = rankingViewPanel.isCellStrikeout(entry.getKey(),
              income.getFullName());
          return (incomeFilter && educationFilter) ^ strikeout;
        }));

    // switch to or
    filtering = rankingPage.getToolbar().clickFiltering(2);
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
    rankingPage = (RankingPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given combinated condition");
    RankingViewPanel rankingViewPanel2 = rankingPage.getActiveView();
    incomeValues = rankingViewPanel2.getColumns(income.getFullName());
    Map<String, String> educationValues2 = rankingViewPanel2.getColumns(secondDataVariable.getFullName());
    Assert.assertTrue("Not all locations fulfill the restriction: " + incomeValues,
        incomeValues.entrySet().stream().allMatch(entry -> {
          int intValue = StringParsing.parsePriceToInt(entry.getValue());
          boolean incomeFilter = DataVariableRelation.getNumericCondition(greater)
              .test(new Point(intValue, incomeValue));

          double actualEducation = StringParsing
              .parsePercentageToDouble(educationValues2.get(entry.getKey()));
          boolean educationFilter = DataVariableRelation.getNumericCondition(less)
              .test(new Point((int) (actualEducation * 100), (int) (secondValue * 100)));

          boolean strikeout = rankingViewPanel2.isCellStrikeout(entry.getKey(),
              income.getFullName());
          return (incomeFilter || educationFilter) ^ strikeout;
        }));
  }

  private void verifyCondition(RankingPage rankingPage, DataVariableRelation relation,
      DataVariable dataVariable, int restrictionValue) {
    Filtering filtering = rankingPage.getToolbar().clickFiltering();
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
    rankingPage = (RankingPage) filtering.clickCloseFiltering();

    verificationStep("Verify that the present locations fulfill the given restriction");
    Map<String, String> values = rankingPage.getActiveView().getColumns(dataVariable.getFullName());
   
    Assert.assertTrue("Not all locations fulfill the restriction: " + values,
        values.values().stream().allMatch(value -> {
          String strValue = StringParsing.parsePriceToString(value);
          int intValue = StringParsing.parsePriceToInt(strValue);
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
