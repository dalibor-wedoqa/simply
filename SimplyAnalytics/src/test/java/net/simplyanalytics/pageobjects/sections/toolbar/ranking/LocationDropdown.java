package net.simplyanalytics.pageobjects.sections.toolbar.ranking;

//import net.simplyanalytics.enums.Location;
//import net.simplyanalytics.pageobjects.base.BasePage;
//import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
//import io.qameta.allure.Step;

//public class LocationDropdown extends BasePage {
//
//  private static final By rootElement = By
//      .cssSelector(".x-panel:not([style*='visibility: hidden'])");
//  private WebElement root;
//
//  public LocationDropdown(WebDriver driver) {
//    super(driver, rootElement);
//
//    root = driver.findElement(rootElement);
//  }
//
//  @Override
//  public void isLoaded() {
//    
//  }
//
//  @Step("Click on the location {0} button")
//  public RankingPage clickSortByDatavariable(Location location) {
//    logger.debug("Click on the location " + location.getName() + " button");
//
//    waitForElementToBeClickable(
//        root.findElement(By.xpath(".//span[normalize-space(.)='" + location.getName() + "']")),
//        "Location " + location.getName() + " element is not present").click();
//    return new RankingPage(driver);
//  }
//}
