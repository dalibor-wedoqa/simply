package net.simplyanalytics.pageobjects.sections.toolbar.map;

import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ByMapViewComboElements extends BasePage {

  protected static final By rootLocator = By
      .cssSelector(".sa-menu-button-menu:not([style*='hidden'])");
  protected WebElement root;

  @FindBy(xpath = ".//span[contains(normalize-space(text()), 'Auto')]")
  private WebElement autoButton;

  public ByMapViewComboElements(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(autoButton, "The auto button should appear");
  }

  /**
   * Click on location type.
   * @param locationType location type
   */
  @Step("Click on location type \"{0}\"")
  public void clickLocationType(LocationType locationType) {
    logger.debug("Click on location type: " + locationType.getSingularName());
    WebElement button = root.findElement(
        By.xpath(".//span[normalize-space(text()) = '" + locationType.getPluralName() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on location type Auto.
   */
  @Step("Click on location type Auto")
  public void clickAutoLocationType() {
    logger.debug("Click on location type Auto");
    autoButton.click();
    waitForElementToDisappear(root);
  }
}
