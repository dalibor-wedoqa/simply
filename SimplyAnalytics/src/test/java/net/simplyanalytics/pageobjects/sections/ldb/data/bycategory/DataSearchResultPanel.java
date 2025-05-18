package net.simplyanalytics.pageobjects.sections.ldb.data.bycategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataSearchResultPanel extends BasePage {

  @FindBy(css = ".sa-data-search-results-wrap")
  private WebElement dataSearchResultsElement;

  @FindBy(css = ".sa-data-search-result-selected")
  private List<WebElement> defaultCheckedElements;

  @FindBy(xpath = ".//span[@class='sa-data-search-results-attribute-name']") 
  private List<WebElement> allDataElements;
  
  @FindBy(css = ".sa-checkbox-button")
  private WebElement groupRelatedDataCheckButton;

  private By searchResultHeader = By.cssSelector(".sa-data-search-result-header");
  private By allCheckedElements = By.cssSelector(".sa-data-search-results-attribute-selected span.sa-data-search-results-attribute-name");
  private DataByCategoryDropwDown dataByCategoryDropwDown;

  public DataSearchResultPanel(WebDriver driver, DataByCategoryDropwDown dataByCategoryDropwDown) {
    super(driver);
    this.dataByCategoryDropwDown = dataByCategoryDropwDown;
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(dataSearchResultsElement, "Data Search Result Panel is not loaded");
  }

  /**
   * Getting all checked results.
   * 
   * @return all checked results
   */
  public List<String> allCheckedResults() {
    List<String> result = new ArrayList<>();
    String year = dataByCategoryDropwDown.getDataFilterResultPanel().getSelectedYear();
    waitForAllElementsToAppearByLocator(allCheckedElements, "Checked Elements are not present ")
        .forEach(element -> result.add(getDataVariableText(element) + ", " + DataVariable.getSeedVariablesYear()));
    return result;
  }

  @Step("Verify is there any results")
  public boolean isResultsFound() {
    By noResultsFound = By.cssSelector(".sa-data-search-no-results");
    return isPresent(noResultsFound);
  }

  protected List<WebElement> getAllDataElements() {
    return allDataElements;
  }
  
  @Step("Verify is there any header present")
  public boolean isHeaderPresent() {
    return isPresent(searchResultHeader);
  }
  
  public List<String> getDataList() {
	return (allDataElements.stream().map(data -> getDataVariableText(data))).collect(Collectors.toList());
  }
}