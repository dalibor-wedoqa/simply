package net.simplyanalytics.tests.ldb.data;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.HeadlessIssue;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataFilterResultPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.filter.BaseFilter;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilteredDataDeleteTest extends TestBase {

  private MapPage mapPage;

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
  }

  @Test
  @HeadlessIssue
  public void choseDataVariableWithSearchFieldTest() {
    String search = "Education";
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataTab.enterDataSearch(search);
    BaseFilter baseFilter = dataByCategoryDropwDown.getBaseFilter();

    dataByCategoryDropwDown.clickOnARandomDataResult();
    logger.trace(baseFilter.getNumberOfResults() + " the active filters!");

    dataByCategoryDropwDown.clickOnYear("2020");
    DataFilterResultPanel dataFilterResultPanel = dataByCategoryDropwDown
        .getDataFilterResultPanel();
    baseFilter = dataFilterResultPanel.addRandomFilter();

    verificationStep("Verify that actual year is the same as the checked year");
    Assert.assertTrue("Years are not the same",
        baseFilter.filterYear().get(0).equals(dataFilterResultPanel.getCheckedYear()));
    //TODO in case EASI Data filter
    Assert.assertTrue(
        "There is missing some filters " + baseFilter.getAllFilterElements().size()
            + (baseFilter.getSizeOfFilteredElement(baseFilter.filterData())
                + baseFilter.getSizeOfFilteredElement(baseFilter.filterText())
                + baseFilter.getSizeOfFilteredElement(baseFilter.filterYear())),
        (baseFilter.getAllFilterElements()
            .size() == (baseFilter.getSizeOfFilteredElement(baseFilter.filterData())
                + baseFilter.getSizeOfFilteredElement(baseFilter.filterText())
                + baseFilter.getSizeOfFilteredElement(baseFilter.filterYear()))));

    baseFilter.deleteAllFilters();
    verificationStep("Verify is the delete operation success");
    Assert.assertTrue("Delete operation fail!", baseFilter.getAllFilterElements().size() == 1);
  }
}