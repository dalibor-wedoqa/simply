package net.simplyanalytics.tests.activeview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.CrosstabViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CrosstabActiveViewTests extends TestBase {
  
  private EditCrosstabPage editCrosstabPage;
  private MapPage mapPage;
  
  @Before
  public void createProject() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);

    //ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    //projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
  }
  
  @Test
  @DisplayName("Verify that in Simmons Crosstab view table the colums can be moved to rows and vice versa")
  @Description("The test creates a Simmons Crosstab view and moves a column to rows, verifies that it was moved and then moves it back and verifies again.")
  @Tag("Flaky")
  public void testDragAndDropRowToColumn() {
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!column.contains(data))
      column.add(data);
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.TECHNOLOGY);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      String data = dataByCategoryDropwDown.clickOnARandomDataResult();
      if (!row.contains(data))
      row.add(data);
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
        for (int i = 0; i < 3; i++) {
          rowElements.get(rand.nextInt(rowElements.size())).click();
        }
      } else {
        logger.debug("Row elements selected, clicking on the Done button");
      }
    } catch (TimeoutException e) {
      logger.debug("Timed out waiting for the Done button state.");
    }
    CrosstabPage crosstabPage = (CrosstabPage) editCrosstabPage.clickDone();
    
    CrosstabViewPanel viewPanel = crosstabPage.getActiveView();
    
    column.remove(column.size()-1);
    
    for(String columnElement : column) {
      viewPanel.moveColumnElement(columnElement, -300, 150);
      row.add(0, columnElement);
    }
    logger.info("Elements are moved");
    verificationStep("Verify that there is only one column header value");
    Assert.assertEquals("Should be only one column header value", viewPanel.getColumnHeaderValues().size(), 2);
      
    row.remove(row.size()-1);
    
    for(String rowElement : row) {
      viewPanel.moveRowElement(rowElement, 300, -150);
    }
    logger.info("Elements are moved");
    verificationStep("Verify that there is only one row header value");
    Assert.assertEquals("Should be only one row header value", viewPanel.getRowHeaderValues().size(), 2);
  }
  
  @Test
  public void testDataLimitMessage() throws Exception {
//    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
//    editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(ViewType.CROSSTAB_TABLE);
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editCrosstabPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE,
        editCrosstabPage.getActiveView().getErrorMessage());
    
    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSING);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HOUSEHOLDS);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.INCOME);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.RACE_AND_ETHNICITY);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.AGE);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.EDUCATION);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.TECHNOLOGY);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.FAMILY_TYPE);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.HEALTH);
    for(int i = 0; i < 12; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.FINANCE);
    for(int i = 0; i < 12; i++) {
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
        for (int i = 0; i < 45; i++) {
          rowElements.get(rand.nextInt(rowElements.size())).click();
        }
      } else {
        logger.debug("Row elements selected, clicking on the Done button");
      }
    } catch (TimeoutException e) {
      logger.debug("Timed out waiting for the Done button state.");
    }
    verificationStep("Verify that the \"" + EditViewWarning.CROSSTABLIMITMESSAGE + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editCrosstabPage.getActiveView().isLimitErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.CROSSTABLIMITMESSAGE,
        editCrosstabPage.getActiveView().getLimitErrorMessage());
  }

}
