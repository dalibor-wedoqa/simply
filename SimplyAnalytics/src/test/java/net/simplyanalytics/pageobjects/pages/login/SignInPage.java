package net.simplyanalytics.pageobjects.pages.login;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.FindBys;
import io.qameta.allure.Step;

public class SignInPage extends BasePage {

  @FindBy(css = "#user-sign-in-email-input")
  private WebElement emailAdressElement;

  @FindBy(css = "#user-sign-in-password-input")
  private WebElement passwordElement;

  @FindBys({
          @FindBy(css = "button[class='button dark-button sign-in-button'"),
          @FindBy(xpath = "(//*[text()='Sign In'])[1]")
  })
  private WebElement signInButtonElement;

  @FindBy(css = "a.create-account-link")
  private WebElement createAnAccountElement;

  @FindBy(css = "a.reset-password-link")
  private WebElement resetPassElement;

  @FindBy(css = "div.user-sign-in-guest-section")
  private WebElement signInAsGuestElement;

  @FindBy(xpath = "//h4[text()='Sign in with your account']")
  private WebElement signInDisplayText;

  //@FindBy(xpath = "//h4[text()='Low Resolution']")
  //@FindBy(css = "#small-screen-dialog")
  @FindBy(xpath = "//div[@id='small-screen-dialog']")
  private WebElement signInLowResolutionError;

  @FindBy(css = "input.dialog-close")
  private WebElement dialogCloseOkButton;

  public SignInPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {

//    sleep(500);
//    logger.debug(String.valueOf(isPresent(signInLowResolutionError)));
//    if(!isPresent(signInLowResolutionError)) {
//      logger.debug("Low resolution error not present.");
//      waitForElementToAppear(signInDisplayText, "Sign In Page is not loaded");
//      sleep(500);
//    } else {
//      logger.debug("Low resolution error appeared, closing the dialog");
//      dialogCloseOkButton.click();
//      waitForElementToAppear(signInDisplayText, "Sign In Page is not loaded");
//
////      // TODO we don't want to type password into email field, do we?
//      sleep(500);
//    }
//
////    logger.debug("if appeared or not");
////    waitForElementToAppear(signInDisplayText, "Sign In Page is not loaded");
////
////    // TODO we don't want to type password into email field, do we?
////    sleep(500);
  }

  /**
   * Type email into the Email address field.
   *
   * @param email Email address
   */
  @Step("Enter email \"{0}\"")
  public void enterEmail(String email) {
    logger.debug("Enter email: " + email);
    emailAdressElement.click();
    emailAdressElement.clear();
    emailAdressElement.sendKeys(email);
  }

  public String getEmail() {
    return emailAdressElement.getAttribute("value");
  }

  /**
   * Type password into the password field.
   *
   * @param pass Password
   */
  @Step("Enter password \"{0}\"")
  public void enterPass(String pass) {
    logger.debug("Enter password: " + pass);
    passwordElement.click();
    passwordElement.clear();
    passwordElement.sendKeys(pass);
  }

  public String getPassword() {
    return passwordElement.getAttribute("value");
  }

  public WelcomeScreenTutorialWindow clickSignIn() {
    clickVoidSignIn();
    return new WelcomeScreenTutorialWindow(driver);
  }

  @Step("Click on the Sign In button")
  public void clickVoidSignIn() {
    logger.debug("Click on the Sign In button");
    signInButtonElement.click();
  }

  /**
   * Click on the Reset Password link.
   *
   * @return ResetPasswordPage
   */
  @Step("Click on the Reset Password link")
  public ResetPasswordPage clickResetPassword() {
    logger.debug("Click on the Reset Password link");
    resetPassElement.click();
    return new ResetPasswordPage(driver);
  }

  /**
   * Click on the Create Account link.
   *
   * @return CreateAccountPage
   */
  @Step("Click on the Create Account link")
  public CreateAccountPage clickCreateAccount() {
    logger.debug("Click on the Create Account link");
    createAnAccountElement.click();
    return new CreateAccountPage(driver);
  }

  /**
   * Click on the Sign in as guest option.
   *
   * @return WelcomeScreenTutorialWindow
   */
  public WelcomeScreenTutorialWindow clickSignInAsGuest() {
    allureStep("Click on the Sign in as guest option");
    System.out.println("Wait for element");
    waitForElementToAppear(signInAsGuestElement, "Sign in As Guest Element did not appear");
    signInAsGuestElement.click();
    clickVoidSignIn();
    waitForPageLoadedShort();
    return new WelcomeScreenTutorialWindow(driver);
  }

  /**
   * Sign in to SimplyanAlytics.
   *
   * @param user     Username
   * @param password Password
   */
  @Step("Sign in with \"{0}\"/\"{1}\"")
  private void voidSignIn(String user, String password) {
    enterEmail(user);
    enterPass(password);
    clickVoidSignIn();
  }

  /**
   * Sign in to SimplyanAlytics.
   *
   * @param user     Username
   * @param password Password
   */
  public MainPage signIn(String user, String password) {
    voidSignIn(user, password);
   // waitForLoadingToDisappear(2);
    waitForElementToDisappear(By.cssSelector("div.sa-boxed-spinner"));

    ViewType topViewType = new ViewChooserSection(driver).getTopViewType();

    switch (topViewType) {
      case BUSINESSES:
        return new BusinessesPage(driver);
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case MAP:
        return new MapPage(driver);
      case QUICK_REPORT:
        return new QuickReportPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case RELATED_DATA:
        return new RelatedDataReportPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case CROSSTAB_TABLE:
        return new CrosstabPage(driver);
      case SCATTER_PLOT:
        return new ScatterPlotPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      case SCARBOROUGH_CROSSTAB_TABLE:
        return new ScarboroughCrosstabPage(driver);
      default:
        return new NewViewPage(driver);
    }
  }

  public void signInVoid(String user, String password) {
    voidSignIn(user, password);
  }

  public SignInPage failedSignIn(String user, String password) {
    voidSignIn(user, password);
    return new SignInPage(driver);
  }

  public String getMessage() {
    return waitForElementToBeVisible(By.cssSelector(".error"), "message should be visible").getText();
  }

}
