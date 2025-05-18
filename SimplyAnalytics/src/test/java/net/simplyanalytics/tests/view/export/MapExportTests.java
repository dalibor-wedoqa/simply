package net.simplyanalytics.tests.view.export;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.mail.Message;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.downloadutil.DownloadUtil;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Orientation;
import net.simplyanalytics.enums.StandardSize;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.Step;

/**
 * .
 *
 * @author wedoqa
 */
/*
 * SA-1045
 */
public class MapExportTests extends TestBase {

  private final String pdfMessage = "PDF Email Message - Map "
      + randomNumericStringGenerator.generate(4);
  private final String pngMessage = "PNG Email Message - Map "
      + randomNumericStringGenerator.generate(4);
  private final String jpegMessage = "JPEG Email Message - Map "
      + randomNumericStringGenerator.generate(4);
  private final String svgMessage = "SVG Email Message - Map "
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

    driver.manage().window().maximize();
    institutionPage = new AuthenticateInstitutionPage(driver);
    signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
  }

  @Test
  public void testPdfDownloadExportMap() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToYourComputer(croppingWindow, StandardSize.TABLOID, Orientation.PORTRAIT,
        StandardSize.LEGAL, Orientation.LANDSCAPE, FileFormat.PDF);

    verificationStep("Verify that the selected map was successfully downloaded");
    Assert.assertTrue("The selected map was not exported", DownloadUtil
        .isFileDownloaded(ViewType.MAP, FileFormat.PDF.getExtension(), getDownloadFilePath()));
  }

  @Test
  public void testPngDownloadExportMap() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToYourComputer(croppingWindow, StandardSize.LEGAL, Orientation.LANDSCAPE,
        StandardSize.LETTER, Orientation.PORTRAIT, FileFormat.PNG);

    verificationStep("Verify that the selected map was successfully downloaded");
    Assert.assertTrue("The selected map was not exported", DownloadUtil
        .isFileDownloaded(ViewType.MAP, FileFormat.PNG.getExtension(), getDownloadFilePath()));
  }

  @Test
  public void testJpegDownloadExportMap() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToYourComputer(croppingWindow, StandardSize.LETTER, Orientation.LANDSCAPE,
        StandardSize.LEGAL, Orientation.PORTRAIT, FileFormat.JPEG);

    verificationStep("Verify that the selected map was successfully downloaded");
    Assert.assertTrue("The selected map was not exported", DownloadUtil
        .isFileDownloaded(ViewType.MAP, FileFormat.JPEG.getExtension(), getDownloadFilePath()));
  }
/* this test is no longer necessary due to vector map 
  @Test
  public void testSvgDownloadExportMap() {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToYourComputer(croppingWindow, StandardSize.LEGAL, Orientation.LANDSCAPE,
        StandardSize.LETTER, Orientation.LANDSCAPE, FileFormat.SVG);

    verificationStep("Verify that the selected map was successfully downloaded ");
    Assert.assertTrue("The selected map was not exported", DownloadUtil
        .isFileDownloaded(ViewType.MAP, FileFormat.SVG.getExtension(), getDownloadFilePath()));
  }
*/
  @Test
  public void testPdfEmailExportMap() throws Exception {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToEmail(croppingWindow, StandardSize.LEGAL, Orientation.LANDSCAPE, StandardSize.LETTER,
        Orientation.LANDSCAPE, FileFormat.PDF, pdfMessage);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", mapPage.getAlertMessage());

    final String exportName = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    final String exportNameVar = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        pdfMessage);

    verificationStep("Verify that the selected map was exported to email");
    Assert.assertTrue("The selected map was not exported",
        emailUtils.isExportPresent(message, FileFormat.PDF, exportName, exportNameVar));
  }
/* this test is no longer necessary due to vector map implementation
  @Test
  public void testSvgEmailExportMap() throws Exception {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToEmail(croppingWindow, StandardSize.TABLOID, Orientation.PORTRAIT, StandardSize.LEGAL,
        Orientation.LANDSCAPE, FileFormat.SVG, svgMessage);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", mapPage.getAlertMessage());

    final String exportName = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    final String exportNameVar = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        svgMessage);

    verificationStep("Verify that the selected map was exported to email");
    Assert.assertTrue("The selected map was not exported",
        emailUtils.isExportPresent(message, FileFormat.SVG, exportName, exportNameVar));
  }
*/
  @Test
  public void testJpegEmailExportMap() throws Exception {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToEmail(croppingWindow, StandardSize.LEGAL, Orientation.LANDSCAPE, StandardSize.LETTER,
        Orientation.PORTRAIT, FileFormat.JPEG, jpegMessage);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", mapPage.getAlertMessage());

    final String exportName = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    final String exportNameVar = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        jpegMessage);

    verificationStep("Verify that the selected map was exported to email");
    Assert.assertTrue("The selected map was not exported",
        emailUtils.isExportPresent(message, FileFormat.JPEG, exportName, exportNameVar));
  }

  @Test
  public void testPngEmailExportMap() throws Exception {

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);

    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();

    exportToEmail(croppingWindow, StandardSize.LETTER, Orientation.LANDSCAPE, StandardSize.LEGAL,
        Orientation.PORTRAIT, FileFormat.PNG, pngMessage);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", mapPage.getAlertMessage());

    final String exportName = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now());
    final String exportNameVar = "New Project_Map_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH").format(LocalTime.now().plusMinutes(1));

    // verify that email arrives , open and verify that attachment with map export
    // is present
    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        pngMessage);

    verificationStep("Verify that the selected map was exported to email");
    Assert.assertTrue("The selected map was not exported",
        emailUtils.isExportPresent(message, FileFormat.PNG, exportName, exportNameVar));
  }

  @Step("Cropping Window add data")
  private LayoutWindow croppingAddData(CroppingWindow croppingWindow, StandardSize standartSize,
      Orientation orientation) {
    croppingWindow.chooseStandardSize(standartSize);
    switch (orientation) {
      case LANDSCAPE:
        croppingWindow.clickLandscape();
        break;
      case PORTRAIT:
        croppingWindow.clickPortrait();
        break;
      default:
        break;
    }
    return croppingWindow.clickContinueToLayout();
  }

  @Step("Layout Window add a data")
  private ExportWindow layoutAddData(LayoutWindow layoutWindow, StandardSize standardSize,
      Orientation orientation) {
    layoutWindow.clickInPixels();
    layoutWindow.chooseStandardSize(standardSize);
    switch (orientation) {
      case LANDSCAPE:
        layoutWindow.clickLandscape();
        break;
      case PORTRAIT:
        layoutWindow.clickPortrait();
        break;
      default:
        break;
    }
    layoutWindow.clickNorthArrowCheckBox();
    layoutWindow.clickInsetMapCheckBox();
    return layoutWindow.clickContinueToExport();
  }

  @Step("Export Map to your Computer")
  private void exportToYourComputer(CroppingWindow croppingWindow,
      StandardSize croppingStandartSize, Orientation croppingOrientation,
      StandardSize layoutStandartSize, Orientation layoutOrientation, FileFormat fileFormat) {
    LayoutWindow layoutWindow = croppingAddData(croppingWindow, croppingStandartSize,
        croppingOrientation);
    ExportWindow exportWindow = layoutAddData(layoutWindow, layoutStandartSize, layoutOrientation);
    exportWindow.chooseFileFormat(fileFormat);
    exportWindow.clickFinished();
  }

  @Step("Export Map to email")
  private void exportToEmail(CroppingWindow croppingWindow, StandardSize croppingStandartSize,
      Orientation croppingOrientation, StandardSize layoutStandartSize,
      Orientation layoutOrientation, FileFormat fileFormat, String emailMessage) {
    LayoutWindow layoutWindow = croppingAddData(croppingWindow, croppingStandartSize,
        croppingOrientation);
    ExportWindow exportWindow = layoutAddData(layoutWindow, layoutStandartSize, layoutOrientation);
    exportWindow.chooseFileFormat(fileFormat);
    exportWindow.clickExportTo();
    exportWindow.enterEMail(GMailUser.EMAIL);
    exportWindow.enterMessage(emailMessage);
    exportWindow.clickFinished();
  }
}
