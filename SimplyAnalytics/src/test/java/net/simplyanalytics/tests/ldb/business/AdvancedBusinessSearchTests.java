package net.simplyanalytics.tests.ldb.business;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.BusinessSearchBy;
import net.simplyanalytics.enums.BusinessSearchConditions;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AndOrDropwdown;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.SearchConditionRow;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessColumnsPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;

/**
 * .
 *
 * @author wedoqa
 */
public class AdvancedBusinessSearchTests extends TestBase {

  private MapPage mapPage;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.FRAMINGHAM_MA_CITY);
  }

  @Test
  public void testSearchByName() {

    BusinessSearchBy searchBy = BusinessSearchBy.NAME;
    String text = "app";

    final String businessStartsName = searchBy.getName().toLowerCase() + " "
        + BusinessSearchConditions.STARTS.getName() + " " + text;

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.CONTAINS,
        text);
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.STARTS,
        text);

    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());

    BusinessColumnsPanel businessColumnsPanel = businessesPage.getToolbar().clickViewActions()
        .clickColumnButton();
    businessColumnsPanel.clickCheckbox("Business Name 2");
    businessColumnsPanel.clickCheckbox("Business Name 3");
    businessColumnsPanel.clickCheckbox("Business Name 4");
    businessColumnsPanel.clickCheckbox("Business Name 5");

    String[] columns = { "Company Name", "Business Name", "Business Name 2", "Business Name 3",
        "Business Name 4", "Business Name 5" };
    businessesPage.getHeaderSection().exitViewActions();

    verificationStep("Verify for each business that the searched text appears in at least one of "
        + "their business name columns");

    List<List<String>> rows = businessesPage.getActiveView().getCellValuesTry(driver, columns);
    for (List<String> cells : rows) {
      Assert.assertTrue("Searched string is not in any of these cells: " + cells,
              cells.stream().anyMatch(cell -> cell.toLowerCase().contains(text.toLowerCase())));
    }

    businessesPage = businessesPage.getToolbar().changeBusiness(businessStartsName);

    verificationStep(
        "Verify for each business that the searched text appears in the beginning in at least one"
            + " of their business name columns");
    rows = businessesPage.getActiveView().getCellValuesTry(driver, columns);

    for (List<String> cells : rows) {
      Assert.assertTrue("Searched string is not in the starts in any of this cells: " + cells,
          cells.stream().anyMatch(cell -> cell.toLowerCase().startsWith(text.toLowerCase())));
    }
  }

  @Test
  public void testSearchBySic() {

    BusinessSearchBy searchBy = BusinessSearchBy.SIC;

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();

    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    String businessesDataNumber = searchConditionRow.selectRandomBusinessCode();
    advancedBusinessSearchWindow.clickSearch();

    mapPage = new MapPage(driver);

    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());

    BusinessColumnsPanel businessColumnsPanel = businessesPage.getToolbar().clickViewActions()
        .clickColumnButton();
    businessColumnsPanel.clickCheckbox("SIC 2");
    businessColumnsPanel.clickCheckbox("SIC 3");
    businessColumnsPanel.clickCheckbox("SIC 4");
    businessColumnsPanel.clickCheckbox("SIC 5");
    businessColumnsPanel.clickCheckbox("SIC 6");

    businessesPage.getHeaderSection().exitViewActions();

    verificationStep(
        "Verify for each business that the searched SIC code appears in at least one of their "
            + "SIC columns");
    String[] columns = { "Primary SIC", "SIC 2", "SIC 3", "SIC 4", "SIC 5", "SIC 6" };

    for (List<String> sicValues : businessesPage.getActiveView().getCellValuesTry(driver, columns)) {
      boolean found = false;
      for (String sicValue : sicValues) {
        if (sicValue.contains(businessesDataNumber)) {
          found = true;
          break;
        }
      }
      Assert.assertTrue("The sic value was not found, searched value: " + businessesDataNumber + ","
          + " sic values in this row: " + sicValues, found);
    }
  }

  @Test
  public void testSearchByNaics() {

    BusinessSearchBy searchBy = BusinessSearchBy.NAICS;

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();

    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    String businessesDataNumber = searchConditionRow.selectRandomBusinessCode();
    advancedBusinessSearchWindow.clickSearch();

    mapPage = new MapPage(driver);
    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());

    BusinessColumnsPanel businessColumnsPanel = businessesPage.getToolbar().clickViewActions()
        .clickColumnButton();
    businessColumnsPanel.clickCheckbox("NAICS 2");
    businessColumnsPanel.clickCheckbox("NAICS 3");
    businessColumnsPanel.clickCheckbox("NAICS 4");
    businessColumnsPanel.clickCheckbox("NAICS 5");
    businessColumnsPanel.clickCheckbox("NAICS 6");

    businessesPage.getHeaderSection().exitViewActions();

    verificationStep(
        "Verify for each business that the searched NAICS code appears in at least one of their "
            + "NAICS columns");
    String[] columns = { "Primary NAICS", "NAICS 2", "NAICS 3", "NAICS 4", "NAICS 5", "NAICS 6" };

    for (List<String> naicsValues : businessesPage.getActiveView().getCellValuesTry(driver, columns)) {
      boolean found = false;
      for (String naicsValue : naicsValues) {
        if (naicsValue.contains(businessesDataNumber)) {
          found = true;
          break;
        }
      }
      Assert.assertTrue("The naics value was not found, searched value: " + businessesDataNumber
          + "," + " naics values in this row: " + naicsValues, found);
    }
  }

  @Test
  public void testSearchByNumberOfLocalEmployees() {

    // Step 1: Initialize the search criteria
    BusinessSearchBy searchBy = BusinessSearchBy.NUMBER_OF_LOCAL_EMPLOYEES;
    System.out.println("Search criteria: " + searchBy.getName());

    // Step 2: Create different search query names
    final String businessGreaterThenName = searchBy.getName().toLowerCase() + " > " + "3,000";
    final String businessLessThenName = searchBy.getName().toLowerCase() + " < " + "5";
    final String businessEqualToName = searchBy.getName().toLowerCase() + " = " + "100";
    final String businessGreaterThenOrEqualToName = searchBy.getName().toLowerCase() + " ≥ " + "2,000";
    final String businessLessThenOrEqualToName = searchBy.getName().toLowerCase() + " ≤ " + "5";
    final String businessNotEqualToName = searchBy.getName().toLowerCase() + " ≠ " + "2";

    System.out.println("Search queries created: " + businessGreaterThenName + ", "
            + businessLessThenName + ", " + businessEqualToName + ", "
            + businessGreaterThenOrEqualToName + ", " + businessLessThenOrEqualToName + ", "
            + businessNotEqualToName);

    // Step 3: Perform search operations using different conditions
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.GREATER, "3000");
    System.out.println("Search by greater than 3000 employees completed.");

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.LESS, "5");
    System.out.println("Search by less than 5 employees completed.");

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.EQUAL, "100");
    System.out.println("Search by equal to 100 employees completed.");

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.GREATEREQUAL, "2000");
    System.out.println("Search by greater than or equal to 2000 employees completed.");

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.LESSEQUAL, "5");
    System.out.println("Search by less than or equal to 5 employees completed.");

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.NOT_EQUAL, "2");
    System.out.println("Search by not equal to 2 employees completed.");

    // Step 4: Switch to the Businesses view
    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
            .clickView(ViewType.BUSINESSES.getDefaultName());
    System.out.println("Switched to Businesses view.");

    // Step 5: Test NOT_EQUAL condition
    businessesPage = businessesPage.getToolbar().changeBusiness(businessNotEqualToName);
    List<String> numberOfEmployeesList = businessesPage.getActiveView().getCellValues("Local Employees").stream()
            .map(list -> list.get(0))
            .collect(Collectors.toList());
    System.out.println("Number of employees (NOT_EQUAL '2'): " + numberOfEmployeesList);
    verifyNumericCondition("Local Employees", BusinessSearchConditions.NOT_EQUAL, numberOfEmployeesList, "2");

    // Step 6: Test GREATER condition
    businessesPage = businessesPage.getToolbar().changeBusiness(businessGreaterThenName);
    numberOfEmployeesList = businessesPage.getActiveView().getCellValues("Local Employees").stream()
            .map(list -> list.get(0))
            .collect(Collectors.toList());
    System.out.println("Number of employees (GREATER '3000'): " + numberOfEmployeesList);
    verifyNumericCondition("Local Employees", BusinessSearchConditions.GREATER, numberOfEmployeesList, "3000");

    // Step 7: Test LESS condition
    businessesPage = businessesPage.getToolbar().changeBusiness(businessLessThenName);
    numberOfEmployeesList = businessesPage.getActiveView().getCellValues("Local Employees").stream()
            .map(list -> list.get(0))
            .collect(Collectors.toList());
    System.out.println("Number of employees (LESS '5'): " + numberOfEmployeesList);
    verifyNumericCondition("Local Employees", BusinessSearchConditions.LESS, numberOfEmployeesList, "5");

    // Step 8: Test GREATEREQUAL condition
    businessesPage = businessesPage.getToolbar().changeBusiness(businessGreaterThenOrEqualToName);
    numberOfEmployeesList = businessesPage.getActiveView().getCellValues("Local Employees").stream()
            .map(list -> list.get(0))
            .collect(Collectors.toList());
    System.out.println("Number of employees (GREATEREQUAL '2000'): " + numberOfEmployeesList);
    verifyNumericCondition("Local Employees", BusinessSearchConditions.GREATEREQUAL, numberOfEmployeesList, "2000");

    // Step 9: Test LESSEQUAL condition
    businessesPage = businessesPage.getToolbar().changeBusiness(businessLessThenOrEqualToName);
    numberOfEmployeesList = businessesPage.getActiveView().getCellValues("Local Employees").stream()
            .map(list -> list.get(0))
            .collect(Collectors.toList());
    System.out.println("Number of employees (LESSEQUAL '5'): " + numberOfEmployeesList);
    verifyNumericCondition("Local Employees", BusinessSearchConditions.LESSEQUAL, numberOfEmployeesList, "5");

    // Step 10: Test EQUAL condition
    businessesPage = businessesPage.getToolbar().changeBusiness(businessEqualToName);
    numberOfEmployeesList = businessesPage.getActiveView().getCellValues("Local Employees").stream()
            .map(list -> list.get(0))
            .collect(Collectors.toList());
    System.out.println("Number of employees (EQUAL '100'): " + numberOfEmployeesList);
    verifyNumericCondition("Local Employees", BusinessSearchConditions.EQUAL, numberOfEmployeesList, "100");
  }


  @Test
  public void testSearchBySalesVolume() {

    driver.manage().window().setSize(new Dimension(800, 600));
    driver.manage().window().maximize();

    BusinessSearchBy searchBy = BusinessSearchBy.SALES_VOLUME;
    String searchNumberValue = "2000000";
    String searchNumber = "2,000,000";

    final String businessGreaterThenName = searchBy.getName().toLowerCase() + " > " + searchNumber;
    final String businessLessThenName = searchBy.getName().toLowerCase() + " < " + searchNumber;
    final String businessEqualToName = searchBy.getName().toLowerCase() + " = " + searchNumber;
    final String businessGreaterThenOrEqualToName = searchBy.getName().toLowerCase() + " ≥ "
        + searchNumber;
    final String businessLessThenOrEqualToName = searchBy.getName().toLowerCase() + " ≤ "
        + searchNumber;
    final String businessNotEqualToName = searchBy.getName().toLowerCase() + " ≠ " + searchNumber;

    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.GREATER,
        searchNumberValue);
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.LESS,
        searchNumberValue);
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.EQUAL,
        searchNumberValue);
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.GREATEREQUAL,
        searchNumberValue);
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.LESSEQUAL,
        searchNumberValue);
    mapPage = (MapPage) selectAdvancedBusinessSearch(mapPage, searchBy, BusinessSearchConditions.NOT_EQUAL,
        searchNumberValue);

    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());

    businessesPage = businessesPage.getToolbar().changeBusiness(businessNotEqualToName);
    List<String> salesVolumeList = businessesPage.getActiveView().getCellValues("Sales Volume")
        .stream().map(list -> list.get(0)).collect(Collectors.toList());
    verifyNumericCondition("Sales Volume", BusinessSearchConditions.NOT_EQUAL, salesVolumeList,
        searchNumberValue);

    businessesPage = businessesPage.getToolbar().changeBusiness(businessGreaterThenName);
    salesVolumeList = businessesPage.getActiveView().getCellValues("Sales Volume").stream()
        .map(list -> list.get(0)).collect(Collectors.toList());
    verifyNumericCondition("Sales Volume", BusinessSearchConditions.GREATER, salesVolumeList,
        searchNumberValue);

    businessesPage = businessesPage.getToolbar().changeBusiness(businessLessThenName);
    salesVolumeList = businessesPage.getActiveView().getCellValues("Sales Volume").stream()
        .map(list -> list.get(0)).collect(Collectors.toList());
    verifyNumericCondition("Sales Volume", BusinessSearchConditions.LESS, salesVolumeList,
        searchNumberValue);

    businessesPage = businessesPage.getToolbar().changeBusiness(businessGreaterThenOrEqualToName);
    salesVolumeList = businessesPage.getActiveView().getCellValues("Sales Volume").stream()
        .map(list -> list.get(0)).collect(Collectors.toList());
    verifyNumericCondition("Sales Volume", BusinessSearchConditions.GREATEREQUAL, salesVolumeList,
        searchNumberValue);

    businessesPage = businessesPage.getToolbar().changeBusiness(businessLessThenOrEqualToName);
    salesVolumeList = businessesPage.getActiveView().getCellValues("Sales Volume").stream()
        .map(list -> list.get(0)).collect(Collectors.toList());
    verifyNumericCondition("Sales Volume", BusinessSearchConditions.LESSEQUAL, salesVolumeList,
        searchNumberValue);

    businessesPage = businessesPage.getToolbar().changeBusiness(businessEqualToName);
    salesVolumeList = businessesPage.getActiveView().getCellValues("Sales Volume").stream()
        .map(list -> list.get(0)).collect(Collectors.toList());
    verifyNumericCondition("Sales Volume", BusinessSearchConditions.EQUAL, salesVolumeList,
        searchNumberValue);
  }
  
  @Test
  public void testMultipleConditionWithConjunction() {

    BusinessSearchBy searchBy1 = BusinessSearchBy.NAME;
    String searchText = "space";
    BusinessSearchBy searchBy2 = BusinessSearchBy.NUMBER_OF_LOCAL_EMPLOYEES;
    int employeeNumber = 20;

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy1);
    searchConditionRow.selectCondition(BusinessSearchConditions.CONTAINS);
    searchConditionRow.enterSearchValue(searchText);

    advancedBusinessSearchWindow = advancedBusinessSearchWindow.clickAddCondition();

    verificationStep("Verify that the condition is by default 'and'");
    Assert.assertEquals("The default divider condition should be 'and'", "and",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(0));

    searchConditionRow = advancedBusinessSearchWindow.getConditionRow(1);
    searchConditionRow.selectSearchBy(searchBy2);
    searchConditionRow.selectCondition(BusinessSearchConditions.GREATER);
    searchConditionRow.enterSearchValue("" + employeeNumber);

    advancedBusinessSearchWindow.clickSearch();

    mapPage = new MapPage(driver);
    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());

    BusinessColumnsPanel businessColumnsPanel = businessesPage.getToolbar().clickViewActions()
        .clickColumnButton();
    businessColumnsPanel.clickCheckbox("Business Name 2");
    businessColumnsPanel.clickCheckbox("Business Name 3");
    businessColumnsPanel.clickCheckbox("Business Name 4");
    businessColumnsPanel.clickCheckbox("Business Name 5");

    String[] columns = { "Company Name", "Business Name", "Business Name 2", "Business Name 3",
        "Business Name 4", "Business Name 5" };
    businessesPage.getHeaderSection().exitViewActions();

    verificationStep("Verify for each business that the searched text "
        + "appears in at least one of their business name columns");
    List<List<String>> rows = businessesPage.getActiveView().getCellValuesTry(driver, columns);
    for (List<String> cells : rows) {
      Assert.assertTrue("Searched string is not in any of this cells: " + cells,
          cells.stream().anyMatch(cell -> cell.toLowerCase().contains(searchText.toLowerCase())));
    }

    verificationStep("Verify for each business that the number of local employees is greater than "
        + employeeNumber);
    String[] columns2 = { "Local Employees" };
    for (List<String> employeeNumbers : businessesPage.getActiveView().getCellValuesTry(driver, columns2)) {
      Assert.assertTrue(
          "The number of employees (: " + employeeNumbers + ") should be greated than: "
              + employeeNumber,
          employeeNumbers.stream().anyMatch(actual -> Integer.parseInt(actual.replace(",", "")) > employeeNumber));
    }
  }

  @Test
  public void testMultipleConditionWithDisjunction() {

    BusinessSearchBy searchBy1 = BusinessSearchBy.NAME;
    String searchText = "satellite";
    BusinessSearchBy searchBy2 = BusinessSearchBy.NAME;
    String searchText2 = "rocket";

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy1);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    searchConditionRow.enterSearchValue(searchText);

    advancedBusinessSearchWindow = advancedBusinessSearchWindow.clickAddCondition();

    AndOrDropwdown andOrDropwdown = advancedBusinessSearchWindow.clickDividerCondition(0);
    andOrDropwdown.clickOr();

    verificationStep("Verify that the condition is 'or'");
    Assert.assertEquals("The condition should be 'or'", "or",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(0));

    searchConditionRow = advancedBusinessSearchWindow.getConditionRow(1);
    searchConditionRow.selectSearchBy(searchBy2);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    searchConditionRow.enterSearchValue(searchText2);

    advancedBusinessSearchWindow.clickSearch();

    mapPage = new MapPage(driver);
    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());

    BusinessColumnsPanel businessColumnsPanel = businessesPage.getToolbar().clickViewActions()
        .clickColumnButton();
    businessColumnsPanel.clickCheckbox("Business Name 2");
    businessColumnsPanel.clickCheckbox("Business Name 3");
    businessColumnsPanel.clickCheckbox("Business Name 4");
    businessColumnsPanel.clickCheckbox("Business Name 5");

    String[] columns = { "Company Name", "Business Name", "Business Name 2", "Business Name 3",
        "Business Name 4", "Business Name 5" };
    businessesPage.getHeaderSection().exitViewActions();

    verificationStep("Verify for each business that at least one "
        + "of the search text appears in at least one of their business name columns");
    List<List<String>> rows = businessesPage.getActiveView().getCellValuesTry(driver, columns);
    for (List<String> cells : rows) {
      Assert.assertTrue("Searched string is not in any of this cells: " + cells,
          cells.stream().anyMatch(cell -> cell.toLowerCase().startsWith(searchText.toLowerCase())
              || cell.toLowerCase().startsWith(searchText2.toLowerCase())));
    }
  }

  @Test
  public void testMultipleConditionDividerChange() {

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();

    advancedBusinessSearchWindow.clickAddCondition();

    verificationStep("Verify that the condition is by default 'and'");
    Assert.assertEquals("The default divider condition should be 'and'", "and",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(0));

    advancedBusinessSearchWindow.clickAddCondition();

    verificationStep("Verify that the new condition is the default 'and' as well");
    Assert.assertEquals("The new divider condition should be 'and'", "and",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(1));

    advancedBusinessSearchWindow.clickDividerCondition(1).clickOr();

    verificationStep("Verify that both condition is changed to 'or'");
    Assert.assertEquals("The first divider condition should be 'or'", "or",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(0));
    Assert.assertEquals("The second divider condition should be 'or'", "or",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(1));

    advancedBusinessSearchWindow.clickDividerCondition(0).clickAnd();

    verificationStep("Verify that both condition is changed to 'and'");
    Assert.assertEquals("The first divider condition should be 'and'", "and",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(0));
    Assert.assertEquals("The second divider condition should be 'and'", "and",
        advancedBusinessSearchWindow.getActualValueForDividerCondition(1));

  }

  @Test
  public void testMultipleConditionLimit() {

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();

    advancedBusinessSearchWindow = advancedBusinessSearchWindow.clickAddCondition();
    advancedBusinessSearchWindow = advancedBusinessSearchWindow.clickAddCondition();

    verificationStep("Verify that the Add condition button is enabled");
    Assert.assertTrue("The Add conditon button should be enabled",
        advancedBusinessSearchWindow.isAddConditionButtonEnabled());

    advancedBusinessSearchWindow = advancedBusinessSearchWindow.clickAddCondition();

    verificationStep("Verify that the Add condition button is disabled");
    Assert.assertFalse("The Add conditon button should be disabled",
        advancedBusinessSearchWindow.isAddConditionButtonEnabled());

    advancedBusinessSearchWindow = advancedBusinessSearchWindow.getConditionRow(3)
        .clickRemoveButton();

    verificationStep("Verify that the Add condition button is enabled again");
    Assert.assertTrue("The Add conditon button should be enabled",
        advancedBusinessSearchWindow.isAddConditionButtonEnabled());

  }

  private void verifyNumericCondition(String fieldName, BusinessSearchConditions condition,
      List<String> cells, String searchNumberValue) {
    verificationStep("Verify for each business that their " + fieldName + " field " + condition
        + " " + searchNumberValue);
    Assert.assertTrue(
        "The cells do not match the condition: " + condition + "\n\n cells: " + cells + "\n\n "
            + searchNumberValue,
        cells.stream().filter(numberString -> !numberString.isEmpty()).map(numberString -> {
          int number = Integer
              .parseInt(numberString.replace(",", "").replace(" ", "").replace("$", ""));
          int searchNumber = Integer.parseInt(searchNumberValue);
          return new Point(number, searchNumber);
        }).allMatch(BusinessSearchConditions.getNumericCondition(condition)));

  }

  private BaseViewPage selectAdvancedBusinessSearch(BaseViewPage baseViewPage, BusinessSearchBy searchBy,
      BusinessSearchConditions searchCondition, String value) {
    BusinessesTab businessesTab = baseViewPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy);
    Assert.assertEquals("The search by is not the expected", searchBy,
        searchConditionRow.getActualSearchBy());
    searchConditionRow.selectCondition(searchCondition);
    Assert.assertEquals("The search condition is not the expected", searchCondition,
        searchConditionRow.getActualSearchCondition());
    searchConditionRow.enterSearchValue(value);
    advancedBusinessSearchWindow.clickSearch();
    return baseViewPage;
  }
}