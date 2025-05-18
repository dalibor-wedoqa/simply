package net.simplyanalytics.pageobjects.pages.login;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CreateAccountPage extends BasePage {

  @FindBy(css = "#create-account-email-input")
  private WebElement emailField;

  @FindBy(css = "#create-account-password-1-input")
  private WebElement passwordField;

  @FindBy(css = "#create-account-password-2-input")
  private WebElement reenterPasswordField;

  @FindBy(css = "#eula-checkbox")
  private WebElement eulaCheckbox;

  @FindBy(css = "button[class='button light-button create-account-button'")
  private WebElement createAccountButton;

  public CreateAccountPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(emailField, "Email field should appear");
    waitForElementToAppear(passwordField, "Password field should appear");
    waitForElementToAppear(reenterPasswordField, "Re-enter password field should appear");
    waitForElementToAppear(eulaCheckbox, "EULA checkbox should appear");
    waitForElementToAppear(createAccountButton, "The Create Account button should appear");
  }
  
  /**
   * Type email into the Email address field.
   * @param email Email address
   */
  @Step("Enter email \"{0}\"")
  public void enterEmail(String email) {
    logger.debug("Enter email: " + email);
    emailField.click();
    emailField.clear();
    emailField.sendKeys(email);
  }
  
  /**
   * Type password into the password field.
   * @param password Password
   */
  @Step("Enter password \"{0}\"")
  public void enterPassword(String password) {
    logger.debug("Enter password: " + password);
    passwordField.click();
    passwordField.clear();
    passwordField.sendKeys(password);
  }
  
  /**
   * Reenter password into the Re-enter password field.
   * @param password Password
   */
  @Step("Reenter password \"{0}\"")
  public void enterPasswordAgain(String password) {
    logger.debug("Reenter password: " + password);
    reenterPasswordField.click();
    reenterPasswordField.clear();
    reenterPasswordField.sendKeys(password);
  }

  @Step("Click on the EULA checkbox")
  public void checkEula() {
    logger.debug("Click on the EULA checkbox");
    eulaCheckbox.click();
  }
  
  /**
   * Click on the Create button. 
   * @return DialogPage
   */
  @Step("Click on the Create button")
  public DialogPage clickCreateAccount() {
    logger.debug("Click on the Create button");
    createAccountButton.click();
    return new DialogPage(driver);
  }

}
