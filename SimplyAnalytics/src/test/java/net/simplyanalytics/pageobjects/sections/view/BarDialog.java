package net.simplyanalytics.pageobjects.sections.view;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BarDialog extends BasePage {
  
  @FindBy(xpath = ".//div[contains(@class,'sa-arrow-menu')]")
  private WebElement root;
  
  @FindBy(css = ".sa-report-grid-menu-caption")
  private WebElement locationName;
  
  @FindBy(xpath = ".//a[contains(@class,'x-menu-item-link')]//span[text()='Create Map']")
  private WebElement createMapButton;
  
  @FindBy(xpath = ".//a[contains(@class,'x-menu-item-link')]//span[text()='Create Ranking']")
  private WebElement createRankingButton;
  
  @FindBy(xpath = ".//a[contains(@class,'x-menu-item-link')]//span[text()='Remove from this View']")
  private WebElement removeFromViewButton;
  
  protected BarDialog(WebDriver driver) {
    super(driver);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(root, "Dropdown panel is not present");
    waitForElementToAppear(removeFromViewButton, "Remove button is not present");
  }
  
  public void removeBar() {
    logger.debug("Remove bar from view");
    removeFromViewButton.click();
  }
  
  public MapViewPanel createMap() {
    logger.debug("Create Map view");
    createMapButton.click();
    return new MapViewPanel(driver);
  }
  
  public RankingViewPanel createRanking() {
    logger.debug("Create Ranking view");
    createRankingButton.click();
    return new RankingViewPanel(driver);
  }
  
}
