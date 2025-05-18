package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class WelcomeScreenTutorialWindow extends BasePage {

  private static final int WAIT_TIME_FOR_TUTORIAL = 90;
  private static final String classAttributeWithNumber = "sa-tutorial-slide-";

  @FindBy(css = ".sa-tutorial")
  private WebElement welcomeScreenWindow;

  @FindBy(css = "button.sa-tutorial-next-button")
  private WebElement nextButton;

  @FindBy(css = "button.sa-tutorial-back-button")
  private WebElement backButton;

  @FindBy(css = "button.sa-tutorial-close-button")
  private WebElement closeXButton;

  //NB Added locators
  @FindBy(css = ".sa-page-header-username-button")
  private WebElement getGuestButton;

  @FindBy(css = ".x-box-item:nth-child(7)")
  private WebElement getSignOutButton;

  @FindBy(css = ".sa-new-project-window")
  private WebElement newProjectWindow;

  //NB end of added locators

  private By activeSlideLocator = By.cssSelector("div.sa-tutorial-slide-active");

  private By goToSimplyAnalyticsLocator = By.cssSelector("button.sa-tutorial-done-button");

  private By replayLocator = By.cssSelector("button.sa-tutorial-replay-button");

  public WelcomeScreenTutorialWindow(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppearWithCustomTime(welcomeScreenWindow,
        "The welcome screen tutorial dialog should appear", WAIT_TIME_FOR_TUTORIAL);
  }

  //NB Added Code start
  public void clickOnTheGuestButton(){
    logger.debug("Click on the Guest button to open a dropdown menu");
    getGuestButton.click();
  }
  public void clickOnTheSignOutButton(){
    logger.debug("Click on the Sing out button");
    getSignOutButton.click();
  }
  //NB end of Added code
  /**
   * // // // // .
   **/

  @Step("Click on the Next button")
  public void clickNext() {
    logger.debug("Click on the Next button");
    waitForElementToAppear(nextButton, "The Next button should appear");
    nextButton.click();
  }

  /**
   * // // // // .
   **/

  @Step("Click on the Back button")
  public void clickBack() {
    logger.debug("Click on the Back button");
    waitForElementToAppear(backButton, "The Back button should appear");
    backButton.click();
  }

  /**
   * // // // // .
   **/

  @Step("Click on the close button")
  public NewProjectLocationWindow clickClose() {
    logger.debug("Click on the close button");
    waitForElementToAppear(closeXButton, "The close button should appear");
    closeXButton.click();
    if ((newProjectWindow.isDisplayed())) {
      return new NewProjectLocationWindow(driver);
    }
    waitForElementToDisappear(welcomeScreenWindow, "The Welcome Screen window should disappear");
    return new NewProjectLocationWindow(driver);
  }

  /**
   * // // // // .
   **/

  @Step("Click on the Go to SimplyAnalytics button")
  public NewProjectLocationWindow clickGoToSimplyAnalyticsButton() {
    logger.debug("Click on the Go to SimplyAnalytics button");
    driver.findElement(goToSimplyAnalyticsLocator).click();
    return new NewProjectLocationWindow(driver);
  }

  @Step("Click on the Reply Tutorial")
  public void clickReplayTutorial() {
    logger.debug("Click on the Reply Tutorial");
    driver.findElement(replayLocator).click();
  }

  /**
   * Getting slide title.
   * @return slide title
   */
  public String getSlideTitle() {
    WebElement slide = driver
        .findElement(By.cssSelector(".sa-tutorial-slide-navless[class*='slide-1'] h3"));
    return slide.getText();
  }

  /**
   * // // // // .
   **/
  public String getActiveSlideTitle() {
    WebElement activeSlide = waitForElementToAppearWithCustomTime(activeSlideLocator,
        "The active slide should be visible", WAIT_TIME_FOR_TUTORIAL);
    WebElement slideTitle = activeSlide.findElement(By.cssSelector("h3.sa-tutorial-title"));
    waitForElementToAppearWithCustomTime(slideTitle, "The title should be visible",
        WAIT_TIME_FOR_TUTORIAL);
    return slideTitle.getText();
  }

  /**
   * // // // // .
   **/

  public void waitForSlideWithGivenNumberToAppear(int slideNumber) {
    waitForElementToAppearByLocator(By.cssSelector("." + classAttributeWithNumber + slideNumber),
        "Slide with number " + slideNumber + " didn't appeared");

  }

  public boolean welcomeScreenWindowIsDisplayed() {
    return isPresent(welcomeScreenWindow);
  }

}
