package net.simplyanalytics.tests.userbased;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * .
 * 
 * @author wedoqa
 */
public class MissingRequiredDataOpenViewTests extends BaseUserBasedTest {

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private MapPage mapPage;
  private EditMapPage editMapPage;
  private EditBusinessesPage editBusinessPage;
  private EditComparisonReportPage editComparisonReportPage;
  private EditRankingPage editRankingPage;
  private NewViewPage newViewPage;
  private EditRingStudyPage editRingStudyPage;
  private EditRelatedDataReportPage editRelatedData;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    NewProjectLocationWindow newProjectLocationWindow = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection()
     .clickNewProject();

    mapPage = newProjectLocationWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  @Test
  public void testOpenViewWithMissingRequiredData() {

    editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(ViewType.MAP.getDefaultName()).clickEdit();
    editMapPage.getActiveView().getLocationsPanel().clickElement("None");
    editMapPage.getActiveView().getDataPanel().clickElement("None");
    editMapPage.getActiveView().getBusinessesPanel().clickElement("None");

    editMapPage.getActiveView().clickDoneButton();

    editComparisonReportPage = (EditComparisonReportPage) mapPage.getViewChooserSection()
        .openViewMenu(ViewType.COMPARISON_REPORT.getDefaultName()).clickEdit();

    editComparisonReportPage.getActiveView().getDataPanel().clickClear();
    //editComparisonReportPage.getActiveView().getLocationsPanel().clickClear();

    editRankingPage = (EditRankingPage) editComparisonReportPage.getViewChooserSection()
        .openViewMenu(ViewType.RANKING.getDefaultName()).clickEdit();

    editRankingPage.getActiveView().getDataPanel().clickClear();

    newViewPage = editRankingPage.getViewChooserSection().clickNewView();

    editBusinessPage = (EditBusinessesPage) newViewPage.getActiveView()
        .clickCreate(ViewType.BUSINESSES);
    editBusinessPage.getActiveView().clickDoneButton();

    newViewPage = editBusinessPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView()
        .clickCreate(ViewType.RING_STUDY);
    editRingStudyPage.getActiveView().getDataPanel().clickClear();

    newViewPage = editRingStudyPage.getViewChooserSection().clickNewView();

    editRelatedData = (EditRelatedDataReportPage) newViewPage.getActiveView()
        .clickCreate(ViewType.RELATED_DATA);
    editRelatedData.getActiveView().getLocationsPanel().clickClear();

    editComparisonReportPage = (EditComparisonReportPage) editRelatedData.getViewChooserSection()
        .clickView(ViewType.COMPARISON_REPORT.getDefaultName(), true);

    editRingStudyPage = (EditRingStudyPage) editComparisonReportPage.getViewChooserSection()
        .clickView(ViewType.RING_STUDY.getDefaultName(), true);

    editBusinessPage = (EditBusinessesPage) editRingStudyPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName(), true);

    editRankingPage = (EditRankingPage) editBusinessPage.getViewChooserSection()
        .clickView(ViewType.RANKING.getDefaultName(), true);

    editRelatedData = (EditRelatedDataReportPage) editRankingPage.getViewChooserSection()
        .clickView(ViewType.RELATED_DATA.getDefaultName(), true);
  }
  
  @After
  public void after() {
    unlockUser();
  }

}