package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MetadataWindowCloseTest extends TestBase {

  private MetadataWindow metadataWindow;

  /**
   * Signing in, creating new project and open the metadata window.
   */ 
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    NewProjectVariablesWindow createNewProjectVariablesWindow = createNewProjectWindow
        .createNewProjectWithLocation(Location.LOS_ANGELES_CA_CITY);
    
    MapPage mapPage = createNewProjectVariablesWindow.clickCreateProjectButton();
    mapPage.getActiveView().waitForFullLoad();
    mapPage.getLdbSection().clickLocations();
    DataTab dataTab = mapPage.getLdbSection().clickData();
    RecentFavoriteMenu recentDataMenu = dataTab.clickRecent();
    RecentFavoriteMenuItemWithSubMenu recentData = 
        (RecentFavoriteMenuItemWithSubMenu) recentDataMenu
        .getNthMenuItem(1);

    metadataWindow = recentData.clickOnMoreOptions().clickViewMetadata();
  }

  @Test
  public void closeMetadataWindow() {

    metadataWindow.clickClose();

    verificationStep("Verify that the Metadata Window is disappeared");
    Assert.assertFalse("The Metadata Window should be disappeared", metadataWindow.isDisplayed());
  }
  
  @Test
  public void closeAndMoveMetadataWindow() {

    metadataWindow = metadataWindow.moveWindow(100, 100); 
    metadataWindow = metadataWindow.moveWindow(-100, -100);
    metadataWindow.clickClose();

    verificationStep("Verify that the Metadata Window is disappeared");
    Assert.assertFalse("The Metadata Window should be disappeared", metadataWindow.isDisplayed());
  }

}
