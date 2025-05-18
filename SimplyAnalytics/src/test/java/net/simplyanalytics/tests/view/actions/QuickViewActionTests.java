package net.simplyanalytics.tests.view.actions;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.sections.toolbar.quickreport.QuickViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.QuickReportViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuickViewActionTests extends TestBase {

  private QuickReportPage quickReportPage;
  private MapPage mapPage;

  /**
   * Signing in, creating new project, and open the quick report page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.QUICK_REPORT).clickDone();
  }

  @Test
  public void testAddRemoveUSAFromReport() {
    QuickReportViewPanel quickReportViewPanel = quickReportPage.getActiveView();

    verificationStep("Verify that USA is present as a column header in the table");
    Assert.assertTrue("USA didn't appear in table's column headers",
        quickReportViewPanel.getNormalHeaderValues().contains(Location.USA.getName()));

    QuickViewActionMenu viewActionsMenu = (QuickViewActionMenu) quickReportPage.getToolbar()
        .clickViewActions();

    viewActionsMenu.clickAddRemoveUsa();

    verificationStep("Verify that USA is no longer present as a column header in the table");
    Assert.assertFalse("USA did appear in table's column headers",
        quickReportViewPanel.getNormalHeaderValues().contains(Location.USA.getName()));
  }

  @Test
  public void testAddVariabletoReport() {
    quickReportPage.getActiveView().addVariableToProject(DataVariable.TOTAL_POPULATION_2018);
    mapPage = (MapPage) quickReportPage.getViewChooserSection().clickView("Map");

    verificationStep("Verify that the choosen data variable is present in the project");
    Assert.assertTrue("The selected variable is not present in the project", mapPage.getToolbar()
        .openDataVariableListMenu().isVariablePresent(DataVariable.TOTAL_POPULATION_2018));
  }
}
