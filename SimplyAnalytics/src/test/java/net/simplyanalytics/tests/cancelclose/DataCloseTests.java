package net.simplyanalytics.tests.cancelclose;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataCloseTests extends TestBase {

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
  public void closeByCategory() {

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) mapPage.getLdbSection().clickData().clickSearchBy(DataBrowseType.CATEGORY);

    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickRandomDataCategory();
    dataByCategoryDropwDown.clickClose(Page.MAP_VIEW);

    verificationStep("Verify that the data drop down is disappeared");
    Assert.assertFalse("The data drop down should be disappeared",
        dataByCategoryDropwDown.isDisplayed());
  }

  @Test
  public void closeByDataset() {
    DataByDataFolderPanel dataByDatasetPanel = (DataByDataFolderPanel) mapPage.getLdbSection().clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel.clickRandomDataset();
    dataByDatasetDropDown.clickClose(Page.MAP_VIEW);
    
    verificationStep("Verify that the data drop down is disappeared");
    Assert.assertFalse("The data drop down should be disappeared",
        dataByDatasetDropDown.isDisplayed());
  }

}
