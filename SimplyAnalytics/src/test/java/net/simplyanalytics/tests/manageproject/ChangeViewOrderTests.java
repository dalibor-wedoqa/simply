package net.simplyanalytics.tests.manageproject;

import java.util.Arrays;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageViewsPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.view.manageproject.ViewDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChangeViewOrderTests extends TestBase {

  private ProjectSettingsPage projectSettingsPage;

  /**
   * Signing in and creating new project, add ring study and related data view.
   */
  @Before
  public void createProjectWithOneOfEachView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    // add ring study and related data view
    newViewPage = newViewPage.getActiveView().clickCreate(ViewType.RING_STUDY).clickDone()
        .getViewChooserSection().clickNewView();
    newViewPage = newViewPage.getActiveView().clickCreate(ViewType.RELATED_DATA).clickDone()
        .getViewChooserSection().clickNewView();
    projectSettingsPage = newViewPage.getHeaderSection().clickProjectSettings();
  }
  
  @Test
  public void testArrows() {
    ManageViewsPanel manageViewsPanel = projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton();

    verificationStep("Verify that the default view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickDownArrow(ViewType.COMPARISON_REPORT.getDefaultName());

    verificationStep("Verify that the view order is correct "
        + "(\"Quick Report\", \"Comparison Report\", \"Map\", \"Ranking\", "
        + "\"Businesses\",  \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The view order is incorrect",
        Arrays.asList(ViewType.QUICK_REPORT.getDefaultName(),
            ViewType.COMPARISON_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickUpArrow(ViewType.COMPARISON_REPORT.getDefaultName());

    verificationStep("Verify that the view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickUpArrow(ViewType.COMPARISON_REPORT.getDefaultName());

    verificationStep("Verify that the view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickDownArrow(ViewType.RELATED_DATA.getDefaultName());

    verificationStep("Verify that the view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickUpArrow(ViewType.BUSINESSES.getDefaultName());

    verificationStep(
        "Verify that the view order is correct (\"Comparison Report\", \"Quick Report\", \"Map\", \"Businesses\", "
            + "\"Ranking\", \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.BUSINESSES.getDefaultName(), ViewType.RANKING.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickDownArrow(ViewType.RANKING.getDefaultName());

    verificationStep(
        "Verify that the view order is correct (\"Comparison Report\", \"Quick Report\", \"Map\", \"Businesses\","
            + " \"Ring Study\", \"Ranking\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.BUSINESSES.getDefaultName(), ViewType.RING_STUDY.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());
  }

  @Test
  public void testDragAndDrop() {
    ManageViewsPanel manageViewsPanel = projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton();

    verificationStep("Verify that the default view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.dragAndDropRow(ViewType.COMPARISON_REPORT.getDefaultName(), 1);

    verificationStep(
        "Verify that the view order is correct (\"Quick Report\", \"Comparison Report\", \"Map\", \"Ranking\", "
            + "\"Businesses\",  \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The view order is incorrect",
        Arrays.asList(ViewType.QUICK_REPORT.getDefaultName(),
            ViewType.COMPARISON_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.dragAndDropRow(ViewType.COMPARISON_REPORT.getDefaultName(), -1);

    verificationStep("Verify that the view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.dragAndDropRow(ViewType.COMPARISON_REPORT.getDefaultName(), -1);

    verificationStep("Verify that the view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.dragAndDropRow(ViewType.RELATED_DATA.getDefaultName(), 1);

    verificationStep("Verify that the view order is correct "
        + "(\"Comparison Report\", \"Quick Report\", \"Map\", \"Ranking\", \"Businesses\","
        + " \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.dragAndDropRow(ViewType.BUSINESSES.getDefaultName(), -1);

    verificationStep(
        "Verify that the view order is correct (\"Comparison Report\", \"Quick Report\", \"Map\", \"Businesses\", "
            + "\"Ranking\", \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.BUSINESSES.getDefaultName(), ViewType.RANKING.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.dragAndDropRow(ViewType.RANKING.getDefaultName(), 1);

    verificationStep(
        "Verify that the view order is correct (\"Comparison Report\", \"Quick Report\", \"Map\", \"Businesses\","
            + " \"Ring Study\", \"Ranking\", \"Related Data Report\")");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.BUSINESSES.getDefaultName(), ViewType.RING_STUDY.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

  }

  @Test
  public void testMoveToTop() {
    ManageViewsPanel manageViewsPanel = projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton();

    verificationStep(
        "Verify that the default view order is correct (\"Comparison Report\", \"Quick Report\", \"Map\", "
            + "\"Ranking\", \"Businesses\", \"Ring Study\", \"Related Data Report)");
    Assert.assertEquals("The default view order is incorrect",
        Arrays.asList(ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName(), ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    ViewDropdown viewDropdown = manageViewsPanel
        .clickViewMenu(ViewType.COMPARISON_REPORT.getDefaultName());

    verificationStep("Verify that the Move To Top button is disabled");
    Assert.assertTrue("The button should be disabled", viewDropdown.isMoveToTopDisabled());

    projectSettingsPage.getHeaderSection().clickProjectSettings();

    manageViewsPanel = projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton();
    manageViewsPanel.clickViewMenu(ViewType.MAP.getDefaultName()).clickMoveToTop();

    verificationStep(
        "Verify that the view order is correct (\"Map\", \"Comparison Report\", \"Quick Report\", \"Ranking\","
            + " \"Businesses\", \"Ring Study\", \"Related Data Report\")");
    Assert.assertEquals("The view order is incorrect",
        Arrays.asList(ViewType.MAP.getDefaultName(), ViewType.COMPARISON_REPORT.getDefaultName(),
            ViewType.QUICK_REPORT.getDefaultName(), ViewType.RANKING.getDefaultName(),
            ViewType.BUSINESSES.getDefaultName(), ViewType.RING_STUDY.getDefaultName(),
            ViewType.RELATED_DATA.getDefaultName()),
        manageViewsPanel.getAllViews());

    manageViewsPanel.clickViewMenu(ViewType.RELATED_DATA.getDefaultName())
        .clickMoveToTop();

    verificationStep("Verify that the view order is correct (\"Related Data Report\", \"Map\", "
        + "\"Comparison Report\", \"Quick Report\", \"Ranking\", \"Businesses\", \"Ring Study\")");
    Assert.assertEquals("The view order is incorrect",
        Arrays.asList(ViewType.RELATED_DATA.getDefaultName(), ViewType.MAP.getDefaultName(),
            ViewType.COMPARISON_REPORT.getDefaultName(), ViewType.QUICK_REPORT.getDefaultName(),
            ViewType.RANKING.getDefaultName(), ViewType.BUSINESSES.getDefaultName(),
            ViewType.RING_STUDY.getDefaultName()),
        manageViewsPanel.getAllViews());
  }

}
