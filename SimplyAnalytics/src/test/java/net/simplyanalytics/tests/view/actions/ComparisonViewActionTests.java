package net.simplyanalytics.tests.view.actions;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonReportToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.ComparisonReportViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ComparisonViewActionTests extends TestBase {

  private ComparisonReportPage comparisonReportPage;
  private ComparisonReportToolbar comparisonReportToolbar;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable dataVariable = defaultDataVariables.get(1);

  /**
   * Signing in, creating new project and open the comarison report page.
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
    comparisonReportToolbar = comparisonReportPage.getToolbar();
  }

  @Test
  public void testAddRemoveUSAFromReport() {
    ComparisonReportViewPanel comparisonReportViewPanel = comparisonReportPage.getActiveView();

    verificationStep("Verify that USA is present as a column header in the table");
    Assert.assertTrue("USA didn't appear in table's column headers",
        comparisonReportViewPanel.getNormalHeaderValues().contains(Location.USA.getName()));

    ComparisonViewActionMenu viewActionsMenu = (ComparisonViewActionMenu) comparisonReportToolbar
        .clickViewActions();

    viewActionsMenu.clickAddRemoveUsa();

    verificationStep("Verify that USA is no longer present as a column header in the table");
    Assert.assertFalse("USA did appear in table's column headers",
        comparisonReportViewPanel.getNormalHeaderValues().contains(Location.USA.getName()));

    viewActionsMenu = (ComparisonViewActionMenu) comparisonReportToolbar.clickViewActions();
    comparisonReportPage = viewActionsMenu.clickTransposeReport();
    
    comparisonReportViewPanel = comparisonReportPage.getActiveView();
    
    viewActionsMenu = (ComparisonViewActionMenu) comparisonReportToolbar.clickViewActions();
    viewActionsMenu.clickAddRemoveUsa();

    verificationStep("Verify that USA is present as a row header in the table");
    Assert.assertTrue("USA didn't appear in table's row headers",
        comparisonReportViewPanel.getRowHeaderValues().contains(Location.USA.getName()));

    viewActionsMenu = (ComparisonViewActionMenu) comparisonReportToolbar.clickViewActions();
    viewActionsMenu.clickAddRemoveUsa();

    verificationStep("Verify that USA is no longer present as a row header in the table");
    Assert.assertFalse("USA did appear in table headers",
        comparisonReportViewPanel.getRowHeaderValues().contains(Location.USA.getName()));
  }

  @Test
  public void testTransposeReport() {
    ComparisonReportViewPanel comparisonReportViewPanel = comparisonReportPage.getActiveView();

    List<String> prevColumnHeaders = comparisonReportViewPanel.getNormalHeaderValues();
    List<String> prevRowHeaders = comparisonReportViewPanel.getRowHeaderValues();

    ComparisonViewActionMenu viewActionsMenu = (ComparisonViewActionMenu) comparisonReportToolbar
        .clickViewActions();
    comparisonReportPage = viewActionsMenu.clickTransposeReport();
    comparisonReportViewPanel = comparisonReportPage.getActiveView();

    verificationStep("Verify that the Data variables are now present as column headers");
    Assert.assertArrayEquals("All data variables should be present in column headers section",
        prevColumnHeaders.toArray(), comparisonReportViewPanel.getRowHeaderValues().toArray());

    verificationStep("Verify that the Locations are now present as row headers");
    Assert.assertArrayEquals("All locations should be present in row headers section",
        prevRowHeaders.toArray(), comparisonReportViewPanel.getNormalHeaderValues().toArray());
    
    comparisonReportViewPanel = comparisonReportPage.getActiveView();
    viewActionsMenu = (ComparisonViewActionMenu) comparisonReportToolbar.clickViewActions();
    comparisonReportPage = viewActionsMenu.clickTransposeReport();
    comparisonReportViewPanel = comparisonReportPage.getActiveView();
    
    verificationStep("Verify that the Data variables are now present as row headers");
    Assert.assertArrayEquals("All data variables should be present in row headers section",
    		prevRowHeaders.toArray(), comparisonReportViewPanel.getRowHeaderValues().toArray());

    verificationStep("Verify that the Locations are now present as column headers");
    Assert.assertArrayEquals("All locations should be present in column headers section",
    		prevColumnHeaders.toArray(), comparisonReportViewPanel.getNormalHeaderValues().toArray());
  }
  
  @Test
  public void testCreateMap() {
    MapPage mapPage = comparisonReportPage.getActiveView()
        .openCellDropdown(dataVariable.getFullName(), losAngeles.getName()).clickCreateMapButton();

    verificationStep("Verify that the new map view is created");
    Assert.assertEquals("The actual actual view is the new created view", "Map 2",
        mapPage.getViewChooserSection().getActiveViewName());
  }
  
  @Test
  public void testCreateRanking() {
    RankingPage rankingPage = comparisonReportPage.getActiveView()
        .openCellDropdown(dataVariable.getFullName(), losAngeles.getName()).clickCreateRankingButton();

    verificationStep("Verify that the new map view is created");
    Assert.assertEquals("The actual actual view is the new created view", "Ranking 2",
        rankingPage.getViewChooserSection().getActiveViewName());
  }

}
