package net.simplyanalytics.tests.ldb.business;

import java.util.List;

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
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItem;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessesToolbar;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecentBusinessTests extends TestBase {

  private MapPage mapPage;
  private BusinessesTab businessesTab;

  /**
   * Signing in, creating new project and open the business tab.
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
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    businessesTab = mapPage.getLdbSection().clickBusinesses();
  }

  @Test
  public void testRecentMenuPresence() {
    verificationStep("Verify that the recent business button is not present");
    Assert.assertFalse("The recent businesses button should not be present",
        businessesTab.isRecentPresent());
    mapPage = selectAdvancedBusinessSearch(mapPage, BusinessSearchBy.NAICS,
        BusinessSearchConditions.STARTS);

    verificationStep(
        "Verify that the recent button (Clock icon on the right side of the Business Search field) "
            + "is present");
    Assert.assertTrue("The recent businesses button should be present",
        businessesTab.isRecentPresent());
  }

  @Test
  public void testRecentBusinessSelection() {
    mapPage = selectAdvancedBusinessSearch(mapPage, BusinessSearchBy.NAICS,
        BusinessSearchConditions.STARTS);
    mapPage = selectAdvancedBusinessSearch(mapPage, BusinessSearchBy.NAICS,
        BusinessSearchConditions.STARTS);
    mapPage = selectAdvancedBusinessSearch(mapPage, BusinessSearchBy.NAICS,
        BusinessSearchConditions.STARTS);

    verificationStep("Verify that the recent business button is present");
    Assert.assertTrue("The recent businesses button should be present",
        businessesTab.isRecentPresent());

    BusinessesPage businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());
    BusinessesToolbar businessToolbar = businessesPage.getToolbar();

    verificationStep(
        "Verify that the selected business searches are on the recent businesses list");
    List<RecentFavoriteMenuItem> recentSearches = businessesTab.clickRecent().getMenuItems();
    for (RecentFavoriteMenuItem recentSearch : recentSearches) {
      recentSearch.clickToSelect();
      verificationStep("Verify that the selected business is the active business on the map view");
      Assert.assertEquals(
          "The business names in recent searches should"
          + " be match with businesses in businesses page",
          businessToolbar.getActiveBusiness(), recentSearch.getTitle());
    }
  }

  private MapPage selectAdvancedBusinessSearch(MapPage mapPage, BusinessSearchBy searchBy,
      BusinessSearchConditions searchCondition) {
    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy);
    searchConditionRow.selectCondition(searchCondition);
    searchConditionRow.selectRandomBusinessCode();
    advancedBusinessSearchWindow.clickSearch();
    return new MapPage(driver);
  }
}
