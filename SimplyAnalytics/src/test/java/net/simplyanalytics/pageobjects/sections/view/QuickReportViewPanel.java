package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewPanelQuickReport;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

//TODO implement this page object
public class QuickReportViewPanel extends TableViewPanelQuickReport {
  
  @FindBy(css = ".sa-quick-report-first-col-data-row-with-addable-attribute")
  private List<WebElement> unselectedVariables;
  
  public QuickReportViewPanel(WebDriver driver) {
    super(driver, ViewType.QUICK_REPORT);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    
    waitForElementToAppearWithCustomTime(tableHeader, "The row of coloumn headers is missing", 3000);
    waitForElementToAppear(lockedColumn, "The column of row headers is missing");
  }
  
  /**
   * Getting row headers.
   * 
   * @return row headers
   */
  public Map<String, List<String>> getRowHeaders() {
    Map<String, List<String>> map = new HashMap<>();
    for (WebElement section : lockedColumn.findElements(By.cssSelector(".sa-quick-report-first-col-section"))) {
      String sectionName = section.findElement(By.cssSelector(".sa-quick-report-first-col-section-header")).getText();
      List<String> rowHeaders = new ArrayList<>();
      for (WebElement rowHeaderElement : section.findElements(By.cssSelector(".sa-quick-report-first-col-data-row"))) {
        rowHeaders.add(rowHeaderElement.getText());
      }
      map.put(sectionName, rowHeaders);
    }
    return map;
  }
  
  public String getLocationType(String locationName) {
    return getColumnHeader(locationName).findElement(By.cssSelector(".sa-quick-report-header-location-type")).getText();
  }
  
  /**
   * Getting column headers.
   * 
   * @return column headers
   */
  public List<String> getColumnHeaders() {
    List<String> list = new ArrayList<>();
    waitForAllElementsToAppearByLocator(By.cssSelector(".sa-quick-report-header-location-identifier"),"Not all table headers are displayed.");
    for (WebElement header : tableHeader.findElements(By.cssSelector(".sa-quick-report-header-location-identifier"))) {
      list.add(header.getText());
    }
    return list;
  }
  //Added for elementsa to be visible N.B.
  public List<String> getColumnHeadersWithScroll() {
    List<String> columnHeaders = new ArrayList<>();

    // Find all elements with the specified locator
    List<WebElement> headerElements = driver.findElements(By.cssSelector(".sa-quick-report-header-location-identifier"));

    // Create an Actions object for performing actions like click and scroll
    Actions actions = new Actions(driver);

    // Start with the first element
    WebElement firstElement = headerElements.get(0);
    actions.click(firstElement).perform();

    // Add the text of the first element to the list
    columnHeaders.add(firstElement.getText());

    // Scroll to the right to view the next element
    scrollRight();

    // Iterate through the rest of the elements
    for (int i = 1; i < headerElements.size(); i++) {
      WebElement element = headerElements.get(i);
      actions.click(element).perform();
      columnHeaders.add(element.getText());
      scrollRight();
    }

    return columnHeaders;
  }

  // Function to scroll to the right
  private void scrollRight() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(80, 0)"); // You may need to adjust the scroll amount based on your UI

  }
  // Placeholder for the waitForAllElementsToAppearByLocator method
  private void waitForAllElementsToAppearByLocator(WebDriver driver, By locator, String errorMessage) {
    // Implement your waiting logic here

  }


  /**
   * Getting cell value.
   */
  public String getCellValue(String rowName, String columnName) {
    WebElement column = getColumnHeader(columnName);
    String locationID = column.findElement(By.cssSelector("[data-location-series]"))
        .getAttribute("data-location-series");
    
    WebElement row = getRowHeader(rowName);
    String variableID = row.getAttribute("data-attributes");
    
    return table
        .findElement(
            By.cssSelector("[data-attribute = '" + variableID + "']" + "[data-location-series = '" + locationID + "']"))
        .getText();
  }
  
  private WebElement getColumnHeader(String columnHeader) {
    return tableHeader.findElement(By.xpath(".//div[contains(@class,'sa-quick-report-header-cell')]"
        + "[.//span[text()=" + xpathSafe(columnHeader) + "]]"));
  }
  
  private WebElement getRowHeader(String columnHeader) {
    return lockedColumn.findElement(By.xpath(
        ".//div[contains(@class,'sa-quick-report-first-col-data-row')]" + "[text()=" + xpathSafe(columnHeader) + "]"));
  }
  
  public void addVariableToProject(DataVariable variable) {
    for (WebElement actualVariable : unselectedVariables) {
      if (actualVariable.getText().split(",")[0].equals(variable.getName())) {
        logger.debug("Add " + variable.getName() + " variable to project");
        actualVariable.findElement(By.cssSelector("span[title='Add Variable(s) to Project']")).click();
        waitForElementToDisappear(actualVariable);
      }
    }
  }
}
