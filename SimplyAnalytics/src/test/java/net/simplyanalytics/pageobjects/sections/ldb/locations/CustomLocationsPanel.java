package net.simplyanalytics.pageobjects.sections.ldb.locations;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * .
 *
 * @author wedoqa
 */
public class CustomLocationsPanel extends BasePage {

  @FindBy(xpath = "//div[text()='View your custom locations']/..")
  private WebElement viewCustomLocationButton;
  @FindBy(xpath = "//div[text()='Create new radius location']/..")
  private WebElement createRadiusLocationButton;
  @FindBy(xpath = "//div[text()='Create new combination location']/..")
  private WebElement createCombinationLocationButton;
  @FindBy(xpath = "//div[text()='Create new combination location']/../..")
  private WebElement customLocationContainer;
  
  public CustomLocationsPanel(WebDriver driver) {
    super(driver);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(customLocationContainer, " Custom Location Panel is not loaded");
  }
  
  /**
   * Click on the view custom location.
   * @return RecentFavoriteMenu
   */
  @Step("Click on the view custom location")
  public RecentFavoriteMenu clickViewCustomLocation() {
    logger.debug("Click on the view custom location");
    waitForElementToBeClickable(viewCustomLocationButton,
        "View Custom location button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }
  
  /**
   * Click on the create radius location.
   * @return CreateRadiusLocation
   */
  @Step("Click on the create radius location")
  public CreateRadiusLocation clickCreateRadiusLocation() {
    logger.debug("Click on the create radius location");
    waitForElementToAppear(createRadiusLocationButton,
        "Create radius location button is not displayed").click();
    return new CreateRadiusLocation(driver);
  }
  
  /**
   * Click on the create combination location butto.
   * @return CreateOrEditCombinationLocation
   */
  @Step("Click on the create combination location button")
  public CreateOrEditCombinationLocation clickCreateCombinationLocation() {
    logger.debug("Click on the create combination location button");
    createCombinationLocationButton.click();
    return new CreateOrEditCombinationLocation(driver);
  }
  
  /**
   * Create a new radius location.
   * @param location location
   * @param radiusSize radius size
   * @param radiusUnits radius units
   * @param customRadiusLocationName custom radius location name
   */
  @Step("Create a new radius location")
  public void addRadiusLocation(Location location, int radiusSize, String radiusUnits,
      String customRadiusLocationName) {
    CreateRadiusLocation createRadiusLocation = clickCreateRadiusLocation();
    createRadiusLocation.voidChooseLocation(location);
    createRadiusLocation.enterRadiusSize(radiusSize);
    createRadiusLocation.chooseRadiusUnits(radiusUnits);
    createRadiusLocation.enterRadiusLocationName(customRadiusLocationName);
    createRadiusLocation.clickSave();
  }
  
  /**
   * Create a new combination location.
   * @param combinationName combination name
   * @param location location
   */
  @Step("Create a new combination location")
  public void addCombinationLocation(String combinationName, Location... location) {
    CreateOrEditCombinationLocation combinationLocation = clickCreateCombinationLocation();
    combinationLocation.enterCombinationName(combinationName);
    for (Location location1 : location) {
      combinationLocation.chooseLocation(location1);
    }
    combinationLocation.clickSave();
    sleep(1000);
  }
  
}
