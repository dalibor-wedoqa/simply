package net.simplyanalytics.tests.view.export;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import javax.mail.Message;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.downloadutil.DownloadUtil;
import net.simplyanalytics.enums.BusinessSearchBy;
import net.simplyanalytics.enums.BusinessSearchConditions;
import net.simplyanalytics.enums.ExportLocation;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.SearchConditionRow;
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
 * .
 * 
 * @author wedoqa
 */
public class BusinessesExportTests extends TestBase {

  private static final ViewType viewType = ViewType.BUSINESSES;
  private final String excelMessage = "Excel Email Message - Businesses"
      + randomNumericStringGenerator.generate(4);

  private final String csvMessage = "CSV Email Message - Businesses"
      + randomNumericStringGenerator.generate(4);

  private final String dbfMessage = "DBF Email Message - Businesses"
      + randomNumericStringGenerator.generate(4);

  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;
  private BusinessesPage businessesPage;
  private ExportWindow exportWindow;

  /**
   * Signing in.
   */
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  @Test
  public void testExcelEmailExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    if (businessesPage.getToolbar().isOutOfLimit()) {
      mapPage.addRandomBusiness();
      mapPage.addRandomBusiness();
      mapPage.addRandomBusiness();
      mapPage.addRandomBusiness();
    }

    exportWindow = businessesPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, excelMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", businessesPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        excelMessage);
    
    verificationStep("Verify that the Excel Business was successfully exported to email");
    Assert.assertTrue("The selected Business was not exported",
        emailUtils.isExportPresent(message, FileFormat.EXCEL, exportName, exportNameVar));
    
   InputStream is = new ByteArrayInputStream(emailUtils
        .getAttachmentInByte(message, "name=\\\"New Project_" + viewType.getDefaultName() + "[^\\\\\"]*\\\"([^\\-]+)--"));

    List<String[]> attachmentContent = new ExcelUtils().getExcelContent(is);
    List<List<String>> tableContent = businessesPage.getActiveView().getTableContents();
    attachmentContent.remove(0);
    
    verificationStep("Verify that attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent));
  } 

  @Test
  public void testCsvEmailExport() throws Exception {
    // TODO implement verify for the number of result not to be more than 2500
    // #everywhere
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    //mapPage.addRandomBusiness();
    //mapPage.addRandomBusiness();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExport();
    exportWindow.export(viewType, FileFormat.CSV, GMailUser.EMAIL, csvMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", businessesPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        csvMessage);

    verificationStep("Verify that the CSV Business report was successfully exported to email");
    Assert.assertTrue("The selected Business was not exported",
        emailUtils.isExportPresent(message, FileFormat.CSV, exportName, exportNameVar));
    
    String decodedString = emailUtils
        .getAttachment(message, "name=\\\"New Project_" + viewType.getDefaultName()+ "[^\\\\\"]*\\\"([^\\-]+)--");
    List<String[]> attachmentContent = new CsvUtils().getCsvContent(decodedString);
    
    List<List<String>> tableContent = businessesPage.getActiveView().getTableContents();
    attachmentContent.remove(0);  //header
    
    verificationStep("Verify if attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent));
  }
    
  @Test
  public void testDBFEmailExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.DBF, GMailUser.EMAIL, dbfMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", businessesPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        dbfMessage);

    verificationStep("Verify that the BDF Business report was successfully exported to email");
    Assert.assertTrue("The selected Business was not exported",
        emailUtils.isExportPresent(message, FileFormat.DBF, exportName, exportNameVar));
     
    InputStream is = new ByteArrayInputStream(emailUtils
        .getAttachmentInByte(message, "name=\\\"New Project_" + viewType.getDefaultName() + "[^\\\\\"]*\\\"([^\\-]+)--"));
    
    List<String[]> attachmentContent = new DbfUtils().getDbfContent(is);
    List<List<String>> tableContent = businessesPage.getActiveView().getTableContents();
    attachmentContent.remove(0);
    
    verificationStep("Verify that attachment content is same as table content");
    Assert.assertTrue
      ("Attachment content is different than table content:\n", CompareUtils.compareAttachment(tableContent, attachmentContent));
  }
  
  @Test
  public void testExcelDownloadExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    
    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.EXCEL.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the Excel Business report was successfully downloaded");
    Assert.assertNotEquals("The selected Business was not exported", file, null);
    
    FileInputStream fis = new FileInputStream(file);  
    
    List<String[]> fileContent = new ExcelUtils().getExcelContent(fis);
    List<List<String>> tableContent = businessesPage.getActiveView().getTableContents();
    fileContent.remove(0);
    
    verificationStep("Verify that file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent));
    
    fis.close();
    DownloadUtil.deleteDownloadedFile(file);
  }

  @Test
  public void testCsvDownloadExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.CSV, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.CSV.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the CSV Business report was successfully downloaded");
    Assert.assertNotEquals("The selected Business report was not exported", file, null);
    
    List<String[]> fileContent = new CsvUtils().getCsvContent(file);
    List<List<String>> tableContent = businessesPage.getActiveView().getTableContents();
    
    fileContent.remove(0);  //header
    
    verificationStep("Verify that file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent));
    
    DownloadUtil.deleteDownloadedFile(file);
  }

  @Test
  public void testDbfDownloadExport() throws Exception {

    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();
    mapPage.addRandomBusiness();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.DBF, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.DBF.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the DBF Business report was successfully downloaded");
    Assert.assertNotEquals("The selected Business was not exported", file, null);
    
    FileInputStream fis = new FileInputStream(file);  
    
    List<String[]> fileContent = new DbfUtils().getDbfContent(fis);
    List<List<String>> tableContent = businessesPage.getActiveView().getTableContents();
    
    fileContent.remove(0);
    
    verificationStep("Verify that file content is same as table content");
    Assert.assertTrue
      ("File content is different than table content:\n", CompareUtils.compareAttachment(tableContent, fileContent));
    
    fis.close();
    DownloadUtil.deleteDownloadedFile(file);
  }
  
  @Test
  public void testExportEmptyTable() {
    BusinessSearchBy searchBy = BusinessSearchBy.NAICS;
    
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    
    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();

    AdvancedBusinessSearchWindow advancedBusinessSearchWindow = businessesTab
        .clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(searchBy);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    searchConditionRow.enterSearchValue("111120");
    advancedBusinessSearchWindow.clickSearch();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(viewType.getDefaultName());

    exportWindow = businessesPage.getToolbar().clickExportForEmptyTable();
    
    verificationStep("Verify that togger switch button is disabled");
    Assert.assertFalse("Togger switch button is enabled", exportWindow.isExportToEnabled());
    
    verificationStep("Verify that Export button is disabled");
    Assert.assertFalse("Export button is enabled", exportWindow.isExportButtonEnabled());

    verificationStep("Verify that Cancel button is enabled");
    Assert.assertTrue("Cancel button is disabled", exportWindow.isCancelButtonEnabled());

  }
}