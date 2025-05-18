package net.simplyanalytics.pageobjects.sections.view;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.simplyanalytics.utils.Helper;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.CrosstabHeaderDropdown;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.simplyanalytics.utils.Helper.measureImplicitWaitTime;

public class CrosstabViewPanel extends TableViewWithoutDataVariableColoumnPanel{
  
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

  public CrosstabViewPanel(WebDriver driver) {
    super(driver, ViewType.CROSSTAB_TABLE);
    this.jsExecutor = (JavascriptExecutor) driver;
  }


  // Function to scroll and collect all column headers
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

  // Function to scroll and collect all row headers
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



  // Function to collect visible column headers in the current view
  private void getCurrentVisibleColumnHeaderValues(List<String> collectedHeaderValues) {
    List<WebElement> currentHeaders = tableRoot.findElements(By.cssSelector(".sa-sectioned-table-top td div"));

    for (WebElement header : currentHeaders) {
      String headerText = header.getText();
      if (!collectedHeaderValues.contains(headerText)) {
        collectedHeaderValues.add(headerText); // Add only unique headers
      }
    }
  }

  // If you want to return the list of collected header values as a String list
  public List<String> getCollectedColumnHeaderValues() {
    return columnHeaderValues.stream().map(WebElement::getText).collect(Collectors.toList());
  }


  @Override
  public void isLoaded() {
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

  public List<String> getRowHeaderValuesCrosstabTableII(){
    List<String> rowValues = new ArrayList<>();
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")))) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollTop = 0",
              tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      long oldScrollTop = 0;
      long newScrollTop = 1;
      while(oldScrollTop != newScrollTop) {
        getCurrentRowHeaderValues();
        //
        /*for(WebElement value : rowHeaderValues) {
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
      */
        //
        for (WebElement value : rowHeaderValues) {
          try {
            logger.debug("Click on value");
            value.click();
            getCurrentCellMenuAttribute();
            String valueName = cellMenuAttribute.getText();

            // provera da li ima 2021 u valueName i obrisi ako ima ti si dodao nemoj da zeznes
            int index = valueName.indexOf("2020");
            if (index != -1) {
              valueName = valueName.substring(0, index);
            }
            if (!rowValues.contains(valueName)) {
              rowValues.add(valueName);
            }
            logger.debug("Click on value");
            value.click();
          }
          catch (ElementClickInterceptedException e) {
            String valueText = value.getText();

            // proveri da li ima 2021 i obrisi ti si dodao nemoj da zeznes
            int index = valueText.indexOf("2020");
            if (index != -1) {
              valueText = valueText.substring(0, index);
            }
            if (!rowValues.contains(valueText)) {
              rowValues.add(valueText);
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

  public List<String> getRowHeaderValuesCrosstabTable() {
    List<String> rowValues = new ArrayList<>();
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")))) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollTop = 0",
              tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      long oldScrollTop = 0;
      long newScrollTop = 1;
      WebDriverWait wait = new WebDriverWait(driver, 10); // Adjust the timeout as needed

      while (oldScrollTop != newScrollTop) {
        getCurrentRowHeaderValues();
        for (WebElement value : rowHeaderValues) {
          try {
            wait.until(ExpectedConditions.elementToBeClickable(value)).click();
            getCurrentCellMenuAttribute();
            String valueName = cellMenuAttribute.getText();

            // Remove "2021" if present
            valueName = valueName.replace("2021", "");

            if (!rowValues.contains(valueName)) {
              rowValues.add(valueName);
            }
          } catch (Exception e) {
            // Log the exception
            logger.error("Exception occurred while processing row header values: " + e.getMessage());
          } finally {
            // Ensure the value is clicked again regardless of errors
            value.click();
          }
        }

        oldScrollTop = newScrollTop;
        logger.info("Scroll");
        js.executeScript("arguments[0].scrollTop += 200",
                tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
        try {
          Thread.sleep(100); // Introducing a small delay for stability
        } catch (InterruptedException e) {
          // Handle interruption if needed
          Thread.currentThread().interrupt();
        }
        newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)",
                tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
      }
    }
    return rowValues;
  }

  public List<String> getColumnHeaderValuesCrosstabTable() {
    // Step 1: Initialize the list that will store column values
    List<String> columnValues = new ArrayList<>();
    System.out.println("Initialized columnValues: " + columnValues);

    // Uncomment if needed: Checking if the scroller element is present
//    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")))) {
//      System.out.println("Scroller element is present.");

    // Uncomment if needed: Initialize the JavaScript executor for handling scroll
//      JavascriptExecutor js = (JavascriptExecutor) driver;
//      js.executeScript("arguments[0].scrollLeft = 0",
//              tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
//      System.out.println("Scroller set to left-most position.");

    // Uncomment if needed: Initialize scroll position variables
//      long oldScrollLeft = 0;
//      long newScrollLeft = 1;
//      System.out.println("Scroll positions initialized: oldScrollLeft = " + oldScrollLeft + ", newScrollLeft = " + newScrollLeft);

    // Uncomment if needed: Start scrolling through the table
//      while (oldScrollLeft != newScrollLeft) {
    // Step 2: Fetch the current column header values
    getCurrentColumnHeaderValues();
    System.out.println("Fetched current column header values: " + columnHeaderValues.toString());

    // Uncomment if needed: Additional loop to process each column header
        /*
        for (WebElement value : columnHeaderValues) {
          try {
            logger.debug("Click on the value");
            value.click();
            getCurrentCellMenuAttribute();
            String valueName = cellMenuAttribute.getText().trim();
            if (!columnValues.contains(valueName)) {
              columnValues.add(valueName);
            }
            logger.debug("Click on the value");
            value.click();
          } catch (ElementClickInterceptedException e) {
            if (!columnValues.contains(value.getText())) {
              columnValues.add(value.getText());
            }
          } catch (NoSuchElementException e) {
            logger.info("No such element exception: value is not clickable");
          }
        }
        */

    // Step 3: Loop through each column header value
    for (WebElement value : columnHeaderValues) {
      try {
        logger.debug("Click on the value: " + value.getText());
        value.click(); // Attempt to click on the value
        System.out.println("Clicked on the value: " + value.getText());

        getCurrentCellMenuAttribute(); // Fetch the current cell menu attribute
        String valueName = cellMenuAttribute.getText().trim(); // Get the text and trim whitespace
        System.out.println("Cell menu attribute fetched: " + valueName);

        // Step 4: Check if "2021" is present in the valueName and remove it if found
        int index = valueName.indexOf("2021");
        if (index != -1) {
          valueName = valueName.substring(0, index);
          System.out.println("Removed '2021' from valueName: " + valueName);
        }

        // Step 5: Add the value to columnValues if it's not already present
        if (!columnValues.contains(valueName)) {
          columnValues.add(valueName);
          System.out.println("Added value to columnValues: " + valueName);
        }

        logger.debug("Click on the value again: " + value.getText());
        value.click(); // Click again on the value
        System.out.println("Clicked again on the value: " + value.getText());

      } catch (ElementClickInterceptedException e) {
        String valueText = value.getText().trim();
        System.out.println("Caught ElementClickInterceptedException for value: " + valueText);

        // Step 6: Handle the case when "2021" is present in the value text and remove it
        int index = valueText.indexOf("2021");
        if (index != -1) {
          valueText = valueText.substring(0, index);
          System.out.println("Removed '2021' from valueText: " + valueText);
        }

        // Step 7: Add the intercepted value if it's not already present in columnValues
        if (!columnValues.contains(valueText)) {
          columnValues.add(valueText);
          System.out.println("Added intercepted value to columnValues: " + valueText);
        }

      } catch (NoSuchElementException e) {
        // Step 8: Handle the case where the element is not clickable
        logger.info("No such element exception: value is not clickable");
        System.out.println("NoSuchElementException caught: value is not clickable.");
      }
    }

    // Uncomment if needed: Update the scroll positions and continue scrolling
//        oldScrollLeft = newScrollLeft;
//        logger.info("Scroll updated.");
//        js.executeScript("arguments[0].scrollLeft += 360",
//                tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
//        System.out.println("Scrolled right by 360 pixels.");

    // Uncomment if needed: Introduce a small delay for stability
//        sleep(200);
//        System.out.println("Introduced delay for stability.");

    // Uncomment if needed: Update the new scroll position after scrolling
//        newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)",
//                tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
//        System.out.println("New scrollLeft position: " + newScrollLeft);
//      }
//    }

    // Step 9: Return the collected column values
    System.out.println("Returning columnValues: " + columnValues);
    return columnValues;
  }


  public List<String> getColumnHeaderValuesCrosstabTableII() {
    // Step 1: Initializing the list to store column values
    List<String> columnValues = new ArrayList<>();
    System.out.println("Initialized columnValues: " + columnValues);

    // Step 2: Check if the table root and its scroller are present
    if (isPresent(tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")))) {
      System.out.println("Scroller element is present.");

      // Step 3: Scroll to the left-most position
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollLeft = 0",
              tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      System.out.println("Scrolled to the left-most position.");

      // Step 4: Initializing scroll positions
      long oldScrollLeft = 0;
      long newScrollLeft = 1;
      System.out.println("Initial scroll positions: oldScrollLeft = " + oldScrollLeft + ", newScrollLeft = " + newScrollLeft);

      WebDriverWait wait = new WebDriverWait(driver, 10); // Adjust the timeout as needed
      System.out.println("WebDriverWait initialized with timeout: 10 seconds");

      // Step 5: While loop to scroll and process column headers
      while (oldScrollLeft != newScrollLeft) {
        // Step 6: Get the current column header values
        getCurrentColumnHeaderValues();
        System.out.println("Fetched current column header values: " + columnHeaderValues);

        // Step 7: Loop through each column header element
        for (WebElement value : columnHeaderValues) {
          try {
            // Step 8: Scroll the page to make the element clickable
            js.executeScript("arguments[0].scrollIntoView()", value);
            System.out.println("Scrolled into view for value: " + value.getText());

            wait.until(ExpectedConditions.elementToBeClickable(value)).click();
            System.out.println("Clicked on value: " + value.getText());

            // Step 9: Get the current cell menu attribute
            getCurrentCellMenuAttribute();
            String valueName = cellMenuAttribute.getText().trim();
            System.out.println("Extracted cell menu attribute: " + valueName);

            // Step 10: Remove "2021" if present in the value name
            valueName = valueName.replace("2021", "");
            System.out.println("Processed valueName (after removing '2021'): " + valueName);

            // Step 11: Add the value to the list if it's not already present
            if (!columnValues.contains(valueName)) {
              columnValues.add(valueName);
              System.out.println("Added value to columnValues: " + valueName);
            }
          } catch (Exception e) {
            // Step 12: Log the exception if one occurs
            System.out.println("Exception occurred while processing column header values: " + e.getMessage());
            logger.error("Exception occurred while processing column header values: " + e.getMessage());
          } finally {
            // Step 13: Ensure the value is clicked again regardless of errors
            value.click();
            System.out.println("Clicked value again in finally block: " + value.getText());
          }
        }

        // Step 14: Update the scroll positions
        oldScrollLeft = newScrollLeft;
        System.out.println("Updated oldScrollLeft: " + oldScrollLeft);

        // Step 15: Scroll to the right by 360 pixels
        js.executeScript("arguments[0].scrollLeft += 360",
                tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
        System.out.println("Scrolled right by 360 pixels.");

        // Step 16: Add a short delay for stability
        try {
          Thread.sleep(200); // Introducing a small delay for stability
          System.out.println("Introduced a delay of 200ms.");
        } catch (InterruptedException e) {
          // Step 17: Handle thread interruption
          System.out.println("Thread was interrupted during sleep: " + e.getMessage());
          Thread.currentThread().interrupt();
        }

        // Step 18: Get the new scroll position
        newScrollLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft)",
                tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
        System.out.println("Updated newScrollLeft: " + newScrollLeft);
      }
    } else {
      System.out.println("Scroller element is not present.");
    }

    // Step 19: Return the collected column values
    System.out.println("Returning column values: " + columnValues);
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
  @Step("Open row header dropdown")
  public CrosstabHeaderDropdown openRowHeaderDropdown(int index) {
    logger.info("Click on the row header value by index");
    rowHeaderValues.get(index).click();
    waitForElementToAppearByLocator(By.cssSelector(".sa-arrow-popup"), "Dropdown did not appear");
    return new CrosstabHeaderDropdown(this, driver, driver.findElement(By.cssSelector(".sa-arrow-popup")));
  }
  
  @Step("Open column header dropdown")
  public CrosstabHeaderDropdown openColumnHeaderDropdown(int index) {
    logger.info("Click on the column header value by index");
    columnHeaderValues.get(index).click();
    waitForElementToAppearByLocator(By.cssSelector(".sa-arrow-popup"), "Dropdown did not appear");
    return new CrosstabHeaderDropdown(this, driver, driver.findElement(By.cssSelector(".sa-arrow-popup")));
  }
  
  @Step("Move row element up")
  public void moveRowElementUp(int index) {
    logger.debug("Move element with index " + index + " up by 60px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(rowHeaderValues.get(index), 0, -60).build().perform(); 
  }
  
  @Step("Move row element down")
  public void moveRowElementDown(int index) {
    logger.debug("Move element with index " + index + " down by 60px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(rowHeaderValues.get(index), 0, 60).build().perform(); 
  }
  
  @Step("Move column element left")
  public void moveColumnElementLeft(int index) {
    logger.debug("Move element with index " + index + " left by 180px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(columnHeaderValues.get(index), -180, 0).build().perform(); 
  }
  
  @Step("Move column element right")
  public void moveColumnElementRight(int index) {
    logger.debug("Move element with index " + index + " right by 180px");
    Actions act = new Actions(driver);
    getCurrentRowHeaderValues();
    act.dragAndDropBy(columnHeaderValues.get(index), 180, 0).build().perform(); 
  }
  
  @Step("Move column element")
  public void moveColumnElement(String columnName, int x, int y) {
    logger.debug("Move column element by (" + x + "," + y + ")");
    logger.trace("Column element: " + columnName);
    Actions act = new Actions(driver);
    act.clickAndHold(getColumnHeaderElement(columnName)).pause(100).moveByOffset(0, y).pause(100).moveByOffset(x, 0).pause(100).release().build().perform();
    logger.info("Element is moved");
  }
  
  @Step("Move row element")
  public void moveRowElement(String rowName, int x, int y) {
    logger.debug("Move row element by (" + x + "," + y + ")");
    logger.trace("Row element: " + rowName);
    Actions act = new Actions(driver);
    act.clickAndHold(getRowHeaderElement(rowName)).pause(100).moveByOffset(0, y).pause(100).moveByOffset(x, 0).pause(100).release().build().perform();
    logger.info("Element is moved");
  }
  
  @Override
  public int scrollToRow(String rowName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollLeft = 0",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    long oldScrollTop = 0;
    
    for(WebElement row : rowHeaderValues) {
      if (rowName.endsWith(row.getText().trim().substring(3))) {
        return row.getLocation().getY();
      }
    }
    
    js.executeScript("arguments[0].scrollLeft += 181",
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
    long newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
        tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));
    
    while(oldScrollTop != newScrollTop) {
      for(WebElement row : rowHeaderValues) {
        if (rowName.endsWith(row.getText().trim().substring(3))) {
          return row.getLocation().getY();
        }
      }
      oldScrollTop = newScrollTop;
      js.executeScript("arguments[0].scrollLeft += 181",
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-top .sa-table-section-scroller")));
      newScrollTop = (Long) js.executeScript("return Math.round(arguments[0].scrollTop)", 
          tableRoot.findElement(By.cssSelector(".sa-sectioned-table-left .sa-table-section-scroller")));    
    }
    throw new Error("Data variable with name: " + rowName + " is not present");
  }
  
}
