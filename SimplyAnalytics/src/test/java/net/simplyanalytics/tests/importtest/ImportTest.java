package net.simplyanalytics.tests.importtest;

import java.io.File;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.header.Header;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ErrorWindow;
import net.simplyanalytics.pageobjects.sections.header.importpackage.IdentifyDataView;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ImportSection;
import net.simplyanalytics.pageobjects.sections.header.importpackage.LocationNotFoundWindow;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ManageWindow;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ReviewAndImportView;
import net.simplyanalytics.pageobjects.sections.header.importpackage.SuccessfulDialog;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

public class ImportTest extends TestBase {
  private Header header;
  private ImportSection importSection;
  private IdentifyDataView identfyDataView;
  private ManageWindow manageWindow;
  private ProjectSettingsPage projectSettingsPage;
  private MapPage mapPage;
  private ReviewAndImportView reviewAndImportView;
  private String previousName;
  private int previousSize;
  
  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    lockUser();
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    projectSettingsPage = signInPage.signIn(SignUser.USER2, SignUser.PASSWORD2).getHeaderSection().clickProjectSettings();
    
    NewProjectLocationWindow newProjectLocationWindow = projectSettingsPage.getHeaderSection()
        .clickNewProject();
    mapPage = newProjectLocationWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    header = mapPage.getHeaderSection();
    
    //I think this is not necessary here. 
    //manageWindow = header.clickUser().clickManageImport();
    //manageWindow.clickClose();
    importSection = header.clickUser().clickImport();
  }
  
  @Test
  public void testImport() {	
    importSection.sendPath(LocationType.ZIP_CODE, getDriverConfiguration());
    importSection.selectGeographicUnit(LocationType.ZIP_CODE);
    importSection.addImportFile(LocationType.CENSUS_TRACT, getDriverConfiguration());
    importSection.addImportFile(LocationType.CITY, getDriverConfiguration());
    importSection.sendNameOfDataset();
    identfyDataView=importSection.clickNext();
    identfyDataView.autoFillDataType();
    reviewAndImportView=identfyDataView.clickNext();
    SuccessfulDialog successfulDialog = reviewAndImportView.clickBeginImport();    
    mapPage = successfulDialog.clickOkButton();
    manageWindow = header.clickUser().clickManageImport();
    manageWindow.clickEdit().renameDataset();
  }
  
  @Test
  public void testRenameImportedDataset() {
    importSection.sendPath(LocationType.ZIP_CODE, getDriverConfiguration());
    importSection.selectGeographicUnit(LocationType.ZIP_CODE);
    importSection.sendNameOfDataset();
    identfyDataView=importSection.clickNext();
    identfyDataView.autoFillDataType();
    reviewAndImportView=identfyDataView.clickNext();
    SuccessfulDialog successfulDialog = reviewAndImportView.clickBeginImport(); 
    
    verificationStep("Verify that the Import is success");
    Assert.assertTrue("The Import is not success", successfulDialog.isDisplayed());
    
    mapPage = successfulDialog.clickOkButton();
    manageWindow = header.clickUser().clickManageImport();
    previousName=manageWindow.getDatasetName();
    manageWindow.clickEdit().renameDataset();
    
    verificationStep("Verify that the name of dataset changed");
    Assert.assertTrue("The dataset name is the same", manageWindow.getDatasetName().equals(previousName)); 
    
   /* previousSize = manageWindow.getNumberOfImportedDatasets();
    manageWindow.deleteLastDatasets();
    
    verificationStep("Verify that the last dataset is deleted");
    Assert.assertTrue("The dataset is not deleted", manageWindow.getNumberOfImportedDatasets() != previousSize); */

  }

  @Test
  public void testDeleteImportedDataset() {
	  importSection.clickCancel();
	  manageWindow = header.clickUser().clickManageImport();
	  
	  previousSize = manageWindow.getNumberOfImportedDatasets();
	  manageWindow.deleteLastDatasets();
	  
	  verificationStep("Verify that the last dataset is deleted");
	  Assert.assertTrue("The dataset is not deleted", manageWindow.getNumberOfImportedDatasets() != previousSize); 
  }
  
  @Test
  public void testImportwithoutName() {  
    importSection.sendPath(LocationType.ZIP_CODE, getDriverConfiguration());
    importSection.selectGeographicUnit(LocationType.COUNTY);
    
    verificationStep(
        "Verify that alert appears (Please correct the following error(s) to continue:)");
    ErrorWindow errorWindow = importSection.clickNextWithError();
    
    verificationStep("Verify that error dataset name is required appears ");
    Assert.assertEquals(Arrays.asList("A dataset name is required."), errorWindow.getErrors());
    
    errorWindow.clickOkButton(); 
    
    importSection.sendNameOfDataset();
    
    identfyDataView=importSection.clickNext();
    identfyDataView.autoFillDataType();
    reviewAndImportView=identfyDataView.clickNext();
    LocationNotFoundWindow locationNotFoundWindow = reviewAndImportView.clickBeginImportLocationError();
    
    verificationStep("Verify that the Location error message is present");
    Assert.assertTrue("The dataset is not deleted", locationNotFoundWindow.isDisplayed()); 
  }


  @After
  public void after() {
    unlockUser();
  }  
}
