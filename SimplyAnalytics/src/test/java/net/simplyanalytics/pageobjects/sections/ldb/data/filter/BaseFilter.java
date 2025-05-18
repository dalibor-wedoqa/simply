package net.simplyanalytics.pageobjects.sections.ldb.data.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class BaseFilter extends BasePage {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-data-search-conditions");

  protected WebElement root;

  @FindBy(xpath = "//*[contains(text(),'results for')]")
  private WebElement numberOfResults;

  @FindBy(css = "span.sa-data-search-condition")
  private List<WebElement> allFilterElements;

  @FindBy(css = ".sa-data-search-results-wrap")
  private WebElement dataSearchResultsElement;

  public BaseFilter(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  protected FilterYear filterYear = new FilterYear(driver);
  protected FilterData filterData = new FilterData(driver);
  protected FilterText filterText = new FilterText(driver);

  @Override
  protected void isLoaded() {
    waitForElementToAppear(numberOfResults, "The number of results is missing");
  }

  @Step("Search for the number of results")
  public String getNumberOfResults() {
    logger.debug("Search for the number of results");
    return numberOfResults.getText();
  }

  @Step("Verify if there is owerflow filters")
  public String verifyOverflow() {
    if (root.findElement(By.cssSelector(".sa-data-search-conditions-overflow-menu-btn"))
        .isDisplayed()) {
      root.findElement(By.cssSelector(".sa-data-search-conditions-overflow-menu-btn")).click();
      OwerflowDropdown overflow = new OwerflowDropdown(driver);
      allFilterElements = overflow.getAllFilterElements();
      return "Owerflow is present";
    }
    return "Owerflow is not present";
  }

  @Step("Delete the active filters except the year")
  public void deleteAllFilters() {
    logger.debug("Delete the active filters except the year");
    allFilterElements = collectActiveFilters();
    do {
      for (WebElement activeElement : allFilterElements) {
        if (!activeElement.getAttribute("data-type").equals("year")) {
          allFilterElements = clickRemoveFilter(activeElement);
          break;
        }
      }
    } while (allFilterElements.size() > 1);
  }

  @Step("Collect the active filters")
  private List<WebElement> collectActiveFilters() {
    List<WebElement> list = root.findElements(By.cssSelector("span.sa-data-search-condition"));
    return list;
  }

  @Step("Return the list of the filters")
  public List<String> getFilters() {
    List<String> list = new ArrayList<String>();
    for (WebElement filterElement : allFilterElements) {
      list.add(filterElement.getText());
    }
    return list;
  }

  private List<WebElement> clickRemoveFilter(WebElement activeFilter) {
    if (activeFilter.findElement(By.cssSelector(".sa-data-search-condition-remove-wrap"))
        .isDisplayed()) {
      logger.debug("Remove filter: " + activeFilter.findElement(By.cssSelector(".sa-data-search-condition-label")).getText().trim());
      WebElement removedFilter = activeFilter.findElement(By.cssSelector(".sa-data-search-condition-remove-wrap"));
      removedFilter.click();
      waitForElementToDisappear(removedFilter);
      sleep(2000);
    }
    return collectActiveFilters();
  }

  public int getSizeOfFilteredElement(List<String> list) {
    return list.size();
  }

  public List<WebElement> getAllFilterElements() {
    return allFilterElements;
  }

  public void placeActiveFilters() {
    allFilterElements = collectActiveFilters();
  }

  public List<String> filterYear() {
    return filterYear.filterYearClass(allFilterElements);
  }

  public List<String> filterText() {
    return filterText.filterTextClass(allFilterElements);
  }

  public List<String> filterData() {
    return filterData.filterDataClass(allFilterElements, 1);
  }

  public FilterYear getYear() {
    return filterYear;
  }

  public FilterData getData() {
    return filterData;
  }

  public FilterText getText() {
    return filterText;
  }

}
