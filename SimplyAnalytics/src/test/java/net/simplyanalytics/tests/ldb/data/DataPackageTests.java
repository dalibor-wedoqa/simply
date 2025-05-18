package net.simplyanalytics.tests.ldb.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.DataPackage;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByDataFolderPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class DataPackageTests extends TestBase {
  
  private MapPage mapPage;
  private NewProjectLocationWindow createNewProjectWindow;
  
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }
  
  @Test
  public void testUSADataPackages() {
    NewViewPage newViewPage = createNewProjectWindow.clickClose();
    mapPage = (MapPage) newViewPage.getActiveView().clickCreate(ViewType.MAP).clickDone();
    
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) mapPage.getLdbSection().clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    
    DataPackage[] expectedPackages = DataPackage.values();
   
    verificationStep("Verify that the correct data packages are present");
    Assert.assertArrayEquals("Not the correct packages are present", expectedPackages, dataByDataFolderPanel.getDataPackages().toArray());
  }
  
  @Test
  public void testCanadianDataPackages() {
    createNewProjectWindow.selectCountry("Canada");
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);
    
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) mapPage.getLdbSection().clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    
    DataPackage[] expectedPackages = {DataPackage.STANDARD_DATA_PACKAGE, DataPackage.PREMIUM_DATA};
    
    verificationStep("Verify that the correct data packages are present");
    Assert.assertArrayEquals("Not the correct packages are present", expectedPackages, dataByDataFolderPanel.getDataPackages().toArray());
  }

  //
  @Test
  public void testUSADatasetsPresent() {
    NewViewPage newViewPage = createNewProjectWindow.clickClose();
    mapPage = (MapPage) newViewPage.getActiveView().
            clickCreate(ViewType.MAP).
            clickDone();
    
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) mapPage.
            getLdbSection().
            clickData().
            clickSearchBy(DataBrowseType.DATA_FOLDER);

    System.out.println("========================================================================================================================");
    System.out.println("Dataset\n" + Dataset.getUsaDatasetByRelease("Latest"));
    System.out.println("========================================================================================================================");
    System.out.println("dataByDataFolderPanel.getAllDatasets (USA)\n" + dataByDataFolderPanel.getAllDatasets("USA"));
    System.out.println("========================================================================================================================");

    verificationStep("Verify that the correct datasets are present");
    Assert.assertArrayEquals("Not the correct datasets are present",
            Dataset.
                    getUsaDatasetByRelease("Latest").
                    toArray(),
            dataByDataFolderPanel.
                    getAllDatasets("USA").
                    toArray());
  }
  
  @Test
  public void testCanadianDatasetsPresent() {
    createNewProjectWindow.selectCountry("Canada");
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);
    
    DataByDataFolderPanel dataByDataFolderPanel = (DataByDataFolderPanel) mapPage.getLdbSection().clickData().clickSearchBy(DataBrowseType.DATA_FOLDER);
    
    verificationStep("Verify that the correct datasets are present");
    Assert.assertArrayEquals("Not the correct datasets are present", Dataset.getCanadaDatasetByRelease("Latest").toArray(), dataByDataFolderPanel.getAllDatasets("Canada").toArray());
  }

}
