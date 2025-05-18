package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.List;

import net.simplyanalytics.enums.DataFormat;
import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class GeographicUnitComboElements extends BasePage {

  protected static final By rootLocator = By
      .cssSelector(".sa-menu-button-menu:not([style*='hidden'])");
  protected WebElement root;

  @FindBy(css = ".x-menu-item-text")
  private List<WebElement> listElements;

  protected GeographicUnitComboElements(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
    // TODO Auto-generated constructor stub
  }

  public void isLoaded() {
    // isLoaded solved by root locator
  }

  @Step("Click on location \"{0}\"")
  protected void clickLocation(LocationType location) {
    logger.debug("Click on location: " + location.getPluralName());
    WebElement button = root
        .findElement(By.xpath(".//span[normalize-space(.) = '" + location.getPluralName() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    waitForElementToBeClickable(button, "The dropdown button is not clickable");
    button.click();
    waitForElementToDisappear(root);
    waitForLoadingToDisappear();
  }

  @Step("Click on data type \"{0}\"")
  protected void clickDataType(DataType data) {
    logger.debug("Click on type: " + data.getType());
    WebElement button = root
        .findElement(By.xpath(".//span[normalize-space(.) = '" + data.getType() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    waitForElementToBeClickable(button, "The dropdown button is not clickable");
    button.click();
    waitForElementToDisappear(root);
  }

  @Step("Click on data format \"{0}\"")
  protected void clickDataFormat(DataFormat format) {
    logger.debug("Click on format: " + format.getType());
    WebElement button = root
        .findElement(By.xpath(".//span[normalize-space(.) = '" + format.getType() + "']"));
    waitForElementToAppear(button, "The dropdown button is not present");
    waitForElementToBeClickable(button, "The dropdown button is not clickable");
    button.click();
    waitForElementToDisappear(root);
  }

}
