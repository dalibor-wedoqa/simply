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
import net.simplyanalytics.pageobjects.pages.main.export.quickexport.QuickExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.HistogramPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

public class HistogramExportTests extends TestBase {
	
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private static final ViewType viewType = ViewType.HISTOGRAM;
	
  private final String pngMessage = "PNG Email Message - Histogram"
	 + randomNumericStringGenerator.generate(4);
	  
  private final String jpegMessage = "JPEG Email Message - Histogram"
	 + randomNumericStringGenerator.generate(4);
	  
  private final String svgMessage = "SVG Email Message - Histogram"
	 + randomNumericStringGenerator.generate(4);
	  
  private final String pdfMessage = "PDF Email Message - Histogram"
	 + randomNumericStringGenerator.generate(4);
	  
  private HistogramPage histogramPage;
  private QuickExportWindow exportWindow;

  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(losAngeles);
    histogramPage = (HistogramPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.HISTOGRAM).clickDone();
  }
  
  @Test
  public void testPNGEmailExport() throws Exception {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PNG, GMailUser.EMAIL, pngMessage,
        ExportLocation.EMAIL);
	    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", histogramPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
	    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with Histogram export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co", EmailFolder.INBOX);
	    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        pngMessage);
	    
    verificationStep("Verify that the PNG Histogram was successfully exported to email");
    Assert.assertTrue("The selected Histogram was not exported",
    emailUtils.isExportPresent(message, FileFormat.PNG, exportName, exportNameVar));
  } 
	  
  @Test
  public void testJPEGEmailExport() throws Exception {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.JPEG, GMailUser.EMAIL, jpegMessage,
        ExportLocation.EMAIL);
	    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", histogramPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with Histogram export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        jpegMessage);
	    
    verificationStep("Verify that the JPEG Histogram was successfully exported to email");
    Assert.assertTrue("The selected Histogram was not exported",
        emailUtils.isExportPresent(message, FileFormat.JPEG, exportName, exportNameVar));
  } 
	  
  @Test
  public void testSVGEmailExport() throws Exception {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.SVG, GMailUser.EMAIL, svgMessage,
        ExportLocation.EMAIL);
	    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", histogramPage.getAlertMessage());
	    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives, open and verify that attachment with Histogram export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        svgMessage);
    
    verificationStep("Verify that the SVG Histogram was successfully exported to email");
    Assert.assertTrue("The selected Histogram was not exported",
        emailUtils.isExportPresent(message, FileFormat.SVG, exportName, exportNameVar));
  } 
	  
  @Test
  public void testPDFEmailExport() throws Exception {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PDF, GMailUser.EMAIL, pdfMessage,
        ExportLocation.EMAIL);
	    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", histogramPage.getAlertMessage());

    String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives, open and verify that attachment with Histogram export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        pdfMessage);
	    
    verificationStep("Verify that the PDF Histogram was successfully exported to email");
    Assert.assertTrue("The selected Histogram was not exported",
        emailUtils.isExportPresent(message, FileFormat.PDF, exportName, exportNameVar));
  } 
  
  @Test
  public void testPNGDownloadExport() {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PNG, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the PNG Histogram was successfully downloaded");
    Assert.assertTrue("The selected Histogram was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.PNG.getExtension(), getDownloadFilePath()));
  }
  
  @Test
  public void testJPEGDownloadExport() {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.JPEG, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the JPEG Histogram was successfully downloaded");
    Assert.assertTrue("The selected Histogram was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.JPEG.getExtension(), getDownloadFilePath()));
  }
  
  @Test
  public void testJSVGDownloadExport() {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.SVG, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the SVG Histogram was successfully downloaded");
    Assert.assertTrue("The selected Histogram was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.SVG.getExtension(), getDownloadFilePath()));
  }
  
  @Test
  public void testJPDFDownloadExport() {
    exportWindow = histogramPage.getToolbar().clickExport();

    exportWindow.export(viewType, FileFormat.PDF, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    verificationStep("Verify that the PDF Histogram was successfully downloaded");
    Assert.assertTrue("The selected Histogram was not exported", DownloadUtil
        .isFileDownloaded(viewType, FileFormat.PDF.getExtension(), getDownloadFilePath()));
  }
}
