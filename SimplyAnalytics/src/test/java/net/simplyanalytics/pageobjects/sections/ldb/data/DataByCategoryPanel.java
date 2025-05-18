package net.simplyanalytics.pageobjects.sections.ldb.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByImportedDataseDropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataByCategoryPanel extends DataBaseBrowsePanel {

  protected static final By ROOT_LOCATOR = By
      .cssSelector("[id*='data-sidebar'] .sa-data-categories-grid");
  protected WebElement root;

  @FindBy(css = ".sa-sidebar-section:not([style='display: none;']) .sa-sidebar-list")
  private WebElement dateByCategoryContainer;

  @FindBy(css = ".sa-sidebar-section:not([style='display: none;']) .sa-sidebar-list-item")
  private List<WebElement> allDataElements;

  public DataByCategoryPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(allDataElements.get(1), "Popular Data is not present");
  }

  /**
   * Click on the category.
   * 
   * @param categoryData Data category
   * @return DataByCategoryDropwDown
   */
  @Step("Click on the category \"{0}\"")
  public DataByCategoryDropwDown clickOnACategoryData(CategoryData categoryData) {
    logger.debug("Click on the category " + categoryData);
    waitForElementToAppear(
        driver.findElement(
            By.xpath("(//div[contains(@class,'sa-data-categories-grid')]//button[contains(@data-id,'"
                + categoryData.getDataId() + "')])[1]")),
        "Category Data " + categoryData.getName() + " is not clickable at a point").click();
    return new DataByCategoryDropwDown(driver);
  }
  
  @Step("Click on the category \"{0}\"")
  public DataByImportedDataseDropdown clickOnMyImportedDataCategory(String datasetName) {
    CategoryData categoryData = CategoryData.MY_IMPORTED_DATA;
    logger.debug("Click on the category " + categoryData);
    waitForElementToAppear(
        driver.findElement(
            By.xpath("//div[contains(@class,'sa-data-categories-grid')]//button[contains(@data-id,'"
                + categoryData.getDataId() + "')]")),
        "Category Data " + categoryData.getName() + " is not clickable at a point").click();
    return new DataByImportedDataseDropdown(driver, datasetName);
  }

  /**
   * Click on a random data Category.
   * 
   * @return DataByCategoryDropwDown
   */
  @Step("Click on a random data Category")
  public DataByCategoryDropwDown clickRandomDataCategory() {
    int index = new Random().nextInt(allDataElements.size() - 1);
    WebElement randomDataElement = allDataElements.get(index);
    while(randomDataElement.getAttribute("data-id").equals("imports")) {
      index = new Random().nextInt(allDataElements.size() - 1);
      randomDataElement = allDataElements.get(index);
    }
    String randomElementName = randomDataElement.getText();
    logger.debug("Click on a random data category: " + randomElementName);
    randomDataElement.click();
    return new DataByCategoryDropwDown(driver);
  }

  /**
   * Click on random Category Data.
   * 
   * @param page page
   * @return randomDataVariable
   */
  @Step("Click on random Category Data")
  public String chooseRandomDataVariable(Page page) {
    DataByCategoryDropwDown basePanel = clickRandomDataCategory();
    String randomDataVariable = basePanel.clickOnARandomDataResult();
    basePanel.clickClose(page);
    return randomDataVariable;
  }

  /**
   * Click on random Category Datas.
   * 
   * @param dataNumber data number
   * @param page       page
   * @return allDataSelected
   */
  @Step("Click on random Category Datas")
  public List<String> chooseRandomNumberOfDataVariables(int dataNumber, Page page) {

    List<String> allDataSelected = new ArrayList<>();

    for (int i = 0; i < dataNumber; i++) {
      DataByCategoryDropwDown basePanel = clickRandomDataCategory();
      String randomDataVariable = basePanel.clickOnARandomDataResult();
      allDataSelected.add(randomDataVariable);
      basePanel.clickClose(page);
    }

    return allDataSelected;
  }
  
  public boolean isDataCategoryPresent(CategoryData category) {
    for(WebElement categoryElement : allDataElements) {
      if(categoryElement.getAttribute("data-id").equals(category.getDataId())) {
        return true;
      }
    }
    return false;
  }
}
