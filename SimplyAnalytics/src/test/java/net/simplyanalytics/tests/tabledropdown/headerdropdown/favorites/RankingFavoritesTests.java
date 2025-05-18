package net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RankingFavoritesTests extends TestBase {

  private RankingPage rankingPage;
  private Location losAngeles = Location.ZIP_90034_LOS_ANGELES;
  private DataVariable houseIncome = medianDefaultDataVariable;

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
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    rankingPage = (RankingPage) mapPage.getViewChooserSection()
        .clickView(ViewType.RANKING.getDefaultName());
  }

  @Test
  public void testAddColumnHeaderDataVariableToFavorites() {
    rankingPage = (RankingPage) rankingPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickAddFavorites();
    DataTab dataTab = rankingPage.getLdbSection().clickData();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(houseIncome.getFullName()));

    rankingPage = (RankingPage) rankingPage.getActiveView()
        .openColumnHeaderDropdown(houseIncome.getFullName()).clickRemoveFavorites();

    dataTab = rankingPage.getLdbSection().getDataTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
  }

  @Test
  public void testAddRowHeaderLocationToFavorites() {
    rankingPage = (RankingPage) rankingPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickAddFavorites();
    LocationsTab locationsTab = rankingPage.getLdbSection().clickLocations();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", locationsTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected location");
    Assert.assertTrue("The selected location should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(losAngeles.getName()));

    rankingPage = (RankingPage) rankingPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickRemoveFavorites();

    locationsTab = (LocationsTab) rankingPage.getLdbSection().getLocationsTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present",
        locationsTab.isFavoritesPresent());
  }

}
