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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

/**
 * .
 * 
 * @author wedoqa
 */
public class BarChartExportTests extends TestBase {

    private static final ViewType viewType = ViewType.BAR_CHART;
    private final String pngMessage = "PNG Email Message - BarChart"
        + randomNumericStringGenerator.generate(4);

    private final String jpegMessage = "JPEG Email Message - BarChart"
        + randomNumericStringGenerator.generate(4);

    private final String svgMessage = "SVG Email Message - BarChart"
        + randomNumericStringGenerator.generate(4);

    private final String pdfMessage = "PDF Email Message - BarChart"
        + randomNumericStringGenerator.generate(4);
    
    private NewProjectLocationWindow createNewProjectWindow;
    private MapPage mapPage;
    private ExportWindow exportWindow;
    private NewViewPage newViewPage;

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
      
      mapPage = createNewProjectWindow
          .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);
      mapPage.getLdbSection().getLocationsTab().chooseLocation("Mia", "Miami, FL");
      mapPage.getLdbSection().getLocationsTab().chooseLocation("Los", "Los Angeles, CA");
    }
    
    @Test
    public void testPNGEmailExport() throws Exception {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.PNG, GMailUser.EMAIL, pngMessage,
          ExportLocation.EMAIL);
      
      verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
      Assert.assertEquals("Message is missing", 
          "Your export is being processed.You will receive an email shortly.", barChartPage.getAlertMessage());

      String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
      String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

      // verify that email arrives , open and verify that attachment with Bar Chart export
      // is present
      EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
          EmailFolder.INBOX);
      Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
          pngMessage);
      
      verificationStep("Verify that the PNG Bar Chart was successfully exported to email");
      Assert.assertTrue("The selected Bar Chart was not exported",
          emailUtils.isExportPresent(message, FileFormat.PNG, exportName, exportNameVar));
    } 
    
    @Test
    public void testJPEGEmailExport() throws Exception {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.JPEG, GMailUser.EMAIL, jpegMessage,
          ExportLocation.EMAIL);
      
      verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
      Assert.assertEquals("Message is missing", 
          "Your export is being processed.You will receive an email shortly.", barChartPage.getAlertMessage());

      String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
      String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

      // verify that email arrives , open and verify that attachment with Bar Chart export
      // is present
      EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
          EmailFolder.INBOX);
      Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
          jpegMessage);
      
      verificationStep("Verify that the JPEG Bar Chart was successfully exported to email");
      Assert.assertTrue("The selected Bar Chart was not exported",
          emailUtils.isExportPresent(message, FileFormat.JPEG, exportName, exportNameVar));
    } 
    
    @Test
    public void testSVGEmailExport() throws Exception {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.SVG, GMailUser.EMAIL, svgMessage,
          ExportLocation.EMAIL);
      
      verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
      Assert.assertEquals("Message is missing", 
          "Your export is being processed.You will receive an email shortly.", barChartPage.getAlertMessage());

      String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
      String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

      // verify that email arrives, open and verify that attachment with Bar Chart export
      // is present
      EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
          EmailFolder.INBOX);
      Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
          svgMessage);
      
      verificationStep("Verify that the SVG Bar Chart was successfully exported to email");
      Assert.assertTrue("The selected Bar Chart was not exported",
          emailUtils.isExportPresent(message, FileFormat.SVG, exportName, exportNameVar));
    } 
    
    @Test
    public void testPDFEmailExport() throws Exception {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.PDF, GMailUser.EMAIL, pdfMessage,
          ExportLocation.EMAIL);
      
      verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
      Assert.assertEquals("Message is missing", 
          "Your export is being processed.You will receive an email shortly.", barChartPage.getAlertMessage());

      String exportName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
      String exportNameVar = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
          + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

      // verify that email arrives, open and verify that attachment with Bar Chart export
      // is present
      EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
          EmailFolder.INBOX);
      Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
          pdfMessage);
      
      verificationStep("Verify that the PDF Bar Chart was successfully exported to email");
      Assert.assertTrue("The selected Bar Chart was not exported",
          emailUtils.isExportPresent(message, FileFormat.PDF, exportName, exportNameVar));
    } 
    
    @Test
    public void testPNGDownloadExport() {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.PNG, GMailUser.EMAIL, "Email message",
          ExportLocation.COMPUTER);

      verificationStep("Verify that the PNG Bar Chart was successfully downloaded");
      Assert.assertTrue("The selected Bar Chart was not exported", DownloadUtil
          .isFileDownloaded(viewType, FileFormat.PNG.getExtension(), getDownloadFilePath()));
    }
    
    @Test
    public void testJPEGDownloadExport() {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.JPEG, GMailUser.EMAIL, "Email message",
          ExportLocation.COMPUTER);

      verificationStep("Verify that the JPEG Bar Chart was successfully downloaded");
      Assert.assertTrue("The selected Bar Chart was not exported", DownloadUtil
          .isFileDownloaded(viewType, FileFormat.JPEG.getExtension(), getDownloadFilePath()));
    }
    
    @Test
    public void testJSVGDownloadExport() {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.SVG, GMailUser.EMAIL, "Email message",
          ExportLocation.COMPUTER);

      verificationStep("Verify that the SVG Bar Chart was successfully downloaded");
      Assert.assertTrue("The selected Bar Chart was not exported", DownloadUtil
          .isFileDownloaded(viewType, FileFormat.SVG.getExtension(), getDownloadFilePath()));
    }
    
    @Test
    public void testJPDFDownloadExport() {
      newViewPage = mapPage.getViewChooserSection().clickNewView();
      EditBarChartPage editBarChartPage = (EditBarChartPage) newViewPage.getActiveView().clickCreate(viewType);
      BarChartPage barChartPage = (BarChartPage) editBarChartPage.clickDone();

      exportWindow = barChartPage.getToolbar().clickExport();

      exportWindow.export(viewType, FileFormat.PDF, GMailUser.EMAIL, "Email message",
          ExportLocation.COMPUTER);

      verificationStep("Verify that the PDF Bar Chart was successfully downloaded");
      Assert.assertTrue("The selected Bar Chart was not exported", DownloadUtil
          .isFileDownloaded(viewType, FileFormat.PDF.getExtension(), getDownloadFilePath()));
    }
}
