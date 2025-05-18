package net.simplyanalytics.tests.ldb.business;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.BusinessSearchBy;
import net.simplyanalytics.enums.BusinessSearchConditions;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.SearchConditionRow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusinessToolbarTests extends TestBase {
  private MapPage mapPage;
  private BusinessesPage businessesPage;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();

    for (int i = 0; i < 5; i++) {
      AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
          .clickUseAdvancedSearch();
      SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
      searchConditionRow.selectSearchBy(BusinessSearchBy.NAICS);
      searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
      searchConditionRow.selectRandomBusinessCode();
      advancedBusinessSearchWindow.clickSearch();
    }
    mapPage = new MapPage(getDriver());
  }

  @Test
  public void testSelectBusiness() {
    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName());
    List<List<String>> rows = businessesPage.getActiveView().getCellValues("City");
    businessesPage.getToolbar().changeLocation(Location.USA);
    List<List<String>> rows2 = businessesPage.getActiveView().getCellValues("City");

    verificationStep("Verify that location changed at city column in the table");
    //TODO case if the tables are empty
    Assert.assertNotEquals("Location unchanged", rows2, rows);

    businessesPage = businessesPage.getToolbar().changeToRandomBusiness();

    List<List<String>> rows1 = businessesPage.getActiveView().getCellValues("City");
    verificationStep("Verify that business changed as well as column in the table");
    //TODO case if the tables are empty
    Assert.assertNotEquals("Business unchanged", rows1, rows);

  }

}
