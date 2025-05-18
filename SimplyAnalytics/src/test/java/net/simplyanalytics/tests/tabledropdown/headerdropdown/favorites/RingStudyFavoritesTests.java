package net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RingStudyFavoritesTests extends TestBase {
  
  private RingStudyPage ringStudyPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable houseIncome = defaultDataVariables.get(0);
  
  /**
   * Signing in, creating new project and open the ring study page.
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
    
    ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
        .clickCreate(ViewType.RING_STUDY).clickDone();
    
  }
  
  @Test
  public void testAddRowHeaderDataVariableToFavorites() {
    ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickAddFavorites();
    DataTab dataTab = ringStudyPage.getLdbSection().clickData();
    
    verificationStep("Verify that the favorites menu present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());
    
    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();
    
    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(houseIncome.getFullName()));
    
    ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView()
        .openRowHeaderDropdown(houseIncome.getFullName()).clickRemoveFavorites();
    
    dataTab = ringStudyPage.getLdbSection().getDataTab();
    
    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
  }
}
