package net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparisonTableFavoritesTests extends TestBase {

  private ComparisonReportPage comparisonReportPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable houseIncome = medianDefaultDataVariable;

  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(losAngeles);
    comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection()
        .clickView(ViewType.COMPARISON_REPORT.getDefaultName());
  }

  @Test
  public void testAddColumnHeaderLocationToFavorites() {
    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickAddFavorites();
    LocationsTab locationsTab = comparisonReportPage.getLdbSection().clickLocations();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", locationsTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected location");
    Assert.assertTrue("The selected location should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(losAngeles.getName()));

    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickRemoveFavorites();

    locationsTab = (LocationsTab) comparisonReportPage.getLdbSection().getLocationsTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present",
        locationsTab.isFavoritesPresent());
  }

  @Test
  public void testAddRowHeaderDataVariableToFavorites() {
    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickAddFavorites();
    DataTab dataTab = comparisonReportPage.getLdbSection().clickData();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(houseIncome.getFullName()));

    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickRemoveFavorites();

    dataTab = comparisonReportPage.getLdbSection().getDataTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
  }

  @Test
  public void testAddColumnHeaderDataVariableToFavorites() {
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();

    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickAddFavorites();
    DataTab dataTab = comparisonReportPage.getLdbSection().clickData();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(houseIncome.getFullName()));

    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickRemoveFavorites();

    dataTab = comparisonReportPage.getLdbSection().getDataTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
  }

  @Test
  public void testAddRowHeaderLocationToFavorites() {
    comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar()
        .clickViewActions()).clickTransposeReport();

    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickAddFavorites();
    LocationsTab locationsTab = comparisonReportPage.getLdbSection().clickLocations();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", locationsTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected location");
    Assert.assertTrue("The selected location should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(losAngeles.getName()));

    comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickRemoveFavorites();

    locationsTab = (LocationsTab) comparisonReportPage.getLdbSection().getLocationsTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present",
        locationsTab.isFavoritesPresent());
  }
}
