package net.simplyanalytics.pageobjects.sections.ldb.businesses;

import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class AdvancedBusinessSearchWindow extends BasePage {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-business-editor");
  protected WebElement root;

  @FindBy(css = ".sa-business-editor-condition-row")
  private List<WebElement> conditionRows;

  @FindBy(css = ".sa-business-editor input")
  private WebElement inputField;

  @FindBy(css = ".sa-divider-combo-button-button")
  private List<WebElement> dividerConditions;

  @FindBy(css = ".sa-business-editor-add-condition-btn")
  private WebElement addConditionButton;

  @FindBy(css = ".sa-close")
  private WebElement close;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Search']]")
  private WebElement searchButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;

  public AdvancedBusinessSearchWindow(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {

  }

  /**
   * Click on the Add condition button.
   * 
   * @return AdvancedBusinessSearchWindow
   */
  @Step("Click on the Add condition button")
  public AdvancedBusinessSearchWindow clickAddCondition() {
    logger.debug("Click on the Add condition button");
    addConditionButton.click();
    return new AdvancedBusinessSearchWindow(driver);
  }

  public boolean isAddConditionButtonEnabled() {
    return !addConditionButton.getAttribute("class").contains("x-item-disabled");
  }

  public SearchConditionRow getConditionRow(int dividerNumber) {
    WebElement conditionRowRoot = conditionRows.get(dividerNumber);
    return new SearchConditionRow(driver, conditionRowRoot);
  }

  /**
   * Click divider condition.
   * 
   * @param dividerNumber dividerNumber
   * @return AndOrDropwdown
   */
  @Step("Click divider condition {0}")
  public AndOrDropwdown clickDividerCondition(int dividerNumber) {
    logger.debug("Click on the " + dividerNumber + "th divider condition");
    dividerConditions.get(dividerNumber).click();
    WebElement dropdownRoot = driver
        .findElement(By.cssSelector("div.sa-combo-button-menu:not([style*='hidden'])"));
    return new AndOrDropwdown(driver, dropdownRoot);
  }

  public String getActualValueForDividerCondition(int dividerNumber) {
    return dividerConditions.get(dividerNumber).getText();
  }

  /**
   * Click on the Search button.
   */
  @Step("Click on the Search button")
  public void clickSearch() {
    logger.debug("Click on the Search button");
    searchButton.click();
    waitForElementToDisappear(root, "The advanced search dialog should disappear");
  }

  /**
   * Click on the Input field.
   */
  @Step("Click on the Input field")
  public void typeInput(String val) {
    logger.debug("Click on the Input field");
    inputField.click();
    logger.debug("Type the value:"+val);
    inputField.sendKeys(val);
//    waitForElementToAppear(root, "The advanced search dialog should disappear");
  }

  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancelAdvancedBusinessSearch() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
    waitForElementToDisappear(root, "The advanced search dialog should disappear");
  }

  /**
   * Click on the close button.
   */
  @Step("Click on the close button")
  public void clickCloseAdvancedBusinessSearch() {
    logger.debug("Click on the close button");
    close.click();
    waitForElementToDisappear(root, "The advanced search dialog should disappear");
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }
}