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
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditQuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.export.quickexport.QuickExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.CompareUtils;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.ExcelUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * .
 *
 * @author wedoqa
 */
public class QuickExportTests extends TestBase {

  private static final ViewType viewType = ViewType.QUICK_REPORT;
  
  private final String excelMessage = "Excel Email Message - Quick report"
      + randomNumericStringGenerator.generate(4);

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;

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

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.MIAMI_FL_CITY);

    QuickReportPage quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickView(viewType.toString());

    QuickExportWindow exportWindow = quickReportPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, excelMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", quickReportPage.getAlertMessage());

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

    verificationStep("Verify that the Excel Quick Report was successfully exported to email");
    Assert.assertTrue("The selected Quick Report was not exported",
        emailUtils.isExportPresent(message, FileFormat.EXCEL, exportName, exportNameVar));
    
    InputStream is = new ByteArrayInputStream(emailUtils
        .getAttachmentInByte(message, "name=\\\"New Project_" + viewType.getDefaultName() + "[^\\\\\"]*\\\"([^\\-]+)--"));

    List<String[]> attachmentContent = new ExcelUtils().getExcelContent(is);
    List<List<String>> tableContent = quickReportPage.getActiveView().getTableContents();

    attachmentContent.remove(0);
    attachmentContent.remove(0);
    
    for(int i = 0; i < attachmentContent.size(); i++) {
      if (attachmentContent.get(i)[0].matches("[A-Z\\\\s\\\\& ]+")) {
        attachmentContent.remove(i);
        i--;
        if (i == attachmentContent.size())
          break;
      }
    }
    
    for(int i = 0; i < attachmentContent.size(); i++) {
      attachmentContent.set(i, Arrays
          .copyOfRange(attachmentContent.get(i), 1, attachmentContent.get(i).length));
    }
    
    verificationStep("Verify if attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent)); 
  }
  
  @Test
  public void testExcelDownloadExport() throws Exception {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.MIAMI_FL_CITY);

    QuickReportPage quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickView(viewType.toString());

    QuickExportWindow exportWindow = quickReportPage.getToolbar().clickExport();

    logger.info("Verify that the excel button is enabled");
    Assert.assertTrue("The excel file format should be enabled",
        exportWindow.isFormatEnabled(FileFormat.EXCEL));
    exportWindow.chooseFileFormat(FileFormat.EXCEL);
    exportWindow.clickExport();
    
    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.EXCEL.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the Excel Quick report was successfully downloaded");
    Assert.assertNotEquals("The selected Quick report was not exported", file, null);
    
    FileInputStream fis = new FileInputStream(file);  
    
    List<String[]> fileContent = new ExcelUtils().getExcelContent(fis);
    List<List<String>> tableContent = quickReportPage.getActiveView().getTableContents();
    
    fileContent.remove(0);
    fileContent.remove(0);
    
    for(int i = 0; i < fileContent.size(); i++) {
      if (fileContent.get(i)[0].matches("[A-Z\\\\s\\\\& ]+")) {
        fileContent.remove(i);
        i--;
        if (i == fileContent.size())
          break;
      }
    }
    
    for(int i = 0; i < fileContent.size(); i++) {
      fileContent.set(i, Arrays
          .copyOfRange(fileContent.get(i), 1, fileContent.get(i).length));
    }
    
    verificationStep("Verify if file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent));
    DownloadUtil.deleteDownloadedFile(file);
  }

  @Test
  public void testCsvDisabled() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.MIAMI_FL_CITY);

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) newViewPage.getActiveView()
        .clickCreate(viewType);
    QuickReportPage quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();

    QuickExportWindow exportWindow = quickReportPage.getToolbar().clickExport();

    logger.info("Verify that the CSV button is disabled");
    Assert.assertFalse("The CSV file format should be disabled",
        exportWindow.isFormatEnabled(FileFormat.CSV));

  }

  @Test
  public void testDbfDisabled() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.MIAMI_FL_CITY);

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) newViewPage.getActiveView()
        .clickCreate(viewType);
    QuickReportPage quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();

    QuickExportWindow exportWindow = quickReportPage.getToolbar().clickExport();

    logger.info("Verify that the DBF button is disabled");
    Assert.assertFalse("The DBF file format should be disabled",
        exportWindow.isFormatEnabled(FileFormat.DBF));

  }
}