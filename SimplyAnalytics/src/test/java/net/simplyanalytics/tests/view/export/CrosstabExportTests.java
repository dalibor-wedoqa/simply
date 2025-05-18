package net.simplyanalytics.tests.view.export;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javax.mail.Message;

import net.simplyanalytics.core.parallel.SingleTestRunner;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.downloadutil.DownloadUtil;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.ExportLocation;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxCrosstabContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CrosstabExportTests extends TestBase {
    
  private static final ViewType viewType = ViewType.CROSSTAB_TABLE;
  
  private final String excelMessage = "Excel Email Message - Crosstab Table "
      + randomNumericStringGenerator.generate(4);

  private AuthenticateInstitutionPage institutionPage;
  private SignInPage signInPage;
  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;
  private NewProjectLocationWindow createNewProjectWindow;
  
  private EditCrosstabPage editCrosstabPage;
  private MapPage mapPage;

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
    
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.WASHINGTON_DC_CITY);

    //ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    //projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();


  }
  
  @Test
  public void testExcelEmailExport() throws Exception {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSING);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.INCOME);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.EDUCATION);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);
    
    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
    for(int i = 0; i < 40; i++)
      checkbowContainerPanel.clickRowDataItem(i);
    
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    ExportWindow exportWindow = crosstabPage.getToolbar().clickExport();
    
    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, excelMessage,
        ExportLocation.EMAIL);
    
    verificationStep("Verify that 'Your export is being processed. You will receive an email shortly.' message appears");
    Assert.assertEquals("Message is missing", 
        "Your export is being processed.You will receive an email shortly.", crosstabPage.getAlertMessage());

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

    verificationStep("Verify that the Excel Crosstab Table was successfully exported to email");
    Assert.assertTrue("The Crosstab Table was not exported",
        emailUtils.isExportPresent(message, FileFormat.EXCEL, exportName, exportNameVar));
  }
  
  @Test
  public void testExcelDownloadExport() throws Exception {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSING);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.INCOME);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.EDUCATION);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);
    
    CheckboxCrosstabContainerPanel checkbowContainerPanel = editCrosstabPage.getActiveView().getDataPanel();
    for(int i = 0; i < 40; i++)
      checkbowContainerPanel.clickRowDataItem(i);
    
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    ExportWindow exportWindow = crosstabPage.getToolbar().clickExport();
    
    exportWindow.export(viewType, FileFormat.EXCEL, GMailUser.EMAIL, "Email message",
        ExportLocation.COMPUTER);

    logger.info("Get downloaded file");
    File file = DownloadUtil
          .getDownloadedFile(viewType, FileFormat.EXCEL.getExtension(), getDownloadFilePath());
    
    verificationStep("Verify that the Excel Crosstab Table was successfully downloaded");
    Assert.assertNotEquals("The selected Crosstab Table was not exported", file, null);
    
    DownloadUtil.deleteDownloadedFile(file);
  }

  @Test
  public void testDbfDisabled() {
    System.out.println("Step: Navigate to NewViewPage using the View Chooser section");
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    System.out.println("Navigated to NewViewPage");

    System.out.println("Step: Create EditCrosstabPage from the active view");
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    System.out.println("EditCrosstabPage created successfully");

    System.out.println("Step: Verify that the error \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" appears");
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear", editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    System.out.println("Verified: Error message is displayed");
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
            editCrosstabPage.getActiveView().getErrorMessage());
    System.out.println("Verified: Error message matches expected value");

    System.out.println("Step: Open DataByCategoryPanel");
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
            .clickData().getBrowsePanel();

    System.out.println("Step: Select data from the AGE category");
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.AGE);
    for (int i = 0; i < 10; i++) {
      System.out.println("Selecting random data from AGE category, iteration: " + i);
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Step: Select data from the TECHNOLOGY category");
    dataByCategoryDropwDown = dataByCategoryPanel
            .clickOnACategoryData(CategoryData.TECHNOLOGY);
    for (int i = 0; i < 3; i++) {
      System.out.println("Selecting random data from TECHNOLOGY category, iteration: " + i);
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    System.out.println("Step: Close DataByCategoryDropwDown and navigate to EditCrosstabPage");
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    System.out.println("Step: Wait for the Done button state (enabled or disabled)");
    WebDriverWait wait = new WebDriverWait(driver, 10);
    By enabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box");
    By disabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box.x-item-disabled");

    try {
      wait.until(ExpectedConditions.or(
              ExpectedConditions.presenceOfElementLocated(enabledDoneButton),
              ExpectedConditions.presenceOfElementLocated(disabledDoneButton)
      ));
      if (driver.findElements(disabledDoneButton).size() > 0) {
        System.out.println("Done button is disabled; selecting random row elements");
        List<WebElement> rowElements = driver.findElements(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap>.sa-check-button"));
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
          System.out.println("Selecting random row element, iteration: " + i);
          rowElements.get(rand.nextInt(rowElements.size())).click();
        }
      } else {
        System.out.println("Done button is enabled; proceeding to click Done");
      }
    } catch (TimeoutException e) {
      System.out.println("Timed out waiting for the Done button state.");
    }

    System.out.println("Step: Click Done and navigate to CrosstabPage");
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();

    System.out.println("Step: Open ExportWindow from the toolbar");
    ExportWindow exportWindow = crosstabPage.getToolbar().clickExport();

    System.out.println("Step: Verify that the DBF button is disabled");
    logger.info("Verify that the DBF button is disabled");
    boolean isDbfEnabled = exportWindow.isFormatEnabled(FileFormat.DBF);
    System.out.println("DBF format enabled state: " + isDbfEnabled);
    Assert.assertFalse("The DBF file format should be disabled", isDbfEnabled);
    System.out.println("Verified: The DBF file format is disabled");
  }

  
  @Test
  public void testCsvDisabled() {

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }

    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.TECHNOLOGY);
    for(int i = 0; i < 3; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

    WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout

    By enabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box");
    By disabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box.x-item-disabled");

    try {
      wait.until(ExpectedConditions.or(
              ExpectedConditions.presenceOfElementLocated(enabledDoneButton),
              ExpectedConditions.presenceOfElementLocated(disabledDoneButton)
      ));

      if (driver.findElements(disabledDoneButton).size() > 0) {
        logger.debug("No row elements are selected, selecting random row elements");
        List<WebElement> rowElements = driver.findElements(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap>.sa-check-button"));
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
          rowElements.get(rand.nextInt(rowElements.size())).click();
        }
      } else {
        logger.debug("Row elements selected, clicking on the Done button");
      }
    } catch (TimeoutException e) {
      logger.debug("Timed out waiting for the Done button state.");
    }

    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    ExportWindow exportWindow = crosstabPage.getToolbar().clickExport();

    logger.info("Verify that the DBF button is disabled");
    Assert.assertFalse("The DBF file format should be disabled",
        exportWindow.isFormatEnabled(FileFormat.CSV));
  }
}

