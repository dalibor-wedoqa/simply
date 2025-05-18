package net.simplyanalytics.pageobjects.sections.toolbar;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.simplyanalytics.enums.CellDisplayType;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;

import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import io.qameta.allure.Step;

public class DropdownToolbar extends BasePage {

  private ViewType view;

  private static final By rootElement = By
      .cssSelector(".x-panel:not([style*='visibility: hidden'])");
  private WebElement root;

  public DropdownToolbar(WebDriver driver) {
    super(driver, rootElement);

    root = driver.findElement(rootElement);
  }

  @Override
  public void isLoaded() {

  }

  @Step("Click on the location {0} button")
  public BaseViewPage clickSortByDatavariable(Location location) {
    logger.debug("Click on the location " + location.getName() + " button");

    waitForElementToBeClickable(
        root.findElement(By.xpath(".//span[normalize-space(.)='" + location.getName() + "']")),
        "Location " + location.getName() + " element is not present").click();
    switch (view) {
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case MAP:
        return new MapPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case BUSINESSES:
        return new BusinessesPage(driver);
      case QUICK_REPORT:
        return new QuickReportPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case RELATED_DATA:
        return new RelatedDataReportPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      default:
        throw new AssertionError("view type is not supported: " + view);
    }
  }

  // TODO
  public void clickonLocation(Location location) {
    logger.debug("Click on location: " + location.getName());
    WebElement button = root
        .findElement(By.xpath("//a[contains(@class, 'x-menu-item-link')]//span[text()= '" + location.getName() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(root);
    waitForLoadingToDisappear(20);
  }
  
  public List<Location> getLocationList() {
    logger.info("Get locations list");
    List<WebElement> locations = root
        .findElements(By.cssSelector(".x-menu-item-link span"));
    return locations.stream().map(location -> Location.getByName(location.getText())).collect(Collectors.toList());
  }

  // TODO
  public void clickLocationType(LocationType locationType) {
    logger.debug("Click on location type: " + locationType.getSingularName());
    WebElement button = root.findElement(
        By.xpath(".//span[normalize-space(.) = '" + locationType.getPluralName() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(root);
  }
  
  public List<LocationType> getLocationTypeList() {
    logger.info("Get locations type list");
    List<WebElement> locationTypes = root
        .findElements(By.cssSelector(".x-menu-item-link span"));
    return locationTypes.stream().map(locationType -> LocationType.getByPluralName(locationType.getText())).collect(Collectors.toList());
  }

  public void clickDisplayType(CellDisplayType displayType) {
    logger.debug("Click on display type: " + displayType.getName());
    WebElement button = root.findElement(
        By.xpath(".//span[normalize-space(.) = '" + displayType.getName() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(root);
  }
  
}
