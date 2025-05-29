package net.simplyanalytics.pageobjects.pages.main.export;

import java.util.List;
import net.simplyanalytics.enums.ExportLocation;
import net.simplyanalytics.enums.FileFormat;
import net.simplyanalytics.enums.ViewType;
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
  
  protected static final By ROOT_LOCATOR = By
      .cssSelector("[class*='arrow-export-menu'].sa-arrow-menu:not([style*='visibility: hidden'])");
  protected WebElement root;
  
  @FindBy(css = ".sa-check-field.sa-radio-default")
  private List<WebElement> fileFormats;
  
  @FindBy(css = ".sa-toggle-switch[class*='export-widget-email-or-save-toggle-switch'] div")
  private WebElement exportTo;
  
  @FindBy(css = ".x-form-field.x-form-required-field")
  private WebElement emailTextField;
  
  @FindBy(css = ".x-form-field.x-form-textarea")
  private WebElement messageTextBox;
  
  @FindBy(css = ".sa-simple-container[class*='export-widget-footer'] button")
  private List<WebElement> exportCancelButtons;
  
  @FindBy(css = ".x-component[class*='sa-beyond-limit-businesses-message']")
  private WebElement limitLocator;
  
  public ExportWindow(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    this.root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    waitForLoadingToDisappear(driver.findElement(ROOT_LOCATOR));
  }
  
  /**
   * Click on the Export button.
   */
  @Step("Click on the Export button")
  private void clickExport() {
    logger.debug("Click on the Export button");

    if (isExportButtonClickable()) {
      System.out.println("DEBBUG:" + exportCancelButtons);
      waitForAllElementsToAppear(exportCancelButtons, "Export and Cancel buttons are not loaded").get(0).click();
    }
  }
  
  public boolean isExportButtonEnabled() {
    return !exportCancelButtons.get(0).getAttribute("class").contains("x-item-disabled");
  }
  
  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    waitForAllElementsToAppear(exportCancelButtons, "Export and Cancel buttons are not loaded").get(1).click();
    waitForElementToDisappear(root);
  }
  
  public boolean isCancelButtonEnabled() {
    return !exportCancelButtons.get(1).getAttribute("class").contains("x-item-disabled");
  }
  
  /**
   * Click Export to toggle button.
   */
  @Step("Click Export to toggle button")
  private void clickExportTo() {
    logger.debug("Click Export to toggle button");
    waitForElementToAppear(exportTo, "The toggle switch button is not displayed").click();
    
  }
  
  public boolean isExportToEnabled() {
    return !driver.findElement(By.cssSelector(".sa-export-widget-email-or-save-toggle-switch"))
        .getAttribute("class").contains("x-item-disabled");
  }
  
  /**
   * Enter email address.
   * 
   * @param email email address
   */
  @Step("Enter email: {0}")
  private void enterEMail(String email) {
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
   * Choose a file format.
   * 
   * @param fileFormat file format
   */
  @Step("Choose a file format {0}")
  private void chooseFileFormat(FileFormat fileFormat) {
    logger.debug("Choose a file   : " + fileFormat);
    for (WebElement radioButton : fileFormats) {
      if (radioButton.findElement(By.cssSelector(".sa-check-field-label")).getText().trim().equals(fileFormat.getName())) {
        radioButton.click();
        return;
      }
    }
    throw new Error("The file format is not present: " + fileFormat.getName());
    
  }
  
  /**
   * Select export email and enter message.
   * 
   * @param email   email
   * @param message message
   */
  @Step("Select export email {0} and enter message {1}")
  private void enterEmailExportDetails(String email, String message) {
    clickExportTo();
    enterEMail(email);
    enterMessage(message);
  }
  
  /**
   * Get limit message.
   * 
   * @return limit message
   */
  public String limitMessage() {
    String limitMessage = waitForElementToAppear(limitLocator, "Message is not available").getText();
    return limitMessage;
  }
  
  /**
   * // This method choose a export data type, enters email and message // (if
   * email export location is selected) // and export the data. @return String
   * with which we could populate document // name...
   **/

  @Step("Export {0} to {1} ")
  public void export(ViewType viewType, FileFormat fileFormat, String gmailUser, String message,
                     ExportLocation exportLocation) {

    System.out.println("Starting export process");
    System.out.println("Selected viewType: " + viewType);
    System.out.println("Selected fileFormat: " + fileFormat);
    System.out.println("Selected exportLocation: " + exportLocation);

    System.out.println("Choosing file format: " + fileFormat);
    chooseFileFormat(fileFormat);

    if (exportLocation.equals(ExportLocation.EMAIL)) {
      System.out.println("Export location is EMAIL. Entering email details.");
      System.out.println("Gmail user: " + gmailUser);
      System.out.println("Email message: " + message);
      enterEmailExportDetails(gmailUser, message);
    }

    System.out.println("Clicking the export button");
    clickExport();

    if (viewType.equals(ViewType.MAP)) {
      System.out.println("Export from MAP view is not supported. Throwing AssertionError.");
      throw new AssertionError("Not supported view");
    }

    System.out.println("Export process completed for viewType: " + viewType + ", format: " + fileFormat + ", location: " + exportLocation);
  }

  
  public boolean isExportButtonClickable() {
    return exportCancelButtons.get(0).isEnabled();
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
  
  public boolean isFormatEnabled(FileFormat fileFormat) {
    for (WebElement radioButton : fileFormats) {
      if (radioButton.getText().trim().equals(fileFormat.getName())) {
        return !radioButton.getAttribute("class").contains("x-item-disabled");
      }
    }
    throw new Error("The file format is not present: " + fileFormat.getName());
  }
}
