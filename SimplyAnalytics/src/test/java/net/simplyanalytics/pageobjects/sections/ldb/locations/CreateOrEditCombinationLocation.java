package net.simplyanalytics.pageobjects.sections.ldb.locations;

import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * .
 *
 * @author wedoqa
 */
public class CreateOrEditCombinationLocation extends BasePage {

  protected static final By ROOT_LOCATION = By.cssSelector(".sa-combo-location-window");
  protected WebElement root;

  @FindBy(css = "input[placeholder='Enter a name']")
  private WebElement inputName;

  @FindBy(css = "input[placeholder='Location Search']")
  private WebElement inputLocation;

  @FindBy(css = ".sa-selected-locations-list-table")
  private List<WebElement> locations;

  @FindBy(xpath = ".//span[text()='Save']/..")
  private WebElement saveButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement canselButton;

  @FindBy(css = ".sa-close")
  private WebElement closeButton;

  public CreateOrEditCombinationLocation(WebDriver driver) {
    super(driver, ROOT_LOCATION);
    root = driver.findElement(ROOT_LOCATION);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(inputName, "Name input element is not loaded");
    waitForElementToAppear(inputLocation, "Location input element is not loaded");
  }

  /**
   * Enter combination name.
   * 
   * @param combinationName combination name
   */
  @Step("Enter combination name: \"{0}\"")
  public void enterCombinationName(String combinationName) {
    logger.debug("Enter combination name: " + combinationName);
    inputName.click();
    inputName.clear();
    inputName.sendKeys(combinationName);
  }

  public String getLocatinoName() {
    return inputName.getAttribute("value");
  }

  /**
   * Select location.
   * 
   * @param location location
   */
  @Step("Select location {0}")
  public void chooseLocation(Location location) {
    logger.debug("Select location " + location);
    waitForElementToAppear(inputLocation, "Location input element is not loaded")
        .sendKeys(location.getName().substring(0, 3));
    waitForElementToAppear(
        driver.findElement(By.xpath(".//span[text()='" + location.getName() + "']")),
        "Choosed location was not loaded").click();
  }

  /**
   * get selected locations.
   * 
   * @return locations
   */
  public List<String> getSelectedLocations() {
    return locations
        .stream().map(element -> element
            .findElement(By.cssSelector(".sa-selected-locations-list-item-name")).getText().trim())
        .collect(Collectors.toList());
  }

  /**
   * Remove location.
   * 
   * @param name location name
   */
  @Step("Remove location {0}")
  public void removeLocation(String name) {
    logger.debug("Remove location " + name);
    locations.stream()
        .filter(
            element -> element.findElement(By.cssSelector(".sa-selected-locations-list-item-name"))
                .getText().equals(name))
        .findFirst().get().findElement(By.cssSelector(".sa-selected-locations-list-x")).click();
  }

  /**
   * Click on the Save button.
   */
  @Step("Click on the Save button")
  public void clickSave() {
    logger.debug("Click on the Save button");
    waitForElementToAppear(saveButton, "Save button is not displayed").click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    waitForElementToAppear(canselButton, "Cancel button is not displayed").click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Close button.
   */
  @Step("Click on the Close button")
  public void clickClose() {
    logger.debug("Click on the Close button");
    waitForElementToAppear(closeButton, "Close button is not displayed").click();
    waitForElementToDisappear(root);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}
