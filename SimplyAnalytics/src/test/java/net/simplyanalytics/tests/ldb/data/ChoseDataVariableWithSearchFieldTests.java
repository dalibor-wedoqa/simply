package net.simplyanalytics.tests.ldb.data;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChoseDataVariableWithSearchFieldTests extends TestBase {

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
  public void choseDataVariableWithSearchFieldTest() {
    String search = "Education";
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataTab.enterDataSearch(search);

    dataByCategoryDropwDown.clickOnARandomDataResult();
    dataByCategoryDropwDown.clickClose(Page.MAP_VIEW);
    
    verificationStep("Verify that actual data variable contains the searched text");
    Assert.assertTrue("The actual data variable: " + mapPage.getToolbar().getNameOfActiveDataVariable() + " should containing the 'Education' text",
        mapPage.getToolbar().getNameOfActiveDataVariable().contains(search.toLowerCase()) || mapPage.getToolbar().getNameOfActiveDataVariable().contains(search.toUpperCase()) || mapPage.getToolbar().getNameOfActiveDataVariable().contains(search));
  }

}
