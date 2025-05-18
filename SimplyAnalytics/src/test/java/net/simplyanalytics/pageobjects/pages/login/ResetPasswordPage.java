package net.simplyanalytics.pageobjects.pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ResetPasswordPage extends DialogPage {
  @FindBy(id = "reset-password-window")
  private WebElement resetPasswordDialog;
  
  @FindBy(css = "#reset-password-email-input")
  private WebElement emailField;

  @FindBy(css = "button[class='button light-button reset-password-button']")
  private WebElement resetPasswordButton;

  @FindBy(css = "input[class='button light-button dialog-close']")
  private WebElement cancelButton;

  public ResetPasswordPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(emailField, "The email field should appear");
    waitForElementToAppear(resetPasswordButton, "The Reset Password button should appear");
  }

  /**
   * Type email into the Email address field.
   * 
   * @param email Email address
   */
  @Step("Enter email \"{0}\" into the email field")
  public void enterEmail(String email) {
    logger.debug("Enter email into email field: " + email);
    emailField.click();
    emailField.clear();
    emailField.sendKeys(email);
  }

  /**
   * Click on the Reset Password button.
   * 
   * @return DialogPage
   */
  @Step("Click on the Reset Password button")
  public DialogPage clickResetPasswordButton() {
    logger.debug("Click on the Reset Password button");
    resetPasswordButton.click();
    waitForElementToDisappear(root, "The current dialog should disappear");
    return new DialogPage(driver);
  }

  /**
   * Click on the Cancel button.
   * 
   * @return SignInPage
   */
  @Step("Click on the Cancel Button")
  public SignInPage clickCancelButton() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
    waitForElementToDisappear(root, "The current dialog should disappear");
    return new SignInPage(driver);
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
}
