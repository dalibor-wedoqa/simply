package net.simplyanalytics.pageobjects.sections.header.windows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class ChangeEmailAddressWindow extends BasePage {
 
  protected static final By rootLocator = By.cssSelector(".sa-change-email-window");
  protected WebElement root;
  
  @FindBy(xpath = ".//a[contains(@class,'sa-button') and .//span[text()='Continue']]")
  private WebElement continueButton;
  
  @FindBy(xpath = ".//a[contains(@class,'sa-button') and .//span[text()='Cancel']]")
  private WebElement cancelButton;
  
  @FindBy(xpath = ".//div[contains(@class,\"sa-change-email-window-field-row\")][.//div[normalize-space(.)=\"New email address:\"]]//input")
  private WebElement newEmailField;
  
  @FindBy(xpath = ".//div[contains(@class,\"sa-change-email-window-field-row\")][.//div[normalize-space(.)=\"Retype email address:\"]]//input")
  private WebElement retypeEmailField;
  
  @FindBy(xpath = ".//div[contains(@class,\"sa-change-email-window-field-row\")][.//div[normalize-space(.)=\"Current password:\"]]//input")
  private WebElement currentPasswordField;
  
  public ChangeEmailAddressWindow(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }
  
  public void isLoaded() {
    waitForElementToAppear(continueButton, "Continue button is not present");
    waitForElementToBeClickable(cancelButton, "Cancel button is not clickable");
    waitForElementToAppear(newEmailField, "New email address input field is not present");
    waitForElementToAppear(retypeEmailField, "Retype email address input field is not present");
    waitForElementToAppear(currentPasswordField, "Current password input field is not present");
  }
  
  @Step("Enter email address")
  public void enterNewEmailAddress(String emailAddress) {
    logger.debug("Enter new email address");
    newEmailField.clear();
    newEmailField.sendKeys(emailAddress);
  }
  
  @Step("Enter Retype email address")
  public void enterRetypeEmailAddress(String emailAddress) {
    logger.debug("Enter retype email address");
    retypeEmailField.clear();
    retypeEmailField.sendKeys(emailAddress);
  }
  
  @Step("Enter current password")
  public void enterCurrentPassword(String currentPassword) {
    logger.debug("Enter current password");
    currentPasswordField.clear();
    currentPasswordField.sendKeys(currentPassword);
  }
  
  @Step("Click on the Continue button")
  public AlmostDoneDialog clickContinueButton() {
    logger.debug("Click on the Continue button");
    sleep(1000);
    waitForElementToBeClickable(continueButton, "Continue button is not clickable").click();
    waitForElementToDisappear(rootLocator);
    return new AlmostDoneDialog(driver);
  }
  
  @Step("Click on the Cancel button")
  public void clickCancalButton() {
    waitForElementToBeClickable(cancelButton, "Cancel button is not clickable").click();
  }
}
