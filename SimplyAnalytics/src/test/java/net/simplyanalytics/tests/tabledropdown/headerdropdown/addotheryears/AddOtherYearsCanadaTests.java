package net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears;

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
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddOtherYearsCanadaTests extends TestBase {

    private MapPage mapPage;
    private DataVariable hashtagBasicPopulation2024 = DataVariable.HASHTAG_BASIC_POPULATION_2024;
    private List<String> dataVariablesList = new ArrayList<>();

    @Before
    public void login() {

        AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
        SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
        WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
        NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
        createNewProjectWindow.selectCountry("Canada");
        mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);

    }

    @Test
    public void testRankingViewColumnHeaderAddOtherYears() {
        RankingPage rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
        dataVariablesList.add(hashtagBasicPopulation2024.getFullName());
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(hashtagBasicPopulation2024.getFullName()).
                addOtherYearCanada();
        rankingPage.getActiveView().closeColumnHeaderDropdown(hashtagBasicPopulation2024.getFullName());
        verificationStep("Verify that the randomized other year data variable is added.");
        List<String> columnHeaderValues = rankingPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", columnHeaderValues.contains(DataVariable.
                HASHTAG_BASIC_POPULATION_2028.getFullName()));
    }

    @Test
    public void testRingStudyViewColumnHeaderAddOtherYears() {
        // Step 1: Create a new Ring Study view from the view chooser section and click 'Done'
        System.out.println("Creating a new Ring Study view and clicking 'Done'.");
        RingStudyPage ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RING_STUDY).clickDone();

        // Step 2: Add the full name of 'hashtagBasicPopulation2024' to the data variables list
        System.out.println("Adding 'hashtagBasicPopulation2024' to the data variables list.");
        dataVariablesList.add(hashtagBasicPopulation2024.getFullName());

        // Step 3: Open the row header dropdown for 'hashtagBasicPopulation2024' and add other year data for Canada
        System.out.println("Opening row header dropdown for 'hashtagBasicPopulation2024' and adding other year data for Canada.");
        ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView().openRowHeaderDropdown(hashtagBasicPopulation2024.getFullName())
                .addOtherYearCanada();

        // Step 4: Reopen the row header dropdown for 'hashtagBasicPopulation2024' without performing any additional actions
        System.out.println("Reopening row header dropdown for 'hashtagBasicPopulation2024' without performing any action.");
        ringStudyPage.getActiveView().openRowHeaderDropdownVoid(hashtagBasicPopulation2024.getFullName());

        // Step 5: Verification step to ensure the randomized data variable for the other year is added
        System.out.println("Verification step: Checking if the randomized data variable for the other year is added.");
        verificationStep("Verify that the randomized other year data variable is added.");

        // Step 6: Retrieve the row header values to confirm that the expected data variable is present
        System.out.println("Retrieving row header values.");
        List<String> rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();

        // Step 7: Assertion to ensure the 'hashtagBasicPopulation2028' data variable is present in the row header
        System.out.println("Verifying that the 'hashtagBasicPopulation2028' data variable is added.");
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(DataVariable.
                HASHTAG_BASIC_POPULATION_2028.getFullName()));
    }


    @Test
    public void testComparisonReportViewHeaderWithTransposeAddOtherYears() {
        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickView(ViewType.COMPARISON_REPORT.getDefaultName());
        dataVariablesList.add(hashtagBasicPopulation2024.getFullName());
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openRowHeaderDropdown(hashtagBasicPopulation2024.
                getFullName()).addOtherYearCanada();
        comparisonReportPage.getActiveView().openRowHeaderDropdownVoid(hashtagBasicPopulation2024.getFullName());
        verificationStep("Verify that the randomized other year data variable is added.");
        List<String> rowHeaderValues = comparisonReportPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(DataVariable.
                HASHTAG_BASIC_POPULATION_2024.getFullName()));

        comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
                .clickTransposeReport();
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openColumnHeaderDropdown(hashtagBasicPopulation2024.
                getFullName()).addOtherYearCanada();
        comparisonReportPage.getActiveView().closeColumnHeaderDropdown(hashtagBasicPopulation2024.getFullName());
        verificationStep("Verify that the other year data variable is removed.");
        List<String> columnHeaderValues = comparisonReportPage.getActiveView().getNormalHeaderValues();
        Assert.assertFalse("The data variable is not removed.", columnHeaderValues.contains(DataVariable.
                HASHTAG_BASIC_POPULATION_2028.getFullName()));
    }
}
