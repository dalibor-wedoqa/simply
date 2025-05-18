package net.simplyanalytics.pageobjects.sections.toolbar.ranking;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class ListSizeDropdown extends BasePage {
  
  private static final By rootElement = By
      .cssSelector(".x-panel:not([style*='visibility: hidden'])");
  private WebElement root;
  
  public ListSizeDropdown(WebDriver driver) {
    super(driver, rootElement);
    root = driver.findElement(rootElement);
  }
  
  @Override
  public void isLoaded() {
    
  }
    
  @Step("Click on the Top {0} Data results button")
  public RankingPage clickTopDataResults(int numberOfDataResults) {
    logger.debug("Click on the Top " + numberOfDataResults + " Data results button");
    waitForElementToBeClickable(
        root.findElement(By.xpath(".//span[normalize-space(.)='Top " + numberOfDataResults + "']")),
        "Top " + numberOfDataResults + " Data results element is not present").click();
    return new RankingPage(driver);
  }
  
}
