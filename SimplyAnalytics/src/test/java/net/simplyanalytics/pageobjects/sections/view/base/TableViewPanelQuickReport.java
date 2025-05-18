package net.simplyanalytics.pageobjects.sections.view.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;

import org.openqa.selenium.*;
//import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class TableViewPanelQuickReport extends GenericTableViewPanel {

  @FindBy(css = ".sa-quick-report")
  protected WebElement tableRoot;

  @FindBy(css = ".sa-quick-report-header")
  protected WebElement tableHeader;

  @FindBy(css = ".sa-quick-report-body-wrap")
  protected WebElement table;

  @FindBy(css = ".sa-quick-report-first-col-sections")
  protected WebElement lockedColumn;

  public TableViewPanelQuickReport(WebDriver driver, ViewType viewType) {
    super(driver, viewType);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();

    waitForElementToAppear(tableRoot, "The table grid is missing");
    waitForLoadingToDisappear(tableRoot);
  }

  /**
   * This method returns the first maxCount (+15) value from the given column The
   * scroll is not 100% accurate, has a chance that a value will be added multiple
   * times ~0.1% for each item
   * 
   */
  // TODO fix the scroll accuracy
  public List<List<String>> getCellValues(int maxCount, String... columnNames) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    js.executeScript("arguments[0].scrollTop -= " + getPixelsToTop() + 1000,
        tableRoot.findElement(By.cssSelector(".sa-grid-normal")));

    List<List<String>> results = new ArrayList<>();

    int scrolled = getTableScrolled();
    int oldScrolled = scrolled - 1;

    // tableRoot.findElement(By.cssSelector(".sa-grid-normal"))
    // .getCssValue("max-height").replace("px",
    // "");

    while (results.size() < maxCount && oldScrolled != scrolled) {
      int scroll = -10;
      for (WebElement row : table.findElements(By.cssSelector(".sa-grid-row"))) {
        scroll += row.getSize().getHeight();
        List<String> rowValues = new ArrayList<>();
        for (String columnName : columnNames) {
          int columnLocation = scrollColumnInView(columnName);
          String cellValue = row.findElements(By.cssSelector(".sa-grid-cell")).stream()
              .filter(cell -> cell.getLocation().getX() == columnLocation).findFirst().get()
              .getAttribute("innerHTML");
          rowValues.add(cellValue);
        }
        if (results.contains(rowValues)
            && Math.abs(results.indexOf(rowValues) - results.size()) < 5) {
          continue;
        } else {
          results.add(rowValues);
        }
      }

      js.executeScript("arguments[0].scrollTop += " + scroll,
          tableRoot.findElement(By.cssSelector(".sa-grid-normal")));

      oldScrolled = scrolled;
      scrolled = getTableScrolled();
    }
    return results;
  }

  public Map<String, String> getRow(String dataVariableFullName) {
    return getNormalHeaderValues().stream().collect(
        Collectors.toMap(header -> header, header -> getCellValue(dataVariableFullName, header)));
  }

  public Map<String, String> getLockedRow(String dataVariableFullName) {
    return getLockedHeaderValues().stream().collect(
        Collectors.toMap(header -> header, header -> getCellValue(dataVariableFullName, header)));
  }

  protected WebElement getRowHeaderElement(String rowName) {
    int y = scrollToRow(rowName);
    return lockedColumn.findElements(By.cssSelector("tr")).stream()
        .filter(webElement -> webElement.getLocation().getY() == y).findFirst().get();
  }

  protected WebElement getRowElement(String rowName) {
    int y = scrollToRow(rowName);
    return table.findElements(By.cssSelector("tr")).stream()
        .filter(webElement -> webElement.getLocation().getY() == y).findFirst().get();
  }

  public Map<String, String> getColumns(String column) {
    return getRowHeaderValues().stream()
        .collect(Collectors.toMap(row -> row, row -> getCellValue(row, column)));
  }

  public String getCellValue(DataVariable row, String columnName) {
    return getCellValue(row.getFullName(), columnName);
  }

  public String getCellValue(String row, String column) {
    return getCellElement(row, column).getAttribute("innerHTML").replaceAll("<[^>]*>", "")
        .replaceAll("&nbsp;", " ");
  }

  protected WebElement getCellElement(String row, String column) {
    int x = scrollColumnInView(column);
    int y = scrollToRow(row);

    return getAllCells().stream()
        .filter(webElement -> webElement.getLocation().equals(new Point(x, y))).findAny().get();
  }

  /**
   * Scroll to row.
   * 
   * @param rowHeaderName row header name
   * @return
   */
  public int scrollToRow(String rowHeaderName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    By locator = By.xpath("//div[contains(@class, 'sa-quick-report-first-col-row') and contains(.,"
        + xpathSafe(rowHeaderName) + ")]");
    while (!isChildPresent(lockedColumn, locator) && getPixelsToBottom() > 0) {
      js.executeScript("arguments[0].scrollTop += " + 500,
          tableRoot.findElement(By.cssSelector(".sa-quick-report-body")));
    }

    if (!isChildPresent(lockedColumn, locator)) {
      throw new AssertionError("No row with the given name: " + rowHeaderName);
    }

    return lockedColumn.findElement(locator).getLocation().getY();
  }

  protected int scrollColumnInView(String columnName) {

    WebElement column = getColumn(columnName);

    JavascriptExecutor js = (JavascriptExecutor) driver;
    while (column.getLocation().getX() + column.getSize().getWidth() > tableHeader.getLocation()
        .getX() + tableHeader.getSize().getWidth()) {
      js.executeScript("arguments[0].scrollLeft += 250",
          tableRoot.findElement(By.cssSelector(".sa-quick-report-body")));
      // .sa-quick-report-body-row.sa-quick-report-body-cell-pair
    }

    return column.getLocation().getX();
  }

  protected WebElement getColumn(String columnName) {
    return tableHeader.findElement(By.xpath("//div[@class='sa-quick-report-header-cell']"
        + "[.//span[@class='sa-quick-report-header-name'][normalize-space(text())='" + columnName
        + "']]"));
  }

  protected int getTableScrolled() {
    return Integer.parseInt(table.getCssValue("margin-top").replace("px", "").trim());
  }

  protected List<WebElement> getAllCells() {
    List<WebElement> list = table.findElements(By.cssSelector(".sa-quick-report-body-value-cell"
        + ".sa-quick-report-body-value-cell.sa-quick-report-body-cell-left"));
    list.addAll(lockedColumn.findElements(
        By.cssSelector(".sa-quick-report-first-col-row.sa-quick-report-first-col-data-row")));
    return list;
  }

  protected int getPixelsToBottom() {
    return Integer.parseInt(lockedColumn.getCssValue("margin-bottom").replace("px", "").trim());
  }

  protected int getPixelsToTop() {
    return Integer.parseInt(lockedColumn.getCssValue("margin-top").replace("px", "").trim());
  }

  /**
   * Getting locked header values.
   * 
   * @return list of locked header values
   */
  public List<String> getLockedHeaderValues() {
    return tableHeader.findElements(By.cssSelector(".sa-grid-header-locked .sa-grid-column"))
        .stream().map(element -> element.getAttribute("innerHTML").replaceAll("<[^>]*>", "")
            .replaceAll("&nbsp;", " ").trim())
        .collect(Collectors.toList());
  }

  /**
   * Getting normal header values.
   * 
   * @return list of normal header values
   */
  public List<String> getNormalHeaderValues() {
    return tableHeader.findElements(By.cssSelector(".sa-quick-report-header-name")).stream()
        .map(element -> element.getAttribute("innerHTML").replaceAll("<[^>]*>", "")
            .replaceAll("&nbsp;", " ").trim())
        .collect(Collectors.toList());
  }
  
  public List<String> getColumnHeaderValues(){
    List<String> headerValues = getLockedHeaderValues();
    headerValues.addAll(getNormalHeaderValues());
    return headerValues;
  }

  /**
   * Getting roe header values.
   * 
   * @return list of row header values
   */
  public List<String> getRowHeaderValues() {
    return lockedColumn.findElements(By.cssSelector(".sa-grid-row")).stream()
        .map(element -> element.findElement(By.cssSelector(".sa-grid-cell")).getText())
        .collect(Collectors.toList());

  }

  public List<List<String>> getTableContents() throws IOException{
    logger.trace("Get table content");
    List<List<String>> tableContent = new ArrayList<>();
    
    List<WebElement> header = tableHeader.findElements(By.cssSelector(".sa-quick-report-header-name"));
    int numberOfColumns = header.size();    
    List<WebElement> listLeft = table.findElements(By.cssSelector(".sa-quick-report-body-value-cell"
        + ".sa-quick-report-body-value-cell.sa-quick-report-body-cell-left"));
    
    int numberOfRows = listLeft.size()/numberOfColumns;
    List<WebElement> listRight = table.findElements(By.cssSelector(".sa-quick-report-body-cell-right"));
    logger.trace("Get text from the table");
    for(int i = 0; i < numberOfColumns; i++) {
      List<String> columnLeft = new ArrayList<>();
      List<String> columnRight = new ArrayList<>();
      
      for(int j = 0; j < numberOfRows; j++) {
        columnLeft.add(listLeft.get(j + i*numberOfRows).getText());
        columnRight.add(listRight.get(j + i*numberOfRows).getText());
      }

      tableContent.add(columnLeft);
      tableContent.add(columnRight);
    }
    return tableContent;
  }
  
  /**
   * Click on column header with name.
   * 
   * @param columnName column name
   * @return HeaderDropdown
   */
  @Step("Click on column header with name: {0}")
  public HeaderDropdown openColumnHeaderDropdown(String columnName) {
    logger.debug("Click on column header with name: " + columnName);
    getColumn(columnName).click();
    WebElement dropdownRoot = waitForElementToAppear(
        driver.findElement(
            By.cssSelector("div[class^='x-panel sa-quick-report-cell-menu sa-arrow-menu']")),
        "Header dropdown has not apeared");
    return new HeaderDropdown(this, driver, dropdownRoot);
  }

  /**
   * Click on row header with name.
   * 
   * @param rowName row name
   * @return HeaderDropdown
   */
  @Step("Click on row header with name: {0}")
  public HeaderDropdown openRowHeaderDropdown(String rowName) {
    logger.debug("Click on row header with name: " + rowName);
    WebElement locked = getRowHeaderElement(rowName)
        .findElement(By.xpath(".//td[contains(.," + xpathSafe(rowName) + ")]"));
    try {
      locked.click();
    } catch (Exception e) {
      WebElement normal = getRowElement(rowName)
          .findElement(By.xpath(".//td[contains(.," + xpathSafe(rowName) + ")]"));
      normal.click();
    }
    WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
    return new HeaderDropdown(this, driver, dropdownRoot);
  }

  /**
   * Click on the cell.
   * 
   * @param row    row
   * @param column column
   * @return CellDropdownQuickReport
   */
  @Step("Click on the cell (row = {0}, column = {1})")
  public CellDropdownQuickReport openCellDropdown(String row, String column) {
    logger.debug("Click on the cell (row = " + row + ", column = " + column + ")");
    getCellElement(row, column).click();
    WebElement root = driver.findElement(By.cssSelector(".sa-quick-report-cell-menu"));
    return new CellDropdownQuickReport(driver, root);
  }

}
