package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class NewProjectLocationWindow extends BasePage {

  protected static final By root = By.cssSelector(".sa-new-project-locations-window-multi-country");

  @FindBy(css = ".sa-text-field-input")
  private WebElement locationSearchElement;

  @FindBy(css = ".sa-button-with-button-el")
  private WebElement nextButton;

  @FindBy(css = "[class='sa-button sa-menu-button sa-button-arrow x-unselectable sa-button-underline x-border-box']")
  private WebElement activeCounrtyComboBox;
  
  @FindBy(css = ".sa-search-field-recent:not(.sa-text-field-button-hidden)")
  private WebElement recentLocationsButton;
  
  @FindBy(css = ".sa-search-field-favorites:not(.sa-text-field-button-hidden)")
  private WebElement favoriteLocationsButton;

  @FindBy(xpath = "(//button[@class='x-component sa-close x-component-default x-border-box'])[2]")
  private WebElement closeButton;

  public NewProjectLocationWindow(WebDriver driver) {
    super(driver, root);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(nextButton, "Create New Project Window is not loaded");
  }

  public String getActiveCountry() {
    waitForElementToAppear(activeCounrtyComboBox, "The active country combo box should be visible");
    return activeCounrtyComboBox.getText();
  }

  /**
   * Select country.
   * 
   * @param country country name
   */
  public void selectCountry(String country) {
    logger.debug("Choose country " + country);
    waitForElementToAppear(activeCounrtyComboBox, "The active country combo box should be visible");
    activeCounrtyComboBox.click();
    waitForElementToAppearByLocator(
        By.xpath("//div[contains(@class, 'sa-combo-button-menu')]//a/span[normalize-space(.)="
            + xpathSafe(country) + "]"),
        "The country is not present in the dropdown list").click();
  }

  /**
   * Entering search for location.
   * 
   * @param location location for search
   */
  @Step("Enter search for location \"{0}\"")
  private void enterSearchLocation(String location) {
    logger.debug("Enter search for location: " + location);
    locationSearchElement.sendKeys(location);
    // TODO Check that it is necessary or not
    sleep(500);
  }

  /**
   * Click on the Next button.
   * 
   * @return NewProjectVariablesWindow
   */
  @Step("Click on the Next button")
  public NewProjectVariablesWindow clickNextButton() {
    logger.debug("Click on the Next button");
    waitForElementToAppear(nextButton, "Next button is not visible");
    // TODO error on page
    sleep(1000);
    nextButton.click();
    return new NewProjectVariablesWindow(driver);
  }

  /**
   * Select location with name.
   * 
   * @param nameOfLocation name of location
   */
  @Step("Select location with name \"{0}\"")
  private void chooseALocation(String nameOfLocation) {
    logger.debug("Select location with name: " + nameOfLocation);
    waitForElementToDisappear(By.cssSelector(".sa-sidebar-location-results-mask"));

    int x = 0;
    while (x < 5) {
      try {
        waitForElementToBeClickableByLocator(By
            .xpath("//button[@class='sa-sidebar-location-result']//span[text()='"
                + nameOfLocation + "']"),
            "The " + nameOfLocation + " did not appear").click();
        break;
      } catch (StaleElementReferenceException e) {
        sleep(1000);
        x++;
      }
    }
  }

  @Step("Select the first available location")
  private void chooseFirstLocation() {
    logger.debug("Selecting the first available location...");

    // Wait for the sidebar location results mask to disappear
    waitForElementToDisappear(By.cssSelector(".sa-sidebar-location-results-mask"));

    int attempts = 0;
    while (attempts < 5) {
      try {
        WebElement firstLocationButton = waitForElementToBeClickableByLocator(
                By.xpath("(//button[@class='sa-sidebar-location-result'])[1]"),
                "No location appeared"
        );
        firstLocationButton.click();
        logger.debug("Successfully clicked the first location.");
        break;
      } catch (StaleElementReferenceException e) {
        logger.warn("StaleElementReferenceException encountered. Retrying...");
        sleep(1000);
        attempts++;
      }
    }

    if (attempts == 5) {
      logger.error("Failed to select a location after multiple attempts.");
    }
  }


  /**
   * Click on the close button.
   * 
   * @return ManageProjectPage
   */
  @Step("Click on the close button")
  public NewViewPage clickClose() {
    logger.debug("Click on the close button");
    waitForElementToAppear(closeButton, "Close button is not visible");
    waitForElementToBeClickable(closeButton, "Close button is not clicable").click();
    return new NewViewPage(driver);
  }

  /**
   * Create Project without location.
   * 
   * @return ManageProjectPage
   */
  @Step("Create Project without location")
  public NewViewPage clickCreateButtonWithEmptyLocation() {
    NewProjectVariablesWindow newProjectVariablesWindow = clickNextButton();
    // TODO error on page
    //sleep(1000);
    return newProjectVariablesWindow.clickCreateProjectButtonEmptyLocation();
  }

  /**
   * Create Project with location.
   * 
   * @param location location
   * @return NewProjectVariablesWindow
   */
  @Step("Create Project with location \"{0}\"")
  public NewProjectVariablesWindow createNewProjectWithLocation(Location location) {
    enterSearchLocation(location.getName().substring(0, Math.min(location.getName().length(), 12)));
    if (location.getType()==LocationType.NIELSEN_DESIGNATED_MARKETING_AREA) {
      chooseFirstLocation();
    } else {
      chooseALocation(location.getName());
    };
    clickNextButton();
    return new NewProjectVariablesWindow(driver);
  }

  /**
   * Create Project with location.
   * 
   * @param location location
   * @return NewProjectVariablesWindow
   */
  @Step("Create Project with location \"{0}\"")
  private NewProjectVariablesWindow createNewProjectWithState(Location location) {
    enterSearchLocation(location.getName().substring(0, 3));
    chooseALocation(location.getName());
    clickNextButton();
    return new NewProjectVariablesWindow(driver);
  }

  @Step("Create Project with location \"{0}\" and close Got It dialog")
  public MapPage createNewProjectWithStateAndDefaultVariables(Location location) {
    return createNewProjectWithState(location).clickCreateProjectButton();
  }

  @Step("Create Project with location \"{0}\" and close Got It dialog")
  public MapPage createNewProjectWithLocationAndDefaultVariables(Location location) {
    return createNewProjectWithLocation(location).clickCreateProjectButton();
  }

  /**
   * This method is present only for testing the Washington problem with shorter
   * input.
   */
  @Step("Create Project with location \"{0}\"")
  private NewProjectVariablesWindow createNewProjectWithLocationShort(Location location) {
    enterSearchLocation(location.getName().substring(0, Math.min(location.getName().length(), 9)));
    chooseALocation(location.getName());
    clickNextButton();
    return new NewProjectVariablesWindow(driver);
  }

  /**
   * This method is present only for testing the Washington problem with shorter
   * input.
   */
  @Step("Create Project with location \"{0}\" and close Got It dialog")
  public MapPage createNewProjectWithLocationAndDefaultVariablesShort(Location location) {
    return createNewProjectWithLocationShort(location).clickCreateProjectButton();
  }
  
  public boolean isRecentPresent() {
    return isPresent(recentLocationsButton);
  }
  
  @Step("Click on the Recent Locations button")
  public RecentFavoriteMenu clickRecent() {
    logger.debug("Click on the Recent Locations button");
    waitForElementToBeClickable(recentLocationsButton, "Recent Locations button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }
  
  public boolean isFavoritesPresent() {
    for(int i = 0; i<=2; i++) {
      if(isPresent(favoriteLocationsButton)) {
        return true;
      }
      sleep(3000);
    }
    return false;
  }

  @Step("Click on the Favorite Locations button")
  public RecentFavoriteMenu clickFavorites() {
    logger.debug("Click on the Favorite Locations button");
    waitForElementToAppear(favoriteLocationsButton, "Favorite Locations button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }

}
