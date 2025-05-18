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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScatterPlotViewActionsTests extends TestBase {

	private ViewType viewType = ViewType.SCATTER_PLOT;
	private ScatterPlotPage scatterPlotPage;

	@Before
	public void createProjectWithMapView() {
		AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
		SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
		WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
		NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
		MapPage mapPage = createNewProjectWindow
				.createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

		EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) mapPage.getViewChooserSection().clickNewView()
				.getActiveView().clickCreate(viewType);
		scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
	}

	@Test
	public void testCreateDataTable() {

		ViewActionsMenu viewActionMenu = (ViewActionsMenu) scatterPlotPage.getToolbar().clickViewActions();
		RankingPage rankingpage = (RankingPage) viewActionMenu.clickCreateDataTable();

		verificationStep("Verify that the new Ranking table view is created");
		Assert.assertEquals("The actual view is the new created view", "Ranking 2",
				rankingpage.getViewChooserSection().getActiveViewName());

	}
}
