package net.simplyanalytics.tests.canada;

import net.simplyanalytics.utils.DatasetYearUpdater;
import org.junit.Assert;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.search.DataResultItem;
import net.simplyanalytics.pageobjects.windows.DataSheetWindow;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class DataSheetWindowTests extends TestBase {
  String year = "2024";

  // https://atlassian.simplymap.net:9080/browse/SA-1054
  @Test
  public void testWindowLayout() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    createNewProjectWindow.selectCountry("Canada");
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);
    DataByDataFolderPanel dataByDatasetPanel = (DataByDataFolderPanel) mapPage.getLdbSection()
        .getDataTab().clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDatasetDropDown dataByDatasetDropDown = dataByDatasetPanel
        .clickDataset(Dataset.PRIZM_CA);
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
        .clickFolder("Social Groups");
    dataByDatasetDropDown = dataByDatasetDropDown.getDatasetNavigationPanel()
            .clickFolder("Households");
    DataResultItem dataResultItem = dataByDatasetDropDown
        .getDatasetSearchResultPanel()
        .getDataVariableRow("# Households in U1: Urban Elite");
    MetadataWindow metadataWindow = dataResultItem.clickOnMoreOptions().clickViewMetadata();
    DataSheetWindow dataSheetWindow = metadataWindow
        .clickDataSheetByName(DatasetYearUpdater.updateDatasetYear("# Households in U1: Urban Elite, 2023",year));

    verificationStep("Verify that the header padding is not too big");
    int padding = dataSheetWindow.getHeaderHeight() - dataSheetWindow.getTitleHeight();

    logger.trace("padding: " + padding);
    Assert.assertTrue("The padding should be less than 50 pixel", padding < 50);

    verificationStep("Verify that the close button is horizontally in the middle of the window");
    int expected = dataSheetWindow.getWindowXLocation() + dataSheetWindow.getWindowWidth() / 2;
    int actual = dataSheetWindow.getCloseButtonXLocation()
        + dataSheetWindow.getCloseButtonWidth() / 2;
    int difference = expected - actual;

    logger.trace("difference: " + difference);
    Assert.assertTrue("The dictance from the centar of the window should be less than 5 pixel",
        difference < 5);
  }

}
