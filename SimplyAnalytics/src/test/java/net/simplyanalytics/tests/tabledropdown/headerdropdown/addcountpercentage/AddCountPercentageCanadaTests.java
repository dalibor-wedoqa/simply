package net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage;

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

public class AddCountPercentageCanadaTests extends TestBase {

    private MapPage mapPage;
    private DataVariable housholdPopulationPercent = DataVariable.PERCENT_HOUSEHOLD_POPULATION_25_TO_64_2024;
    private DataVariable housholdPopulationCount = DataVariable.HASHTAG_HOUSEHOLD_POPULATION_25_TO_64_2024;
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
    public void testRankingViewColumnHeaderAddCount() {

        RankingPage rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
        dataVariablesList.add(housholdPopulationPercent.getFullName());
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(housholdPopulationPercent.getFullName()).clickAddCount();
        verificationStep("Verify that the data variable " + housholdPopulationCount + " data is added.");
        List<String> columnHeaderValues = rankingPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", columnHeaderValues.contains(housholdPopulationCount.getFullName()));
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(housholdPopulationPercent.getFullName()).clickHideDataVariable();
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(housholdPopulationCount.getFullName()).clickAddPercent();
        verificationStep("Verify that the data variable " + housholdPopulationPercent + " data is added.");
        columnHeaderValues = rankingPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", columnHeaderValues.contains(housholdPopulationPercent.getFullName()));

    }

    @Test
    public void testRingStudyViewRowHeaderAddCount() {

        RingStudyPage ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RING_STUDY).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(housholdPopulationPercent.getFullName());
        ringStudyPage.getActiveView().openRowHeaderDropdown(housholdPopulationPercent.getFullName()).clickAddCount();
        verificationStep("Verify that the data variable " + housholdPopulationCount + " data is added.");
        List<String> rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(housholdPopulationCount.getFullName()));
        ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView().openRowHeaderDropdown(housholdPopulationPercent.getFullName()).clickHideDataVariable();
        ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView().openRowHeaderDropdown(housholdPopulationCount.getFullName()).clickAddPercent();
        verificationStep("Verify that the data variable " + housholdPopulationPercent + " data is added.");
        rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(housholdPopulationPercent.getFullName()));

    }

    @Test
    public void testComparisonReportViewHeaderWithTransposeAddCount() {

        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.COMPARISON_REPORT).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(housholdPopulationPercent.getFullName());
        comparisonReportPage.getActiveView().openRowHeaderDropdown(housholdPopulationPercent.getFullName()).clickAddCount();
        verificationStep("Verify that the data variable " + housholdPopulationCount + " data is added.");
        List<String> headerValues = comparisonReportPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", headerValues.contains(housholdPopulationCount.getFullName()));

        comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
                .clickTransposeReport();
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openColumnHeaderDropdown(housholdPopulationPercent.getFullName()).clickHideDataVariable();
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openColumnHeaderDropdown(housholdPopulationCount.getFullName()).clickAddPercent();
        verificationStep("Verify that the data variable " + housholdPopulationPercent + " data is added.");
        headerValues = comparisonReportPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", headerValues.contains(housholdPopulationPercent.getFullName()));

    }

}
