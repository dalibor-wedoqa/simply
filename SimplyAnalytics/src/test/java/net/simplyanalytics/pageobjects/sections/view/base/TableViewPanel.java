package net.simplyanalytics.pageobjects.sections.view.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.ViewType;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class TableViewPanel extends GenericTableViewPanel {

  private static final int ROW_LIMIT = 20;

  @FindBy(xpath = "//div[contains(text(), 'City')]")
  protected WebElement cityColumn;

  @FindBy(className = "x-panel")
  protected WebElement xPanel;

  @FindBy(css = ".sa-grid")
  protected WebElement tableRoot;

  @FindBy(css = ".sa-grid .sa-grid-header")
  protected WebElement tableHeader;

  @FindBy(css = ".sa-report-grid-row-header")
  protected WebElement tableRowHeader;

  @FindBy(css = ".sa-grid .sa-grid-normal table")
  protected WebElement table;

  @FindBy(css = ".sa-grid .sa-grid-locked table")
  protected WebElement lockedColumn;

  //NB added for verification on the elements
  @FindBy(css = "x-menu-item-text")
  protected WebElement getModalWindowAfterClick;
  
  private By cellLoading = By.cssSelector(".sa-report-grid-empty-cell-loading");

  protected static final By firstRowLocator = By.cssSelector(".sa-report-grid-column-text-first");
  
  public TableViewPanel(WebDriver driver, ViewType viewType) {
    super(driver, viewType);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();

    waitForElementToAppear(tableRoot, "The table grid is missing");
    /* It takes 30 seconds to get an alert:: 
     * Contacting the server took longer than expected. The request has been aborted.*/
    waitForLoadingToDisappear(tableRoot, 180);
  }
  
  public boolean isTableLoaded() {
    return waitForLoadingElementToDisappear(driver, cellLoading, 150);
  }

  //NB code with scroll

/*
//works new scroll row goes to the desired column
*/
//  @Step("Click on column header with name: {0}")
//  public HeaderDropdown openColumnHeaderDropdownTrick(String columnName) {
//    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));
//
//    WebElement targetColumnHeader = null;
//    for (WebElement columnHeader : columnHeaders) {
//      if (columnHeader.getText().trim().equals(columnName)) {
//        targetColumnHeader = columnHeader;
//        break;
//      }
//    }
//
//    if (targetColumnHeader != null) {
//      // Scroll to the column header element and a bit more to the right
//      scrollToElement(targetColumnHeader);
//
//      // Click on the column header or perform any other desired action
//      targetColumnHeader.click();
//
//      // Assuming HeaderDropdown is a page object representing the dropdown
//      WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
//      return new HeaderDropdown(this, driver, dropdownRoot);
//    } else {
//      throw new RuntimeException("Column header with name '" + columnName + "' not found.");
//    }
//  }
//
//  private void scrollToElement(WebElement element) {
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//    // Scroll to the element
//    js.executeScript("arguments[0].scrollIntoView(true);", element);
//    // Scroll a bit more to the right (adjust the value '100' as needed)
//    js.executeScript("window.scrollBy(100, 0);");
//  }


  //trick one


  /*

  @Step("Click on column header with name: {0}")
  public HeaderDropdown openColumnHeaderDropdownTrick(String columnName) {
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

    int columnIndex = -1;
    for (int i = 0; i < columnHeaders.size(); i++) {
      if (columnHeaders.get(i).getText().trim().equals(columnName)) {
        columnIndex = i;
        break;
      }
    }

    if (columnIndex != -1) {
      // Scroll to the column header element
      scrollToColumnHeader(columnIndex);

      // Scroll to the corresponding row element
      scrollToRowElement(columnIndex);

      // Click on the column header or perform any other desired action
      columnHeaders.get(columnIndex).click();

      // Find the dropdown menu after clicking the column header
      WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

      // Return the HeaderDropdown object
      return new HeaderDropdown(this, driver, dropdownRoot);
    } else {
      throw new RuntimeException("Column header with name '" + columnName + "' not found.");
    }
  }

  private void scrollToColumnHeader(int index) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement columnHeader = driver.findElements(By.cssSelector(".sa-grid-column")).get(index);
    // Scroll the main container to the column header element
    js.executeScript("arguments[0].scrollIntoView(true);", columnHeader);
    // Scroll a bit more to the right (adjust the value '100' as needed)
    js.executeScript("window.scrollBy(100, 0);");
  }

  private void scrollToRowElement(int index) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    List<WebElement> rowCells = driver.findElements(By.cssSelector(".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']"));
    WebElement rowCell = rowCells.get(index);
    // Scroll the main container to the row element
    js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
    // Scroll a bit more to the right (adjust the value '100' as needed)
    js.executeScript("window.scrollBy(100, 0);");
  }
*/



  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumn(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the row elements
    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";

    // Get the list of row cells
    List<WebElement> rowCells = driver.findElements(By.cssSelector(rowCellLocator));

    // Scroll through the first 9 elements
    for (int i = 0; i < Math.min(9, rowCells.size()); i++) {
      WebElement rowCell = rowCells.get(i);
      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
      // Scroll a bit more to the right (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(100, 0);");

      // Check if this is the last element we are scrolling
      if (i == 8 || i == rowCells.size() - 1) {
        // Get the column headers
        List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

        // Ensure the column index is within bounds
        if (i < columnHeaders.size()) {
          WebElement columnHeader = columnHeaders.get(i);

          // Check if the column name matches
          if (columnHeader.getText().trim().equals(columnName)) {
            // Click on the column header
            columnHeader.click();

            // Find the dropdown menu after clicking the column header
            WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

            // Return the HeaderDropdown object
              return new HeaderDropdown(this, driver, dropdownRoot);
          } else {

            try {
              Thread.sleep(20000);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }

            throw new RuntimeException("Column header with name '" + columnName + "' not found at the expected index.");
          }
        } else {
          throw new RuntimeException("Column index is out of bounds.");
        }
      }
    }

    // If we reach here, it means the column header was not found within the first 9 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnSecondTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the row elements
    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";

    // Get the list of row cells
    List<WebElement> rowCells = driver.findElements(By.cssSelector(rowCellLocator));

    // Scroll through the first 20 elements
    for (int i = 0; i < Math.min(20, rowCells.size()); i++) {
      WebElement rowCell = rowCells.get(i);
      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
      // Scroll a bit more to the right (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(100, 0);");

      // Get the column headers
      List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

      // Ensure the column index is within bounds
      if (i < columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i);

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {
          // Click on the column header
          columnHeader.click();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }
    }

    // If we reach here, it means the column header was not found within the first 20 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the first 20 elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnThirdTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the row elements
    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through the first 20 elements
    for (int i = 0; i < 8; i++) {
      WebElement rowCell = driver.findElements(By.cssSelector(rowCellLocator)).get(i);

      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Check if the column index is within bounds
      if (i < columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i);

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {
          // Scroll a bit more to the right (adjust the value '100' as needed)
          js.executeScript("window.scrollBy(100, 0);");

          // Click on the column header
          columnHeader.click();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }

      // Scroll a bit more to the right after each row element (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(100, 0);");
    }

    // If we reach here, it means the column header was not found within the first 20 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the first 20 elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnFourthTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Actions actions = new Actions(driver);

    // Define the CSS selector for the row elements
    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through the first 20 elements
    for (int i = 0; i < 5; i++) {
      WebElement rowCell = driver.findElements(By.cssSelector(rowCellLocator)).get(i);

      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Check if the column index is within bounds
      if (i < columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i);

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {
          // Scroll a bit more to the right (adjust the value '100' as needed)
          js.executeScript("window.scrollBy(10, 0);");

          // Perform double-click with a 0.2-second pause between clicks
          actions.moveToElement(columnHeader).click().perform();
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          actions.moveToElement(columnHeader).click().perform();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }

      // Scroll a bit more to the right after each row element (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(10, 0);");
    }

    // If we reach here, it means the column header was not found within the first 20 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the first 20 elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnFifthTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through up to 11 elements
    for (int i = 2; i < 9; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));

      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Check if the column index is within bounds
      if (i <= columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i - 1); // Adjust index to zero-based

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {

          // Scroll a bit more to the right (adjust the value '100' as needed)
          js.executeScript("window.scrollBy(100, 0);");

          // Click on the column header
          columnHeader.click();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }

      // Scroll a bit more to the right after each row element (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(50, 0);");
    }

    // If we reach here, it means the column header was not found within the first 11 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the first 11 elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnSixthTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through up to 11 elements
    for (int i = 3; i <= 11; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));

      // Scroll the main container to the row cell element first
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Pause for half a second (adjust as needed)
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Check if the column index is within bounds
      if (i <= columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i - 1); // Adjust index to zero-based

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {
          // Scroll a bit more to the right (adjust the value '100' as needed)
          js.executeScript("window.scrollBy(100, 0);");

          // Click on the column header
          columnHeader.click();

          // Pause for 0.2 seconds (adjust as needed)
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          // Click on the column header again
          columnHeader.click();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }

      // Scroll a bit more to the right after each row element (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(100, 0);");
    }

    // If we reach here, it means the column header was not found within the first 11 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the first 11 elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnSeventhTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through up to 11 elements
    for (int i = 2; i < 10; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));

//      //Scroll the main container to the row cell element
//      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Scroll a bit more to the right after each row element (adjust the value '50' as needed)
      js.executeScript("window.scrollBy(100, 0);");

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

//      // Scroll a bit more to the right after each row element (adjust the value '50' as needed)
//      js.executeScript("window.scrollBy(100, 0);");

      // Check if the column index is within bounds
      if (i <= columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i - 1); // Adjust index to zero-based

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {

          // Scroll a bit more to the right (adjust the value '100' as needed)
          js.executeScript("window.scrollBy(100, 0);");

          // Click on the column header
          columnHeader.click();

          // Pause for 0.2 seconds between clicks
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          // Click on the column header again
          columnHeader.click();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }
    }

    // If we reach here, it means the column header was not found within the first 20 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnEightTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through up to 11 elements
    for (int i = 2; i < 10; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));

      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Scroll a bit more to the right after each row element (adjust the value '150' as needed)
      js.executeScript("window.scrollBy(150, 0);");

      // Check if the column index is within bounds
      if (i <= columnHeaders.size()) {
        WebElement columnHeader = columnHeaders.get(i - 1); // Adjust index to zero-based

        // Check if the column name matches
        if (columnHeader.getText().trim().equals(columnName)) {

          // Ensure the column is fully visible by scrolling a bit more to the right (adjust the value '200' as needed)
          js.executeScript("arguments[0].scrollIntoView({inline: 'end'});", columnHeader);
          js.executeScript("window.scrollBy(200, 0);");

          // Click on the column header
          columnHeader.click();

          // Pause for 0.2 seconds between clicks
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          // Click on the column header again
          columnHeader.click();

          // Find the dropdown menu after clicking the column header
          WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

          // Return the HeaderDropdown object
          return new HeaderDropdown(this, driver, dropdownRoot);
        }
      }
    }

    // If we reach here, it means the column header was not found within the first 20 elements
    throw new RuntimeException("Column header with name '" + columnName + "' not found within the elements.");
  }

  @Step("Scroll and click on column with name: {0}")
  public HeaderDropdown scrollAndClickColumnNinthTry(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the row cell elements
    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(";
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Scroll through up to 9 elements
    for (int i = 2; i < 9; i++) {

      // Construct the CSS selector for the row cell element dynamically
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator + i + ")"));

      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
      js.executeScript("window.scrollBy(150, 0);");

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // Scroll a bit more to the right after going through all the elements
    js.executeScript("window.scrollBy(150, 0);");

    // Click on the specified column
    for (WebElement columnHeader : columnHeaders) {
      if (columnHeader.getText().trim().equals(columnName)) {
        // Click on the column header again
        columnHeader.click();

        // Find the dropdown menu after clicking the column header
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

        // Return the HeaderDropdown object
        return new HeaderDropdown(this, driver, dropdownRoot);
      }
    }

    // If we reach here, it means the column header was not found
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");
  }

  @Step("Scroll to the right for the first 8 elements")
  public HeaderDropdown scrollRightThroughElements(String columnName) {

    JavascriptExecutor js = (JavascriptExecutor) driver;
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

    for (int i = 2; i <= 8; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));
//
//      // Scroll the main container to the row cell element
//      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

      // Scroll a bit more to the right
      js.executeScript("window.scrollBy(100, 0);");

      // Pause for half a second
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    // Click on the specified column
    for (WebElement columnHeader : columnHeaders) {
      if (columnHeader.getText().trim().equals(columnName)) {
        // Click on the column header again
        columnHeader.click();

        // Find the dropdown menu after clicking the column header
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

        // Return the HeaderDropdown object
        return new HeaderDropdown(this, driver, dropdownRoot);
      }
    }

    // If we reach here, it means the column header was not found
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");

  }

  @Step("Scroll to the right for the first 8 elements")
  public HeaderDropdown scrollRightThroughElementsSecond(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

    for (int i = 2; i <= 11; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));

      // Check if the row cell element is visible
      if (rowCell.isDisplayed()) {
        // Scroll the main container to the row cell element
        js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

        // Scroll a bit more to the right
        js.executeScript("window.scrollBy(80, 0);");

        // Pause for half a second
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        throw new RuntimeException("Row element at index " + i + " is not visible.");
      }
    }

    // Click on the specified column
    for (WebElement columnHeader : columnHeaders) {
      if (columnHeader.getText().trim().equals(columnName)) {
        // Click on the column header again
        columnHeader.click();

        // Find the dropdown menu after clicking the column header
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

        // Return the HeaderDropdown object
        return new HeaderDropdown(this, driver, dropdownRoot);
      }
    }

    // If we reach here, it means the column header was not found
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");
  }

  @Step("Scroll to the right for the first 8 elements")
  public HeaderDropdown scrollRightThroughElementsThird(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

    for (int i = 2; i <= 8; i++) {
      // Construct the CSS selector for the row cell element dynamically
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + i + ")";
      WebElement rowCell = driver.findElement(By.cssSelector(rowCellLocator));

      // Check if the row cell element is visible
      if (rowCell.isDisplayed()) {
        // Scroll the main container to the row cell element
        js.executeScript("arguments[0].scrollIntoView(true);", rowCell);

        // Scroll a bit more to the right
        js.executeScript("window.scrollBy(80, 0);");

        // Pause for half a second
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        throw new RuntimeException("Row element at index " + i + " is not visible.");
      }
    }

    // Click on the specified column
    for (WebElement columnHeader : columnHeaders) {
      if (columnHeader.getText().trim().equals(columnName)) {
        // Click on the column header twice with a 1-second pause between clicks
        columnHeader.click();

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        columnHeader.click();

        // Find the dropdown menu after clicking the column header
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

        // Return the HeaderDropdown object
        return new HeaderDropdown(this, driver, dropdownRoot);
      }
    }

    // If we reach here, it means the column header was not found
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");
  }

  @Step("Scroll grid to the middle of the page and click on column with name: {0}")
  public HeaderDropdown scrollGridToMiddleAndClickColumn(String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Find the grid containing the row elements
    WebElement gridElement = driver.findElement(By.cssSelector(".sa-grid-normal"));

    // Scroll the grid to the middle of the page
    js.executeScript("arguments[0].scrollIntoView({ block: 'center' });", gridElement);

    // Pause for a moment to allow scrolling to complete
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Define the CSS selector for the column elements
    String columnLocator = ".sa-grid-column";

    // Get the list of column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(columnLocator));

    // Click on the specified column
    for (WebElement columnHeader : columnHeaders) {
      if (columnHeader.getText().trim().equals(columnName)) {
        // Click on the column header twice with a 1-second pause between clicks
        columnHeader.click();

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        columnHeader.click();

        // Find the dropdown menu after clicking the column header
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

        // Return the HeaderDropdown object
        return new HeaderDropdown(this, driver, dropdownRoot);
      }
    }

    // If we reach here, it means the column header was not found
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");
  }

  @Step("Scroll to the right for the first 8 elements")
  public HeaderDropdown scrollRightThroughElementsLastTry(String columnName) {
    // Scroll through the first 10 row elements
    for (int i = 1; i <= 10; i++) {
      String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";
      scrollToElement(rowCellLocator);
    }

    // Click on the specified column header
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));
    for (WebElement columnHeader : columnHeaders) {
      if (columnHeader.getText().trim().equals(columnName)) {
        // Click on the column header twice with a pause between clicks
        clickElement(columnHeader);
        try {
          Thread.sleep(500); // Adjust sleep time as needed
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        clickElement(columnHeader);

        // Find the dropdown menu after clicking the column header
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

        // Return the HeaderDropdown object
        return new HeaderDropdown(this, driver, dropdownRoot);
      }
    }

    // If column not found, throw an exception
    throw new RuntimeException("Column header with name '" + columnName + "' not found.");
  }

  // Method to scroll to an element
  private void scrollToElement(String locator) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement element = driver.findElement(By.cssSelector(locator));
    js.executeScript("arguments[0].scrollIntoView(true);", element);
    // Optionally scroll a bit more to the right
    js.executeScript("window.scrollBy(100, 0);"); // Adjust scroll amount as needed
    try {
      Thread.sleep(500); // Pause for scrolling
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // Method to click on an element
  private void clickElement(WebElement element) {
    element.click();
  }

  // Method to scroll through the first 10 elements and click on a specified column
  public HeaderDropdown scrollRightThroughElementsLastDitch(String columnName) {
    // Locator for the grid rows
    String rowLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";
    // Locator for the column headers
    String columnLocator = ".sa-grid-column";

    // Scroll through the first 10 elements
    for (int i = 0; i < 10; i++) {
      WebElement element = driver.findElement(By.cssSelector(rowLocator));
      // Perform scrolling action to the right a little bit for each element
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft += 1000;", element);
    }

    // Click on the specified column
    WebElement columnElement = driver.findElement(By.cssSelector(columnLocator));
    columnElement.click();

    // Find the dropdown menu after clicking the column header
    WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

    // Return the HeaderDropdown object
    return new HeaderDropdown(this, driver, dropdownRoot);
  }

  // Method to scroll through the first 10 elements and click on a specified column
  public HeaderDropdown scrollAndClickColumns(String columnName) {
    List<String> columnHeaders = new ArrayList<>();

    // Locator for the grid rows (adjust as per your specific selector)
    String rowLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";
    // Locator for the column headers (adjust as per your specific selector)
    String columnLocator = ".sa-grid-column";

    // Find all elements with the specified locator
    List<WebElement> headerElements = driver.findElements(By.cssSelector(rowLocator));

    // Create an Actions object for performing actions like click and scroll
    Actions actions = new Actions(driver);

    // Iterate through the first 10 elements or less if fewer are found
    int count = Math.min(headerElements.size(), 10);
    for (int i = 0; i < count; i++) {
      WebElement element = headerElements.get(i);

      // Perform actions on each element
      actions.click(element).perform();

      // Add the text of the element to the list (assuming it's the column header text)
      columnHeaders.add(element.getText());

      // Scroll to the right to view the next element
      scrollRight(element);
    }

    // Click on the specified column after scrolling through the elements
    WebElement columnElement = driver.findElement(By.cssSelector(columnLocator));
    columnElement.click();

    // Find the dropdown menu after clicking the column header
    WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

    // Return the HeaderDropdown object
    return new HeaderDropdown(this, driver, dropdownRoot);
  }

  // Function to scroll to the right relative to an element
  private void scrollRight(WebElement element) {
    // Adjust the scroll amount based on your UI and requirements
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft += 100;", element);
  }

  // Method to scroll to a specific point within the grid container
  private void scrollToTheColumnElement() {
    // Locator for the grid container
    String gridLocator = ".sa-grid-normal[style='margin-left: 235px; max-height: 435px; max-width: 714px;']";

    // Find the grid container element
    WebElement gridElement = driver.findElement(By.cssSelector(gridLocator));

    // Scroll to a specific point within the grid container (e.g., 200 pixels to the right)
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 200;", gridElement);
  }

  // Method to scroll to the right for the first 8 elements, locate the specified column, click on it, and return HeaderDropdown
  @Step("Scroll to the right for the first 8 elements")
  public HeaderDropdown returnTheSpecifiedColumn(String columnName) {
    // Call the method to scroll to the specific point
    scrollToTheColumnElement();

    // Locator for the column elements
    String columnLocator = ".sa-grid-column";

    // Find the specified column element by its text
    WebElement columnElement = driver.findElement(By.xpath("//div[contains(@class, 'sa-grid-column') and text()='" + columnName + "']"));

    // Click on the specified column
    columnElement.click();

    // Find the dropdown menu after clicking the column header
    WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

    // Return the HeaderDropdown object
    return new HeaderDropdown(this, driver, dropdownRoot);
  }





/*
// new scrll with row and then stop scrooling after finding the element column
*/
//  @Step("Click on column header with name: {0}")
//  public HeaderDropdown openColumnHeaderDropdownTrickII(String columnName) {
//    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));
//
//    int columnIndex = -1;
//    for (int i = 0; i < columnHeaders.size(); i++) {
//      if (columnHeaders.get(i).getText().trim().equals(columnName)) {
//        columnIndex = i;
//        break;
//      }
//    }
//
//    if (columnIndex != -1) {
//      // Scroll through the row elements until the desired column header is visible
//      scrollToColumnHeaderAndRow(columnIndex);
//
//      // Click on the column header or perform any other desired action
//      columnHeaders.get(columnIndex).click();
//
//      // Find the dropdown menu after clicking the column header
//      WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
//
//      // Return the HeaderDropdown object
//      return new HeaderDropdown(this, driver, dropdownRoot);
//    } else {
//      throw new RuntimeException("Column header with name '" + columnName + "' not found.");
//    }
//  }
//
//  private void scrollToColumnHeaderAndRow(int columnIndex) {
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//    List<WebElement> rowCells = driver.findElements(By.cssSelector(".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']"));
//
//    // Scroll through row elements until the column header and row element are visible
//    for (WebElement rowCell : rowCells) {
//      // Check if the row cell index matches the column index
//      if (rowCells.indexOf(rowCell) == columnIndex) {
//        // Scroll the main container to the row cell element
//        js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
//        // Scroll a bit more to the right (adjust the value '100' as needed)
//        js.executeScript("window.scrollBy(100, 0);");
//
//        // Check if the column header is visible
//        WebElement columnHeader = driver.findElement(By.cssSelector(".sa-grid-column:nth-of-type(" + (columnIndex + 1) + ")"));
//        if (isElementVisible(columnHeader)) {
//          break; // Stop scrolling once column header is visible
//        }
//      }
//    }
//  }

/*
// Scroll column element fin the column take index scroll row element with tha index doesn't work
*/

//  @Step("Click on column header with name: {0}")
//  public HeaderDropdown openColumnHeaderDropdownTrick(String columnName) {
//    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));
//
//    int columnIndex = -1;
//    for (int i = 0; i < columnHeaders.size(); i++) {
//      if (columnHeaders.get(i).getText().trim().equals(columnName)) {
//        columnIndex = i;
//        break;
//      }
//    }
//
//    if (columnIndex != -1) {
//      // Scroll to the corresponding row element
//      scrollToRowElement(columnIndex);
//
//      // Scroll to the column header element
//      scrollToColumnHeader(columnIndex);
//
//      // Click on the column header or perform any other desired action
//      columnHeaders.get(columnIndex).click();
//
//      // Find the dropdown menu after clicking the column header
//      WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
//
//      // Return the HeaderDropdown object
//      return new HeaderDropdown(this, driver, dropdownRoot);
//    } else {
//      throw new RuntimeException("Column header with name '" + columnName + "' not found.");
//    }
//  }
//
//  private void scrollToColumnHeader(int index) {
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//    WebElement columnHeader = driver.findElements(By.cssSelector(".sa-grid-column")).get(index);
//    // Scroll the main container to the column header element
//    js.executeScript("arguments[0].scrollIntoView(true);", columnHeader);
//    // Scroll a bit more to the right (adjust the value '100' as needed)
//    js.executeScript("window.scrollBy(100, 0);");
//  }
//
//  private void scrollToRowElement(int columnIndex) {
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//
//    // Define the CSS selector for the row elements based on the column index
//    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']:nth-of-type(" + (columnIndex + 1) + ")";
//
//    List<WebElement> rowCells = driver.findElements(By.cssSelector(rowCellLocator));
//
//    for (WebElement rowCell : rowCells) {
//      // Scroll the main container to the row cell element
//      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
//      // Scroll a bit more to the right (adjust the value '100' as needed)
//      js.executeScript("window.scrollBy(100, 0);");
//
//      // Check if the row cell is visible
//      if (isElementVisible(rowCell)) {
//        break; // Stop scrolling once the row cell is visible
//      }
//    }
//  }


  /*
  //stop scrolling when the specified column element is visible
  */

  @Step("Click on column header with name: {0}")
  public HeaderDropdown openColumnHeaderDropdownTrick(String columnName) {
    // Find all column headers
    List<WebElement> columnHeaders = driver.findElements(By.cssSelector(".sa-grid-column"));

    System.out.println(" net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.BusinessesSortingTests.testSortingByLatitude - COLUMN HEADER NAMES\n" + columnHeaders);

    int columnIndex = -1;
    for (int i = 0; i < columnHeaders.size(); i++) {
      if (columnHeaders.get(i).getText().trim().equals(columnName)) {
        columnIndex = i;
        break;
      }
    }

    if (columnIndex != -1) {
      // Scroll to the corresponding row element until the column header is visible
      scrollToColumnHeaderAndRow(columnIndex);

      // Click on the column header or perform any other desired action
      columnHeaders.get(columnIndex).click();

      // Find the dropdown menu after clicking the column header
      WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));

      // Return the HeaderDropdown object
      return new HeaderDropdown(this, driver, dropdownRoot);
    } else {
      throw new RuntimeException("Column header with name '" + columnName + "' not found.");
    }
  }

  private void scrollToColumnHeaderAndRow(int columnIndex) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Define the CSS selector for the row elements
    String rowCellLocator = ".sa-grid-normal > .sa-grid-table .sa-grid-row > [class='sa-grid-cell sa-grid-cell-normal']";

    // Get the list of row cells
    List<WebElement> rowCells = driver.findElements(By.cssSelector(rowCellLocator));

    for (WebElement rowCell : rowCells) {
      // Scroll the main container to the row cell element
      js.executeScript("arguments[0].scrollIntoView(true);", rowCell);
      // Scroll a bit more to the right (adjust the value '100' as needed)
      js.executeScript("window.scrollBy(100, 0);");

      // Check if the column header is visible
      WebElement columnHeader = driver.findElement(By.cssSelector(".sa-grid-column:nth-of-type(" + (columnIndex + 1) + ")"));
      if (isElementVisible(columnHeader)) {
        break; // Stop scrolling once the column header is visible
      }
    }
  }

  // Helper method to check if an element is visible
  private boolean isElementVisible(WebElement element) {
    try {
      return element.isDisplayed();
    } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.ElementNotVisibleException e) {
      return false;
    }
  }

  public void clickAndSortByColumnName(WebDriver driver, String name) {
    System.out.println("Finding column");
    try {
      WebDriverWait wait = new WebDriverWait(driver, 5);
      JavascriptExecutor js = (JavascriptExecutor) driver;

      // Locate the column header
      WebElement columnHeader = driver.findElement(By.xpath("//div[contains(@class, 'sa-grid-column') and text()='" + name + "']"));

      // Scroll to the element using JavaScript
//      js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", name);
//      System.out.println("Scrolled to '" + name + "' column header.");

      // Click the column header to open the dropdown
      columnHeader.click();
      System.out.println("'" + name + "' column clicked.");

      // Wait for dropdown to appear
      WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sa-dropdown-menu")));
      System.out.println("Dropdown appeared.");

      // Click sorting option (Modify selector if necessary)
      WebElement sortOption = dropdown.findElement(By.xpath(".//[contains('Sort A-Z')]"));
      sortOption.click();
      System.out.println("Sorting option clicked.");

    } catch (NoSuchElementException | TimeoutException e) {
      System.out.println("Element not found or action timed out: " + e.getMessage());
      Assert.fail("Failed to locate and interact with sorting dropdown.");
    }
  }


  @Step("Click on column header with name: {0}")
  public HeaderDropdown openColumnHeaderDropdownTry(String columnName) {
    logger.debug("Click on column header with name: " + columnName);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    long endTime = System.currentTimeMillis() + 1000; // 1 seconds timeout
    //WebDriverWait wait = new WebDriverWait(driver, 10);
    clickAndSortByColumnName(driver, columnName);

    while (System.currentTimeMillis() < endTime) {
      try {
        List<WebElement> columns = tableHeader.findElements(By.cssSelector(".sa-grid-column"));

        // Click on the second and third elements first
        for (int i = 1; i <= 2 && i < columns.size(); i++) {
          WebElement columnElement = columns.get(i);
          logger.debug("Checking column: " + getInnerText(columnElement));

          // Scroll to the element before clicking
          js.executeScript("arguments[0].scrollIntoView();", columnElement);

          //columnElement.click();
          //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sa-report-grid-menu"))); // Adjust selector as per your modal
          //columnElement.click();

          if ((isChildPresent(columnElement, firstRowLocator) && getInnerText(columnElement.findElement(firstRowLocator)).contains(columnName))
                  || getInnerText(columnElement).contains(columnName)) {
            logger.debug("Found the target column: " + columnName);

            // Click on the column and return immediately after clicking
            columnElement.click();
            WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
            return new HeaderDropdown(this, driver, dropdownRoot);
          }

          // Scroll to the next two elements to position the target column closer to the middle
          if (i + 2 < columns.size()) {
            js.executeScript("arguments[0].scrollIntoView();", columns.get(i + 2));
          } else if (i + 1 < columns.size()) {
            js.executeScript("arguments[0].scrollIntoView();", columns.get(i + 1));
          }
        }

        // Click on subsequent elements and scroll before each click
        for (int i = 3; i < columns.size(); i++) {
          WebElement columnElement = columns.get(i);
          logger.debug("Checking column: " + getInnerText(columnElement));

          // Scroll to the element before clicking
          js.executeScript("arguments[0].scrollIntoView();", columnElement);

//          columnElement.click();
//          wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sa-report-grid-menu"))); // Adjust selector as per your modal
//          columnElement.click();

          if ((isChildPresent(columnElement, firstRowLocator) && getInnerText(columnElement.findElement(firstRowLocator)).contains(columnName))
                  || getInnerText(columnElement).contains(columnName)) {
            logger.debug("Found the target column: " + columnName);

            // Click on the column and return immediately after clicking
            columnElement.click();
            WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
            return new HeaderDropdown(this, driver, dropdownRoot);
          }

          // Scroll to the next two elements to position the target column closer to the middle
          if (i + 2 < columns.size()) {
            js.executeScript("arguments[0].scrollIntoView();", columns.get(i + 2));
          } else if (i + 1 < columns.size()) {
            js.executeScript("arguments[0].scrollIntoView();", columns.get(i + 1));
          }
        }

        logger.debug("Target column not found in the current view. Scrolling.");
        // Scroll right by the width of the last column in the view
        js.executeScript("window.scrollBy(arguments[0], 0);", columns.get(columns.size() - 1).getSize().getWidth());
        Thread.sleep(10); // Wait for a short period before retrying
      } catch (Exception e) {
        logger.debug("Exception occurred: " + e.getMessage());
        try {
          Thread.sleep(10); // Wait for a short period before retrying
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt(); // Restore interrupted state
          throw new RuntimeException(ie);
        }
      }
    }

    throw new NoSuchElementException("Column header with name " + columnName + " not found after scrolling.");
  }





  public List<List<String>> getCellValuesTry(WebDriver driver, String... columnName) {
    WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds wait time
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<List<String>> result = new ArrayList<>();
    List<List<String>> values = new ArrayList<>();

    for (String column : columnName) {
      values.add(getCellValuesForSingleColumnStable(driver, wait, js, column));
    }

    int maxSize = values.stream().mapToInt(List::size).max().orElse(0);

    for (int i = 0; i < maxSize; i++) {
      result.add(new ArrayList<>());
    }

    for (List<String> column : values) {
      for (int i = 0; i < maxSize; i++) {
        if (i < column.size()) {
          result.get(i).add(column.get(i));
        } else {
          result.get(i).add("");
        }
      }
    }
    return result;
  }

  private List<String> getCellValuesForSingleColumnStable(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String column) {
    List<String> cellValues = new ArrayList<>();
    boolean firstElement = true; // Flag to check if it's the first element

    for (int rowIndex = 0; rowIndex < ROW_LIMIT; rowIndex++) {
      try {
        WebElement cellElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".sa-grid-row:nth-child(" + (rowIndex + 1) + ") > .sa-grid-cell.sa-grid-cell-locked:nth-child(" + (getColumnIndex(column) + 1) + ")")));

        // Extract the text from the current element
        cellValues.add(cellElement.getText());

        // Scroll a bit every third element
        if ((rowIndex + 1) % 3 == 0) {
          js.executeScript("window.scrollBy(0, 100);"); // Scroll down by 200 pixels
        }

        // Optionally, add a small pause to see the scrolling effect
        try {
          Thread.sleep(1); // 500 milliseconds pause
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        // Element not found, break out of the loop
        break;
      }
    }
    return cellValues;
  }

  private int getColumnIndex(String columnName) {
    // Implement the logic to find the column index based on the column name
    // For example, if you have a mapping of column names to indices, return the index here
    // This is a placeholder implementation
    switch (columnName.toLowerCase()) {
      case "column1":
        return 0;
      case "column2":
        return 1;
      // Add cases for other columns as needed
      default:
        return -1; // Return -1 or throw an exception if the column is not found
    }
  }

  public List<List<String>> getRowCellValuesForBussinesSearch(WebDriver driver, String... columnNames) {
    WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds wait time
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<List<String>> result = new ArrayList<>();
    List<List<String>> values = new ArrayList<>();

    for (String column : columnNames) {
      values.add(getCellValuesForSingleRow(driver, wait, js, column));
    }

    int maxSize = values.stream().mapToInt(List::size).max().orElse(0);

    for (int i = 0; i < maxSize; i++) {
      result.add(new ArrayList<>());
    }

    for (List<String> column : values) {
      for (int i = 0; i < maxSize; i++) {
        if (i < column.size()) {
          result.get(i).add(column.get(i));
        } else {
          result.get(i).add("");
        }
      }
    }
    return result;
  }

  private List<String> getCellValuesForSingleRow(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String column) {
    List<String> cellValues = new ArrayList<>();
    boolean firstElement = true; // Flag to check if it's the first element

    for (int rowIndex = 0; rowIndex < ROW_LIMIT; rowIndex++) {
      try {
        WebElement cellElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".sa-grid-row:nth-child(" + (rowIndex + 1) + ") > .sa-grid-cell.sa-grid-cell-normal:nth-child(" + (getColumnIndex(column) + 1) + ")")));

        // Extract the text from the current element
        cellValues.add(cellElement.getText());

        // Scroll a bit every third element
        if ((rowIndex + 1) % 3 == 0) {
          js.executeScript("window.scrollBy(0, 100);"); // Scroll down by 200 pixels
        }

        // Optionally, add a small pause to see the scrolling effect
        try {
          Thread.sleep(500); // 500 milliseconds pause
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        // Element not found, break out of the loop
        break;
      }
    }
    return cellValues;
  }

  //End of NB integarted coding



  public List<List<String>> getCellValues(String... columnName) {
    List<List<String>> result = new ArrayList<>();
    List<List<String>> values = new ArrayList<>();

    for (String column: columnName) {
      values.add(getCellValuesForSingleColumn(ROW_LIMIT, column));
    }

    int maxSize = 0;
    for (int i = 0; i < values.size(); i++) {
      if (values.get(i).size() > maxSize) {
        maxSize = values.get(i).size();
      }
    }

    for(int i = 0; i < maxSize; i++) {
      result.add(new ArrayList<>());
    }
    for (List<String> column: values) {
      for(int i = 0; i < maxSize; i++) {
        if (i < column.size()) {
          result.get(i).add(column.get(i));
        } else {
          result.get(i).add("");
        }
      }
    }
    return result;
  }

  /**
   * . This method returns the first maxCount (+15) value from the given column
   * The scroll is not 100% accurate, has a chance that a value will be added
   * multiple times ~0.1% for each item
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

    // tableRoot.findElement(By.cssSelector(".sa-grid-normal")).getCssValue("max-height")
    // .replace("px",// "");

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
          logger.trace("value: " + cellValue);
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
      if (oldScrolled + scroll - scrolled > 25) {
        //The scroll reached the end of the table, and duplicate rows are expected
        break;
      }
    }
    return results;
  }

  public List<String> getCellValuesForSingleColumn(int maxCount, String columnName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollTop -= " + getPixelsToTop() + 1000,
        tableRoot.findElement(By.cssSelector(".sa-grid-normal")));
    
    List<String> results = new ArrayList<>();
    
    int scrolled = getTableScrolled();
    int oldScrolled = scrolled - 1;
    
    int columnLocation = scrollColumnInView(columnName);
    
    while (results.size() < maxCount && oldScrolled != scrolled) {
      int scroll = -10;
      for (WebElement row : table.findElements(By.cssSelector(".sa-grid-row"))) {
        logger.trace("row");
        scroll += row.getSize().getHeight();        
        String cellValue = row.findElements(By.cssSelector(".sa-grid-cell")).stream()
            .filter(cell -> Math.abs(cell.getLocation().getX() - columnLocation) < 5).findFirst().get()
            .getAttribute("innerHTML");
        logger.trace("cellValue: " + cellValue);
        results.add(cellValue);
      }
      logger.trace("scroll");
      js.executeScript("arguments[0].scrollTop += " + scroll,
          tableRoot.findElement(By.cssSelector(".sa-grid-normal")));
      
      oldScrolled = scrolled;
      scrolled = getTableScrolled();
      if (oldScrolled + scroll - scrolled > 25) {
        //The scroll reached the end of the table, and duplicate rows are expected
        break;
      }      
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

  protected abstract int scrollToRow(String rowName);

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

  public abstract String getCellValue(String row, String header);

  protected abstract WebElement getCellElement(String row, String header);

  protected int scrollColumnInView(String columnName) {

    WebElement column = getColumn(columnName);

    JavascriptExecutor js = (JavascriptExecutor) driver;
    while (column.getLocation().getX() + column.getSize().getWidth() > tableHeader.getLocation()
        .getX() + tableHeader.getSize().getWidth()) {
      js.executeScript("arguments[0].scrollLeft += 250",
          tableRoot.findElement(By.cssSelector(".sa-grid-normal")));
    }

    return column.getLocation().getX();
  }

  protected WebElement getColumn(String columnName) {
    for (WebElement column : tableHeader.findElements(By.cssSelector(".sa-grid-column"))) {
      if ((isChildPresent(column, firstRowLocator) && getInnerText(column.findElement(firstRowLocator)).contains(columnName))
          || getInnerText(column).contains(columnName)) {
        return column;
      }
    }

    throw new AssertionError("No column with the give name: " + columnName);
  }

  protected int getTableScrolled() {
    return Integer.parseInt(table.getCssValue("margin-top").replace("px", "").trim());
  }

  protected List<WebElement> getAllCells() {
    List<WebElement> list = table.findElements(By.cssSelector("tr td"));
    list.addAll(lockedColumn.findElements(By.cssSelector("tr td")));
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
   * @return list of locked header values
   */
  public List<String> getLockedHeaderValues() {
    return tableHeader.findElements(By.cssSelector(".sa-grid-header-locked .sa-grid-column"))
        .stream().map(element -> getInnerText(element))
        .collect(Collectors.toList());
  }

  /**
   * Getting normal header values.
   * @return list of normal header values
   */
  public List<String> getNormalHeaderValues() {
    return tableHeader.findElements(By.cssSelector(".sa-grid-header-normal .sa-grid-column"))
        .stream().map(element -> 
            isChildPresent(element, firstRowLocator)
              ?getInnerText(element.findElement(firstRowLocator)).split("Dataset")[0].trim()
              :getInnerText(element).split("Dataset")[0].trim())
        .collect(Collectors.toList());
  }

  public List<String[]> getNormalHeaderValuesArray() {
    return tableHeader.findElements(By.cssSelector(".sa-grid-header-normal .sa-grid-column"))
            .stream()
            .map(element -> {
              String text = isChildPresent(element, firstRowLocator)
                      ? getInnerText(element.findElement(firstRowLocator))
                      : getInnerText(element);

              // Split the text by "Dataset" and then further process it
              String[] splitText = text.split("Dataset");
              // Assuming the actual name is the first part of the split text
              String name = splitText[0].trim();

              // Remove the year if it's the last part of the string separated by a comma
              // This regex will match a comma followed by a space and a 4-digit year at the end of the string
              name = name.replaceAll(", \\d{4}$", "").trim();

              // Further processing if needed to clean the name (e.g., removing badges, etc.)
              name = name.replaceAll("\\(.*?\\)", "").trim();

              // Return the cleaned name in a single-element array
              return new String[]{name};
            })
            .collect(Collectors.toList());
  }


  /**
   * Getting normal header values with badges.
   * @return list of normal header values and badges
   */
  public List<String[]> getNormalHeaderValuesAndBadges() {
    List<String[]> dataList = new ArrayList<String[]>();
    List<WebElement> headerValues = tableHeader.findElements(By.cssSelector(".sa-grid-header-normal .sa-grid-column"));
    for(WebElement dataValue : headerValues) {
            String dataName = isChildPresent(dataValue, firstRowLocator)
              ?StringEscapeUtils.unescapeHtml4(getInnerText(dataValue.findElement(firstRowLocator)).split("Dataset")[0].trim())
              :StringEscapeUtils.unescapeHtml4(getInnerText(dataValue).split("Dataset")[0].trim());
              String badge = "";
              if(isChildPresent(dataValue, firstRowLocator)) {
                badge = dataValue.findElement(By.cssSelector(".sa-report-grid-column-text-last .sa-attribute-badge")).getText().trim();
              }
              else {
                badge = dataValue.findElement(By.cssSelector(".sa-attribute-badge")).getText().trim();
              }
            String[] data = {dataName, badge};
            dataList.add(data);
    }
    return dataList;
  }

  public List<String[]> getDataVariablesAndBadgesWithoutEstimateAndProjData() {
    List<String[]> dataList = new ArrayList<String[]>();
    List<WebElement> headerValues = tableHeader.findElements(By.cssSelector(".sa-grid-header-normal .sa-grid-column"));

    for (WebElement dataValue : headerValues) {
      // Scroll a little bit to the right
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(50, 0);", dataValue);

      String dataName = isChildPresent(dataValue, firstRowLocator)
              ? StringEscapeUtils.unescapeHtml4(getInnerText(dataValue.findElement(firstRowLocator)).split("Dataset")[0].trim())
              : StringEscapeUtils.unescapeHtml4(getInnerText(dataValue).split("Dataset")[0].trim());

      // Remove "est" or "proj" if present
      dataName = dataName.replaceAll("\\b(est|proj)\\b", "").trim();

      String badge = "";
      if (isChildPresent(dataValue, firstRowLocator)) {
        badge = dataValue.findElement(By.cssSelector(".sa-report-grid-column-text-last .sa-attribute-badge")).getText().trim();
      } else {
        badge = dataValue.findElement(By.cssSelector(".sa-attribute-badge")).getText().trim();
      }

      String[] data = { dataName, badge };
      dataList.add(data);
    }

    return dataList;
  }


  public List<String> getColumnHeaderValues(){
    List<String> headerValues = getLockedHeaderValues();
    headerValues.addAll(getNormalHeaderValues());
    return headerValues;
  }
  
  /**
   * Getting row header values.
   * @return list of row header values
   */
  public List<String> getRowHeaderValues() {
    return lockedColumn.findElements(By.cssSelector(".sa-grid-row")).stream()
        .map(element -> getInnerText(element.findElement(By.cssSelector(".sa-grid-cell"))).split("Dataset")[0].trim().toString())
        .collect(Collectors.toList());
  }
  
  //Base Table content
  public List<List<String>> getTableContents() throws IOException {
    logger.trace("Get table content");
    List<String> headerValues = getColumnHeaderValues();
    int numberOfColumns = headerValues.size();
    
    int numberOfRows = getRowHeaderValues().size();
    int i = 1;
    
    List<List<String>> tableContent = new ArrayList<>();
    
    while (i < numberOfColumns) {
      List<String> column = getCellValuesForSingleColumn(numberOfRows, headerValues.get(i));
      tableContent.add(column);
      i++;
    }
   
    return tableContent;    
  }
  
  /**
   * Click on column header with name.
   * @param columnName column header name
   * @return HeaderDropdown
   */
  @Step("Click on column header with name: {0}")
  public HeaderDropdown openColumnHeaderDropdown(String columnName) {
    logger.debug("Click on column header with name: " + columnName);
    JavascriptExecutor jr = (JavascriptExecutor) driver;
    while (true) {
      try {
        getColumn(columnName).click();
        WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
        return new HeaderDropdown(this, driver, dropdownRoot);
      } catch (Exception error) {
        logger.debug("Element was not found, scrolling.");
        jr.executeScript("arguments[0].scrollIntoView();",getColumn(columnName));
      } catch (Error error) {
        logger.debug("Element was not found, scrolling.");
        jr.executeScript("arguments[0].scrollIntoView();",getColumn(columnName));
      }
    }
//    getColumn(columnName).click();
//    WebElement dropdownRoot = driver.findElement(By.cssSelector(".sa-report-grid-menu"));
//    return new HeaderDropdown(this, driver, dropdownRoot);
  }

  @Step("Close the header column dropdown by clicking on it")
  public void closeColumnHeaderDropdown(String columnName) {
    logger.debug("Close the header column dropdown by clicking on it: " + columnName);
    getColumn(columnName).click();
  }

  @Step("Close the header row dropdown by clicking on it")
  public void closeRowHeaderDropdown(String rowName) {
    logger.debug("Close the header row dropdown by clicking on it: " + rowName);
    getRowElement(rowName).click();
  }

  /**
   * Click on row header with name.
   * @param rowName row header name
   * @return HeaderDropdown
   */
  @Step("Click on row header with name: {0}")
  public HeaderDropdown openRowHeaderDropdown(String rowName) {
    logger.debug("Click on row header with name: " + rowName);
    WebElement locked = getRowHeaderElement(rowName)
        .findElement(By.xpath(".//td[contains(.," + xpathSafe(rowName) + ")]"));
    try {
      locked.click();
    } catch (ElementNotInteractableException e) {
      WebElement normal = getRowElement(rowName)
          .findElement(By.xpath(".//td[contains(.," + xpathSafe(rowName) + ")]"));
      normal.click();
    }
    WebElement dropdownRoot = waitForElementToAppearByLocator(By.cssSelector(".sa-report-grid-menu"), "Table invisible");
    return new HeaderDropdown(this, driver, dropdownRoot);
  }

  @Step("Click on row header with name: {0}")
  public void openRowHeaderDropdownVoid(String rowName) {
    logger.debug("Click on row header with name: " + rowName);
    WebElement locked = getRowHeaderElement(rowName)
            .findElement(By.xpath(".//td[contains(.," + xpathSafe(rowName) + ")]"));
    try {
      locked.click();
    } catch (ElementNotInteractableException e) {
      WebElement normal = getRowElement(rowName)
              .findElement(By.xpath(".//td[contains(.," + xpathSafe(rowName) + ")]"));
      normal.click();
    }
  }

  /**
   * Click on the cell.
   * @param row row
   * @param column column
   * @return CellDropdown
   */
  @Step("Click on the cell (row = {0}, column = {1})")
  public CellDropdown openCellDropdown(String row, String column) {
    logger.debug("Click on the cell (row = " + row + ", column = " + column + ")");
    waitForElementToBeClickable(getCellElement(row, column), "Cell element is not clickable").click();
    sleep(500);
    WebElement root = waitForElementToAppear(driver.findElement(By.cssSelector(".sa-report-grid-menu")), "Dropdown should appear");
    return new CellDropdown(driver, root);
  }
  
  public void waitForColumnElementToChange(String columnName) {
    waitForElementToAppear(tableRoot.findElement(By.xpath(".//div[contains(@class, 'sa-grid-header-normal')]//div[contains(@class, 'sa-grid-column') "
        + "and contains(normalize-space(.), '" + columnName.split("\\[")[0].trim() + "') and contains(normalize-space(.), '" + columnName.split("\\[")[1].trim() + "')]")),
        "Column with name is not present");
  }
}
