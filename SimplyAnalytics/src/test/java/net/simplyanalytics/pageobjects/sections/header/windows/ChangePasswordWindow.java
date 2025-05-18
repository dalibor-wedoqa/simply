package net.simplyanalytics.pageobjects.sections.header.windows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class ChangePasswordWindow extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-change-password-window");
  protected WebElement root;
  
  @FindBy(xpath = ".//a[contains(@class,'sa-button') and .//span[text()='Save']]")
  private WebElement saveButton;
  
  @FindBy(xpath = ".//a[contains(@class,'sa-button') and .//span[text()='Cancel']]")
  private WebElement cancelButton;
  
  @FindBy(xpath = ".//div[contains(@class,\"sa-change-password-window-field-row\")][.//div[normalize-space(.)=\"Old password:\"]]//input")
  private WebElement oldPasswordField;
  
  @FindBy(xpath = ".//div[contains(@class,\"sa-change-password-window-field-row\")][.//div[normalize-space(.)=\"New password:\"]]//input")
  private WebElement newPasswordField;
  
  @FindBy(xpath = ".//div[contains(@class,\"sa-change-password-window-field-row\")][.//div[normalize-space(.)=\"Retype new password:\"]]//input")
  private WebElement retypeNewPasswordField;
  
  public ChangePasswordWindow(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }
  
  public void isLoaded() {
    waitForElementToAppear(saveButton, "Save button is not present");
    waitForElementToBeClickable(cancelButton, "Cancel button is not clickable");
    waitForElementToAppear(oldPasswordField, "Old password input field is not present");
    waitForElementToAppear(newPasswordField, "New password address input field is not present");
    waitForElementToAppear(retypeNewPasswordField, "Retype new password input field is not present");
  }
  
  @Step("Enter old password")
  public void enterOldPassword(String oldPassword) {
    logger.debug("Enter old password");
    oldPasswordField.clear();
    oldPasswordField.sendKeys(oldPassword);
  }
  
  @Step("Enter new password")
  public void enterNewPassword(String newPassword) {
    logger.debug("Enter new password");
    newPasswordField.clear();
    newPasswordField.sendKeys(newPassword);
  }
  
  @Step("Retype new password")
  public void retypeNewPassword(String newPassword) {
    logger.debug("Retype new password");
    retypeNewPasswordField.clear();
    retypeNewPasswordField.sendKeys(newPassword);
  }
  
  @Step("Click on the Save button")
  public void clickSaveButton() {
    logger.debug("Click on the Save button");
    sleep(1000);
    waitForElementToBeClickable(saveButton, "Save button is not clickable").click();
    waitForElementToDisappear(rootLocator);
  }
  
  @Step("Click on the Cancel button")
  public void clickCancalButton() {
    waitForElementToBeClickable(cancelButton, "Cancel button is not clickable").click();
  }
  
}
