package net.simplyanalytics.tests.view.export;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LimitMessageExportTests extends TestBase {

  private static final ViewType viewType = ViewType.BUSINESSES;

  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;
  private ExportWindow exportWindow;
  private BusinessesPage businessesPage;

  /**
   * Signing in.
   */
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  @Test
  public void testLimitMessage() {

    mapPage = createNewProjectWindow.createNewProjectWithStateAndDefaultVariables(Location.USA);

    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    businessesTab.enterBusinessSearch("lemon");

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExport();

    String messageWithLimit = exportWindow.limitMessage();
    int numberOfResults = businessesPage.getToolbar().getNumberOfResults();
    int limitday = (int) Math.round(numberOfResults/(double)2500*100);
    int limitweek = (int) Math.round(numberOfResults/(double)5000*100);
    int limityear = (int) Math.round(numberOfResults/(double)20000*100);
    
    String messageOnSite = "SimplyAnalytics users can export a maximum of 2,500 "
        + "businesses per export, 5,000 businesses per week, and 20,000 businesses per year."
        + "\n"
        + "With this report, you would be exporting " + numberOfResults + " businesses now "
        + "(" + limitday + "% of limit), and would have exported " + numberOfResults + " businesses this week (" + limitweek + "% of limit) "
        + "and " + numberOfResults + " businesses this year (" + limityear + "% of limit).";
    
    messageWithLimit = messageWithLimit.replaceAll("(\\d)(,)(\\d)", "$1$3");
    messageOnSite = messageOnSite.replaceAll("(\\d)(,)(\\d)", "$1$3");
    
    Assert.assertEquals( 
        "Message is not as displayed, should be " + messageOnSite + "but was: " + messageWithLimit,
        messageWithLimit, messageOnSite);
  }

}
