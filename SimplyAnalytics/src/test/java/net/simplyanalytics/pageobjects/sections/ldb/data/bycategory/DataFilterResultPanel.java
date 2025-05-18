package net.simplyanalytics.pageobjects.sections.ldb.data.bycategory;

import java.util.List;
import java.util.Random;

import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.data.filter.BaseFilter;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataFilterResultPanel extends BasePage {

  @FindBy(css = ".sa-data-search-facet-browser")
  private WebElement dataFilterResultContainer;

  @FindBy(css = ".sa-sidebar-section-header-button-text")
  private WebElement selectedFilterYear;

  @FindBy(css = ".sa-sidebar-data-radio.sa-sidebar-data-section-radio") // still we
                                                                                     // will use
                                                                                     // data
                                                                                     // variabled
                                                                                     // from 2018
  private List<WebElement> firstFilterYear;

  @FindBy(css = ".sa-sidebar-data-checkbox-button")
  private List<WebElement> filters;

  public DataFilterResultPanel(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(dataFilterResultContainer, "Data Filter Result Panel is not loaded");
  }

  /**
   * // // // // .
   **/

  public String getSelectedYear() {
    if (selectedFilterYear.getText().equalsIgnoreCase("Latest")) {
      if (firstFilterYear.size() > 1) {
        String[] st = firstFilterYear.get(1).getText().split(",");
        String year = st[st.length - 1].split(" ")[0];
        return year;
      }
    }
    return selectedFilterYear.getText();
  }

  // This method may can change the method above this comment
  public String getCheckedYear() {
    getCurrentCheckedYear();
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedFilterYear);
    waitForElementToAppear(selectedFilterYear, "Selected year is not visible");
    return selectedFilterYear.getText();
  }
  
  private void getCurrentCheckedYear() {
    selectedFilterYear = driver.findElement(By.cssSelector(".sa-sidebar-data-radio-selected .sa-sidebar-data-radio-label"));
  }
  
  private void getCurrentFilters(){
    filters =  driver.findElements(By.cssSelector(".sa-sidebar-data-checkbox-button"));
  }

  @Step("Click on a random filter")
  public BaseFilter addRandomFilter() {
    getCurrentFilters();
    int index = new Random().nextInt(filters.size() - 1);
    logger.debug("Click on a random filter");
    logger.debug("Filter index: " + index);
    logger.debug("Filter name: " + driver.findElements(By.cssSelector(".sa-sidebar-data-checkbox-label")).get(index).getText());
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filters.get(index));//added to prevent stale element exception
    waitForElementToBeClickable(filters.get(index), "Element is not clickable").click();
    return new BaseFilter(driver);
  }
  
  @Step("Click in the data type checkbox {1}")
  public void clickDataType(DataType dataType) {
    logger.info("Check data type: " + dataType);
    driver.findElement(By.xpath(".//div[contains(@class, 'sa-sidebar-data-checkbox-group')]"
        + "[.//div[normalize-space(.)='Data Type']]//button[@data-value='" + dataType.getType().toLowerCase() + "']")).click();
  }

}
