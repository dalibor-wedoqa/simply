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

public class AddOtherYearsTests extends TestBase {

    private MapPage mapPage;
    private DataVariable educationBachelorDegreePercent = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
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
    public void testRankingViewColumnHeaderAddOtherYears() {
        RankingPage rankingPage = (RankingPage) mapPage.getViewChooserSection().clickView(ViewType.RANKING.getDefaultName());
        dataVariablesList.add(educationBachelorDegreePercent.getFullName());
        rankingPage = (RankingPage) rankingPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreePercent.getFullName()).addOtherYear();
        rankingPage.getActiveView().closeColumnHeaderDropdown(educationBachelorDegreePercent.getFullName());
        verificationStep("Verify that the randomized other year data variable is added.");
        List<String> columnHeaderValues = rankingPage.getActiveView().getNormalHeaderValues();
        Assert.assertTrue("The data variable is not added.", columnHeaderValues.contains(DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020.getFullName()));

    }

    @Test
    public void testRingStudyViewColumnHeaderAddOtherYears() {
        RingStudyPage ringStudyPage = (RingStudyPage) mapPage.getViewChooserSection().clickNewView().getActiveView()
                .clickCreate(ViewType.RING_STUDY).clickDone();
        dataVariablesList.add(educationBachelorDegreePercent.getFullName());
        ringStudyPage = (RingStudyPage) ringStudyPage.getActiveView().openRowHeaderDropdown(educationBachelorDegreePercent.getFullName()).addOtherYear();
        ringStudyPage.getActiveView().openRowHeaderDropdownVoid(educationBachelorDegreePercent.getFullName());
        verificationStep("Verify that the randomized other year data variable is added.");
        List<String> rowHeaderValues = ringStudyPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020.getFullName()));
    }

    @Test
    public void testComparisonReportViewHeaderWithTransposeAddOtherYears() {
        ComparisonReportPage comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection().clickView(ViewType.COMPARISON_REPORT.getDefaultName());
        dataVariablesList.add(educationBachelorDegreePercent.getFullName());
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openRowHeaderDropdown(educationBachelorDegreePercent.getFullName()).addOtherYear();
        comparisonReportPage.getActiveView().openRowHeaderDropdownVoid(educationBachelorDegreePercent.getFullName());
        verificationStep("Verify that the randomized other year data variable is added.");
        List<String> rowHeaderValues = comparisonReportPage.getActiveView().getRowHeaderValues();
        Assert.assertTrue("The data variable is not added.", rowHeaderValues.contains(DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020.getFullName()));

        comparisonReportPage = ((ComparisonViewActionMenu) comparisonReportPage.getToolbar().clickViewActions())
                .clickTransposeReport();
        comparisonReportPage = (ComparisonReportPage) comparisonReportPage.getActiveView().openColumnHeaderDropdown(educationBachelorDegreePercent.getFullName()).addOtherYear();
        comparisonReportPage.getActiveView().closeColumnHeaderDropdown(educationBachelorDegreePercent.getFullName());
        verificationStep("Verify that the other year data variable is removed.");
        List<String> columnHeaderValues = comparisonReportPage.getActiveView().getNormalHeaderValues();
        Assert.assertFalse("The data variable is not removed.", columnHeaderValues.contains(DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020.getFullName()));

    }
}
