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

public class AddCountPercentageTests extends TestBase {

    private MapPage mapPage;
    private DataVariable educationBachelorDegreePercent = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
    private DataVariable educationBachelorDegreeCount = DataVariable.HASHTAG_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
    private List<String> dataVariablesList = new ArrayList<>();

    @Before
    public void login() {

        AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
        SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
        WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
        NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
        mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

    }

    @Test
    public void testRankingViewColumnHeaderAddCount() {

        RankingPage rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
        dataVariablesList.add(educationBachelorDegreePercent.getFullName());
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreePercent.getFullName()).clickAddCount();
        verificationStep("Verify that the data variable " + educationBachelorDegreeCount + " data is added.");
        List<String> columnHeaderValues = rankingPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", columnHeaderValues.contains(educationBachelorDegreeCount.getFullName()));
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreePercent.getFullName()).clickHideDataVariable();
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreeCount.getFullName()).clickAddPercent();
        verificationStep("Verify that the data variable " + educationBachelorDegreePercent + " data is added.");
        columnHeaderValues = rankingPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", columnHeaderValues.contains(educationBachelorDegreePercent.getFullName()));

    }

    @Test
    public void testRingStudyViewRowHeaderAddCount() {

        RingStudyPage ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RING_STUDY).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(educationBachelorDegreePercent.getFullName());
        ringStudyPage.getActiveView().openRowHeaderDropdown(educationBachelorDegreePercent.getFullName()).clickAddCount();
        verificationStep("Verify that the data variable " + educationBachelorDegreeCount + " data is added.");
        List<String> rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(educationBachelorDegreeCount.getFullName()));
        ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView().openRowHeaderDropdown(educationBachelorDegreePercent.getFullName()).clickHideDataVariable();
        ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView().openRowHeaderDropdown(educationBachelorDegreeCount.getFullName()).clickAddPercent();
        verificationStep("Verify that the data variable " + educationBachelorDegreePercent + " data is added.");
        rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(educationBachelorDegreePercent.getFullName()));

    }

    @Test
    public void testComparisonReportViewHeaderWithTransposeAddCount() {

        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.COMPARISON_REPORT).clickDone();
        List<String> dataVariablesList = new ArrayList<>();
        dataVariablesList.add(educationBachelorDegreePercent.getFullName());
        comparisonReportPage.getActiveView().openRowHeaderDropdown(educationBachelorDegreePercent.getFullName()).clickAddCount();
        verificationStep("Verify that the data variable " + educationBachelorDegreeCount + " data is added.");
        List<String> headerValues = comparisonReportPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", headerValues.contains(educationBachelorDegreeCount.getFullName()));

        comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
                .clickTransposeReport();
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreePercent.getFullName()).clickHideDataVariable();
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreeCount.getFullName()).clickAddPercent();
        verificationStep("Verify that the data variable " + educationBachelorDegreePercent + " data is added.");
        headerValues = comparisonReportPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", headerValues.contains(educationBachelorDegreePercent.getFullName()));

    }

}
