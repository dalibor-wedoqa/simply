package net.simplyanalytics.pageobjects.sections.toolbar.map;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class InMapViewComboElements extends BasePage {

  protected static final By rootLocator = By
      .cssSelector(".sa-menu-button-menu:not([style*='hidden'])");
  protected WebElement root;

  @FindBy(xpath = ".//span[contains(normalize-space(text()), 'None')]")
  private WebElement noneButton;
  
  @FindBy(xpath= ".//div[contains(@class, 'x-panel-body')]//div[contains(@class, 'x-menu-item-checked')]")
  private WebElement comboLocation;

  public InMapViewComboElements(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(noneButton, "The none button should appear");
  }

  /**
   * Click on location.
   * @param location location
   */
  @Step("Click on location \"{0}\"")
  public void clickLocation(Location location) {
    logger.debug("Click on location: " + location.getName());
    WebElement button = root
        .findElement(By.xpath("//span[normalize-space(text()) = '" + location.getName() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(root);
    waitForLoadingToDisappear();
  }
  
  /**
   * Click on combo location.
   * @param location location
   */
  @Step("Click on combo location \"{0}\"")
  public void clickComboLocation() {
    logger.debug("Click on combo location: " + comboLocation.getText());
    waitForElementToAppear(comboLocation, "The dropdown button is not present");
    waitForElementToBeClickable(comboLocation, "Combo location is not clickable");
    comboLocation.click();
    waitForElementToDisappear(root);
    waitForLoadingToDisappear();
  }

  /**
   * Click on None button.
   */
  @Step("Click on None button")
  public void clickNoneButton() {
    logger.debug("Click on the None button");
    noneButton.click();
    waitForElementToDisappear(root);
  }
}
