package net.simplyanalytics.tests.view.export;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.mail.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

public class ScatterPlotExportTests extends TestBase {

  private final String pdfMessage = "PDF Email Message - Scatter Plot "
      + randomNumericStringGenerator.generate(4);
  private final String pngMessage = "PNG Email Message - Scatter Plot "
      + randomNumericStringGenerator.generate(4);
  private final String jpegMessage = "JPEG Email Message - Scatter Plot "
      + randomNumericStringGenerator.generate(4);
  private final String svgMessage = "SVG Email Message - Scatter Plot "
      + randomNumericStringGenerator.generate(4);
  
  ViewType viewType = ViewType.SCATTER_PLOT;
  ScatterPlotPage scatterPlotPage;
  
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) newViewPage.getActiveView().clickCreate(viewType);
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
  }
  
  @Test
  public void testPNGEmailExport() throws Exception {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PNG, GMailUser.EMAIL, pngMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", scatterPlotPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with Scatter Plot export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        pngMessage);
    
    verificationStep("Verify that the PNG Scatter Plot was successfully exported to email");
    Assert.assertTrue("The selected Scatter Plot was not exported",
        emailUtils.isExportPresent(message, FileFormat.PNG, exportName, exportNameVar));
  } 
  
  @Test
  public void testPDFEmailExport() throws Exception {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PDF, GMailUser.EMAIL, pdfMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", scatterPlotPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with Scatter Plot export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        pdfMessage);
    
    verificationStep("Verify that the PDF Scatter Plot was successfully exported to email");
    Assert.assertTrue("The selected Scatter Plot was not exported",
        emailUtils.isExportPresent(message, FileFormat.PDF, exportName, exportNameVar));
  }
  
  @Test
  public void testJPEGEmailExport() throws Exception {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.JPEG, GMailUser.EMAIL, jpegMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", scatterPlotPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with Scatter Plot export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        jpegMessage);
    
    verificationStep("Verify that the JPEG Scatter Plot was successfully exported to email");
    Assert.assertTrue("The selected Scatter Plot was not exported",
        emailUtils.isExportPresent(message, FileFormat.JPEG, exportName, exportNameVar));
  }
  
  @Test
  public void testSVGEmailExport() throws Exception {
    // Open the export window
    System.out.println("Opening the export window...");
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    // Perform the export action
    System.out.println("Exporting with parameters:");
    System.out.println("View Type: " + viewType);
    System.out.println("File Format: " + FileFormat.SVG);
    System.out.println("Recipient Email: " + GMailUser.EMAIL);
    System.out.println("SVG Message: " + svgMessage);
    exportWindow.export(viewType, FileFormat.SVG, GMailUser.EMAIL, svgMessage, ExportLocation.EMAIL);

    // Verify the alert message after export
    System.out.println("Verifying the alert message after export...");
    String alertMessage = scatterPlotPage.getAlertMessage();
    System.out.println("Actual alert message: " + alertMessage);
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing",
            "Your export is being processed.You will receive an email shortly.", alertMessage);

    // Construct expected export names
    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
            + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
            + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));
    System.out.println("Expected export names:");
    System.out.println("Primary: " + exportName);
    System.out.println("Alternate: " + exportNameVar);

    // Verify email arrival and attachment
    System.out.println("Connecting to email server...");
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co", EmailFolder.INBOX);

    System.out.println("Waiting for email with subject: " + Emails.DATA_EXPORTED.getTitle());
    System.out.println("Looking for message body: " + svgMessage);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(), svgMessage);
    if (message != null) {
      System.out.println("Email received successfully!");
    } else {
      System.out.println("Email not received.");
    }

    // Verify attachment in the email
    System.out.println("Verifying attachment in the email...");
    boolean isExportPresent = emailUtils.isExportPresent(message, FileFormat.SVG, exportName, exportNameVar);
    System.out.println("Is SVG export present in email: " + isExportPresent);
    verificationStep("Verify that the SVG Scatter Plot was successfully exported to email");
    Assert.assertTrue("The selected Scatter Plot was not exported", isExportPresent);
  }

  
  @Test
  public void testPNGDownloadExport() {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PNG, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the PNG Scatter Plot was successfully downloaded");
    Assert.assertTrue("The selected Scatter Plot was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.PNG.getExtension(), getDownloadFilePath()));
  }
  
  @Test
  public void testPDFDownloadExport() {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PDF, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the PDF Scatter Plot was successfully downloaded");
    Assert.assertTrue("The selected Scatter Plot was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.PDF.getExtension(), getDownloadFilePath()));
  }
  
  @Test
  public void testJPEGDownloadExport() {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.JPEG, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the JPEG Scatter Plot was successfully downloaded");
    Assert.assertTrue("The selected Scatter Plot was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.JPEG.getExtension(), getDownloadFilePath()));
  }
  
  @Test
  public void testSVGDownloadExport() {
    ExportWindow exportWindow = scatterPlotPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.SVG, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the SVG Scatter Plot was successfully downloaded");
    Assert.assertTrue("The selected Scatter Plot was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.SVG.getExtension(), getDownloadFilePath()));
  }
  
}
