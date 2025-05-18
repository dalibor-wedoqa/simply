package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import java.util.List;

import net.simplyanalytics.enums.FileFormat;
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

public class ExportWindow extends MainExport {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-vector-map-export-delivery-options-window");

  @FindBy(css = ".sa-vector-map-export-delivery-options-window-header")
  private WebElement dialogTitle;

  @FindBy(css = ".sa-radio.sa-check-field")
  private List<WebElement> fileFormats;

  @FindBy(css = ".sa-toggle-switch-button")
  private WebElement exportTo;

  @FindBy(css = ".sa-vector-map-export-delivery-options-window-field-row input")
  private WebElement emailTextField;

  @FindBy(css = "textarea")
  private WebElement messageTextBox;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Return to Layout']]")
  private WebElement returnToLayout;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Finished']]")
  private WebElement finishedButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;

  @FindBy(css = ".sa-close")
  private WebElement closeButton;

  protected WebElement root;

  public ExportWindow(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(finishedButton, "Export Map window is not loaded");
  }

  public String getTitle() {
    return dialogTitle.getText();
  }

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickClose() {
    logger.debug("Click on the close button (x icon)");
    closeButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Finished button.
   */
  @Step("Click on the Finished button")
  public void clickFinished() {
    logger.debug("Click on the Finished button");
    finishedButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Return to Layout button.
   * 
   * @return LayoutWindow
   */
  @Step("Click on the Return to Layout button")
  public LayoutWindow clickReturnToLayout() {
    logger.debug("Click on the Return to Layout button");
    returnToLayout.click();
    waitForElementToDisappear(root);
    return new LayoutWindow(driver);
  }

  @Step("Click on the Export to toggle button")
  public void clickExportTo() {
    logger.debug("Click on the Export to toggle button");
    waitForElementToAppear(exportTo, "").click();
  }

  @Step("Enter email address: {0}")
  public void enterEMail(String email) {
    logger.debug("Enter email address: " + email);
    waitForElementToAppear(emailTextField, "").sendKeys(email);
  }

  @Step("Enter message: {0}")
  public void enterMessage(String msg) {
    logger.debug("Enter message: " + msg);
    waitForElementToAppear(messageTextBox, "").sendKeys(msg);
  }

  /**
   * Choose a file format.
   * 
   * @param fileFormat file format
   */
  @Step("Choose a file format: {0}")
  public void chooseFileFormat(FileFormat fileFormat) {

    logger.debug("Choose file format " + fileFormat.getName());
    for (WebElement radioButton : fileFormats) {
      if (radioButton.getText().trim().equals(fileFormat.getName())) {
        radioButton.click();
        return;
      }
    }
    throw new Error("The file format is not present: " + fileFormat.getName());
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }
}
