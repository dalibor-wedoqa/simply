package net.simplyanalytics.tests.view.export;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.mail.Message;

import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.downloadutil.DownloadUtil;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.ExportLocation;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

public class ScarboroughExportTests extends TestBase {
  
  
  private static final ViewType viewType = ViewType.SCARBOROUGH_CROSSTAB_TABLE;
 
  private final String excelMessage = "Excel Email Message - Scarborough Crosstab Table "
      + randomNumericStringGenerator.generate(4);

  private AuthenticateInstitutionPage institutionPage;
   private SignInPage signInPage;
   private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
   private NewProjectLocationWindow createNewProjectWindow;
   
   private EditScarboroughCrosstabPage editScarboroughCrosstabPage;
   private MapPage mapPage;
   ScarboroughCrosstabPage scarboroughCrosstabPage;
  
   /**
    * Signing in.
    */
   @Before
   public void before() {
 
     institutionPage = new AuthenticateInstitutionPage(driver);
     signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
         InstitutionUser.PASSWORD);
     welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
     createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
     
     mapPage = createNewProjectWindow
         .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
     ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
     projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
     
   }
 
   @Test
   public void testExcelEmailExport() throws Exception {
     NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
     StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);
  
     DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
         .clickData().getBrowsePanel();
     
     DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.AGE);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.GENDER);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.INCOME);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.EDUCATION);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.LANGUAGE);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
     
     scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
     
     ExportWindow exportWindow = scarboroughCrosstabPage.getToolbar().clickExport();
     
     exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, excelMessage,
         ExportLocation.EMAIL);
     
     verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
     Assert.assertEquals("Message is missing", 
         "Your export is being processed.You will receive an email shortly.", scarboroughCrosstabPage.getAlertMessage());
  
     final String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now()
         + "_" + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
     final String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now()
         + "_" + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));
  
     // verify that email arrives , open and verify that attachment with map export
     // is present
     EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
         EmailFolder.INBOX);
     Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
         excelMessage);
  
     verificationStep("Verify that the Excel Scarborough Crosstab Table was successfully exported to email");
     Assert.assertTrue("The Scarborough Crosstab Table was not exported",
         emailUtils.isExportPresent(message, FileFormat.EXCEL, exportName, exportNameVar));
   }
   
   @Test
   public void testExcelDownloadExport() throws Exception {
     NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
     StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);
  
     DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
         .clickData().getBrowsePanel();
     
     DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.AGE);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.GENDER);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.INCOME);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.EDUCATION);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.LANGUAGE);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
     
     scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
     
     ExportWindow exportWindow = scarboroughCrosstabPage.getToolbar().clickExport();
     
     exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, "Email message",
         ExportLocation.COMPUTER);
     
     logger.info("Get downloaded file");
     File file = DownloadUtil
           .getDownloadedFile(viewType, FileFormat.EXCEL.getExtension(), getDownloadFilePath());
     
     verificationStep("Verify that the Excel Scarborough Crosstab Table was successfully downloaded");
     Assert.assertNotEquals("The selected Scarborough Crosstab Table was not exported", file, null);
     
     DownloadUtil.deleteDownloadedFile(file);
   }
   
   @Test
   public void testDbfDisabled() {
     NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
     StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);
  
     DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
         .clickData().getBrowsePanel();
     
     DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.GENDER);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
     
     scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
     
     ExportWindow exportWindow = scarboroughCrosstabPage.getToolbar().clickExport();
  
     logger.info("Verify that the DBF button is disabled");
     Assert.assertFalse("The DBF file format should be disabled",
         exportWindow.isFormatEnabled(FileFormat.DBF));
   }
   
   @Test
   public void testCsvDisabled() {
     NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
     StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);
  
     DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
         .clickData().getBrowsePanel();
     
     DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.GENDER);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     dataByCategoryDropwDown = dataByCategoryPanel
         .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
     for(int i = 0; i < 10; i++) {
       dataByCategoryDropwDown.clickOnARandomDataResult();
     }
     
     editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);
     
     scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
     
     ExportWindow exportWindow = scarboroughCrosstabPage.getToolbar().clickExport();
  
     logger.info("Verify that the DBF button is disabled");
     Assert.assertFalse("The DBF file format should be disabled",
         exportWindow.isFormatEnabled(FileFormat.CSV));
   }
 
}
