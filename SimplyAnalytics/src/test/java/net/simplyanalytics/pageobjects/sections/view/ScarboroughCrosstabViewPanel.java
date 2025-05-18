package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.CrosstabHeaderDropdown;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;

public class ScarboroughCrosstabViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  @FindBy(css = ".sa-sectioned-table")
  private WebElement tableRoot;
  
  @FindBy(xpath = ".//div[contains(@class, 'sa-sectioned-table-body')]")
  private WebElement tableBody;
  
  @FindBy(xpath = ".//div[contains(@class, 'sa-sectioned-table-body')]/div/table/tbody/tr")
  private List<WebElement> tableRows;
  
  @FindBy(css = ".sa-sectioned-table-left td div")
  private List<WebElement> rowHeaderValues;
  
  @FindBy(css = ".sa-sectioned-table-top td div")
  private List<WebElement> columnHeaderValues;
  
  @FindBy(css = ".sa-arrow-menu-2-caption")
  private WebElement cellMenuAttribute;

  private JavascriptExecutor jsExecutor;
  
  public ScarboroughCrosstabViewPanel(WebDriver driver) {
    super(driver, ViewType.SCARBOROUGH_CROSSTAB_TABLE);
  }
  
  @Override
  public void isLoaded() {
    /* It takes 30 seconds to get an alert:: 
     * Contacting the server took longer than expected. The request has been aborted.*/
    waitForLoadingToDisappear(tableRoot, 180);
    //waitForElementToAppear(tableRoot, "The table is missing");
  }

  public List<String> collectColumnHeaderValues() {
    // Find the scroller element
    System.out.println("Attempting to find scroller element.");
    WebElement scroller = tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller"));
    System.out.println("Scroller element found: " + scroller);

    // Define scroll amount and initialize variables
    int scrollAmount = 400; // Scroll step amount
    int lastScrollPosition = 0;
    int currentScrollPosition = 0;
    boolean endReached = false;

    System.out.println("Initial variables - scrollAmount: " + scrollAmount
            + ", lastScrollPosition: " + lastScrollPosition
            + ", currentScrollPosition: " + currentScrollPosition
            + ", endReached: " + endReached);

    // List to store collected headers
    List<String> collectedHeaderValues = new ArrayList<>();
    System.out.println("Initialized collectedHeaderValues list: " + collectedHeaderValues);
    // Start scrolling and collecting headers
    while (!endReached) {
      System.out.println("Scrolling loop started. End reached: " + endReached);
      // Collect visible column headers
      List<WebElement> elements = driver.findElements(By.cssSelector(".sa-sectioned-table-top td div"));
      System.out.println("Set wait time to 0");
      driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
      System.out.println("Current visible headers:");
      for (WebElement element : elements) {
        System.out.println(element.getText());
      }
      int headernum = elements.size();
      for (int i = 0; i < headernum; i++) {
        try {
          WebElement header = elements.get(i);
          header.isDisplayed();
          if (!header.getText().equals("TOTAL")) {
            boolean infoIsOpen = false;
            String valueName = "";
            while (!infoIsOpen) {
              System.out.println("Opening info...");
              logger.debug("Click on the value");
              System.out.println(header.getText());
              header.click();
              System.out.println("Element clicked");
              try {
                valueName = driver.findElement(By.cssSelector(".sa-arrow-menu-2-caption")).getText().trim();
                infoIsOpen = true;
              } catch (Exception e) {
                System.out.println("Info is not open");
              }
            }
            if (!collectedHeaderValues.contains(valueName)) {
              collectedHeaderValues.add(valueName);
              System.out.println("New value added:"+valueName);
            } else {
              System.out.println("Value already added");
            }
            logger.debug("Close info box");
            WebElement closeButton = driver.findElements(By.cssSelector(".sa-arrow-menu-2-close-button")).get(0);
            closeButton.click();
          }
        } catch (Exception e) {
          System.out.println("Element stale. Moving on.");
        }

      }

      // Scroll the scroller horizontally
      System.out.println("Scrolling horizontally by " + scrollAmount + " pixels.");
      jsExecutor.executeScript("arguments[0].scrollBy(arguments[1], 0);", scroller, scrollAmount);

      // Get current scroll position
      currentScrollPosition = ((Long) jsExecutor.executeScript("return arguments[0].scrollLeft;", scroller)).intValue();
      System.out.println("Current scroll position: " + currentScrollPosition);

      // Check if the end of the scroll has been reached
      if (Math.abs(currentScrollPosition - lastScrollPosition) < 1) {  // Tolerance of 1 pixel
        endReached = true;
        System.out.println("End of scrolling reached. Stopping.");
      } else {
        lastScrollPosition = currentScrollPosition;
        System.out.println("Scrolling continues. New lastScrollPosition: " + lastScrollPosition);
      }
    }
    System.out.println("Final collected headers: " + collectedHeaderValues);
    return collectedHeaderValues;
  }

  public List<String> collectRowHeaderValues() {
    // Find the scroller element
    System.out.println("Attempting to find scroller element.");
    WebElement scroller = tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller"));
    System.out.println("Scroller element found: " + scroller);

    // Define scroll amount and initialize variables
    int scrollAmount = 200; // Scroll step amount (vertical scrolling)
    int lastScrollPosition = 0;
    int currentScrollPosition = 0;
    boolean endReached = false;

    System.out.println("Initial variables - scrollAmount: " + scrollAmount
            + ", lastScrollPosition: " + lastScrollPosition
            + ", currentScrollPosition: " + currentScrollPosition
            + ", endReached: " + endReached);

    // List to store collected rows
    List<String> collectedRowValues = new ArrayList<>();
    System.out.println("Initialized collectedRowValues list: " + collectedRowValues);

    // Start scrolling and collecting row values
    while (!endReached) {
      System.out.println("Scrolling loop started. End reached: " + endReached);

      // Collect visible rows
      List<WebElement> elements = driver.findElements(By.cssSelector(".sa-sectioned-table-left td div"));
      System.out.println("Current visible rows:");
      for (WebElement element : elements) {
        System.out.println(element.getText());
      }
      int rownum = elements.size();
      for (int i = 0; i < rownum; i++) {
        try {
          WebElement header = elements.get(i);
          header.isDisplayed();
          if (!header.getText().equals("TOTAL")) {
            boolean infoIsOpen = false;
            String valueName = "";
            while (!infoIsOpen) {
              System.out.println("Opening info...");
              logger.debug("Click on the value");
              System.out.println(header.getText());
              header.click();
              System.out.println("Element clicked");
              try {
                valueName = driver.findElement(By.cssSelector(".sa-arrow-menu-2-caption")).getText().trim();
                infoIsOpen = true;
              } catch (Exception e) {
                System.out.println("Info is not open");
              }
            }
            if (!collectedRowValues.contains(valueName)) {
              collectedRowValues.add(valueName);
              System.out.println("New value added: " + valueName);
            } else {
              System.out.println("Value already added");
            }
            logger.debug("Close info box");
            WebElement closeButton = driver.findElements(By.cssSelector(".sa-arrow-menu-2-close-button")).get(0);
            closeButton.click();
          }
        } catch (Exception e) {
          System.out.println("Element stale. Moving on.");
        }
      }

      // Scroll the scroller vertically
      System.out.println("Scrolling vertically by " + scrollAmount + " pixels.");
      jsExecutor.executeScript("arguments[0].scrollBy(0, arguments[1]);", scroller, scrollAmount);

      // Get current vertical scroll position
      currentScrollPosition = ((Long) jsExecutor.executeScript("return arguments[0].scrollTop;", scroller)).intValue();
      System.out.println("Current vertical scroll position: " + currentScrollPosition);

      // Check if the end of the scroll has been reached
      if (Math.abs(currentScrollPosition - lastScrollPosition) < 1) {  // Tolerance of 1 pixel
        endReached = true;
        System.out.println("End of scrolling reached. Stopping.");
      } else {
        lastScrollPosition = currentScrollPosition;
        System.out.println("Scrolling continues. New lastScrollPosition: " + lastScrollPosition);
      }
    }

    System.out.println("Final collected row values: " + collectedRowValues);
    return collectedRowValues;
  }

  @Override
  public List<String> getRowHeaderValues(){
    List<String> rowValues = new ArrayList<>();
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")))) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollTop = 0",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      long oldScrollTop = 0;
      long newScrollTop = 1;
      while(oldScrollTop != newScrollTop) {
        getCurrentRowHeaderValues();
        for(WebElement value : rowHeaderValues) {
          try {
            logger.debug("Click on value");
            value.click();
            getCurrentCellMenuAttribute();
            String valueName = cellMenuAttribute.getText();
            if (!rowValues.contains(valueName)) {
              rowValues.add(valueName);
            }
            logger.debug("Click on value");
            value.click();
         }
          catch (ElementClickInterceptedException e){
            if (!rowValues.contains(value.getText())) {
              rowValues.add(value.getText());
            }
          }
        }
        oldScrollTop = newScrollTop;
        logger.info("Scroll");
        js.executeScript("arguments[0].scrollTop += 200",
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
        sleep(100);
        newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      }
    }
    return rowValues;
  }
  
  @Override
  public List<String> getColumnHeaderValues(){
    List<String> columnValues = new ArrayList<>();
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")))) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollLeft = 0",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      long oldScrollLeft = 0;
      long newScrollLeft = 1;
      while(oldScrollLeft != newScrollLeft) {
        getCurrentColumnHeaderValues();
        for(WebElement value : columnHeaderValues) {
          try {
            logger.debug("Click on the value");
            value.click();
            getCurrentCellMenuAttribute();
            String valueName = cellMenuAttribute.getText().trim();
            System.out.println(valueName);
            if (!columnValues.contains(valueName)) {
              columnValues.add(valueName);
            }
            logger.debug("Click on the value");
            value.click();
          }
          catch (ElementClickInterceptedException e){
            if (!columnValues.contains(value.getText())) {
              columnValues.add(value.getText());
            }
          }
          catch (NoSuchElementException e){
            logger.info("No such element exception: value is not clickable");
          }
        }
        oldScrollLeft = newScrollLeft;
        logger.info("Scroll");
        js.executeScript("arguments[0].scrollLeft += 360",
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
        sleep(200);
        newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)", 
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      }
    }
    return columnValues;
  }
  
  public List<List<String[]>> getTableRowsVerticalDisplay(){
    List<List<String[]>> rows = new ArrayList<List<String[]>>();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollTop = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    for(WebElement rowElement : tableRows) {
      rows.add(getTableRowByElementVerticalDisplay(rowElement));
    }
    
    long oldScrollTop = 0;
    logger.info("Scroll");
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    js.executeScript("arguments[0].scrollTop += 61",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    sleep(100);
    long newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    while(Math.abs(newScrollTop - oldScrollTop) == 61) {
      js.executeScript("arguments[0].scrollLeft = 0",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      getCurrentTableRows();
      rows.add(getTableRowByElementVerticalDisplay(tableRows.get(tableRows.size()-1)));
      
      logger.info("Scroll");
      oldScrollTop = newScrollTop;
      js.executeScript("arguments[0].scrollTop += 61",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      sleep(100);
      newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));    
    }
    return rows;
  }
  
  public List<List<String[]>> getTableRowsSampleDisplay(){
    List<List<String[]>> rows = new ArrayList<List<String[]>>();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollTop = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    for(WebElement rowElement : tableRows) {
      rows.add(getTableRowByElementSampleDisplay(rowElement));
    }
    
    long oldScrollTop = 0;
    logger.info("Scroll");
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    js.executeScript("arguments[0].scrollTop += 61",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    sleep(100);
    long newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    while(Math.abs(newScrollTop - oldScrollTop) == 61) {
      js.executeScript("arguments[0].scrollLeft = 0",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      getCurrentTableRows();
      rows.add(getTableRowByElementSampleDisplay(tableRows.get(tableRows.size()-1)));
      
      logger.info("Scroll");
      oldScrollTop = newScrollTop;
      js.executeScript("arguments[0].scrollTop += 61",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      sleep(100);
      newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));    
    }
    return rows;
  }
  
  private void getCurrentTableRows() {
    tableRows = tableRoot.findElements(By.xpath(".//div[contains(@class, 'sa-sectioned-table-body')]/div/table/tbody/tr"));
  }
  
  public List<String[]> getTableRowByElementVerticalDisplay(WebElement rowElement){
    List<String[]> row = new ArrayList<>();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    sleep(100);
    
    List<WebElement> cells = rowElement.findElements(By.xpath("td"));
    for(WebElement cell : cells) {
      List<WebElement> values = cell.findElements(By.cssSelector(".sa-crosstab-default-cell-section span"));
      String[] cellValues = new String[3];
      for(int i = 0; i < 3; i++) {
        cellValues[i] = values.get(i).getText();
      }
      row.add(cellValues);
    }
    
    long oldScrollLeft = 0;
    logger.info("Scroll");
    js.executeScript("arguments[0].scrollLeft += 181",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    sleep(100);
    long newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)", 
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    while(Math.abs(newScrollLeft - oldScrollLeft) == 181) {
        cells = rowElement.findElements(By.xpath("td"));
        List<WebElement> values = cells.get(cells.size()-1).findElements(By.cssSelector(".sa-crosstab-default-cell-section span"));
        String[] cellValues = new String[3];
        for(int i = 0; i < 3; i++) {
          cellValues[i] = values.get(i).getText();
        }
        row.add(cellValues);
      
      oldScrollLeft = newScrollLeft;
      logger.info("Scroll");
      js.executeScript("arguments[0].scrollLeft += 181",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      sleep(100);
      newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    }
    return row;
  }
  
  public List<String[]> getTableRowByElementSampleDisplay(WebElement rowElement){
    List<String[]> row = new ArrayList<>();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    sleep(100);
    
    List<WebElement> cells = rowElement.findElements(By.xpath("td"));
    for(WebElement cell : cells) {
      List<WebElement> values = cell.findElements(By.xpath("//table//td[contains(@class, 'sa-crosstab-metadata-cell-value')]"));
      String[] cellValues = new String[3];
      for(int i = 0; i < 3; i++) {
        cellValues[i] = values.get(i).getText().trim();
      }
      row.add(cellValues);
    }
    
    long oldScrollLeft = 0;
    logger.info("Scroll");
    js.executeScript("arguments[0].scrollLeft += 181",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    sleep(100);
    long newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)", 
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    while(Math.abs(newScrollLeft - oldScrollLeft) == 181) {
        cells = rowElement.findElements(By.xpath("td"));
        List<WebElement> values = cells.get(cells.size()-1).findElements(By.xpath("//table//td[contains(@class, 'sa-crosstab-metadata-cell-value')]"));
        String[] cellValues = new String[3];
        for(int i = 0; i < 3; i++) {
          cellValues[i] = values.get(i).getText();
        }
        row.add(cellValues);
      
      oldScrollLeft = newScrollLeft;
      logger.info("Scroll");
      js.executeScript("arguments[0].scrollLeft += 181",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      sleep(100);
      newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    }
    return row;
  }
  
  private void getCurrentCellMenuAttribute() {
    cellMenuAttribute = driver.findElement(By.cssSelector(".sa-arrow-menu-2-caption"));
  }
  
  private void getCurrentRowHeaderValues() {
    rowHeaderValues = tableRoot.findElements(By.cssSelector(".sa-sectioned-table-left td div"));
  }
  
  private void getCurrentColumnHeaderValues() {
    columnHeaderValues = tableRoot.findElements(By.cssSelector(".sa-sectioned-table-top td div"));
  }
  
  protected WebElement getRowHeaderElement(String rowName) {
    getCurrentRowHeaderValues();
    rowHeaderValues.remove(0);
    for(WebElement row : rowHeaderValues) {
      if (rowName.contains(row.getText().trim().substring(3))) {
        return row;
      }
    }
    return null;
  }
  
  protected WebElement getColumnHeaderElement(String columnName) {
  getCurrentColumnHeaderValues();
  columnHeaderValues.remove(0);
  for(WebElement column : columnHeaderValues) {
    if (columnName.contains(column.getText().trim().substring(3))) {
      return column;
    }
  }
  return null;
}
  
  public CrosstabHeaderDropdown openRowHeaderDropdown(int index) {
    logger.info("Click on the row header value by index");
    rowHeaderValues.get(index).click();
    waitForElementToAppearByLocator(By.cssSelector(".sa-arrow-popup"), "Dropdown did not appear");
    return new CrosstabHeaderDropdown(this, driver, driver.findElement(By.cssSelector(".sa-arrow-popup")));
  }
  
  public CrosstabHeaderDropdown openColumnHeaderDropdown(int index) {
    logger.info("Click on the column header value by index");
    logger.info("Selected data is: " + columnHeaderValues.get(index).getText());
    columnHeaderValues.get(index).click();
    waitForElementToAppearByLocator(By.cssSelector(".sa-arrow-popup"), "Dropdown did not appear");
    return new CrosstabHeaderDropdown(this, driver, driver.findElement(By.cssSelector(".sa-arrow-popup")));
  }
  
  public void moveRowElementUp(int index) {
    logger.debug("Move element with index " + index + " up by 60px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(rowHeaderValues.get(index), 0, -60).build().perform(); 
  }
  
  public void moveRowElementDown(int index) {
    logger.debug("Move element with index " + index + " down by 60px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(rowHeaderValues.get(index), 0, 60).build().perform(); 
  }
  
  public void moveColumnElementLeft(int index) {
    logger.debug("Move element with index " + index + " left by 180px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(columnHeaderValues.get(index), -180, 0).build().perform(); 
  }
  
  public void moveColumnElementRight(int index) {
    logger.debug("Move element with index " + index + " right by 180px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(columnHeaderValues.get(index), 180, 0).build().perform(); 
  }
  
  public void moveColumnElement(String columnName, int x, int y) {
    logger.debug("Move column element by (" + x + "," + y + ")");
    logger.debug("Column element: " + columnName);
    Actions act = new Actions(driver);
    act.clickAndHold(getColumnHeaderElement(columnName)).pause(100).moveByOffset(0, y).pause(100).moveByOffset(x, 0).pause(100).release().build().perform();
    logger.info("Element is moved");
  }
  
  public void moveRowElement(String rowName, int x, int y) {
    logger.debug("Move row element by (" + x + "," + y + ")");
    logger.debug("Row element: " + rowName);
    Actions act = new Actions(driver);
    act.clickAndHold(getRowHeaderElement(rowName)).pause(100).moveByOffset(0, y).pause(100).moveByOffset(x, 0).pause(100).release().build().perform();
    logger.info("Element is moved");
  }
  
  @Override
  public int scrollToRow(String rowName) {
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")))) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollTop = 0",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      long oldScrollTop = 0;
      
      for(WebElement row : rowHeaderValues) {
        if (rowName.contains(row.getText().trim().substring(3))) {
          return row.getLocation().getY();
        }
      }
      js.executeScript("arguments[0].scrollTop += 200",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      long newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      
      while(oldScrollTop != newScrollTop) {
        for(WebElement row : rowHeaderValues) {
          if (rowName.contains(row.getText().trim().substring(3))) {
            return row.getLocation().getY();
          }
        }
        oldScrollTop = newScrollTop;
        js.executeScript("arguments[0].scrollTop += 200",
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
        newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));    
      }
    }
    else {
      for(WebElement row : rowHeaderValues) {
        if (rowName.contains(row.getText().trim().substring(3))) {
          return row.getLocation().getY();
        }
      }
    }
    throw new Error("Data variable with name: " + rowName + " is not present");
  }
  
  public int scrollToColumn(String columnName) {
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")))) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollLeft = 0",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      long oldScrollTop = 0;
      
      for(WebElement column : columnHeaderValues) {
        if (columnName.contains(column.getText().trim().substring(3))) {
          return column.getLocation().getX();
        }
      }
      
      js.executeScript("arguments[0].scrollLeft += 360",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      long newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      
      while(oldScrollTop != newScrollTop) {
        for(WebElement column : columnHeaderValues) {
          if (columnName.contains(column.getText().trim().substring(3))) {
            return column.getLocation().getX();
          }
        }
        oldScrollTop = newScrollTop;
        js.executeScript("arguments[0].scrollLeft += 360",
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
        newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
            tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));    
      }
    }
    else {
      for(WebElement column : columnHeaderValues) {
        if (columnName.contains(column.getText().trim().substring(3))) {
          return column.getLocation().getX();
        }
      }
    }
    throw new Error("Data variable with name: " + columnName + " is not present");
  }
 
  public void hoverVerticalPercent(String rowData, String columnData) throws Exception {
    int y = scrollToRow(rowData);
    int x = scrollToColumn(columnData);
    
    logger.info("Hover vertical percent (" + x + 31 + "," + y + 30 + ")");
    Actions actions = new Actions(driver);
    actions.moveByOffset(x + 31, y + 30).perform();
    
  }
}
