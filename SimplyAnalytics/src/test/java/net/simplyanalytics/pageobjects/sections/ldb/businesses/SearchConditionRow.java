package net.simplyanalytics.pageobjects.sections.ldb.businesses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.simplyanalytics.enums.BusinessSearchBy;
import net.simplyanalytics.enums.BusinessSearchConditions;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class SearchConditionRow extends BasePage {

  @FindBy(css = ".sa-business-editor-value-combo")
  private WebElement searchByCombobox;

  @FindBy(css = ".sa-business-editor-operator-combo")
  private WebElement searchConditionCombobox;

  @FindBy(css = "table input")
  private WebElement searchValue;

  @FindBy(css = ".sa-remove-button")
  private WebElement removeButton;

  private WebElement root;

  public SearchConditionRow(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(searchByCombobox, "The search by combo box should appear");
    waitForElementToAppear(searchConditionCombobox,
        "The search condition combo box should appear");
    waitForElementToAppear(searchValue, "The input filed should be present");
    waitForElementToAppear(removeButton, "The remove button should be present");
  }

  public BusinessSearchBy getActualSearchBy() {
    return BusinessSearchBy.getByName(searchByCombobox.getText());
  }
  
  /**
   * Select search by Business Search.
   * 
   * @param searchBy Business Search
   */
  @Step("Select search by {0}")
  public void selectSearchBy(BusinessSearchBy searchBy) {
    logger.debug("Select search by " + searchBy);
    searchByCombobox.click();
    waitForElementToBeVisible(
        By.xpath("//div[contains(@class, 'sa-combo-button-menu')]"
            + "[not(contains(@style, 'visibility: hidden;'))]//span[text()='" + searchBy + "']"),
        "Search combo " + searchBy + " element is not loaded").click();
  }
  
  public BusinessSearchConditions getActualSearchCondition() {
    return BusinessSearchConditions.getByName(searchConditionCombobox.getText());
  }

  /**
   * Select condition.
   * 
   * @param condition condition
   */
  @Step("Select condition {0}")
  public void selectCondition(BusinessSearchConditions condition) {
    logger.debug("Select condition " + condition);
    validate(getActualSearchBy(), condition);
    searchConditionCombobox.click();
    waitForElementToBeVisible(
        By.xpath("//div[contains(@class, 'sa-combo-button-menu')]"
            + "[not(contains(@style, 'visibility: hidden;'))]//span[text()='" + condition + "']"),
        "Condition combo " + condition.getName() + " element is not loaded").click();
  }

  /**
   * Enter search value.
   * 
   * @param filterCondition filtering contition
   */
  @Step("Enter search value {0}")
  public void enterSearchValue(String filterCondition) {
    logger.debug("Enter search value " + filterCondition);
    searchValue.click();
    searchValue.clear();
    searchValue.sendKeys(filterCondition);
  }

  /**
   * Search for a random business code.
   * 
   * @return businessNum
   */
  @Step("Search for a random business code")
  public String selectRandomBusinessCode() {
    List<String> allCodes = new ArrayList<>();
    logger.debug("Click into the search value input field");
    searchValue.click();
    List<WebElement> allBusinessesByCode = waitForAllElementsToAppearByLocator(
        By.cssSelector(".sa-business-editor-code-list-item"),
        "All Businesses code elements should be present");

    allBusinessesByCode.forEach(i -> allCodes.add(i.getAttribute("data-code")));

    allureStep("Select a business category from the dropdown");
    allBusinessesByCode.get(new Random().nextInt(allBusinessesByCode.size())).click();
    allBusinessesByCode = waitForAllElementsToAppearByLocator(
        By.cssSelector(".sa-business-editor-code-list-item"),
        "All Businesses by code elements are not loaded");

    String businessNum = null;
    if (!allBusinessesByCode.isEmpty()) {
      int random = new Random().nextInt(allBusinessesByCode.size());
      businessNum = allBusinessesByCode.get(random).getText();
      logger.trace(businessNum);
      businessNum = allBusinessesByCode.get(random).getText().split(" ")[0];
      logger.debug(businessNum);
      allBusinessesByCode.get(random).click();
    }
    return businessNum;
  }

  /**
   * Click on the x icon after the condition.
   * 
   * @return AdvancedBusinessSearchWindow
   */
  @Step("Click on the x icon after the condition")
  public AdvancedBusinessSearchWindow clickRemoveButton() {
    logger.debug("Click on the x icon after the condition");
    removeButton.click();
    waitForElementToDisappear(root);
    return new AdvancedBusinessSearchWindow(driver);
  }
  
  private boolean validate(BusinessSearchBy searchBy, BusinessSearchConditions condition) {
    if (searchBy.equals(BusinessSearchBy.NAME)
        && !(condition.equals(BusinessSearchConditions.CONTAINS)
            || condition.equals(BusinessSearchConditions.STARTS))) {
      return false;
    }

    if ((searchBy.equals(BusinessSearchBy.SIC) || searchBy.equals(BusinessSearchBy.NAICS))
        && !condition.equals(BusinessSearchConditions.STARTS)) {
      return false;
    }

    if ((searchBy.equals(BusinessSearchBy.NUMBER_OF_LOCAL_EMPLOYEES)
        || searchBy.equals(BusinessSearchBy.SALES_VOLUME))
        && !(condition.equals(BusinessSearchConditions.GREATER)
            || condition.equals(BusinessSearchConditions.LESS)
            || condition.equals(BusinessSearchConditions.EQUAL)
            || condition.equals(BusinessSearchConditions.GREATEREQUAL)
            || condition.equals(BusinessSearchConditions.LESSEQUAL)
            || condition.equals(BusinessSearchConditions.NOT_EQUAL))) {
      return false;
    }
    return true;
  }

}