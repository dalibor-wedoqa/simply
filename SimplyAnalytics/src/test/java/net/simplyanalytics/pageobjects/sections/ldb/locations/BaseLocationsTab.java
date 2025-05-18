package net.simplyanalytics.pageobjects.sections.ldb.locations;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import io.qameta.allure.Step;

public class BaseLocationsTab extends BasePage {
  
  @FindBy(css = "input[placeholder='Location Search']")
  private WebElement locationInput;
  
  public BaseLocationsTab(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(locationInput, "Location Input is not loaded");
  }

  /**
   * Search and select location.
   * @param searchLetters search letters
   * @param location location
   */
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
            driver.findElement(By.xpath("//button[contains(@class,'sa-sidebar-location-result')]"
                + "/span[text()='" + location + "']")),
            "Location element is not present").click();
        break;
      } catch (StaleElementReferenceException e) {
        sleep(500);
        x++;
      }
    }
  }

  public void voidChooseLocation(Location location) {
    voidChooseLocation(location.getName(), location.getName());
  }

  public MapPage chooseLocation(String searchLetters, String location) {
    voidChooseLocation(searchLetters, location);
    return new MapPage(driver);
  }

  public MapPage chooseLocation(Location location) {
    voidChooseLocation(location);
    return new MapPage(driver);
  }
  
  public BaseViewPage chooseLocation(Location location, ViewType view) {
    voidChooseLocation(location);
    switch (view) {
    case MAP:
      return new MapPage(driver);
    case COMPARISON_REPORT:
      return new ComparisonReportPage(driver);
    case RANKING:
      return new RankingPage(driver);
    case RING_STUDY:
      return new RingStudyPage(driver);
    case BUSINESSES:
      return new BusinessesPage(driver);
    case RELATED_DATA:
      return new RelatedDataReportPage(driver);
    case QUICK_REPORT:
      return new QuickReportPage(driver);
    case TIME_SERIES:
      return new TimeSeriesPage(driver);
    case BAR_CHART:
      return new BarChartPage(driver);
    case CROSSTAB_TABLE:
      return new CrosstabPage(driver);
    case SCATTER_PLOT:
      return new ScarboroughCrosstabPage(driver);
    default:
      throw new AssertionError("View type is not supported");
    }
  }

  /**
   * // // // // .
   **/
  @Step("Select {2} random locations")
  public void chooseRandomNumberLocations(String searchLetters, int num) {
    logger.debug("Select " + num + " random locations");
    while (num > 0) {

      locationInput.sendKeys(searchLetters);
      List<WebElement> allSearchLocations = waitForAllElementsToAppearByLocator(
          By.cssSelector(".sa-sidebar-location-result"), "Locations did not appear");
      int index = new Random().nextInt(allSearchLocations.size() - 1);
      logger.trace("Click on the location: " 
          + allSearchLocations.get(index).findElement(By.cssSelector(".sa-sidebar-location-name")).getText().trim());
      allSearchLocations.get(index).click();

      num--;
    }
  }
}
