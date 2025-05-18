package net.simplyanalytics.tests.tabledropdown.headerdropdown.sort;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.view.base.HeaderDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.StringParsing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RankingSortingTests extends TestBase {

  private RankingPage rankingPage;

  /**
   * Signing in, creating new project and open the ranking page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    NewProjectVariablesWindow newProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    // deselect variable
    newProjectVariablesWindow
        .clickDataVariable(defaultDataVariables.get(0).getName());
    // select numeric value
    newProjectVariablesWindow.clickDataVariable(DataVariable.PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2024.getName());

    MapPage mapPage = newProjectVariablesWindow.clickCreateProjectButton();

    rankingPage = (RankingPage) mapPage.getViewChooserSection()
        .clickView(ViewType.RANKING.getDefaultName());
  }

  @Test
  public void testSortingByString() {
    String columnName = "Location";

    verificationStep("Verify the A-Z order of the location name");
    List<List<String>> list = rankingPage.getActiveView().getCellValues(columnName);
    String oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The " + newValue + " should be before the previous value " + oldValue,
          oldValue.compareTo(newValue) <= 0);
      oldValue = newValue;
    }

    HeaderDropdown headerDropdown = rankingPage.getActiveView().openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the A-Z order of the income values");
    list = rankingPage.getActiveView().getCellValues(columnName);
    oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The " + newValue + " should be before the previous value " + oldValue,
          oldValue.compareTo(newValue) >= 0);
      oldValue = newValue;
    }
    
    columnName = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024.getFullName();
    headerDropdown = rankingPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickSortSmallestToLargest();
    
    rankingPage.getToolbar().clickDataVariable().clickOnLocationName();
    columnName = "Location";
    
    verificationStep("Verify the A-Z order of the income values");
    list = rankingPage.getActiveView().getCellValues(columnName);
    oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The " + newValue + " should be before the previous value " + oldValue,
          oldValue.compareTo(newValue) <= 0);
      oldValue = newValue;
    }
    
  }

  @Test
  public void testSortingByPriceValue() {
    String columnName = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024.getFullName();

    HeaderDropdown headerDropdown = rankingPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickSortSmallestToLargest();

    verificationStep("Verify the ascending order of the income values");
    List<List<String>> list = rankingPage.getActiveView().getCellValues(columnName);
    int oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
      int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue,
          oldValue <= newValue);
      oldValue = newValue;
    }

    headerDropdown = rankingPage.getActiveView().openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the income values");
    list = rankingPage.getActiveView().getCellValues(columnName);
    logger.trace(list.toString());
    oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    logger.trace("" + oldValue);
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
    rankingPage.getToolbar().clickDataVariable().clickSortByDatavariable(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024);
    // default order
    verificationStep("Verify the ascending order of the population values is the default order");
    List<List<String>> list = rankingPage.getActiveView().getCellValues(columnName);
    int oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
      int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue,
          oldValue >= newValue);
      oldValue = newValue;
    }

    HeaderDropdown headerDropdown = rankingPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the population values");
    list = rankingPage.getActiveView().getCellValues(columnName);
    oldValue = StringParsing.parsePriceToInt(list.get(0).get(0));
    for (List<String> row : list) {
      int newValue = StringParsing.parsePriceToInt(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue <= newValue);
      oldValue = newValue;
    }
  }

  @Test
  public void testSortingByPercentageValue() {
    String columnName = DataVariable.PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2024
        .getFullName();

    HeaderDropdown headerDropdown = rankingPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickSortSmallestToLargest();

    verificationStep("Verify the ascending order of the public transportation values");
    List<List<String>> list = rankingPage.getActiveView().getCellValues(columnName);
    double oldValue = StringParsing.parsePercentageToDouble(list.get(0).get(0));
    for (List<String> row : list) {
      double newValue = StringParsing.parsePercentageToDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue,
          oldValue <= newValue);
      oldValue = newValue;
    }

    headerDropdown = rankingPage.getActiveView().openColumnHeaderDropdown(columnName);
    rankingPage = (RankingPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the public transportation values");
    list = rankingPage.getActiveView().getCellValues(columnName);
    oldValue = StringParsing.parsePercentageToDouble(list.get(0).get(0));
    for (List<String> row : list) {
      double newValue = StringParsing.parsePercentageToDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue >= newValue);
      oldValue = newValue;
    }
  }
}
