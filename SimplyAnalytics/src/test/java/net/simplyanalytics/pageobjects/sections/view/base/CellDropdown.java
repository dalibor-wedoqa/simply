package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CellDropdown extends BasePage {
  
  protected WebElement root;
  
  @FindBy(css = ".sa-close")
  private WebElement closeButton;
  
  @FindBy(css = "[id*='menu-attribute']")
  private WebElement dataVariableElement;
  
  @FindBy(css = "[id*='menu-location']")
  private WebElement locationElement;
  
  @FindBy(css = ".x-box-item[id*='menu-create-map']")
  private WebElement createMapButton;
  
  @FindBy(css = ".x-box-item[id*='menu-create-ranking']")
  private WebElement createRankingButton;
  
  protected CellDropdown(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(dataVariableElement, "The data variable element is missing");
    waitForElementToAppear(locationElement, "The location element is missing");
    waitForElementToAppear(closeButton, "The close button is missing");
    waitForElementToAppear(createMapButton, "The Create Map button is missing");
    waitForElementToAppear(createRankingButton, "The Create Ranking button is missing");
  }
  
  /**
   * Click on the close (x) button.
   */
  @Step("Click on the close (x) button")
  public void clickCloseIcon() {
    logger.debug("Click on the close (x) button");
    closeButton.click();
    waitForElementToDisappear(root, "The dropdown should diasapear");
  }
  
  public String getDataVariable() {
    return dataVariableElement.getText();
  }
  
  public String getLocation() {
    return locationElement.getText();
  }
  
  /**
   * Click on the Create Map button.
   * @return MapPage
   */
  @Step("Click on the Create Map button")
  public MapPage clickCreateMapButton() {
    logger.debug("Click on the Create Map button");
    createMapButton.click();
    return new MapPage(driver);
  }
  
  /**
   * Click on the Create Ranking button.
   * @return RankingPage
   */
  @Step("Click on the Create Ranking button")
  public RankingPage clickCreateRankingButton() {
    logger.debug("Click on the Create Ranking button");
    createRankingButton.click();
    return new RankingPage(driver);
  }
}