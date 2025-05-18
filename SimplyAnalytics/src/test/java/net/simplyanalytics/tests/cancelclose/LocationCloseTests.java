package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.CreateOrEditCombinationLocation;
import net.simplyanalytics.pageobjects.sections.ldb.locations.CreateRadiusLocation;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationCloseTests extends TestBase {

  private MapPage mapPage;

  /**
   * Sign-in and creating a new project.
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
  }

  @Test
  public void closeViewYourCustomLocations() {
    RecentFavoriteMenu recentFavoriteMenu = mapPage.getLdbSection().clickLocations()
        .clickShowCustomLocations().clickViewCustomLocation();
    recentFavoriteMenu.clickCloseButton();

    verificationStep("Verify that the custom locations sub menu is disappeared");
    Assert.assertFalse("The custom locations sub menu should be disappeared",
        recentFavoriteMenu.isDisplayed());
  }

  @Test
  public void closeCreateRadiusLocation() {
    CreateRadiusLocation createRadiusLocation = mapPage.getLdbSection().clickLocations()
        .clickShowCustomLocations().clickCreateRadiusLocation();

    createRadiusLocation.clickClose();

    verificationStep("Verify that the Create Radius Location dialog is disappeared");
    Assert.assertFalse("The Create Radius Location dialog should be disappeared",
        createRadiusLocation.isDisplayed());
  }

  @Test
  public void cancelCreateRadiusLocation() {
    CreateRadiusLocation createRadiusLocation = mapPage.getLdbSection().clickLocations()
        .clickShowCustomLocations().clickCreateRadiusLocation();

    createRadiusLocation.clickCancel();

    verificationStep("Verify that the Create Radius Location dialog is disappeared");
    Assert.assertFalse("The Create Radius Location dialog should be disappeared",
        createRadiusLocation.isDisplayed());
  }

  @Test
  public void closeCreateCombinationLocation() {
    CreateOrEditCombinationLocation createOrEditCombinationLocation = mapPage.getLdbSection()
        .clickLocations().clickShowCustomLocations().clickCreateCombinationLocation();
    
    createOrEditCombinationLocation.clickClose();
    
    verificationStep("Verify that the Create Combination Location dialog is disappeared");
    Assert.assertFalse("The Create Combination Location dialog should be disappeared",
        createOrEditCombinationLocation.isDisplayed());
  }
  
  @Test
  public void cancelCreateCombinationLocation() {
    CreateOrEditCombinationLocation createOrEditCombinationLocation = mapPage.getLdbSection()
        .clickLocations().clickShowCustomLocations().clickCreateCombinationLocation();
    
    createOrEditCombinationLocation.clickCancel();
    
    verificationStep("Verify that the Create Combination Location dialog is disappeared");
    Assert.assertFalse("The Create Combination Location dialog should be disappeared",
        createOrEditCombinationLocation.isDisplayed());
  }

}
