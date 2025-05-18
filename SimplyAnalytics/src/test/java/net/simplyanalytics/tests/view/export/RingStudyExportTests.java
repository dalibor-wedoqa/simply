package net.simplyanalytics.tests.view.export;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

import javax.mail.Message;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.downloadutil.DownloadUtil;
import net.simplyanalytics.enums.ExportLocation;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;

import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.CompareUtils;
import net.simplyanalytics.utils.CsvUtils;
import net.simplyanalytics.utils.DbfUtils;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.ExcelUtils;

import net.simplyanalytics.utils.EmailUtils.EmailFolder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *.
 * @author wedoqa
 */
public class RingStudyExportTests extends TestBase {

  private static final ViewType viewType = ViewType.RING_STUDY;

  private final String excelMessage = "Excel Email Message - RingStudy"
      + randomNumericStringGenerator.generate(4);

  private final String csvMessage = "CSV Email Message - RingStudy"
      + randomNumericStringGenerator.generate(4);

  private final String dbfMessage = "DBF Email Message - RingStudy"
      + randomNumericStringGenerator.generate(4);

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;
  private RingStudyPage ringStudyPage;
  private ExportWindow exportWindow;
  private NewViewPage newViewPage;
  private EditRingStudyPage editRingStudyPage;

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
  }
 
  @Test
  public void testExcelEmailExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    newViewPage = mapPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(viewType);

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    ((DataByCategoryPanel) ringStudyPage.getLdbSection().getDataTab().getBrowsePanel())
        .chooseRandomNumberOfDataVariables(5, Page.RING_STUDY_VIEW);

    exportWindow = ringStudyPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, excelMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", ringStudyPage.getAlertMessage());

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

    verificationStep("Verify that the Excel Ring Study was successfully exported to email");
    Assert.assertTrue("The selected Ring Study was not exported",
        emailUtils.isExportPresent(message, FileFormat.EXCEL, exportName, exportNameVar));
    
    InputStream is = new ByteArrayInputStream(emailUtils
        .getAttachmentInByte(message, "name=\\\"New Project_" + viewType.getDefaultName() + "[^\\\\\"]*\\\"([^\\-]+)--"));

    List<String[]> attachmentContent = new ExcelUtils().getExcelContent(is);
    List<List<String>> tableContent = ringStudyPage.getActiveView().getTableContents();
    
    //Remove the headers
    attachmentContent.remove(0);
    for(int i = 0; i < attachmentContent.size(); i++)
      attachmentContent.set(i, Arrays.copyOfRange(attachmentContent.get(i), 1, attachmentContent.get(i).length));
    
    verificationStep("Verify if attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent)); 
  }

  @Test
  public void testCsvEmailExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    newViewPage = mapPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(viewType);

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    ((DataByCategoryPanel) ringStudyPage.getLdbSection().getDataTab().getBrowsePanel())
        .chooseRandomNumberOfDataVariables(5, Page.RING_STUDY_VIEW);

    exportWindow = ringStudyPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.CSV, GMailUser.EMAIL, csvMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", ringStudyPage.getAlertMessage());

    final String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now()
        + "_" + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    final String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now()
        + "_" + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        csvMessage);

    verificationStep("Verify that the CSV Ring Study was successfully exported to email");
    Assert.assertTrue("The selected Ring Study was not exported",
        emailUtils.isExportPresent(message, FileFormat.CSV, exportName, exportNameVar));
    
    String decodedString = emailUtils
        .getAttachment(message, "name=\\\"New Project_" + viewType.getDefaultName()+ "[^\\\\\"]*\\\"([^\\-]+)--");
    List<String[]> attachmentContent = new CsvUtils().getCsvContent(decodedString);
    List<List<String>> tableContent = ringStudyPage.getActiveView().getTableContents();
    
    //Remove the headers
    attachmentContent.remove(0);
    for(int i = 0; i < attachmentContent.size(); i++)
      attachmentContent.set(i, Arrays.copyOfRange(attachmentContent.get(i), 1, attachmentContent.get(i).length));
    
    verificationStep("Verify if attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent));
  }

  @Test
  public void testDbfEmailExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    newViewPage = mapPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(viewType);

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    ((DataByCategoryPanel) ringStudyPage.getLdbSection().getDataTab().getBrowsePanel())
        .chooseRandomNumberOfDataVariables(5, Page.RING_STUDY_VIEW);

    exportWindow = ringStudyPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.DBF, GMailUser.EMAIL, dbfMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", ringStudyPage.getAlertMessage());

    final String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now()
        + "_" + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    final String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now()
        + "_" + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        dbfMessage);

    verificationStep("Verify that the BDF Ring Study was successfully exported to email");
    Assert.assertTrue("The selected Ring Study was not exported",
        emailUtils.isExportPresent(message, FileFormat.DBF, exportName, exportNameVar));
    
    InputStream is = new ByteArrayInputStream(emailUtils
        .getAttachmentInByte(message, "name=\\\"New Project_" + viewType.getDefaultName() + "[^\\\\\"]*\\\"([^\\-]+)--"));
    
    List<String[]> attachmentContent = new DbfUtils().getDbfContent(is);
    List<List<String>> tableContent = ringStudyPage.getActiveView().getTableContents();
    
    //Remove the headers
    attachmentContent.remove(0);
    for(int i = 0; i < attachmentContent.size(); i++)
      attachmentContent.set(i, Arrays.copyOfRange(attachmentContent.get(i), 1, attachmentContent.get(i).length));
    
    verificationStep("Verify if attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent));
    
  }

  @Test
  public void testExcelDownloadExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    newViewPage = mapPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(viewType);

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    ((DataByCategoryPanel) ringStudyPage.getLdbSection().getDataTab().getBrowsePanel())
        .chooseRandomNumberOfDataVariables(5, Page.RING_STUDY_VIEW);

    exportWindow = ringStudyPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.EXCEL.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the Excel Ring Study was successfully downloaded");
    Assert.assertNotEquals("The selected Ring Study was not exported", file, null);
    
    FileInputStream fis = new FileInputStream(file);  
    
    List<String[]> fileContent = new ExcelUtils().getExcelContent(fis);
    List<List<String>> tableContent = ringStudyPage.getActiveView().getTableContents();
    
    //Remove the headers
    fileContent.remove(0);
    for(int i = 0; i < fileContent.size(); i++)
      fileContent.set(i, Arrays.copyOfRange(fileContent.get(i), 1, fileContent.get(i).length));
    
    verificationStep("Verify if file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent)); 
    
    fis.close();
    DownloadUtil.deleteDownloadedFile(file);

  }

  @Test
  public void testCsvDownloadExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    newViewPage = mapPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(viewType);

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    ((DataByCategoryPanel) ringStudyPage.getLdbSection().getDataTab().getBrowsePanel())
        .chooseRandomNumberOfDataVariables(5, Page.RING_STUDY_VIEW);

    exportWindow = ringStudyPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.CSV, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.CSV.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the CSV Ring Study was successfully downloaded");
    Assert.assertNotEquals("The selected Ring Study was not exported", file, null);

    List<String[]> fileContent = new CsvUtils().getCsvContent(file);
    List<List<String>> tableContent = ringStudyPage.getActiveView().getTableContents();

    //Remove the headers
    fileContent.remove(0);
    for(int i = 0; i < fileContent.size(); i++)
      fileContent.set(i, Arrays.copyOfRange(fileContent.get(i), 1, fileContent.get(i).length));
    
    verificationStep("Verify if file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent));
    
    DownloadUtil.deleteDownloadedFile(file);
  }

  @Test
  public void testDbfDownloadExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    newViewPage = mapPage.getViewChooserSection().clickNewView();

    editRingStudyPage = (EditRingStudyPage) newViewPage.getActiveView().clickCreate(viewType);

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    ((DataByCategoryPanel) ringStudyPage.getLdbSection().getDataTab().getBrowsePanel())
        .chooseRandomNumberOfDataVariables(5, Page.RING_STUDY_VIEW);

    exportWindow = ringStudyPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.DBF, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);
    
    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.DBF.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the BDF Ring Study was successfully downloaded");
    Assert.assertNotEquals("The selected Ring Study was not exported", file, null);
    
    FileInputStream fis = new FileInputStream(file);  
    
    List<String[]> fileContent = new DbfUtils().getDbfContent(fis);
    List<List<String>> tableContent = ringStudyPage.getActiveView().getTableContents();
    
    //Remove the headers
      fileContent.remove(0);
      for(int i = 0; i < fileContent.size(); i++)
        fileContent.set(i, Arrays.copyOfRange(fileContent.get(i), 1, fileContent.get(i).length));
    
    verificationStep("Verify if file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent));
    
    fis.close();
    DownloadUtil.deleteDownloadedFile(file);
  }
}