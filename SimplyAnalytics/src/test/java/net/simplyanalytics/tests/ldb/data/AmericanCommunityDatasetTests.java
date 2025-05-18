package net.simplyanalytics.tests.ldb.data;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.enums.ViewType;
import org.junit.Assert;
import org.junit.Before;
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
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class AmericanCommunityDatasetTests extends TestBase {

  private MapPage mapPage;
  
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
  public void testAmericanCommunitySurveyTooltip() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDatasetDropDown datasetDropDown = dataByDataFolderPanel
        .clickDataset(Dataset.AMERICAN_COMMUNITY_SURVEY);
    datasetDropDown = datasetDropDown.getDatasetNavigationPanel().openRandomFoldersACSurvey();
    List<String> dataVariableList = datasetDropDown.getDatasetSearchResultPanel().getAllDataVariables();
    datasetDropDown.searchText("Test");
    sleep(1000);
    verificationStep("Verify that data results are hidden");
    Assert.assertTrue("Results are not hidden", datasetDropDown.getDatasetSearchResultPanel().isResultHidden());
    
    List<String> hiddenDataVariables = new ArrayList<String>();
    for(int i = 0; i < dataVariableList.size(); i++) {
      hiddenDataVariables.add(datasetDropDown.getDatasetSearchResultPanel().hoverDataVariableByIndex(i));
    }
    
    verificationStep("Verify the tooltip contents");
    Assert.assertArrayEquals("The tooltip content is not the expected", dataVariableList.toArray(), hiddenDataVariables.toArray());
  }
  
  @Test
  public void testAmericanCommunitySurveyMetadataWindowFields() {

    String year = "2020";
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    dataTab.openYearDropdown().clickYear(year);

    DataByDatasetDropDown dataByDatasetDropdown = dataByDataFolderPanel
        .clickDataset(Dataset.AMERICAN_COMMUNITY_SURVEY);

    dataByDatasetDropdown = dataByDatasetDropdown.getDatasetNavigationPanel().
            openRandomFoldersACSurvey();
    String data = dataByDatasetDropdown.getDatasetSearchResultPanel().
            clickOnRandomData();
    MetadataWindow metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).
            clickOnMoreOptions().
            clickViewMetadata();

    System.out.println("=======================================================================================");
    System.out.println("Godina za data folder select\n" + year);
    System.out.println("=======================================================================================");
    System.out.println("Metada window godina replaceAll\n" + metadataWindow.getMetadataValue("Year").
                    replaceAll(".*?(\\d{4}).*", "$1").trim());
    System.out.println("=======================================================================================");
    System.out.println("Metada window godina split [0]\n" + metadataWindow.getMetadataValue("Year").split(" ")[0].trim());
    System.out.println("=======================================================================================");
    System.out.println("Metada window godina replaceAll ali sa estimate celim tekstom bez trima\n" + metadataWindow.getMetadataValue("Year").
            replaceAll("(estimate calculated using 2013-2018 data for this variable)", ""));
    System.out.println("=======================================================================================");
    System.out.println("Metada window godina replaceAll ali sa estimate celim tekstom sa trimom\n" + metadataWindow.getMetadataValue("Year").
            replaceAll("(estimate calculated using 2013-2018 data for this variable)", "").trim());
    System.out.println("=======================================================================================");


    //verificationStep("Verify value of the year field is: " + year + " (projection calculated using 2013-2018 data for this variable)");
    verificationStep("Verify value of the year field is: " + year);
    Assert.assertEquals("Year is not the expected", year, metadataWindow.getMetadataValue("Year").split(" ")[0].trim());
    //ovo ispod je bilo ovo iznad si ti dodao
    //Assert.assertEquals("Year is not the expected", year + " (estimate calculated using 2013-2018 data for this variable)", metadataWindow.getMetadataValue("Year"));
  
    metadataWindow.clickClose();

//    mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().clickGeneralSettingsButton().
//            getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
//    mapPage.getViewChooserSection().clickNewView().getActiveView().clickHistoricalViewsCheckbox();
//    mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.MAP);
    
    year = "2022";
    dataTab.openYearDropdown().clickYear(year);
    dataByDatasetDropdown = dataByDataFolderPanel
        .clickDataset(Dataset.AMERICAN_COMMUNITY_SURVEY);
    dataByDatasetDropdown = dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersACSurvey();
    data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
    metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).
            clickOnMoreOptions().clickViewMetadata();

    //verificationStep("Verify value of the year field is: " + year + " (estimate calculated using 2013-2018 data for this variable)");
    //Assert.assertEquals("Year is not the expected", year + " (estimate calculated using 2013-2018 data for this variable)", metadataWindow.getMetadataValue("Year"));
    verificationStep("Verify value of the year field is: " + year);
    Assert.assertEquals("Year is not the expected", year, metadataWindow.getMetadataValue("Year"));

    metadataWindow.clickClose();

    year = "2028";
    dataTab.openYearDropdown().clickYear(year);
    dataByDatasetDropdown = dataByDataFolderPanel
        .clickDataset(Dataset.AMERICAN_COMMUNITY_SURVEY);
    dataByDatasetDropdown = dataByDatasetDropdown.getDatasetNavigationPanel().openRandomFoldersACSurvey();
    data = dataByDatasetDropdown.getDatasetSearchResultPanel().clickOnRandomData();
    metadataWindow = dataByDatasetDropdown.getDatasetSearchResultPanel().getDataVariableRow(data).clickOnMoreOptions().clickViewMetadata();

    //verificationStep("Verify value of the year field is: " + year + " (projection calculated using 2013-2018 data for this variable)");
    //Assert.assertEquals("Year is not the expected", year + " (projection calculated using 2013-2018 data for this variable)", metadataWindow.getMetadataValue("Year"));
    verificationStep("Verify value of the year field is: " + year);
    Assert.assertEquals("Year is not the expected", year, metadataWindow.getMetadataValue("Year"));

    metadataWindow.clickClose();
  }
}
