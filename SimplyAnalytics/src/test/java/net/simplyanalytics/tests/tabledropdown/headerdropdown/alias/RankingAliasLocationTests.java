package net.simplyanalytics.tests.tabledropdown.headerdropdown.alias;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RankingAliasLocationTests extends TestBase {

  private RankingPage rankingPage;
  private Location losAngeles = Location.ZIP_90034_LOS_ANGELES;

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
  public void testRowHeaderAliasLocation() {
    String aliasName = "alias name";

    AliasLocationWindow aliasLocationWindow = rankingPage.getActiveView()
        .openRowHeaderDropdown(losAngeles.getName()).clickAddAliasLocationName();

    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();

    rankingPage = new RankingPage(driver);

    String newName = rankingPage.getActiveView().getLockedRow(losAngeles.getName()).get("Location");

    verificationStep(
        "Verify that the new location name contains the alias name and the original name");
    String expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);

    aliasLocationWindow = rankingPage.getActiveView().openRowHeaderDropdown(losAngeles.getName())
        .clickEditAliasLocationName();

    aliasName = "alias name edited";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();

    rankingPage = new RankingPage(driver);

    newName = rankingPage.getActiveView().getLockedRow(losAngeles.getName()).get("Location");

    verificationStep(
        "Verify that the new location name contains the edited alias name and the original name");
    expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);

    aliasLocationWindow = rankingPage.getActiveView().openRowHeaderDropdown(losAngeles.getName())
        .clickEditAliasLocationName();

    aliasName = "";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();

    rankingPage = new RankingPage(driver);

    newName = rankingPage.getActiveView().getLockedRow(losAngeles.getName()).get("Location");

    verificationStep("Verify that the location name is the original name");
    Assert.assertEquals("The location name is not the expected", losAngeles.getName(), newName);
  }
}
