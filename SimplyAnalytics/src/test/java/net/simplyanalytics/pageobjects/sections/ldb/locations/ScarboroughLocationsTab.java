package net.simplyanalytics.pageobjects.sections.ldb.locations;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import io.qameta.allure.Step;

public class ScarboroughLocationsTab extends BaseLocationsTab {
  
  By root = By.xpath(".//div[contains(@id,'scarborough-locations-sidebar')]");
  
  @FindBy(css = "input[placeholder='Filter']")
  private WebElement locationInput;
  
  @FindBy(css = ".sa-sidebar-dma-list")
  private WebElement locationlist;

  public ScarboroughLocationsTab(WebDriver driver) {
    super(driver);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(locationInput, "Location Input is not loaded");
    waitForElementToAppear(locationlist, "Location list");
  }
  
  public EditScarboroughCrosstabPage clickOnTheDMALocation(DMAsLocation location) {
    logger.debug("Click on the DMAs location");
    waitForElementToBeClickable(driver.findElement(By.cssSelector("div[data-locationseries='" + location.getDataName() + "']")), 
        "Location: " + location.getDataName() + "is not clickable")
        .click();
    return new EditScarboroughCrosstabPage(driver);
  }
  
  /**
   * Search and select location.
   * @param searchLetters search letters
   * @param location location
   */
  @Override
  @Step("Search for location with \"{0}\" and select location \"{1}\"")
  public void voidChooseLocation(String searchLetters, String location) {
    logger.debug("Search for location " + searchLetters);
    locationInput.sendKeys(searchLetters);
    waitForElementToDisappear(By.cssSelector(".sa-sidebar-location-results-mask"));
    logger.debug("Select location " + location);
    int x = 0;
    while (x < 5) {
      try {
        waitForElementToAppear(
            driver.findElement(By.xpath("//div[contains(@class,'sa-sidebar-location-result')]"
                + "/span[text()='" + location + "']")),
            "Location element is not present").click();
        break;
      } catch (StaleElementReferenceException e) {
        sleep(500);
        x++;
      }
    }
  }

  @Override
  public void voidChooseLocation(Location location) {
    voidChooseLocation(location.getName(), location.getName());
  }

  @Override
  public MapPage chooseLocation(String searchLetters, String location) {
    voidChooseLocation(searchLetters, location);
    return new MapPage(driver);
  }

  @Override
  public MapPage chooseLocation(Location location) {
    voidChooseLocation(location);
    return new MapPage(driver);
  }

  /**
   * // // // // .
   **/
  @Override
  @Step("Select {2} random locations")
  public void chooseRandomNumberLocations(String searchLetters, int num) {
    logger.debug("Select " + num + " random locations");
    while (num > 0) {

      locationInput.sendKeys(searchLetters);
      List<WebElement> allSearchLocations = waitForAllElementsToAppearByLocator(
          By.cssSelector(".sa-sidebar-location-result"), "Locations did not appear");

      allSearchLocations.get(new Random().nextInt(allSearchLocations.size() - 1)).click();

      num--;
    }
  }
  
}
