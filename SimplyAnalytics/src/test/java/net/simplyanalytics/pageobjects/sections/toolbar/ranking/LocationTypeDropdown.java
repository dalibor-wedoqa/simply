package net.simplyanalytics.pageobjects.sections.toolbar.ranking;

import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class LocationTypeDropdown extends BasePage {
  
  private static final By rootElement = By
      .cssSelector(".x-panel:not([style*='visibility: hidden'])");
  
  private WebElement root;
  
  public LocationTypeDropdown(WebDriver driver) {
    super(driver, rootElement);
    root = driver.findElement(rootElement);
    
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  @Step("Click on the Location type {0} button")
  public RankingPage clickFilterBy(LocationType locationType) {
    logger.debug("Click on the Location type " + locationType.getPluralName() + " button");
    waitForElementToBeClickable(
        root.findElement(By.xpath(".//span[normalize-space(.)='" + locationType.getPluralName() + "']")),
        "Location type " + locationType.getPluralName() + " element is not present")
            .click();
    return new RankingPage(driver);
  }
  
}
