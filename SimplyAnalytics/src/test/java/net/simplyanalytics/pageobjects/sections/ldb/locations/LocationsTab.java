package net.simplyanalytics.pageobjects.sections.ldb.locations;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.sections.ldb.TabWithFavoritesMenuInterface;
import net.simplyanalytics.pageobjects.sections.ldb.TabWithRecentMenuInterface;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * .
 *
 * @author Wedoqa
 */
public class LocationsTab extends BaseLocationsTab
    implements TabWithRecentMenuInterface, TabWithFavoritesMenuInterface {

  @FindBy(xpath = "(//button[@title='Recent Locations'])[1]")
  private WebElement recentLocations;

  @FindBy(xpath = "(//button[@title='Favorite Locations'])[1]")
  private WebElement favoriteLocations;

  @FindBy(css = ".sa-sidebar-section-header-expandable span")
  private WebElement showHide;
  
  @FindBy(xpath = ".//span[.='OK']/../../../div[2]")
  private WebElement maxRadiusMessage;
  
  @FindBy(xpath = ".//span[text() =\"OK\"]/..")
  private WebElement okButtonOnMessage;

  int flag = 6;
  
  public LocationsTab(WebDriver driver) {
    super(driver);
  }

  /**
   * // // // // .
   **/
  @Step("Click on the Show custom location")
  public CustomLocationsPanel clickShowCustomLocations() {
    logger.debug("Click on the Show custom location");
    flag--;
    if (flag == 0) {
      Assert.assertFalse("in loop, terminating case", 1 == 1);
    }
    showHide.click();
    return getCustomLocations();
  }

  /**
   * // // // // .
   **/
  @Step("Click on the Hide custom location")
  public LocationsTab clickHideCustomLocation() {
    logger.debug("Click on the Hide custom location");
    showHide.click();
    return new LocationsTab(driver);
  }

  /**
   * // // // // .
   **/
  public CustomLocationsPanel getCustomLocations() {
    if (isPresent(CustomLocationsPanel.class, driver)) {
      return new CustomLocationsPanel(driver);
    } else {
      return clickShowCustomLocations();
    }
  }

  @Step("Add new custom radius location")
  public void addNewCustomRadiusLocation(Location location, String customRadiusLocationName,
      int radiusSize, String radiusUnits) {
    clickShowCustomLocations().addRadiusLocation(location, radiusSize, radiusUnits,
        customRadiusLocationName);
  }

  @Step("Add new custom combination location")
  public void addNewCustomCombinationLocation(String customCombinationLocationName,
      Location... location) {
    clickShowCustomLocations().addCombinationLocation(customCombinationLocationName, location);
  }
  
  /**
   * Get maximum radius exceeded message.
   * 
   * @return maximum radius exceeded message
   */
  @Step("")
  public String getMaximumRadiusExceededMessage() {
    String maximumRadius = maxRadiusMessage.getText();
    return maximumRadius;
  }
  
  public CreateRadiusLocation clickOkOnMessage() {
    logger.debug("Click OK");
    waitForElementToAppear(okButtonOnMessage, "The OK button is not appeared").click();
    return new CreateRadiusLocation(driver);
  }

  @Override
  public boolean isRecentPresent() {
    return recentLocations.isDisplayed();
  }

  @Override
  @Step("Click on the Recent Locations button")
  public RecentFavoriteMenu clickRecent() {
    logger.debug("Click on the Recent Locations button");
    waitForElementToBeClickable(recentLocations, "Recent Locations button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }

  @Override
  public boolean isFavoritesPresent() {
    for(int i = 0; i<=2; i++) {
      if(favoriteLocations.isDisplayed()) {
        return true;
      }
      sleep(3000);
    }
    return false;
  }

  @Override
  @Step("Click on the Favorite Locations button")
  public RecentFavoriteMenu clickFavorites() {
    logger.debug("Click on the Favorite Locations button");
    waitForElementToAppear(favoriteLocations, "Favorite Locations button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }

}
