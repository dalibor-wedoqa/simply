package net.simplyanalytics.tests.view.actions;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class RankingToolbarTests extends TestBase {

  private RankingPage rankingPage;
  private DataVariable dataVariable = firstDefaultDataVariable;

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
  public void testRankingToolBar() {
    String[] columns = { "Location", dataVariable.getFullName() };
    rankingPage.getHeaderSection().clickOnBlankSpace();

    String numberofrowsbefore = rankingPage.getToolbar().getActiveListSize();
    rankingPage.getToolbar().clickTopNumberOfDataInTableCombo(10);
    String numberofrowsafter = rankingPage.getToolbar().getActiveListSize();

    List<List<String>> rows = rankingPage.getActiveView().getCellValues(columns);
    rankingPage.getToolbar().changeLocation(Location.USA);
    List<List<String>> rows2 = rankingPage.getActiveView().getCellValues(columns);

    rankingPage.getToolbar().clickFilterByLocationTypeListMenu()
        .clickFilterBy(LocationType.CENSUS_TRACT);
    List<List<String>> rows3 = rankingPage.getActiveView().getCellValues(columns);
    rankingPage.getToolbar().clickDataVariable().clickSortByDatavariable(dataVariable);
    List<List<String>> rows4 = rankingPage.getActiveView().getCellValues(columns);

    Assert.assertNotEquals("Number of rows didn't change", numberofrowsbefore, numberofrowsafter);
    Assert.assertNotEquals("Location changed", rows2, rows);
    Assert.assertNotEquals("Location type changed", rows2, rows3);
    Assert.assertNotEquals("Data changed", rows3, rows4);
  }

}
