package net.simplyanalytics.tests.ldb.data;

import java.util.ArrayList;
import java.util.List;

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
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ConsumerExpeditureExtimatesDatasetTests extends TestBase {
  
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
  public void testConsumerExpeditureEstimatesTooltip() {
    DataTab dataTab = mapPage.getLdbSection().clickData();
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) dataTab.clickSearchBy(DataBrowseType.DATA_FOLDER);
    DataByDatasetDropDown datasetDropDown = dataByDataFolderPanel
        .clickDataset(Dataset.CONSUMER_EXPENDITURE_ESTIMATE);
    datasetDropDown = datasetDropDown.getDatasetNavigationPanel().openRandomFolders();
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
}
