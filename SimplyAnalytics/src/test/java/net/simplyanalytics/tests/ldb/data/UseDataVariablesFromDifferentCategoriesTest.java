package net.simplyanalytics.tests.ldb.data;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UseDataVariablesFromDifferentCategoriesTest extends TestBase {

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
  public void testUseDataVariablesFromDifferentCategories() {
    List<String> dataElements = mapPage.getToolbar().openDataVariableListMenu().getElementsName();
    int numberOfElements = dataElements.size();
    int numberOfAddedElements = 4;
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) mapPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    dataByCategoryDropwDown.clickOnARandomDataResult();

    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.HOUSEHOLDS);
    dataByCategoryDropwDown.clickOnARandomDataResult();

    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    dataByCategoryDropwDown.clickOnARandomDataResult();

    dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.POPULAR_DATA);
    String addedElement = dataByCategoryDropwDown.clickOnARandomDataResult();
    
    if (dataElements.contains(addedElement))
      --numberOfAddedElements;

    dataByCategoryDropwDown.clickClose(Page.MAP_VIEW);

    verificationStep("Verify that number of data variable is increased");
    Assert.assertEquals("The actual data variable should be increased by " + numberOfAddedElements, numberOfElements + numberOfAddedElements,
        mapPage.getToolbar().openDataVariableListMenu().getNumberOfElements());
  }

}
