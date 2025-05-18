package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.RankingExportOptions;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditQuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindowRanking;
import net.simplyanalytics.pageobjects.pages.main.export.RankingChooseExportDropdown;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.quickexport.QuickExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExportCloseSpecialCasesTests extends TestBase {

  private MapPage mapPage;
  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {

    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  @Test
  public void mapExportCancel() {

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();
    croppingWindow.clickCancel();

    verificationStep("Verify that the Export dialog is disappeared");
    Assert.assertFalse("The Export dialog should be disappeared",
        croppingWindow.exportIsDisplayed());
  }

  @Test
  public void mapExportClose() {

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();
    croppingWindow.clickClose();

    verificationStep("Verify that the Export dialog is disappeared");
    Assert.assertFalse("The Export dialog should be disappeared",
        croppingWindow.exportIsDisplayed());
  }

  @Test
  public void quickReportExportClose() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) newViewPage.getActiveView()
        .clickCreate(ViewType.QUICK_REPORT);
    QuickReportPage quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();

    QuickExportWindow exportWindow = quickReportPage.getToolbar().clickExport();
    exportWindow.clickCancel();

    verificationStep("Verify that the Export dialog is disappeared");
    Assert.assertFalse("The Export dialog should be disappeared", exportWindow.isDisplayed());

  }
  
  @Test
  public void rankingExportClose() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    EditRankingPage editRankingPage = (EditRankingPage) newViewPage.getActiveView()
        .clickCreate(ViewType.RANKING);
  
    RankingPage rankingPage = (RankingPage) editRankingPage.clickDone();

    RankingChooseExportDropdown rankingChooseExportDropdown = (RankingChooseExportDropdown) rankingPage.getToolbar().clickExportButton();
    ExportWindowRanking exportWindow = rankingChooseExportDropdown.clickExport(RankingExportOptions.EXPORT_ALL_ROWS);
    
    exportWindow.clickCancel();
    
    verificationStep("Verify that the Export dialog is disappeared");
    Assert.assertFalse("The Export dialog should be disappeared", exportWindow.isDisplayed());
  }

}
