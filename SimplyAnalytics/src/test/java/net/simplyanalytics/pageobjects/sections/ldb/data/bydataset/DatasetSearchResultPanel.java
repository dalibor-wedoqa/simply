package net.simplyanalytics.pageobjects.sections.ldb.data.bydataset;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.search.DataResultItem;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenuItemWithSubMenu;
import io.qameta.allure.Step;

public class DatasetSearchResultPanel extends BasePage {

  protected static final By ROOT_LOCATOR = By.xpath(".//div[(contains(@class, ' sa-data-search-results') and not(contains(@style, 'display: none;')))]");
  protected WebElement root;
  
  @FindBy(css = ".sa-data-search-result-selected")
  private List<WebElement> defaultCheckedElements;

  @FindBy(css = "span.sa-data-search-results-attribute-name")
  private List<WebElement> allDataElements;
  
  @FindBy(css = ".sa-data-search-results-placeholder")
  private List<WebElement> allHiddenDataElements;
  
  public DatasetSearchResultPanel(WebDriver driver) {
    super(driver);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    // TODO
  }

  /**
   * Getting recent favorite menu item with sub menu.
   * 
   * @param dataVariableName data variable name
   * @return RecentFavoriteMenuItemWithSubMenu
   */
  public RecentFavoriteMenuItemWithSubMenu dataVariableRow(String dataVariableName) {
    WebElement rowElement = root
        .findElement(By.xpath(".//button[@class= 'sa-data-search-results-attribute-body']//span[text()= '" + dataVariableName + ' ' + "']"));
    return new RecentFavoriteMenuItemWithSubMenu(driver, rowElement);
  }
  
  /**
   * Getting recent favorite menu item with sub menu.
   * 
   * @param dataVariableName data variable name
   * @return DataResultItem
   */
  public DataResultItem getDataVariableRow(String dataVariableName) {
    WebElement rowElement = driver
        .findElement(By.xpath(".//button[@class= 'sa-data-search-results-attribute-body']//span[text()= \"" + dataVariableName + " "  + "\"]/../.."));
    return new DataResultItem(driver, rowElement);
  }

  public DataResultItem getDataVariableRowFix(String dataVariableName) {
    // Adjusted XPath to correctly locate the element
    String xpath = ".//button[@class='sa-data-search-results-attribute-body']//span[contains(text(), \"" + dataVariableName + "\")]/../..";
    WebElement rowElement = driver.findElement(By.xpath(xpath));
    return new DataResultItem(driver, rowElement);
  }

  public DataResultItem getDataVariableRowCssSelector(String dataVariableName) {
    // Find all buttons with the specified class
    List<WebElement> elements = driver.findElements(By.cssSelector("button.sa-data-search-results-attribute-body"));

    for (WebElement element : elements) {
      // Find the span element with the specified text within each button
      WebElement spanElement = element.findElement(By.cssSelector("span.sa-data-search-results-attribute-name"));

      // Use JavaScript to match the text content
      String script = "return arguments[0].innerText.includes('" + dataVariableName + "')";
      boolean matches = (Boolean) ((JavascriptExecutor) driver).executeScript(script, spanElement);

      if (matches) {
        // If match found, return the DataResultItem
        return new DataResultItem(driver, element);
      }
    }

    return null; // If no element found
  }


  /**
   * Click on data variable.
   * 
   * @param nameOfData name of data
   */
  @Step("Click on data variable \"{0}\"")
  public void clickOnDataVariable(DataVariable nameOfData) {
    logger.debug("Click on data variable " + nameOfData);
    driver.findElement(By.xpath("//span[contains(@class, sa-data-search-result-name) and contains(normalize-space(.),"
        + xpathSafe(nameOfData.getName()) + ")]")).click();
  }
  
  private void findCurrentDataElements() {
    allDataElements = root.findElements(By.cssSelector("span.sa-data-search-results-attribute-name"));
  }
  
  @Step("Click on random data")
  public String clickOnRandomData() {
    findCurrentDataElements();
    int randomDataIndex = new Random().nextInt(allDataElements.size());
    logger.debug("Click on random data, with index: " + randomDataIndex);
    String dataName = allDataElements.get(randomDataIndex).getText().trim();
    allDataElements.get(randomDataIndex).click();
    logger.debug("Data name: " + dataName);
    return getDataVariableText(allDataElements.get(randomDataIndex));
  }
  
  public List<String> getAllDataVariables() {
    return allDataElements.stream()
        .map(data -> data.getText().trim()).collect(Collectors.toList());
  }
  
  public boolean isResultHidden() {
    return !root.getAttribute("class").contains("sa-data-search-no-results-hidden");
  }
  
  public int getResultsCount() {
    return allHiddenDataElements.size();
  }
  
  @Step("Hover data variable by index {0}")
  public String hoverDataVariableByIndex(int index) {
    logger.debug("Hover hidden data variable by index: " + index);
    Actions action = new Actions(driver);
    action.moveToElement(allHiddenDataElements.get(index)).build().perform();
    WebElement tooltip = waitForElementToAppear(driver.findElement(By.cssSelector(".sa-tooltip-content")), "Tooltip is not visible");
    //sleep(300);
    String tooltipText = tooltip.getAttribute("innerHTML");
    action.moveByOffset(380, 0).build().perform();
    //waitForElementToDisappear(By.cssSelector(".sa-tooltip-content")); -> removed due to the element not always being present for the wait function
    return tooltipText;
  }
  
}
