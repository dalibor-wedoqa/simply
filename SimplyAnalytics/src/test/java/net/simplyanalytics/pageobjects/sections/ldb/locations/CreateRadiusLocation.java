package net.simplyanalytics.pageobjects.sections.ldb.locations;

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
public class CreateRadiusLocation extends BasePage {
  
  protected static final By rootLocator = By.cssSelector(".sa-create-radius");
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(xpath = ".//div[text()='Create Radius Location']")
  private WebElement title;
  
  @FindBy(css = "input[placeholder='Location Search']")
  private WebElement locationSearchInput;
  
  @FindBy(css = ".sa-create-radius-selected-location")
  private WebElement selectedLocation;
  
  @FindBy(css = "input[value = '1']")
  private WebElement radiusSizeInputElement;

  @FindBy(xpath = ".//label[.='Radius:']/../../td[2]/div/div/div/a")
  private WebElement radiusSizeUnitsCombo;
  
  @FindBy(css = ".sa-create-radius-name-field input")
  private WebElement radiusLocationNameInput;

  @FindBy(xpath = ".//span[text()='Save']/..")
  private WebElement saveButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement canselButton;

  @FindBy(xpath = "//div[@class='x-box-target']//button[contains(@class, 'sa-close')]")
  private WebElement closeButton;
  
  public CreateRadiusLocation(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(title, "The title is not expected. The Create Radius Location is not loaded");
  }
  
  /**
   * Enter radius size.
   * 
   * @param radiusSize radius size
   */
  @Step("Enter radius size {0}")
  public void enterRadiusSize(int radiusSize) {
    logger.debug("Enter radius size " + radiusSize);
    WebElement enterName = waitForElementToAppear(radiusSizeInputElement, "Radius Size input is not displayed");
    enterName.clear();
    enterName.sendKeys(String.valueOf(radiusSize));
  }
  
  @Step("Click on radius size unit combo box")
  private void clickRadiusUnits() {
    logger.debug("Click on radius size unit combo box");
    waitForElementToAppear(radiusSizeUnitsCombo, "Radius Size Units is not displayed").click();
  }
  
  /**
   * Select radius size unit.
   * 
   * @param radiusUnits radius size unit
   */
  @Step("Select radius size unit \"{0}\"")
  public void chooseRadiusUnits(String radiusUnits) {
    clickRadiusUnits();
    logger.debug("Select radius size unit: " + radiusUnits);
    waitForElementToAppear(driver.findElement(By.xpath("//div[contains(@class, 'sa-combo-button-menu')]//span[text()='" + radiusUnits + "']")),
                    "Radius unit element is not loaded").click();
  }
  
  /**
   * Enter custom radius location name.
   * 
   * @param customRadiusLocationName custom radius location name
   */
  @Step("Enter custom radius location name : {0}")
  public void enterRadiusLocationName(String customRadiusLocationName) {
    logger.debug("Enter radius location name : " + customRadiusLocationName);
    WebElement enterName = waitForElementToAppear(radiusLocationNameInput,
        "Radius Location Name input is not displayed");
    enterName.clear();
    enterName.sendKeys(customRadiusLocationName);
  }
  
  @Step("Create a new radius location")
  public void addRadiusLocation(int radiusSize, String radiusUnits, String customRadiusLocationName) {
    enterRadiusSize(radiusSize);
    chooseRadiusUnits(radiusUnits);
    enterRadiusLocationName(customRadiusLocationName);
    clickSave();
  }
  
  /**
   * Click on the save button.
   */
  @Step("Click on the save button")
  public void clickSave() {
    logger.debug("Click on the Save button");
    waitForElementToAppear(saveButton, "Save button is not displayed").click();
    waitForElementToDisappear(rootLocator);
    waitForLoadingToDisappear();
  }
  
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    waitForElementToAppear(canselButton, "Cancel button is not displayed").click();
  }
  
  @Step("Click on the Close button")
  public void clickClose() {
    logger.debug("Click on the Close button");
    waitForElementToAppear(closeButton, "Close button is not displayed").click();
  }
  
  @Step("Search for \"{0}\" and select location \"{1}\"")
  private void voidChooseLocation(String searchLetters, String location) {
    logger.debug("Search for location \"" + searchLetters + "\"");
    locationSearchInput.sendKeys(searchLetters);
    logger.debug("Select location \"" + location + "\"");
    waitForElementToAppear(
        driver.findElement(By.xpath("//button[@class='sa-sidebar-location-result']" + "/span[text()='" + location + "']")),
        "Location element is not present").click();
  }
  
  @Step("Add location {0} to the project")
  public void voidChooseLocation(Location location) {
    voidChooseLocation(location.getName().substring(0, 3), location.getName());
  }
  
  public Location getSelectedLocation() {
    return Location.getByName(selectedLocation.getText());
  }
  
  public boolean isDisplayed() {
    return isPresent(rootLocator);
  }
  
}
