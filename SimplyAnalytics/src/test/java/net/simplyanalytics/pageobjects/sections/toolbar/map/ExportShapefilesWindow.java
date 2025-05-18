package net.simplyanalytics.pageobjects.sections.toolbar.map;

import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ExportShapefilesWindow extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-shapefiles");
  protected WebElement root;

  @FindBy(css = ".sa-menu-button:nth-child(2)")
  private WebElement selectLocation;

  @FindBy(css = ".sa-menu-button:nth-child(4) ")
  private WebElement selectGeographicUnit;

  @FindBy(css = "li[data-id]")
  private List<WebElement> dataVariables;

  @FindBy(css = "input")
  private WebElement emailTo;

  @FindBy(xpath = "//a[span[text()='Send']]")
  private WebElement sendBtn;

  @FindBy(xpath = "//a[span[text()='Cancel']]")
  private WebElement cancelBtn;

  @FindBy(css = ".sa-close")
  private WebElement close;

  protected ExportShapefilesWindow(WebDriver driver) {
    super(driver, driver.findElement(rootLocator));
    root = driver.findElement(rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(selectLocation, "Select location didn't appear");
    waitForElementToAppear(selectGeographicUnit, "Select geographic unit didn't appear");
    waitForElementToAppear(emailTo, "Email to field didn't appear");
    waitForElementToAppear(sendBtn, "Send button didn't appear");
    waitForElementToAppear(cancelBtn, "Cancel button didn't appear");
  }

  @Step("Click on location combo box")
  private void clickLocation() {
    logger.debug("Click on location combo box");
    waitForElementToAppear(selectLocation, "Location is not displayed").click();
  }

  /**
   * Select location.
   * 
   * @param location location
   */
  @Step("Select location \"{0}\"")
  public void chooseLocation(String location) {
    clickLocation();
    logger.debug("Select location: " + location);
    waitForElementToAppear(driver.findElement(By.xpath("//div[contains(@class, 'x-box-item') and"
        + " contains(@id,'menucheckitem') and normalize-space(.)=" + xpathSafe(location) + "]")),
        "Location element is not loaded").click();
  }

  @Step("Click on geographical unit combo box")
  private void clickGeographicUnit() {
    logger.debug("Click on geographical unit combo box");
    waitForElementToAppear(selectGeographicUnit, "Geographical unit is not displayed").click();
  }

  /**
   * Select geographical unit.
   * 
   * @param geographicUnit geographical unit
   */
  @Step("Select geographical unit \"{0}\"")
  public void chooseGeographicUnit(String geographicUnit) {
    clickGeographicUnit();
    logger.debug("Select geographic unit: " + geographicUnit);
    waitForElementToAppear(driver.findElement(By.xpath("//div[contains(@class, 'x-box-item')"
        + " and contains(@id,'menucheckitem') and normalize-space(.)=" + xpathSafe(geographicUnit)
        + "]")), "Geographic unit element is not loaded").click();
  }

  /**
   * Enter Email to.
   * 
   * @param email email
   */
  @Step("Enter Email to: {0}")
  public void enterEmailTo(String email) {
    logger.debug("Enter email to: " + email);
    emailTo.click();
    emailTo.sendKeys(email);
  }

  @Step("Click on Send button")
  public void clickOnSendButton() {
    logger.debug("Click on Send button");
    sendBtn.click();
  }

  /**
   * Click on Cancel button.
   */
  @Step("Click on Cancel button")
  public void clickOnCancelButton() {
    logger.debug("Click on Cancel button");
    cancelBtn.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on Close button.
   */
  @Step("Click on Close button")
  public void clickOnClose() {
    logger.debug("Click on Close button");
    close.click();
    waitForElementToDisappear(root);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}
