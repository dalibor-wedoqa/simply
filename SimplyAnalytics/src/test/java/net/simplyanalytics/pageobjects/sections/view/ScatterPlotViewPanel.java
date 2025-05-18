package net.simplyanalytics.pageobjects.sections.view;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;
import net.simplyanalytics.pageobjects.sections.view.scatterplot.ScatterPlotLegendPanel;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScatterPlotViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  @FindBy(css = ".sa-scatter-plot-view-wrap")
  protected WebElement rootElement;
  
  @FindBy(css = ".points>g")
  private WebElement pointContainer;
  
  @FindBy(css = ".sa-scatter-plot-point")
  private List<WebElement> pointElements;
  
  @FindBy(css = ".sa-scatter-plot-point-popup")
  private WebElement pointPopup;
  
  @FindBy(css = ".sa-scatter-plot-point-popup td:nth-child(2)")
  private WebElement popupXValue;
  
  @FindBy(css = ".sa-scatter-plot-point-popup td:nth-child(3)")
  private WebElement popupYValue;

  @FindBy(css = ".axis-x .tick>text")
  private List<WebElement> valuesOfXAxis;
  
  @FindBy(css = ".axis-y .tick>text")
  private List<WebElement> valuesOfYAxis;
  
  @FindBy(css = ".sa-editable-title-narrower textarea")
  private WebElement titleTextBox;
  
  @FindBy(css = ".axis-x tspan")
  private List<WebElement> xAxisTitle;
  
  @FindBy(css = ".axis-y tspan")
  private List<WebElement> yAxisTitle;
  
  @FindBy(css = ".line")
  private WebElement line;
  
  public ScatterPlotViewPanel(WebDriver driver, ViewType viewType) {
    super(driver, viewType);
  }
  
  @Override
  public void isLoaded() {
  }
  
  public ScatterPlotLegendPanel getLegendPanel() {
    return new ScatterPlotLegendPanel(driver);
  }
  
  public String getChartTitle() {
    return titleTextBox.getAttribute("value");
  }
  
  @Step("Enter chart title")
  public void enterChartTitle(String title) {
    titleTextBox.sendKeys(Keys.CONTROL, Keys.chord("a"));  //clear() does not work
    titleTextBox.sendKeys(Keys.BACK_SPACE);
    titleTextBox.sendKeys(title + Keys.ENTER);
  }

  public static void changePointsRepresent(WebDriver driver) {
    try {
      // Locate and click on the second 'Zip Codes' dropdown element
      WebElement dropdownTrigger = driver.findElement(By.xpath("(//span[@class='sa-button-text' and text()='Zip Codes'])[2]"));

      // Debugging outputs
      System.out.println("Dropdown Trigger Text: " + dropdownTrigger.getText());
      System.out.println("Dropdown Trigger Class: " + dropdownTrigger.getAttribute("class"));
      System.out.println("Dropdown Trigger is Displayed: " + dropdownTrigger.isDisplayed());
      System.out.println("Dropdown Trigger is Enabled: " + dropdownTrigger.isEnabled());

      // Ensure the dropdown is clickable
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.elementToBeClickable(dropdownTrigger));

      // Click on the dropdown
      dropdownTrigger.click();
      System.out.println("Successfully clicked on the second 'Zip Codes' dropdown.");

      // Locate all the Block Groups elements in the dropdown
      List<WebElement> blockGroupsList = driver.findElements(By.xpath("//span[contains(text(), 'Block Groups')]"));

      if (blockGroupsList.isEmpty()) {
        System.err.println("No Block Groups elements found.");
      } else {
        // Select the last Block Groups element in the list
        WebElement lastBlockGroup = blockGroupsList.get(blockGroupsList.size() - 1);

        // Debugging output for the last element
        System.out.println("Last Block Group Text: " + lastBlockGroup.getText());
        System.out.println("Last Block Group Class: " + lastBlockGroup.getAttribute("class"));
        System.out.println("Last Block Group is Displayed: " + lastBlockGroup.isDisplayed());
        System.out.println("Last Block Group is Enabled: " + lastBlockGroup.isEnabled());

        // Ensure the last Block Group element is clickable
        wait.until(ExpectedConditions.elementToBeClickable(lastBlockGroup));

        // Click on the last Block Group element
        lastBlockGroup.click();
        System.out.println("Successfully clicked on the last 'Block Groups' element.");
      }

    } catch (NoSuchElementException e) {
      System.err.println("The second 'Zip Codes' or 'Block Groups' element was not found: " + e.getMessage());
    } catch (ElementClickInterceptedException e) {
      System.err.println("Click intercepted for the 'Zip Codes' or 'Block Groups' element. Attempting with JavaScript: " + e.getMessage());

      try {
        WebElement dropdownTrigger = driver.findElement(By.xpath("(//span[@class='sa-button-text' and text()='Zip Codes'])[2]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownTrigger);
        System.out.println("Successfully clicked on the second 'Zip Codes' dropdown using JavaScript.");

        // JavaScript click for the last Block Group element as well
        List<WebElement> blockGroupsList = driver.findElements(By.xpath("//span[contains(text(), 'Block Groups')]"));
        if (!blockGroupsList.isEmpty()) {
          WebElement lastBlockGroup = blockGroupsList.get(blockGroupsList.size() - 1);
          ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lastBlockGroup);
          System.out.println("Successfully clicked on the last 'Block Groups' element using JavaScript.");
        }
      } catch (Exception jsException) {
        System.err.println("JavaScript click also failed: " + jsException.getMessage());
      }
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
  }





  public String getXAxisTitle() {
    String titles = " ";
    List<String> titleList = xAxisTitle.stream()
        .map(title -> title.getText().trim()).collect(Collectors.toList());
    for(String title : titleList) {
      if('-' != titles.charAt(titles.length() - 1)) {
        titles += " ";
      }
      titles += title;
    }
    
    return titles.trim();
  }
  
  public String getYAxisTitle() {
    String titles = " ";
    List<String> titleList = yAxisTitle.stream()
        .map(title -> title.getText().trim()).collect(Collectors.toList());
    for(String title : titleList) {
      if('-' != titles.charAt(titles.length() - 1)) {
        titles += " ";
      }
      titles += title;
    }
    return titles.trim();
  }

  protected void getCurrentPointElements() {
    waitForAllElementsToAppear(driver.findElements(By.cssSelector(".sa-scatter-plot-point")),"Not all points are displayed");
    pointElements = driver.findElements(By.cssSelector(".sa-scatter-plot-point"));
  }
  
  public String getPointsColor() {
    return pointContainer.getAttribute("fill").trim();
  }
  
  public String getPointSizeByIndex(int index) {
    return pointElements.get(index).getAttribute("r").trim();
  }

  public List<String> getAllPointSizes(){
    return pointElements.stream().map(point -> point.getAttribute("r").trim()).collect(Collectors.toList());
  }
  
  public int getPointsCount() {
    getCurrentPointElements();
    return pointElements.size();
  }
  
  @Step("Click on the point by index")
  public void clickToOpenPointPopup(int index) {
    logger.debug("Open popup, point by index: " + index);
    getCurrentPointElements();
    Actions builder = new Actions(driver);
    waitForAllElementsToAppear(pointElements,"All point elements are not displayed");
    builder.moveToElement(pointElements.get(index)).pause(500).click().build().perform();
    try {
      if(!pointPopup.isDisplayed()){
        logger.debug("Popup is not present. Retry with a second action.");
        waitForElementToBeClickable(pointPopup,"Point popup is not clickable");
        builder.moveToElement(pointElements.get(index)).pause(500).click().build().perform();
      } else {
        waitForElementToAppear(pointPopup, "Popup is not present");
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Step("Click on the point by index")
  public void clickToOpenPointPopup2(int index) {
    System.out.println("DEBUG: Open popup, point by index: " + index);
    logger.debug("Open popup, point by index: " + index);

    // Get all current point elements
    System.out.println("DEBUG: Calling getCurrentPointElements to retrieve all point elements.");
    getCurrentPointElements();
    System.out.println(pointElements.size());

    //js clickin
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement element = pointElements.get(index);
    js.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true}));", element);

    try {
      // Check if popup is displayed
      if (!pointPopup.isDisplayed()) {
        System.out.println("DEBUG: Popup is not displayed. Retrying the action.");
        logger.debug("Popup is not present. Retry with a second action.");

        // Wait until the point popup is clickable
        System.out.println("DEBUG: Waiting for the popup to be clickable.");
        waitForElementToBeClickable(pointPopup, "Point popup is not clickable");

        // Retry the move and click action
        System.out.println("DEBUG: Retrying move to element at index " + index + " and clicking.");
        js.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true}));", element);
      } else {
        // Wait until the popup appears
        System.out.println("DEBUG: Popup is displayed. Waiting for the popup to fully appear.");
        waitForElementToAppear(pointPopup, "Popup is not present");
      }
    } catch (Exception e) {
      // Log exception stack trace for debugging
      System.out.println("DEBUG: Exception occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }

  
  @Step("Click on the point by index")
  public void clickToClosePointPopup(int index) {
    logger.debug("Close popup, point by index: " + index);
    getCurrentPointElements();
    Actions builder = new Actions(driver);
    builder.moveToElement(pointElements.get(index)).pause(500).click().build().perform();
  }
  
  public boolean isPointPopupOpen(int index) {
    return isPresent(pointElements.get(index));
  }
  
  public String getPopupXValue() {
    return popupXValue.getText().trim().replace(",", "");
  }
  
  public String getPopupYValue() {
    return popupYValue.getText().trim().replace(",", "");
  }
  
  public List<String> getValuesOfXAxis() {
    List<String> listValuesOfAxis = new ArrayList<String>();
    sleep(1000);
    for (WebElement value : valuesOfXAxis) {
      listValuesOfAxis.add(value.getText().trim());
    }
    return listValuesOfAxis;
  }
  
  public List<String> getValuesOfYAxis() {
    List<String> listValuesOfAxis = new ArrayList<String>();
    for (WebElement value : valuesOfYAxis) {
      listValuesOfAxis.add(value.getText().trim());
    }
    return listValuesOfAxis;
  }
  
  public boolean isLineOfBestFitActive() {
    try {
      line.findElement(By.cssSelector("line"));
      return true;
    }
    catch(Exception e) {
      return false;
    }
  }

  //NB a little bit more stable methods compared to the original
  @Step("Click on the point by index")
  public void clickToOpenPointPopupStable(int index) {
    logger.debug("Open popup, point by index: " + index);
    getCurrentPointElements();
    Actions builder = new Actions(driver);
    waitForAllElementsToAppear(pointElements, "All point elements are not displayed");
    builder.moveToElement(pointElements.get(index)).pause(500).click().build().perform();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 10); // Wait time in seconds
      wait.until(ExpectedConditions.visibilityOf(pointPopup));
      if (!pointPopup.isDisplayed()) {
        logger.debug("Popup is not present. Retry with a second action.");
        waitForElementToBeClickable(pointPopup, "Point popup is not clickable");
        builder.moveToElement(pointElements.get(index)).pause(500).click().build().perform();
      } else {
        waitForElementToAppear(pointPopup, "Popup is not present");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void clickToOpenPointPopupStable2(int index) {
    try {
      // Debugging log for the index and starting the popup interaction
      System.out.println("Open popup, point by index: " + index);

      // Get all point elements and log their count
      getCurrentPointElements();
      System.out.println("Total point elements found: " + pointElements.size());

      // Ensure that all point elements are displayed
      waitForAllElementsToAppear(pointElements, "All point elements are not displayed");
      System.out.println("All point elements are displayed");

      // Move to the point element, wait a bit, then click it
      Actions builder = new Actions(driver);
      WebElement pointElement = pointElements.get(index);
      System.out.println("Interacting with point element at index " + index + ": " + pointElement.getText());

      builder.moveToElement(pointElement).pause(500).click().build().perform();
      System.out.println("First click performed on point element.");

      // Wait for the point popup to become visible
      WebDriverWait wait = new WebDriverWait(driver, 10); // Wait time in seconds
      wait.until(ExpectedConditions.visibilityOf(pointPopup));
      System.out.println("Popup is visible.");

      // Check if the popup is displayed and handle accordingly
      if (!pointPopup.isDisplayed()) {
        System.out.println("Popup is not present. Retry with a second action.");

        // Retry by waiting for the popup to be clickable, then clicking again
        waitForElementToBeClickable(pointPopup, "Point popup is not clickable");

        // Second attempt to click the point element
        builder.moveToElement(pointElement).pause(500).click().build().perform();
        System.out.println("Second click performed on point element.");
      } else {
        // Ensure the popup is fully visible after the first click
        waitForElementToAppear(pointPopup, "Popup did not appear as expected");
        System.out.println("Popup is fully visible after first click.");
      }

    } catch (NoSuchElementException e) {
      System.out.println("Element not found: " + e.getMessage());
      e.printStackTrace();}}




      public int getPointsCountStable() {
    getCurrentPointElementsStable();
    return pointElements.size();
  }

  protected void getCurrentPointElementsStable() {
    WebDriverWait wait = new WebDriverWait(driver, 10); // Wait time in seconds
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".sa-scatter-plot-point")));
    pointElements = driver.findElements(By.cssSelector(".sa-scatter-plot-point"));
  }

}