package net.simplyanalytics.tests.tabledropdown.headerdropdown.sort;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.base.HeaderDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.StringParsing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparisonReportSortingTests extends TestBase {

  private ComparisonReportPage comparisonReportPage;

  /**
   * Signing in and creating new project.
   * Deselect variable.
   * Select numeric value.
   * Open the comparison report page.
   */
  @Before
  public void login() {
    System.out.println("Starting login sequence...");

    // Step 1: Auth page
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    System.out.println("Initialized AuthenticateInstitutionPage.");

    // Step 2: Login using credentials
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    System.out.println("Logged in with institution credentials.");

    // Step 3: Click Sign In As Guest
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    System.out.println("Clicked 'Sign In As Guest'.");

    // Step 4: Close welcome tutorial
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    System.out.println("Closed the Welcome Tutorial window.");

    // Step 5: Create new project with location
    NewProjectVariablesWindow newProjectVariablesWindow =
            createNewProjectWindow.createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    System.out.println("Created new project with location: " + Location.LOS_ANGELES_CA_CITY.getName());

    // Step 6: Deselect the first default variable
    System.out.println("Deselecting seed variables");
    String deselectVar = defaultDataVariables.get(0).getName();
    System.out.println("Name of the variable:"+deselectVar);
    newProjectVariablesWindow.clickDataVariable(deselectVar);
    System.out.println("Deselected default variable: " + deselectVar);

    // Step 7: Select specific numeric data variable
    String targetVar = DataVariable.PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2024.getName();
    newProjectVariablesWindow.clickDataVariable(targetVar);
    System.out.println("Selected variable: " + targetVar);

    // Step 8: Create the project and land on the map page
    MapPage mapPage = newProjectVariablesWindow.clickCreateProjectButton();
    System.out.println("Created project and navigated to Map Page.");

    // Step 9: Open the Locations tab and choose multiple locations
    LocationsTab locationsTab = mapPage.getLdbSection().clickLocations();
    System.out.println("Opened Locations tab.");

    locationsTab.chooseLocation(Location.CALIFORNIA);
    System.out.println("Selected Location: California");
    locationsTab.chooseLocation(Location.FLORIDA);
    System.out.println("Selected Location: Florida");
    locationsTab.chooseLocation(Location.WASHINGTON_DC_CITY);
    System.out.println("Selected Location: Washington DC (City)");
    locationsTab.chooseLocation(Location.MIAMI_FL_CITY);
    System.out.println("Selected Location: Miami, FL (City)");
    locationsTab.chooseLocation(Location.CHICAGO_IL_CITY);
    System.out.println("Selected Location: Chicago, IL (City)");
    locationsTab.chooseLocation(Location.ZIP_90034_LOS_ANGELES);
    System.out.println("Selected ZIP: 90034 (Los Angeles)");
    locationsTab.chooseLocation(Location.ZIP_20001_WASHINGTON_DC);
    System.out.println("Selected ZIP: 20001 (Washington DC)");

    // Step 10: Open Comparison Report view
    EditComparisonReportPage editComparisonReportPage = ((EditComparisonReportPage) mapPage
            .getViewChooserSection().openViewMenu(ViewType.COMPARISON_REPORT.getDefaultName())
            .clickEdit());
    System.out.println("Opened Comparison Report in Edit mode.");

    // Step 11: Select all locations
    editComparisonReportPage.getActiveView().getLocationsPanel().clickSelectAll();
    System.out.println("Clicked Select All in locations panel.");

    // Step 12: Finish editing to view the report
    comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();
    System.out.println("Finished editing, report page loaded.");

    // Step 13: Transpose the report
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
            .clickViewActions()).clickTransposeReport();
    System.out.println("Transposed the Comparison Report.");

    System.out.println("Login flow completed.");
  }


  @Test
  public void testSortingByPriceValue() {
    String columnName = medianDefaultDataVariable.getFullName();

    HeaderDropdown headerDropdown = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    comparisonReportPage = (ComparisonReportPage) headerDropdown.clickSortSmallestToLargest();

    verificationStep("Verify the ascending order of the income values");
    List<List<String>> list = comparisonReportPage.getActiveView().getCellValues(columnName);
    int oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
      int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue,
          oldValue <= newValue);
      oldValue = newValue;
    }

    headerDropdown = comparisonReportPage.getActiveView().openColumnHeaderDropdown(columnName);
    comparisonReportPage = (ComparisonReportPage) headerDropdown.clickSortLargestToSmallest();

    verificationStep("Verify the descending order of the income values");
    list = comparisonReportPage.getActiveView().getCellValues(columnName);
    oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
      int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue >= newValue);
      oldValue = newValue;
    }
  }

  @Test
  public void testSortingByNumericValue() {
    String columnName = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024.getFullName();

    HeaderDropdown headerDropdown = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    comparisonReportPage = (ComparisonReportPage) headerDropdown.clickSortSmallestToLargest();

    verificationStep("Verify the ascending order of the population values");
    List<List<String>> list = comparisonReportPage.getActiveView().getCellValues(columnName);
    int oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
    	int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue,
          oldValue <= newValue);
      oldValue = newValue;
    }

    headerDropdown = comparisonReportPage.getActiveView().openColumnHeaderDropdown(columnName);
    comparisonReportPage = (ComparisonReportPage) headerDropdown.clickSortLargestToSmallest();

    verificationStep("Verify the descending order of the population values");
    list = comparisonReportPage.getActiveView().getCellValues(columnName);
    oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
    	int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue >= newValue);
      oldValue = newValue;
    }
  }

  @Test
  public void testSortingByPercentageValue() {
    String columnName = DataVariable.PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2024.getFullName();

    HeaderDropdown headerDropdown = comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    comparisonReportPage = (ComparisonReportPage) headerDropdown.clickSortSmallestToLargest();

    verificationStep("Verify the ascending order of the public transportation values");
    List<List<String>> list = comparisonReportPage.getActiveView().getCellValues(columnName);
    double oldValue = StringParsing.parsePercentageToDouble(list.get(0).get(0));
    for (List<String> row : list) {
      double newValue = StringParsing.parsePercentageToDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue,
          oldValue <= newValue);
      oldValue = newValue;
    }

    headerDropdown = comparisonReportPage.getActiveView().openColumnHeaderDropdown(columnName);
    comparisonReportPage = (ComparisonReportPage) headerDropdown.clickSortLargestToSmallest();

    verificationStep("Verify the descending order of the public transportation values");
    list = comparisonReportPage.getActiveView().getCellValues(columnName);
    oldValue = StringParsing.parsePercentageToDouble(list.get(0).get(0));
    for (List<String> row : list) {
      double newValue = StringParsing.parsePercentageToDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue >= newValue);
      oldValue = newValue;
    }
  }
}
