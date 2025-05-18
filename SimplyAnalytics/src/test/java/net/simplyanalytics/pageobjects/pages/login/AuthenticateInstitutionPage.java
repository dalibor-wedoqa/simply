package net.simplyanalytics.pageobjects.pages.login;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.base.BasePage;

import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectVariablesWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthenticateInstitutionPage extends BasePage {

  private static final String INSTITUTION_LOGIN_URL = "https://test.simplyanalytics.net/login.html?institution";

  @FindBy(css = "input[id='institution-sign-in-name-input']")
  private WebElement institutionInputElement;

  @FindBy(css = "input[id='institution-sign-in-password-input']")
  private WebElement passwordInputElement;

  @FindBy(css = "button[class='button dark-button sign-in-button']")
  private WebElement nextButton;

  //NB Added Locator for element
  @FindBy(css = "button.sa-tutorial-close-button")
  private WebElement getTutorialCloseButton;

  @FindBy(css = "[class=\"sa-text-field sa-text-field-default x-border-box\"] > .sa-text-field-input")
  private WebElement getLoactionInputField;

  @FindBy(css = ".sa-sidebar-location-result:nth-child(2)")
  private WebElement getLosAngelesLocation;

  @FindBy(css = ".sa-button-with-button-el")
  private WebElement getNextButtonProject;

  @FindBy(css = ".sa-button-with-button-el")
  private WebElement getCreateProjectButton;

  //End of added locators

  public AuthenticateInstitutionPage(WebDriver driver) {
    super(driver);
  }

  @Override
  @Step("Navigate to the site")
  public void load() {
    logger.debug("Navigate to the site");
    //driver.get("http://www.google.com");
    sleep(1000);
    driver.get(INSTITUTION_LOGIN_URL);
    sleep(1000);
  }

  @Override
  public void isLoaded() {
    String currentUrl = driver.getCurrentUrl();
    if (currentUrl == null || !currentUrl.equals(INSTITUTION_LOGIN_URL)) {
      throw new Error("The page url is incorrect: " + currentUrl);
    }
    waitForElementToAppear(nextButton, "Sign in button should be present");
    waitForElementToAppear(institutionInputElement, "Institution input field should be present");
    waitForElementToAppear(passwordInputElement, "Password input field should be present");
  }

  //NB added code
  public void clickCloseTutorialButton() {
    logger.debug("Click on the close button");
    waitForElementToAppear(getTutorialCloseButton, "The close button should appear");
    getTutorialCloseButton.click();
  }

  // Method to type "Los Angeles, CA" into the input field and select the location
  public void typeAndSelectLocation() {
    getLoactionInputField.clear();  // Clear any existing text in the input field
    getLoactionInputField.sendKeys("Los Angeles, CA");  // Type the desired location

    // Wait for the location suggestion to be visible and click on it
    WebDriverWait wait = new WebDriverWait(driver, 10);  // You can adjust the timeout as needed
    wait.until(ExpectedConditions.visibilityOf(getLosAngelesLocation)).click();
  }

  public void clickOnNextButton() {
    logger.debug("Click on the Next button");
    waitForElementToAppear(getNextButtonProject, "Next button is not visible");
    getNextButtonProject.click();
  }

  public void clickCreateProjectButton() {
    logger.debug("Click on the Create project button");
    waitForElementToAppear(getCreateProjectButton, "Create project button is not visible");
    getCreateProjectButton.click();
  }

  //NB code Edns Here

  /**
   * Type username into institution name field.
   * @param institution Institution username
   */
  @Step("Type \"{0}\" into institution name field")
  private void enterInstitution(String institution) {
    logger.debug("Type \"" + institution + "\" into Institution username field");
    institutionInputElement.click();
    institutionInputElement.clear();
    institutionInputElement.sendKeys(institution);
  }
  
  /**
   * Type password into institution password field.
   * @param pass password
   */
  @Step("Type \"{0}\" into institution password field")
  private void enterPassword(String pass) {
    logger.debug("Type \"" + pass + "\" into Institution password field");
    passwordInputElement.click();
    passwordInputElement.clear();
    passwordInputElement.sendKeys(pass);
  }

  @Step("Click on the Next button")
  private void signIn() {
    logger.debug("Click on the Next button");
    nextButton.click();
  }

  @Step("Login with institute \"{0}\"/\"{1}\"")
  public SignInPage institutionLogin(String institution, String pass) {
    System.out.println("institution login");
    voidInstitutionLogin(institution, pass);
    System.out.println("done");
    SignInPage signInPage = new SignInPage(driver);
    System.out.println("signin created");
    return new SignInPage(driver);
  }
  
  /**
   * Sign in to institution.
   * @param institution Institution username
   * @param pass Password
   */
  public void voidInstitutionLogin(String institution, String pass) {
    System.out.println("Entering user and pass");
    enterInstitution(institution);
    enterPassword(pass);
    signIn();
  }
  
  /**
   * Try to sign in to institution with incorrect credentials.
   * @param institution Institution username
   * @param pass Password
   * @return Institution Sign-In Page
   */
  @Step("Try to login with institute \"{0}\"/\"{1}\"")
  public AuthenticateInstitutionPage failedInstitutionLogin(String institution, String pass) {
    voidInstitutionLogin(institution, pass);
    waitForElementToAppearWithCustomTime(DialogPage.ROOT_LOCATOR,
        "the error doesn't appear even after 20s", 20);
    return new AuthenticateInstitutionPage(driver);
  }

  public String getMessage() {
    return waitForElementToBeVisible(By.cssSelector(".error"), "message should be visible")
        .getText();
  }

}
