package net.simplyanalytics.tests.view.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.HistogramPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class HistogramViewActionsTests extends TestBase {

	private HistogramPage histogramPage;
	private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
	private DataVariable dataVariable = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020;

	@Before
	public void login() {
		AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
		SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
		WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
		NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
		MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(losAngeles);
		histogramPage = (HistogramPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
				.clickCreate(ViewType.HISTOGRAM).clickDone();
	}

	@Test
	public void testCreateDataTable() {

		ViewActionsMenu viewActionMenu = (ViewActionsMenu) histogramPage.getToolbar().clickViewActions();
		RankingPage rankingpage = (RankingPage) viewActionMenu.clickCreateDataTable();

		verificationStep("Verify that the new Ranking table view is created");
		Assert.assertEquals("The actual view is the new created view", "Ranking 2",
				rankingpage.getViewChooserSection().getActiveViewName());
		
		/*verificationStep("Verify that the correct data variable is present in Ranking table");
		Assert.assertNotEquals("Wrong data is disaplyed in the Ranking table", //***, /****);*/
	}
}
