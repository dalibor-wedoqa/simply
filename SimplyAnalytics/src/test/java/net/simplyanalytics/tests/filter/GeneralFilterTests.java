package net.simplyanalytics.tests.filter;

import java.util.List;

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
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering.FilteringConditionRow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeneralFilterTests extends TestBase {

  // tested on comparison report page, it is faster then map page
  private ComparisonReportPage comparisonReportPage;
  private DataVariable dataVariable = medianDefaultDataVariable;


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
    comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection()
            .clickView(ViewType.COMPARISON_REPORT.getDefaultName());
  }

  @Test
  public void testAddFilter() {
    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();

    verificationStep("Verify that the Apply button is disabled");
    Assert.assertTrue("The apply button is enabled", filtering.isApplyButtonDisabled());

    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(DataVariableRelation.GREATEREQUAL);
    filteringConditionRow.enterValue1("60000");

    verificationStep("Verify that the Apply button is enabled");
    Assert.assertFalse("The apply button is disabled", filtering.isApplyButtonDisabled());

    SimpleFilter actualFilter = filtering.getRowForDataVariable(dataVariable).getActualValues();
    SimpleFilter expectedFilter = new SimpleFilter(dataVariable, DataVariableRelation.GREATEREQUAL,
            "$60,000.00");
    verificationStep("Verify that the condition is the expected");
    Assert.assertTrue("The condition is not the expected", ((actualFilter.toString()).contains(expectedFilter.toString())));
  }

  @Test
  public void testFilterOnLabel() {

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(DataVariableRelation.GREATEREQUAL);
    filteringConditionRow.enterValue1("60000");
    filtering.clickApply();
    filtering.clickCloseFiltering();

    verificationStep("Verify that the \"ON\" label added to the Filtering button");
    Assert.assertTrue("The ON label should be present on the Filtering button",
            comparisonReportPage.getToolbar().isFilterActive());

    filtering = comparisonReportPage.getToolbar().clickFiltering();
    filtering.switchActivity();
    filtering.clickApply();
    filtering.clickCloseFiltering();

    verificationStep("Verify that the \"ON\" label disappeared from the Filtering button");
    Assert.assertFalse("The ON label should not be present on the Filtering button",
            comparisonReportPage.getToolbar().isFilterActive());

    filtering = comparisonReportPage.getToolbar().clickFiltering();
    filtering.switchActivity();
    filtering.clickApply();
    filtering.clickCloseFiltering();

    verificationStep("Verify that the \"ON\" label added to the Filtering button");
    Assert.assertTrue("The ON label should be present on the Filtering button",
            comparisonReportPage.getToolbar().isFilterActive());
  }

  @Test
  public void testDeleteFilter() {

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(DataVariableRelation.GREATEREQUAL);
    filteringConditionRow.enterValue1("60000");
    filtering.clickApply();
    filtering.clickCloseFiltering();

    filtering = comparisonReportPage.getToolbar().clickFiltering();
    filtering.getRowForDataVariable(dataVariable).clickRemoveButton();
    verificationStep("Verify that the condition row disappeared");
    Assert.assertTrue("There should be no conditions", filtering.getAllConditionRow().isEmpty());
  }

  @Test
  public void testCancelFiltering() {

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(DataVariableRelation.GREATEREQUAL);
    filteringConditionRow.enterValue1("60000");
    filtering.clickClose();

    filtering = comparisonReportPage.getToolbar().clickFiltering();
    verificationStep("Verify that the condition row disappeared");
    Assert.assertTrue("There should be no conditions", filtering.getAllConditionRow().isEmpty());
  }

  @Test
  public void testCloseFiltering() {

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering.clickDataVariable(dataVariable);
    filteringConditionRow.selectRelation(DataVariableRelation.GREATEREQUAL);
    filteringConditionRow.enterValue1("60000");
    filtering.clickCloseFiltering();

    filtering = comparisonReportPage.getToolbar().clickFiltering();
    verificationStep("Verify that the condition row disappeared");
    Assert.assertTrue("There should be no conditions", filtering.getAllConditionRow().isEmpty());
  }

  @Test
  public void testSearchDataVariable() {

    Filtering filtering = comparisonReportPage.getToolbar().clickFiltering();
    filtering.enterDataVaraibleSearch("Income");
    List<String> dataVariableNames = filtering.getPresentDataVariables();

    verificationStep("Verify that only the " + dataVariable + " data variable appears");
    Assert.assertTrue("Only one data varaible should be on the list",
            dataVariableNames.size() == 1);
    Assert.assertTrue("The expected data varaible is not on the list",
            (dataVariableNames.toString()).contains(dataVariable.getFullName()));
  }

}
