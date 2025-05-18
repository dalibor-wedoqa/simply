package net.simplyanalytics.tests.ldb.data;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OpenDataFromGridLDB extends TestBase {

  private MapPage mapPage;
  private DataByCategoryDropwDown dataByCategoryDropwDown;

  /**
   * Signing in and creating new project.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  @Test
  public void openRandomDataTab() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) dataTab.getBrowsePanel();
    int temp = 0;
    while (temp < 7) {
      dataByCategoryDropwDown = dataByCategoryPanel.clickRandomDataCategory();
      verificationStep("Verify that the category dropdown is present");
      Assert.assertTrue("Category dropdown is not present", dataByCategoryDropwDown.isDisplayed());
      dataByCategoryDropwDown.clickClose(Page.MAP_VIEW);
      temp++;
    }
  }

}
