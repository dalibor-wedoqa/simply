package net.simplyanalytics.tests.view.actions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.BusinessSearchBy;
import net.simplyanalytics.enums.BusinessSearchConditions;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.SearchConditionRow;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessColumnsPanel;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.BusinessesViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusinessViewActionTests extends TestBase {

  private MapPage mapPage;
  private BusinessesPage businessesPage;

  /**
   * Signing in, creating new project and add business.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(BusinessSearchBy.NAICS);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    searchConditionRow.selectRandomBusinessCode();
    advancedBusinessSearchWindow.clickSearch();
    mapPage = new MapPage(getDriver());
    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());
  }

  @Test
  public void testColumnsPresence() {
    BusinessViewActionMenu viewActionsMenu = businessesPage.getToolbar().clickViewActions();
    BusinessColumnsPanel businessColumnsPanel = viewActionsMenu.clickColumnButton();

    Map<String, Boolean> columnValues = businessColumnsPanel.getCheckboxValues();
    columnValues = businessColumnsPanel.getCheckboxValues();
    businessesPage.getHeaderSection().clickOnBlankSpace();

    BusinessesViewPanel businessesViewPanel = businessesPage.getActiveView();
    List<String> tableColumns = businessesViewPanel.getNormalHeaderValues();
    tableColumns.add("Company Name");

    verificationStep("Verify that the columns are displayed in the business table");
    Assert.assertTrue(
        "The columns should be present in the business table. \ntableColumns: " + tableColumns
            + " \ncolumnValues: " + columnValues,
        tableColumns.containsAll(columnValues.entrySet().stream().filter(entry -> entry.getValue())
            .map(entry -> entry.getKey()).collect(Collectors.toList())));

    viewActionsMenu = businessesPage.getToolbar().clickViewActions();
    businessColumnsPanel = viewActionsMenu.clickColumnButton();

    businessColumnsPanel.clickCheckbox("Zip Code");
    businessColumnsPanel.clickCheckbox("City");
    businessColumnsPanel.clickCheckbox("Mailing City");

    columnValues.replace("Zip Code", !columnValues.get("Zip Code"));
    columnValues.replace("City", !columnValues.get("City"));
    columnValues.replace("Mailing City", !columnValues.get("Mailing City"));

    businessesPage.getHeaderSection().clickOnBlankSpace();

    tableColumns = businessesViewPanel.getNormalHeaderValues();
    tableColumns.add("Company Name");

    verificationStep("Verify that the columns are displayed in the business table");
    Assert.assertTrue("The columns should be present in the business table",
        tableColumns.containsAll(columnValues.entrySet().stream().filter(entry -> entry.getValue())
            .map(entry -> entry.getKey()).collect(Collectors.toList())));
  }

}
