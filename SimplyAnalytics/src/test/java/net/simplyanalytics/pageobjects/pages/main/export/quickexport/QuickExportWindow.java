package net.simplyanalytics.pageobjects.pages.main.export.quickexport;

import java.util.List;

import net.simplyanalytics.enums.ExportLocation;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.MainExport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * .
 *
 * @author wedoqa
 */
public class QuickExportWindow extends MainExport {

  protected static final By ROOT_LOCATOR = By
      .cssSelector("[class*='export-menu'].x-panel-default-framed");
  protected WebElement root;

  @FindBy(css = ".sa-radio")
  private List<WebElement> fileFormats;

  @FindBy(css = ".sa-toggle-switch[class*='sa-toggle-switch'] div")
  private WebElement exportTo;

  @FindBy(css = ".x-form-field.x-form-required-field")
  private WebElement emailTextField;

  @FindBy(css = ".x-form-field.x-form-textarea")
  private WebElement messageTextBox;
  
  @FindBy(css = ".sa-simple-container a")
  private List<WebElement> exportCancelButtons;
  
  @FindBy(css = ".sa-load-mask .sa-load-mask-default .x-border-box")
  private WebElement loadMask;

  public QuickExportWindow(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    this.root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForLoadingToDisappear(driver.findElement(ROOT_LOCATOR));
    waitForElementToBeVisible(By.cssSelector(".sa-radio-group:not([style*=\"display: none\"])"), "Radio buttons are not visible");
  }

  /**
   * // // // // .
   **/

  @Step("Click on the Export button")
  public void clickExport() {
    logger.debug("Click on the Export button");
    waitForAllElementsToAppear(exportCancelButtons, "Export and Cancel buttons are not loaded")
        .get(0).click();

  }

  /**
   * // // // // .
   **/

  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    waitForAllElementsToAppear(exportCancelButtons, "Export and Cancel buttons are not loaded")
        .get(1).click();
    waitForElementToDisappear(root);
  }
  
  /**
   * Click Export to toggle button.
   */
  @Step("Click Export to toggle button")
  public void clickExportTo() {
    logger.debug("Click Export to toggle button");
    waitForElementToAppear(exportTo, "The toggle switch button is not displayed").click();

  }

  /**
   * Enter email address.
   * 
   * @param email email address
   */
  @Step("Enter email: {0}")
  public void enterEMail(String email) {
    logger.debug("Enter email: " + email);
    waitForElementToAppear(emailTextField, "").sendKeys(email);

  }

  /**
   * Enter message.
   * 
   * @param msg message
   */
  @Step("Enter message: {0}")
  private void enterMessage(String msg) {
    logger.debug("Enter message: " + msg);
    waitForElementToAppear(messageTextBox, "").sendKeys(msg);

  }

  /**
   * Checking if format is enabled.
   * @param fileFormat file format
   * @return result
   */
  public boolean isFormatEnabled(FileFormat fileFormat) {
    for (WebElement radioButton : fileFormats) {
      if (radioButton.getText().trim().equals(fileFormat.getName())) {
        return !radioButton.getAttribute("class").contains("x-item-disabled");
      }
    }
    throw new Error("The file format is not present: " + fileFormat.getName());
  }

  /**
   * // // // // .
   **/
  @Step("Choose a file format {0}")
  public void chooseFileFormat(FileFormat fileFormat) {
    logger.debug("Choose a file   : " + fileFormat);
    waitForLoadingToDisappear();
    for (WebElement radioButton : fileFormats) {
      if (radioButton.getText().trim().equals(fileFormat.getName())) {
        radioButton.click();
        return;
      }
    }
    throw new Error("The file format is not present: " + fileFormat.getName());

  }
  

  /**
   * Select export email and enter message.
   * @param email email
   * @param message message
   */
  @Step("Select export email {0} and enter message {1}")
  private void enterEmailExportDetails(String email, String message) {
    clickExportTo();
    enterEMail(email);
    enterMessage(message);
  }

  /**
   * // This method choose a export data type, enters email and message // (if
   * email export location is selected) // and export the data. @return String
   * with which we could populate document // name...
   **/

  @Step("Export {0} to {1} ")
  public void export(ViewType viewType, FileFormat fileFormat, String gmailUser, String message,
      ExportLocation exportLocation) {

    chooseFileFormat(fileFormat);

    if (exportLocation.equals(ExportLocation.EMAIL)) {
      enterEmailExportDetails(gmailUser, message);
    }
    clickExport();

    if (viewType.equals(ViewType.MAP)) {
      throw new AssertionError("Not supported view");
    }
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}
