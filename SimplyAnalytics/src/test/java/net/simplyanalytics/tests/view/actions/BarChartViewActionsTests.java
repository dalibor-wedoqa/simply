package net.simplyanalytics.tests.view.actions;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.barchart.BarChartToolbar;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.sections.view.ComparisonReportViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class BarChartViewActionsTests extends TestBase {

	private BarChartToolbar barChartToolbar;
	private BarChartViewPanel barChartViewPanel;
	private BarChartPage barChartPage;

	/**
	 * Signing in, creating new project and open the comparison report page.
	 */
	@Before
	public void login() {
		AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
		SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
		WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
		NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
		MapPage mapPage = createNewProjectWindow
				.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
		barChartPage = (BarChartPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
				.clickCreate(ViewType.BAR_CHART).clickDone();
		barChartToolbar = barChartPage.getToolbar();
		barChartViewPanel = barChartPage.getActiveView();
	}

	@Test
	public void testCreateDataTable() {
		
		ViewActionsMenu viewActionMenu = (ViewActionsMenu) barChartPage.getToolbar().clickViewActions();
		ComparisonReportPage coparisonPage = (ComparisonReportPage) viewActionMenu.clickCreateDataTable();
		ComparisonReportViewPanel comparisonReportViewPanel = coparisonPage.getActiveView();

		verificationStep("Verify that the new Comparison table view is created");
		Assert.assertEquals("The actual view is the new created view", "Comparison Table 2",
				coparisonPage.getViewChooserSection().getActiveViewName());

	}
}
